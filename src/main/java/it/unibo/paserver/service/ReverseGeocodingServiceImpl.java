package it.unibo.paserver.service;

import br.com.bergmannsoft.utils.NominatimClient;
import it.unibo.paserver.domain.ReverseGeocoding;
import it.unibo.paserver.repository.ReverseGeocodingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Claudio
 * @project participact-server
 * @date 18/07/2019
 **/
@Service
@Transactional(readOnly = true)
public class ReverseGeocodingServiceImpl implements ReverseGeocodingService {
    @Autowired
    ReverseGeocodingRepository repos;

    @Override
    @Transactional(readOnly = false)
    public ReverseGeocoding saveOrUpdate(ReverseGeocoding geocoding) {
        return repos.saveOrUpdate(geocoding);
    }

    @Override
    @Transactional(readOnly = true)
    public ReverseGeocoding fetch(Double lat, Double lon) {
        return repos.fetch(lat, lon);
    }

    @Override
    @Transactional(readOnly = false)
    public ReverseGeocoding lookup(double lat, double lon) {
        ReverseGeocoding geocoding = this.fetch(lat, lon);
        if (geocoding == null) {
            try {
                NominatimClient nom = new NominatimClient();
                geocoding = nom.getAdress(lat, lon);
                //Se nao encontrou no openmaps, vamos tentar o google maps
                if (geocoding == null) {
                    return null;
                    // System.out.println("Google Maps");
                    // GoogleMapsGeocoding mapsGeocoding = new GoogleMapsGeocoding();
                    // mapsGeocoding.getAdress(lat, lon);
                } else {
                    return this.saveOrUpdate(geocoding);
                }
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
        return geocoding;
    }
}
