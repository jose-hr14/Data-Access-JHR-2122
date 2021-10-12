package u2.ejemplo01;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//This is still practice, not the exercise resolved
public class Main {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String option;

        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream  objectInputStream = null;
        ArrayList<Contact> contactList = new ArrayList<>();

        try {
            if(new File("contact.dat").exists())
                contactList = (ArrayList<Contact>) objectInputStream.readObject();

            objectOutputStream = new ObjectOutputStream(new FileOutputStream("contact.dat"));
            objectInputStream = new ObjectInputStream(new FileInputStream("contact.dat"));

            while(true)
            {
                do {
                    System.out.println("Select an option");
                    System.out.println("1. Save contact");
                    System.out.println("2. Show contact list");
                    System.out.println("3. Search contact by name");
                    System.out.println("4. Exit");
                    option = keyboard.nextLine();
                }while (!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4"));
                switch (option)
                {
                    case "1":
                    {
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

                        objectOutputStream.writeObject(contactList);
                        break;
                    }
                    case "2":

                        break;
                    case "3":
                        break;
                    case "4":
                        return;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        }


    }

    public static void aux(){

    }

    public static void saveContact() {
        Scanner keyboard = new Scanner(System.in);
        ObjectOutputStream serializer = null;
        try {

            //if (new File("contact.obj").exists())
              //  serializer = new ObjectOutputStream(new FileOutputStream(new File("contact.obj"), true));
            //else
                //serializer = new ObjectOutputStream(new FileOutputStream(new File("contact.obj"), false));
            serializer = new ObjectOutputStream(new FileOutputStream(new File("contact.obj"), false));
            ArrayList<Contact> contactList = new ArrayList<Contact>();




            //serializer.writeObject(newContact);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serializer.flush();
                serializer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void readContactList() {
        ObjectInputStream deserializer = null;
        try {
            deserializer = new ObjectInputStream(new FileInputStream(new File("contact.obj")));

            Contact contact = (Contact) deserializer.readObject();
            //ArrayList<Contact> contactRead = (ArrayList<Contact>) deserializer.readObject();




        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                deserializer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
