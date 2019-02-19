package com.dayannn.RSOI2.gatewayservice.service;

import com.dayannn.RSOI2.gatewayservice.jedis.JedisManager;
import com.dayannn.RSOI2.gatewayservice.jedis.WorkThread;
import org.apache.http.*;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpHostConnectException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@Service
public class GatewayServiceImpl implements GatewayService {
    private Logger logger = LoggerFactory.getLogger(GatewayServiceImpl.class);

    final private String reviewsServiceUrl = "http://localhost:8070";
    final private String booksServiceUrl = "http://localhost:8071";
    final private String usersServiceUrl = "http://localhost:8072";

    private String gatewayToken = "";
    private String booksToken = "";
    private String reviewsToken = "";
    private String usersToken = "";

    private Jedis jedis = new Jedis("127.0.0.1", 6379);
    private JedisManager jedisManager = new JedisManager(jedis);
    private WorkThread workThread = new WorkThread(jedis);

    HttpResponseFactory responseFactory= new DefaultHttpResponseFactory();

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
        HttpGet request = new HttpGet(usersServiceUrl + "/users/" + userId);
        HttpResponse response = authAndExecute(usersServiceUrl, request, usersToken);

        return EntityUtils.toString(response.getEntity());
    }

    @Override
    public void deleteUser(Long userId) throws IOException{
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
    public ResponseEntity getBooksWithReviews() throws IOException, JSONException{
        HttpGet request = new HttpGet(booksServiceUrl + "/books");
        HttpResponse response = authAndExecute(booksServiceUrl, request, booksToken);

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
            return ResponseEntity.status(response.getStatusLine().getStatusCode())
                    .body(EntityUtils.toString(response.getEntity()));
        }
        JSONArray books = new JSONArray(EntityUtils.toString(response.getEntity()));

        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i < books.length(); i++) {
            JSONObject obj = books.getJSONObject(i);
            String sfname = obj.getString("id");

            HttpGet request2 = new HttpGet(reviewsServiceUrl + "/reviews/bybook/" + sfname);
            HttpResponse response2 = authAndExecute(reviewsServiceUrl, request2, reviewsToken);

            StringBuilder responseStr = new StringBuilder();
            responseStr.append(obj);
            if (response2.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                StringBuilder reviews = new StringBuilder(",\n\"reviews\":\n");
                reviews.append(EntityUtils.toString(response2.getEntity()));

                responseStr.insert(responseStr.length()-1, reviews);
            }
            if (i != books.length()-1)
                responseStr.append(",");

            result.append(responseStr);
        }
        result.append("]");
        System.out.println();

        System.out.print(result);

        return ResponseEntity.ok(result);
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
        try{
            HttpPost request = new HttpPost(reviewsServiceUrl + "/reviews");
            StringEntity params = new StringEntity(review, "UTF-8");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            authAndExecute(reviewsServiceUrl, request, reviewsToken);

            JSONObject obj = new JSONObject(review);
            long bookId = obj.getLong("bookId");
            request = new HttpPost(booksServiceUrl + "/books/" + bookId + "/add_review");
            authAndExecute(booksServiceUrl, request, booksToken);

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
            authAndExecute(booksServiceUrl, request3, booksToken);
        } catch (Exception ex) {
            // обработка исключения
        }
    }

    @Override
    public void deleteReview(Long reviewId){
        try{
            HttpGet request = new HttpGet(reviewsServiceUrl + "/reviews/" + reviewId);
            HttpResponse response = authAndExecute(reviewsServiceUrl, request, reviewsToken);

            String book = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONObject obj = new JSONObject(book);
            Long bookId = obj.getLong("bookId");

            HttpDelete request1 = new HttpDelete(reviewsServiceUrl + "/reviews/" + reviewId);
            authAndExecute(reviewsServiceUrl, request1, reviewsToken);

            HttpPost request3 = new HttpPost(booksServiceUrl + "/books/" + bookId + "/delete_review");
            authAndExecute(booksServiceUrl, request3, booksToken);

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
            authAndExecute(booksServiceUrl, request4, booksToken);
        } catch (Exception ex) {
            // обработка исключения
        }
    }


    @Override
    public String getReviewsForBook(Long bookId, PageRequest p) throws IOException{
        HttpGet request = new HttpGet(reviewsServiceUrl +"/reviews/bybook/"+ bookId +
                "?page=" + p.getPageNumber() + "&size=" + p.getPageSize());
        HttpResponse response = authAndExecute(reviewsServiceUrl, request, reviewsToken);

        return EntityUtils.toString(response.getEntity());
    }

    @Override
    public String getBookById(@PathVariable Long bookId) throws IOException{
        HttpGet request = new HttpGet(booksServiceUrl + "/book/" + bookId);
        HttpResponse response = authAndExecute(booksServiceUrl, request, booksToken);

        return EntityUtils.toString(response.getEntity());
    }

    @Override
    public HttpResponse requestToken(String url, String credentials) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        request.addHeader("Authorization",
                "Basic " + credentials);

