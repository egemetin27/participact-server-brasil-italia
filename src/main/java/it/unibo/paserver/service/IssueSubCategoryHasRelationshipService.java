package it.unibo.paserver.service;

import it.unibo.paserver.domain.IssueSubCategoryHasRelationship;
import java.util.List;

public interface IssueSubCategoryHasRelationshipService {
    IssueSubCategoryHasRelationship save(IssueSubCategoryHasRelationship i);

    boolean deleteBySubcategoryId(long issueSubcategoryId);

    List<Object[]> getListRelationOuvidoria(Long id);

    List<Object[]> getListRelationSubCategory(Long relationshipId);

    boolean ItContains(Long issueSubcategoryId, Long relationshipId);

    boolean deleteByIds(long relationshipId, long issueSubcategoryId);
}
