package com.jhr2122.unit5.finalactivity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "public", catalog = "library")
public class UsersEntity {
    private String code;
    private String name;
    private String surname;
    private Date birthdate;
    private Date fined;
    private List<LendingEntity> lentBooks;
    private List<ReservationsEntity> reservedBooks;

    public UsersEntity() {
    }

    public UsersEntity(String code, String name, String surname, Date birthdate) {
        this.code = code;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
    }

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
        return Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(birthdate, that.birthdate) && Objects.equals(fined, that.fined);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, surname, birthdate, fined);
    }

    @OneToMany(mappedBy = "borrower")
    public List<LendingEntity> getLentBooks() {
        return lentBooks;
    }

    public void setLentBooks(List<LendingEntity> lentBooks) {
        this.lentBooks = lentBooks;
    }

    @OneToMany(mappedBy = "borrower")
    public List<ReservationsEntity> getReservedBooks() {
        return reservedBooks;
    }

    public void setReservedBooks(List<ReservationsEntity> reservedBooks) {
        this.reservedBooks = reservedBooks;
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname();
    }
}
