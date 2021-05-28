package it.unibo.paserver.domain;

import java.util.HashMap;
import java.util.Map;

import br.com.bergmannsoft.utils.Validator;

/**
 * Tipos de filtros permitidos na barra de consulta
 * 
 * @author Claudio
 */
public enum FilterByUser {
	FILTER_NAME("name"), 
	FILTER_SURNAME("surname"),
	FILTER_BIRTHDATE("birthdate"), 
	FILTER_GENDER("gender"), 
	FILTER_CONTACTPHONENUMBER("contactPhoneNumber"), 
	FILTER_DEVICE("device"), 
	FILTER_DOCUMENTID("documentId"), 
	FILTER_DOCUMENTIDTYPE("documentIdType"), 
	FILTER_HOMEPHONENUMBER("homePhoneNumber"), 
	FILTER_INSTITUTIONID("institutionId"), 
	FILTER_NOTES("notes"), 
	FILTER_FILESOURCE("fileSource"),
	FILTER_OFFICIALEMAIL("officialEmail"), 
	FILTER_EMAIL("email"),
	FILTER_EMAIL_CAPITALIZE("Email"), 
	FILTER_PASSWORD("password"), 
	FILTER_SCHOOLCOURSEID("schoolCourseId"), 
	FILTER_UNICOURSE("uniCourse"), 
	FILTER_UNIYEAR("uniYear"), 
	FILTER_ADDRESS("currentAddress"),
	FILTER_ADDRESSDISTRICT("currentDistrict"), 
	FILTER_ADDRESSCITY("currentCity"), 
	FILTER_ADDRESSCOUNTRY("currentCountry"), 
	FILTER_ADDRESSNUMBER("currentNumber"),
	FILTER_ADDRESSPOSTALCODE("currentZipCode"), 
	FILTER_ADDRESSSTATE("currentProvince"),
	
	FILTER_UNIDEPARTMENT("uniDepartment"),
	FILTER_UNICODCOURSE("uniCodCourse"),
	FILTER_SCHOOLCOURSE("schoolCourse"),
	FILTER_UNIPHASE("uniPhase"),
	FILTER_START("start"), 
	FILTER_CREATIONDATE("creationDate"),
	FILTER_UNISTATUS("uniStatus"),
	FILTER_USERID("userId"),
	FILTER_TASKID("taskId"),
	FILTER_TASKID_RUNNING("campaignId"), 
	FILTER_USERLISTID("userListId");

	private final String name;
	public static int COUNT = FilterByUser.values().length;
	// Reverse-lookup map for getting a day from an abbreviation
	public static final Map<String, FilterByUser> lookup = new HashMap<String, FilterByUser>();

	private FilterByUser(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}

	public String toString() {
		return this.name;
	}

	public static FilterByUser getEnumByString(String value) {
		for (FilterByUser e : FilterByUser.values()) {
			if (value.equals(e.name))
				return e;
		}
		return null;
	}

	public static boolean hasEnumByValue(String value) {
		if (!Validator.isEmptyString(value)) {
			for (FilterByUser e : FilterByUser.values()) {
				if (value.equals(e.name))
					return true;
			}
		}
		return false;
	}
}
