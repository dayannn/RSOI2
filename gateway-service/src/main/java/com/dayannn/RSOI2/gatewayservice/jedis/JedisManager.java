package com.dayannn.RSOI2.gatewayservice.jedis;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Arrays;

public class JedisManager {
    private Logger logger = LoggerFactory.getLogger(JedisManager.class);
    private Jedis blockingQueue = null;

    private void setBlockingQueue(Jedis blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public JedisManager(Jedis j) {
        this.setBlockingQueue(j);
    }

    public void addReqToQueue(String reqType, HttpRequest req, String cred, String authUrl) {
        JSONObject requestJson = new JSONObject();

        if (!Arrays.asList("GET", "POST", "PUT", "DELETE").contains(reqType)) {
            return;
        }

        try {
            requestJson.put("req_type", reqType);
            requestJson.put("url", req.getRequestLine().getUri());
            requestJson.put("token", req.getFirstHeader("Authorization"));

            requestJson.put("cred", cred);
            requestJson.put("auth_url", authUrl);

//            switch (reqType) {
//                case "POST":
//                    requestJson.put("body", EntityUtils.toString(((HttpPost) req).getEntity()));
//                    break;
//                case "PUT":
//                    requestJson.put("body", EntityUtils.toString(((HttpPut) req).getEntity()));
//                    break;
//            }
        } catch (Exception e) {
            logger.error("Error adding request to jedis queue ", e);
        }

        blockingQueue.lpush("work", requestJson.toString());
    }
}