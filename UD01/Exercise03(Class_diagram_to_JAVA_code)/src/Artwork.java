public class Artwork {
    protected String title;
    protected Author author;
    protected Hall hall;



    public Artwork(String title, Author author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Hall getHall() {
        return hall;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}
