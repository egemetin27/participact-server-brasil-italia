package it.unibo.paserver.repository;

import br.com.bergmannsoft.utils.Validator;
import com.google.common.collect.ListMultimap;
import it.unibo.paserver.domain.IssueSubCategory;
import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings({"Duplicates", "JpaQlInspection"})
@Repository("issueSubCategoryRepository")
public class JpaIssueSubCategoryRepository implements IssueSubCategoryRepository {
    @PersistenceContext
    private EntityManager entityManager;
    // Query sendo executado no momento
    private String hsql;
    private Map<String, Object> hpars;

    @Override
    public IssueSubCategory saveOrUpdate(IssueSubCategory is) {
        
        if (is.getId() != null && is.getId() != 0) {
            is.setUpdateDate(new DateTime());
            if (is.getCreationDate() == null) {
                is.setCreationDate(new DateTime());
            }
            return entityManager.merge(is);
        } else {
            is.setId(null);
            is.setCreationDate(new DateTime());
            is.setUpdateDate(new DateTime());
            entityManager.persist(is);
            return is;
        }
    }

    @Override
    public IssueSubCategory findById(long id) {
        
        return entityManager.find(IssueSubCategory.class, id);
    }

    @Override
    public List<IssueSubCategory> findAll() {
        
        String hql = "SELECT s FROM IssueSubCategory s WHERE s.removed=0 ";
        TypedQuery<IssueSubCategory> query = entityManager.createQuery(hql, IssueSubCategory.class);
        List<IssueSubCategory> rs = query.getResultList();
        return rs;
    }

    @Override
    public Long getCount() {
        
        String hql = "SELECT COUNT(id) FROM IssueSubCategory is";
        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
        return query.getSingleResult();
    }

    @Override
    public boolean removed(long id) {
        
        try {
            IssueSubCategory is = findById(id);
            is.setRemoved(true);
            is.setUpdateDate(new DateTime());
            entityManager.merge(is);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean reorder(long id, int index) {
        String sql = String.format("UPDATE issue_subcategory SET sequence=%d WHERE id=%d ", index, id);
        System.out.println(sql);
        int icUpdated = entityManager.createNativeQuery(sql).executeUpdate();
        return icUpdated > 0;
    }

    @Override
    public List<Object[]> getTotalBySubCategory() {
        // SQL
        String hql = " SELECT r.subcategory_id, i.name AS subcategory, COUNT (r.id) " +
                " FROM issue_report AS r " +
                " INNER JOIN issue_subcategory AS i ON r.subcategory_id = i.id " +
                " WHERE r.removed=0 AND i.removed=0 " +
                " GROUP BY r.subcategory_id, i.name ";
        // QUERY
        // System.out.println(this.hsql);
        Query query = entityManager.createNativeQuery(hql);
        // Execute
        List<Object[]> res = query.getResultList();
        return res;
    }

    @SuppressWarnings("JpaQueryApiInspection")
    @Override
    public boolean removeAll(Long categoryId) {
        try {
            Query query = entityManager.createNativeQuery("UPDATE issue_subcategory SET updatedate=NOW(), removed=1 WHERE category_id=:categoryId");
            query.setParameter("categoryId", categoryId);
            query.setMaxResults(1);
            int numberUpdated = query.executeUpdate();
            return (numberUpdated > 0);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return false;
    }

    @SuppressWarnings("unused")
    @Override
    public List<Object[]> search(ListMultimap<String, Object> conditions, PageRequest pageable) {
        // SQL
        String hql = "SELECT s.id, s.name, s.description, s.urlAsset ,s.category.name AS category_name, s.category.color AS category_color " +
                " FROM IssueSubCategory s " +
                " WHERE s.removed=:removed ";
        // Where
        boolean q = this.searchsetParameter(hql, conditions);
        // ORDER BY
        this.hsql = this.hsql + " ORDER BY s.name ASC ";
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
    public List<IssueSubCategory> filter(ListMultimap<String, Object> conditions, PageRequest pageable) {
        // SQL
        String hql = "SELECT s FROM IssueSubCategory s WHERE s.removed=:removed ";
        // Where
        boolean q = this.searchsetParameter(hql, conditions);
        // ORDER BY
        this.hsql = this.hsql + " ORDER BY s.sequence ASC ";
        // QUERY
        System.out.println(this.hsql);
        TypedQuery<IssueSubCategory> query = entityManager.createQuery(this.hsql, IssueSubCategory.class);
        // Where
        for (Entry<String, Object> pars : this.hpars.entrySet()) {
            query.setParameter(pars.getKey(), pars.getValue());
            // //System.out.println(pars.getKey() +" <<>> "+ pars.getValue());
        }
        // limit
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        // Execute
        List<IssueSubCategory> res = query.getResultList();
        return res;
    }

    @SuppressWarnings("unused")
    @Override
    public Long searchTotal(ListMultimap<String, Object> params) {
        // SQL
        String hql = "SELECT COUNT(DISTINCT s.id) AS total FROM IssueSubCategory s WHERE s.removed=:removed ";
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
        String[] haystack = {"name", "search", "updateDate", "categoryId", "parentId", "level"};
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
                            hql += " s." + k + ">=:" + k + count + " ";
                            DateTime dateTime = DateTime.parse(v.toString());
                            params.put(k + count, dateTime);

                        } else if (k.equals("categoryId")) {
                            hql += " s.category.id=:" + k + count + " ";
                            params.put(k + count, v);

                        } else if (k.equals("parentId")) {
                            hql += " s.parentId=:" + k + count + " ";
                            params.put(k + count, v);

                        } else if (k.equals("level")) {
                            hql += " s.level=:" + k + count + " ";
                            params.put(k + count, v);

                        } else if (k.equals("search")) {
                            hql += " UPPER(s.name) LIKE:" + k + count + " ";
                            params.put(k + count, "%" + v.toString().toUpperCase() + "%");

                        } else {
                            hql += " UPPER(s." + k + ") LIKE:" + k + count + " ";
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