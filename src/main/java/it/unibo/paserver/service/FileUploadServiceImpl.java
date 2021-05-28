package it.unibo.paserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.FileUpload;
import it.unibo.paserver.repository.FileUploadRepository;

@Service
@Transactional(readOnly = true)
public class FileUploadServiceImpl implements FileUploadService {
	@Autowired
	FileUploadRepository repos;

	@Override
	@Transactional(readOnly = true)
	public FileUpload findById(long id) {
		
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public FileUpload findByFilename(String filename) {
		
		return repos.findByFilename(filename);
	}

	@Override
	@Transactional(readOnly = false)
	public FileUpload saveOrUpdate(FileUpload f) {
		
		return repos.saveOrUpdate(f);
	}
}