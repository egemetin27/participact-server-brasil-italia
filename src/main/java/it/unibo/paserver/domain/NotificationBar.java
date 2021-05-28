package it.unibo.paserver.domain;

/**
 * Notificacoes para o usuario
 * @author Claudio
 */

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NotificationBar implements Serializable {
	private static final long serialVersionUID = -4127091961205397890L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "parentId", nullable = true)
	private Long parentId = null;	
	@Column(columnDefinition = "TEXT")
	private String message;
	private String alink = "javascript:;";
	@NotNull
	@Enumerated(EnumType.STRING)
	private ResultType resultType = ResultType.TYPE_NONE;
	@Column(name = "isRead", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isRead = false;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "creationDate", updatable = false, nullable = false)
	private DateTime creationDate;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "updateDate", updatable = true, nullable = false)
	private DateTime updateDate;
	@Column(name = "removed", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean removed = false;	
	/**
	 * Setter/Getters
	 */
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public boolean isRemoved() {
		return removed;
	}
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
	public String getAlink() {
		return alink;
	}
	public void setAlink(String alink) {
		this.alink = alink;
	}
	public ResultType getResultType() {
		return resultType;
	}
	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}
}
