package com.phmc.bmapper.xml;

import com.pedrocosta.springutils.output.Log;
import com.phmc.bmapper.exceptions.LoadConfigException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class XmlFileLoader {

    private static final String XML_SUFFIX = "_bmapper.xml";

    public static Set<Document> loadXmlDocuments() throws LoadConfigException {
        return loadXmlDocuments(null);
    }

    public static Set<Document> loadXmlDocuments(final String className) throws LoadConfigException {
        String completeXmlFileName = XML_SUFFIX;
        if (className != null) {
            completeXmlFileName = className.endsWith(XML_SUFFIX)
                    ? className
                    : className + completeXmlFileName;
        } else {
            completeXmlFileName = "/**/*" + completeXmlFileName;
        }

        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resourceFiles = resolver.getResources("classpath*:" + completeXmlFileName);
            Set<Document> setXmlDocs = new HashSet<>(resourceFiles.length);
            for (Resource resource : resourceFiles) {
                try {
                    File xmlFile = new File(resource.getURL().toURI());
                    DocumentBuilderFactory xmlBuilderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder xmlBuilder = xmlBuilderFactory.newDocumentBuilder();
                    Document document = xmlBuilder.parse(xmlFile);

                    // Just process files with "bmapper" tag
                    if (XmlSchemaTag.contains(document.getDocumentElement().getNodeName())) {
                        setXmlDocs.add(document);
                    }
                } catch (URISyntaxException | ParserConfigurationException | SAXException | IOException e) {
                    Log.error(XmlFileLoader.class, new LoadConfigException(
                            String.format("Could not load XML file %s", resource.getFilename()), e));
                }
            }

            return setXmlDocs;
        } catch (IOException e) {
            LoadConfigException loadConfigException = new LoadConfigException(
                    String.format("Could not find XML file %s", completeXmlFileName), e);

            Log.error(XmlFileLoader.class, loadConfigException);
            throw loadConfigException;
        }
    }
}
