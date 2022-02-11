package com.jhr2122.unit5.finalactivity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "reservations", schema = "public", catalog = "library")
public class ReservationsEntity {
    private int id;
    private Date reservation;
    private BooksEntity book;
    private UsersEntity borrower;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "reservation", nullable = false)
    public Date getReservation() {
        return reservation;
    }

    public void setReservation(Date reservation) {
        this.reservation = reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationsEntity that = (ReservationsEntity) o;
        return id == that.id && Objects.equals(reservation, that.reservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reservation);
    }

    @ManyToOne
    @JoinColumn(name = "book", referencedColumnName = "isbn", nullable = false)
    public BooksEntity getBook() {
        return book;
    }

    public void setBook(BooksEntity book) {
        this.book = book;
    }

    @ManyToOne
    @JoinColumn(name = "borrower", referencedColumnName = "code", nullable = false)
    public UsersEntity getBorrower() {
        return borrower;
    }

    public void setBorrower(UsersEntity borrower) {
        this.borrower = borrower;
    }
}
