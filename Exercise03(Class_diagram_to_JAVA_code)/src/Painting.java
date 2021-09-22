public class Painting extends Artwork {
    PaintingType type;
    String format;

    public Painting(String title, Author author, PaintingType type, String format) {
        super(title, author);
        this.type = type;
        this.format = format;
    }

    public PaintingType getType() {
        return type;
    }

    public String getFormat() {
        return format;
    }

    public void setType(PaintingType type) {
        this.type = type;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
