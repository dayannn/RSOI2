package com.dayannn.RSOI2.booksservice;

import com.dayannn.RSOI2.booksservice.entity.Book;
import com.dayannn.RSOI2.booksservice.repository.BooksRepository;
import com.dayannn.RSOI2.booksservice.service.BooksService;
import com.dayannn.RSOI2.booksservice.service.BooksServiceImplementation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

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
        booksService = new BooksServiceImplementation(booksRepository);
    }

    @Test
    public void shouldCreateNewBook(){
        Book book = new Book();
        book.setPages_num(42);
        book.setName("book");
    }

    @Test
    public void shouldReturnBooksList(){
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setName("book");
        book.setPages_num(13);
        books.add(book);

        given(booksRepository.findAll()).willReturn(books);
        List<Book> booksReturned = booksService.getAllBooks();
        assertThat(booksReturned, is(books));
    }

}
