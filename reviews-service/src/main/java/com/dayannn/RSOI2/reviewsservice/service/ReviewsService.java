package com.dayannn.RSOI2.reviewsservice.service;

import com.dayannn.RSOI2.reviewsservice.entity.Review;

import java.util.List;

public interface ReviewsService {
    List<Review> getAllReviews();
    void createReview(Review review);

}