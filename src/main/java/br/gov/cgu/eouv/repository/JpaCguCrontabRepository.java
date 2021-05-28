package br.gov.cgu.eouv.repository;


import br.gov.cgu.eouv.domain.CguCrontab;
import org.joda.time.DateTime;
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
@Repository("jpaCguCrontabRepository")
public class JpaCguCrontabRepository implements CguCrontabRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CguCrontab saveOrUpdate(CguCrontab c) {
        if (c.getId() != null && c.getId() != 0 && find(c.getId()) != null) {
            c.setUpdatedate(new DateTime());
            return entityManager.merge(c);
        } else {
            CguCrontab st = this.first();
            if (st != null) {
                c.setId(st.getId());
                c.setUpdatedate(new DateTime());
            } else {
                c.setCreationdate(new DateTime());
                c.setUpdatedate(new DateTime());
                entityManager.persist(c);
            }
            return c;
        }
    }

    @Override
    public CguCrontab find(Long id) {
        return entityManager.find(CguCrontab.class, id);
    }

    @Override
    public CguCrontab first() {
        String hql = "SELECT c FROM CguCrontab c ORDER BY id DESC ";
        TypedQuery<CguCrontab> query = entityManager.createQuery(hql, CguCrontab.class);
        query.setMaxResults(1);
        List<CguCrontab> res = query.getResultList();
        return res != null && res.size() > 0 ? res.get(0) : null;
    }
}
