package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataMagneticField", indexes = {
		@Index(name = "magfield_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "magfield_ts", columnNames = { "sampletimestamp" }) })
public class DataMagneticField extends Data {

	private static final long serialVersionUID = -7734250913687773940L;

	@NotNull
	private float magneticFieldX;

	@NotNull
	private float magneticFieldY;

	@NotNull
	private float magneticFieldZ;

	public float getMagneticFieldX() {
		return magneticFieldX;
	}

	public void setMagneticFieldX(float magneticFieldX) {
		this.magneticFieldX = magneticFieldX;
	}

	public float getMagneticFieldY() {
		return magneticFieldY;
	}

	public void setMagneticFieldY(float magneticFieldY) {
		this.magneticFieldY = magneticFieldY;
	}

	public float getMagneticFieldZ() {
		return magneticFieldZ;
	}

	public void setMagneticFieldZ(float magneticFieldZ) {
		this.magneticFieldZ = magneticFieldZ;
	}

}
