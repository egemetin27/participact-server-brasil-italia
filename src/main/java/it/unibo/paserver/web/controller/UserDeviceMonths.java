package it.unibo.paserver.web.controller;

import java.util.List;

public class UserDeviceMonths {

	List<UserDeviceStats> userDevice;
	String[] months;

	public List<UserDeviceStats> getUserDevice() {
		return userDevice;
	}

	public void setUserDevice(List<UserDeviceStats> userDevice) {
		this.userDevice = userDevice;
	}

	public String[] getMonths() {
		return months;
	}

	public void setMonths(String[] months) {
		this.months = months;
	}

	public UserDeviceMonths() {

	}

	
}
