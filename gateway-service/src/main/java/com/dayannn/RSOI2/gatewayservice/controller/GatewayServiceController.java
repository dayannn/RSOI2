package com.dayannn.RSOI2.gatewayservice.controller;

import com.dayannn.RSOI2.gatewayservice.service.GatewayService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/api")
public class GatewayServiceController {
    private final GatewayService gatewayService;
    final private String authServiceUrl = "http://localhost:8081";
    private Logger logger;

    @Autowired
    public GatewayServiceController(GatewayService gatewayService){
        logger = LoggerFactory.getLogger(GatewayServiceController.class);
        this.gatewayService = gatewayService;
    }

    private boolean isTokenValid(String token) throws IOException {
        token = token.replace("Bearer ", "");
        return gatewayService.checkToken(authServiceUrl, token);
    }

    @GetMapping(path = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsers(@RequestHeader("Authorization") String token) throws IOException{
        logger.info("[GET] users/");
        if (!isTokenValid(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return ResponseEntity.ok(gatewayService.getUsers());
    }

    @DeleteMapping(path = "users/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId, @RequestHeader("Authorization") String token) throws IOException{
        if (!isTokenValid(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        logger.info("[DELETE] users/" + userId);
        gatewayService.deleteUser(userId);
        return ResponseEntity.ok("");
    }

    @PostMapping(path = "users")
    public ResponseEntity addUser(@RequestBody String user, @RequestHeader("Authorization") String token) throws IOException{
        if (!isTokenValid(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        logger.info("[POST] /users\n ", user);
        gatewayService.addUser(user);
        return ResponseEntity.ok("");
    }

    @GetMapping(path = "users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserById(@PathVariable Long userId, @RequestHeader("Authorization") String token) throws IOException{
        if (!isTokenValid(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        logger.info("[GET] users/" +userId);
        return ResponseEntity.ok(gatewayService.getUserById(userId));
    }

    @GetMapping(path = "/users/{userId}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getReviewsByUser(@PathVariable Long userId, @RequestHeader("Authorization") String token) throws IOException{
        if (!isTokenValid(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        logger.info("[GET] /users/" + userId + "/reviews");
        return ResponseEntity.ok(gatewayService.getReviewsByUser(userId));
    }

    @GetMapping(path = "/book/{bookId}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getReviewsForBook(@PathVariable Long bookId,
                                    @RequestParam (value = "page") int page,
                                    @RequestParam (value = "size") int size,
                                    @RequestHeader("Authorization") String token) throws IOException{
        if (!isTokenValid(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        logger.info("[GET] /book/" + bookId + "/reviews/?page=" + page + "&size=" + size);
        PageRequest p;
        p = PageRequest.of(page, size);
        return ResponseEntity.ok(gatewayService.getReviewsForBook(bookId, p));
    }

    @GetMapping(path = "book/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getBookById(@PathVariable Long bookId, @RequestHeader("Authorization") String token) throws IOException{
        if (!isTokenValid(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        logger.info("[GET] /book/" + bookId);
        return ResponseEntity.ok(gatewayService.getBookById(bookId));
    }


    @GetMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getBooksWithReviews(@RequestHeader("Authorization") String token) throws IOException, JSONException {
        logger.info("[GET] /books");
        if (!isTokenValid(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.ok(gatewayService.getBooksWithReviews());
    }

    @PostMapping(value = "/reviews")
    public ResponseEntity createReview(@RequestBody String review, @RequestHeader("Authorization") String token) throws IOException {
        logger.info("[POST] /reviews ", "review: ", review);
        if (!isTokenValid(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        gatewayService.createReview(review);
        return ResponseEntity.ok("");
    }

    @DeleteMapping(value = "/reviews/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable Long reviewId, @RequestHeader("Authorization") String token) throws IOException {
        logger.info("[DELETE] /reviews/" + reviewId);
        if (!isTokenValid(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        gatewayService.deleteReview(reviewId);
        return ResponseEntity.ok("");
    }
}
