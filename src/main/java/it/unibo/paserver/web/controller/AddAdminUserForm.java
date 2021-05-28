package it.unibo.paserver.web.controller;

import it.unibo.paserver.domain.BinaryDocumentType;
import it.unibo.paserver.domain.support.UserBuilder;

public class AddAdminUserForm extends AdminUserForm {

	public AddAdminUserForm() {
		super();
	}

	public UserBuilder setAllFields(UserBuilder ub) {
		ub.setBirthdate(getBirthdate());
		ub.setContactPhoneNumber(getContactPhoneNumber());
		ub.setCredentials(getOfficialEmail(), getPassword());
		ub.setCurrentAddress(getCurrentAddress());
		ub.setCurrentCity(getCurrentCity());
		ub.setCurrentProvince(getCurrentProvince());
		ub.setCurrentZipCode(getCurrentZipCode());
		ub.setDocumentId(getDocumentId());
		ub.setDocumentIdType(getDocumentIdType());
		ub.setDomicileAddress(getDomicileAddress());
		ub.setDomicileCity(getDomicileCity());
		ub.setDomicileProvince(getDomicileProvince());
		ub.setDomicileZipCode(getDomicileZipCode());
		ub.setGender(getGenderAsEnum());
		ub.setHomePhoneNumber(getHomePhoneNumber());
		ub.setName(getName());
		ub.setNotes(getNotes());
		ub.setOfficialEmail(getOfficialEmail());
		ub.setProjectEmail(getProjectEmail());
		ub.setProjectPhoneNumber(getProjectPhoneNumber());
		ub.setSurname(getSurname());
		ub.setUniCity(getUniCity());
		ub.setUniCourse(getUniCourse());
		ub.setUniDegree(getUniDegree());
		ub.setUniDepartment(getUniDepartment());
		ub.setUniIsSupplementaryYear(getUniIsSupplementaryYear());
		ub.setUniSchool(getUniSchool());
		ub.setUniYear(getUniYear());
		ub.setFacebookEmail(getFacebookEmail());
		ub.setSimStatus(getSimStatus());
		ub.setWantsPhone(getWantsPhone());
		ub.setHasSIM(getHasSIM());
		ub.setWantsPhone(getWantsPhone());
		ub.setIsActive(getIsActive());
		ub.setIccid(getIccid());
		ub.setOriginalPhoneOperator(getOriginalPhoneOperator());
		ub.setCf(getCf());
		ub.setImei(getImei());
		ub.setDevice(getDevice());
		ub.setIsMyCompanyRegistered(getIsMyCompanyRegistered());
		setupBinaryDocument(ub, BinaryDocumentType.ID_SCAN, getIdScan(),
				idScanOrigFname, idScanExt);
		setupBinaryDocument(ub, BinaryDocumentType.LAST_INVOICE,
				getLastInvoiceScan(), lastInvoiceOrigFname, lastInvoiceScanExt);
		setupBinaryDocument(ub, BinaryDocumentType.PRIVACY, getPrivacyScan(),
				privacyScanOrigFname, privacyScanExt);
		setupBinaryDocument(ub, BinaryDocumentType.PRESA_CONSEGNA_PHONE,
				getPresaConsegnaPhoneScan(), presaConsegnaPhoneScanOrigFname,
				presaConsegnaPhoneScanExt);
		setupBinaryDocument(ub, BinaryDocumentType.PRESA_CONSEGNA_SIM,
				getPresaConsegnaSIMScan(), presaConsegnaSIMScanOrigFname,
				presaConsegnaSIMScanExt);
		return ub;
	}

}
