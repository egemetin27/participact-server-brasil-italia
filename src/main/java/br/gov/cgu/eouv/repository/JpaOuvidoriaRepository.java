package br.gov.cgu.eouv.repository;

import br.gov.cgu.eouv.domain.Ouvidoria;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/04/2019
 **/
@SuppressWarnings("JpaQlInspection")
@Repository("jpaOuvidoriaRepository")
public class JpaOuvidoriaRepository implements OuvidoriaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Ouvidoria saveOrUpdate(Ouvidoria o) {
        if (o.getIdOuvidoria() != null && o.getIdOuvidoria() != 0 && find(o.getIdOuvidoria()) != null) {
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
    public Ouvidoria find(Long codOrg) {
        return entityManager.find(Ouvidoria.class, codOrg);
    }

    @Override
    public List<Ouvidoria> fetchAll() {
        String hql = "SELECT o FROM Ouvidoria o ORDER BY o.hasAllowOmbudsman DESC, o.nomeOrgaoOuvidoria ASC";
        TypedQuery<Ouvidoria> query = entityManager.createQuery(hql, Ouvidoria.class);
        List<Ouvidoria> res = query.getResultList();
        return res;
    }

    @Override
    public List<Ouvidoria> filter(String search) {
        String hql = "SELECT o FROM Ouvidoria o WHERE LOWER(o.nomeOrgaoOuvidoria) LIKE LOWER(CONCAT('%', :search,'%'))";
        TypedQuery<Ouvidoria> query = entityManager.createQuery(hql, Ouvidoria.class);
        query.setParameter("search", search);
        query.setMaxResults(10);
        List<Ouvidoria> res = query.getResultList();
        return res;
    }

    @Override
    public boolean removeAll() {
        try {
            Query query = entityManager.createNativeQuery("UPDATE cgu_ouvidorias_v4 SET updatedate=NOW(), removed=true WHERE removed=false AND idouvidoria > 0 ");
            int numberUpdated = query.executeUpdate();
            return (numberUpdated > 0);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return false;
    }

    @SuppressWarnings("JpaQueryApiInspection")
    @Override
    public List<Object[]> fetchAllowOmbudsmansOffice(String mun) {
        String hql = "SELECT c.idouvidoria, c.idmunicipio, m.codmun7, m.codmun6 "
                + " , (SELECT COUNT(r.issue_subcategory_id) FROM issue_subcategory_has_relationship r WHERE r.relationship_id=c.idouvidoria) AS rel "
                + " , c.nomeorgaoouvidoria "
                + " FROM cgu_ibge_mun m INNER JOIN cgu_ouvidorias_v4 c ON c.idmunicipio=m.codmun6 "
                + " WHERE m.removed=false AND c.removed=false AND c.hasallowombudsman=true "
                + " AND m.nomemun ILIKE UNACCENT ('" + mun + "') " +
                " ORDER BY rel ASC ";

        Query query = entityManager.createNativeQuery(hql);
        List<Object[]> res = query.getResultList();
        return res.size() > 0 ? res : null;
    }
}
