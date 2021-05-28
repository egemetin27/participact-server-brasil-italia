package br.com.bergmannsoft.utils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import it.unibo.paserver.domain.ReverseGeocoding;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

import static br.com.bergmannsoft.config.Config.NOMINATIM_SERVER_URL;

/**
 * @author Claudio
 * @project participact-server
 * @date 18/07/2019
 **/
public class NominatimClient {

    public NominatimClient() {
    }

    public ReverseGeocoding getAdress(double lat, double lon) {
        String url = String.format(NOMINATIM_SERVER_URL, String.valueOf(lat), String.valueOf(lon));
        // Request
        String response = request(url);
        if (!Validator.isEmptyString(response) && Validator.isValidJson(response)) {
            Gson gson = new Gson();
            NominatimReverse reverse = gson.fromJson(response, NominatimReverse.class);
            if (reverse != null) {
                ReverseGeocoding geocoding = new ReverseGeocoding();
                geocoding.setId(null);
                geocoding.setPlaceId(reverse.getPlaceId());
                geocoding.setLat(lat);
                geocoding.setLon(lon);
                geocoding.setLicence(reverse.getLicence());
                geocoding.setCategory(reverse.getOsmId());
                geocoding.setDisplayName(reverse.getDisplayName());
                // Address
                NominatimAddress address = reverse.getAddress();
                if (address != null) {
                    geocoding.setAddressRoad(address.getRoad());
                    geocoding.setAddressNeighbourhood(address.getNeighbourhood());
                    geocoding.setAddressCityDistrict(address.getCityDistrict());
                    geocoding.setAddressCity(address.getCity());
                    geocoding.setAddressCounty(address.getCounty());
                    geocoding.setAddressStateDistrict(address.getStateDistrict());
                    geocoding.setAddressState(address.getState());
                    geocoding.setAddressPostCode(address.getCountryCode());
                    geocoding.setAddressCountry(address.getCountry());
                    geocoding.setAddressCountryCode(address.getCountryCode());
                }
                // Bounds
                geocoding.setBbNorthLat(reverse.getBbNorthLat());
                geocoding.setBbNorthLng(reverse.getBbNorthLng());
                geocoding.setBbSouthLat(reverse.getBbSouthLat());
                geocoding.setBbSouthLng(reverse.getBbSouthLng());
                //Res
                return geocoding;
            }
        }
        return null;
    }

    private String request(String url) {
        // Request parameters and other properties.
        String res = null;
        HttpGet httpGet = new HttpGet(url);
        //Fix headers
        // Headers
        httpGet.addHeader("cache-control", "no-cache");
        httpGet.addHeader("Cache-Control", "no-cache");
        httpGet.addHeader("Accept-Encoding", "gzip, deflate");
        httpGet.addHeader("Content-Type", "application/json");
        try {
            System.out.println(httpGet.getURI());
            // Execute and get the response.
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            StatusLine status = response.getStatusLine();
            if (status.getStatusCode() == 200) {
                if (entity != null) {
                    //Entity
                    res = EntityUtils.toString(entity);
                    res = res.replaceAll("\\s+", " ");
                    System.out.println(res.toString());
                    //Json Response
                    return res;
                }
            } else {
                throw new Exception(status.getReasonPhrase());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }
}

class NominatimReverse {
    @SerializedName("place_id")
    private Long placeId;
    @SerializedName("licence")
    private String licence;
    @SerializedName("osm_type")
    private String osmType;
    @SerializedName("osm_id")
    private Long osmId;
    @SerializedName("lat")
    private Double lat;
    @SerializedName("lon")
    private Double lon;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("boundingbox")
    private ArrayList<Double> boundingbox;
    @SerializedName("address")
    private NominatimAddress address;

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getOsmType() {
        return osmType;
    }

    public void setOsmType(String osmType) {
        this.osmType = osmType;
    }

    public Long getOsmId() {
        return osmId;
    }

    public void setOsmId(Long osmId) {
        this.osmId = osmId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public ArrayList<Double> getBoundingbox() {
        return boundingbox;
    }

    public void setBoundingbox(ArrayList<Double> boundingbox) {
        this.boundingbox = boundingbox;
    }

    public NominatimAddress getAddress() {
        return address;
    }

    public void setAddress(NominatimAddress address) {
        this.address = address;
    }

    public Double getBbNorthLat() {
        return getBb(0);
    }

    public Double getBbNorthLng() {
        return getBb(2);
    }

    public Double getBbSouthLat() {
        return getBb(1);
    }

    public Double getBbSouthLng() {
        return getBb(3);
    }

    private Double getBb(int index) {
        if (boundingbox != null && boundingbox.size() > 0) {
            return boundingbox.get(index);
        }
        return null;
    }
}

class NominatimAddress {
    @SerializedName("road")
    private String road;
    @SerializedName("neighbourhood")
    private String neighbourhood;
    @SerializedName("city_district")
    private String cityDistrict;
    @SerializedName("city")
    private String city;
    @SerializedName("county")
    private String county;
    @SerializedName("state_district")
    private String stateDistrict;
    @SerializedName("state")
    private String state;
    @SerializedName("postcode")
    private String postcode;
    @SerializedName("country")
    private String country;
    @SerializedName("country_code")
    private String countryCode;

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getCityDistrict() {
        return cityDistrict;
    }

    public void setCityDistrict(String cityDistrict) {
        this.cityDistrict = cityDistrict;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStateDistrict() {
        return stateDistrict;
    }

    public void setStateDistrict(String stateDistrict) {
        this.stateDistrict = stateDistrict;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}