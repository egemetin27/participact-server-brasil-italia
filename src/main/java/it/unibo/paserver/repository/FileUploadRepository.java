package it.unibo.paserver.repository;

import it.unibo.paserver.domain.FileUpload;

public interface FileUploadRepository {

	FileUpload findById(long id);

	FileUpload findByFilename(String filename);

	FileUpload saveOrUpdate(FileUpload f);
}
