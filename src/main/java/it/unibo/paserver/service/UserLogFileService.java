package it.unibo.paserver.service;

import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.UserLogFile;

import java.util.List;

public interface UserLogFileService {

	UserLogFile findById(long id);

	UserLogFile save(UserLogFile userLogFile);

	UserLogFile merge(UserLogFile userLogFile);

	List<UserLogFile> getUserLogFiles();

	boolean deleteUserLogFile(long id);

	BinaryDocument getLogFile(long userLogFileId);

}
