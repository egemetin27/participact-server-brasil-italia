package it.unibo.paserver.domain.support;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.IssueCategory;
import it.unibo.paserver.domain.IssueSubCategory;

@Component
public class IssueSubCategoryBuilder extends EntityBuilder<IssueSubCategory> {

	@Override
	void initEntity() {
		
		entity = new IssueSubCategory();

	}

	@Override
	IssueSubCategory assembleEntity() {
		
		return entity;
	}

	public IssueSubCategoryBuilder setAll(Long id, String name, String description, boolean removed) {
		this.setId(id);
		this.setName(name);
		this.setDescription(description);
		return this.setRemoved(removed);
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public IssueSubCategoryBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public IssueSubCategoryBuilder setName(String name) {
		entity.setName(name);
		return this;
	}

	private IssueSubCategoryBuilder setDescription(String description) {
		
		entity.setDescription(description);
		return this;
	}

	/**
	 * @param sequence
	 *            the sequence to set
	 */
	public IssueSubCategoryBuilder setSequence(int sequence) {
		entity.setSequence(sequence);
		return this;
	}

	/**
	 * @param urlAsset
	 *            the urlAsset to set
	 */
	public IssueSubCategoryBuilder setUrlAsset(String urlAsset) {
		entity.setUrlAsset(urlAsset);
		return this;
	}

	/**
	 * @param categories
	 *            the categories to set
	 */
	public IssueSubCategoryBuilder setCategory(IssueCategory category) {
		entity.setCategory(category);
		return this;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public IssueSubCategoryBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public IssueSubCategoryBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	/**
	 * @param removed
	 *            the removed to set
	 */
	public IssueSubCategoryBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}
