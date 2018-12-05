package com.dayannn.RSOI2.booksservice.service;

import com.dayannn.RSOI2.booksservice.entity.Book;
import com.dayannn.RSOI2.booksservice.exception.BookNotFoundException;
import com.dayannn.RSOI2.booksservice.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksServiceImplementation implements BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksServiceImplementation(BooksRepository booksRepository){
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
}
