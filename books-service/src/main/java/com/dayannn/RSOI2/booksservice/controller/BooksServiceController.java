package com.dayannn.RSOI2.booksservice.controller;

import com.dayannn.RSOI2.booksservice.entity.Book;
import com.dayannn.RSOI2.booksservice.entity.BookInfo;
import com.dayannn.RSOI2.booksservice.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BooksServiceController {

    @Autowired
    private BooksRepository booksRepository;

    @PostMapping(value = "/books")
    public ResponseEntity<Void> createBook(@RequestBody Book book){
        booksRepository.save(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/books")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = booksRepository.findAll();

        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

}
