package it.unibo.paserver.domain.rest;

import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.Institutions;
import it.unibo.paserver.domain.SchoolCourse;
import it.unibo.paserver.domain.Level.LevelRank;
import it.unibo.paserver.domain.User;

/**
 * Utility Class.
 * Used to obtain a REST-friendly representation of
 * a Participact User
 * 
 * @author danielecampogiani
 * @see User
 *
 */
public class UserRestResult implements Comparable<UserRestResult> {

	private long id;
	private String name;
	private String surname;
	private String phone;
	private String address;
	private String photo;
	private LevelRank sensingMostLevel;
	private LevelRank photoLevel;
	private LevelRank questionnaireLevel;
	private LevelRank activityDetectionLevel;
	private String currentZipCode;
	private String currentNumber;
	private String currentCountry;
	private String currentCity;
	private String currentProvince;
	private Gender gender;
	private String birthdate;
	private String occupation;
	private String facebookUrl;
	private String googleUrl;
	private String twitterUrl;
	private String instagramUrl;
	private String youtubeUrl;
	private String ageRange;
	private Institutions institutionId;
	private SchoolCourse schoolCourseId;	
	private boolean authorizeContact = false;
	private String emailPrimary;
	private String emailSecondary;
	/**
	 * Use this constructor to obtain UserRestResult instance
	 * from a User object.
	 * 
	 * @param user the user used to instantiate a new UserRestResult object
	 */
	public UserRestResult(User user) {
		this.name = user.getName();
		this.surname = user.getSurname();
		this.id = user.getId();
	}

	public UserRestResult() {
		// TODO Auto-generated constructor stub
	}

	public void transpose(User user) {
		this.setName(user.getName());
		this.setId(user.getId());
		this.setEmailPrimary(user.getOfficialEmail());
		this.setEmailSecondary(user.getSecondaryEmail());
		
		this.setPhone(user.getHomePhoneNumber());
		this.setPhoto(user.getPhoto());
		this.setAddress(user.getCurrentAddress());
		this.setCurrentCountry(user.getCurrentCountry());
		this.setCurrentCity(user.getCurrentCity());
		this.setCurrentNumber(user.getCurrentNumber());
		this.setCurrentZipCode(user.getCurrentZipCode());
		this.setCurrentProvince(user.getCurrentProvince());
		this.setGender(user.getGender());		
		
		this.setOccupation(user.getOccupation());
		this.setFacebookUrl(user.getFacebookUrl());
		this.setGoogleUrl(user.getGoogleUrl());
		this.setTwitterUrl(user.getTwitterUrl());
		this.setInstagramUrl(user.getInstagramUrl());
		this.setYoutubeUrl(user.getYoutubeUrl());
		this.setAgeRange(user.getAgeRange());
		this.setInstitutionId(user.getInstitutionId());
		this.setSchoolCourseId(user.getSchoolCourseId());
		this.setPhoto(user.getPhoto());
		this.setAuthorizeContact(user.isAuthorizeContact());
	}
	/**
	 * Returns User's id.
	 * 
	 * @return User's id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets User's id
	 * 
	 * @param id User's id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns User's name.
	 * 
	 * @return User's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets User's name.
	 * 
	 * @param name User's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns User's surname.
	 * 
	 * @return User's surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Sets User's surname.
	 * 
	 * @param surname User's surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Returns the User's LevelRank for ActionType SENSING_MOST.
	 * 
	 * @return the User's LevelRank for ActionType SENSING_MOST
	 * @see LevelRank
	 * @see Level
	 * @see ActionType
	 */
	public LevelRank getSensingMostLevel() {
		return sensingMostLevel;
	}

	/**
	 * Sets the User's LevelRank for ActionType SENSING_MOST
	 * 
	 * @param sensingMostLevel the User's LevelRank for ActionType SENSING_MOST
	 * @see LevelRank
	 * @see Level
	 * @see ActionType
	 */
	public void setSensingMostLevel(LevelRank sensingMostLevel) {
		this.sensingMostLevel = sensingMostLevel;
	}

	/**
	 * Returns the User's LevelRank for ActionType PHOTO.
	 * 
	 * @return the User's LevelRank for ActionType PHOTO
	 * @see LevelRank
	 * @see Level
	 * @see ActionType
	 */
	public LevelRank getPhotoLevel() {
		return photoLevel;
	}

	/**
	 * Sets the User's LevelRank for ActionType PHOTO
	 * 
	 * @param sensingMostLevel the User's LevelRank for ActionType PHOTO
	 * @see LevelRank
	 * @see Level
	 * @see ActionType
	 */
	public void setPhotoLevel(LevelRank photoLevel) {
		this.photoLevel = photoLevel;
	}

	/**
	 * Returns the User's LevelRank for ActionType QUESTIONNAIRE.
	 * 
	 * @return the User's LevelRank for ActionType QUESTIONNAIRE
	 * @see LevelRank
	 * @see Level
	 * @see ActionType
	 */
	public LevelRank getQuestionnaireLevel() {
		return questionnaireLevel;
	}

