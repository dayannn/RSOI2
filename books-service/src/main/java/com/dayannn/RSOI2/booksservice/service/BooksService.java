package com.dayannn.RSOI2.booksservice.service;

import com.dayannn.RSOI2.booksservice.entity.Book;
import com.dayannn.RSOI2.booksservice.exception.BookNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BooksService {
    List<Book> getAllBooks();
    void createBook(Book book);
    void setReviewsNum(Long id, int reviewsNum) throws BookNotFoundException;
    Book getBookById(Long id) throws BookNotFoundException;
    int getReviewsNum(Long id) throws BookNotFoundException;
    void addReview(Long id) throws BookNotFoundException;
    void deleteReview(Long id) throws BookNotFoundException;
    void setRating(Long id, double rating) throws BookNotFoundException;
    ResponseEntity healthCheck();
}