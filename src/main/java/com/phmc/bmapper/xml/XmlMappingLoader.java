package com.phmc.bmapper.xml;

import com.pedrocosta.springutils.output.Log;
import com.phmc.bmapper.BMapper;
import com.phmc.bmapper.ChainPropertyDescriptor;
import com.phmc.bmapper.PropertyDescriptor;
import com.phmc.bmapper.utils.MapperUtils;
import com.phmc.bmapper.utils.MappingLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class XmlMappingLoader implements MappingLoader {
    @Override
    public Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedProperties(ApplicationContext context) {
        Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mappedObjects = new HashMap<>();

        Set<Document> xmlDocuments = XmlFileLoader.loadXmlDocuments();

        for (Document document : xmlDocuments) {
            document.getDocumentElement().normalize();

            NodeList mappingNodeList = document.getElementsByTagName(XmlSchemaTag.MAPPING.getTag());

            for (int indexMapping = 0; indexMapping < mappingNodeList.getLength(); indexMapping++) {
                Node mappingNode = mappingNodeList.item(indexMapping);

                if (mappingNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element mappingElement = (Element)mappingNode;
                    ChainPropertyDescriptor chainPropDescClassA = getChainPropertyDescriptor(mappingElement, XmlSchemaTag.A);
                    ChainPropertyDescriptor chainPropDescClassB = getChainPropertyDescriptor(mappingElement, XmlSchemaTag.B);

                    // Create mapping properties from class A to class B
                    Map<ChainPropertyDescriptor, ChainPropertyDescriptor> chainPropDescMapAToB = new HashMap<>();
                    chainPropDescMapAToB.put(chainPropDescClassA, chainPropDescClassB);
                    mappedObjects.put(
                            MapperUtils.getMappingKeyName(chainPropDescClassA.getObjClass(), chainPropDescClassB.getObjClass()),
                            chainPropDescMapAToB);

                    // Create mapping properties from class B to class A
                    Map<ChainPropertyDescriptor, ChainPropertyDescriptor> chainPropDescMapBToA = new HashMap<>();
                    chainPropDescMapBToA.put(chainPropDescClassA, chainPropDescClassB);
                    mappedObjects.put(
                            MapperUtils.getMappingKeyName(chainPropDescClassB.getObjClass(), chainPropDescClassA.getObjClass()),
                            chainPropDescMapBToA);
                }
            }
        }

        return mappedObjects;
    }

    @NotNull
    private ChainPropertyDescriptor getChainPropertyDescriptor(Element mappingElement, XmlSchemaTag schemaTag) {
        ChainPropertyDescriptor chainPropDesc = new ChainPropertyDescriptor();

        String classAttr = "";
        switch (schemaTag) {
            case A:
                classAttr = "class-a";
                break;
            case B:
                classAttr = "class-b";
                break;
        }

        try {
            Class<?> clazz = Class.forName(mappingElement.getAttribute(classAttr));
            chainPropDesc.setObjClass(clazz);

            NodeList fieldNodeList = mappingElement.getElementsByTagName(XmlSchemaTag.FIELD.getTag());
            for (int indexField = 0; indexField < fieldNodeList.getLength(); indexField++) {
                Node fieldNode = fieldNodeList.item(indexField);
                if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fieldElement = (Element)fieldNode;

                    final String typeAttrName = "type";
                    Class<?> fieldTypeClass = null;
                    if (fieldElement.hasAttribute(typeAttrName)) {
                        fieldTypeClass = Class.forName(fieldElement.getAttribute(typeAttrName));
                    }

                    NodeList fieldANodeList = fieldElement.getElementsByTagName(schemaTag.getTag());
                    if (fieldANodeList.getLength() > 0) {
                        String propertyName = fieldANodeList.item(0).getNodeValue();
                        java.beans.PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, propertyName);

                        if (isValidProperty(propertyDescriptor, fieldTypeClass)) {
                            chainPropDesc.add(new PropertyDescriptor(propertyDescriptor, clazz, MapperUtils.getInstance(clazz), null));
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | IntrospectionException e) {
            Log.error(this, e);
            throw new RuntimeException(e);
        }

        return chainPropDesc;
    }

    @Contract("null, _ -> false")
    private boolean isValidProperty(java.beans.PropertyDescriptor propertyDescriptor, Class<?> typeClass) {
        return MapperUtils.isValidPropertyDescriptor(propertyDescriptor)
                && propertyDescriptor.getPropertyType().equals(typeClass);
    }
}
