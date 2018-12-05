package com.dayannn.RSOI2.booksservice.service;

import com.dayannn.RSOI2.booksservice.entity.Book;
import com.dayannn.RSOI2.booksservice.exception.BookNotFoundException;

import java.util.List;

public interface BooksService {
    List<Book> getAllBooks();
    void createBook(Book book);
    void setReviewsNum(Long id, int reviewsNum) throws BookNotFoundException;
    int getReviewsNum(Long id) throws BookNotFoundException;
    void addReview(Long id) throws BookNotFoundException;
    void deleteReview(Long id) throws BookNotFoundException;
}