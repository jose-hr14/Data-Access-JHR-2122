import java.util.List;

public class Main {
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
            for (int n = 0; n < artworkList.size(); n++)
            {
                title = artworkList.get(i).getTitle();
                author = artworkList.get(i).getAuthor().getName();
                hallName = artworkList.get(i).getHall().getName();
                museumName = museum.getName();

                if(artworkList.get(i) instanceof Painting)
                {
                    paintingType = ((Painting) artworkList.get(i)).getType();
                    paintingFormat = ((Painting) artworkList.get(i)).getFormat();
                }
                if(artworkList.get(i) instanceof Sculpture)
                {
                    sculptureMaterial = ((Sculpture) artworkList.get(i)).getMaterial();
                    sculptureStyle = ((Sculpture) artworkList.get(i)).getStyle();
                }
            }
        }
    }
}
