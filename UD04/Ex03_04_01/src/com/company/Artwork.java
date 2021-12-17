package com.company;

import java.util.Date;

public class Artwork {
    protected int code;
    protected String title;
    protected Date date;
    protected Styles style;
    protected Author author;

    public Artwork() {
    }

    public Artwork(int code, String title, Date date, Styles style, Author author) {
        this.code = code;
        this.title = title;
        this.date = date;
        this.style = style;
        this.author = author;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Styles getStyle() {
        return style;
    }

    public void setStyle(Styles style) {
        this.style = style;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
