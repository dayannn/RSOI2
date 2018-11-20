package com.dayannn.RSOI2.reviewsservice.controller;

import com.dayannn.RSOI2.reviewsservice.entity.Review;
import com.dayannn.RSOI2.reviewsservice.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewsServiceController {
    private ReviewsService reviewsService;

    @Autowired
    ReviewsServiceController(ReviewsService reviewsService){
        this.reviewsService = reviewsService;
    }

    @PostMapping(value = "/books")
    public void createBook(@RequestBody Review review){
        reviewsService.createReview(review);
    }

    @GetMapping(value = "/books")
    public List<Review> getAllReviews(){
        return reviewsService.getAllReviews();
    }


}
