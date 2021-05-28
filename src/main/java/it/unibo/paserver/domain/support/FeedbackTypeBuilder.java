package it.unibo.paserver.domain.support;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.FeedbackReport;
import it.unibo.paserver.domain.FeedbackType;

@Component
public class FeedbackTypeBuilder extends EntityBuilder<FeedbackType> {

	@Override
	void initEntity() {
		
		entity = new FeedbackType();

	}

	@Override
	FeedbackType assembleEntity() {
		
		return entity;
	}

	public FeedbackTypeBuilder setAll(Long id, String name, String description, boolean removed) {
		this.setId(id);
		this.setName(name);
		this.setDescription(description);
		return this.setRemoved(removed);
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public FeedbackTypeBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public FeedbackTypeBuilder setName(String name) {
		entity.setName(name);
		return this;
	}

	private FeedbackTypeBuilder setDescription(String description) {
		
		entity.setDescription(description);
		return this;
	}
	/**
	 * @param feedbacks
	 *            the feedbacks to set
	 */
	public FeedbackTypeBuilder setFeedbacks(List<FeedbackReport> feedbacks) {
		entity.setFeedbacks(feedbacks);
		return this;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public FeedbackTypeBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public FeedbackTypeBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}


	/**
	 * @param removed
	 *            the removed to set
	 */
	public FeedbackTypeBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}
