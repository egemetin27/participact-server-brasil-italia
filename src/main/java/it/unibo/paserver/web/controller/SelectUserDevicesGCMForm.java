package it.unibo.paserver.web.controller;

import java.io.Serializable;

import it.unibo.participact.domain.PANotification;

public class SelectUserDevicesGCMForm extends SelectUserDevicesForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1192870312076196939L;
	
	private PANotification.Type gcmType;

	public PANotification.Type getGcmType() {
		return gcmType;
	}

	public void setGcmType(PANotification.Type gcmType) {
		this.gcmType = gcmType;
	}
	
}
