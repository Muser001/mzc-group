package com.model.util.metadata;

import com.model.init.dict.Dict;
import org.xml.sax.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.InputStream;

public class JaxUtils {
    private static final String VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
    private static final String NAMESPACES_FEATURE = "http://xml.org/sax/features/namespaces";
    private static final String EXTERNAL_GENERAL_ENTITIES_FEATURE = "http://xml.org/sax/features/external-general-entities";
    private static final String EXTERNAL_PARAMETER_ENTITIES_FEATURE = "http://xml.org/sax/features/external-parameter-entities";
    private static final String SECURE_PROCESSING_FEATURE = "http://xml.org/sax/features/secure-processing";
    private static final String DISALLOW_DOCTYPE_DECL_FEATURE = "http://xml.org/sax/features/disallow-doctype-decl";

    public JaxUtils() {
    }

    public static Dict readXML(InputStream is) throws JAXBException, SAXException, ParserConfigurationException {
        JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{Dict.class, Field.class, Enumeration.class});
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        InputSource inputSource = new InputSource(is);
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setFeature("http://xml.org/sax/features/validation",false);
        spf.setFeature("http://xml.org/sax/features/namespaces",true);
        spf.setFeature("http://xml.org/sax/features/external-general-entities",false);
        spf.setFeature("http://xml.org/sax/features/external-parameter-entities",false);
        spf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing",true);
        spf.setFeature("http://apache.org/xml/features/disallow-doctype-decl",true);
        SAXParser sp = spf.newSAXParser();
        XMLReader xmlReader = sp.getXMLReader();
        SAXSource saxSource = new SAXSource(xmlReader,inputSource);
        Dict obj = (Dict)unmarshaller.unmarshal(saxSource);
        return obj;
    }
}
