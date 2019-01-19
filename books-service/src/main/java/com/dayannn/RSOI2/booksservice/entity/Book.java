package com.dayannn.RSOI2.booksservice.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;


@Entity
@Table(name = "BOOKS")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // TODO: add ISBN to entity

    @Column(name = "NAME")
    private String name;

    @Column(name = "AUTHOR")
    private String author;

    @Lob
    @Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "PAGES_NUM")
    private int pagesNum;

    @Column(name = "REVIEWS_NUM", columnDefinition = "int default 0")
    private int reviewsNum;

    @Column(name = "RATING", columnDefinition = "double precision default 0.0")
    private double rating;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPagesNum() {
        return pagesNum;
    }

    public void setPagesNum(int pagesNum) {
        this.pagesNum = pagesNum;
    }

    public int getReviewsNum() {
        return reviewsNum;
    }

    public void setReviewsNum(int reviewsNum) {
        this.reviewsNum = reviewsNum;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return new EqualsBuilder()
                .append(pagesNum, book.pagesNum)
                .append(reviewsNum, book.reviewsNum)
                .append(rating, book.rating)
                .append(id, book.id)
                .append(name, book.name)
                .append(author, book.author)
                .append(description, book.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(author)
                .append(description)
                .append(pagesNum)
                .append(reviewsNum)
                .append(rating)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", pagesNum=" + pagesNum +
                ", reviewsNum=" + reviewsNum +
                ", rating=" + rating +
                '}';
    }
}
