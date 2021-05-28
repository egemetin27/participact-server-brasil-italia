package it.unibo.paserver.repository;

import it.unibo.paserver.domain.ReverseGeocoding;

/**
 * @author Claudio
 * @project participact-server
 * @date 18/07/2019
 **/
public interface ReverseGeocodingRepository {

    ReverseGeocoding saveOrUpdate(ReverseGeocoding geocoding);

    ReverseGeocoding find(Long id);

    ReverseGeocoding fetch(Double lat, Double lon);
}
