package it.unibo.paserver.web.controller;


import it.unibo.paserver.domain.UserDevice;
import it.unibo.paserver.service.DevicesService;
import it.unibo.paserver.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EditUserDeviceForm extends UserDeviceForm{
	
	@Autowired
	UserService userService;
	
	@Autowired
	DevicesService deviceService;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(EditUserDeviceForm.class);
	
	public EditUserDeviceForm() {
		super();
	}
	
	public void initFormUserDevice(UserDevice userDevice) {
		setName(userDevice.getName());
		setUuid(userDevice.getImei());
		setDeviceId(userDevice.getDevice().getId());
        setUserId(userDevice.getUser().getId());
        setPriority(userDevice.getPriority());
        setId(userDevice.getId());
	}
	
	// Non Utilizzato
	public void updateUserDevice(UserDevice userDevice) {

		if (getName() != null) {
			userDevice.setName(getName());
		} else if (userDevice.getName() != null && getName() == null) {
			userDevice.setName("");
		}
		
		if (getUuid() != null) {
			userDevice.setImei(getUuid());
		} else if (userDevice.getImei() != null && getUuid() == null) {
			userDevice.setImei("");
		}

		if (getPriority() != null) {
			userDevice.setPriority(getPriority());
		} else if (userDevice.getPriority() != null && getPriority() == null) {
			userDevice.setPriority((long) 0);
		}
		
		userDevice.setUser(userService.getUser(getUserId()));
		userDevice.setDevice(deviceService.findById(getDeviceId()));	
	}
	
}
