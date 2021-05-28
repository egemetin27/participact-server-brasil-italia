package it.unibo.paserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "storage_file_video")
public class StorageFileVideo extends StorageFile {
	private static final long serialVersionUID = -6639651276432061885L;

	@Column(columnDefinition = "TEXT")
	private String thumbnailUrl;
	@Column(columnDefinition = "TEXT")
	private String metadata;
	@Column(columnDefinition = "TEXT")
	private String encodingType;

	private boolean isVideoClip = false;
	private long duration = 0L;
	@Transient
	private char discriminatorType = 'V';

	/**
	 * @return the thumbnailUrl
	 */
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	/**
	 * @param thumbnailUrl
	 *            the thumbnailUrl to set
	 */
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	/**
	 * @return the metadata
	 */
	public String getMetadata() {
		return metadata;
	}

	/**
	 * @param metadata
	 *            the metadata to set
	 */
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	/**
	 * @return the encodingType
	 */
	public String getEncodingType() {
		return encodingType;
	}

	/**
	 * @param encodingType
	 *            the encodingType to set
	 */
	public void setEncodingType(String encodingType) {
		this.encodingType = encodingType;
	}

	/**
	 * @return the isVideoClip
	 */
	public boolean isVideoClip() {
		return isVideoClip;
	}

	/**
	 * @param isVideoClip
	 *            the isVideoClip to set
	 */
	public void setVideoClip(boolean isVideoClip) {
		this.isVideoClip = isVideoClip;
	}

	/**
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * @return the discriminatorType
	 */
	public char getDiscriminatorType() {
		return discriminatorType;
	}

	/**
	 * @param discriminatorType
	 *            the discriminatorType to set
	 */
	public void setDiscriminatorType(char discriminatorType) {
		this.discriminatorType = discriminatorType;
	}
}