package com.company;

import java.util.Date;

public class Painting extends Artwork{
    private PaintingTypes paintingType;
    private DimensionsType dimensionsType;

    public Painting() {
    }

    public Painting(int code, String title, Date date, Styles style, int authorCode, PaintingTypes paintingType, DimensionsType dimensionsType) {
        super(code, title, date, style, authorCode);
        this.paintingType = paintingType;
        this.dimensionsType = dimensionsType;
    }

    public PaintingTypes getPaintingType() {
        return paintingType;
    }

    public void setPaintingType(PaintingTypes paintingType) {
        this.paintingType = paintingType;
    }

    public DimensionsType getDimensionsType() {
        return dimensionsType;
    }

    public void setDimensionsType(DimensionsType dimensionsType) {
        this.dimensionsType = dimensionsType;
    }
}
