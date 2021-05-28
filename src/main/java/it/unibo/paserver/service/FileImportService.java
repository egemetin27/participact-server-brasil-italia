package it.unibo.paserver.service;

import it.unibo.paserver.domain.FileUpload;

public interface FileImportService {
	void importUsersByFile(FileUpload f);

	void importUsersByScript(FileUpload f);
}
