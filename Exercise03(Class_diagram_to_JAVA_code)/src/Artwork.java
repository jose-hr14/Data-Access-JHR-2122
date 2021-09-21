public class Artwork {
    String title;
    Author author;
    Hall hall;

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

    //funcion que recorra todas las obras y de sus datos: nombre, autor, sala, y museo, y saque unos datos u otros segun si es pintura o escultura, con instanceof
}
