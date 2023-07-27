package com.phmc.bmapper.xml;

import com.pedrocosta.springutils.output.Log;
import com.phmc.bmapper.BMapper;
import com.phmc.bmapper.ChainPropertyDescriptor;
import com.phmc.bmapper.PropertyDescriptor;
import com.phmc.bmapper.utils.MapperUtils;
import com.phmc.bmapper.utils.MappingContext;
import com.phmc.bmapper.utils.MappingLoader;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

public class XmlMappingLoader implements MappingLoader {
    @Override
    public @NotNull Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedProperties(final MappingContext mappingContext) {
        Set<Document> xmlDocuments = XmlFileLoader.loadXmlDocuments();
        return getMappedProperties(xmlDocuments);
    }

    @Override
    public @NotNull Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedProperties(final Class<?> classA, final Class<?> classB) {
        Set<Document> xmlDocuments = XmlFileLoader.loadXmlDocuments(classA.getSimpleName());
        if (xmlDocuments.isEmpty()) {
            xmlDocuments = XmlFileLoader.loadXmlDocuments(classB.getSimpleName());
        }

        return getMappedProperties(xmlDocuments);
    }

    @NotNull
    private Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedProperties(Set<Document> xmlDocuments) {
        Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mappedObjects = new HashMap<>();

        for (Document document : xmlDocuments) {
            document.getDocumentElement().normalize();

            NodeList mappingNodeList = document.getElementsByTagName(XmlSchemaTag.MAPPING.getTag());

            for (int indexMapping = 0; indexMapping < mappingNodeList.getLength(); indexMapping++) {
                Node mappingNode = mappingNodeList.item(indexMapping);

                if (mappingNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element mappingElement = (Element)mappingNode;

                    XmlMappingClass xmlMappingClass = getXmlMappingClass(mappingElement);
                    updateMap(mappedObjects, xmlMappingClass);

                    XmlMappingClass revertedMappingClass = revertMappingClass(xmlMappingClass);
                    updateMap(mappedObjects, revertedMappingClass);

                    XmlMappingClass xmlMappingSameClassA = getXmlMappingSameClass(xmlMappingClass, xmlMappingClass.getClassA());
                    updateMap(mappedObjects, xmlMappingSameClassA);

                    XmlMappingClass xmlMappingSameClassB = getXmlMappingSameClass(xmlMappingClass, xmlMappingClass.getClassB());
                    updateMap(mappedObjects, xmlMappingSameClassB);
                }
            }
        }

        return mappedObjects;
    }

    private Map<ChainPropertyDescriptor, ChainPropertyDescriptor> getMappedProperties(XmlMappingClass xmlMappingClass) {
        Map<ChainPropertyDescriptor, ChainPropertyDescriptor> resultMapChainPropDesc = new HashMap<>();

        // Load fields from XML file
        if (!xmlMappingClass.hasFields() && !xmlMappingClass.hasSameFields()) {
            return resultMapChainPropDesc;
        }

        Log.info(BMapper.class,
                String.format("Initiate mapping: %s -> %s",
                        xmlMappingClass.getClassA().getSimpleName(),
                        xmlMappingClass.getClassB().getSimpleName()));

        for (XmlMappingField xmlMappingField : xmlMappingClass.getMappingFields()) {
            addToChainPropDescMap(resultMapChainPropDesc, xmlMappingClass.getClassA(), xmlMappingField.getFieldA(),
                    xmlMappingClass.getClassB(), xmlMappingField.getFieldB());
        }

        // Mapping fields with same names
        if (xmlMappingClass.hasSameFields()) {
            PropertyDescriptor[] propDescArrClassA = MapperUtils.getPropertyDescriptors(xmlMappingClass.getClassA());
            PropertyDescriptor[] propDescArrClassB = MapperUtils.getPropertyDescriptors(xmlMappingClass.getClassB());
            int minSize = Math.min(propDescArrClassA.length, propDescArrClassB.length);

            for (int i = 0; i < minSize; i++) {
                PropertyDescriptor propDescClassA = propDescArrClassA[i];
                PropertyDescriptor propDescClassB = propDescArrClassB[i];
                if (propDescClassA.getName().equals(propDescClassB.getName())) {
                    addToChainPropDescMap(resultMapChainPropDesc, xmlMappingClass.getClassA(), propDescClassA.getName(),
                            xmlMappingClass.getClassB(), propDescClassB.getName());
                }
            }
        }

        return resultMapChainPropDesc;
    }

