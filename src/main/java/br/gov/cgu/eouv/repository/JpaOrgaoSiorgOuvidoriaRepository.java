package br.gov.cgu.eouv.repository;

import br.gov.cgu.eouv.domain.OrgaoSiorgOuvidoria;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 09/04/2019
 **/
@Repository("jpaOrgaoSiorgOuvidoriaRepository")
public class JpaOrgaoSiorgOuvidoriaRepository implements OrgaoSiorgOuvidoriaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OrgaoSiorgOuvidoria saveOrUpdate(OrgaoSiorgOuvidoria o) {
        if (o.getCodOrg() != null && o.getCodOrg() != 0 && find(o.getCodOrg()) != null) {
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
    public OrgaoSiorgOuvidoria find(Long codOrg) {
        return entityManager.find(OrgaoSiorgOuvidoria.class, codOrg);
    }

    @Override
    public List<OrgaoSiorgOuvidoria> fetchAll() {
        String hql = "SELECT o FROM OrgaoSiorgOuvidoria o";
        TypedQuery<OrgaoSiorgOuvidoria> query = entityManager.createQuery(hql, OrgaoSiorgOuvidoria.class);
        List<OrgaoSiorgOuvidoria> res = query.getResultList();
        return res;
    }

    @Override
    public List<OrgaoSiorgOuvidoria> filter(String search) {
        String hql = "SELECT o FROM OrgaoSiorgOuvidoria o WHERE LOWER(o.nomOrgao) LIKE LOWER(CONCAT('%', :search,'%'))";
        TypedQuery<OrgaoSiorgOuvidoria> query = entityManager.createQuery(hql, OrgaoSiorgOuvidoria.class);
        query.setParameter("search", search);
        query.setMaxResults(10);
        List<OrgaoSiorgOuvidoria> res = query.getResultList();
        return res;
    }
}
