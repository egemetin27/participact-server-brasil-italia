package it.unibo.paserver.repository;

import it.unibo.paserver.domain.AudienceSelector;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.UserListTask;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"Duplicates", "JpaQlInspection"})
@Repository("userListTaskRepository")
public class JpaUserListTaskRepository implements UserListTaskRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserListTask saveOrUpdate(UserListTask t) {
        
        if (t.getId() != null && t.getId() != 0) {
            t.setUpdateDate(new DateTime());
            return entityManager.merge(t);
        } else {
            t.setId(null);
            t.setCreationDate(new DateTime());
            t.setUpdateDate(new DateTime());
            entityManager.persist(t);
            return t;
        }
    }

    @Override
    public List<UserListTask> findAll() {
        
        // HQL
        String hql = "SELECT i FROM UserListTask i WHERE removed=:removed";
        TypedQuery<UserListTask> query = entityManager.createQuery(hql, UserListTask.class).setParameter("removed", false);
        // return
        List<UserListTask> list = query.getResultList();
        return list;
    }

    @Override
    public UserListTask find(Long id) {
        
        return entityManager.find(UserListTask.class, id);
    }

    @Override
    public UserListTask findUserListTask(Long taskId) {
        
        // HQL
        String hql = "SELECT t FROM UserListTask t WHERE t.taskId.id=:TaskId AND removed=:removed";
        // stm
        TypedQuery<UserListTask> query = entityManager.createQuery(hql, UserListTask.class);
        query.setParameter("TaskId", taskId);
        query.setParameter("removed", false);
        // limit
        query.setMaxResults(1);
        // return
        List<UserListTask> rs = query.getResultList();
        return rs.size() == 1 ? rs.get(0) : null;
    }


    @Override
    public Task findUserTask(Long taskId) {
        
        //System.out.println(taskId.toString());
        return entityManager.find(Task.class, taskId);
    }


    @Override
    public Task findLastUpdate() {
        
        // HQL
        String hql = "SELECT t FROM Task t WHERE removed=:removed ORDER BY updateDate DESC";
        // stm
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class);
        query.setParameter("removed", false);
        // limit
        query.setMaxResults(1);
        // return
        List<Task> rs = query.getResultList();
        return rs.size() == 1 ? rs.get(0) : null;
    }

    @Override
    public Task findLastUpdateRemoved() {
        
        // HQL
        String hql = "SELECT t FROM Task t WHERE removed=:removed ORDER BY updateDate DESC";
        // stm
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class);
        query.setParameter("removed", true);
        // limit
        query.setMaxResults(1);
        // return
        List<Task> rs = query.getResultList();
        return rs.size() == 1 ? rs.get(0) : null;
    }

    @Override
    public List<Task> fetchAllAndPublic(DateTime updateDate, PageRequest pageable) {
        
        // HQL
        String hql = "SELECT t.taskId FROM UserListTask t "
                + " WHERE t.userListId.audienceSelector LIKE 'SELECTOR_ALL' "
                + " and t.taskId.start <= :startTime and t.taskId.deadline >= :endTime AND t.taskId.removed=:removed "
                + " AND t.removed=:removed  ";
        if (updateDate != null) {
            hql += " AND t.taskId.updateDate>=:updateDate ";
        }
        // stm
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class);
        query.setParameter("removed", false);
        DateTime now = DateTime.now().withZone(DateTimeZone.forID("UTC"));
        System.out.println(now.toString());
        query.setParameter("startTime", now);
        query.setParameter("endTime", now);
        if (updateDate != null) {
            query.setParameter("updateDate", updateDate.withZone(DateTimeZone.forID("UTC")));
        }
        // limit
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        // CATALINA.OUT JSON
        // System.out.println("fetchAllAndPublic X Y Z");
        // System.out.println(hql.toString());
        // return
        List<Task> rs = query.getResultList();
        return rs;
    }

    @Override
    public List<Task> fetchAllAndPublicPublish(DateTime updateDate, PageRequest pageable) {
        // HQL
        String hql = "SELECT t.taskId FROM UserListTask t "
                + " WHERE t.userListId.audienceSelector LIKE 'SELECTOR_ALL' "
                + " AND t.taskId.enabled=true "
                + " AND t.taskId.start <= :startTime and t.taskId.deadline >= :endTime AND t.taskId.removed=:removed AND t.taskId.isPublish=:isPublish "
                + " AND t.removed=:removed  ";
        if (updateDate != null) {
            hql += " AND t.taskId.updateDate>=:updateDate ";
        }

        // stm
        TypedQuery<Task> query = entityManager.createQuery(hql, Task.class);
        query.setParameter("removed", false);
        DateTime now = DateTime.now().withZone(DateTimeZone.forID("UTC"));
        System.out.println(now.toString());
        query.setParameter("startTime", now);
        query.setParameter("endTime", now);
        query.setParameter("isPublish", true);
        if (updateDate != null) {
            query.setParameter("updateDate", updateDate.withZone(DateTimeZone.forID("UTC")));
        }
        // limit
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        // CATALINA.OUT JSON
        System.out.println(hql.toString());
        // return
        List<Task> rs = query.getResultList();
        return rs;
    }

    @Override
    public List<Task> fetchAllAndClosed(Long userId, DateTime updateDate, PageRequest pageable) {
        DateTime now = DateTime.now().withZone(DateTimeZone.forID("UTC"));
        System.out.println(now.toString());
        // HQL
        String hql = "SELECT ta.task_id "
                + " FROM user_list_item AS li "
                + " INNER JOIN user_list AS l ON l.id=li.listid "
                + " INNER JOIN user_list_task AS lt ON lt.userlistid=l.id "
                + " INNER JOIN task AS ta ON ta.task_id=lt.taskid "
                + " WHERE li.userid='" + userId + "' AND l.audienceselector='SELECTOR_CLOSED' "
                + " AND ta.enabled=true "
                + " AND ta.start<='" + now.toString("yyyy-MM-dd HH:mm:ss") + "'"
                + " AND ta.deadline>='" + now.toString("yyyy-MM-dd HH:mm:ss") + "'"
                + " AND ta.removed=0  AND ta.ispublish=1 ";
        if (updateDate != null) {
            hql += " AND ta.updateDate>='" + updateDate.withZone(DateTimeZone.forID("UTC")).toString("yyyy-MM-dd HH:mm:ss") + "' ";
        }
        // STM
        Query query = entityManager.createNativeQuery(hql);
        // LIMIT
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        // CATALINA.OUT JSON
        System.out.println("fetchAllAndClosed");
        System.out.println(hql.toString());
        // return
        List<BigInteger> results = query.getResultList();
        List<Task> res = new ArrayList<Task>();
        for (BigInteger t : results) {
            res.add(entityManager.find(Task.class, t.longValue()));
        }
        return res;
    }

    @Override
    public List<UserListTask> fetchAllSelector(AudienceSelector audienceSelector, DateTime updateDate, PageRequest pageable) {
        // HQL
        String hql = "SELECT t FROM UserListTask t "
                + " WHERE t.userListId.audienceSelector LIKE :audienceSelector "
                + " AND t.taskId.enabled=true "
                + " and t.taskId.start <= :startTime and t.taskId.deadline >= :endTime "
                + " AND t.removed=:removed ";
        if (updateDate != null) {
            hql += " AND t.taskId.updateDate>=:updateDate ";
        }
        // stm
        TypedQuery<UserListTask> query = entityManager.createQuery(hql, UserListTask.class);
        query.setParameter("audienceSelector", audienceSelector);
        query.setParameter("removed", false);
        DateTime now = DateTime.now().withZone(DateTimeZone.forID("UTC"));
        System.out.println(now.toString());
        query.setParameter("startTime", now);
        query.setParameter("endTime", now);
        if (updateDate != null) {
            query.setParameter("updateDate", updateDate.withZone(DateTimeZone.forID("UTC")));
        }
        // limit
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        // CATALINA.OUT JSON
        System.out.println("fetchAllSelector");
        System.out.println(hql.toString());
        // return
        List<UserListTask> rs = query.getResultList();
        return rs;
    }
}