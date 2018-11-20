package com.dayannn.RSOI2.reviewsservice.repository;

import com.dayannn.RSOI2.reviewsservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Review, Long> {
}
