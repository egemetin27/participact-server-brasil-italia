package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.Devices;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.UserDevice;

public class UserDeviceBuilder extends EntityBuilder<UserDevice>{

	@Override
	void initEntity() {
		entity = new UserDevice();	
	}

	@Override
	UserDevice assembleEntity() {
		return entity;
	}
	
	public UserDeviceBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}
	
	public UserDeviceBuilder setName(String name) {
		entity.setName(name);
		return this;
	}
	
	public UserDeviceBuilder setImei(String imei) {
		entity.setImei(imei);
		return this;
	}
	
	public UserDeviceBuilder setPriority(Long priority) {
		entity.setPriority(priority);
		return this;
	}
	
	public UserDeviceBuilder setDevice(Devices device) {
		entity.setDevice(device);
		return this;
	}
	
	public UserDeviceBuilder setUser(User user) {
		entity.setUser(user);
		return this;
	}
	
}
