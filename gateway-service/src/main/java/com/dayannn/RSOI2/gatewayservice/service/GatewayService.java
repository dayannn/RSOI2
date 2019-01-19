package com.dayannn.RSOI2.gatewayservice.service;

import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;


public interface GatewayService {
    String getUsers() throws IOException;
    String getUserById(Long userId) throws IOException;
    String getReviewsByUser(@PathVariable Long userId) throws IOException;
    String getBooksWithReviews() throws IOException, JSONException;
    void addUser(@RequestBody String user) throws IOException;
    void createReview(@RequestBody String review) throws IOException;
    void deleteReview(Long reviewId) throws IOException;
    void deleteUser(Long id) throws IOException;
}
