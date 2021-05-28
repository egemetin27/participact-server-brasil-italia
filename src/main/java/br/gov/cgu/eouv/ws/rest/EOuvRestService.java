package br.gov.cgu.eouv.ws.rest;

import br.com.bergmannsoft.utils.Validator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/07/2019
 **/
public class EOuvRestService {
    protected Map headers = new HashMap<String, String>();

    /**
     * POST Request
     *
     * @param restUrl
     * @param restPoint
     * @param restBody
     * @return
     */
    @SuppressWarnings("Duplicates")
    public String post(String restUrl, String restPoint, String restBody) {
        // Request parameters and other properties.
        String res = new String();
        String url = String.format("https://%s/%s", restUrl, restPoint);
        HttpPost httpPost = new HttpPost(url);
        // Log
        System.out.println(restBody);
        //Fix headers
        httpPost.addHeader("Host", restUrl);
        if (!Validator.isEmptyString(restBody)) {
            // Create a StringEntity
            String body = restBody;
            StringEntity stringEntity = new StringEntity(body, "UTF-8");
            stringEntity.setChunked(false);
            httpPost.setEntity(stringEntity);
        }
        try {
            res = this.request(httpPost);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return res;
    }

    /**
     * GET Request
     *
     * @param restUrl
     * @param restPoint
     * @param restBody
     * @return
     */
    @SuppressWarnings("Duplicates")
    public String get(String restUrl, String restPoint, String restBody) {
        // Request parameters and other properties.
        String res = new String();
        String url = String.format("https://%s/%s", restUrl, restPoint);
        HttpGetWithEntity httpGet = new HttpGetWithEntity(url);
        //Fix headers
        httpGet.addHeader("Host", restUrl);
        if (!Validator.isEmptyString(restBody)) {
            // Create a StringEntity
            String body = restBody;
            StringEntity stringEntity = new StringEntity(body, "UTF-8");
            stringEntity.setChunked(true);
            httpGet.setEntity(stringEntity);
        }
        try {
            res = this.request(httpGet);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    /**
     * HttpClient Request in APIs
     *
     * @param http
     * @return
     * @throws IOException
     */
    @SuppressWarnings("Duplicates")
    private String request(HttpRequestBase http) throws Exception {
        // Headers
        http.addHeader("cache-control", "no-cache");
        http.addHeader("Cache-Control", "no-cache");
        http.addHeader("Accept-Encoding", "gzip, deflate");
        //Dynaminc  Headers
        if (headers != null && headers.size() > 0) {
            Iterator it = headers.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                http.addHeader(pair.getKey().toString(), pair.getValue().toString());
            }
        } else {
            http.addHeader("Content-Type", "application/json");
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++ \n");
        System.out.println(http.getURI());
        // Execute and get the response.
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(http);
        HttpEntity entity = response.getEntity();
        StatusLine status = response.getStatusLine();
        if (status.getStatusCode() == 200) {
            String res = null;
            if (entity != null) {
                //Entity
                res = EntityUtils.toString(entity);
                res = res.replaceAll("\\s+", " ");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++ \n");
                System.out.println(res.toString());
                System.out.println("+++++++++++++++++++++++++++++++++++++++++ \n");
                //Json Response
                return res;
            }
        } else {
            throw new Exception(status.getReasonPhrase());
        }


        return null;
    }
}
