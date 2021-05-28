package it.unibo.paserver.repository;

import br.com.bergmannsoft.utils.Validator;
import com.google.common.collect.ListMultimap;
import it.unibo.paserver.domain.IssueCategory;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("Duplicates")
@Repository("issueCategoryRepository")
public class JpaIssueCategoryRepository implements IssueCategoryRepository {
    @PersistenceContext
    private EntityManager entityManager;
    // Query sendo executado no momento
    private String hsql;
    private Map<String, Object> hpars;

    @Override
    public IssueCategory saveOrUpdate(IssueCategory ic) {
        
        if (ic.getId() != null && ic.getId() != 0) {
            ic.setUpdateDate(new DateTime());
            return entityManager.merge(ic);
        } else {
            ic.setId(null);
            ic.setCreationDate(new DateTime());
            ic.setUpdateDate(new DateTime());
            ic.setEditDate(new DateTime());
            entityManager.persist(ic);
            return ic;
        }
    }

    @Override
    public IssueCategory findById(long id) {
        
        IssueCategory ic = entityManager.find(IssueCategory.class, id);
        if (ic != null) {
            Hibernate.initialize(ic.getSubcategories());
        }
        return ic;
    }

    @Override
    public List<IssueCategory> findAll() {
        
        String hql = "SELECT ic FROM IssueCategory ic WHERE ic.removed=false ORDER BY ic.sequence ASC";
        TypedQuery<IssueCategory> query = entityManager.createQuery(hql, IssueCategory.class);
        List<IssueCategory> ic = query.getResultList();
        if (ic != null && ic.size() > 0) {
            for (IssueCategory ic_item : ic) {
                Hibernate.initialize(ic_item.getSubcategories());
            }
        }
        return ic;
    }

    @Override
    public Long getCount() {
        
        String hql = "SELECT COUNT(id) FROM IssueCategory ic";
        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
        return query.getSingleResult();
    }

