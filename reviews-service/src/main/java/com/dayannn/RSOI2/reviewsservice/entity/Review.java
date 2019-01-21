package com.dayannn.RSOI2.reviewsservice.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "REVIEWS")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "TEXT", columnDefinition = "LONGTEXT")
    private String text;

    @Column(name = "UID")
    private Long uid;

    @Column(name="BOOK_ID")
    private Long bookId;

    @Column(name="RATING", columnDefinition = "double precision default 5.0")
    private double rating;

    @Column(name="POSTED_TIME")
    private Long postedTime;

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Long getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(Long postedTime) {
        this.postedTime = postedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        return new EqualsBuilder()
                .append(rating, review.rating)
                .append(id, review.id)
                .append(text, review.text)
                .append(uid, review.uid)
                .append(bookId, review.bookId)
                .append(postedTime, review.postedTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(text)
                .append(uid)
                .append(bookId)
                .append(rating)
                .append(postedTime)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", uid=" + uid +
                ", bookId=" + bookId +
                ", rating=" + rating +
                ", postedTime=" + postedTime +
                '}';
    }
}
