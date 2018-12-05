package com.dayannn.RSOI2.reviewsservice.controller;

import com.dayannn.RSOI2.reviewsservice.entity.Review;
import com.dayannn.RSOI2.reviewsservice.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewsServiceController {
    private ReviewsService reviewsService;

    @Autowired
    ReviewsServiceController(ReviewsService reviewsService){
        this.reviewsService = reviewsService;
    }

    @PostMapping(value = "/reviews")
    public Long createReview(@RequestBody Review review){
        return reviewsService.createReview(review);
    }

    @GetMapping(value = "/reviews")
    public List<Review> getAllReviews(){
        return reviewsService.getAllReviews();
    }

    @GetMapping(value = "/reviews/byuser/{userId}")
    public List<Review> getReviewsByUser(@PathVariable Long userId){
        return reviewsService.getReviewsByUser(userId);
    }

    @GetMapping(value = "/reviews/bybook/{bookId}")
    public List<Review> getReviewsByBook(@PathVariable Long bookId){
        return reviewsService.getReviewsByBook(bookId);
    }
}
