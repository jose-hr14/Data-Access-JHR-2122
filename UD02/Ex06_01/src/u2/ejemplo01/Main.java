package u2.ejemplo01;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Please, check just the main, the rest is just practice
public class Main {
    public static void main(String[] args) {
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



    //From here, just practice
    public static void aux() {
        Scanner keyboard = new Scanner(System.in);
        String option;

        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        ArrayList<Contact> contactList = new ArrayList<>();

        try {
            if (new File("contact.dat").exists())
                contactList = (ArrayList<Contact>) objectInputStream.readObject();

            objectOutputStream = new ObjectOutputStream(new FileOutputStream("contact.dat"));
            objectInputStream = new ObjectInputStream(new FileInputStream("contact.dat"));

            while (true) {
                do {
                    System.out.println("Select an option");
                    System.out.println("1. Save contact");
                    System.out.println("2. Show contact list");
                    System.out.println("3. Search contact by name");
                    System.out.println("4. Exit");
                    option = keyboard.nextLine();
                } while (!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4"));
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

                        objectOutputStream.writeObject(contactList);
                        objectOutputStream.flush();
                        objectInputStream.close();
                        break;
                    }
                    case "2": {
                        for (int i = 0; i < contactList.size(); i++) {
                            System.out.println(contactList.get(i).getName());
                        }
                        break;
                    }

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
        }


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

    public static void saveContactt() {
        ObjectOutputStream objectWriter = null;
        List<Contact> contacts = new ArrayList<>();
        try {
            objectWriter = new ObjectOutputStream(new FileOutputStream("archivo.dat"));
            contacts.add(new Contact("David"));
            contacts.add(new Contact("Mario"));
            objectWriter.writeObject(contacts);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (objectWriter != null)
                try {
                    objectWriter.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
        }
    }

    public static void readContactt(List<Contact> contactList) {

        contactList = null;
        ObjectInputStream objectReader = null;
        try {
            objectReader = new ObjectInputStream(new FileInputStream("archivo.dat"));
            contactList = (ArrayList<Contact>) objectReader.readObject();
        } catch (IOException | ClassNotFoundException fnfe) {
            fnfe.printStackTrace();
        } finally {
            if (objectReader != null)
                try {
                    objectReader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
        }

        for (int i = 0; i < contactList.size(); i++) {
            System.out.println(contactList.get(i).getName());
        }


    }
}
