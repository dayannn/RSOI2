package com.dayannn.RSOI2.reviewsservice.controller;

import com.dayannn.RSOI2.reviewsservice.entity.Review;
import com.dayannn.RSOI2.reviewsservice.exeption.ReviewNotFoundException;
import com.dayannn.RSOI2.reviewsservice.service.ReviewsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ReviewsServiceController {
    private ReviewsService reviewsService;
    private Logger logger;

    @Autowired
    ReviewsServiceController(ReviewsService reviewsService){
        this.reviewsService = reviewsService;
        logger = LoggerFactory.getLogger(ReviewsServiceController.class);
    }

    @PostMapping(value = "/reviews")
    public Long createReview(@RequestBody Review review){
        logger.info("[POST] /reviews", review);
        return reviewsService.createReview(review);
    }

    @GetMapping(value = "reviews/{id}")
    public Review getReviewById(@PathVariable Long id) throws ReviewNotFoundException {
        logger.info("[GET] /reviews/" + id);
        return reviewsService.getReviewById(id);
    }

    @GetMapping(value = "/reviews")
    public List<Review> getAllReviews(){
        logger.info("[GET] /reviews");
        return reviewsService.getAllReviews();
    }

    @GetMapping(value = "/reviews/byuser/{userId}")
    public List<Review> getReviewsByUser(@PathVariable Long userId){
        logger.info("[GET] /reviews/byuser/" + userId);
        return reviewsService.getReviewsByUser(userId);
    }

    @GetMapping(value = "/reviews/bybook/{bookId}")
    public List<Review> getReviewsByBook(@PathVariable Long bookId){
        logger.info("[GET] /reviews/bybook/" + bookId);
        return reviewsService.getReviewsByBook(bookId);
    }

    @DeleteMapping(value = "reviews/{id}")
    public void deleteReviewById(@PathVariable Long id) {
        logger.info("[DELETE] /reviews/" + id);
        reviewsService.deleteById(id);
    }

    @DeleteMapping(value = "reviews/bybook/{bookId}")
    public void deleteReviewsByBook(@PathVariable Long bookId) {
        logger.info("[DELETE] /reviews/bybook/" + bookId);
        reviewsService.deleteReviewsByBook(bookId);
    }
}