    private void addToChainPropDescMap(Map<ChainPropertyDescriptor, ChainPropertyDescriptor> resultMapChainPropDesc,
                                       Class<?> classA, String fieldNameA, Class<?> classB, String fieldNameB) {
        ChainPropertyDescriptor chainPropDescClassA = MapperUtils.buildChainPropertyDescriptor(classA, fieldNameA);
        ChainPropertyDescriptor chainPropDescClassB = MapperUtils.buildChainPropertyDescriptor(classB, fieldNameB);
        resultMapChainPropDesc.put(chainPropDescClassB, chainPropDescClassA);
    }

    @NotNull
    private XmlMappingClass getXmlMappingClass(Element mappingElement) {
        XmlMappingClass xmlMappingClass = new XmlMappingClass();

        try {
            xmlMappingClass.setClassA(Class.forName(mappingElement.getAttribute("class-a")));
            xmlMappingClass.setClassB(Class.forName(mappingElement.getAttribute("class-b")));

            NodeList fieldNodeList = mappingElement.getElementsByTagName(XmlSchemaTag.FIELD.getTag());
            for (int indexField = 0; indexField < fieldNodeList.getLength(); indexField++) {
                Node fieldNode = fieldNodeList.item(indexField);
                if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fieldElement = (Element)fieldNode;

                    if (XmlSchemaTag.SAME_FIELDS.getTag().equals(fieldElement.getTagName())) {
                        xmlMappingClass.setHasSameFields(true);
                    }

                    NodeList fieldANodeList = fieldElement.getElementsByTagName(XmlSchemaTag.A.getTag());
                    NodeList fieldBNodeList = fieldElement.getElementsByTagName(XmlSchemaTag.B.getTag());

                    if (fieldANodeList.getLength() <= 0 || fieldBNodeList.getLength() <= 0) {
                        continue;
                    }

                    XmlMappingField xmlMappingField = new XmlMappingField();
                    xmlMappingField.setFieldType(fieldElement.getAttribute("type"));

                    String propertyAName = fieldANodeList.item(0).getTextContent();
                    xmlMappingField.setFieldA(propertyAName);

                    String propertyBName = fieldBNodeList.item(0).getTextContent();
                    xmlMappingField.setFieldB(propertyBName);

                    xmlMappingClass.addMappingField(xmlMappingField);
                }
            }
        } catch (ClassNotFoundException e) {
            Log.error(this, e);
            throw new RuntimeException(e);
        }

        return xmlMappingClass;
    }

    private void updateMap(Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mappedObjects, XmlMappingClass xmlMappingClass) {
        String keyName = MapperUtils.getMappingKeyName(xmlMappingClass.getClassA(), xmlMappingClass.getClassB());
        if (!mappedObjects.containsKey(keyName)) {
            mappedObjects.put(keyName, getMappedProperties(xmlMappingClass));
        }
    }

    public XmlMappingClass revertMappingClass(final XmlMappingClass xmlMappingClass) {
        XmlMappingClass revertedMappingClass = new XmlMappingClass();

        revertedMappingClass.setClassA(xmlMappingClass.getClassB());
        revertedMappingClass.setClassB(xmlMappingClass.getClassA());
        revertedMappingClass.setHasSameFields(xmlMappingClass.hasSameFields());

        for (XmlMappingField xmlMappingField : xmlMappingClass.getMappingFields()) {
            XmlMappingField revertedMappingField = new XmlMappingField();
            revertedMappingField.setFieldType(xmlMappingField.getFieldType());
            revertedMappingField.setFieldA(xmlMappingField.getFieldB());
            revertedMappingField.setFieldB(xmlMappingField.getFieldA());
            revertedMappingClass.addMappingField(revertedMappingField);
        }

        return revertedMappingClass;
    }

    public XmlMappingClass getXmlMappingSameClass(final XmlMappingClass xmlMappingClass, final @NotNull Class<?> clazz) {
        XmlMappingClass mappingSameClass = new XmlMappingClass();
        mappingSameClass.setHasSameFields(true);

        if (clazz.equals(xmlMappingClass.getClassA())) {
            mappingSameClass.setClassA(xmlMappingClass.getClassA());
            mappingSameClass.setClassB(xmlMappingClass.getClassA());
        } else {
            mappingSameClass.setClassA(xmlMappingClass.getClassB());
            mappingSameClass.setClassB(xmlMappingClass.getClassB());
        }

        return mappingSameClass;
    }
}
