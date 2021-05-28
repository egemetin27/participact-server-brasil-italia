package it.unibo.paserver.web.controller;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectUserDevice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1151236302143462113L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(SelectUserDevice.class);
	
	private Long userDeviceId;
	
	private String userDeviceName;

	private String imei;

	private Long priority;

	private Long deviceId;

	private String deviceModel;

	private String deviceManufacturer;

	private Long userId;

	private String userName;

	private String userSurname;

	private String userOfficialEmail;

	public Long getDeviceId() {
		return deviceId;
	}

	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public String getImei() {
		return imei;
	}

	public Long getPriority() {
		return priority;
	}

	public Long getUserDeviceId() {
		return userDeviceId;
	}

	public String getUserDeviceName() {
		return userDeviceName;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserOfficialEmail() {
		return userOfficialEmail;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public void setUserDeviceId(Long userDeviceId) {
		this.userDeviceId = userDeviceId;
	}

	public void setUserDeviceName(String userDeviceName) {
		this.userDeviceName = userDeviceName;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setUserOfficialEmail(String userOfficialEmail) {
		this.userOfficialEmail = userOfficialEmail;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}
	
}
