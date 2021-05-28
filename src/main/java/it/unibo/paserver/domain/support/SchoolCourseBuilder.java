package it.unibo.paserver.domain.support;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.SchoolCourse;
import it.unibo.paserver.domain.UniCourse;

@Component
public class SchoolCourseBuilder extends EntityBuilder<SchoolCourse> {

	@Override
	void initEntity() {
		
		entity = new SchoolCourse();
	}

	@Override
	SchoolCourse assembleEntity() {
		
		return entity;
	}

	public SchoolCourseBuilder setAll(Long id, String name, UniCourse uniCourseId, String description) {
		this.setUniCourseId(uniCourseId);
		this.setName(name);
		this.setDescription(description);
		this.setActive(true);
		this.setCreationDate(new DateTime());
		this.setUpdateDate(new DateTime());
		this.setRemoved(false);
		
		return this;
	}

	public SchoolCourseBuilder setUniCourseId(UniCourse uniCourseId) {
		entity.setUniCourseId(uniCourseId);
		return this;
	}

	public SchoolCourseBuilder setName(String name) {
		entity.setName(name);
		return this;
	}

	public SchoolCourseBuilder setDescription(String description) {
		entity.setDescription(description);
		return this;
	}

	public SchoolCourseBuilder setActive(boolean isActive) {
		entity.setActive(isActive);
		return this;
	}

	public SchoolCourseBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	public SchoolCourseBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	public SchoolCourseBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}

	public SchoolCourseBuilder setId(long id) {
		entity.setId(id);
		return this;
	}
}