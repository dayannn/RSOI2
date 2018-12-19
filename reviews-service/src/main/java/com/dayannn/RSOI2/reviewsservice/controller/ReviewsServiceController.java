package com.dayannn.RSOI2.reviewsservice.controller;

import com.dayannn.RSOI2.reviewsservice.entity.Review;
import com.dayannn.RSOI2.reviewsservice.exeption.ReviewNotFoundException;
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

    @GetMapping(value = "reviews/{id}")
    public Review getReviewById(@PathVariable Long id) throws ReviewNotFoundException {
        return reviewsService.getReviewById(id);
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

    @DeleteMapping(value = "reviews/{id}")
    public void deleteReviewById(@PathVariable Long id){
        reviewsService.deleteById(id);
    }

    @DeleteMapping(value = "reviews/bybook/{bookId}")
    public void deleteReviewsByBook(@PathVariable Long bookId){
        reviewsService.deleteReviewsByBook(bookId);
    }
}
