package com.dayannn.RSOI2.booksservice.controller;

import com.dayannn.RSOI2.booksservice.entity.Book;
import com.dayannn.RSOI2.booksservice.exception.BookNotFoundException;
import com.dayannn.RSOI2.booksservice.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BooksServiceController {
    private final BooksService booksService;

    @Autowired
    public BooksServiceController(BooksService booksService){
        this.booksService = booksService;
    }

    @PostMapping(value = "/books")
    public void createBook(@RequestBody Book book){
        booksService.createBook(book);
    }

    @GetMapping(value = "/books")
    public List<Book> getAllBooks(){
        return booksService.getAllBooks();
    }

    @PostMapping(value = "/books/{id}/add_review")
    public void addReview(@PathVariable Long id) throws BookNotFoundException {
        booksService.addReview(id);
    }

    @PostMapping(value = "/books/{id}/delete_review")
    public void deleteReview(@PathVariable Long id) throws BookNotFoundException {
        booksService.deleteReview(id);
    }

    @PostMapping(value = "books/{id}/setRating/{rating}")
    public void setRating(@PathVariable Long id, @PathVariable double rating) throws BookNotFoundException{
        booksService.setRating(id, rating);
    }
}
