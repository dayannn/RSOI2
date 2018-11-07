package com.dayannn.RSOI2.booksservice.entity;

import javax.persistence.*;


@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "pages_num")
    private int pages_num;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPages_num() {
        return pages_num;
    }

    public void setPages_num(int pages_num) {
        this.pages_num = pages_num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(pages_num, book.pages_num)
                .append(id, book.id)
                .append(name, book.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(pages_num)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pages_num=" + pages_num +
                '}';
    }
}
