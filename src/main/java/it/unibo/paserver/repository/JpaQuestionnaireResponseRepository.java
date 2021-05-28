package it.unibo.paserver.repository;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.QuestionnaireResponse;
import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Repository("questionnaireResponseRepository")
public class JpaQuestionnaireResponseRepository implements QuestionnaireResponseRepository {
    @PersistenceContext
    private EntityManager entityManager;
    // Query sendo executado no momento
    private String hsql;
    private Map<String, Object> hpars;

    @Override
    public QuestionnaireResponse saveOrUpdate(QuestionnaireResponse s) {
        
        if (s.getId() != null && s.getId() != 0) {
            return this.updated(s);
        } else {
            QuestionnaireResponse r = this.findByIds(s.getTaskId(), s.getActionId(), s.getQuestionId(), s.getUserId());
            if (r == null) {
                return this.saved(s);
            } else {
                String a = r.getResponse();
                String b = s.getResponse();
                if (r.isClosed() && !Validator.isEmptyString(a) && !Validator.isEmptyString(b) && !Validator.isStringEquals(a, b)) {
                    s.setResponse(a + " | " + b);
                }

                s.setId(r.getId());
                return this.updated(s);
            }
        }
    }

    @Override
    public QuestionnaireResponse updated(QuestionnaireResponse s) {
        s.setUpdateDate(new DateTime());
        return entityManager.merge(s);
    }

    @Override
    public QuestionnaireResponse saved(QuestionnaireResponse s) {
        s.setId(null);
        s.setCreationDate(new DateTime());
        s.setUpdateDate(new DateTime());
        entityManager.persist(s);
        return s;
    }

    @Override
    public QuestionnaireResponse findById(long id) {
        return entityManager.find(QuestionnaireResponse.class, id);
    }

    @Override
    public QuestionnaireResponse findByIds(Long taskId, Long actionId, Long questionId, Long userId) {
        String hql = "SELECT s FROM QuestionnaireResponse s "
                + " WHERE taskId=:taskId AND  actionId=:actionId AND questionId=:questionId AND  userId=:userId AND removed=0 ";
        TypedQuery<QuestionnaireResponse> query = entityManager.createQuery(hql, QuestionnaireResponse.class);
        query.setParameter("taskId", taskId);
        query.setParameter("actionId", actionId);
        query.setParameter("questionId", questionId);
        query.setParameter("userId", userId);
        query.setMaxResults(1);
        // return
        List<QuestionnaireResponse> s = query.getResultList();
        return s.size() == 1 ? s.get(0) : null;
    }

    @Override
    public List<Object[]> search(long taskId, long actionId, long userId, PageRequest pageable) {
        // SQL
        String hql = "SELECT r.id, r.taskid, r.actionid, r.questionid, r.answerid, r.isclosed, r.userid, r.response, u.name AS username, u.surname AS usersurname, u.officialemail AS userofficialemail, "
                + "  r.isphoto , date(r.creationdate), r.accuracy, r.latitude, r.longitude, r.altitude, r.provider, r.ipaddress "
                + " FROM actionquestionaire_response AS r "
                + " INNER JOIN user_accounts AS u ON u.id=r.userid"
                + " WHERE r.removed=0 AND r.taskid=:taskId AND r.actionid=:actionId ";
        if (userId > 0) {
            hql += " AND r.userid=:userId ";
        }
        // QUERY
        Query query = entityManager.createNativeQuery(hql);
        // Where
        query.setParameter("taskId", taskId);
        query.setParameter("actionId", actionId);
        if (userId > 0) {
            query.setParameter("userId", userId);
        }
        //limit
        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
        // Execute
        List<Object[]> res = query.getResultList();
        return res;
    }

    @Override
    public Long searchTotal(long taskId, long actionId) {
        
        // SQL
        String hql = "SELECT COUNT(*) AS total FROM QuestionnaireResponse AS r WHERE r.removed=:removed ";
        // Where
        boolean q = this.searchsetParameter(hql, taskId, actionId);
        // QUERY
        TypedQuery<Long> query = entityManager.createQuery(this.hsql, Long.class);
        // Where
        for (Entry<String, Object> pars : this.hpars.entrySet()) {
            query.setParameter(pars.getKey(), pars.getValue());
        }
        // Execute
        Long rs = query.getSingleResult();
        return (rs == null) ? 0 : rs;
    }

    @Override
    public boolean incrementClosedAnswer(Long questionId, Long answerId) {
        try {
            int updated = entityManager.createNativeQuery("UPDATE closedanswer SET amount=COALESCE(amount,0)+1 WHERE closed_answer_id=:answerId AND question_question_id=:questionId")
                    .setParameter("questionId", questionId)
                    .setParameter("answerId", answerId)
                    .executeUpdate();
            if (updated > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<QuestionnaireResponse> search(Long questionId, PageRequest pageable) {
        // SQL
        String hql = "SELECT r FROM QuestionnaireResponse AS r WHERE r.removed=0 AND r.questionId=:questionId  ";
        // QUERY
        Query query = entityManager.createQuery(hql, QuestionnaireResponse.class);
        // Where
        query.setParameter("questionId", questionId);
        //limit
        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
        // Execute
        List<QuestionnaireResponse> res = query.getResultList();
        return res;
    }

    /**
     * Adicionar os paramentros default da busca
     *
     * @param hql
     * @param taskId
     * @param actionId
     * @return
     */
    private boolean searchsetParameter(String hql, long taskId, long actionId) {
        // Dynamic
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("removed", false);

        if (taskId > 0) {
            hql += " AND r.taskId=:taskId";
            params.put("taskId", taskId);
        }

        if (actionId > 0) {
            hql += " AND r.actionId=:actionId";
            params.put("actionId", actionId);
        }
        // Set
        this.hsql = hql;
        this.hpars = params;
        return true;// Flag
    }
}