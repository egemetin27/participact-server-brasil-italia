package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataGyroscope", indexes = {
		@Index(name = "gyro_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "gyro_ts", columnNames = { "sampletimestamp" }) })
public class DataGyroscope extends Data {

	private static final long serialVersionUID = 9078245648320258665L;

	@NotNull
	private float rotationX;

	@NotNull
	private float rotationY;

	@NotNull
	private float rotationZ;

	public float getRotationX() {
		return rotationX;
	}

	public void setRotationX(float rotationX) {
		this.rotationX = rotationX;
	}

	public float getRotationY() {
		return rotationY;
	}

	public void setRotationY(float rotationY) {
		this.rotationY = rotationY;
	}

	public float getRotationZ() {
		return rotationZ;
	}

	public void setRotationZ(float rotationZ) {
		this.rotationZ = rotationZ;
	}

}
