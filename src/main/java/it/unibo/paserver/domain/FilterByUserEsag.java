package it.unibo.paserver.domain;

import java.util.HashMap;
import java.util.Map;

import br.com.bergmannsoft.utils.Validator;

/**
 * Tipos de filtros permitidos na barra de consulta
 * 
 * @author Claudio
 */
public enum FilterByUserEsag {
	FILTER_NAME("Nome"), 
	FILTER_BIRTHDATE("Data Nascimento"), 
	FILTER_GENDER("G�nero"), 
	FILTER_EMAIL("Email"),  
	FILTER_CONTACTPHONENUMBER("Fone"),
	FILTER_HOMEPHONENUMBER("Cel"),

	FILTER_ADDRESS("Rua"),
	FILTER_ADDRESSDISTRICT("Bairro"),	
	FILTER_ADDRESSPOSTALCODE("CEP"), 
	
	FILTER_UNIDEPARTMENT("Centro"),
	FILTER_UNICODCOURSE("Cod Cur"),
	FILTER_SCHOOLCOURSE("Curso"),
	FILTER_UNIPHASE("Fase"),
	FILTER_UNISTATUS("Status"),

	FILTER_ADDRESSSTATE("currentProvince"),
	FILTER_ADDRESSCITY("currentCity"), 
	FILTER_ADDRESSCOUNTRY("currentCountry"), 
	FILTER_ADDRESSNUMBER("currentNumber");
	
	private final String name;
	public static int COUNT = FilterByUserEsag.values().length;
	// Reverse-lookup map for getting a day from an abbreviation
	public static final Map<String, FilterByUserEsag> lookup = new HashMap<String, FilterByUserEsag>();

	private FilterByUserEsag(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}

	public String toString() {
		return this.name;
	}

	public static FilterByUserEsag getEnumByString(String value) {
		for (FilterByUserEsag e : FilterByUserEsag.values()) {
			if (value.equals(e.name))
				return e;
		}
		return null;
	}

	public static boolean hasEnumByValue(String value) {
		if (!Validator.isEmptyString(value)) {
			for (FilterByUserEsag e : FilterByUserEsag.values()) {
				if (value.equals(e.name))
					return true;
			}
		}
		return false;
	}
}
