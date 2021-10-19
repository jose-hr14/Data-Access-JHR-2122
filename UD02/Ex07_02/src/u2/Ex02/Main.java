package u2.Ex02;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String option = "";
        boolean exit = false;

        while(!exit)
        {
            while(!option.equals("1") && !option.equals("2") && !option.equals("3")){
                System.out.println("1. Obj to XML");
                System.out.println("2. XML to OBJ");
                System.out.println("3. Exit");
                option = new Scanner(System.in).nextLine();
            }
            switch (option)
            {
                case "1":
                    ObjToXML();
                    option = "";
                    break;
                case "2":
                    XmlToObj();
                    option = "";
                    break;
                case "3":
                    exit = true;
                    break;
            }
        }
    }
    public static void ObjToXML(){
        ObjectInputStream objectInputStream = null;
        try {
            System.out.print("Type the obj docname you want to read from: ");
            String objDocname = new Scanner(System.in).nextLine();
            objectInputStream = new ObjectInputStream(new FileInputStream(objDocname));
            //objectInputStream = new ObjectInputStream(new FileInputStream("contact2.obj"));
            ArrayList<Contact> contactList = (ArrayList<Contact>) objectInputStream.readObject();

            System.out.print("Type the xml docname you want to write in: ");
            String xmlDocname = new Scanner(System.in).nextLine();
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(xmlDocname));
            //PrintWriter printWriter = new PrintWriter(new FileOutputStream("contact3.xml"));

            Contact contact;

            printWriter.println("<contactList>");
            for(int i = 0; i < contactList.size(); i++)
            {
                contact = contactList.get(i);
                printWriter.println("\t<contact>");
                printWriter.println("\t\t<name>"+ contact.getName() + "</name>");
                printWriter.println("\t\t<surname>"+ contact.getSurname() + "</surname>");
                printWriter.println("\t\t<email>"+ contact.geteMail() + "</email>");
                printWriter.println("\t\t<phoneNumber>"+ contact.getPhoneNumber() + "</phoneNumber>");
                printWriter.println("\t\t<description>" + contact.getDescription() + "</description>");
                printWriter.println("\t</contact>");
            }
            printWriter.println("</contactList>");

            printWriter.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void XmlToObj(){
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            XmlContactHandler xmlContactHandler = new XmlContactHandler();

            System.out.print("Type the xml you want to read: ");
            String xmlDocname = new Scanner(System.in).nextLine();
            saxParser.parse(xmlDocname, xmlContactHandler);
            //saxParser.parse("contact2.xml", xmlContactHandler);
            ArrayList<Contact> contactList = xmlContactHandler.getContactList();

            System.out.print("Type the obj you want to write in: ");
            String objDocname = new Scanner(System.in).nextLine();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(objDocname));
            //ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("contact2.obj"));
            objectOutputStream.writeObject(contactList);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}