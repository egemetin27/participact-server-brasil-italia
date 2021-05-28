package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataBluetooth", indexes = {
		@Index(name = "blue_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "blue_ts", columnNames = { "sampletimestamp" }) })
public class DataBluetooth extends Data {

	private static final long serialVersionUID = -4260573805168092975L;

	@NotNull
	private String mac;

	@NotNull
	private String friendlyName;

	@NotNull
	private int deviceClass;

	@NotNull
	private int majorClass;

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	public int getDeviceClass() {
		return deviceClass;
	}

	public void setDeviceClass(int deviceClass) {
		this.deviceClass = deviceClass;
	}

	public int getMajorClass() {
		return majorClass;
	}

	public void setMajorClass(int major_class) {
		this.majorClass = major_class;
	}

}
