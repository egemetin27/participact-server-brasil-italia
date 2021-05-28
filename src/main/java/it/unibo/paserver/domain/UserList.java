package it.unibo.paserver.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "user_list")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserList implements Serializable {
	private static final long serialVersionUID = -5201234085234274481L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "parentId")
	@Index(name = "idx_user_list_parent_id")
	private Long parentId;
	
	@Enumerated(EnumType.STRING)
	private AudienceSelector audienceSelector = AudienceSelector.SELECTOR_NONE;	
	
	@Column(columnDefinition = "TEXT")
	private String hashmap;
	
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
	 * Constructor's
	 */
	public UserList(Long id, Long parentId, boolean removed) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.removed = removed;
		this.updateDate = new DateTime();
		if (id == null) {
			this.creationDate = this.updateDate;
		}
	}

	public UserList() {
	}

	/**
	 * Setters/Getters
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public AudienceSelector getAudienceSelector() {
		return audienceSelector;
	}

	public void setAudienceSelector(AudienceSelector audienceSelector) {
		this.audienceSelector = audienceSelector;
	}

	public String getHashmap() {
		return hashmap;
	}

	public void setHashmap(String hashmap) {
		this.hashmap = hashmap;
	}
}