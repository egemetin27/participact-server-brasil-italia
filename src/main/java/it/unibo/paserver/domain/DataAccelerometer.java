package it.unibo.paserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataAccelerometer", indexes = {
		@Index(name = "acce_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "acce_ts", columnNames = { "sampletimestamp" }) })
public class DataAccelerometer extends Data {

	private static final long serialVersionUID = 7793293804630922508L;

	@NotNull
	@Column(name = "X")
	private float x;

	@NotNull
	@Column(name = "Y")
	private float y;

	@NotNull
	@Column(name = "Z")
	private float z;

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

}
