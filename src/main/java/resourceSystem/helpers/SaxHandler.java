package resourceSystem.helpers;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;


/**
 * Created by stalker on 31.01.16.
 */
public class SaxHandler extends DefaultHandler {
    private static String CLASS = "class";
    private String element = null;
    private Object object = null;

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start document");
    }

    //uri - пространство имен
    //localName - локальное имя элемента
    //qName - комбинация локального имени с пространством имен (разделяется двоеточием)
    //attributes - атрибуты данного элемента
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes ) throws SAXException {
        if(!qName.equals(CLASS)){
            element = qName;
        } else {
            String className = attributes.getValue(0);
            System.out.println("Class name: " + className);
            object = ReflectionHelper.createInstance(className);
        }
    }

    @Override
    public void endElement(String uri,String localName, String qName) throws SAXException{
        element = null;
    }

    //ch[] - массив символьных данных
    //start - индекс первого элемента
    //length - длина сообщаемой символьной строки
    @Override
    public void characters(char ch[], int start, int length) throws  SAXException{
        if(element != null){
            String value =  new String(ch, start, length);
            System.out.println(element + " = " + value);
            ReflectionHelper.setFieldValue(object, element,value);
        }
    }

    @Override
    public void endDocument() throws  SAXException {
        System.out.println("End document ");
    }

    public Object getObject(){
        return object;
    }

}
