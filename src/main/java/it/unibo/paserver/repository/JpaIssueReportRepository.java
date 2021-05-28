package it.unibo.paserver.repository;

import br.com.bergmannsoft.utils.Validator;
import com.google.common.collect.ListMultimap;
import it.unibo.paserver.domain.IssueReport;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("Duplicates")
@Repository("issueReportRepository")
public class JpaIssueReportRepository implements IssueReportRepository {
    @PersistenceContext
    private EntityManager entityManager;
    // Query sendo executado no momento
    private String hsql;
    private Map<String, Object> hpars;

    @Override
    public IssueReport saveOrUpdate(IssueReport ir) {
        if (ir.getId() != null && ir.getId() != 0) {
            ir.setUpdateDate(new DateTime());
            return entityManager.merge(ir);
        } else {
            ir.setId(null);
            ir.setCreationDate(new DateTime());
            ir.setUpdateDate(new DateTime());
            ir.setEditDate(new DateTime());
            ir.setPublicDate(new DateTime());
            ir.setQueryAt(new Date(System.currentTimeMillis()));
            entityManager.persist(ir);
            return ir;
        }
    }

    @Override
    public IssueReport find(long id) {
        IssueReport ir = entityManager.find(IssueReport.class, id);
        return ir;
    }


    @Override
    public IssueReport findById(long id) {
        IssueReport ir = entityManager.find(IssueReport.class, id);
        if (ir != null) {
            Hibernate.initialize(ir.getFiles());
            Hibernate.initialize(ir.getUser());
        }
        return ir;
    }

    @Override
    public List<IssueReport> findAll() {

        String hql = "SELECT ir FROM IssueReport ir";
        TypedQuery<IssueReport> query = entityManager.createQuery(hql, IssueReport.class);
        List<IssueReport> ir = query.getResultList();
        return ir;
    }

    @Override
    public List<IssueReport> fetchAll() {
        // SQL
        String hql = "SELECT ir FROM IssueReport ir WHERE ir.removed=:removed AND ir.user.removed=:removed ORDER BY ir.id DESC";
        // QUERY
        TypedQuery<IssueReport> query = entityManager.createQuery(hql, IssueReport.class);
        // WHERE
        query.setParameter("removed", false);
        // Execute
        List<IssueReport> res = query.getResultList();
        // Iinitialize
        if (res.size() > 0) {
            for (IssueReport ir : res) {
                Hibernate.initialize(ir.getFiles());
                Hibernate.initialize(ir.getUser());
            }
        }
        // Return
        return res;
    }

    @Override
    public List<IssueReport> fetchAll(String formattedCity) {
        // SQL
        String hql = "SELECT ir FROM IssueReport ir WHERE ir.removed=:removed AND ir.user.removed=:removed AND ir.formattedCity=:formattedCity ORDER BY ir.id DESC";
        // QUERY
        TypedQuery<IssueReport> query = entityManager.createQuery(hql, IssueReport.class);
        // WHERE
        query.setParameter("removed", false);
        query.setParameter("formattedCity", (String) formattedCity);
        // Execute
        List<IssueReport> res = query.getResultList();
        // Iinitialize
        if (res.size() > 0) {
            for (IssueReport ir : res) {
                Hibernate.initialize(ir.getFiles());
                Hibernate.initialize(ir.getUser());
            }
        }
        // Return
        return res;
    }

    @Override
    public Long getCount() {

        String hql = "SELECT COUNT(id) FROM IssueReport ir";
        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
        return query.getSingleResult();
    }

