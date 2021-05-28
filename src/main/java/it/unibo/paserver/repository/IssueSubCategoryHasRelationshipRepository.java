package it.unibo.paserver.repository;

import it.unibo.paserver.domain.IssueSubCategoryHasRelationship;

import java.util.List;

public interface IssueSubCategoryHasRelationshipRepository {
    IssueSubCategoryHasRelationship save(IssueSubCategoryHasRelationship i);

    boolean deleteBySubcategoryId(long issueSubcategoryId);

    List<Object[]> getListRelationOuvidoria(Long issueSubcategoryId);

    @SuppressWarnings("JpaQueryApiInspection")
    List<Object[]> getListRelationSubCategory(Long relationshipId);

    boolean ItContains(Long issueSubcategoryId, Long relationshipId);

    boolean deleteByIds(long relationshipId, long issueSubcategoryId);
}
