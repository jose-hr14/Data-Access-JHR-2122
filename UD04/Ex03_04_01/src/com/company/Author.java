package com.company;

public class Author {
    private String code;
    private String name;
    private String nationality;

    public Author() {
    }

    public Author(String code, String name, String nationality) {
        this.code = code;
        this.name = name;
        this.nationality = nationality;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
