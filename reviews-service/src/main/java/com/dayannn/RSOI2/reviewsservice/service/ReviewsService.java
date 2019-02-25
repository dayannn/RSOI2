package com.dayannn.RSOI2.reviewsservice.service;

import com.dayannn.RSOI2.reviewsservice.entity.Review;
import com.dayannn.RSOI2.reviewsservice.exeption.ReviewNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewsService {
    List<Review> getAllReviews();
    Long createReview(Review review);
    List<Review> getReviewsByUser(Long userId);
    Page<Review> getReviewsByBook(Long bookId, PageRequest p);
    List<Review> getReviewsByBook(Long bookId);
    void deleteReviewsByBook(Long bookId);
    void deleteById(Long id);
    Review getReviewById(Long id) throws ReviewNotFoundException;
    ResponseEntity healthCheck();
}
