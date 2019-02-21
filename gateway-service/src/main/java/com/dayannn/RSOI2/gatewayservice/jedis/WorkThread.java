package com.dayannn.RSOI2.gatewayservice.jedis;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class WorkThread implements Runnable {
    private Logger logger = LoggerFactory.getLogger(WorkThread.class);
    private Jedis queue;
    private Thread thread;
    final private int TIMEOUT_SECONDS = 2;

    public WorkThread(Jedis queue) {
        this.queue = queue;
        this.thread = new Thread(this);
    }

    public void start() {
        this.thread.start();
    }

    public void run() {
        while (queue.exists("work")) {
            JSONObject req = null;
            try {
                req = new JSONObject(queue.rpop("work"));
            } catch (JSONException e) {
                logger.error("Error parsing request from queue ", e);
                return;
            }

            logger.info("Trying to execute request from queue " + req);

            CloseableHttpResponse httpResponse;
            try {
                CloseableHttpClient httpClient = HttpClients.createDefault();

                HttpRequestBase requestBase = null;
                String url = req.getString("url");
                switch (req.getString("req_type")) {
                    case ("GET"):
                        requestBase = new HttpGet(url);
                        break;
                    case ("POST"):
                        requestBase = new HttpPost(url);
                       // ((HttpPost) requestBase).setEntity(new StringEntity(req.getString("body")));
                        break;
                    case ("PUT"):
                        requestBase = new HttpPut(url);
                       // ((HttpPut) requestBase).setEntity(new StringEntity(req.getString("body")));
                        break;
                    case ("DELETE"):
                        requestBase = new HttpDelete(url);
                        break;
                    default:
                        logger.warn("Unknown request type: ", req.getString("req_type"));
                        return;
                }
                requestBase.addHeader("content-type", "application/json");
                requestBase.addHeader("Authorization", "Bearer " + req.getString("token"));

                httpResponse = httpClient.execute(requestBase);
                if (httpResponse.getStatusLine().getStatusCode() == 401 || httpResponse.getStatusLine().getStatusCode() == 403) {
                    String t = this.askToken(req.getString("auth_url"), req.getString("cred"));
                    requestBase.removeHeaders("Authorization");
                    requestBase.addHeader("Authorization", "Bearer " + t);
                    req.remove("token");
                    req.put("token", t);

                    queue.lpush("work", req.toString());
                    try {
                        Thread.sleep(TIMEOUT_SECONDS * 1000);
                    } catch (InterruptedException ignored) {
                    }
                }

            } catch (Exception e) {
                logger.warn("Error executing request ", e);
                queue.lpush("work", req.toString());
                try {
                    Thread.sleep(TIMEOUT_SECONDS * 1000);
                } catch (InterruptedException ignored) {
                    this.thread.interrupt();
                    logger.error("Sleep interrupted ", e);
                }
            }

        }
        this.thread.interrupt();
    }

    private String askToken(String url, String cred) {
        String t;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request1 = new HttpPost(url + "/oauth/token?grant_type=client_credentials");
        request1.addHeader("Authorization", "Basic " + cred);
        try {
            HttpResponse hr = httpClient.execute(request1);
            JSONObject p = new JSONObject(EntityUtils.toString(hr.getEntity()));
            t = p.getString("access_token");
        } catch (Exception e) {
            logger.error("Error asking token in Work Thread ", e);
            t = "";
        }
        return t;
    }
}