package com.company;


import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

//El proyecto usa el JDK 1.8 de Java, ya que el JDK 16 me daba error al escribir objetos con enums en la base de datos
public class Main {
    public static void main(String[] args) {
/*        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "museum.dat");
        db.store(new Author("1", "Smith", "German"));
        db.store(new Author("1", "Smith", "German"));
        db.store(new Author("1", "Smith", "German"));
        db.commit();
        db.close();*/
        createArtwork();
    }

    public static void createAuthor()
    {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "museum.dat");
        Scanner scanner = new Scanner(System.in);
        Author author = new Author();
        System.out.print("Introduce author code: ");
        author.setCode(scanner.nextLine());
        System.out.print("Introduce author name: ");
        author.setName(scanner.nextLine());
        System.out.println("Introduce author nationality");
        author.setNationality(scanner.nextLine());
        db.store(author);
        db.commit();
        db.close();
    }

    public static void createArtwork()
    {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "museum.dat");
        Scanner scanner = new Scanner(System.in);
        Artwork artwork = new Artwork();
        System.out.print("Introduce artwork code: ");
        artwork.setCode(scanner.nextInt());
        scanner.nextLine();
        System.out.print("Introduce artwork tittle: ");
        artwork.setTitle(scanner.nextLine());
        System.out.print("Introduce artwork date in dd-MM-yyyy format: ");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            artwork.setDate(format.parse(scanner.nextLine()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.print("Introduce a style (GRECOROMAN,NEOCLASSIC or CUBISM): ");
        artwork.setStyle(Styles.valueOf(scanner.nextLine().toUpperCase()));
        System.out.print("Introduce and author code: ");
        artwork.setAuthorCode(scanner.nextInt());
        db.store(artwork);
        db.commit();
        db.close();
    }

    public void createPainting()
    {
        
    }
}
