import java.util.ArrayList;
import java.util.List;

public class Hall {
    String name;
    Museum museum;
    List<Artwork> artworkList;

    public Hall(String name, Museum museum) {
        this.name = name;
        this.museum = museum;
        this.artworkList = new ArrayList<Artwork>();
    }

    public String getName() {
        return name;
    }

    public Museum getMuseum() {
        return museum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    public List<Artwork> getArtworkList() {
        return artworkList;
    }

}
