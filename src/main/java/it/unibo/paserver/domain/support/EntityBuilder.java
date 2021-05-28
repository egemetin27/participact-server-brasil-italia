package it.unibo.paserver.domain.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.ArrayUtils;

public abstract class EntityBuilder<T extends Serializable> {

	protected T entity;

	{
		initEntity();
	}

	abstract void initEntity();

	abstract T assembleEntity();

	public T build(Boolean... doNotPersist) {
		T entity = assembleEntity();
		if (ArrayUtils.isEmpty(doNotPersist)
				|| (ArrayUtils.isNotEmpty(doNotPersist) && doNotPersist[0] == Boolean.FALSE)) {
			EntityBuilderManager.getEntityManager().persist(entity);
		}
		T temp = entity;
		initEntity();
		return temp;
	}

	public T getEntity() {
		return entity;
	}

    public static class EntityBuilderManager {
		private static ThreadLocal<EntityManager> entityManagerHolder = new ThreadLocal<EntityManager>();

		public static void setEntityManager(EntityManager entityManger) {
			entityManagerHolder.set(entityManger);
		}

		public static void clearEntityManager() {
			entityManagerHolder.remove();
		}

		public static EntityManager getEntityManager() {
			return entityManagerHolder.get();
		}
	}
}
