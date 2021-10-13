package u2.ejemplo01;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XMLhander extends DefaultHandler {
    protected String currentTag;
    protected String tagContent;

    public void startElement(String uri, String localName, String qName, Attributes attributes)
    {
        currentTag = qName;

        if(currentTag.equals("contact"))
        {
            System.out.println("ID: " + attributes.getValue("id"));
        }
    }
    public void characters(char characters[], int start, int length)
    {
        tagContent = new String(characters, start, length);
        if(currentTag.equals("Name"))
            System.out.println(tagContent);
    }
    public void endElement(String uri, String localName, String qName)
    {
        
    }
}
