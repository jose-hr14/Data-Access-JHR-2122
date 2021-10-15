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

        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            XmlContactHandler xmlContactHandler = new XmlContactHandler();
            saxParser.parse("contact2.xml", xmlContactHandler);

            ArrayList<Contact> contactList = xmlContactHandler.getContactList();

            for(int i = 0; i < contactList.size(); i++)
            {
                System.out.println(contactList.get(i).getName());
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void Aux(){
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        List<Contact> contactList;
        Scanner keyboard = new Scanner(System.in);
        String option;

        try {
            if ((new File("contact.dat").exists())) {
                objectInputStream = new ObjectInputStream(new FileInputStream("contact.dat"));
                contactList = (ArrayList<Contact>) objectInputStream.readObject();
            } else {
                objectOutputStream = new ObjectOutputStream(new FileOutputStream("contact.dat"));
                contactList = new ArrayList<>();
            }

            while (true) {
                do {
                    System.out.println("Select an option");
                    System.out.println("1. Save contact");
                    System.out.println("2. Show contact list");
                    System.out.println("3. Search contact by name");
                    System.out.println("4. Search contact by phone number");
                    System.out.println("5. Exit");
                    option = keyboard.nextLine();
                } while (!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4") && !option.equals("5"));
                switch (option) {
                    case "1": {
                        String name, surname, email, phoneNumber, description;
                        System.out.println("Type contact name");
                        name = keyboard.nextLine();
                        System.out.println("Type contact surname");
                        surname = keyboard.nextLine();
                        System.out.println("Type contact email");
                        email = keyboard.nextLine();
                        System.out.println("Type contact phone number");
                        phoneNumber = keyboard.nextLine();
                        System.out.println("Type contact description");
                        description = keyboard.nextLine();

                        contactList.add(new Contact(name, surname, email, phoneNumber, description));
                        System.out.println("Contact saved \n");
                        break;
                    }
                    case "2": {
                        System.out.println();
                        for (int i = 0; i < contactList.size(); i++) {
                            System.out.println(contactList.get(i).getName());
                        }
                        System.out.println();
                        break;
                    }

                    case "3": {
                        System.out.print("Type a contact name: ");
                        String name = keyboard.nextLine();
                        for (int i = 0; i < contactList.size(); i++) {
                            if (contactList.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
                                System.out.print("Contact found: ");
                                System.out.println(contactList.get(i).getName() + " " + contactList.get(i).getSurname());
                            }
                        }
                        System.out.println();
                        break;
                    }
                    case "4": {
                        System.out.print("Type a contact number: ");
                        String phoneNumber = keyboard.nextLine();
                        for (int i = 0; i < contactList.size(); i++) {
                            if (contactList.get(i).getPhoneNumber().toLowerCase().equals(phoneNumber.toLowerCase())) {
                                System.out.print("Contact found: ");
                                System.out.println(contactList.get(i).getName() + " " + contactList.get(i).getSurname());
                            }
                        }
                        System.out.println();
                        break;
                    }

                    case "5": {
                        objectOutputStream = new ObjectOutputStream(new FileOutputStream("contact.dat"));
                        objectOutputStream.writeObject(contactList);
                        return;
                    }
                }
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                objectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
