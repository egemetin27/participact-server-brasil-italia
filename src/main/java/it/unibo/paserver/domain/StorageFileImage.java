package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="storage_file_image")
public class StorageFileImage extends StorageFile {
	private static final long serialVersionUID = -1010026629278342440L;	
	@Transient
	private char discriminatorType = 'I';
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