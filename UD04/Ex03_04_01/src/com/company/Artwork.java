package com.company;

import java.util.Date;

public class Artwork {
    protected int code;
    protected String title;
    protected Date date;
    protected Styles style;
    protected String authorCode;

    public Artwork() {
    }

    public Artwork(int code, String title, Date date, Styles style, String authorCode) {
        this.code = code;
        this.title = title;
        this.date = date;
        this.style = style;
        this.authorCode = authorCode;
    }

    public Artwork(String title, Date date, Styles style, String authorCode) {
        this.title = title;
        this.date = date;
        this.style = style;
        this.authorCode = authorCode;
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

    public String getAuthorCode() {
        return authorCode;
    }

    public void setAuthorCode(String authorCode) {
        this.authorCode = authorCode;
    }
}
