package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(appliesTo = "DataActivityRecognitionCompare", indexes = { @Index(name = "dadc_user_sampletimestamp", columnNames = {
		"user_id", "sampletimestamp" }) })
public class DataActivityRecognitionCompare extends Data {

	private static final long serialVersionUID = -3910074051596517387L;

	@NotNull
	private String userActivity;
	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime googleArTimestamp;
	@NotNull
	private String googleArValue;
	@NotNull
	private Integer googleArConfidence;
	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime mostArTimestamp;
	@NotNull
	private String mostArValue;

	public String getUserActivity() {
		return userActivity;
	}

	public void setUserActivity(String userActivity) {
		this.userActivity = userActivity;
	}

	public DateTime getGoogleArTimestamp() {
		return googleArTimestamp;
	}

	public void setGoogleArTimestamp(DateTime googleArTimestamp) {
		this.googleArTimestamp = googleArTimestamp;
	}

	public String getGoogleArValue() {
		return googleArValue;
	}

	public void setGoogleArValue(String googleArValue) {
		this.googleArValue = googleArValue;
	}

	public Integer getGoogleArConfidence() {
		return googleArConfidence;
	}

	public void setGoogleArConfidence(Integer googleArConfidence) {
		this.googleArConfidence = googleArConfidence;
	}

	public DateTime getMostArTimestamp() {
		return mostArTimestamp;
	}

	public void setMostArTimestamp(DateTime mostArTimestamp) {
		this.mostArTimestamp = mostArTimestamp;
	}

	public String getMostArValue() {
		return mostArValue;
	}

	public void setMostArValue(String mostArValue) {
		this.mostArValue = mostArValue;
	}

}
