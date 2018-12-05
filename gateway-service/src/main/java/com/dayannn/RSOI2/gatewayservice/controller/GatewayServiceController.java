package com.dayannn.RSOI2.gatewayservice.controller;

import com.dayannn.RSOI2.gatewayservice.service.GatewayService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GatewayServiceController {
    private final GatewayService gatewayService;

    @Autowired
    public GatewayServiceController(GatewayService gatewayService){
        this.gatewayService = gatewayService;
    }

    @GetMapping(path = "users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserById(@PathVariable Long userId) throws IOException {
        return gatewayService.getUserById(userId);
    }

    @GetMapping(path = "/users/{userId}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getReviewsByUser(@PathVariable Long userId) throws IOException, JSONException {
        return gatewayService.getReviewsByUser(userId);
    }

    @GetMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBooksWithReviews() throws IOException, JSONException {
        return gatewayService.getBooksWithReviews();
    }

    @PostMapping(value = "/reviews")
    public void createReview(@RequestBody String review) throws IOException {
        createReview(review);
    }


}
