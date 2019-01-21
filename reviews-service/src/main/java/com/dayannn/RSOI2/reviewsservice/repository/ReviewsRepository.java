package com.dayannn.RSOI2.reviewsservice.repository;

import com.dayannn.RSOI2.reviewsservice.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Review, Long> {
    List<Review> findByUidOrderByPostedTimeDesc(Long uid);
    List<Review> findByBookIdOrderByPostedTimeDesc(Long bookId);
    Page<Review> findByBookIdOrderByPostedTimeDesc(Long bookId, Pageable p);
    void deleteAllByBookId(Long bookId);
}
