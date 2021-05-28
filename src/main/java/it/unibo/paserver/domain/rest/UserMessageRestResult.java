package it.unibo.paserver.domain.rest;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.unibo.paserver.domain.support.JsonDateTimeDeserializer;
import it.unibo.paserver.domain.support.JsonDateTimeSerializer;


public class UserMessageRestResult implements Comparable<UserMessageRestResult> {

	private Long id;
	private String message;
	private boolean isRead = false;

	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	private DateTime creationDate;

	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	private DateTime updateDate;

	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	private DateTime readingDate;
	
	private boolean removed = false;

	
	public UserMessageRestResult() {
		// TODO Auto-generated constructor stub
	}
	
	
	public UserMessageRestResult(Long id, String message, boolean isRead, DateTime creationDate, DateTime updateDate, DateTime readingDate, boolean removed) {
		super();
		this.id = id;
		this.message = message;
		this.isRead = isRead;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
		this.readingDate = readingDate;
		this.removed = removed;
	}


	@Override
	public int compareTo(UserMessageRestResult arg0) {

		return 0;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}


	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * @return the isRead
	 */
	public boolean isRead() {
		return isRead;
	}


	/**
	 * @param isRead the isRead to set
	 */
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}


	/**
	 * @return the creationDate
	 */
	public DateTime getCreationDate() {
		return creationDate;
	}


	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}


	/**
	 * @return the updateDate
	 */
	public DateTime getUpdateDate() {
		return updateDate;
	}


	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}


	/**
	 * @return the readingDate
	 */
	public DateTime getReadingDate() {
		return readingDate;
	}


	/**
	 * @param readingDate the readingDate to set
	 */
	public void setReadingDate(DateTime readingDate) {
		this.readingDate = readingDate;
	}


	/**
	 * @return the removed
	 */
	public boolean isRemoved() {
		return removed;
	}


	/**
	 * @param removed the removed to set
	 */
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
}