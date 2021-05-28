package it.unibo.paserver.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

@Entity
@Table(name = "feedback_report")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FeedbackReport implements Serializable {

	private static final long serialVersionUID = -3394708381794603916L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String comment;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id")
	private FeedbackType type;

	@OneToMany(fetch = FetchType.LAZY, targetEntity = StorageFile.class, cascade = CascadeType.ALL)
	@JoinTable(name = "feedback_storage_file", joinColumns = { @JoinColumn(name = "feedback_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "storage_file_id", referencedColumnName = "id") })
	@JsonIgnore
	private List<StorageFile> files;

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
	@Column(name = "editDate", updatable = true, nullable = false)
	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	@JsonSerialize(using = JsonDateTimeSerializer.class)		
	private DateTime editDate;

	@Column(name = "removed", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean removed = false;

	// Getters
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the type
	 */
	public FeedbackType getType() {
		return type;
	}

	/**
	 * @return the files
	 */
	public List<StorageFile> getFiles() {
		return files;
	}

	/**
	 * @return the creationDate
	 */
	public DateTime getCreationDate() {
		return creationDate;
	}

	/**
	 * @return the updateDate
	 */
	public DateTime getUpdateDate() {
		return updateDate;
	}

	/**
	 * @return the editDate
	 */
	public DateTime getEditDate() {
		return editDate;
	}

	// Setter
	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the removed
	 */
	public boolean isRemoved() {
		return removed;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(FeedbackType type) {
		this.type = type;
	}

	/**
	 * @param files
	 *            the files to set
	 */
	public void setFiles(List<StorageFile> files) {
		this.files = files;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @param editDate
	 *            the editDate to set
	 */
	public void setEditDate(DateTime editDate) {
		this.editDate = editDate;
	}

	/**
	 * @param removed
	 *            the removed to set
	 */
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

}
