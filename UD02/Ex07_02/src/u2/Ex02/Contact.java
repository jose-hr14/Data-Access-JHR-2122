package u2.Ex02;

import java.io.Serializable;

public class Contact implements Serializable {
    String name;
    String surname;
    String eMail;
    String phoneNumber;
    String description;

    public Contact() {

    }

    public Contact(String name, String surname, String eMail, String phoneNumber, String description) {
        this.name = name;
        this.surname = surname;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }
    public Contact(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
