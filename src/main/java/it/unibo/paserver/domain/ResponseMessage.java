package it.unibo.paserver.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class ResponseMessage implements Serializable {

	private static final long serialVersionUID = -4920503490628523872L;

	public static final int RESULT_OK = 200;
	public static final int DATA_ALREADY_ON_SERVER = 555;
	public static final int DATA_NOT_REQUIRED = 556;

	private static final String KEY = "key";
	private static final String RESULT_CODE = "resultCode";
	private static final String REST_STATUS = "status";
	private static final String REST_MESSAGE = "message";
	private static final String REST_CODE = "code";
	private static final String REST_DATA = "data";

	Properties map;

	public ResponseMessage() {
		map = new Properties();
		map.setProperty(KEY, "");
		map.setProperty(RESULT_CODE, -1 + "");

		map.setProperty(REST_STATUS, "false");
		map.setProperty(REST_MESSAGE, "");
		map.setProperty(REST_CODE, 0 + "");
		map.setProperty(REST_DATA, "");
	}

	public String getStatus() {
		return map.getProperty(REST_STATUS);
	}

	public String getMessage() {
		return map.getProperty(REST_MESSAGE);
	}

	public String getCode() {
		return map.getProperty(REST_CODE);
	}

	public String getData() {
		return map.getProperty(REST_DATA);
	}

	public String getProperty(String key) {
		return map.getProperty(key);
	}

	public void setProperty(String key, String value) {
		map.put(key, value);
	}

	public String getKey() {
		return map.getProperty(KEY);
	}

	public void setKey(String key) {
		map.put(KEY, key);
	}

	public int getResultCode() {
		return Integer.parseInt(map.getProperty(RESULT_CODE));
	}

	public void setResultCode(int resultCode) {
		map.setProperty(RESULT_CODE, resultCode + "");
	}

	@Override
	public String toString() {
		List<String> result = new ArrayList<String>();
		for (Object key : map.keySet()) {
			result.add(String.format("%s: %s", key, map.get(key)));
		}
		Collections.sort(result);
		return String.format("ResponseMessage {%s}", StringUtils.join(result, ", "));
	}
}