//        HttpResponse response = responseFactory.newHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, null);

//        JSONObject p = new JSONObject(EntityUtils.toString(r.getEntity()));
//        token = p.getString("access_token");

        logger.info("Token received from url" + url + " : " + gatewayToken);
        return httpClient.execute(request);

    }

    @Override
    public HttpResponse checkToken(String host, String token) throws IOException {
        HttpGet request;
        HttpResponse response;

        String encodedCredentials = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

        request = new HttpGet(host + "/oauth/check_token?token=" + gatewayToken); // not the token i need
        response = tryGetResponse(request, "Basic", encodedCredentials);

        if (response.getStatusLine().getStatusCode() == 400
                || response.getStatusLine().getStatusCode() == 401
                || response.getStatusLine().getStatusCode() == 403) {
            HttpResponse httpResponse = requestToken(host + "/oauth/token?grant_type=client_credentials", encodedCredentials);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                return httpResponse;
            }
            else {
                try {
                    JSONObject p = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
                    gatewayToken = p.getString("access_token");
                } catch (JSONException e) {
                    logger.error("Error parsing response ", e);
                    return responseFactory.newHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_INTERNAL_SERVER_ERROR, null);
                }
            }
            request = new HttpGet(host + "/oauth/check_token?token=" + gatewayToken);
            response = tryGetResponse(request, "Basic", encodedCredentials);
        }

        try {
            JSONObject p = new JSONObject(EntityUtils.toString(response.getEntity()));
            boolean active = p.getBoolean("active");
            if (active) {
                return responseFactory.newHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, null);
            }
        } catch (JSONException e) {
            logger.error("Error parsing json ", e);
            return responseFactory.newHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_INTERNAL_SERVER_ERROR, null);
        }

        logger.error("Unchecked error while checking token");
        return responseFactory.newHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_FORBIDDEN, null);
    }

    private HttpResponse tryGetResponse(HttpUriRequest request, String authType, String credentials) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        request.removeHeaders("Authorization");
        request.addHeader("Authorization", authType + " " + credentials);
        return httpClient.execute(request);
    }


    private HttpResponse authAndExecute(String host, HttpUriRequest request, String token) throws IOException {
        HttpResponse response = responseFactory.newHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_BAD_REQUEST, null);

        try {
            response = tryGetResponse(request, "Bearer", token);
            if (response.getStatusLine().getStatusCode() == 401 || response.getStatusLine().getStatusCode() == 403) {
                HttpResponse httpResponse = requestToken(host + "/oauth/token?grant_type=client_credentials", Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()));
                JSONObject p = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
                token = p.getString("access_token");;
                response = tryGetResponse(request, "Bearer", token);
            }
            return response;
        }
        catch(HttpHostConnectException ex){
            JSONObject answer = new JSONObject();
            try {
                answer.put("error", "Service " + host + " is not available at the moment");
                response.setEntity(new StringEntity(answer.toString()));
                response.setStatusCode(HttpStatus.SC_SERVICE_UNAVAILABLE);
            } catch (JSONException e) {
                logger.error("Error parsing json ", e);
            }
            return response;
        } catch (JSONException e) {
            logger.error("Error parsing response ", e);
            return responseFactory.newHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public String oauthGetCode(String host, String clientId, String redirectUri, String responseType) throws IOException {
        return (host + "/oauth/authorize?grant_type=authorization_code&" +
                "client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=" + responseType);
    }

    @Override
    public String oauthExchangeCode(String host, String code, String redirectUri, String clientCred) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost((host + "/oauth/token?grant_type=authorization_code&" +
                "code=" + code +
                "&redirect_uri=" + redirectUri));

        request.addHeader("Authorization", "Basic " + clientCred);
        HttpResponse response = httpClient.execute(request);

        return EntityUtils.toString(response.getEntity());
    }

}
