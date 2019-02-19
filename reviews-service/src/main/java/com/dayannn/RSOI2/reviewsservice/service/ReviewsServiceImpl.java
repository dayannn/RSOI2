package com.dayannn.RSOI2.reviewsservice.service;

import com.dayannn.RSOI2.reviewsservice.entity.Review;
import com.dayannn.RSOI2.reviewsservice.exeption.ReviewNotFoundException;
import com.dayannn.RSOI2.reviewsservice.repository.ReviewsRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ReviewsServiceImpl implements ReviewsService {
    private final ReviewsRepository reviewsRepository;

    @Autowired
    public ReviewsServiceImpl(ReviewsRepository reviewsRepository){
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewsRepository.findAll();
    }

    @Override
    public Long createReview(Review review) {
        review.setPostedTime(Calendar.getInstance().getTime().getTime());
        reviewsRepository.save(review);
        return review.getId();
    }

    @Override
    public List<Review> getReviewsByUser(Long userId) {
        return reviewsRepository.findByUidOrderByPostedTimeDesc(userId);
    }



    @Override
    @Transactional
    public Page<Review> getReviewsByBook(Long bookId, PageRequest p) {
        return reviewsRepository.findByBookIdOrderByPostedTimeDesc(bookId, p);
    }

    @Override
    @Transactional
    public List<Review> getReviewsByBook(Long bookId) {
        return reviewsRepository.findByBookIdOrderByPostedTimeDesc(bookId);
    }


    @Override
    public void deleteReviewsByBook(Long bookId) {
        reviewsRepository.deleteAllByBookId(bookId);
    }

    @Override
    public void deleteById(Long id) {
        reviewsRepository.deleteById(id);
    }

    @Override
    public Review getReviewById(Long id) throws ReviewNotFoundException {
        return reviewsRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
    }

    @Override
    public ResponseEntity healthCheck() {
        return ResponseEntity.ok("The reviewsService is up");
    }
}