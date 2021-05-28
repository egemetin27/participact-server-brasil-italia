package it.unibo.paserver.service;

import it.unibo.paserver.domain.AuthenticationException;
import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.User;

import java.util.List;

import org.joda.time.DateTime;

public interface UserService {

	User save(User user);
	
	User created(User user);
	/**
	 * @deprecated User a funcao findByEmailAndPassword, essa so verifica o email
	 * @param email
	 * @param password
	 * @return
	 * @throws AuthenticationException
	 */
	User login(String email, String password) throws AuthenticationException;

	boolean deleteUser(long id);

	User getUser(String email);

	User getUser(Long id);

	User getUser(Long id, boolean fullFetch);

	List<User> getUsers();

	List<String> getActiveUsers();
	
	public List<User> getWorkingUsers(int inputType, DateTime start);
	
	/**
	 * Gets the total number of user accounts.
	 * 
	 * @return Total accounts count.
	 */
	Long getUserCount();

	BinaryDocument getIdScan(Long id);

	BinaryDocument getLastInvoiceScan(Long id);

	BinaryDocument getPrivacyScan(Long id);

	BinaryDocument getPresaConsegnaPhone(Long id);

	BinaryDocument getPresaConsegnaSIM(Long id);

	boolean deleteBinaryDocument(long id);

	User findByEmailAndPassword(String email, String password);

	boolean unRemoved(Long id);
}
