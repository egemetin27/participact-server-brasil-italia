package it.unibo.paserver.repository;

import br.com.bergmannsoft.utils.Validator;
import com.google.common.collect.ListMultimap;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.TaskValutation;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("Duplicates")
@Repository("taskRepository")
public class JpaTaskRepository implements TaskRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaTaskRepository.class);
    @PersistenceContext
    private EntityManager entityManager;
    private String hsql;
    private Map<String, Object> hpars;

    @Override
    public Task findById(long id) {
        return entityManager.find(Task.class, id);
    }

    @Override
    public Task findByIdAndDetach(long id) {
        
        Task clone = this.findById(id);
        entityManager.detach(clone);
        clone.setId(null);
        return clone;
    }

    @Override
    public Task save(Task task) {
        task.setUpdateDate(new DateTime());
        if (task.getId() != null && task.getId() != 0) {
            logger.trace("Merging task {}", task.toString());
            Task t = entityManager.merge(task);
            entityManager.flush();
            return t;
        } else {
            task.setId(null);
            task.setCreationDate(new DateTime());
            logger.trace("Persisting task {}", task.toString());
            entityManager.persist(task);
            return task;
        }
    }

    @SuppressWarnings("JpaQlInspection")
    @Override
    public Long getTasksCount() {
        String hql = "SELECT count(id) FROM Task WHERE t.removed=false ";
        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
        return query.getSingleResult();
    }

    @Override
    public Long getTasksCount(long userId) {
        String hql = "SELECT count(t.task.id) FROM TaskReport t WHERE t.user.id=:userId" + "  AND t.task.removed=false ";
        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class).setParameter("userId", userId);
        return query.getSingleResult();
    }

    @Override
    public List<Task> getTasks() {
        String hql = "SELECT t FROM Task t WHERE t.removed=false ";
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class);
        return query.getResultList();
    }

    @Override
    public List<Task> getTasksByUser(long userId) {
        String hql = "SELECT t.task FROM TaskReport t WHERE t.user.id=:userId" + " AND t.task.removed=false ";
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class).setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Task> getTasksByAction(long actionId) {
        String hql = "SELECT t FROM Task t JOIN t.actions a WHERE a.id=:actionId AND t.removed=false ";
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class).setParameter("actionId", actionId);
        return query.getResultList();
    }

    @Override
    public boolean deleteTask(long id) {
        Task task = findById(id);
        try {
            if (task != null) {
                entityManager.remove(task);
                return true;
            } else {
                logger.warn("Unable to find task {}", id);
            }
        } catch (Exception e) {
            logger.info("Exception: {}", e);
        }
        return false;
    }

    @Override
    public List<Task> getAvailableTasksByUser(long userId, TaskState taskCurrentState, DateTime dateTime) {
        String hql = "SELECT t.task " + " FROM TaskReport t " + " WHERE t.user.id=:userId and t.currentState=:taskstate and t.task.start <= :startTime and t.task.deadline >= :endTime AND t.task.removed=false";
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class).setParameter("userId", userId).setParameter("taskstate", taskCurrentState).setParameter("startTime", dateTime).setParameter("endTime", dateTime);
        List<Task> result = query.getResultList();
        logger.debug("Search for user {}, target time: {}: {} hits", userId, dateTime, result.size());
        return result;
    }

    @Override
    public List<Task> getTasksByOwner(long ownerId) {
        String hql = "SELECT t.task FROM TaskUser t WHERE t.owner.id=:ownerId  AND t.task.removed=false ";
        List<Task> result = entityManager.createQuery(hql, Task.class).setParameter("ownerId", ownerId).getResultList();
        return result;
    }

    @Override
    public Task findByIdAndOwner(Long taskId, Long ownerId) {
        String hql = "SELECT t.task FROM TaskUser t WHERE t.task.id=:taskId and t.owner.id=:ownerId  AND t.task.removed=false ";
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class).setParameter("ownerId", ownerId).setParameter("taskId", taskId);
        return query.getSingleResult();
    }

    @Override
    public List<Task> getTasksByOwner(long ownerId, TaskValutation currentTaskValutation) {
        String hql = "SELECT t.task FROM TaskUser t  WHERE t.owner.id=:ownerId and t.valutation=:currentTaskValutation AND t.task.removed=false ";

        List<Task> result = entityManager.createQuery(hql, Task.class).setParameter("ownerId", ownerId).setParameter("currentTaskValutation", currentTaskValutation).getResultList();
        return result;
    }

    @Override
    public List<Task> getTaskByAdmin() {
        String hql = "SELECT t FROM Task t WHERE t.id not in (SELECT tu.task.id FROM TaskUser tu) AND t.removed=false ";
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class);
        return query.getResultList();
    }

    @Override
    public List<Task> getAvailableAdminTasksByUser(Long id, TaskState taskState, DateTime dateTime) {
        String hql = "SELECT t.task FROM TaskReport t " + " WHERE t.task.id not in (SELECT tu.task.id FROM TaskUser tu) and t.user.id=:userId and t.currentState=:taskstate and t.task.start <= :startTime and t.task.deadline >= :endTime"
                + "  AND t.task.removed=false ";
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class).setParameter("userId", id).setParameter("taskstate", taskState).setParameter("startTime", dateTime).setParameter("endTime", dateTime);
        List<Task> result = query.getResultList();
        logger.debug("Search for user {}, target time: {}: {} hits", id, dateTime, result.size());
        return result;
    }

    @Override
    public List<Task> getAvailableUserTasksByUser(Long id, TaskState taskState, DateTime dateTime) {
        String hql = "SELECT t.task FROM TaskReport t " + " WHERE t.task.id in (SELECT tu.task.id FROM TaskUser tu) and t.user.id=:userId and t.currentState=:taskstate and t.task.start <= :startTime and t.task.deadline >= :endTime"
                + "  AND t.task.removed=false ";
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class).setParameter("userId", id).setParameter("taskstate", taskState).setParameter("startTime", dateTime).setParameter("endTime", dateTime);
        List<Task> result = query.getResultList();
        logger.debug("Search for user {}, target time: {}: {} hits", id, dateTime, result.size());
        return result;
    }

    @Override
    public List<Task> getTasksByUser(long userId, TaskState taskCurrentState) {
        String hql = "SELECT t.task " + " FROM TaskReport t " + " WHERE t.user.id=:userId AND t.currentState=:taskstate" + "  AND t.task.removed=false ";
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class).setParameter("userId", userId).setParameter("taskstate", taskCurrentState);
        return query.getResultList();
    }

    @Override
    public List<Task> getAdminTasksByUser(Long userId, TaskState taskState) {
        String hql = "SELECT t.task FROM TaskReport t " + " WHERE t.task.id not in (SELECT tu.task.id FROM TaskUser tu) and t.user.id=:userId and t.currentState=:taskstate" + "  AND t.task.removed=false ";
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class).setParameter("userId", userId).setParameter("taskstate", taskState);
        List<Task> result = query.getResultList();
        logger.debug("Search for user {}, target time: {}: {} hits", userId, result.size());
        return result;
    }

    @Override
    public List<Task> getUserTasksByUser(Long userId, TaskState taskState) {
        String hql = "SELECT t.task " + " FROM TaskReport t " + " WHERE t.task.id in (SELECT tu.task.id FROM TaskUser tu) " + " AND t.user.id=:userId " + " AND t.currentState=:taskstate";
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class).setParameter("userId", userId).setParameter("taskstate", taskState);
        List<Task> result = query.getResultList();
        logger.debug("Search for user {}, target time: {}: {} hits", userId, result.size());
        return result;
    }

    @Override
    public List<Task> getTasksByDate(DateTime start, DateTime end) {
        String hql = "SELECT t FROM Task t WHERE t.start >= :start and t.start < :end  AND t.removed=false ";
        List<Task> result = entityManager.createQuery(hql, Task.class).setParameter("start", start).setParameter("end", end).getResultList();
        return result;
    }

    @Override
    public List<Task> getTasksUserByDate(DateTime start, DateTime end) {
        @SuppressWarnings("JpaQlInspection")
        String hql = "SELECT t.task FROM TaskUser t WHERE t.task.start >= :start and t.task.start < :end  AND t.removed=false ";
        List<Task> result = entityManager.createQuery(hql, Task.class).setParameter("start", start).setParameter("end", end).getResultList();
        return result;
    }

    @Override
    public List<Task> getTaskByAdmin(DateTime start, DateTime end) {
        String hql = "SELECT t FROM Task t WHERE t.id not in (SELECT tu.task.id FROM TaskUser tu) and t.start >= :start and t.start < :end  AND t.removed=false ";
        List<Task> result = entityManager.createQuery(hql, Task.class).setParameter("start", start).setParameter("end", end).getResultList();
        return result;
    }

    /**
     * Busca pelo id e parentid
     */
    @Override
    public Task findByIdAndParentId(long id, long parentId) {
        // HQL
        String hql = "SELECT t FROM Task t WHERE t.removed=:removed AND t.id=:id";
        // Dynamic
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("removed", false);
        params.put("id", id);
        // Somente de um usuario?
        if (parentId != 0) {
            hql += " AND t.parentId=:parentId";
            params.put("parentId", parentId);
        }
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class);
        // Where
        for (Entry<String, Object> pars : params.entrySet()) {
            query.setParameter(pars.getKey(), pars.getValue());
        }
        // limit
        query.setMaxResults(1);
        // return
        List<Task> t = query.getResultList();
        return t.size() == 1 ? t.get(0) : null;
    }

    @Override
    public Task findByQrCode(String token) {
        // HQL
        String hql = "SELECT t FROM Task t WHERE t.removed=:removed AND t.inviteQrCodeToken=:token";
        // Dynamic
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("removed", false);
        params.put("token", token);
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class);
        // Where
        for (Entry<String, Object> pars : params.entrySet()) {
            query.setParameter(pars.getKey(), pars.getValue());
        }
        // limit
        query.setMaxResults(1);
        // return
        List<Task> t = query.getResultList();
        return t.size() == 1 ? t.get(0) : null;
    }

    @Override
    public List<Task> fetchAllByRemoved(PageRequest pageable, DateTime updateDate) {
        // HQL
        String hql = "SELECT t FROM Task t WHERE t.removed=:removed";
        if (updateDate != null) {
            hql += " AND t.updateDate>=:updateDate ";
        }
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class);
        // Where
        query.setParameter("removed", true);
        if (updateDate != null) {
            query.setParameter("updateDate", updateDate);
        }
        // limit
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        // CATALINA.OUT JSON
        System.out.println("fetchAllByRemoved");
        System.out.println(hql.toString());
        // return
        List<Task> t = query.getResultList();
        return query.getResultList();
    }

    /**
     * Desabilida um item
     */
    @Override
    public boolean removed(long id) {
        
        try {
            Task t = findById(id);
            t.setRemoved(true);
            entityManager.merge(t);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Busca dinamica
     */
    @SuppressWarnings("unused")
    @Override
    public List<Object[]> search(ListMultimap<String, Object> params, String sortField, boolean ascending, PageRequest pageable) {
        // SQL
        String hql = "SELECT t.id, t.name, t.description, t.start, t.deadline, t.duration, t.sensingDuration , t.isDuration, t.isPublish, "
                + " CASE WHEN  t.deadline>NOW() THEN true ELSE false END AS isExpired, t.canBeRefused, t.removed, t.isAssign, t.isSelectAll, " + " CASE WHEN LENGTH(t.notificationArea) > 0 THEN true ELSE false END AS isNotification, "
                + " CASE WHEN  LENGTH(t.activationArea) > 0 THEN true ELSE false END AS isActivation, " + " COUNT(item) AS hasSensor,"
                + " t.parentId , t.sensingWeekSun, t.sensingWeekMon, t.sensingWeekTue, t.sensingWeekWed, t.sensingWeekThu, t.sensingWeekFri, t.sensingWeekSat, "
                + " t.inviteQrCodeToken, t.color, t.iconUrl, t.enabled "
                + " FROM Task t LEFT JOIN t.actions item " + " WHERE t.removed=:removed ";
        // Where
        boolean q = this.searchsetParameter(hql, params);
        // GROUP
        this.hsql = this.hsql + " GROUP BY t.id ";
        // ORDER BY
        String sort = ascending ? " DESC " : " ASC ";
        String cols = " ORDER BY t.isPublish ASC,  isExpired DESC, t.name ";
        if (sortField.equals("Name")) {
            cols = " ORDER BY t.name ";
        } else if (sortField.equals("Dates")) {
            cols = " ORDER BY t.creationDate ";
        } else if (sortField.equals("Durations")) {
            cols = " ORDER BY t.sensingDuration ";
        } else if (sortField.equals("Actions")) {
            cols = " ORDER BY t.isAssign ";
        }

        this.hsql = this.hsql + cols + sort;
        // this.hsql = this.hsql + " ORDER BY t.isPublish , isExpired ASC ";
        System.out.println(this.hsql.toString());
        // QUERY
        TypedQuery<Object[]> query = entityManager.createQuery(this.hsql, Object[].class);
        // Where
        for (Entry<String, Object> pars : this.hpars.entrySet()) {
            query.setParameter(pars.getKey(), pars.getValue());
        }
        // limit
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        // Execute
        List<Object[]> u = query.getResultList();
        return u;
    }

    /**
     * Retorna a lista em forma de objecto
     */
    @Override
    public List<Task> searchList(ListMultimap<String, Object> params, PageRequest pageable) {
        // SQL
        String hql = "SELECT t FROM Task t LEFT JOIN t.actions item WHERE t.removed=:removed ";
        // Where
        boolean q = this.searchsetParameter(hql, params);
        // QUERY
        TypedQuery<Task> query = entityManager.createQuery(this.hsql, Task.class);
        // Where
        for (Entry<String, Object> pars : this.hpars.entrySet()) {
            query.setParameter(pars.getKey(), pars.getValue());
        }
        // limit
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        // Execute
        return query.getResultList();
    }

    /**
     * Total de uma consulta dinamica
     */
    @SuppressWarnings("unused")
    @Override
    public Long searchTotal(ListMultimap<String, Object> params) {
        // SQL
        String hql = "SELECT COUNT(DISTINCT t.id) AS total FROM Task t LEFT JOIN t.actions item WHERE t.removed=:removed ";
        // Where
        boolean q = this.searchsetParameter(hql, params);
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

    /**
     * Monta o HQL de acordo com os paramentros passados
     *
     * @param hql
     * @param conditions
     * @return
     */
    private boolean searchsetParameter(String hql, ListMultimap<String, Object> conditions) {
        // Especiais
        String[] haystack = {"isPublish", "parentId", "start", "deadline", "canBeRefused", "pipelineType", "id", "hasPhotos", "hasQuestionnaire"};
        String[] hayStart = {"start"};
        String[] hayEnd = {"deadline"};
        String[] haySensing = {"pipelineType"};
        String[] hayReport = {"taskId"};
        // Dynamic
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("removed", false);
        // Where
        if (conditions != null && conditions.size() > 0) {
            int count = 0;
            for (String k : conditions.keySet()) {
                // Getter values
                Collection<Object> values = conditions.get(k);
                // mais de um?
                boolean operator = !Validator.isValueinArray(haystack, k);
                boolean isStart = Validator.isValueinArray(hayStart, k);
                boolean isEnd = Validator.isValueinArray(hayEnd, k);
                boolean isPipelineType = Validator.isValueinArray(haySensing, k);
                boolean isReport = Validator.isValueinArray(hayReport, k);

                int size = values.size();
                boolean parenthesis = values.size() > 1;
                hql += (parenthesis) ? " AND ( " : " AND ";
                for (Object v : values) {
                    if (operator) {
                        hql += "  UPPER(t." + k + ") LIKE:" + k + count + " ";
                        params.put(k + count, "%" + v.toString().toUpperCase() + "%");
                    } else if (isPipelineType) {
                        hql += " TYPE(item) IN:" + k + count + " AND item.input_type=" + v + " ";
                        params.put(k + count, ActionSensing.class);
                    } else {
                        if (isStart) {
                            hql += " t." + k + ">=:" + k + count + " ";
                            v = new DateTime(v);
                        } else if (isEnd) {
                            hql += " t." + k + "<=:" + k + count + " ";
                            v = new DateTime(v);
                        } else if (isReport) {
                            hql += " tr.task.id." + k + "=:" + k + count + " ";
                        } else {
                            hql += " t." + k + "=:" + k + count + " ";
                        }
                        params.put(k + count, v);
                    }
                    hql += (--size > 0) ? " OR " : "";
                    count++;
                }
                hql += (parenthesis) ? " ) " : " ";
            }
        }
        // Set
        this.hsql = hql;
        this.hpars = params;

        return true;// Flag
    }

    /**
     * Total por estado de atividades de um usuario
     */
    @Override
    public List<Object[]> getTotalByUserAndState(long userId, DateTime startDate, DateTime endDate) {
        // SQL
        String hql = "SELECT MONTH(tr.creationDate), YEAR(tr.creationDate), tr.currentState, COUNT(tr.id) AS total FROM TaskReport tr " + " WHERE tr.user.id=:userId AND tr.creationDate BETWEEN :startDate AND :endDate "
                + " GROUP BY MONTH(tr.creationDate), YEAR(tr.creationDate), tr.currentState";
        // QUERY
        TypedQuery<Object[]> query = entityManager.createQuery(hql, Object[].class);
        query.setParameter("userId", userId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        // Execute
        List<Object[]> rs = query.getResultList();
        return rs;
    }

    /**
     * Total por estado em uma tarefa
     */
    @Override
    public List<Object[]> getTotalByState(long taskId) {
        // SQL
        String hql = "SELECT tr.currentState, COUNT(tr.id) AS total FROM TaskReport tr WHERE tr.task.id=:taskId GROUP BY tr.currentState";
        // QUERY
        TypedQuery<Object[]> query = entityManager.createQuery(hql, Object[].class);
        query.setParameter("taskId", taskId);
        // Execute
        List<Object[]> rs = query.getResultList();
        return rs;
    }

    @Override
    public List<Object[]> getTotalByState(ListMultimap<String, Object> params) {
        
        // SQL
        String hql = "SELECT tr.currentState, COUNT(tr.id) AS total " + " FROM Task t INNER JOIN  t.taskReport tr" + " LEFT JOIN t.actions item " + " WHERE  t.removed=:removed ";
        // WHERE
        boolean q = this.searchsetParameter(hql, params);
        // GROUP BY
        this.hsql = this.hsql + " GROUP BY tr.currentState";
        // QUERY
        TypedQuery<Object[]> query = entityManager.createQuery(this.hsql, Object[].class);
        // WHERE
        for (Entry<String, Object> pars : this.hpars.entrySet()) {
            query.setParameter(pars.getKey(), pars.getValue());
        }
        // Execute
        return query.getResultList();
    }

    @Override
    public List<Object[]> getTotalByStateAndTask(ListMultimap<String, Object> params) {
        
        // SQL
        String hql = "SELECT tr.currentState, COUNT(tr.id) AS total" + " FROM Task t INNER JOIN  t.taskReport tr" + " WHERE  t.removed=:removed ";
        // WHERE
        boolean q = this.searchsetParameter(hql, params);
        // GROUP BY
        this.hsql = this.hsql + " GROUP BY tr.currentState";
        // QUERY
        TypedQuery<Object[]> query = entityManager.createQuery(this.hsql, Object[].class);
        // WHERE
        for (Entry<String, Object> pars : this.hpars.entrySet()) {
            query.setParameter(pars.getKey(), pars.getValue());
        }
        // Execute
        return query.getResultList();
    }

    @Override
    public List<Object[]> searchWpPublishPage(String search, PageRequest pageable) {
        
        // SQL
        String hql = "SELECT t.id, t.name, t.color, t.iconUrl, t.wpPostDescription, t.description, t.creationDate, t.wpPublishPage  " +
                " FROM Task t " +
                " WHERE  t.removed=:removed AND t.wpPublishPage=:wppublishpage AND t.isPublish=:ispublish AND t.enabled=:enabled ";
        if (!Validator.isEmptyString(search)) {
            hql = hql + " AND lower(t.name) LIKE lower(:search)";
        }

        // QUERY
        TypedQuery<Object[]> query = entityManager.createQuery(hql, Object[].class);
        query.setParameter("removed", false);
        query.setParameter("wppublishpage", true);
        query.setParameter("ispublish", true);
        query.setParameter("enabled", true);
        if (!Validator.isEmptyString(search)) {
            query.setParameter("search", "%" + search + "%");
        }
        System.out.println(hql);
        // limit
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        // Execute
        List<Object[]> rs = query.getResultList();
        return rs;
    }
}
