package com.dayannn.RSOI2.gatewayservice.service;

import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class GatewayServiceImplementation implements GatewayService {
    final private String reviewsServiceUrl = "http://localhost:8070";
    final private String booksServiceUrl = "http://localhost:8071";
    final private String usersServiceUrl = "http://localhost:8072";


    private String gatewayToken = "";
    private String booksToken = "";
    private String reviewsToken = "";
    private String usersToken = "";

    private Logger logger = LoggerFactory.getLogger(GatewayServiceImplementation.class);

    @Value("${clientId}")
    private String clientId;

    @Value("${clientSecret}")
    private String clientSecret;

    public String getGatewayToken() {
        return gatewayToken;
    }

    public void setGatewayToken(String gatewayToken) {
        this.gatewayToken = gatewayToken;
    }

    @Override
    public String getUsers() throws IOException{
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(usersServiceUrl + "/users/");
        HttpResponse response = authAndExecute(usersServiceUrl, request, usersToken);

        return EntityUtils.toString(response.getEntity());
    }

    @Override
    public String getUserById(Long userId) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(usersServiceUrl + "/users/" + userId);
        HttpResponse response = authAndExecute(usersServiceUrl, request, usersToken);

        return EntityUtils.toString(response.getEntity());
    }

    @Override
    public void deleteUser(Long userId) throws IOException{
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpDelete request = new HttpDelete(usersServiceUrl + "/users/" + userId);
        authAndExecute(usersServiceUrl, request, usersToken);
    }

    @Override
    public String getReviewsByUser(Long userId) throws IOException {
        String url = reviewsServiceUrl + "/reviews/byuser/" + userId;
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

    @Override
    public String getBooksWithReviews() throws IOException, JSONException{
        HttpGet request = new HttpGet(booksServiceUrl + "/books");
        HttpResponse response = authAndExecute(booksServiceUrl, request, booksToken);

        JSONArray books = new JSONArray(EntityUtils.toString(response.getEntity()));

        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i < books.length(); i++) {
            JSONObject obj = books.getJSONObject(i);
            String sfname = obj.getString("id");

            HttpGet request2 = new HttpGet(reviewsServiceUrl + "/reviews/bybook/" + sfname);
            HttpResponse response2 = authAndExecute(reviewsServiceUrl, request2, reviewsToken);

            StringBuilder responseStr = new StringBuilder();
            StringBuilder reviews = new StringBuilder(",\n\"reviews\":\n");
            reviews.append(EntityUtils.toString(response2.getEntity()));
            responseStr.append(obj);
            responseStr.insert(responseStr.length()-1, reviews);
            if (i != books.length()-1)
                responseStr.append(",");

            result.append(responseStr);
        }
        result.append("]");
        System.out.println();

        System.out.print(result);

        return result.toString();
    }

    @Override
    public void addUser(String user) throws IOException{
        HttpPost request = new HttpPost(usersServiceUrl + "/users");
        StringEntity params = new StringEntity(user);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        authAndExecute(usersServiceUrl, request, usersToken);
    }


    @Override
    public void createReview(String review) throws IOException{
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try{
            HttpPost request = new HttpPost(reviewsServiceUrl + "/reviews");
            StringEntity params = new StringEntity(review, "UTF-8");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = authAndExecute(reviewsServiceUrl, request, reviewsToken);
            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");

            JSONObject obj = new JSONObject(review);
            Long bookId = obj.getLong("bookId");
            request = new HttpPost(booksServiceUrl + "/books/" + bookId + "/add_review");
            response = authAndExecute(booksServiceUrl, request, booksToken);

            HttpGet request2 = new HttpGet(reviewsServiceUrl + "/reviews/bybook/" + bookId);
            HttpResponse response2 = authAndExecute(reviewsServiceUrl, request2, reviewsToken);
            String responseString2 = EntityUtils.toString(response2.getEntity(), "UTF-8");
            JSONArray revsArray = new JSONArray(responseString2);
            double rating = 0;
            for (int i = 0; i < revsArray.length(); i++){
                rating += revsArray.getJSONObject(i).getDouble("rating");
            }
            double averageRating = rating/revsArray.length();

            HttpPost request3 = new HttpPost(booksServiceUrl + "/books/" + bookId +"/setRating/" + String.valueOf(averageRating));
            HttpResponse response3 = authAndExecute(booksServiceUrl, request3, booksToken);
        } catch (Exception ex) {
            // обработка исключения
        } finally {
            httpClient.close();
        }
    }

    @Override
    public void deleteReview(Long reviewId) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try{
            String url = reviewsServiceUrl + "/reviews/" + reviewId;
            URL website = new URL(url);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF8"));


            StringBuilder book = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                book.append(inputLine);
            in.close();
            JSONObject obj = new JSONObject(book.toString());
            Long bookId = obj.getLong("bookId");


            HttpDelete request = new HttpDelete(reviewsServiceUrl + "/reviews/" + reviewId);
            HttpResponse response = authAndExecute(reviewsServiceUrl, request, reviewsToken);

            HttpPost request3 = new HttpPost(booksServiceUrl + "/books/" + bookId + "/delete_review");
            response = authAndExecute(booksServiceUrl, request3, booksToken);

            HttpGet request2 = new HttpGet(reviewsServiceUrl + "/reviews/bybook/" + bookId);
            HttpResponse response2 = authAndExecute(reviewsServiceUrl, request2, reviewsToken);
            String responseString2 = EntityUtils.toString(response2.getEntity(), "UTF-8");
            JSONArray revsArray = new JSONArray(responseString2);
            double rating = 0;
            for (int i = 0; i < revsArray.length(); i++){
                rating += revsArray.getJSONObject(i).getDouble("rating");
            }
            double averageRating = rating/revsArray.length();

            HttpPost request4 = new HttpPost(booksServiceUrl + "/books/" + bookId +"/setRating/" + averageRating);
            HttpResponse response4 = authAndExecute(booksServiceUrl, request4, booksToken);
        } catch (Exception ex) {
            // обработка исключения
        } finally {
            httpClient.close();
        }
    }


    @Override
    public String getReviewsForBook(Long bookId, PageRequest p) throws IOException{
        String url = reviewsServiceUrl + "/reviews/bybook/" + bookId +
                "?page=" + p.getPageNumber() + "&size=" + p.getPageSize();
        HttpGet request = new HttpGet(reviewsServiceUrl +"/reviews/bybook/"+ bookId +
                "?page=" + p.getPageNumber() + "&size=" + p.getPageSize());
        HttpResponse response = authAndExecute(reviewsServiceUrl, request, reviewsToken);

        return EntityUtils.toString(response.getEntity());
    }

    @Override
    public String getBookById(@PathVariable Long bookId) throws IOException{
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(booksServiceUrl + "/book/" + bookId);
        HttpResponse response = authAndExecute(booksServiceUrl, request, booksToken);

        return EntityUtils.toString(response.getEntity());
    }

    @Override
    public String requestToken(String url, String credentials) throws IOException {
        String token = "";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        request.addHeader("Authorization",
                "Basic " + credentials);

        HttpResponse r = httpClient.execute(request);
        try {
            JSONObject p = new JSONObject(EntityUtils.toString(r.getEntity()));
            token = p.getString("access_token");
            logger.info("Token received from url" + url + " : " + gatewayToken);
        } catch (JSONException e) {
            logger.error("Error parsing json ", e);
        }
        return token;
    }

    @Override
    public boolean checkToken(String host, String token) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request;
        HttpResponse response = null;
        int i = 0;
        while (i <= 3) {
            request = new HttpGet(host + "/oauth/check_token?token=" + gatewayToken); // not the token i need
            request.addHeader("Authorization",
                    "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()));
            response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() == 400
                    || response.getStatusLine().getStatusCode() == 401
                    || response.getStatusLine().getStatusCode() == 403) {
                gatewayToken = requestToken(host + "/oauth/token?grant_type=client_credentials",
                        Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()));
            } else
                break;
            i++;
        }

        String username = "";
        try {
            JSONObject p = new JSONObject(EntityUtils.toString(response.getEntity()));
            boolean active = p.getBoolean("active");
            if (active) {
                return true;
            }
        } catch (JSONException e) {
            logger.error("Error parsing json ", e);
        }

        logger.error("Unchecked error while checking token");
        return false;
    }

    private HttpResponse authAndExecute(String host, HttpUriRequest request, String token) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpResponseFactory factory = new DefaultHttpResponseFactory();
        HttpResponse response = factory.newHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_BAD_REQUEST, null), null);

        int i = 0;
        while (i < 3) {
            request.removeHeaders("Authorization");
            request.addHeader("Authorization", "Bearer " + token);
            response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == 401 || response.getStatusLine().getStatusCode() == 403) {
                token = (requestToken(host + "/oauth/token?grant_type=client_credentials", Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes())));
            } else
                //return response;
                break;
            i++;
        }

        return response;
    }

    @Override
    public String oauthGetCode(String host, String client_id, String redirect_uri, String response_type) throws IOException {
        return (host + "/oauth/authorize?grant_type=authorization_code&client_id="+client_id+"&redirect_uri="+redirect_uri+"&response_type="+response_type);
    }

    @Override
    public String oauthExchangeCode(String host, String code, String redirect_uri, String client_cred) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost((host + "/oauth/token?grant_type=authorization_code&code="+code+"&redirect_uri="+redirect_uri));

        request.addHeader("Authorization", "Basic " + client_cred);
        HttpResponse response = httpClient.execute(request);

        return EntityUtils.toString(response.getEntity());
    }

}