    @Override
    public boolean removed(long id) {
        
        try {
            IssueCategory ic = findById(id);
            ic.setRemoved(true);
            ic.setUpdateDate(new DateTime());
            entityManager.merge(ic);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @SuppressWarnings("unused")
    @Override
    public boolean removeAll() {
        
        int icUpdated = entityManager.createNativeQuery("UPDATE issue_category SET removed=1 WHERE id > 0 ").executeUpdate();
        int isUpdated = entityManager.createNativeQuery("UPDATE issue_subcategory SET removed=1  WHERE id > 0 AND category_id > 0 ").executeUpdate();
        return false;
    }

    @Override
    public boolean reorder(long id, int index) {
        String sql = String.format("UPDATE issue_category SET sequence=%d WHERE id=%d ", index, id);
        System.out.println(sql);
        int icUpdated = entityManager.createNativeQuery(sql).executeUpdate();
        return icUpdated > 0;
    }

    @Override
    public List<Object[]> getTotalByCategory() {
        // SQL
        String hql = " SELECT ic.id AS category_id, ic.name AS category, COUNT (r.id) " +
                " FROM issue_report AS r " +
                " INNER JOIN issue_subcategory AS i ON r.subcategory_id = i.id " +
                " INNER JOIN issue_category AS ic ON i.category_id = ic.id " +
                " WHERE r.removed=0 AND i.removed=0 AND ic.removed=0 " +
                " GROUP BY ic.id, ic.name ";
        // QUERY
        // System.out.println(this.hsql);
        Query query = entityManager.createNativeQuery(hql);
        // Execute
        List<Object[]> res = query.getResultList();
        return res;
    }

    @Override
    public Long getTotalByCategoryDatePart(Long id, int year, int monthOfYear) {
        // SQL
        String hql = String.format(" SELECT COUNT (r.id) " +
                " FROM issue_report AS r " +
                " INNER JOIN issue_subcategory AS i ON r.subcategory_id = i.id " +
                " INNER JOIN issue_category AS ic ON i.category_id = ic.id " +
                " WHERE r.removed=0 AND i.removed=0 AND ic.removed=0 " +
                " AND ic.id=%d AND date_part('year',r.creationdate)=%d AND date_part('month',r.creationdate)='%02d' ", id, year, monthOfYear);
        // QUERY
        // System.out.println(hql);
        Query query = entityManager.createNativeQuery(hql);
        try {
            // Execute
            BigInteger res = (BigInteger) query.getSingleResult();
            return res.longValue();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return 0L;
    }

    @SuppressWarnings("unused")
    @Override
    public List<Object[]> search(ListMultimap<String, Object> conditions, PageRequest pageable) {
        // SQL
        String hql = "SELECT ic.id, ic.name, ic.description FROM IssueCategory ic WHERE ic.removed=:removed ";
        // Where
        boolean q = this.searchsetParameter(hql, conditions);
        // ORDER BY
        this.hsql = this.hsql + " ORDER BY ic.name ASC ";
        // QUERY
        // System.out.println(this.hsql);
        TypedQuery<Object[]> query = entityManager.createQuery(this.hsql, Object[].class);
        // Where
        for (Entry<String, Object> pars : this.hpars.entrySet()) {
            query.setParameter(pars.getKey(), pars.getValue());
            // //System.out.println(pars.getKey() +" <<>> "+ pars.getValue());
        }
        // limit
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        // Execute
        List<Object[]> res = query.getResultList();
        return res;
    }

    @SuppressWarnings("unused")
    @Override
    public List<IssueCategory> filter(ListMultimap<String, Object> conditions, PageRequest pageable) {
        // SQL
        String hql = "SELECT ic FROM IssueCategory ic WHERE ic.removed=:removed ";
        // Where
        boolean q = this.searchsetParameter(hql, conditions);
        // ORDER BY
        this.hsql = this.hsql + " ORDER BY ic.name ASC ";
        // QUERY
        System.out.println(this.hsql);
        TypedQuery<IssueCategory> query = entityManager.createQuery(this.hsql, IssueCategory.class);
        // Where
        for (Entry<String, Object> pars : this.hpars.entrySet()) {
            query.setParameter(pars.getKey(), pars.getValue());
            // //System.out.println(pars.getKey() +" <<>> "+ pars.getValue());
        }
        // limit
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        // Execute
        List<IssueCategory> res = query.getResultList();
        if (res.size() > 0) {
            for (IssueCategory ic_item : res) {
                Hibernate.initialize(ic_item.getSubcategories());
            }
        }
        return res;
    }

    @SuppressWarnings("unused")
    @Override
    public Long searchTotal(ListMultimap<String, Object> params) {
        // SQL
        String hql = "SELECT COUNT(DISTINCT ic.id) AS total FROM IssueCategory ic WHERE ic.removed=:removed ";
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

    private boolean searchsetParameter(String hql, ListMultimap<String, Object> conditions) {
        // Dynamic
        Map<String, Object> params = new HashMap<String, Object>();
        // Allow
        String[] haystack = {"name", "search", "updateDate"};
        // Fix
        params.put("removed", false);
        int count = 0;
        // Where
        if (conditions != null && conditions.size() > 0) {
            for (String k : conditions.keySet()) {
                // Allowed
                if (Validator.isValueinArray(haystack, k)) {
                    // Getter values
                    Collection<Object> values = conditions.get(k);
                    int size = values.size();
                    boolean parenthesis = values.size() > 1;
                    hql += (parenthesis) ? " AND ( " : " AND ";
                    for (Object v : values) {
                        if (k.equals("updateDate")) {
                            hql += " ic." + k + ">=:" + k + count + " ";
                            DateTime dateTime = DateTime.parse(v.toString());
                            params.put(k + count, dateTime);

                        } else if (k.equals("search")) {
                            hql += " UPPER(ic.name) LIKE:" + k + count + " ";
                            params.put(k + count, "%" + v.toString().toUpperCase() + "%");

                        } else {
                            hql += " UPPER(ic." + k + ") LIKE:" + k + count + " ";
                            params.put(k + count, "%" + v.toString().toUpperCase() + "%");
                        }
                        hql += (--size > 0) ? " OR " : "";
                        count++;
                    }
                    hql += (parenthesis) ? " ) " : " ";
                }
            }
        }
        // Set
        this.hsql = hql;
        this.hpars = params;
        return true;// Flag
    }
}