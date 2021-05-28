package it.unibo.paserver.web.controller;


//import it.unibo.paserver.domain.Devices;
//import it.unibo.paserver.domain.support.UserDeviceBuilder;
import it.unibo.paserver.service.DevicesService;
import it.unibo.paserver.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AddUserDeviceForm extends UserDeviceForm {
	
	@Autowired
	UserService userService;
	
	@Autowired
	DevicesService deviceService;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(AddUserDeviceForm.class);

	public AddUserDeviceForm() {
		super();
	}
	
	// Non Utilizzato
	/*
	public UserDeviceBuilder setAllFields(UserDeviceBuilder udb) {
		udb.setName(getName());
		udb.setImei(getImei());
		udb.setPriority(getPriority());
		
		Long deviceId = getDeviceId();
		Devices device = deviceService.findById(deviceId);
		udb.setDevice(device);
		udb.setUser(userService.getUser(getUserId()));
		return udb;
	}*/
		
}
