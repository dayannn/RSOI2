package com.dayannn.RSOI2.booksservice.controller;

import com.dayannn.RSOI2.booksservice.entity.Book;
import com.dayannn.RSOI2.booksservice.entity.BookInfo;
import com.dayannn.RSOI2.booksservice.repository.BooksRepository;
import com.dayannn.RSOI2.booksservice.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BooksServiceController {
    private final BooksService booksService;

    @Autowired
    BooksServiceController(BooksService booksService){
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

}