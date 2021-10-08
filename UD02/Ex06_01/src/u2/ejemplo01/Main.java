package u2.ejemplo01;

import java.io.*;
import java.util.ArrayList;

//This is still practice, not the exercise resolved
public class Main {

    public static void main(String[] args) {

    }

    public static void serializationPractice()
    {
        try {
            ObjectOutputStream serializer = new ObjectOutputStream(new FileOutputStream("contact.obj"));
            ArrayList<Contact> contactList = new ArrayList<Contact>();

            contactList.add(new Contact("Jose Miguel", "Navarrete", "navarreteboy@iesmarenostrum.com", "12345678", "my friend"));

            serializer.writeObject(contactList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void deserializationPractice()
    {
        try {
            ObjectInputStream deserializer = new ObjectInputStream(new FileInputStream("contact.obj"));
            ArrayList<Contact> contactList = new ArrayList<Contact>();

            contactList = (ArrayList<Contact>) deserializer.readObject();

            for (Contact contact : contactList) {
                System.out.println(contact.getName());
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
