package com.dayannn.RSOI2.booksservice.controller;

import com.dayannn.RSOI2.booksservice.entity.Book;
import com.dayannn.RSOI2.booksservice.exception.BookNotFoundException;
import com.dayannn.RSOI2.booksservice.service.BooksService;
import com.dayannn.RSOI2.booksservice.service.BooksServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class BooksServiceController {
    private final BooksService booksService;
    Logger logger;

    @Autowired
    public BooksServiceController(BooksService booksService){
        this.booksService = booksService;
        logger  = LoggerFactory.getLogger(BooksServiceImplementation.class);
    }

    @PostMapping(value = "/books")
    public void createBook(@RequestBody Book book){
        booksService.createBook(book);
        logger.info("[POST] /books ", book);
    }

    @GetMapping(value = "/books")
    public List<Book> getAllBooks(){
        logger.info("[GET] /books ");
        return booksService.getAllBooks();
    }

    @PostMapping(value = "/books/{id}/add_review")
    public void addReview(@PathVariable Long id) throws BookNotFoundException {
        booksService.addReview(id);
        logger.info("[POST] /books/" + id + "/add_review");
    }

    @PostMapping(value = "/books/{id}/delete_review")
    public void deleteReview(@PathVariable Long id) throws BookNotFoundException {
        booksService.deleteReview(id);
        logger.info("[POST] /books/" + id + "/delete_review");
    }

    @PostMapping(value = "books/{id}/setRating/{rating}")
    public void setRating(@PathVariable Long id, @PathVariable double rating) throws BookNotFoundException{
        booksService.setRating(id, rating);
        logger.info("[POST] /books/" + id + "/serRating/" + rating);
    }
}
