package it.unibo.paserver.web.controller;

import it.unibo.paserver.domain.Platform;

import java.io.Serializable;
import java.util.List;

public class SelectUserDevicesForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6187407405515358200L;

	private List<SelectUserDevice> selectUserDevices;

	private Platform devicePlatform;

	private Long deviceId;

	private boolean isPrimaryUserDevice;

	private String user;
	
	private Long userDevicesTot;
	
	public Long getDeviceId() {
		return deviceId;
	}

	public Platform getDevicePlatform() {
		return devicePlatform;
	}

	public boolean getIsPrimaryUserDevice() {
		return isPrimaryUserDevice;
	}

	public List<SelectUserDevice> getSelectUserDevices() {
		return selectUserDevices;
	}

	public String getUser() {
		return user;
	}

	public Long getUserDevicesTot() {
		return userDevicesTot;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public void setDevicePlatform(Platform devicePlatform) {
		this.devicePlatform = devicePlatform;
	}

	public void setIsPrimaryUserDevice(boolean isPrimaryUserDevice) {
		this.isPrimaryUserDevice = isPrimaryUserDevice;
	}

	public void setSelectUserDevices(
			List<SelectUserDevice> selectUserDevices) {
		this.selectUserDevices = selectUserDevices;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public void setUserDevicesTot(Long userDevicesTot) {
		this.userDevicesTot = userDevicesTot;
	}
	
}
