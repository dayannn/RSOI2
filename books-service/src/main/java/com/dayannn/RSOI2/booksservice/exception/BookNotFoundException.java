package com.dayannn.RSOI2.booksservice.exception;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(Long id){
        super("User with id= " + id + " was not found!");
    }
}
