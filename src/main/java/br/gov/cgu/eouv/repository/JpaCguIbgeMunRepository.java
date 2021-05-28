package br.gov.cgu.eouv.repository;

import br.com.bergmannsoft.utils.Validator;
import br.gov.cgu.eouv.domain.CguIbgeMun;
import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 16/07/2019
 **/
@SuppressWarnings({"JpaQlInspection", "Duplicates"})
@Repository("jpaCguIbgeMunRepository")
public class JpaCguIbgeMunRepository implements CguIbgeMunRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CguIbgeMun saveOrUpdate(CguIbgeMun m) {
        if (m.getId() != null && m.getId() != 0 && find(m.getId()) != null) {
            m.setUpdatedate(new DateTime());
            return entityManager.merge(m);
        } else {
            m.setCreationdate(new DateTime());
            m.setUpdatedate(new DateTime());
            entityManager.persist(m);
            return m;
        }
    }

    @Override
    public CguIbgeMun find(Long id) {
        return entityManager.find(CguIbgeMun.class, id);
    }

    @Override
    public CguIbgeMun findByCodMun7(Long codMun7) {
        String hql = "SELECT m FROM CguIbgeMun m WHERE m.codmun7=:codMun7";
        TypedQuery<CguIbgeMun> query = entityManager.createQuery(hql, CguIbgeMun.class);
        query.setParameter("codMun7", codMun7.intValue());
        query.setMaxResults(1);
        List<CguIbgeMun> res = query.getResultList();
        return res != null && res.size() > 0 ? res.get(0) : null;
    }

    @Override
    public CguIbgeMun findByCodMun6(Long codMun6) {
        String hql = "SELECT m FROM CguIbgeMun m WHERE m.codmun6=:codMun6";
        TypedQuery<CguIbgeMun> query = entityManager.createQuery(hql, CguIbgeMun.class);
        query.setParameter("codMun6", codMun6.intValue());
        query.setMaxResults(1);
        List<CguIbgeMun> res = query.getResultList();
        return res != null && res.size() > 0 ? res.get(0) : null;
    }

    @Override
    public List<CguIbgeMun> fetchAll() {
        String hql = "SELECT m FROM CguIbgeMun m";
        TypedQuery<CguIbgeMun> query = entityManager.createQuery(hql, CguIbgeMun.class);
        List<CguIbgeMun> res = query.getResultList();
        return res;
    }

    @Override
    public List<CguIbgeMun> filter(String search, PageRequest pageable) {
        String hql = "SELECT m FROM CguIbgeMun m WHERE m.removed=false ";
        if (!Validator.isEmptyString(search)) {
            hql += " AND LOWER(m.nomemun) LIKE LOWER(CONCAT('%', :search,'%')) ";
        }
        System.out.println(hql);
        TypedQuery<CguIbgeMun> query = entityManager.createQuery(hql, CguIbgeMun.class);
        if (!Validator.isEmptyString(search)) {
            query.setParameter("search", search);
        }
        // Limit
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        // query.set
        List<CguIbgeMun> res = query.getResultList();
        return res;
    }

    @Override
    public Long searchTotal(String search) {
        String hql = "SELECT COUNT(DISTINCT m.id) AS total  FROM CguIbgeMun m WHERE m.removed=false ";
        if (!Validator.isEmptyString(search)) {
            hql += " AND LOWER(m.nomemun) LIKE LOWER(CONCAT('%', :search,'%')) ";
        }
        System.out.println(hql);
        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
        if (!Validator.isEmptyString(search)) {
            query.setParameter("search", search);
        }
        // Execute
        Long rs = query.getSingleResult();
        return (rs == null) ? 0 : rs;
    }
}
