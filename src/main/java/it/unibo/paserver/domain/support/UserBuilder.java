package it.unibo.paserver.domain.support;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.*;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.Set;
import java.util.UUID;

public class UserBuilder extends EntityBuilder<User> {

    @Override
    void initEntity() {
        entity = new User();
    }

    @Override
    User assembleEntity() {
        return entity;
    }

    /**
     * Injecta os principais atributos
     *
     * @return
     */
    public UserBuilder setAll(Long id, String officialEmail, String surname, String name, Gender gender, LocalDate birthdate, String contactPhoneNumber, String currentAddress, String currentCity, String currentProvince,
                              String currentZipCode, String currentNumber, String currentCountry, String documentId, DocumentIdType documentIdType, String homePhoneNumber, UniCourse uniCourse, Boolean uniIsSupplementaryYear, Integer uniYear, String device,
                              String notes, Institutions institutionId, SchoolCourse schoolCourseId, String mapLat, String mapLng) {

        this.setId(id);
        this.setOfficialEmail(officialEmail);
        this.setProjectEmail(officialEmail);

        this.setSurname(surname);
        this.setName(name);
        this.setBirthdate(birthdate);
        this.setCf(UUID.randomUUID().toString());
        this.setIccid(UUID.randomUUID().toString());
        this.setNewIccid(UUID.randomUUID().toString());
        this.setContactPhoneNumber(contactPhoneNumber);
        this.setCurrentAddress(currentAddress);
        this.setCurrentCity(currentCity);
        this.setCurrentProvince(currentProvince);
        this.setCurrentZipCode(currentZipCode);
        this.setCurrentNumber(currentNumber);
        this.setCurrentCountry(currentCountry);

        this.setDocumentId(documentId);
        this.setDocumentIdType(documentIdType);

        this.setGender(gender);
        this.setHomePhoneNumber(homePhoneNumber);
        this.setIsActive(true);
        this.setRegistrationDateTime(new DateTime());
        this.setUniCity(UniCity.FLORIANOPOLIS);
        this.setUniCourse(uniCourse);
        this.setUniIsSupplementaryYear(uniIsSupplementaryYear);
        this.setUniYear(uniYear);
        this.setUniDegree(null);
        this.setWantsPhone(false);
        this.setIsMyCompanyRegistered(false);
        this.setDevice(device);
        this.setNotes(notes);
        this.setInstitutionId(institutionId);
        this.setSchoolCourseId(schoolCourseId);
        this.setMapLat(mapLat);
        this.setMapLng(mapLat);
        return this;
    }

    public UserBuilder setMapLat(String mapLat) {
        entity.setMapLat(mapLat);
        return this;
    }

    public UserBuilder setMapLng(String mapLng) {
        entity.setMapLng(mapLng);
        return this;
    }

    public UserBuilder setSchoolCourseId(SchoolCourse schoolCourseId) {
        entity.setSchoolCourseId(schoolCourseId);
        return this;

    }

    public UserBuilder setInstitutionId(Institutions institutionId) {
        entity.setInstitutionId(institutionId);
        return this;
    }

    public UserBuilder setCredentials(String officialEmail, String password) {
        entity.setCredentials(officialEmail, password);
        return this;
    }

    public UserBuilder setCurrentNumber(String n) {
        entity.setCurrentNumber(n);
        return this;
    }

    public UserBuilder setId(Long id) {
        entity.setId(id);
        return this;
    }

    public UserBuilder setName(String name) {
        entity.setName(name);
        return this;
    }

    public UserBuilder setSurname(String surname) {
        surname = Validator.isEmptyString(surname) ? " . " : surname;
        entity.setSurname(surname);
        return this;
    }

    public UserBuilder setGender(Gender gender) {
        entity.setGender(gender);
        return this;
    }

    public UserBuilder setBirthdate(LocalDate birthdate) {
        entity.setBirthdate(birthdate);
        return this;
    }

    public UserBuilder setDocumentIdType(DocumentIdType documentIdType) {
        entity.setDocumentIdType(documentIdType);
        return this;
    }

    public UserBuilder setDocumentId(String documentId) {
        entity.setDocumentId(documentId);
        return this;
    }

    public UserBuilder setDomicileAddress(String domicileAddress) {
        entity.setDomicileAddress(domicileAddress);
        return this;
    }

    public UserBuilder setDomicileZipCode(String domicileZipCode) {
        entity.setDomicileZipCode(domicileZipCode);
        return this;
    }

    public UserBuilder setDomicileCity(String domicileCity) {
        entity.setDomicileCity(domicileCity);
        return this;
    }

    public UserBuilder setDomicileProvince(String domicileProvince) {
        entity.setDomicileProvince(domicileProvince);
        return this;
    }

