package it.unibo.paserver.repository;

import it.unibo.paserver.domain.DataSetNode;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Claudio
 * @project participact-server
 * @date 01/03/2019
 **/
@Repository("dataSetNodeRepository")
public class JpaDataSetNodeRepository implements DataSetNodeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DataSetNode saveOrUpdate(DataSetNode n) {
        if (n.getNodeId() != null && n.getNodeId() != 0) {
            n.setUpdateDate(new DateTime());
            return entityManager.merge(n);
        } else {
            n.setNodeId(null);
            n.setCreationDate(new DateTime());
            n.setUpdateDate(new DateTime());
            entityManager.persist(n);
            return n;
        }
    }
}
