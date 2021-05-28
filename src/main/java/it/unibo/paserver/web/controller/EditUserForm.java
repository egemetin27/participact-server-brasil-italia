package it.unibo.paserver.web.controller;

import it.unibo.paserver.domain.User;

public class EditUserForm extends UserForm {

	public void initFromUsers(User u) {
		setContactPhoneNumber(u.getContactPhoneNumber());
		setCurrentAddress(u.getCurrentAddress());
		setCurrentCity(u.getCurrentCity());
		setCurrentProvince(u.getCurrentProvince());
		setCurrentZipCode(u.getCurrentZipCode());
		if (u.getDomicileAddress() == null) {
			setIsDomicileEqualToCurrentAddr(true);
		}
		setDomicileAddress(u.getDomicileAddress());
		setDomicileCity(u.getDomicileCity());
		setDomicileProvince(u.getDomicileProvince());
		setDomicileZipCode(u.getDomicileZipCode());
		setHomePhoneNumber(u.getHomePhoneNumber());
		setNotes(u.getNotes());
		setProjectEmail(u.getProjectEmail());
		setFacebookEmail(u.getFacebookEmail());

	}

	public void updateUser(User u) {
		u.setContactPhoneNumber(getContactPhoneNumber());
		/*if (getPassword() != null && getPassword().length() > 0) {
			u.setCredentials(u.getOfficialEmail(), getPassword());
		}*/
		u.setCurrentAddress(getCurrentAddress());
		u.setCurrentCity(getCurrentCity());
		u.setCurrentProvince(getCurrentProvince());
		u.setCurrentZipCode(getCurrentZipCode());
		u.setDomicileAddress(getDomicileAddress());
		u.setDomicileCity(getDomicileCity());
		u.setDomicileProvince(getDomicileProvince());
		u.setDomicileZipCode(getDomicileZipCode());
		u.setHomePhoneNumber(getHomePhoneNumber());
		u.setNotes(getNotes());
		u.setProjectEmail(getProjectEmail());
		u.setFacebookEmail(getFacebookEmail());

	}

}
