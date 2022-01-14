package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

//Project uses JDK 1.8, because JDK 16 threw error while trying to write enums in the database
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Museum Database Management ---");
        System.out.println();
        String option = "";
        do {
            System.out.println("Choose an option: ");
            System.out.println("1. Store new element");
            System.out.println("2. List artworks from author");
            System.out.println("3. List artworks from a specific category");
            System.out.println("0. Exit");
            option = new Scanner(System.in).nextLine();

            switch (option) {
                case "1":
                    storeMenu();
                    break;
                case "2":
                    listArtworkByAuthorMenu();
                    break;
                case "3":
                    listBySpecificCategoryMenu();
                    break;
                case "0":
                    return;
            }
        } while (!option.matches("[0]"));
    }

    public static void storeMenu() {
        String option = "";
        do {
            System.out.println("Choose an option: ");
            System.out.println("1. Store new author");
            System.out.println("2. Store new artwork");
            System.out.println("3. Store new painting");
            System.out.println("4. Store new sculpture");
            System.out.println("0. Exit");
            option = new Scanner(System.in).nextLine();

            switch (option) {
                case "1":
                    Author author;
                    if ((author = createAuthor()) != null) {
                        new Db4oHelper().storeAuthor(author);
                    }
                    break;
                case "2":
                    Artwork artwork;
                    if ((artwork = createArtwork()) != null) {
                        new Db4oHelper().storeArtwork(artwork);
                    }
                    break;
                case "3":
                    Painting painting;
                    if ((painting = createPainting()) != null) {
                        new Db4oHelper().storePainting(painting);
                    }
                    break;
                case "4":
                    Sculpture sculpture;
                    if ((sculpture = createSculpture()) != null) {
                        new Db4oHelper().storeSculpture(sculpture);
                    }
                    break;
                case "0":
                    break;
            }
        } while (!option.matches("[0]"));
    }

    public static void listArtworkByAuthorMenu() {
        System.out.print("Type author name: ");
        Author author = new Db4oHelper().retriveAuthorByName(new Scanner(System.in).nextLine());
        if (author != null) {
            List<Artwork> artworkList = new Db4oHelper().listArtworksByAuthor(author.getCode());
            if (!artworkList.isEmpty()) {
                for (Artwork artwork : artworkList) {
                    System.out.println(artwork.title);
                }
            } else
                System.out.println("Author has no artworks stored");
        } else
            System.out.println("Author does not exists");
    }

    public static void listBySpecificCategoryMenu() {
        String option = "";
        do {
            System.out.println("Choose an option: ");
            System.out.println("1. List by style");
            System.out.println("2. List by painting type");
            System.out.println("3. List by material type");
            System.out.println("0. Exit");
            option = new Scanner(System.in).nextLine();

            switch (option) {
                case "1":
                    listArtworkByStyle();
                    break;
                case "2":
                    listArtworkByPaintingType();
                    break;
                case "3":
                    listArtworkByMaterialType();
                    break;
                case "0":
                    break;
            }
        } while (!option.matches("[0]"));
    }

    public static Author createAuthor() {
        Scanner scanner = new Scanner(System.in);
        Author author = new Author();
        System.out.print("Introduce author code: ");
        author.setCode(scanner.nextLine());
        if (new Db4oHelper().autorExits(author.getCode())) {
            System.out.println("This author code already exists, aborting operation");
            return null;
        }
        System.out.print("Introduce author name: ");
        author.setName(scanner.nextLine());
        System.out.print("Introduce author nationality: ");
        author.setNationality(scanner.nextLine());
        return author;
    }

    public static Artwork createArtwork() {
        Scanner scanner = new Scanner(System.in);
        Artwork artwork = new Artwork();
        System.out.print("Introduce artwork code: ");
        try{
            artwork.setCode(scanner.nextInt());
            if (new Db4oHelper().artworkExits(artwork.getCode())) {
                System.out.println("This artwork code already exits, aborting operation");
                return null;
            }
        }
        catch (InputMismatchException e)
        {
            System.out.println("Code must be a number, aborting operation");
            return null;
        }

        scanner.nextLine();
        System.out.print("Introduce artwork title: ");
        artwork.setTitle(scanner.nextLine());
        if (artwork.getTitle().isEmpty()) {
            System.out.println("Artwork tittle cannot be empty, aborting operation");
        }
        System.out.print("Introduce artwork date in dd-MM-yyyy format: ");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            artwork.setDate(format.parse(scanner.nextLine()));
        } catch (ParseException e) {
            System.out.println("Date invalid or date format invalidad, aborting operation");
            return null;
        }
        try {
            System.out.print("Introduce a style (GRECOROMAN,NEOCLASSIC or CUBISM): ");
            artwork.setStyle(Styles.valueOf(scanner.nextLine().toUpperCase()));
        } catch (IllegalArgumentException e) {
            System.out.println("Style not valid, aborting operation");
            return null;
        }
        System.out.print("Introduce and author code: ");
        artwork.setAuthorCode(scanner.nextLine());
        if (!new Db4oHelper().autorExits(artwork.getAuthorCode())) {
            System.out.println("This author don't exists, aborting operation ");
            return null;
        }
        return artwork;
    }

    public static Painting createPainting() {
        Scanner scanner = new Scanner(System.in);
        Artwork artwork = createArtwork();
        try {
            Painting painting = new Painting(artwork.getCode(), artwork.getTitle(), artwork.getDate(), artwork.getStyle(),
                    artwork.getAuthorCode());
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
        catch (NullPointerException nullPointerException)
        {
            return null;
        }
        catch (IllegalArgumentException e) {
            System.out.println("Paitning type not valid, aborting operation");
            return null;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Width and height must be a number, aborting operation");
            return null;
        }
    }

    public static Sculpture createSculpture() {
        try
        {
            Scanner scanner = new Scanner(System.in);
            Artwork artwork = createArtwork();
            Sculpture sculpture = new Sculpture(artwork.getCode(), artwork.getTitle(), artwork.getDate(), artwork.getStyle(),
                    artwork.getAuthorCode());
            System.out.print("Enter the material type (IRON, BRONZE, MARBLE): ");
            sculpture.setMaterial(MaterialTypes.valueOf(scanner.nextLine().toUpperCase()));
            System.out.print("Introduce painting weight: ");
            sculpture.setWeight(scanner.nextFloat());
            return sculpture;
        }
        catch (NullPointerException nullPointerException)
        {
            return null;
        }
        catch (IllegalArgumentException e) {
            System.out.println("Material type not valid, aborting operation");
            return null;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Weight must be a number, aborting operation");
            return null;
        }
    }

    public static void listArtworkByStyle() {
        try {
            System.out.print("Introduce a style (GRECOROMAN,NEOCLASSIC or CUBISM): ");
            Styles style = Styles.valueOf(new Scanner(System.in).nextLine().toUpperCase());
            List<Artwork> artworkList = new Db4oHelper().listArtworksByStyle(style);
            if (!artworkList.isEmpty()) {
                for (Artwork artwork : artworkList) {
                    System.out.println(artwork.title);
                }
            } else
                System.out.println("There are no artworks stored with that style");
        }
        catch (IllegalArgumentException e) {
            System.out.println("Material type not valid, aborting operation");
        }
    }

    public static void listArtworkByPaintingType() {
        try {
            System.out.print("Enter the painting type (OILPAINTING, WATERCOLOUR, PASTEL): ");
            PaintingTypes paintingType = PaintingTypes.valueOf(new Scanner(System.in).nextLine().toUpperCase());
            List<Artwork> artworkList = new Db4oHelper().listArtworksByPaintingType(paintingType);
            if (!artworkList.isEmpty()) {
                for (Artwork artwork : artworkList) {
                    System.out.println(artwork.title);
                }
            } else
                System.out.println("There are no artworks stored with that painting type");
        }
        catch (IllegalArgumentException e) {
            System.out.println("Material type not valid, aborting operation");
        }
    }

    public static void listArtworkByMaterialType() {
        try
        {
            System.out.print("Enter the material type (IRON, BRONZE, MARBLE): ");
            MaterialTypes materialType = MaterialTypes.valueOf(new Scanner(System.in).nextLine().toUpperCase());

            List<Artwork> artworkList = new Db4oHelper().listArtworksByMaterialType(materialType);
            if (!artworkList.isEmpty()) {
                for (Artwork artwork : artworkList) {
                    System.out.println(artwork.title);
                }
            } else
                System.out.println("There are no artworks stored with that painting type");
        }
        catch (IllegalArgumentException e) {
            System.out.println("Material type not valid, aborting operation");
        }
    }
}