    public UserBuilder setCurrentAddress(String currentAddress) {
        entity.setCurrentAddress(currentAddress);
        return this;
    }

    public UserBuilder setCurrentZipCode(String currentZipCode) {
        entity.setCurrentZipCode(currentZipCode);
        return this;
    }

    public UserBuilder setCurrentCity(String currentCity) {
        entity.setCurrentCity(currentCity);
        return this;
    }

    public UserBuilder setCurrentProvince(String currentProvince) {
        entity.setCurrentProvince(currentProvince);
        return this;
    }

    public UserBuilder setContactPhoneNumber(String contactPhoneNumber) {
        entity.setContactPhoneNumber(contactPhoneNumber);
        return this;
    }

    public UserBuilder setHomePhoneNumber(String homePhoneNumber) {
        entity.setHomePhoneNumber(homePhoneNumber);
        return this;
    }

    public UserBuilder setProjectPhoneNumber(String projectPhoneNumber) {
        entity.setProjectPhoneNumber(projectPhoneNumber);
        return this;
    }

    public UserBuilder setOfficialEmail(String officialEmail) {
        entity.setOfficialEmail(officialEmail);
        return this;
    }

    public UserBuilder setProjectEmail(String projectEmail) {
        entity.setProjectEmail(projectEmail);
        return this;
    }

    public UserBuilder setUniCity(UniCity uniCity) {
        entity.setUniCity(uniCity);
        return this;
    }

    public UserBuilder setUniSchool(UniSchool uniSchool) {
        entity.setUniSchool(uniSchool);
        return this;
    }

    public UserBuilder setUniDepartment(String uniDepartment) {
        entity.setUniDepartment(uniDepartment);
        return this;
    }

    public UserBuilder setUniDegree(String uniDegree) {
        entity.setUniDegree(uniDegree);
        return this;
    }

    public UserBuilder setUniCourse(UniCourse uniCourse) {
        entity.setUniCourse(uniCourse);
        return this;
    }

    public UserBuilder setUniIsSupplementaryYear(Boolean uniIsSupplementaryYear) {
        entity.setUniIsSupplementaryYear(uniIsSupplementaryYear);
        return this;
    }

    public UserBuilder setUniYear(Integer uniYear) {
        entity.setUniYear(uniYear);
        return this;
    }

    public UserBuilder setImei(String imei) {
        entity.setImei(imei);
        return this;
    }

    public UserBuilder setPassword(String password) {
        entity.setPassword(password);
        return this;
    }

    public UserBuilder setRegistrationDateTime(DateTime registrationDateTime) {
        entity.setRegistrationDateTime(registrationDateTime);
        return this;
    }

    public UserBuilder setGcmId(String gcmId) {
        entity.setGcmId(gcmId);
        return this;
    }

    public UserBuilder setFacebookEmail(String facebookEmail) {
        entity.setFacebookEmail(facebookEmail);
        return this;
    }

    public UserBuilder setSimStatus(SimStatus simStatus) {
        entity.setSimStatus(simStatus);
        return this;
    }

    public UserBuilder setWantsPhone(Boolean wantsPhone) {
        entity.setWantsPhone(wantsPhone);
        return this;
    }

    public UserBuilder setHasSIM(Boolean hasSIM) {
        entity.setHasSIM(hasSIM);
        return this;
    }

    public UserBuilder setHasPhone(Boolean hasPhone) {
        entity.setHasPhone(hasPhone);
        return this;
    }

    public UserBuilder setIccid(String iccid) {
        entity.setIccid(iccid);
        return this;
    }

    public UserBuilder setNewIccid(String newIccid) {
        entity.setNewIccid(newIccid);
        return this;
    }

    public UserBuilder setLastInvoiceScan(BinaryDocument lastInvoice) {
        entity.setLastInvoiceScan(lastInvoice);
        return this;
    }

    public UserBuilder setOriginalPhoneOperator(String originalPhoneOperator) {
        entity.setOriginalPhoneOperator(originalPhoneOperator);
        return this;
    }

    public UserBuilder setIdScan(BinaryDocument idScan) {
        entity.setIdScan(idScan);
        return this;
    }

    public UserBuilder setCf(String cf) {
        entity.setCf(cf);
        return this;
    }

    public UserBuilder setIsActive(Boolean isActive) {
        entity.setIsActive(isActive);
        return this;
    }

    public UserBuilder setPrivacyScan(BinaryDocument lastInvoice) {
        entity.setPrivacyScan(lastInvoice);
        return this;
    }

    public UserBuilder setPresaConsegnaPhoneScan(BinaryDocument presaConsegnaPhoneScan) {
        entity.setPresaConsegnaPhoneScan(presaConsegnaPhoneScan);
        return this;
    }

