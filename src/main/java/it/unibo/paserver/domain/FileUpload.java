package it.unibo.paserver.domain;
import java.io.File;
import java.io.Serializable;

/**
 * Arquivos Carregados
 */
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FileUpload implements Serializable{
	private static final long serialVersionUID = 5635869262180548880L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;    
	@Column(name = "parentId", nullable = true)
	private Long parentId = null;	
	@NotEmpty
    private String filename;
	@Column(name = "fileSource", length=255,nullable = true)
    private String fileSource = "N/A";
    @Lob
    private byte[] file;
    private String mimeType;
	@Column(name = "isAdmin", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isAdmin = false;    
	@Column(name = "isQueued", nullable = false)
	@Type(type = "org.hibernate.type.ByteType")
	private byte isQueued = 0;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "creationDate", updatable = false, nullable = false)
	private DateTime creationDate;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "updateDate", updatable = true, nullable = false)
	private DateTime updateDate;
	@Column(name = "removed", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean removed = false;
	@Transient
	private File uploadedFile;
	/**
	 * Construtor
	 */
	public FileUpload(long id, Long parentId, String filename, byte[] file, String mimeType, byte isQueued,
			DateTime creationDate, DateTime updateDate, boolean removed) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.filename = filename;
		this.file = file;
		this.mimeType = mimeType;
		this.isQueued = isQueued;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
		this.removed = removed;
	}	
	
	public FileUpload(){};
	/**
	 * Getter/Setters
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
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public byte getIsQueued() {
		return isQueued;
	}
	public void setIsQueued(byte isQueued) {
		this.isQueued = isQueued;
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

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}
	
	public String getFileSource(){
		return this.fileSource;
	}

	/**
	 * @return the uploadedFile
	 */
	public File getUploadedFile() {
		return uploadedFile;
	}

	/**
	 * @param uploadedFile the uploadedFile to set
	 */
	public void setUploadedFile(File uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
}