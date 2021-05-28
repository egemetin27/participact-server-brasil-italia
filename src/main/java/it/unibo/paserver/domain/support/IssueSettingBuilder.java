package it.unibo.paserver.domain.support;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.IssueSetting;

@Component
public class IssueSettingBuilder extends EntityBuilder<IssueSetting> {

	@Override
	void initEntity() {
		
		entity = new IssueSetting();

	}

	@Override
	IssueSetting assembleEntity() {
		
		return entity;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public IssueSettingBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}
	/**
	 * 
	 * @param intervalTime
	 * @return
	 */
	public IssueSettingBuilder setIntervalTime(Long intervalTime) {
		entity.setIntervalTime(intervalTime);
		return this;
	}
	/**
	 * 
	 * @param isEnabled
	 * @return
	 */
	public IssueSettingBuilder setInAppleReview(boolean inAppleReview) {
		
		entity.setInAppleReview(inAppleReview);
		return this;
	}
	/**
	 * 
	 * @param isEnabled
	 * @return
	 */
	public IssueSettingBuilder setEnabled(boolean isEnabled) {
		
		entity.setEnabled(isEnabled);
		return this;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public IssueSettingBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public IssueSettingBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	/**
	 * @param removed
	 *            the removed to set
	 */
	public IssueSettingBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}
