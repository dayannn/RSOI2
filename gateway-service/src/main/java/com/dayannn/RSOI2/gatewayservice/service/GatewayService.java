package com.dayannn.RSOI2.gatewayservice.service;

import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;


public interface GatewayService {

    String getUserById(Long userId) throws IOException;
    String getReviewsByUser(@PathVariable Long userId) throws IOException;
    String getBooksWithReviews() throws IOException, JSONException;
    void createReview(@RequestBody String review) throws IOException;
}
