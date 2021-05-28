package it.unibo.paserver.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.bergmannsoft.utils.Validator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.unibo.paserver.domain.support.JsonDateTimeDeserializer;
import it.unibo.paserver.domain.support.JsonDateTimeSerializer;

@Entity
@Table(name = "issue_category")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IssueCategory implements Serializable {
	private static final long serialVersionUID = 6034789585312138034L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty
	private String name;
	
	private String color;

	private int sequence;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(columnDefinition = "TEXT")
	private String urlAsset;

	@Column(columnDefinition = "TEXT", name = "urlasset_light", nullable = true)
	private String urlAssetLight;

	@Column(columnDefinition = "TEXT")
	private String urlIcon;	

	@OneToMany(mappedBy = "category", targetEntity = IssueSubCategory.class, fetch = FetchType.LAZY)
	@OrderBy("sequence ASC")
	@JsonIgnore
	private List<IssueSubCategory> subcategories;
	
	@Transient
	private List<HashMap<String, Object>> map;

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

	@Column(name = "enabled", nullable = false)
	private boolean enabled = false;

	// Setter/Getters

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sequence
	 */
	public int getSequence() {
		return sequence;
	}

	/**
	 * @param sequence
	 *            the sequence to set
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the urlAsset
	 */
	public String getUrlAsset() {
		return urlAsset;
	}

	/**
	 * @param urlAsset
	 *            the urlAsset to set
	 */
	public void setUrlAsset(String urlAsset) {
		this.urlAsset = urlAsset;
	}

	/**
	 * @return the creationDate
	 */
	public DateTime getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
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
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the editDate
	 */
	public DateTime getEditDate() {
		return editDate;
	}

	/**
	 * @param editDate
	 *            the editDate to set
	 */
	public void setEditDate(DateTime editDate) {
		this.editDate = editDate;
	}

	/**
	 * @return the removed
	 */
	public boolean isRemoved() {
		return removed;
	}

	/**
	 * @param removed
	 *            the removed to set
	 */
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	/**
	 * @return the subcategories
	 */
	public List<IssueSubCategory> getSubcategories() {
		return subcategories;
	}

	/**
	 * @param subcategories
	 *            the subcategories to set
	 */
	public void setSubcategories(List<IssueSubCategory> subcategories) {
		this.subcategories = subcategories;
	}

	/**
	 * @return the map
	 */
	public List<HashMap<String, Object>> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(List<HashMap<String, Object>> map) {
		this.map = map;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the urlIcon
	 */
	public String getUrlIcon() {
		return urlIcon;
	}

	/**
	 * @param urlIcon the urlIcon to set
	 */
	public void setUrlIcon(String urlIcon) {
		this.urlIcon = urlIcon;
	}

	public String getUrlAssetDark() {
		return urlAsset;
	}

	public String getUrlAssetLight() {
		return urlAssetLight;
	}

	public void setUrlAssetLight(String urlAssetLight) {
		this.urlAssetLight = urlAssetLight;
	}


	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}