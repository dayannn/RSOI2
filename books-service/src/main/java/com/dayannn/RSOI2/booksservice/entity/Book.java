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

    @Column(name = "PAGES_NUM")
    private int pagesNum;

    @Column(name = "REVIEWS_NUM", columnDefinition = "int default 0")
    private int reviewsNum;

    @Column(name = "RATING", columnDefinition = "int default 0")
    private int rating;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
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
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
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
                ", pagesNum=" + pagesNum +
                ", reviewsNum=" + reviewsNum +
                ", rating=" + rating +
                '}';
    }
}
