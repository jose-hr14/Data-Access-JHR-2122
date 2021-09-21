public class Painting extends Artwork {
    Style style;
    PaintingType format;
    Author author;

    public Style getStyle() {
        return style;
    }

    public PaintingType getFormat() {
        return format;
    }

    public Author getAuthor() {
        return author;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public void setFormat(PaintingType format) {
        this.format = format;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
