package it.unibo.paserver.repository;

import it.unibo.paserver.domain.DataSetEdge;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Claudio
 * @project participact-server
 * @date 01/03/2019
 **/
@Repository("dataSetEdgeRepository")
public class JpaDataSetEdgeRepository implements DataSetEdgeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DataSetEdge saveOrUpdate(DataSetEdge e) {
        if (e.getEdgeId() != null && e.getEdgeId() != 0) {
            e.setUpdateDate(new DateTime());
            return entityManager.merge(e);
        } else {
            e.setEdgeId(null);
            e.setCreationDate(new DateTime());
            e.setUpdateDate(new DateTime());
            entityManager.persist(e);
            return e;
        }
    }

    @SuppressWarnings("JpaQueryApiInspection")
    @Override
    public boolean updateTargetNode(long fromId, int fromOrder, long targetId, int targetOrder) {
        try {
            int updated = entityManager
                    .createNativeQuery("UPDATE question SET target_id=:targetId , target_order=:targetOrder WHERE question_id=:fromId AND question_order=:fromOrder ")
                    .setParameter("targetId", targetId).setParameter("targetOrder", targetOrder).setParameter("fromId", fromId).setParameter("fromOrder", fromOrder)
                    .executeUpdate();
            if (updated > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @SuppressWarnings("JpaQueryApiInspection")
    @Override
    public boolean updateTargetLeaf(long fromId, long leafId, int leafOrder, long targetId, int targetOrder) {
        try {
            int updated = entityManager
                    .createNativeQuery("UPDATE closedanswer SET target_id=:targetId, target_order=:targetOrder WHERE closed_answer_id=:leafId AND answerorder=:leafOrder AND question_question_id=:fromId ")
                    .setParameter("targetId", targetId).setParameter("targetOrder", targetOrder)
                    .setParameter("leafId", leafId).setParameter("leafOrder", leafOrder).setParameter("fromId", fromId)
                    .executeUpdate();
            if (updated > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        }
        return false;
    }
}
