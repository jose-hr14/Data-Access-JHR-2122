import java.util.List;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args)
    {

        //usando bucle for mejorado con for(element:list) y list.foreach. Despues, list.iterator
        Museum museum = new Museum("Alicante best museum", "Santa Barbara Castle", "Alicante", "Spain");
        Hall hall = new Hall("Navarrete hall");
        Author author = new Author("Jose Miguel Navarrete", "Spanish");
        Artwork painting = new Painting("Navarrete Soccer", author, PaintingType.OIL, "Pocket format");
        Artwork sculpture = new Sculpture("Navarrete player", author, Materials.MARBLE, Style.NEOCLASSICISM);

        hall.addArtwork(painting);
        hall.addArtwork(sculpture);
        museum.addHall(hall);

        showArtworks(museum);
    }
    public static void showArtworks(Museum museum)
    {
        String title;
        String author;
        String hallName;
        String museumName;

        PaintingType paintingType;
        String paintingFormat;

        Materials sculptureMaterial;
        Style sculptureStyle;

        List<Hall> hallList = museum.getHallList();
        List<Artwork> artworkList;

        for (int i = 0; i < hallList.size(); i++)
        {
            artworkList = hallList.get(i).getArtworkList();
            for (int j = 0; j < artworkList.size(); j++)
            {
                title = artworkList.get(j).getTitle();
                author = artworkList.get(j).getAuthor().getName();
                hallName = artworkList.get(j).getHall().getName();
                museumName = museum.getName();

                System.out.println("Tittle: " + title);
                System.out.println("Author: " + author);
                System.out.println("Hall name: " + hallName);
                System.out.println("Museum name: " + museumName);

                if(artworkList.get(j) instanceof Painting)
                {
                    paintingType = ((Painting) artworkList.get(j)).getType();
                    paintingFormat = ((Painting) artworkList.get(j)).getFormat();

                    System.out.println("Painting type: " + paintingType);
                    System.out.println("Painting format: " + paintingFormat);
                }
                else if(artworkList.get(j) instanceof Sculpture)
                {
                    sculptureMaterial = ((Sculpture) artworkList.get(j)).getMaterial();
                    sculptureStyle = ((Sculpture) artworkList.get(j)).getStyle();

                    System.out.println("Sculpture material: " + sculptureMaterial);
                    System.out.println("Sculpture style: " + sculptureStyle);
                }
                System.out.println();
            }


        }
    }

    public static void showArtworks02(Museum museum)
    {
        String title;
        String author;
        String hallName;
        String museumName;

        PaintingType paintingType;
        String paintingFormat;

        Materials sculptureMaterial;
        Style sculptureStyle;

        List<Hall> hallList = museum.getHallList();
        List<Artwork> artworkList;

        for (Hall hall : hallList) {
            artworkList = hall.getArtworkList();
            for (Artwork artwork : artworkList) {
                title = artwork.getTitle();
                author = artwork.getAuthor().getName();
                hallName = artwork.getHall().getName();
                museumName = museum.getName();

                System.out.println("Tittle: " + title);
                System.out.println("Author: " + author);
                System.out.println("Hall name: " + hallName);
                System.out.println("Museum name: " + museumName);

                if (artwork instanceof Painting) {
                    paintingType = ((Painting) artwork).getType();
                    paintingFormat = ((Painting) artwork).getFormat();

                    System.out.println("Painting type: " + paintingType);
                    System.out.println("Painting format: " + paintingFormat);
                } else if (artwork instanceof Sculpture) {
                    sculptureMaterial = ((Sculpture) artwork).getMaterial();
                    sculptureStyle = ((Sculpture) artwork).getStyle();

                    System.out.println("Sculpture material: " + sculptureMaterial);
                    System.out.println("Sculpture style: " + sculptureStyle);
                }
                System.out.println();
            }
        }
    }

    public static void showArtworks03(Museum museum)
    {
        museum.getHallList().forEach((g) -> {
            List<Artwork> artworkList = g.getArtworkList();

            artworkList.forEach((a) -> {});
        });

    }


}
