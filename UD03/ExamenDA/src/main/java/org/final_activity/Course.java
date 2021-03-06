package org.final_activity;

/**
 * Class used to facilitate working with the information
 * read from the date base and to facilitate it's writing to the
 * database as well.
 * @author José Hernández Riquelme
 */
public class Course {
    int code;
    String name;

    public Course() {
    }

    public Course(int code, String name) {
        this.code = code;
        this.name = name;
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

    /**
     * This functions returns the string that will be printed
     * in the course comboBox
     * @return Course name
     */
    @Override
    public String toString() {
        return name;
    }
}
