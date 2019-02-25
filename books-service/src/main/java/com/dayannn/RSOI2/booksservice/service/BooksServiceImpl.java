package com.dayannn.RSOI2.booksservice.service;

import com.dayannn.RSOI2.booksservice.entity.Book;
import com.dayannn.RSOI2.booksservice.exception.BookNotFoundException;
import com.dayannn.RSOI2.booksservice.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Service
public class BooksServiceImpl implements BooksService {
    private final BooksRepository booksRepository;
    @Autowired
    public BooksServiceImpl(BooksRepository booksRepository){
        this.booksRepository = booksRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return booksRepository.findAll();
    }

    @Override
    public void createBook(Book book) {
        booksRepository.save(book);
    }

    @Override
    public void setReviewsNum(Long id, int reviewsNum) throws BookNotFoundException {
        Book book = booksRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        book.setReviewsNum(reviewsNum);
        booksRepository.save(book);
    }

    @Override
    public int getReviewsNum(Long id) throws BookNotFoundException{
        Book book = booksRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return book.getReviewsNum();
    }

    @Override
    public void addReview(Long id) throws BookNotFoundException {
        Book book = booksRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        book.setReviewsNum(book.getReviewsNum() + 1);
        booksRepository.save(book);
    }

    @Override
    public void deleteReview(Long id) throws BookNotFoundException {
        Book book = booksRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        book.setReviewsNum(book.getReviewsNum() - 1);
        booksRepository.save(book);
    }

    @Override
    public void setRating(Long id, double rating) throws BookNotFoundException {
        Book book = booksRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        book.setRating(rating);
        booksRepository.save(book);
    }

    @Override
    public Book getBookById(@PathVariable Long id) throws BookNotFoundException{
        return booksRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public ResponseEntity healthCheck() {
        return ResponseEntity.ok("The booksService is up");
    }


}
