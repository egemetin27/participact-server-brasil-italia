package it.unibo.paserver.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "issue_subcategory_has_relationship")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IssueSubCategoryHasRelationship implements Serializable {

    private static final long serialVersionUID = -3479074055827909154L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "issue_subcategory_id")
    private Long issueSubcategoryId;

    @Column(name = "relationship_id")
    private Long relationshipId;

    public IssueSubCategoryHasRelationship(Long relationshipId, Long issueSubcategoryId) {
        this.setRelationshipId(relationshipId);
        this.setIssueSubcategoryId(issueSubcategoryId);
    }

    public IssueSubCategoryHasRelationship() {

    }

    public Long getIssueSubcategoryId() {
        return issueSubcategoryId;
    }

    public void setIssueSubcategoryId(Long issueSubcategoryId) {
        this.issueSubcategoryId = issueSubcategoryId;
    }

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}