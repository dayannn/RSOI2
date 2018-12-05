package com.dayannn.RSOI2.gatewayservice.controller;

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
    final private String reviewsServiceUrl = "http://localhost:8070";
    final private String booksServiceUrl = "http://localhost:8071";
    final private String usersServiceUrl = "http://localhost:8072";

    @GetMapping(path = "users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserById(@PathVariable Long userId) throws IOException {
        String url = usersServiceUrl + "/users" + "/" + userId;
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close();

        return response.toString();
    }

    @GetMapping(path = "/users/{userId}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getReviewsByUser(@PathVariable Long userId) throws IOException, JSONException {
        String url = reviewsServiceUrl + "/reviews/byuser" + "/" + userId;
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close();

        return response.toString();
    }

    @GetMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBooksWithReviews() throws IOException, JSONException {
        String url = booksServiceUrl + "/books";
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF8"));


        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close();

        JSONArray books = new JSONArray(response.toString());

        JSONArray jsonArray = new JSONArray();


        List<String> temp = new ArrayList<>();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < books.length(); i++) {
            //get the JSON Object
            JSONObject obj = books.getJSONObject(i);
            String sfname = obj.getString("id");
           // temp.add(sfname);


            url = reviewsServiceUrl +"/reviews/bybook/" + sfname;
            website = new URL(url);
            connection = website.openConnection();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            response = new StringBuilder();
            StringBuilder reviews = new StringBuilder(",\n\"reviews\":\n");
            response.append(obj);
            while ((inputLine = in.readLine()) != null)
                reviews.append(inputLine);
            in.close();
            reviews.append("\n");
            response.insert(response.length()-1, reviews);
            //jsonObject = new JSONObject(response.toString());

            result.append(response);
        }

        System.out.println();

        // RestTemplate
        // Jackson / gson
//        for (String a: temp) {
//
//        }

        System.out.print(result);

        return result.toString();
    }

    @PostMapping(value = "/reviews")
    public void createReview(@RequestBody String review) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try{
            HttpPost request = new HttpPost(reviewsServiceUrl + "/reviews");
            StringEntity params =new StringEntity(review);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");

            JSONObject obj = new JSONObject(review);
            Long bookId = obj.getLong("bookId");
            request = new HttpPost(booksServiceUrl + "/books/" + bookId + "/add_review");
            response = httpClient.execute(request);
        } catch (Exception ex) {
            // handle exception here
        } finally {
            httpClient.close();
        }
    }
}
