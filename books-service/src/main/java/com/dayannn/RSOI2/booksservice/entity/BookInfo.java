package com.dayannn.RSOI2.booksservice.entity;

import javax.persistence.Column;

public class BookInfo {
    private String name;
    private int pages_num;

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
}
