package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Sculpture extends Artwork{
    private MaterialTypes material;
    private double weight;

    public Sculpture() {

    }

    public Sculpture(int code, String title, Date date, Styles style, String authorCode, MaterialTypes material, double weight) {
        super(code, title, date, style, authorCode);
        this.material = material;
        this.weight = weight;
    }

    public MaterialTypes getMaterial() {
        return material;
    }

    public void setMaterial(MaterialTypes material) {
        this.material = material;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
