package br.gov.cgu.eouv.ws.soap;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
/**
 * @author Claudio
 * @project participact-server
 * @date 08/04/2019
 **/
public class EOuvWebService {

    /**
     * Requisicao Soap
     *
     * @param soapEnvUrl
     * @param soapEnvAction
     * @param soapEnvBody
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public Document request(String soapEnvUrl, String soapEnvAction, String soapEnvBody) throws IOException, ParserConfigurationException, SAXException {
        // Create a StringEntity for the SOAP XML.
        String body = soapEnvBody;
        StringEntity stringEntity = new StringEntity(body, "UTF-8");
        stringEntity.setChunked(true);

        // Request parameters and other properties.
        HttpPost httpPost = new HttpPost(soapEnvUrl);
        httpPost.setEntity(stringEntity);
        httpPost.addHeader("Accept", "text/xml");
        httpPost.addHeader("Content-Type", "text/xml;charset=UTF-8");
        httpPost.addHeader("SOAPAction", soapEnvAction);
        // Execute and get the response.
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();

        String res = null;
        if (entity != null) {
            //Entity
            res = EntityUtils.toString(entity);
            res = res.replaceAll("\\s+", " ");
            System.out.println("+++++++++++++++++++++++++++++++++++++++++ \n");
            System.out.println(res.toString());
            System.out.println("+++++++++++++++++++++++++++++++++++++++++ \n");
            //Document
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            InputStream is = IOUtils.toInputStream(res, StandardCharsets.UTF_8);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document dom = builder.parse(is);

            return dom;
        }
        return null;
    }
}
