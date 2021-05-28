package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataPhoneCallEvent", indexes = {
		@Index(name = "phoneevt_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "phoneevt_ts", columnNames = { "sampletimestamp" }) })
public class DataPhoneCallEvent extends Data {

	private static final long serialVersionUID = -837842733892409270L;

	@NotNull
	private Boolean isStart;

	@NotNull
	private Boolean isIncomingCall;

	@NotNull
	private String phoneNumber;

	public Boolean getIsStart() {
		return isStart;
	}

	public void setIsStart(Boolean isStart) {
		this.isStart = isStart;
	}

	public Boolean getIsIncomingCall() {
		return isIncomingCall;
	}

	public void setIsIncomingCall(Boolean isIncomingCall) {
		this.isIncomingCall = isIncomingCall;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
