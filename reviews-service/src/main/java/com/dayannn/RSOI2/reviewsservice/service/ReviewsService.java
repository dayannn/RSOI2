package com.dayannn.RSOI2.reviewsservice.service;

import com.dayannn.RSOI2.reviewsservice.entity.Review;

import java.util.List;

public interface ReviewsService {
    List<Review> getAllReviews();
    Long createReview(Review review);
    List<Review> getReviewsByUser(Long userId);
    List<Review> getReviewsByBook(Long bookId);
}
