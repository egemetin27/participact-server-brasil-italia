package it.unibo.paserver.domain.support;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.IssueAbuse;
import it.unibo.paserver.domain.IssueAbuseType;

@Component
public class AbuseTypeBuilder extends EntityBuilder<IssueAbuseType> {

	@Override
	void initEntity() {

		entity = new IssueAbuseType();

	}

	@Override
	IssueAbuseType assembleEntity() {

		return entity;
	}

	public AbuseTypeBuilder setAll(Long id, String name, String description, boolean removed) {
		this.setId(id);
		this.setName(name);
		this.setDescription(description);
		return this.setRemoved(removed);
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public AbuseTypeBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public AbuseTypeBuilder setName(String name) {
		entity.setName(name);
		return this;
	}

	private AbuseTypeBuilder setDescription(String description) {

		entity.setDescription(description);
		return this;
	}
	/**
	 * @param abuses
	 *            the abuses to set
	 */
	public AbuseTypeBuilder setAbuses(List<IssueAbuse> abuses) {
		entity.setAbuses(abuses);
		return this;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public AbuseTypeBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public AbuseTypeBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}


	/**
	 * @param removed
	 *            the removed to set
	 */
	public AbuseTypeBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}
