package com.dayannn.RSOI2.reviewsservice.service;

import com.dayannn.RSOI2.reviewsservice.entity.Review;
import com.dayannn.RSOI2.reviewsservice.repository.ReviewsRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewsServiceImplementation implements ReviewsService {
    private final ReviewsRepository reviewsRepository;

    @Autowired
    public ReviewsServiceImplementation(ReviewsRepository reviewsRepository){
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewsRepository.findAll();
    }

    @Override
    public Long createReview(Review review) {
        reviewsRepository.save(review);
        return review.getId();
    }

    @Override
    public List<Review> getReviewsByUser(Long userId) {
        List<Review> reviews = reviewsRepository.findByUid(userId);
        return reviews;
    }

    @Override
    public List<Review> getReviewsByBook(Long bookId) {
        List<Review> reviews = reviewsRepository.findByBookId(bookId);
        return reviews;
    }

}
