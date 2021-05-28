package it.unibo.paserver.domain.support;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.SystemBackup;;

@Component
public class SystemBackupBuilder extends EntityBuilder<SystemBackup> {

	@Override
	void initEntity() {
		
		entity = new SystemBackup();
	}

	@Override
	SystemBackup assembleEntity() {
		
		return entity;
	}

	public SystemBackupBuilder setAll(long id, String name, String hostname, long port, String username, String password) {

		this.setId(id);
		this.setHostname(hostname);
		this.setPort(port);
		this.setUsername(username);
		this.setPassword(password);
		this.setName(name);
		return this.setRemoved(false);
	}

	public SystemBackupBuilder setId(long id) {
		entity.setId(id);
		return this;
	}

	public SystemBackupBuilder setName(String name) {
		entity.setName(name);
		return this;
	}
	
	public SystemBackupBuilder setHostname(String host) {
		entity.setHostname(host);
		return this;
	}

	public SystemBackupBuilder setPort(long port) {
		entity.setPort(port);
		return this;
	}

	public SystemBackupBuilder setUsername(String username) {
		entity.setUsername(username);
		return this;
	}

	public SystemBackupBuilder setPassword(String password) {
		entity.setPassword(password);
		return this;
	}

	public SystemBackupBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	public SystemBackupBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	public SystemBackupBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
}