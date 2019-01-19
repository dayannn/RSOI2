package com.dayannn.RSOI2.gatewayservice.controller;

import com.dayannn.RSOI2.gatewayservice.service.GatewayService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/api")
public class GatewayServiceController {
    private final GatewayService gatewayService;
    private Logger logger;

    @Autowired
    public GatewayServiceController(GatewayService gatewayService){
        logger = LoggerFactory.getLogger(GatewayServiceController.class);
        this.gatewayService = gatewayService;
    }

    @GetMapping(path = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUsers() throws IOException{
        logger.info("[GET] users/");
        return gatewayService.getUsers();
    }

    @DeleteMapping(path = "users/{userId}")
    public void deleteUser(@PathVariable Long userId) throws IOException{
        logger.info("[DELETE] users/" + userId);
        gatewayService.deleteUser(userId);
    }

    @PostMapping(path = "users")
    public void addUser(@RequestBody String user) throws IOException{
        logger.info("[POST] /users\n ", user);
        gatewayService.addUser(user);
    }


    @GetMapping(path = "users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserById(@PathVariable Long userId) throws IOException {
        logger.info("[GET] users/" +userId);
        return gatewayService.getUserById(userId);
    }

    @GetMapping(path = "/users/{userId}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getReviewsByUser(@PathVariable Long userId) throws IOException, JSONException {
        logger.info("[GET] /users/" + userId + "/reviews");
        return gatewayService.getReviewsByUser(userId);
    }

    @GetMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBooksWithReviews() throws IOException, JSONException {
        logger.info("[GET] /books");
        return gatewayService.getBooksWithReviews();
    }

    @PostMapping(value = "/reviews")
    public void createReview(@RequestBody String review) throws IOException {
        gatewayService.createReview(review);
        logger.info("[POST] /reviews ", "review: ", review);
    }

    @DeleteMapping(value = "/reviews/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) throws IOException {
        gatewayService.deleteReview(reviewId);
        logger.info("[DELETE] /reviews/" + reviewId);
    }
}
