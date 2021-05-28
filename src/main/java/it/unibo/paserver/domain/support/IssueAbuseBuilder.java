package it.unibo.paserver.domain.support;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.IssueAbuse;
import it.unibo.paserver.domain.IssueAbuseType;
import it.unibo.paserver.domain.IssueReport;
import it.unibo.paserver.domain.User;

@Component
public class IssueAbuseBuilder extends EntityBuilder<IssueAbuse> {

	@Override
	void initEntity() {

		entity = new IssueAbuse();
	}

	@Override
	IssueAbuse assembleEntity() {

		return entity;
	}

	// Setters
	public IssueAbuseBuilder setId(Long id) {
		this.setId(id);
		return this;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public IssueAbuseBuilder setComment(String comment) {
		entity.setComment(comment);
		return this;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public IssueAbuseBuilder setUser(User user) {
		entity.setUser(user);
		return this;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public IssueAbuseBuilder setType(IssueAbuseType type) {
		entity.setType(type);
		return this;
	}

	/**
	 * @param files
	 *            the files to set
	 */
	public IssueAbuseBuilder setIssue(IssueReport issue) {
		entity.setIssue(issue);
		return this;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public IssueAbuseBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public IssueAbuseBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	/**
	 * @param editDate
	 *            the editDate to set
	 */
	public IssueAbuseBuilder setEditDate(DateTime editDate) {
		entity.setEditDate(editDate);
		return this;
	}

	/**
	 * @param removed
	 *            the removed to set
	 */
	public IssueAbuseBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}
