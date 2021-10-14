package u2.ejemplo01;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XMLhander extends DefaultHandler {
    protected String currentTag;
    protected String tagContent;

    public void startElement(String uri, String localName, String qName, Attributes attributes)
    {
        currentTag = qName;

    }
    public void characters(char characters[], int start, int length)
    {
        tagContent = new String(characters, start, length);
    }
    public void endElement(String uri, String localName, String qName)
    {
        String father = "";
        if(!currentTag.isBlank())
        {
            if(currentTag.equals("name"))
            {
                System.out.print("Full name: " + tagContent);
            }
            if(currentTag.equals("surname"))
            {
                System.out.println(" " + tagContent);
            }
            if(currentTag.equals("cell") && father.equals("phones"))
            {

            }
            if(currentTag.equals("work") && father.equals("phones"))
            {
                System.out.println(tagContent);
            }
            if(currentTag.equals("home") && father.equals("phones"))
            {
                System.out.println(tagContent);
            }
            currentTag = "";
        }
        else
        {
            father = currentTag;
        }
    }
}
