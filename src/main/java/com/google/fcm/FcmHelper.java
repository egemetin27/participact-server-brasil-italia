package com.google.fcm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Envios pelo FCM
 *
 * @author Claudio
 * @see //github.com/MOSDEV82/fcmhelper/blob/master/FcmHelper.java
 */
public class FcmHelper {
    /**
     * Instance
     **/
    private static FcmHelper instance = null;
    /**
     * Google URL to use firebase cloud messenging
     */
    private static final String URL_SEND = "https://fcm.googleapis.com/fcm/send";
    /**
     * STATIC TYPES
     */
    public static final String TYPE_TO = "to"; // Use for single devices, device
    // groups and topics
    public static final String TYPE_CONDITION = "condition"; // Use for
    // Conditions
    /**
     * Your SECRET server key
     */
    private static String FCM_SERVER_KEY = "<Enter your server key here>";

    public static FcmHelper getInstance(String serverKey) {
        if (instance == null)
            instance = new FcmHelper(serverKey);
        return instance;
    }

    private FcmHelper(String serverKey) {
        FcmHelper.FCM_SERVER_KEY = serverKey;
    }

    /**
     * Retorna o Json Formato
     *
     * @param json
     * @return
     */
    public FcmMessageResponse getFromJson(String json) throws IOException {
        FcmMessageResponse fcmMessageResponse = null;
        ObjectMapper mapper = new ObjectMapper();
        fcmMessageResponse = mapper.readValue(json, FcmMessageResponse.class);
        //System.out.println(" jackson " + fcmMessageResponse.toString());
        //Gson gson = new Gson();
        //fcmMessageResponse = gson.fromJson(json, FcmMessageResponse.class);
        //System.out.println(" Gson " + fcmMessageResponse.toString());

        return fcmMessageResponse;
    }

    /**
     * Send notification
     *
     * @param type
     * @param typeParameter
     * @param notificationObject
     * @return
     * @throws IOException
     */
    public String sendNotification(String type, String typeParameter, JsonObject notificationObject)
            throws IOException {
        return sendNotifictaionAndData(type, typeParameter, notificationObject, null);
    }

    /**
     * Send data
     *
     * @param type
     * @param typeParameter
     * @param dataObject
     * @return
     * @throws IOException
     */
    public String sendData(String type, String typeParameter, JsonObject dataObject) throws IOException {
        return sendNotifictaionAndData(type, typeParameter, null, dataObject);
    }

    /**
     * Send notification and data
     *
     * @param type
     * @param typeParameter
     * @param notificationObject
     * @param dataObject
     * @return
     * @throws IOException
     */
    public String sendNotifictaionAndData(String type, String typeParameter, JsonObject notificationObject,
                                          JsonObject dataObject) throws IOException {
        String result = null;
        if (type.equals(TYPE_TO) || type.equals(TYPE_CONDITION)) {
            JsonObject sendObject = new JsonObject();
            sendObject.addProperty(type, typeParameter);
            result = sendFcmMessage(sendObject, notificationObject, dataObject);
        }
        return result;
    }

    /**
     * Send data to a topic
     *
     * @param topic
     * @param dataObject
     * @return
     * @throws IOException
     */
    public String sendTopicData(String topic, JsonObject dataObject) throws IOException {
        return sendData(TYPE_TO, "/topics/" + topic, dataObject);
    }

    /**
     * Send notification to a topic
     *
     * @param topic
     * @param notificationObject
     * @return
     * @throws IOException
     */
    public String sendTopicNotification(String topic, JsonObject notificationObject) throws IOException {
        return sendNotification(TYPE_TO, "/topics/" + topic, notificationObject);
    }

    /**
     * Send notification and data to a topic
     *
     * @param topic
     * @param notificationObject
     * @param dataObject
     * @return
     * @throws IOException
     */
    public String sendTopicNotificationAndData(String topic, JsonObject notificationObject, JsonObject dataObject)
            throws IOException {
        return sendNotifictaionAndData(TYPE_TO, "/topics/" + topic, notificationObject, dataObject);
    }

    /**
     * Send a Firebase Cloud Message
     *
     * @param sendObject         - Contains to or condition
     * @param notificationObject - Notification Data
     * @param dataObject         - Data
     * @return
     * @throws IOException
     */
    private String sendFcmMessage(JsonObject sendObject, JsonObject notificationObject, JsonObject dataObject)
            throws IOException {
        HttpPost httpPost = new HttpPost(URL_SEND);

        // Header setzen
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "key=" + FCM_SERVER_KEY);

        if (notificationObject != null)
            sendObject.add("notification", notificationObject);
        if (dataObject != null)
            sendObject.add("data", dataObject);

        String data = sendObject.toString();

        StringEntity entity = new StringEntity(data.toString(), StandardCharsets.UTF_8);
        // JSON-Object übergeben
        httpPost.setEntity(entity);
        HttpClient httpClient = HttpClientBuilder.create().build();

        BasicResponseHandler responseHandler = new BasicResponseHandler();
        String response = (String) httpClient.execute(httpPost, responseHandler);

        System.out.println("sendFcmMessage " + data);
        System.out.println("sendFcmMessage " + response);
        return response;
    }

    public String sendGcmMessage(JsonObject sendObject, JsonObject notificationObject, JsonObject dataObject)
            throws IOException {
        HttpPost httpPost = new HttpPost("https://android.googleapis.com/gcm/send");

        // Header setzen
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "key= AIzaSyAGHmaTa_RXnIYFpHaR0kI1JYht_VZXaJ4");

        if (notificationObject != null)
            sendObject.add("notification", notificationObject);
        if (dataObject != null)
            sendObject.add("data", dataObject);

        String data = sendObject.toString();

        StringEntity entity = new StringEntity(data.toString(), StandardCharsets.UTF_8);
        // JSON-Object übergeben
        httpPost.setEntity(entity);
        HttpClient httpClient = HttpClientBuilder.create().build();

        BasicResponseHandler responseHandler = new BasicResponseHandler();
        String response = (String) httpClient.execute(httpPost, responseHandler);

        System.out.println(data);
        System.out.println(response);
        return response;
    }
}