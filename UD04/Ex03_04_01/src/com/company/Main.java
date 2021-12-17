package com.company;


import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import java.util.Scanner;

//store en lugar de set, y retrieve en lugar del get
public class Main {
    public static void main(String[] args) {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "museum.dat");
        db.store(new Author("1", "Smith", "German"));
        db.store(new Author("1", "Smith", "German"));
        db.store(new Author("1", "Smith", "German"));
        db.commit();
        db.close();
    }

    public static void createAuthor()
    {
        Scanner scanner = new Scanner(System.in);
        Author author = new Author();
        System.out.print("Introduce author code: ");
        author.setCode(scanner.nextLine());
        System.out.print("Introduce author name: ");
        author.setName(scanner.nextLine());
        System.out.println("Introduce author nationality");
        
    }
}
