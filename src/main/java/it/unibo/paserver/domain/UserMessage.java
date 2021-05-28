package it.unibo.paserver.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.unibo.paserver.domain.support.JsonDateTimeDeserializer;
import it.unibo.paserver.domain.support.JsonDateTimeSerializer;

/**
 * Mensagens enviadas para um usuario/participante
 * 
 * @author Claudio
 *
 */
@Entity
@Table(name = "user_messages")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserMessage implements Serializable {
	private static final long serialVersionUID = 5217442963567558643L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User userId;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "messageId")
	private PushNotifications messageId;
	@Column(name = "isRead", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isRead = false;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "creationDate", updatable = false, nullable = false)
	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	@JsonSerialize(using = JsonDateTimeSerializer.class)			
	private DateTime creationDate;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "updateDate", updatable = true, nullable = false)
	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	@JsonSerialize(using = JsonDateTimeSerializer.class)			
	private DateTime updateDate;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "readingDate", updatable = true, nullable = true)
	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	@JsonSerialize(using = JsonDateTimeSerializer.class)	
	private DateTime readingDate;
	@Column(name = "removed", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean removed = false;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param userId
	 * @param messageId
	 * @param isRead
	 * @param readingDate
	 * @param removed
	 */
	public UserMessage(Long id, User userId, PushNotifications messageId, boolean isRead, DateTime readingDate,
			boolean removed) {
		super();
		this.id = id;
		this.userId = userId;
		this.messageId = messageId;
		this.isRead = isRead;
		this.creationDate = new DateTime();
		this.updateDate = new DateTime();
		this.readingDate = readingDate;
		this.removed = false;
	}

	/**
	 * Default Constructor
	 */
	public UserMessage() {

	}

	/**
	 * Setter/Getters
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public PushNotifications getMessageId() {
		return messageId;
	}

	public void setMessageId(PushNotifications messageId) {
		this.messageId = messageId;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}

	public DateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}

	public DateTime getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(DateTime readingDate) {
		this.readingDate = readingDate;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
}
