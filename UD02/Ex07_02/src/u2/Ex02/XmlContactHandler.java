package u2.Ex02;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class XmlContactHandler extends DefaultHandler {
    ArrayList<Contact> contactList;
    Contact contact;
    String currentTag;
    String tagContent;

    public XmlContactHandler(){
        contactList = new ArrayList<Contact>();
    }

    public XmlContactHandler(ArrayList<Contact> contactList){
        this.contactList = contactList;
    }

    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<Contact> contactList) {
        this.contactList = contactList;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equals("contact"))
        {
            contact = new Contact();
        }
    }
    public void characters( char ch[], int start, int length) throws SAXException{
        tagContent = new String(ch, start, length);
    }
    public void endElement(String uri, String localName, String qName) throws SAXException{
        if(qName.equals("name")){
            contact.setName(tagContent);
        }
        if(qName.equals("surname")){
            contact.setSurname(tagContent);
        }
        if(qName.equals("email")){
            contact.seteMail(tagContent);
        }
        if(qName.equals("phoneNumber")){
            contact.setPhoneNumber(tagContent);
        }
        if(qName.equals("description")){
            contact.setDescription(tagContent);
        }
        if(qName.equals("contact"))
        {
            contactList.add(contact);
        }
    }
}
