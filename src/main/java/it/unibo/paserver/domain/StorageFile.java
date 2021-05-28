package it.unibo.paserver.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.unibo.paserver.domain.support.JsonDateTimeDeserializer;
import it.unibo.paserver.domain.support.JsonDateTimeSerializer;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "storage_file")
public abstract class StorageFile implements Serializable {
	private static final long serialVersionUID = 8648574222727328441L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty
	private String fileName;
	
	private String originalFilename;

	@NotEmpty
	private String FileExtension;

	@NotEmpty
	@Column(columnDefinition = "TEXT")
	private String assetUrl;
	
	@Column(columnDefinition = "TEXT")
	private String assetUrlWeb;
	
	@Column(columnDefinition = "TEXT")
	private String assetUrlThird;
	
	@Column(columnDefinition = "TEXT")
	private String assetUrlFourth;

	@JsonIgnore
	@Column(name="altitude", nullable = true)
	private Double altitude = 0.0D;
	@JsonIgnore
	@Column(name="latitude", nullable = true)
	private Double latitude = 0.0D;
	@JsonIgnore
	@Column(name="longitude", nullable = true)
	private Double longitude = 0.0D;
	@JsonIgnore
	@Column(name="accuracy", nullable = true)
	private Double accuracy = 0.0D;
	@JsonIgnore
	@Column(name="provider", nullable = true)
	private String provider = "N/A";
	@JsonIgnore
	@Column(name="ipaddress", nullable = true)
	private String ipAddress = "::0";

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
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileExtension
	 */
	public String getFileExtension() {
		return FileExtension;
	}

	/**
	 * @param fileExtension
	 *            the fileExtension to set
	 */
	public void setFileExtension(String fileExtension) {
		FileExtension = fileExtension;
	}

	/**
	 * @return the assetUrl
	 */
	public String getAssetUrl() {
		return assetUrl;
	}

	/**
	 * @param assetUrl
	 *            the assetUrl to set
	 */
	public void setAssetUrl(String assetUrl) {
		this.assetUrl = assetUrl;
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
	 * @return the originalFilename
	 */
	public String getOriginalFilename() {
		return originalFilename;
	}

	/**
	 * @param originalFilename the originalFilename to set
	 */
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	/**
	 * @return the assetUrlWeb
	 */
	public String getAssetUrlWeb() {
		return assetUrlWeb;
	}

	/**
	 * @param assetUrlWeb the assetUrlWeb to set
	 */
	public void setAssetUrlWeb(String assetUrlWeb) {
		this.assetUrlWeb = assetUrlWeb;
	}

	/**
	 * @return the assetUrlThird
	 */
	public String getAssetUrlThird() {
		return assetUrlThird;
	}

	/**
	 * @param assetUrlThird the assetUrlThird to set
	 */
	public void setAssetUrlThird(String assetUrlThird) {
		this.assetUrlThird = assetUrlThird;
	}

	/**
	 * @return the assetUrlFourth
	 */
	public String getAssetUrlFourth() {
		return assetUrlFourth;
	}

	/**
	 * @param assetUrlFourth the assetUrlFourth to set
	 */
	public void setAssetUrlFourth(String assetUrlFourth) {
		this.assetUrlFourth = assetUrlFourth;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
