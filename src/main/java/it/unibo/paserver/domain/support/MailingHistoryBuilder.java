package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.MailingHistory;

public class MailingHistoryBuilder extends EntityBuilder<MailingHistory> {
	@Override
	void initEntity() {
		
		entity = new MailingHistory();

	}

	@Override
	MailingHistory assembleEntity() {
		
		return entity;
	}

	/**
	 * Setter/Getter
	 */

	public MailingHistoryBuilder setAll(Long id, Long taskId, Long parentId, String emailSubject, String emailBody,
			Boolean isSendEmail, Long emailSystemId, boolean removed) {
		this.setId(id);
		this.setTaskId(taskId);
		this.setParentId(parentId);

		this.setEmailBody(emailBody);
		this.setEmailSubject(emailSubject);
		this.setEmailSystemId(emailSystemId);
		this.setIsSendEmail(isSendEmail);
		this.setRemoved(removed);
		return this;
	}

	public MailingHistoryBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	public MailingHistoryBuilder setParentId(Long parentId) {
		entity.setParentId(parentId);
		return this;
	}

	public MailingHistoryBuilder setEmailSubject(String emailSubject) {
		entity.setEmailSubject(emailSubject);
		return this;
	}

	public MailingHistoryBuilder setEmailBody(String emailBody) {
		entity.setEmailBody(emailBody);
		return this;
	}

	public MailingHistoryBuilder setIsSendEmail(Boolean isSendEmail) {
		entity.setIsSendEmail(isSendEmail);
		return this;
	}

	public MailingHistoryBuilder setEmailSystemId(Long emailSystemId) {
		entity.setEmailSystemId(emailSystemId);
		return this;
	}

	public MailingHistoryBuilder setTaskId(Long taskId) {
		entity.setTaskId(taskId);
		return this;
	}

	public MailingHistoryBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}