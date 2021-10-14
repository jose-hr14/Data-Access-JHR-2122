package u2.ejemplo01;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XMLhander extends DefaultHandler {
    protected String currentTag;
    protected String tagContent;
    protected boolean phones = false;

    public void startElement(String uri, String localName, String qName, Attributes attributes)
    {
        currentTag = qName;
        if(qName.equals("phones"))
            phones = true;

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
            if(currentTag.equals("cell") && phones)
            {
                System.out.println(" " + tagContent);
            }
            if(currentTag.equals("work") && phones)
            {
                System.out.println(tagContent);
            }
            if(currentTag.equals("home") && phones)
            {
                System.out.println(tagContent);
            }
            if(currentTag.equals("phones"))
                phones = false;
            currentTag = "";
        }
    }
}
