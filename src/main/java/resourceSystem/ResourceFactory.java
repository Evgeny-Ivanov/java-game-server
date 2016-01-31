package resourceSystem;


import org.xml.sax.SAXException;
import resourceSystem.helpers.SaxHandler;
import resourceSystem.helpers.VFS;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by stalker on 31.01.16.
 */
public class ResourceFactory {
    public static final String ROOT_DIR = "";
    private SAXParser saxParser;

    private static final ResourceFactory RESOURCE_FACTORY = new ResourceFactory();

    private ResourceFactory(){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            saxParser = factory.newSAXParser();//получение SAX-анализатора
        } catch (SAXException | ParserConfigurationException e){
            e.printStackTrace();
        }
    }

    public Resource get(String xmlFile){
        SaxHandler handler = new SaxHandler();
        try {
            saxParser.parse(xmlFile, handler);
        } catch (IOException | SAXException e){
            e.printStackTrace();
        }

        return (Resource)handler.getObject();
    }

    public static Map<Class<?>,Resource> loadResources(String resourcesDir){
        Map<Class<?>,Resource> resources = new HashMap<>();
        VFS vfs = new VFS();
        Iterator<String> iterator = vfs.getIterator("data");
        while (iterator.hasNext()){
            String fileName = iterator.next();
            System.out.println(fileName);
            if (fileName.contains(".xml")) {
                Resource resource = RESOURCE_FACTORY.get(fileName);
                resources.put(resource.getClass(), resource);
            }
        }
        return resources;
    }
}
