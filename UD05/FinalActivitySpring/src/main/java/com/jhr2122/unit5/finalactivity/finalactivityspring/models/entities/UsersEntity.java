package com.jhr2122.unit5.finalactivity.finalactivityspring.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "users", schema = "public", catalog = "Library")
public class UsersEntity {
    private String code;
    private String name;
    private String surname;
    private Date birthdate;
    private Date fined;
    private List<LendingEntity> lentBooks;
    private List<ReservationsEntity> reservedBooks;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "code", nullable = false, length = 8)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 25)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname", nullable = false, length = 25)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "birthdate", nullable = true)
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Basic
    @Column(name = "fined", nullable = true)
    public Date getFined() {
        return fined;
    }

    public void setFined(Date fined) {
        this.fined = fined;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (birthdate != null ? !birthdate.equals(that.birthdate) : that.birthdate != null) return false;
        if (fined != null ? !fined.equals(that.fined) : that.fined != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (birthdate != null ? birthdate.hashCode() : 0);
        result = 31 * result + (fined != null ? fined.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "borrower")
    @JsonIgnoreProperties("borrower")
    public List<LendingEntity> getLentBooks() {
        return lentBooks;
    }

    public void setLentBooks(List<LendingEntity> lentBooks) {
        this.lentBooks = lentBooks;
    }

    @OneToMany(mappedBy = "borrower")
    @JsonIgnoreProperties("borrower")
    public List<ReservationsEntity> getReservedBooks() {
        return reservedBooks;
    }

    public void setReservedBooks(List<ReservationsEntity> reservedBooks) {
        this.reservedBooks = reservedBooks;
    }
}
