package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataBattery", indexes = { @Index(name = "batt_user_ts", columnNames = { "user_id", "sampletimestamp" }), @Index(name = "batt_ts", columnNames = { "sampletimestamp" }) })
public class DataBattery extends Data {

	private static final long serialVersionUID = 1569401021247631204L;

	@NotNull
	private int level;

	@NotNull
	private int scale;

	@NotNull
	private int temperature;

	@NotNull
	private int voltage;

	@NotNull
	private int plugged;

	@NotNull
	private int status;

	@NotNull
	private int health;
	@Transient
	private int batteryLevel = 0;
	@Transient
	private int batteryState = 0;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getVoltage() {
		return voltage;
	}

	public void setVoltage(int voltage) {
		this.voltage = voltage;
	}

	public int getPlugged() {
		return plugged;
	}

	public void setPlugged(int plugged) {
		this.plugged = plugged;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * @return the batteryLevel
	 */
	public int getBatteryLevel() {
		return batteryLevel;
	}

	/**
	 * @param batteryLevel
	 *            the batteryLevel to set
	 */
	public void setBatteryLevel(int batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	/**
	 * @return the batteryState
	 */
	public int getBatteryState() {
		return batteryState;
	}

	/**
	 * @param batteryState
	 *            the batteryState to set
	 */
	public void setBatteryState(int batteryState) {
		this.batteryState = batteryState;
	}
}
