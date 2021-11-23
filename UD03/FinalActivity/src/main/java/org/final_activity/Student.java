package org.final_activity;

/**
 * Class used to facilitate working with the information
 * read from the date base and to facilitate it's writing to the
 * database as well.
 * @author José Hernández Riquelme
 */
public class Student {
    String firstName;
    String lastName;
    String idCard;
    String email;
    String phone;

    public Student() {
        
    }

    public Student(String firstName, String lastName, String idCard, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idCard = idCard;
        this.email = email;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This functions returns the string that will be printed
     * in the student comboBox and the enrolled student comboBox
     * @return Student's first name, last name and idCard
     */
    @Override
    public String toString() {
        return firstName + " " + lastName + " - " + idCard;
    }
}
