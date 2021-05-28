package br.com.bergmannsoft.utils;

import br.com.bergmannsoft.config.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import it.unibo.paserver.domain.ReverseGeocoding;

import java.io.IOException;

/**
 * @author Claudio
 * @project participact-server
 * @date 18/07/2019
 **/
public class GoogleMapsGeocoding {

    public ReverseGeocoding getAdress(Double lat, Double lon) {
        GeoApiContext context = new GeoApiContext.Builder().apiKey(Config.GOOGLE_API_KEY_MAP).build();
        GeocodingApiRequest apiRequest = GeocodingApi.newRequest(context);
        try {
            apiRequest.latlng(new LatLng(lat, lon)).await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // System.out.println(gson.toJson(results.));
        return null;
    }
}
