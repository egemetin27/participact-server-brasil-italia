package it.unibo.paserver.domain.support;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.SystemPage;
import it.unibo.paserver.domain.SystemPageType;;

@Component
public class SystemPageBuilder extends EntityBuilder<SystemPage> {

	@Override
	void initEntity() {
		
		entity = new SystemPage();
	}

	@Override
	SystemPage assembleEntity() {
		
		return entity;
	}

	public SystemPageBuilder setAll(SystemPageType type, String title, String content, boolean isActive) {
		this.setType(type);
		this.setTitle(title);
		this.setContent(content);
		this.setActive(isActive);
		this.setCreationDate(new DateTime());
		this.setUpdateDate(new DateTime());
		return this.setRemoved(false);
	}

	public SystemPageBuilder setType(SystemPageType type) {
		entity.setType(type);
		return this;
	}

	public SystemPageBuilder setTitle(String title) {
		entity.setTitle(title);
		return this;
	}

	public SystemPageBuilder setContent(String content) {
		entity.setContent(content);
		return this;
	}

	public SystemPageBuilder setActive(boolean isActive) {
		entity.setActive(isActive);
		return this;
	}

	public SystemPageBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	public SystemPageBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	public SystemPageBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}

	public SystemPageBuilder setId(long id) {
		entity.setId(id);
		return this;
	}

}