package it.unibo.paserver.service;

import it.unibo.paserver.domain.ReverseGeocoding;

/**
 * @author Claudio
 * @project participact-server
 * @date 18/07/2019
 **/
public interface ReverseGeocodingService {
    ReverseGeocoding saveOrUpdate(ReverseGeocoding geocoding);

    ReverseGeocoding fetch(Double lat, Double lon);

    ReverseGeocoding lookup(double lat, double lng);
}
