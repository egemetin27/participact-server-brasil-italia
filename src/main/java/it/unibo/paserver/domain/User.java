package it.unibo.paserver.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.bergmannsoft.utils.Validator;

@Entity
@Table(name = "user_accounts")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User implements Serializable, Comparable<User> {
	private static MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("sha-256");
	private static final long serialVersionUID = -7480986502756652528L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Email
	@NotEmpty
	@Column(unique = true)
	private String officialEmail;
	private String secondaryEmail;
	private String surname;
	@NotEmpty
	private String name;
	private String social;
	private String photo;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate birthdate;
	// @Column(unique = true)
	private String cf;
	private String contactPhoneNumber;
	private String currentAddress;
	private String currentDistrict;
	private String currentCity;
	private String currentProvince;
	private String currentZipCode;
	private String currentNumber;
	private String currentCountry;
	private String mapLat;
	private String mapLng;	
	private String documentId;
	
	private String occupation;
	private String facebookUrl;
	private String googleUrl;
	private String twitterUrl;
	private String instagramUrl;
	private String youtubeUrl;
	
	@Column(name = "progenitorId")
	@Index(name = "idx_user_accounts_progenitor_id")
	private Long progenitorId = 0L;
	@Column(name = "isGuest", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isGuest = false;	
	@NotNull
	@Enumerated(EnumType.STRING)
	private DocumentIdType documentIdType = DocumentIdType.NONE;
	private String domicileAddress;
	private String domicileCity;
	private String domicileProvince;
	private String domicileZipCode;
	@Email
	private String facebookEmail;
	@Column(columnDefinition = "TEXT")
	private String gcmId;
	@Enumerated(EnumType.STRING)
	private Gender gender = Gender.NONE;
	@NotNull
	private Boolean hasIdScan = Boolean.FALSE;
	@NotNull
	private Boolean hasLastInvoiceScan = Boolean.FALSE;
	@NotNull
	private Boolean hasPhone = Boolean.FALSE;
	@NotNull
	private Boolean hasPresaConsegnaPhoneScan = Boolean.FALSE;
	@NotNull
	private Boolean hasPresaConsegnaSIMScan = Boolean.FALSE;
	@NotNull
	private Boolean hasPrivacyScan = Boolean.FALSE;
	@NotNull
	private Boolean hasSIM = Boolean.FALSE;
	private String homePhoneNumber;
	@Column(unique = true)
	private String iccid;
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private BinaryDocument idScan;
	private String imei;
	@NotNull
	private Boolean isActive = Boolean.TRUE;
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private BinaryDocument lastInvoiceScan;
	private String originalPhoneOperator;
	@NotEmpty
	private String password;
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private BinaryDocument presaConsegnaPhoneScan;
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private BinaryDocument presaConsegnaSIMScan;
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private BinaryDocument privacyScan;
	@Email
	@NotEmpty
	@Column(unique = true)
	private String projectEmail;
	private String projectPhoneNumber;
	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime registrationDateTime;
	@NotNull
	@Enumerated(EnumType.STRING)
	private SimStatus simStatus = SimStatus.NONE;
	@NotNull
	@Enumerated(EnumType.STRING)
	private UniCity uniCity = UniCity.FLORIANOPOLIS;
	@NotNull
	@Enumerated(EnumType.STRING)
	private UniCourse uniCourse = UniCourse.NONE;
	private String uniDegree;
	private String uniDepartment = " ";
	private String uniCodCourse = " ";
	private Integer uniPhase;
	private String uniStatus;
	private String fileSource;
	private String ageRange;
	@NotNull
	private Boolean uniIsSupplementaryYear;
	private Boolean authorizeContact = false;		
	@NotNull
	@Enumerated(EnumType.STRING)
	private UniSchool uniSchool = UniSchool.NONE;
	private Integer uniYear;
	@NotNull
	private Boolean wantsPhone = Boolean.FALSE;
	@NotNull
	private Boolean isMyCompanyRegistered = Boolean.FALSE;
	@NotEmpty
	private String device = "N/A";
	@Column(name = "deviceVersion",nullable = true)
	private Integer DeviceVersion = 0;
	
	@Column(unique = true)
	private String newIccid;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate receivedPhoneOn;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate returnedPhoneOn;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate receivedSIMOn;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate returnedSIMOn;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate activatedSIMOn;
	@Column(columnDefinition = "TEXT")
	private String notes;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "creationDate", updatable = false, nullable = false)
	private DateTime creationDate;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "updateDate", updatable = true, nullable = false)
	private DateTime updateDate;
	@Column(name = "removed", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean removed = false;
	@ManyToOne
    @JoinColumn(name = "institutionId")
	private Institutions institutionId;	
	@ManyToOne
    @JoinColumn(name = "schoolCourseId")
	private SchoolCourse schoolCourseId;		
	// Added for gamification
	@ManyToMany(targetEntity = AbstractBadge.class, fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "User_Badges", joinColumns = {@JoinColumn(name = "user_Id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "badge_Id", referencedColumnName = "badge_id") })
	private Set<Badge> badges;
	//Special Report
	@OneToMany(mappedBy = "user", targetEntity = IssueAbuse.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<IssueAbuse> abuses;
	
	@OneToMany(mappedBy = "user", targetEntity = FeedbackReport.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<FeedbackReport> feedbacks;
	
	@OneToMany(mappedBy = "user", targetEntity = IssueReport.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<IssueReport> issues;
	
	@Column(name = "xPlataform",nullable = true, columnDefinition = "TEXT")
	private String xPlataform;
	@Column(name = "xDeviceModel",nullable = true, columnDefinition = "TEXT")
	private String xDeviceModel;
	@Column(name = "xOsVersion",nullable = true, columnDefinition = "TEXT")
	private String xOsVersion;
	@Column(name = "xManufacturer",nullable = true, columnDefinition = "TEXT")
	private String xManufacturer;
	@Column(name = "xAppVersionName",nullable = true, columnDefinition = "TEXT")
	private String xAppVersionName;
	@Column(name = "xAppVersionCode",nullable = true, columnDefinition = "TEXT")
	private String xAppVersionCode;

	@Column(name = "hasAllowOmbudsman", nullable = false)
	private boolean hasAllowOmbudsman = false;

	//Setters/Getters
	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = Validator.isEmptyString(device) ? "N/A" : device;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDate getActivatedSIMOn() {
		return activatedSIMOn;
	}

	public void setActivatedSIMOn(LocalDate activatedSIMOn) {
		this.activatedSIMOn = activatedSIMOn;
	}

	public LocalDate getReceivedPhoneOn() {
		return receivedPhoneOn;
	}

	public void setReceivedPhoneOn(LocalDate receivedPhoneOn) {
		this.receivedPhoneOn = receivedPhoneOn;
	}

	public LocalDate getReturnedPhoneOn() {
		return returnedPhoneOn;
	}

	public void setReturnedPhoneOn(LocalDate returnedPhoneOn) {
		this.returnedPhoneOn = returnedPhoneOn;
	}

	public LocalDate getReceivedSIMOn() {
		return receivedSIMOn;
	}

	public void setReceivedSIMOn(LocalDate receivedSIMOn) {
		this.receivedSIMOn = receivedSIMOn;
	}

	public LocalDate getReturnedSIMOn() {
		return returnedSIMOn;
	}

	public void setReturnedSIMOn(LocalDate returnedSIMOn) {
		this.returnedSIMOn = returnedSIMOn;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public String getCf() {
		return cf;
	}

	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public String getCurrentProvince() {
		return currentProvince;
	}

	public String getCurrentZipCode() {
		return currentZipCode;
	}

	public String getDocumentId() {
		return documentId;
	}

	public DocumentIdType getDocumentIdType() {
		return documentIdType;
	}

	public String getDomicileAddress() {
		return domicileAddress;
	}

	public String getDomicileCity() {
		return domicileCity;
	}

	public String getDomicileProvince() {
		return domicileProvince;
	}

	public String getDomicileZipCode() {
		return domicileZipCode;
	}

	public String getFacebookEmail() {
		return facebookEmail;
	}

	public String getGcmId() {
		return gcmId;
	}

	public Gender getGender() {
		return gender;
	}

	public Boolean getHasIdScan() {
		return hasIdScan;
	}

	public Boolean getHasLastInvoiceScan() {
		return hasLastInvoiceScan;
	}

	public Boolean getHasPhone() {
		return hasPhone;
	}

	public Boolean getHasPresaConsegnaPhoneScan() {
		return hasPresaConsegnaPhoneScan;
	}

	public Boolean getHasPresaConsegnaSIMScan() {
		return hasPresaConsegnaSIMScan;
	}

	public Boolean getHasPrivacyScan() {
		return hasPrivacyScan;
	}

	public Boolean getHasSIM() {
		return hasSIM;
	}

	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}

	public String getIccid() {
		return iccid;
	}

	public Long getId() {
		return id;
	}

	public BinaryDocument getIdScan() {
		return idScan;
	}

	public String getImei() {
		return imei;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public BinaryDocument getLastInvoiceScan() {
		return lastInvoiceScan;
	}

	public String getName() {
		return name;
	}

	public String getNewIccid() {
		return newIccid;
	}

	public String getOfficialEmail() {
		return officialEmail;
	}

	public String getOriginalPhoneOperator() {
		return originalPhoneOperator;
	}

	public String getPassword() {
		return password;
	}

	public BinaryDocument getPresaConsegnaPhoneScan() {
		return presaConsegnaPhoneScan;
	}

	public BinaryDocument getPresaConsegnaSIMScan() {
		return presaConsegnaSIMScan;
	}

	public BinaryDocument getPrivacyScan() {
		return privacyScan;
	}

	public String getProjectEmail() {
		return projectEmail;
	}

	public String getProjectPhoneNumber() {
		return projectPhoneNumber;
	}

	public DateTime getRegistrationDateTime() {
		return registrationDateTime;
	}

	public String getSurname() {
		String lastname = surname;
		if(lastname == null || lastname == "." || lastname.length()<3) {
			lastname="";
		}
		return lastname;
	}

	public UniCity getUniCity() {
		return uniCity;
	}

	public UniCourse getUniCourse() {
		return uniCourse;
	}

	public String getUniDegree() {
		return uniDegree;
	}

	public String getUniDepartment() {
		return uniDepartment;
	}

	public Boolean getUniIsSupplementaryYear() {
		return uniIsSupplementaryYear;
	}

	public UniSchool getUniSchool() {
		return uniSchool;
	}

	public Integer getUniYear() {
		return uniYear;
	}

	public SimStatus getSimStatus() {
		return simStatus;
	}

	public Boolean getWantsPhone() {
		return wantsPhone;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Set<Badge> getBadges() {
		return badges;
	}

	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}

	public void setCredentials(String password) {
		String endocdedPw = encoder.encodePassword(password, getOfficialEmail());
		setPassword(endocdedPw);
	}

	public void setCredentials(String officialEmail, String password) {
		setOfficialEmail(officialEmail);
		String encodedPw = encoder.encodePassword(password, getOfficialEmail());
		setPassword(encodedPw);
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public void setCurrentProvince(String currentProvince) {
		this.currentProvince = currentProvince;
	}

	public void setCurrentZipCode(String currentZipCode) {
		this.currentZipCode = currentZipCode;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public void setDocumentIdType(DocumentIdType documentIdType) {
		this.documentIdType = documentIdType;
	}

	public void setDomicileAddress(String domicileAddress) {
		this.domicileAddress = domicileAddress;
	}

	public void setDomicileCity(String domicileCity) {
		this.domicileCity = domicileCity;
	}

	public void setDomicileProvince(String domicileProvince) {
		this.domicileProvince = domicileProvince;
	}

	public void setDomicileZipCode(String domicileZipCode) {
		this.domicileZipCode = domicileZipCode;
	}

	public void setFacebookEmail(String facebookEmail) {
		this.facebookEmail = facebookEmail;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setHasIdScan(Boolean hasIdScan) {
		this.hasIdScan = hasIdScan;
	}

	public void setHasLastInvoiceScan(Boolean hasLastInvoiceScan) {
		this.hasLastInvoiceScan = hasLastInvoiceScan;
	}

	public void setHasPhone(Boolean hasPhone) {
		this.hasPhone = hasPhone;
	}

	public void setHasPresaConsegnaPhoneScan(Boolean hasPresaConsegnaPhoneScan) {
		this.hasPresaConsegnaPhoneScan = hasPresaConsegnaPhoneScan;
	}

	public void setHasPresaConsegnaSIMScan(Boolean hasPresaConsegnaSIMScan) {
		this.hasPresaConsegnaSIMScan = hasPresaConsegnaSIMScan;
	}

	public void setHasPrivacyScan(Boolean hasPrivacyScan) {
		this.hasPrivacyScan = hasPrivacyScan;
	}

	public void setHasSIM(Boolean hasSIM) {
		this.hasSIM = hasSIM;
	}

	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIdScan(BinaryDocument idScan) {
		this.idScan = idScan;
		if (idScan != null) {
			setHasIdScan(Boolean.TRUE);
			idScan.setOwner(this);
		} else {
			setHasIdScan(Boolean.FALSE);
		}
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setLastInvoiceScan(BinaryDocument lastInvoiceScan) {
		this.lastInvoiceScan = lastInvoiceScan;
		if (lastInvoiceScan != null) {
			setHasLastInvoiceScan(Boolean.TRUE);
			lastInvoiceScan.setOwner(this);
		} else {
			setHasLastInvoiceScan(Boolean.FALSE);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNewIccid(String newIccid) {
		this.newIccid = newIccid;
	}

	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}

	public void setOriginalPhoneOperator(String originalPhoneOperator) {
		this.originalPhoneOperator = originalPhoneOperator;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPresaConsegnaPhoneScan(BinaryDocument presaConsegnaPhoneScan) {
		this.presaConsegnaPhoneScan = presaConsegnaPhoneScan;
		if (presaConsegnaPhoneScan != null) {
			setHasPresaConsegnaPhoneScan(Boolean.TRUE);
			presaConsegnaPhoneScan.setOwner(this);
		} else {
			setHasPresaConsegnaPhoneScan(Boolean.FALSE);
		}
	}

	public void setPresaConsegnaSIMScan(BinaryDocument presaConsegnaSIMScan) {
		this.presaConsegnaSIMScan = presaConsegnaSIMScan;
		if (presaConsegnaPhoneScan != null) {
			setHasPresaConsegnaSIMScan(Boolean.TRUE);
			presaConsegnaSIMScan.setOwner(this);
		} else {
			setHasPresaConsegnaSIMScan(Boolean.FALSE);
		}
	}

	public void setPrivacyScan(BinaryDocument privacyScan) {
		this.privacyScan = privacyScan;
		if (privacyScan != null) {
			setHasPrivacyScan(Boolean.TRUE);
			privacyScan.setOwner(this);
		} else {
			setHasPrivacyScan(Boolean.FALSE);
		}
	}

	public String getSocial() {
		return social;
	}

	public void setProjectEmail(String projectEmail) {
		this.projectEmail = projectEmail;
	}

	public void setProjectPhoneNumber(String projectPhoneNumber) {
		this.projectPhoneNumber = projectPhoneNumber;
	}

	public void setRegistrationDateTime(DateTime registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setUniCity(UniCity uniCity) {
		this.uniCity = uniCity;
	}

	public void setUniCourse(UniCourse uniCourse) {
		this.uniCourse = uniCourse;
	}

	public void setUniDegree(String uniDegree) {
		this.uniDegree = Validator.isEmptyString(uniDegree) ? "N/A" : uniDegree;
	}

	public void setUniDepartment(String uniDepartment) {
		this.uniDepartment = uniDepartment;
	}

	public void setUniIsSupplementaryYear(Boolean uniIsSupplementaryYear) {
		this.uniIsSupplementaryYear = uniIsSupplementaryYear;
	}

	public void setUniSchool(UniSchool uniSchool) {
		this.uniSchool = uniSchool;
	}

	public void setUniYear(Integer uniYear) {
		this.uniYear = uniYear;
	}

	public void setSimStatus(SimStatus simStatus) {
		this.simStatus = simStatus;
	}

	public void setWantsPhone(Boolean wantsPhone) {
		this.wantsPhone = wantsPhone;
	}

	public Boolean getIsMyCompanyRegistered() {
		return isMyCompanyRegistered;
	}

	public void setIsMyCompanyRegistered(Boolean isMyCompanyRegistered) {
		this.isMyCompanyRegistered = isMyCompanyRegistered;
	}

	public void setBadges(Set<Badge> badges) {
		this.badges = badges;
	}

	public void setSocial(String social) {
		this.social = social;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public int hashCode() {
		return getOfficialEmail().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof User)) {
			return false;
		}
		User other = (User) o;
		return getOfficialEmail().equals(other.getOfficialEmail());
	}

	@Override
	public int compareTo(User o) {
		if (o == null) {
			return 1;
		}
		User u = (User) o;
		if (equals(u)) {
			return 0;
		}
		int surnameOrder = getSurname().toUpperCase().compareTo(u.getSurname().toUpperCase());
		if (surnameOrder != 0) {
			return surnameOrder;
		}
		int nameOrder = getName().toUpperCase().compareTo(u.getName().toUpperCase());
		if (nameOrder != 0) {
			return nameOrder;
		}
		return getBirthdate().compareTo(u.getBirthdate());
	}

	public void setCurrentNumber(String n) {
		this.currentNumber = n;
	}

	public String getCurrentNumber() {
		return this.currentNumber;
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}

	public DateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public String getCurrentCountry() {
		return currentCountry;
	}

	public void setCurrentCountry(String currentCountry) {
		this.currentCountry = currentCountry;
	}

	public Institutions getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Institutions institutionId) {
		this.institutionId = institutionId;
	}

	public SchoolCourse getSchoolCourseId() {
		return schoolCourseId;
	}

	public void setSchoolCourseId(SchoolCourse schoolCourseId) {
		this.schoolCourseId = schoolCourseId;
	}

	public String getMapLat() {
		return mapLat;
	}

	public void setMapLat(String mapLat) {
		this.mapLat = mapLat;
	}

	public String getMapLng() {
		return mapLng;
	}

	public void setMapLng(String mapLng) {
		this.mapLng = mapLng;
	}

	public String getUniCodCourse() {
		return uniCodCourse;
	}

	public void setUniCodCourse(String uniCodCourse) {
		this.uniCodCourse = uniCodCourse;
	}

	public Integer getUniPhase() {
		return uniPhase;
	}

	public void setUniPhase(Integer uniPhase) {
		this.uniPhase = uniPhase;
	}

	public String getUniStatus() {
		return uniStatus;
	}

	public void setUniStatus(String uniStatus) {
		this.uniStatus = uniStatus;
	}

	public String getFileSource() {
		return fileSource;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}

	public String getCurrentDistrict() {
		return currentDistrict;
	}

	public void setCurrentDistrict(String currentDistrict) {
		this.currentDistrict = currentDistrict;
	}

	public String getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}

	public Integer getDeviceVersion() {
		return DeviceVersion;
	}

	public void setDeviceVersion(Integer deviceVersion) {
		DeviceVersion = deviceVersion;
	}

	/**
	 * @return the progenitorId
	 */
	public Long getProgenitorId() {
		return progenitorId;
	}

	/**
	 * @param progenitorId the progenitorId to set
	 */
	public void setProgenitorId(Long progenitorId) {
		this.progenitorId = progenitorId;
	}

	/**
	 * @return the isGuest
	 */
	public boolean isGuest() {
		return isGuest;
	}

	/**
	 * @param isGuest the isGuest to set
	 */
	public void setGuest(boolean isGuest) {
		this.isGuest = isGuest;
	}

	/**
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * @param occupation the occupation to set
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	/**
	 * @return the facebookUrl
	 */
	public String getFacebookUrl() {
		return facebookUrl;
	}

	/**
	 * @param facebookUrl the facebookUrl to set
	 */
	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}

	/**
	 * @return the googleUrl
	 */
	public String getGoogleUrl() {
		return googleUrl;
	}

	/**
	 * @param googleUrl the googleUrl to set
	 */
	public void setGoogleUrl(String googleUrl) {
		this.googleUrl = googleUrl;
	}

	/**
	 * @return the twitterUrl
	 */
	public String getTwitterUrl() {
		return twitterUrl;
	}

	/**
	 * @param twitterUrl the twitterUrl to set
	 */
	public void setTwitterUrl(String twitterUrl) {
		this.twitterUrl = twitterUrl;
	}

	/**
	 * @return the instagramUrl
	 */
	public String getInstagramUrl() {
		return instagramUrl;
	}

	/**
	 * @param instagramUrl the instagramUrl to set
	 */
	public void setInstagramUrl(String instagramUrl) {
		this.instagramUrl = instagramUrl;
	}

	/**
	 * @return the youtubeUrl
	 */
	public String getYoutubeUrl() {
		return youtubeUrl;
	}

	/**
	 * @param youtubeUrl the youtubeUrl to set
	 */
	public void setYoutubeUrl(String youtubeUrl) {
		this.youtubeUrl = youtubeUrl;
	}

	/**
	 * @return the xPlataform
	 */
	public String getxPlataform() {
		return xPlataform;
	}

	/**
	 * @param xPlataform the xPlataform to set
	 */
	public void setxPlataform(String xPlataform) {
		this.xPlataform = xPlataform;
	}

	/**
	 * @return the xDeviceModel
	 */
	public String getxDeviceModel() {
		return xDeviceModel;
	}

	/**
	 * @param xDeviceModel the xDeviceModel to set
	 */
	public void setxDeviceModel(String xDeviceModel) {
		this.xDeviceModel = xDeviceModel;
	}

	/**
	 * @return the xOsVersion
	 */
	public String getxOsVersion() {
		return xOsVersion;
	}

	/**
	 * @param xOsVersion the xOsVersion to set
	 */
	public void setxOsVersion(String xOsVersion) {
		this.xOsVersion = xOsVersion;
	}

	/**
	 * @return the xManufacturer
	 */
	public String getxManufacturer() {
		return xManufacturer;
	}

	/**
	 * @param xManufacturer the xManufacturer to set
	 */
	public void setxManufacturer(String xManufacturer) {
		this.xManufacturer = xManufacturer;
	}

	/**
	 * @return the xAppVersionName
	 */
	public String getxAppVersionName() {
		return xAppVersionName;
	}

	/**
	 * @param xAppVersionName the xAppVersionName to set
	 */
	public void setxAppVersionName(String xAppVersionName) {
		this.xAppVersionName = xAppVersionName;
	}

	/**
	 * @return the xAppVersionCode
	 */
	public String getxAppVersionCode() {
		return xAppVersionCode;
	}

	/**
	 * @param xAppVersionCode the xAppVersionCode to set
	 */
	public void setxAppVersionCode(String xAppVersionCode) {
		this.xAppVersionCode = xAppVersionCode;
	}

	public void setAuthorizeContact(boolean authorizeContact) {
		
		this.authorizeContact  = authorizeContact;
	}

	/**
	 * @return the authorizeContact
	 */
	public boolean isAuthorizeContact() {
		return authorizeContact;
	}

	/**
	 * @return the secondaryEmail
	 */
	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	/**
	 * @param secondaryEmail the secondaryEmail to set
	 */
	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public boolean getHasAllowOmbudsman() {
		return hasAllowOmbudsman;
	}

	public void setHasAllowOmbudsman(boolean hasAllowOmbudsman) {
		this.hasAllowOmbudsman = hasAllowOmbudsman;
	}



}