    @Override
    public boolean removed(long id) {

        try {
            IssueReport ir = findById(id);
            ir.setRemoved(true);
            entityManager.merge(ir);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void incrementNegativeScore(Long reportId) {

        //noinspection JpaQueryApiInspection
        entityManager.createNativeQuery("UPDATE issue_report SET negativescore=negativescore+1 WHERE id=:reportId")
                .setParameter("reportId", reportId).executeUpdate();
    }

    @Override
    public IssueReport findByPublicProtocol(String numProtocolo) {
        // SQL
        String hql = "SELECT ir FROM IssueReport ir WHERE ir.removed=:removed AND ir.publicProtocol=:numProtocolo";
        // QUERY
        TypedQuery<IssueReport> query = entityManager.createQuery(hql, IssueReport.class);
        // Where
        query.setParameter("removed", false);
        query.setParameter("numProtocolo", numProtocolo);
        //Limit
        query.setMaxResults(1);
        // Execute
        List<IssueReport> rs = query.getResultList();
        return (rs == null || rs.size() == 0) ? null : rs.get(0);
    }

    @Override
    public IssueReport findByPrivateProtocol(Integer numProtocolo) {
        // SQL
        String hql = String.format("SELECT ir FROM IssueReport ir WHERE ir.removed=false AND ir.privateProtocol='%d'", numProtocolo);
        // QUERY
        TypedQuery<IssueReport> query = entityManager.createQuery(hql, IssueReport.class);
        //Limit
        query.setMaxResults(1);
        // Execute
        List<IssueReport> rs = query.getResultList();
        if (rs != null && rs.size() > 0) {
            IssueReport ir = rs.get(0);
            Hibernate.initialize(ir.getUser());
            return ir;
        }
        return null;
    }

    @SuppressWarnings("unused")
    @Override
    public Long searchTotal(ListMultimap<String, Object> params) {
        // SQL
        String hql = "SELECT COUNT(DISTINCT ir.id) AS total FROM IssueReport ir WHERE ir.removed=:removed ";
        // Where
        boolean q = this.searchsetParameter(hql, params, false);
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

    @SuppressWarnings("unused")
    @Override
    public List<Object[]> search(ListMultimap<String, Object> conditions, PageRequest pageable) {

        // SQL
        String hql = "SELECT ir.id, ir.user.name, ir.user.officialEmail, ir.comment, ir.subcategory.name, "
                + " ir.creationDate, ir.updateDate, ir.editDate, ir.sampleTimestamp, "
                + " ir.negativeScore, ir.provider, ir.course, ir.floor, ir.speed, ir.horizontalAccuracy, ir.verticalAccuracy, ir.accuracy, ir.altitude, ir.latitude, ir.longitude,"
                + " ir.removed, ir.user.id, ir.user.progenitorId, ir.user.isGuest, ir.user.secondaryEmail, "
                + " ir.isOmbudsman, ir.publicProtocol, ir.publicUrl, ir.publicMessage, ir.isResolved, ir.privateRelEouv, ir.formattedCity, ir.fileCounter, ir.formattedAddress, CURRENT_DATE AS current_date,"
                + " ir.ombudsmanName"
                + " FROM IssueReport ir "
                + " WHERE ir.removed=:removed ";
        // Where
        boolean q = this.searchsetParameter(hql, conditions, false);
        // ORDER BY
        this.hsql = this.hsql + " ORDER BY ir.creationDate DESC ";
        // QUERY
        System.out.println(this.hsql);
        TypedQuery<Object[]> query = entityManager.createQuery(this.hsql, Object[].class);
        // Where
        for (Entry<String, Object> pars : this.hpars.entrySet()) {
            query.setParameter(pars.getKey(), pars.getValue());
            // System.out.println(pars.getKey() +" <<>> "+ pars.getValue());
        }
        // limit
        if (pageable != null) {
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
        // Execute
        List<Object[]> res = query.getResultList();
        return res;
    }

    @Override
    public List<Object[]> searchByNativeQuery(ListMultimap<String, Object> conditions, PageRequest pageable) {

        // SQL
        String hql = " SELECT ir.id, ua.name, ua.officialemail, ir.comment, s.name AS category_name, ir.creationdate, ir.updatedate, ir.editdate, ir.sampletimestamp "
                + " , ir.negativescore, ir.provider, ir.course, ir.floor, ir.speed, ir.horizontalaccuracy, ir.verticalaccuracy, ir.accuracy, ir.altitude, ir.latitude, ir.longitude "
                + " , ir.removed, ua.id AS user_id, ua.progenitorid, ua.isguest, ua.secondaryemail, ir.isombudsman, ir.pub_protocol, ir.pub_url, ir.pub_message, ir.isResolved, ir.priv_rel_eouv "
                + " , ir.formatted_city, ir.file_counter, ir.formatted_address, CURRENT_DATE AS current_date "
                + " , CAST((SELECT json_agg(json_build_object('id',r.id, 'pub_protocol',r.pub_protocol, 'pub_url',r.pub_url, 'pub_message',r.pub_message, 'priv_rel_eouv', r.priv_rel_eouv, 'ombudsman_name', r.ombudsman_name, 'isombudsman', r.isombudsman)) FROM issue_report AS r  WHERE r.parent_id = ir.id AND r.issecondary=true AND r.isfail=false) AS text) AS ombudsmanList"
                + " , ir.ombudsman_name "
                + " FROM issue_report AS ir "
                + "   INNER JOIN user_accounts AS ua ON  ua.id = ir.user_id "
                + "   INNER JOIN issue_subcategory AS s ON s.id = ir.subcategory_id "
                + " WHERE ir.removed=:removed ";
        // Where
        boolean q = this.searchsetParameter(hql, conditions, true);
        // ORDER BY
        this.hsql = this.hsql + " ORDER BY ir.creationdate DESC ";
        // LIMIT/OFFSET
        this.hsql = this.hsql + String.format(" LIMIT %d OFFSET %d ", pageable.getPageSize(), pageable.getOffset());
        // QUERY
        System.out.println(this.hsql);
        Query query = entityManager.createNativeQuery(this.hsql);
        // Where
        for (Entry<String, Object> pars : this.hpars.entrySet()) {
            query.setParameter(pars.getKey(), pars.getValue());
            // System.out.println(pars.getKey() +" <<>> "+ pars.getValue());
        }
        // Execute
        List<Object[]> res = query.getResultList();
        return res;
    }

    @Override
    public List<Object[]> searchByStats(ListMultimap<String, Object> conditions, DateTime queryStart, DateTime queryEnd,
                                        PageRequest pageable) {

        // SQL
        String hql = "SELECT ir.id, isc.category_id, ir.creationdate, ir.latitude, ir.longitude, ic.name AS category_name, ic.color AS category_color, DATE(ir.creationdate) as query_at"
                + " FROM issue_report AS ir " + " INNER JOIN issue_subcategory AS isc ON isc.id = ir.subcategory_id "
                + " INNER JOIN  issue_category AS ic ON ic.id = isc.category_id " + " WHERE ir.issecondary=:issecondary AND ir.removed=:removed ";
        // Where
        boolean q = this.searchsetParameter(hql, conditions, false);
        // ORDER BY
        this.hsql = this.hsql + " AND ( DATE(ir.creationdate) BETWEEN '" + queryStart.toString("yyyy-MM-dd") + "' AND '"
                + queryEnd.toString("yyyy-MM-dd") + "' ) ";
        this.hsql = this.hsql + " ORDER BY ir.creationdate DESC ";
        // ASSUMINDO
        this.hpars.put("removed", 0);
        this.hpars.put("issecondary", false);
        // QUERY
        System.out.println(this.hsql);
        Query query = entityManager.createNativeQuery(this.hsql);
        // Where
        for (Entry<String, Object> pars : this.hpars.entrySet()) {
            query.setParameter(pars.getKey(), pars.getValue());
            System.out.println(pars.getKey() + " <<>> " + pars.getValue());
        }
        // limit
        System.out.println(pageable.getOffset());
        System.out.println(pageable.getPageSize());

        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        // Execute
        List<Object[]> res = query.getResultList();
        return res;
    }

    @Override
    public List<Object[]> searchByStatsGroupd(ListMultimap<String, Object> conditions, DateTime queryStart,
                                              DateTime queryEnd, PageRequest pageable) {

        // SQL
        String hql = "SELECT isc.category_id, ir.queryat AS query_at, ic.name, ic.color, COUNT(ir.id) as total"
                + " FROM issue_report AS ir " + "	INNER JOIN issue_subcategory AS isc ON isc.id = ir.subcategory_id "
                + " INNER JOIN  issue_category AS ic ON ic.id = isc.category_id " + " WHERE ir.removed=:removed ";
        // Where
        boolean q = this.searchsetParameter(hql, conditions, false);
        // ORDER BY
        this.hsql = this.hsql + " AND ( ir.queryat BETWEEN '" + queryStart.toString("yyyy-MM-dd") + "' AND '"
                + queryEnd.toString("yyyy-MM-dd") + "' ) ";
        this.hsql = this.hsql + " GROUP BY ir.queryat, isc.category_id, ic.name, ic.color ";
        this.hsql = this.hsql + " ORDER BY ir.queryat ASC ";
        // ASSUMINDO
        this.hpars.put("removed", 0);
        // QUERY
        System.out.println(this.hsql);
        Query query = entityManager.createNativeQuery(this.hsql);
        // Where
        for (Entry<String, Object> pars : this.hpars.entrySet()) {
            query.setParameter(pars.getKey(), pars.getValue());
            System.out.println(pars.getKey() + " <<>> " + pars.getValue());
        }
        // limit
        System.out.println(pageable.getOffset());
        System.out.println(pageable.getPageSize());

        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        // Execute
        List<Object[]> res = query.getResultList();
        return res;
    }

    @SuppressWarnings("unused")
    @Override
    public List<IssueReport> filter(ListMultimap<String, Object> conditions, PageRequest pageable) {

        // SQL
        String hql = "SELECT ir FROM IssueReport ir WHERE ir.removed=:removed AND ir.user.removed=:removed";
        // Where
        boolean q = this.searchsetParameter(hql, conditions, false);
        // ORDER BY
        this.hsql = this.hsql + " ORDER BY ir.creationDate DESC ";
        // QUERY
        System.out.println(this.hsql);
        TypedQuery<IssueReport> query = entityManager.createQuery(this.hsql, IssueReport.class);
        // Where
        for (Entry<String, Object> pars : this.hpars.entrySet()) {
            query.setParameter(pars.getKey(), pars.getValue());
            System.out.println(pars.getKey() + " <<>> " + pars.getValue());
        }
        // limit
        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
        // Execute
        List<IssueReport> res = query.getResultList();
        // Iinitialize
        if (res.size() > 0) {
            for (IssueReport ir : res) {
                Hibernate.initialize(ir.getFiles());
            }
        }
        // Return
        return res;
    }

    private boolean searchsetParameter(String hql, ListMultimap<String, Object> conditions, boolean isNative) {
        // Dynamic
        Map<String, Object> params = new HashMap<String, Object>();
        // Allow
        String[] haystack = {"comment", "updateDate", "maxLat", "minLat", "maxLon", "minLon", "subCategoryId", "isSecondary",
                "categoryId", "search", "start", "end", "category_id", "endDate", "startDate", "userId", "parentId", "issueId", "isFail",
                "municipality"};
        // Fix
        params.put("removed", isNative ? 0 : false);
//        System.out.println(conditions);
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
                            String column = isNative ? "updatedate" : "updateDate";
                            hql += " ir." + column + ">=:" + k + count + " ";
                            DateTime dateTime = DateTime.parse(v.toString());
                            params.put(k + count, dateTime);

                        } else if (k.equals("startDate")) {
                            String column = isNative ? "updatedate" : "updateDate";
                            hql += " ir." + column + ">=:" + k + count + " ";
                            DateTime dateTime = DateTime.parse(v.toString());
                            params.put(k + count, dateTime);

                        } else if (k.equals("endDate")) {
                            String column = isNative ? "updatedate" : "updateDate";
                            hql += " ir." + column + "<=:" + k + count + " ";
                            DateTime dateTime = DateTime.parse(v.toString());
                            params.put(k + count, dateTime);

                        } else if (k.equals("comment")) {
                            hql += " UPPER(ir." + k + ") LIKE:" + k + count + " ";
                            params.put(k + count, "%" + v.toString().toUpperCase() + "%");

                        } else if (k.equals("search")) {
                            String column0 = isNative ? "ua.name" : "ir.user.name";
                            String column1 = isNative ? "ir.comment" : "ir.comment";
                            hql += " ( UPPER(" + column0 + ") LIKE:" + k + count + " OR UPPER(" + column1 + ") LIKE:" + k + count + " ) ";
                            params.put(k + count, "%" + v.toString().toUpperCase() + "%");

                        } else if (k.equals("minLat")) {
                            hql += " ir.latitude>:" + k + count + " ";
                            params.put(k + count, v);

                        } else if (k.equals("maxLat")) {
                            hql += " ir.latitude<:" + k + count + " ";
                            params.put(k + count, v);

                        } else if (k.equals("minLon")) {
                            hql += " ir.longitude>:" + k + count + " ";
                            params.put(k + count, v);

                        } else if (k.equals("maxLon")) {
                            hql += " ir.longitude<:" + k + count + " ";
                            params.put(k + count, v);

                        } else if (k.equals("subCategoryId")) {
                            String column = isNative ? "ir.subcategory_id" : "ir.subcategory.id";
                            hql += " " + column + "=:" + k + count + " ";
                            params.put(k + count, v);

                        } else if (k.equals("categoryId")) {
                            String column = isNative ? "s.category_id" : "ir.subcategory.category.id";
                            hql += " " + column + "=:" + k + count + " ";
                            params.put(k + count, v);

                        } else if (k.equals("category_id")) {
                            String column = isNative ? "s.category_id" : "ir.subcategory.category.id";
                            hql += " " + column + "=:" + k + count + " ";
                            params.put(k + count, Long.parseLong(v.toString()));

                        } else if (k.equals("parentId")) {
                            String column = isNative ? "ir.parent_id" : "ir.parentId";
                            hql += " " + column + "=:" + k + count + " ";
                            params.put(k + count, v);

                        } else if (k.equals("issueId")) {
                            String column = isNative ? "ir.id" : "ir.id";
                            hql += " " + column + "=:" + k + count + " ";
                            params.put(k + count, v);

                        } else if (k.equals("userId")) {
                            String column0 = isNative ? "ir.user_id" : "ir.user.id";
                            String column1 = isNative ? "ua.progenitorid" : "ir.user.progenitorId";
                            hql += " (" + column0 + " =:" + k + count + " OR  " + column1 + "=:" + k + count + " ) ";
                            params.put(k + count, Long.parseLong(v.toString()));

                        } else if (k.equals("start")) {
                            DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
                            DateTime dateTime = DateTime.parse(v.toString(), dtf);
                            String column = isNative ? "updatedate" : "updateDate";
                            hql += " ir." + column + " >=:" + k + count;
                            params.put(k + count, dateTime);

                        } else if (k.equals("end")) {
                            DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
                            DateTime dateTime = DateTime.parse(v.toString(), dtf);
                            String column = isNative ? "updatedate" : "updateDate";
                            hql += " ir." + column + " <=:" + k + count;
                            params.put(k + count, dateTime);

                        } else if (k.equals("isSecondary")) {
                            String column = isNative ? "issecondary" : "isSecondary";
                            hql += " ir." + column + "=:" + k + count + " ";
                            params.put(k + count, v);

                        } else if (k.equals("isFail")) {
                            String column = isNative ? "isfail" : "isFail";
                            hql += " ir." + column + "=:" + k + count + " ";
                            params.put(k + count, v);

                        } else if (k.equals("municipality")) {
                            String column = isNative ? "formatted_city" : "formattedCity";
                            hql += " ir." + column + "=:" + k + count + " ";
                            params.put(k + count, v);

                        } else {
                            hql += " ir." + k + "=:" + k + count + " ";
                            params.put(k + count, v.toString());
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
