package it.unibo.paserver.repository;

import it.unibo.paserver.domain.IssueSubCategoryHasRelationship;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository("issueSubCategoryHasRelationship")
public class JpaIssueSubCategoryHasRelationshipRepository implements IssueSubCategoryHasRelationshipRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public IssueSubCategoryHasRelationship save(IssueSubCategoryHasRelationship i) {
        entityManager.persist(i);
        return i;
    }

    @SuppressWarnings("JpaQueryApiInspection")
    @Override
    public boolean deleteBySubcategoryId(long issueSubcategoryId) {
        try {
            int numberDeleted = entityManager.createNativeQuery("DELETE FROM issue_subcategory_has_relationship WHERE issue_subcategory_id=:issueSubcategoryId ")
                    .setParameter("issueSubcategoryId", issueSubcategoryId)
                    .executeUpdate();
            if (numberDeleted > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @SuppressWarnings("JpaQueryApiInspection")
    @Override
    public List<Object[]> getListRelationOuvidoria(Long issueSubcategoryId) {
        String hql = "SELECT s.issue_subcategory_id, s.relationship_id,  o.idouvidoria, o.nomeorgaoouvidoria, o.descesfera, o.descmunicipio " +
                " FROM issue_subcategory_has_relationship AS s INNER JOIN  cgu_ouvidorias_v4 AS o ON o.idouvidoria=s.relationship_id" +
                " WHERE o.removed=:removed AND issue_subcategory_id=:issueSubcategoryId" +
                " ORDER BY o.nomeorgaoouvidoria ASC ";
        Query query = entityManager.createNativeQuery(hql);
        query.setParameter("issueSubcategoryId", issueSubcategoryId);
        query.setParameter("removed", false);
        // Execute
        List<Object[]> res = (List<Object[]>) query.getResultList();
        return res;
    }

    @SuppressWarnings("JpaQueryApiInspection")
    @Override
    public List<Object[]> getListRelationSubCategory(Long relationshipId) {
        String hql = "SELECT r.relationship_id, r.issue_subcategory_id, s.name, s.urlasset, s.sequence " +
                " FROM issue_subcategory_has_relationship AS r INNER JOIN issue_subcategory AS s ON s.id = r.issue_subcategory_id " +
                " WHERE s.removed=:removed AND r.relationship_id=:relationshipId " +
                " ORDER BY s.sequence ASC ";
        Query query = entityManager.createNativeQuery(hql);
        query.setParameter("relationshipId", relationshipId);
        query.setParameter("removed", 0);
        // Execute
        List<Object[]> res = (List<Object[]>) query.getResultList();
        return res;
    }

    @Override
    public boolean ItContains(Long issueSubcategoryId, Long relationshipId) {
        String hql = " SELECT r FROM IssueSubCategoryHasRelationship r WHERE r.issueSubcategoryId=:issueSubcategoryId AND r.relationshipId=:relationshipId ";
        TypedQuery<IssueSubCategoryHasRelationship> query = entityManager.createQuery(hql, IssueSubCategoryHasRelationship.class);
        query.setParameter("issueSubcategoryId", issueSubcategoryId);
        query.setParameter("relationshipId", relationshipId);
        query.setMaxResults(1);
        List<IssueSubCategoryHasRelationship> res = query.getResultList();
        return res != null && res.size() > 0;
    }

    @Override
    public boolean deleteByIds(long relationshipId, long issueSubcategoryId) {
        try {
            int numberDeleted = entityManager.createNativeQuery("DELETE FROM issue_subcategory_has_relationship WHERE issue_subcategory_id=:issueSubcategoryId AND relationship_id=:relationshipId ")
                    .setParameter("issueSubcategoryId", issueSubcategoryId)
                    .setParameter("relationshipId", relationshipId)
                    .executeUpdate();
            if (numberDeleted > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
