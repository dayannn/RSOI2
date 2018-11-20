package com.dayannn.RSOI2.booksservice.service;

import com.dayannn.RSOI2.booksservice.entity.Book;
import java.util.List;

public interface BooksService {
    List<Book> getAllBooks();
    void createBook(Book book);
}