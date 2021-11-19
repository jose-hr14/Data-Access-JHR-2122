package org.final_activity;

/**
 * Subject class. Class used to facilitate working with the information
 * read from the date base and to facilitate it's writing to the
 * database as well.
 * @author José Hernández Riquelme
 */
public class Subject {
    int code;
    String name;
    int courseID;
    int hours;
    int year;

    public Subject() {
    }

    public Subject(int code, String name, int courseID, int hours, int year) {
        this.code = code;
        this.name = name;
        this.courseID = courseID;
        this.hours = hours;
        this.year = year;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


}
