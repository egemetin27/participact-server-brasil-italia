package it.unibo.paserver.domain.support;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.SystemEmail;;

@Component
public class SystemEmailBuilder extends EntityBuilder<SystemEmail> {

	@Override
	void initEntity() {
		
		entity = new SystemEmail();
	}

	@Override
	SystemEmail assembleEntity() {
		
		return entity;
	}

	public SystemEmailBuilder setAll(long id, String fromEmail, String fromName, String smtpHost, long smtpPort,
			String username, String password, String encryption, boolean isActive, Integer limitPer, Long limitPeriod, Long limitSending, Long limitTime) {

		this.setId(id);
		this.setFromEmail(fromEmail);
		this.setFromName(fromName);
		this.setSmtpHost(smtpHost);
		this.setSmtpPort(smtpPort);
		this.setUsername(username);
		this.setPassword(password);
		this.setEncryption(encryption);
		this.setActive(isActive);
		
		this.setLimitPer(limitPer);
		this.setLimitPeriod(limitPeriod);
		this.setLimitSending(limitSending);
		this.setLimitTime(limitTime);
		
		return this.setRemoved(false);
	}

	public SystemEmailBuilder setId(long id) {
		entity.setId(id);
		return this;
	}

	public SystemEmailBuilder setFromEmail(String fromEmail) {
		entity.setFromEmail(fromEmail);
		return this;
	}

	public SystemEmailBuilder setFromName(String fromName) {
		entity.setFromName(fromName);
		return this;
	}

	public SystemEmailBuilder setSmtpHost(String smtpHost) {
		entity.setSmtpHost(smtpHost);
		return this;
	}

	public SystemEmailBuilder setSmtpPort(long smtpPort) {
		entity.setSmtpPort(smtpPort);
		return this;
	}

	public SystemEmailBuilder setUsername(String username) {
		entity.setUsername(username);
		return this;
	}

	public SystemEmailBuilder setPassword(String password) {
		entity.setPassword(password);
		return this;
	}

	public SystemEmailBuilder setEncryption(String encryption) {
		entity.setEncryption(encryption);
		return this;
	}

	public SystemEmailBuilder setActive(boolean isActive) {
		entity.setActive(isActive);
		return this;
	}

	public SystemEmailBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	public SystemEmailBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	public SystemEmailBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}

	public SystemEmailBuilder setLimitSending(Long limitSending) {
		entity.setLimitSending(limitSending);
		return this;
	}

	public SystemEmailBuilder setLimitPeriod(Long limitPeriod) {
		entity.setLimitPeriod(limitPeriod);
		return this;
	}

	public SystemEmailBuilder setLimitPer(Integer limitPer) {
		entity.setLimitPer(limitPer);
		return this;
	}

	public SystemEmailBuilder setLimitTime(Long limitTime) {
		entity.setLimitTime(limitTime);
		return this;
	}
}