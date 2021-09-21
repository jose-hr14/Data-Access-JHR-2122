public class Sculpture extends Artwork {
    Materials material;
    Style style;

    public Materials getMaterial() {
        return material;
    }

    public Style getStyle() {
        return style;
    }

    public void setMaterial(Materials material) {
        this.material = material;
    }

    public void setStyle(Style style) {
        this.style = style;
    }
}
