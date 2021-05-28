package it.unibo.paserver.domain;

import java.util.HashMap;
import java.util.Map;

import br.com.bergmannsoft.utils.Validator;

/**
 * Tipos de filtros permitidos na barra de consulta
 * 
 * @author Claudio
 */
public enum FilterByTask {
	FILTER_NAME("name"), 
	FILTER_DESCRIPTION("description"), 
	FILTER_START("start"),	
	FILTER_DEADLINE("deadline"), 
	FILTER_CANBEREFUSED("canBeRefused"), 
	FILTER_PIPELINE_TYPE("pipelineType"), 
	FILTER_HAS_PHOTO("hasPhotos"),
	FILTER_HAS_QUESTION("hasQuestionnaire"),
	FILTER_TASK_ID("id"),
	FILTER_PARENT_ID("parentId"),
	FILTER_FIXED_ID("id");

	private final String name;
	public static int COUNT = FilterByTask.values().length;
	public static final Map<String, FilterByTask> lookup = new HashMap<String, FilterByTask>();

	private FilterByTask(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}

	public String toString() {
		return this.name;
	}

	public static FilterByTask getEnumByString(String value) {
		for (FilterByTask e : FilterByTask.values()) {
			if (value.equals(e.name))
				return e;
		}
		return null;
	}

	public static boolean hasEnumByValue(String value) {
		if (!Validator.isEmptyString(value)) {
			for (FilterByTask e : FilterByTask.values()) {
				if (value.equals(e.name))
					return true;
			}
		}
		return false;
	}
}
