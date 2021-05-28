package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.IssueCategory;
import it.unibo.paserver.domain.IssueSubCategory;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IssueCategoryBuilder extends EntityBuilder<IssueCategory> {

    @Override
    void initEntity() {

        entity = new IssueCategory();

    }

    @Override
    IssueCategory assembleEntity() {

        return entity;
    }

    public IssueCategoryBuilder setAll(Long id, String name, String description, boolean removed) {
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        return this.setRemoved(removed);
    }

    /**
     * @param id the id to set
     */
    public IssueCategoryBuilder setId(Long id) {
        entity.setId(id);
        return this;
    }

    /**
     * @param name the name to set
     */
    public IssueCategoryBuilder setName(String name) {
        entity.setName(name);
        return this;
    }

    private IssueCategoryBuilder setDescription(String description) {

        entity.setDescription(description);
        return this;
    }

    /**
     * @param sequence the sequence to set
     */
    public IssueCategoryBuilder setSequence(int sequence) {
        entity.setSequence(sequence);
        return this;
    }

    /**
     * @param urlAsset the urlAsset to set
     */
    public IssueCategoryBuilder setUrlAsset(String urlAsset) {
        entity.setUrlAsset(urlAsset);
        return this;
    }

    public IssueCategoryBuilder setUrlIcon(String urlIcon) {
        entity.setUrlIcon(urlIcon);
        return this;
    }

    public IssueCategoryBuilder setColor(String color) {
        entity.setColor(color);
        return this;
    }

    /**
     * @param subcategories
     * @return
     */
    public IssueCategoryBuilder setSubcategories(List<IssueSubCategory> subcategories) {
        entity.setSubcategories(subcategories);
        return this;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public IssueCategoryBuilder setCreationDate(DateTime creationDate) {
        entity.setCreationDate(creationDate);
        return this;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public IssueCategoryBuilder setUpdateDate(DateTime updateDate) {
        entity.setUpdateDate(updateDate);
        return this;
    }

    /**
     * @param removed the removed to set
     */
    public IssueCategoryBuilder setRemoved(boolean removed) {
        entity.setRemoved(removed);
        return this;
    }

    public IssueCategoryBuilder setUrlAssetLight(String urlAssetLight) {
        entity.setUrlAssetLight(urlAssetLight);
        return this;
    }

    public IssueCategoryBuilder setEnabled(boolean b) {
        entity.setEnabled(b);
        return this;
    }
}
