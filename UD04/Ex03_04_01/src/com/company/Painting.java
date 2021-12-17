package com.company;

import java.util.Date;

public class Painting extends Artwork{
    private PaintingTypes paintingType;
    private DimensionsType dimensionsType;

    public Painting() {
    }

    public Painting(int code, String title, Date date, Styles style, Author author, PaintingTypes paintingType, DimensionsType dimensionsType) {
        super(code, title, date, style, author);
        this.paintingType = paintingType;
        this.dimensionsType = dimensionsType;
    }
}