	/**
	 * Sets the User's LevelRank for ActionType QUESTIONNAIRE
	 * 
	 * @param sensingMostLevel the User's LevelRank for ActionType QUESTIONNAIRE
	 * @see LevelRank
	 * @see Level
	 * @see ActionType
	 */
	public void setQuestionnaireLevel(LevelRank questionnaireLevel) {
		this.questionnaireLevel = questionnaireLevel;
	}

	/**
	 * Returns the User's LevelRank for ActionType ACTIVITY_DETECTION.
	 * 
	 * @return the User's LevelRank for ActionType ACTIVITY_DETECTION
	 * @see LevelRank
	 * @see Level
	 * @see ActionType
	 */
	public LevelRank getActivityDetectionLevel() {
		return activityDetectionLevel;
	}

	/**
	 * Sets the User's LevelRank for ActionType ACTIVITY_DETECTION
	 * 
	 * @param sensingMostLevel the User's LevelRank for ActionType ACTIVITY_DETECTION
	 * @see LevelRank
	 * @see Level
	 * @see ActionType
	 */
	public void setActivityDetectionLevel(LevelRank activityDetectionLevel) {
		this.activityDetectionLevel = activityDetectionLevel;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getPhoto() {
		return photo;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRestResult other = (UserRestResult) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	@Override
	public int compareTo(UserRestResult o) {
		if (o == null) {
			return 1;
		}
		UserRestResult u = (UserRestResult) o;
		if (equals(u)) {
			return 0;
		}

		int nameOrder = getName().toUpperCase().compareTo(
				u.getName().toUpperCase());
		if (nameOrder != 0) {
			return nameOrder;
		}

		int surnameOrder = getSurname().toUpperCase().compareTo(u.getSurname().toUpperCase());
		return surnameOrder;
	}

	/**
	 * @return the currentZipCode
	 */
	public String getCurrentZipCode() {
		return currentZipCode;
	}

	/**
	 * @param currentZipCode the currentZipCode to set
	 */
	public void setCurrentZipCode(String currentZipCode) {
		this.currentZipCode = currentZipCode;
	}

	/**
	 * @return the currentNumber
	 */
	public String getCurrentNumber() {
		return currentNumber;
	}

	/**
	 * @param currentNumber the currentNumber to set
	 */
	public void setCurrentNumber(String currentNumber) {
		this.currentNumber = currentNumber;
	}

	/**
	 * @return the currentCountry
	 */
	public String getCurrentCountry() {
		return currentCountry;
	}

	/**
	 * @param currentCountry the currentCountry to set
	 */
	public void setCurrentCountry(String currentCountry) {
		this.currentCountry = currentCountry;
	}

	/**
	 * @return the currentCity
	 */
	public String getCurrentCity() {
		return currentCity;
	}

	/**
	 * @param currentCity the currentCity to set
	 */
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	
	public String getCurrentProvince() {
		return currentProvince;
	}

	public void setCurrentProvince(String currentProvince) {
		this.currentProvince = currentProvince;
	}

	public void setGender(Gender gender) {
		
		this.gender = gender;
	}

	public void setBirthdate(String birthdate) {
		
		this.birthdate = birthdate;
	}

	public Gender getGender() {
		return gender;
	}

	public String getBirthdate() {
		return birthdate;
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
	 * @return the ageRange
	 */
	public String getAgeRange() {
		return ageRange;
	}

	/**
	 * @param ageRange the ageRange to set
	 */
	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}

	/**
	 * @return the institutionId
	 */
	public Institutions getInstitutionId() {
		return institutionId;
	}

	/**
	 * @param institutionId the institutionId to set
	 */
	public void setInstitutionId(Institutions institutionId) {
		this.institutionId = institutionId;
	}

	/**
	 * @return the schoolCourseId
	 */
	public SchoolCourse getSchoolCourseId() {
		return schoolCourseId;
	}

	/**
	 * @param schoolCourseId the schoolCourseId to set
	 */
	public void setSchoolCourseId(SchoolCourse schoolCourseId) {
		this.schoolCourseId = schoolCourseId;
	}

	/**
	 * @return the authorizeContact
	 */
	public boolean isAuthorizeContact() {
		return authorizeContact;
	}

	/**
	 * @param authorizeContact the authorizeContact to set
	 */
	public void setAuthorizeContact(boolean authorizeContact) {
		this.authorizeContact = authorizeContact;
	}

	/**
	 * @return the emailPrimary
	 */
	public String getEmailPrimary() {
		return emailPrimary;
	}

	/**
	 * @param emailPrimary the emailPrimary to set
	 */
	public void setEmailPrimary(String emailPrimary) {
		this.emailPrimary = emailPrimary;
	}

	/**
	 * @return the emailSecondary
	 */
	public String getEmailSecondary() {
		return emailSecondary;
	}

	/**
	 * @param emailSecondary the emailSecondary to set
	 */
	public void setEmailSecondary(String emailSecondary) {
		this.emailSecondary = emailSecondary;
	}		
}