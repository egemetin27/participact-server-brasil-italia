package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataLocation", indexes = { @Index(name = "location_user_ts", columnNames = { "user_id", "sampletimestamp" }), @Index(name = "location_ts", columnNames = { "sampletimestamp" }) })
public class DataLocation extends Data {

	private static final long serialVersionUID = 556439058794411146L;

	@NotNull
	private double longitude;

	@NotNull
	private double latitude;

	@NotNull
	private double accuracy;

	@NotNull
	private String provider;

	private double altitude = 0.0D;
	private double horizontalAccuracy = 0.0D;
	private double verticalAccuracy = 0.0D;
	private double course = 0.0D;
	private double speed = 0.0D;
	private double floor = 0.0D;

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	/**
	 * @return the altitude
	 */
	public double getAltitude() {
		return altitude;
	}

	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	/**
	 * @return the horizontalAccuracy
	 */
	public double getHorizontalAccuracy() {
		return horizontalAccuracy;
	}

	/**
	 * @param horizontalAccuracy the horizontalAccuracy to set
	 */
	public void setHorizontalAccuracy(double horizontalAccuracy) {
		this.horizontalAccuracy = horizontalAccuracy;
	}

	/**
	 * @return the verticalAccuracy
	 */
	public double getVerticalAccuracy() {
		return verticalAccuracy;
	}

	/**
	 * @param verticalAccuracy the verticalAccuracy to set
	 */
	public void setVerticalAccuracy(double verticalAccuracy) {
		this.verticalAccuracy = verticalAccuracy;
	}

	/**
	 * @return the course
	 */
	public double getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(double course) {
		this.course = course;
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * @return the floor
	 */
	public double getFloor() {
		return floor;
	}

	/**
	 * @param floor the floor to set
	 */
	public void setFloor(double floor) {
		this.floor = floor;
	}
}
