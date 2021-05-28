package it.unibo.paserver.domain.support;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.FeedbackReport;
import it.unibo.paserver.domain.FeedbackType;
import it.unibo.paserver.domain.StorageFile;
import it.unibo.paserver.domain.User;

@Component
public class FeedbackReportBuilder extends EntityBuilder<FeedbackReport> {

	@Override
	void initEntity() {
		
		entity = new FeedbackReport();
	}

	@Override
	FeedbackReport assembleEntity() {
		
		return entity;
	}

	// Setters
	public FeedbackReportBuilder setId(Long id) {
		this.setId(id);
		return this;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public FeedbackReportBuilder setComment(String comment) {
		entity.setComment(comment);
		return this;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public FeedbackReportBuilder setUser(User user) {
		entity.setUser(user);
		return this;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public FeedbackReportBuilder setType(FeedbackType type) {
		entity.setType(type);
		return this;
	}

	/**
	 * @param files
	 *            the files to set
	 */
	public FeedbackReportBuilder setFiles(List<StorageFile> files) {
		entity.setFiles(files);
		return this;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public FeedbackReportBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public FeedbackReportBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	/**
	 * @param editDate
	 *            the editDate to set
	 */
	public FeedbackReportBuilder setEditDate(DateTime editDate) {
		entity.setEditDate(editDate);
		return this;
	}

	/**
	 * @param removed
	 *            the removed to set
	 */
	public FeedbackReportBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}
