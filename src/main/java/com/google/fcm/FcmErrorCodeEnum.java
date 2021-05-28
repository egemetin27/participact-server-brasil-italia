package com.google.fcm;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FcmErrorCodeEnum {
    @JsonProperty("MissingRegistration")
    MissingRegistration,

    @JsonProperty("InvalidRegistration")
    InvalidRegistration,

    @JsonProperty("NotRegistered")
    NotRegistered,

    @JsonProperty("InvalidPackageName")
    InvalidPackageName,

    @JsonProperty("MismatchSenderId")
    MismatchSenderId,

    @JsonProperty("InvalidParameters")
    InvalidParameters,

    @JsonProperty("MessageTooBig")
    MessageTooBig,

    @JsonProperty("InvalidDataKey")
    InvalidDataKey,

    @JsonProperty("InvalidTtl")
    InvalidTtl,

    @JsonProperty("Unavailable")
    Unavailable,

    @JsonProperty("InternalServerError")
    InternalServerError,

    @JsonProperty("DeviceMessageRateExceeded")
    DeviceMessageRateExceeded,

    @JsonProperty("TopicsMessageRateExceeded")
    TopicsMessageRateExceeded,

    @JsonProperty("InvalidApnsCredential")
    InvalidApnsCredential
}
