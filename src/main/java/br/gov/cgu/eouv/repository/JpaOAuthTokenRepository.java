package br.gov.cgu.eouv.repository;

import br.gov.cgu.eouv.domain.OAuthToken;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/07/2019
 **/
@Repository("jpaOAuthTokenRepository")
public class JpaOAuthTokenRepository implements OAuthTokenRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OAuthToken saveOrUpdate(OAuthToken o) {
        if (o.getId() != null) {
            o.setUpdateDate(new DateTime());
            return entityManager.merge(o);
        } else {
            o.setCreationDate(new DateTime());
            o.setUpdateDate(new DateTime());
            entityManager.persist(o);
            return o;
        }
    }

    @Override
    public OAuthToken getLastAvaliable() {
        String hql = "SELECT o FROM OAuthToken o WHERE removed=false ORDER BY id DESC";
        TypedQuery<OAuthToken> query = entityManager.createQuery(hql, OAuthToken.class);
        query.setMaxResults(1);
        List<OAuthToken> res = query.getResultList();
        return res.size() > 0 ? res.get(0) : null;
    }

    @Override
    public boolean removed(OAuthToken o) {
        
        try {
            OAuthToken rs = findById(o.getId());
            rs.setRemoved(true);
            entityManager.merge(rs);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public OAuthToken findById(Long id) {
        return entityManager.find(OAuthToken.class, id);
    }
}
