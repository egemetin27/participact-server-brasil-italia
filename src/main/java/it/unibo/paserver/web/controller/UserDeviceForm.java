package it.unibo.paserver.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDeviceForm {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(UserDeviceForm.class);
	
	private String name;
	
	private String uuid;

	private Long priority;

	private Long deviceId;

	private Long userId;
	
	private Long Id;

	public Long getDeviceId() {
		return deviceId;
	}

	public Long getId() {
		return Id;
	}
	
	public String getName() {
		return name;
	}

	public Long getPriority() {
		return priority;
	}

	public Long getUserId() {
		return userId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	
	public void setId(Long id) {
		Id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
