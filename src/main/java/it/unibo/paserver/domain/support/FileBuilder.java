package it.unibo.paserver.domain.support;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.FileUpload;
import it.unibo.paserver.domain.Institutions;;

@Component
public class FileBuilder extends EntityBuilder<FileUpload> {

	@Override
	void initEntity() {
		
		entity = new FileUpload();
	}

	@Override
	FileUpload assembleEntity() {
		
		return entity;
	}

	public FileBuilder setAll(long id, long parentId, String filename, byte[] file, String mimeType, byte isQueued, String fileSource) {
		this.setId(id);
		this.setParentId(parentId);
		this.setFilename(filename);
		this.setFile(file);
		this.setMimeType(mimeType);
		this.setIsQueued(isQueued);
		this.setFileSource(fileSource);
		return this;
	}

	private FileBuilder setFileSource(String fileSource) {
		entity.setFileSource(fileSource);
		return this;
	}

	/**
	 * Setters/Getters
	 */
	public FileBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	public FileBuilder setParentId(Long parentId) {
		entity.setParentId(parentId);
		return this;
	}

	public FileBuilder setFilename(String filename) {
		entity.setFilename(filename);
		return this;
	}

	public FileBuilder setFile(byte[] file) {
		entity.setFile(file);
		return this;
	}

	public FileBuilder setMimeType(String mimeType) {
		entity.setMimeType(mimeType);
		return this;
	}

	public FileBuilder setIsQueued(byte isQueued) {
		entity.setIsQueued(isQueued);
		return this;
	}

	public FileBuilder setCreationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	public FileBuilder setUpdateDate(DateTime updateDate) {
		entity.setUpdateDate(updateDate);
		return this;
	}

	public FileBuilder setRemoved(boolean removed) {
		entity.setRemoved(removed);
		return this;
	}
	/**
	 * Se adnmin
	 * @param isAdmin
	 * @return
	 */
	public FileBuilder setAdmin(boolean isAdmin) {
		entity.setAdmin(isAdmin);
		return this;
	}
}