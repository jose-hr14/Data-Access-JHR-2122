package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//El proyecto usa el JDK 1.8 de Java, ya que el JDK 16 me daba error al escribir objetos con enums en la base de datos
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Museum Database Management ---");
        System.out.println();
        String option = "";
        do{
            System.out.println("Choose an option: ");
            System.out.println("1. Store new element");
            System.out.println("2. List artworks from author");
            System.out.println("3. List artworks from a specific category");
            System.out.println("0. Exit");
            option = new Scanner(System.in).nextLine();

            switch (option)
            {
                case "1":
                    storeMenu();
                    break;
                case "2":
                    listArtworkByAuthor();
                    break;
                case "3":
                    break;
                case "0":
                    break;
            }
        }while(option.matches("[0-3]"));
    }

    public static void storeMenu()
    {
        String option = "";
        do{
            System.out.println("Choose an option: ");
            System.out.println("1. Store new author");
            System.out.println("2. Store new artwork");
            System.out.println("3. Store new painting");
            System.out.println("4. Store new sculpture");
            System.out.println("0. Exit");
            option = new Scanner(System.in).nextLine();
        }while(!option.matches("[0-4]"));

        switch (option)
        {
            case "1":
                Author author;
                if((author = createAuthor()) != null)
                {
                    new Db4oHelper().storeAuthor(author);
                }
                break;
            case "2":
                Artwork artwork;
                if((artwork = createArtwork()) != null)
                {
                    new Db4oHelper().storeArtwork(artwork);
                }
                break;
            case "3":
                Painting painting;
                if((painting = createPainting()) != null)
                {
                    new Db4oHelper().storePainting(painting);
                }
                break;
            case "4":
                Sculpture sculpture;
                if((sculpture = createSculpture()) != null)
                {
                    new Db4oHelper().storeSculpture(sculpture);
                }
                break;
            case "0":
                break;
        }
    }

    public static Author createAuthor()
    {
        Scanner scanner = new Scanner(System.in);
        Author author = new Author();
        System.out.print("Introduce author code: ");
        author.setCode(scanner.nextLine());
        if(new Db4oHelper().autorExits(author.getCode()))
        {
            System.out.println("This author code already exists, aborting operation");
            return null;
        }
        System.out.print("Introduce author name: ");
        author.setName(scanner.nextLine());
        System.out.print("Introduce author nationality: ");
        author.setNationality(scanner.nextLine());
        return author;
    }

    public static Artwork createArtwork()
    {
        Scanner scanner = new Scanner(System.in);
        Artwork artwork = new Artwork();
        System.out.print("Introduce artwork code: ");
        artwork.setCode(scanner.nextInt());
        scanner.nextLine();
        System.out.print("Introduce artwork title: ");
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
        artwork.setAuthorCode(scanner.nextLine());
        Db4oHelper db = new Db4oHelper();
        if(!db.autorExits(artwork.getAuthorCode()))
        {
            System.out.println("This author don't exists, aborting operation ");
            return null;
        }
        return artwork;
    }

    public static Painting createPainting()
    {
        Scanner scanner = new Scanner(System.in);
        Painting painting = new Painting();
        System.out.print("Introduce artwork code: ");
        painting.setCode(scanner.nextInt());
        scanner.nextLine();
        System.out.print("Introduce artwork tittle: ");
        painting.setTitle(scanner.nextLine());
        System.out.print("Introduce artwork date in dd-MM-yyyy format: ");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            painting.setDate(format.parse(scanner.nextLine()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.print("Introduce a style (GRECOROMAN,NEOCLASSIC or CUBISM): ");
        painting.setStyle(Styles.valueOf(scanner.nextLine().toUpperCase()));
        System.out.print("Introduce and author code: ");
        painting.setAuthorCode(scanner.nextLine());
        Db4oHelper db = new Db4oHelper();
        if(db.autorExits(painting.getAuthorCode()))
        {
            System.out.println("This author don't exists, aborting operation ");
            return null;
        }
        scanner.nextLine();
        System.out.print("Enter the painting type (OILPAINTING, WATERCOLOUR, PASTEL): ");
        painting.setPaintingType(PaintingTypes.valueOf(scanner.nextLine().toUpperCase()));
        DimensionsType dimensions = new DimensionsType();
        System.out.print("Introduce painting width: ");
        dimensions.setWidth(scanner.nextFloat());
        scanner.nextLine();
        System.out.print("Introduce painting height: ");
        dimensions.setHeight(scanner.nextFloat());
        painting.setDimensionsType(dimensions);
        return painting;
    }

    public static Sculpture createSculpture()
    {
        Scanner scanner = new Scanner(System.in);
        Sculpture sculpture = new Sculpture();
        System.out.print("Introduce artwork code: ");
        sculpture.setCode(scanner.nextInt());
        scanner.nextLine();
        System.out.print("Introduce artwork tittle: ");
        sculpture.setTitle(scanner.nextLine());
        System.out.print("Introduce artwork date in dd-MM-yyyy format: ");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            sculpture.setDate(format.parse(scanner.nextLine()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.print("Introduce a style (GRECOROMAN,NEOCLASSIC or CUBISM): ");
        sculpture.setStyle(Styles.valueOf(scanner.nextLine().toUpperCase()));
        System.out.print("Introduce and author code: ");
        sculpture.setAuthorCode(scanner.nextLine());
        Db4oHelper db = new Db4oHelper();
        if(db.autorExits(sculpture.getAuthorCode()))
        {
            System.out.println("This author don't exists, aborting operation ");
            return null;
        }
        System.out.print("Enter the material type (IRON, BRONZE, MARBLE): ");
        sculpture.setMaterial(MaterialTypes.valueOf(scanner.nextLine().toUpperCase()));
        System.out.print("Introduce painting weight: ");
        sculpture.setWeight(scanner.nextFloat());
        return sculpture;
    }
    public static void listArtworkByAuthor()
    {
        System.out.print("Type author name: ");
        Author author = new Db4oHelper().retriveAuthorByName(new Scanner(System.in).nextLine());
        if(author != null)
        {
            List<Artwork> artworkList = new Db4oHelper().listArtworksByAuthor(author.getCode());
            if(!artworkList.isEmpty())
            {
                for (Artwork artwork: artworkList)
                {
                    System.out.println(artwork.title);
                }
            }
            else
                System.out.println("Author has no artworks stored");
        }
        else
            System.out.println("Author does not exists");
    }
}