    public UserBuilder setPresaConsegnaSIMScan(BinaryDocument presaConsegnaSIMScan) {
        entity.setPresaConsegnaSIMScan(presaConsegnaSIMScan);
        return this;
    }

    public UserBuilder setIsMyCompanyRegistered(Boolean isMyCompanyRegistered) {
        entity.setIsMyCompanyRegistered(isMyCompanyRegistered);
        return this;
    }

    public UserBuilder setNotes(String notes) {
        entity.setNotes(notes);
        return this;
    }

    public UserBuilder setBadges(Set<Badge> badges) {
        entity.setBadges(badges);
        return this;
    }

    public UserBuilder setDevice(String device) {
        entity.setDevice(device);
        return this;
    }

    public UserBuilder setSocial(String social) {
        entity.setSocial(social);
        return this;
    }

    public UserBuilder setPhoto(String photo) {
        entity.setPhoto(photo);
        return this;
    }

    private UserBuilder setCurrentCountry(String currentCountry) {
        entity.setCurrentCountry(currentCountry);
        return this;
    }

    public UserBuilder setUniCodCourse(String uniCodCourse) {
        entity.setUniCodCourse(uniCodCourse);
        return this;
    }

    public UserBuilder setUniPhase(Integer uniPhase) {
        entity.setUniPhase(uniPhase);
        return this;
    }

    public UserBuilder setUniStatus(String uniStatus) {
        entity.setUniStatus(uniStatus);
        return this;
    }

    public UserBuilder setFileSource(String fileSource) {
        entity.setFileSource(fileSource);
        return this;
    }

    public UserBuilder setCurrentDistrict(String addressDistrict) {
        entity.setCurrentDistrict(addressDistrict);
        return this;
    }

    public UserBuilder setAgeRange(String ageRange) {
        entity.setAgeRange(ageRange);
        return this;
    }

    public UserBuilder setGuest(boolean isGuest) {
        
        entity.setGuest(isGuest);
        return this;
    }

    public UserBuilder setOccupation(String occupation) {
        
        entity.setOccupation(occupation);
        return this;
    }

    public UserBuilder setFacebookUrl(String facebookUrl) {
        
        entity.setFacebookUrl(facebookUrl);
        return this;
    }

    public UserBuilder setGoogleUrl(String googleUrl) {
        
        entity.setGoogleUrl(googleUrl);
        return this;
    }

    public UserBuilder setTwitterUrl(String twitterUrl) {
        
        entity.setTwitterUrl(twitterUrl);
        return this;
    }

    public UserBuilder setInstagramUrl(String instagramUrl) {
        
        entity.setInstagramUrl(instagramUrl);
        return this;
    }

    public UserBuilder setYoutubeUrl(String youtubeUrl) {
        
        entity.setYoutubeUrl(youtubeUrl);
        return this;
    }

    /**
     * @param xPlataform the xPlataform to set
     */
    public UserBuilder setxPlataform(String xPlataform) {
        entity.setxPlataform(xPlataform);
        return this;
    }

    /**
     * @param xDeviceModel the xDeviceModel to set
     */
    public UserBuilder setxDeviceModel(String xDeviceModel) {
        entity.setxDeviceModel(xDeviceModel);
        return this;
    }

    /**
     * @param xOsVersion the xOsVersion to set
     */
    public UserBuilder setxOsVersion(String xOsVersion) {
        entity.setxOsVersion(xOsVersion);
        return this;
    }

    /**
     * @param xManufacturer the xManufacturer to set
     */
    public UserBuilder setxManufacturer(String xManufacturer) {
        entity.setxManufacturer(xManufacturer);
        return this;
    }

    /**
     * @param xAppVersionName the xAppVersionName to set
     */
    public UserBuilder setxAppVersionName(String xAppVersionName) {
        entity.setxAppVersionName(xAppVersionName);
        return this;
    }

    /**
     * @param xAppVersionCode the xAppVersionCode to set
     */
    public UserBuilder setxAppVersionCode(String xAppVersionCode) {
        entity.setxAppVersionCode(xAppVersionCode);
        return this;
    }

    public UserBuilder setRemoved(boolean b) {
        entity.setRemoved(b);
        return this;
    }

    public UserBuilder setAuthorizeContact(boolean authorizeContact) {
        
        entity.setAuthorizeContact(authorizeContact);
        return this;
    }

    public UserBuilder setHasAllowOmbudsman(boolean hasAllowOmbudsman) {
        entity.setHasAllowOmbudsman(hasAllowOmbudsman);
        return this;
    }

    public UserBuilder setProgenitorId(Long progenitorId) {
        entity.setProgenitorId(progenitorId);
        return this;
    }

    public UserBuilder setSecondaryEmail(String emailSecondary) {
        entity.setSecondaryEmail(emailSecondary);
        return this;
    }
}