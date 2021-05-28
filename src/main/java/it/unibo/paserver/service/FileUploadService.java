package it.unibo.paserver.service;

import it.unibo.paserver.domain.FileUpload;

public interface FileUploadService {
	FileUpload findById(long id);

	FileUpload findByFilename(String filename);

	FileUpload saveOrUpdate(FileUpload f);
}
