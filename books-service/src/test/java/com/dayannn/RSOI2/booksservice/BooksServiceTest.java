package com.dayannn.RSOI2.booksservice;

import com.dayannn.RSOI2.booksservice.entity.Book;
import com.dayannn.RSOI2.booksservice.exception.BookNotFoundException;
import com.dayannn.RSOI2.booksservice.repository.BooksRepository;
import com.dayannn.RSOI2.booksservice.service.BooksService;
import com.dayannn.RSOI2.booksservice.service.BooksServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class BooksServiceTest {
    private BooksService booksService;

    @Mock
    BooksRepository booksRepository;

    @Before
    public void setUp(){
        initMocks(this);
        booksService = new BooksServiceImpl(booksRepository);
    }

    @Test
    public void shouldCreateNewBook(){
        Book book = new Book();
        book.setPagesNum(42);
        book.setName("book");

        booksRepository.save(book);
    }

    @Test
    public void shouldReturnBooksList(){
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setName("book");
        book.setPagesNum(13);
        books.add(book);

        given(booksRepository.findAll()).willReturn(books);
        List<Book> booksReturned = booksService.getAllBooks();
        assertThat(booksReturned, is(books));
    }

    @Test
    public void shouldSetReviewsNum(){
        Book book = new Book();
        book.setName("book");
        book.setPagesNum(13);
        book.setReviewsNum(5);

        try {
            given(booksRepository.save(book)).willReturn(book);
            Book booksaved = booksRepository.save(book);
            given(booksRepository.findById(booksaved.getId())).willReturn(Optional.of(booksaved));

            int revs_num = booksService.getReviewsNum(booksaved.getId());
            booksService.setReviewsNum(booksaved.getId(), 10);
            assertEquals(10, booksService.getReviewsNum(booksaved.getId()));
        }
        catch (BookNotFoundException ex){
            fail();
        }
    }

    @Test
    public void shouldAddReview(){
        Book book = new Book();
        book.setName("book");
        book.setPagesNum(13);
        book.setReviewsNum(5);

        try {
            given(booksRepository.save(book)).willReturn(book);
            given(booksRepository.findById(book.getId())).willReturn(Optional.of(book));
            Book booksaved = booksRepository.save(book);

            booksService.addReview(booksaved.getId());
            assertEquals(6, booksService.getReviewsNum(booksaved.getId()));
        }
        catch (BookNotFoundException ex){
            fail();
        }
    }

}
