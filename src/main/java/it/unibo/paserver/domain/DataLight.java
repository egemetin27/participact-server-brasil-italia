package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataLight", indexes = {
		@Index(name = "light_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "light_ts", columnNames = { "sampletimestamp" }) })
public class DataLight extends Data {

	private static final long serialVersionUID = -1101240191360967688L;

	@NotNull
	private float value;

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

}
