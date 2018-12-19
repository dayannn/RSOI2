package com.dayannn.RSOI2.reviewsservice.repository;

import com.dayannn.RSOI2.reviewsservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Review, Long> {
    List<Review> findByUid(Long uid);
    List<Review> findByBookId(Long bookId);
    void deleteAllByBookId(Long bookId);
}
