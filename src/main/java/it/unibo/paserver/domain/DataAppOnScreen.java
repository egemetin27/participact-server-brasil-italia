package it.unibo.paserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(appliesTo = "DataAppOnScreen", indexes = {
		@Index(name = "apponscr_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "apponscr_ts", columnNames = { "sampletimestamp" }) })
public class DataAppOnScreen extends Data {

	private static final long serialVersionUID = 2447327791041493962L;

	@NotNull
	@Column(name = "APP_NAME", columnDefinition = "TEXT")
	private String appName;

	@NotNull
	@Column(name = "START_TIME")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime startTime;

	@NotNull
	@Column(name = "END_TIME")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime endTime;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}

}
