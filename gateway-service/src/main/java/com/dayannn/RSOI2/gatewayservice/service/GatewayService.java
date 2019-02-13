package com.dayannn.RSOI2.gatewayservice.service;

import org.json.JSONException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;


public interface GatewayService {
    String getUsers() throws IOException;
    String getUserById(Long userId) throws IOException;
    String getReviewsByUser(Long userId) throws IOException;
    String getBooksWithReviews() throws IOException, JSONException;
    String getReviewsForBook(Long bookId, PageRequest p) throws IOException;
    String getBookById(Long bookId) throws IOException;
    void addUser(String user) throws IOException;
    void createReview(String review) throws IOException;
    void deleteReview(Long reviewId) throws IOException;
    void deleteUser(Long id) throws IOException;
    boolean checkToken(String host, String token) throws IOException;
}
