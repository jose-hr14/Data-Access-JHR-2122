package com.jhr2122.unit5.finalactivity.finalactivityspring.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books", schema = "public", catalog = "Library")
public class BooksEntity {
    private String isbn;
    private String title;
    private Integer copies;
    private String cover;
    private String outline;
    private String publisher;
    @JsonIgnore
    private List<LendingEntity> borrowedBy;
    @JsonIgnore
    private List<ReservationsEntity> reservedBy;

    @Id
    @Column(name = "isbn", nullable = false, length = 13)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 90)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "copies", nullable = true)
    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    @Basic
    @Column(name = "cover", nullable = true, length = 255)
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Basic
    @Column(name = "outline", nullable = false, length = 255)
    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    @Basic
    @Column(name = "publisher", nullable = true, length = 60)
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BooksEntity that = (BooksEntity) o;

        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (copies != null ? !copies.equals(that.copies) : that.copies != null) return false;
        if (cover != null ? !cover.equals(that.cover) : that.cover != null) return false;
        if (outline != null ? !outline.equals(that.outline) : that.outline != null) return false;
        if (publisher != null ? !publisher.equals(that.publisher) : that.publisher != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = isbn != null ? isbn.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (copies != null ? copies.hashCode() : 0);
        result = 31 * result + (cover != null ? cover.hashCode() : 0);
        result = 31 * result + (outline != null ? outline.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "book")
    public List<LendingEntity> getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(List<LendingEntity> borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    @OneToMany(mappedBy = "book")
    public List<ReservationsEntity> getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(List<ReservationsEntity> reservedBy) {
        this.reservedBy = reservedBy;
    }
}
