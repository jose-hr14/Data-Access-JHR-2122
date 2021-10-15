package u2.Ex01;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XMLhander extends DefaultHandler {
    protected String currentTag;
    protected String tagContent;
    protected boolean isEmail = false;

    public void startElement(String uri, String localName, String qName, Attributes attributes)
    {
        currentTag = qName;
        if (currentTag.equals("emails")) {
            isEmail = true;
        }

    }
    public void characters(char characters[], int start, int length)
    {
        tagContent = new String(characters, start, length);
    }
    public void endElement(String uri, String localName, String qName)
    {
        if(currentTag.equals("name"))
        {
            System.out.print("Full name: " + tagContent);
        }
        if(currentTag.equals("surname"))
        {
            System.out.println(" " + tagContent);
        }
        if(currentTag.equals("cell") && isEmail)
        {
            System.out.println("Cell email: " +tagContent);
        }
        if(currentTag.equals("work") && isEmail)
        {
            System.out.println("Work email: " + tagContent);
        }
        if(currentTag.equals("home") && isEmail)
        {
            System.out.println("Home email: " + tagContent);
        }
        if(qName.equals("emails"))
            isEmail = false;
        currentTag = "";

    }
}
