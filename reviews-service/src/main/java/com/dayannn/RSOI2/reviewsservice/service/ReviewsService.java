package com.dayannn.RSOI2.reviewsservice.service;

import com.dayannn.RSOI2.reviewsservice.entity.Review;
import com.dayannn.RSOI2.reviewsservice.exeption.ReviewNotFoundException;

import java.util.List;

public interface ReviewsService {
    List<Review> getAllReviews();
    Long createReview(Review review);
    List<Review> getReviewsByUser(Long userId);
    List<Review> getReviewsByBook(Long bookId);
    void deleteReviewsByBook(Long bookId);
    void deleteById(Long id);
    Review getReviewById(Long id) throws ReviewNotFoundException;
}
