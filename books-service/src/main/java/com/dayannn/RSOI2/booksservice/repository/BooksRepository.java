package com.dayannn.RSOI2.booksservice.repository;

import com.dayannn.RSOI2.booksservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Long> {
}
