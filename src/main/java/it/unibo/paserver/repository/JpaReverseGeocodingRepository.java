package it.unibo.paserver.repository;

import it.unibo.paserver.domain.ReverseGeocoding;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 18/07/2019
 **/

@SuppressWarnings({"Duplicates", "JpaQlInspection"})
@Repository("jpaReverseGeocodingRepository")
public class JpaReverseGeocodingRepository implements ReverseGeocodingRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ReverseGeocoding saveOrUpdate(ReverseGeocoding g) {
        if (g.getId() != null && g.getId() != 0 && find(g.getId()) != null) {
            g.setUpdateDate(new DateTime());
            return entityManager.merge(g);
        } else {
            g.setCreationDate(new DateTime());
            g.setUpdateDate(new DateTime());
            g.setRemoved(false);
            entityManager.persist(g);
            return g;
        }
    }

    @Override
    public ReverseGeocoding find(Long id) {
        return entityManager.find(ReverseGeocoding.class, id);
    }

    @Override
    public ReverseGeocoding fetch(Double lat, Double lon) {
        // String hql = "SELECT g FROM ReverseGeocoding g WHERE g.bbNorthLat>:lat AND g.bbNorthLng>:lon AND g.bbSouthLat>:lat AND g.bbSouthLng>:lon ";
        String hql = "SELECT g FROM ReverseGeocoding g WHERE g.lat=:lat AND g.lon=:lon ";
        TypedQuery<ReverseGeocoding> query = entityManager.createQuery(hql, ReverseGeocoding.class);
        query.setParameter("lat", lat);
        query.setParameter("lon", lon);
        query.setMaxResults(1);
        List<ReverseGeocoding> res = query.getResultList();
        return res != null && res.size() > 0 ? res.get(0) : null;
    }
}
