package it.unibo.paserver.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "storage_file_audio")
public class StorageFileAudio extends StorageFile {
	private static final long serialVersionUID = -127914056851728182L;

	private String author;
	private String title;
	@Column(columnDefinition = "TEXT")
	private String copyright;
	@Column(columnDefinition = "TEXT")
	private String comment;

	@Column(columnDefinition = "TEXT")
	private String metadata;
	@Column(columnDefinition = "TEXT")
	private String encodingType;
	private long duration = 0L;
	
	private long byteLength  = 0L;
	private String format;
	private long frameLength  = 0L;
	@Transient
	private char discriminatorType = 'A';
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the copyright
	 */
	public String getCopyright() {
		return copyright;
	}
	/**
	 * @param copyright the copyright to set
	 */
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the metadata
	 */
	public String getMetadata() {
		return metadata;
	}
	/**
	 * @param metadata the metadata to set
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
	 * @param encodingType the encodingType to set
	 */
	public void setEncodingType(String encodingType) {
		this.encodingType = encodingType;
	}
	/**
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}
	/**
	 * @return the byteLength
	 */
	public long getByteLength() {
		return byteLength;
	}
	/**
	 * @param byteLength the byteLength to set
	 */
	public void setByteLength(long byteLength) {
		this.byteLength = byteLength;
	}
	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	/**
	 * @return the frameLength
	 */
	public long getFrameLength() {
		return frameLength;
	}
	/**
	 * @param frameLength the frameLength to set
	 */
	public void setFrameLength(long frameLength) {
		this.frameLength = frameLength;
	}
	/**
	 * @return the discriminatorType
	 */
	public char getDiscriminatorType() {
		return discriminatorType;
	}
	/**
	 * @param discriminatorType the discriminatorType to set
	 */
	public void setDiscriminatorType(char discriminatorType) {
		this.discriminatorType = discriminatorType;
	}
}