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
    String requestToken(String url, String credentials) throws IOException;
    boolean checkToken(String url, String token) throws IOException;
    String oauthGetCode(String host, String client_id, String redirect_uri, String response_type) throws IOException;
    String oauthExchangeCode(String host, String code, String redirect_uri, String client_cred) throws IOException;

}
