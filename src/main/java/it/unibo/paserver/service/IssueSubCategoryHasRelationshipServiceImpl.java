package it.unibo.paserver.service;

import it.unibo.paserver.domain.IssueSubCategoryHasRelationship;
import it.unibo.paserver.repository.IssueSubCategoryHasRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 26/04/2019
 **/
@Service
@Transactional(readOnly = true)
public class IssueSubCategoryHasRelationshipServiceImpl implements IssueSubCategoryHasRelationshipService {
    @Autowired
    IssueSubCategoryHasRelationshipRepository repos;

    @Override
    @Transactional(readOnly = false)
    public IssueSubCategoryHasRelationship save(IssueSubCategoryHasRelationship i) {
        return repos.save(i);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteBySubcategoryId(long issueSubcategoryId) {
        return repos.deleteBySubcategoryId(issueSubcategoryId);
    }

    @Override
    public List<Object[]> getListRelationOuvidoria(Long issueSubcategoryId) {
        return repos.getListRelationOuvidoria(issueSubcategoryId);
    }

    @Override
    public List<Object[]> getListRelationSubCategory(Long relationshipId) {
        return repos.getListRelationSubCategory(relationshipId);
    }

    @Override
    public boolean ItContains(Long issueSubcategoryId, Long relationshipId) {
        return repos.ItContains(issueSubcategoryId, relationshipId);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteByIds(long relationshipId, long issueSubcategoryId) {
        return repos.deleteByIds(relationshipId, issueSubcategoryId);
    }


}
