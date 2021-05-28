package it.unibo.paserver.service;

import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.UserLogFile;
import it.unibo.paserver.repository.UserLogFileRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLogFileServiceImpl implements UserLogFileService {

	@Autowired
	UserLogFileRepository userLogFileRepository;

	@Override
	@Transactional(readOnly = true)
	public UserLogFile findById(long id) {
		return userLogFileRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public UserLogFile save(UserLogFile userLogFile) {
		return userLogFileRepository.save(userLogFile);
	}

	@Override
	@Transactional(readOnly = false)
	public UserLogFile merge(UserLogFile userLogFile) {
		return userLogFileRepository.merge(userLogFile);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserLogFile> getUserLogFiles() {
		return userLogFileRepository.getUserLogFiles();
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteUserLogFile(long id) {
		return userLogFileRepository.deleteUserLogFile(id);
	}

	@Override
	@Transactional(readOnly = true)
	public BinaryDocument getLogFile(long userLogFileId) {
		return userLogFileRepository.getLogFile(userLogFileId);
	}

}
