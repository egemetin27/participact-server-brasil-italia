package com.google.fcm;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FcmMessageResultItem {

	private final String messageId;
	private final String canonicalRegistrationId;
	private final FcmErrorCodeEnum errorCode;

	@JsonCreator
	public FcmMessageResultItem(@JsonProperty("message_id") String messageId,
			@JsonProperty("registration_id") String canonicalRegistrationId,
			@JsonProperty("error") FcmErrorCodeEnum errorCode) {
		this.messageId = messageId;
		this.canonicalRegistrationId = canonicalRegistrationId;
		this.errorCode = errorCode;
	}

	public String getMessageId() {
		return messageId;
	}

	public String getCanonicalRegistrationId() {
		return canonicalRegistrationId;
	}

	public FcmErrorCodeEnum getErrorCode() {
		return errorCode;
	}

	@Override
	public String toString() {
		return "FcmMessageResultItem{" + "messageId='" + messageId + '\'' + ", canonicalRegistrationId='"
				+ canonicalRegistrationId + '\'' + ", errorCode=" + errorCode + '}';
	}
}
