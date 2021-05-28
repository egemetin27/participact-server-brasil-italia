package it.unibo.paserver.domain.support;

import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.NotificationBar;
import it.unibo.paserver.domain.ResultType;;

@Component
public class NotificationBarBuilder extends EntityBuilder<NotificationBar> {

	@Override
	void initEntity() {

		entity = new NotificationBar();
	}

	@Override
	NotificationBar assembleEntity() {

		return entity;
	}

	public NotificationBarBuilder setAll(long id, Long parentId, String message, boolean isRead, ResultType resultType) {
		this.setId(id);
		this.setParentId(parentId);
		this.setMessage(message);
		this.setRead(isRead);
		this.setResultType(resultType);
		return this;
	}

	public NotificationBarBuilder setId(long id) {
		entity.setId(id);
		return this;
	}

	public NotificationBarBuilder setParentId(Long parentId) {
		entity.setParentId(parentId);
		return this;
	}

	public NotificationBarBuilder setMessage(String message) {
		entity.setMessage(message);
		return this;
	}

	public NotificationBarBuilder setRead(boolean isRead) {
		entity.setRead(isRead);
		return this;
	}

	public NotificationBarBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
	
	public NotificationBarBuilder setAlink(String alink) {
		entity.setAlink(alink);
		return this;
	}
	public NotificationBarBuilder setResultType(ResultType resultType) {
		entity.setResultType(resultType);
		return this;
	}
}