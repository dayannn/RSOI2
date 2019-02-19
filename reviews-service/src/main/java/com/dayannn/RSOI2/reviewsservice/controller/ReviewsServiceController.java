package com.dayannn.RSOI2.reviewsservice.controller;

import com.dayannn.RSOI2.reviewsservice.entity.Review;
import com.dayannn.RSOI2.reviewsservice.exeption.ReviewNotFoundException;
import com.dayannn.RSOI2.reviewsservice.service.ReviewsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ReviewsServiceController {
    private ReviewsService reviewsService;
    private Logger logger = LoggerFactory.getLogger(ReviewsServiceController.class);

    @Autowired
    ReviewsServiceController(ReviewsService reviewsService){
        this.reviewsService = reviewsService;
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
    public Object getReviewsByBook(@PathVariable Long bookId,
                                         @RequestParam (value = "page", required = false) Integer page,
                                         @RequestParam (value = "size", required = false) Integer size){
        if (page != null && size != null) {
            logger.info("[GET] /reviews/bybook/" + bookId + " page= " + page + ", size= " + size);
            PageRequest p = PageRequest.of(page, size);
            return reviewsService.getReviewsByBook(bookId, p);
        }
        else{
            logger.info("[GET] /reviews/bybook/");
            return reviewsService.getReviewsByBook(bookId);
        }
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

    @GetMapping(value = "/healthcheck")
    public ResponseEntity healthCheck(){
        logger.info("[GET] /healthcheck");
        return reviewsService.healthCheck();
    }
}
