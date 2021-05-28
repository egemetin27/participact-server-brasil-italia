package it.unibo.paserver.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.User;

public interface UserRepository {

	User findByEmail(String email);

	User findById(long id);

	User save(User user);
	
	User created(User user);

	/**
	 * Gets the total number of accounts.
	 * 
	 * @return Total accounts count.
	 */
	Long getUserCount();

	List<User> getUsers();

	List<String> getActiveUsers();

	boolean deleteUser(long id);

	BinaryDocument getIdScan(Long id);

	BinaryDocument getLastInvoiceScan(Long id);

	BinaryDocument getPrivacyScan(Long id);

	BinaryDocument getPresaConsegnaPhone(Long id);

	BinaryDocument getPresaConsegnaSIM(Long id);

	boolean deleteBinaryDocument(long id);
	
	public List<User> getWorkingUsers(int inputType, DateTime start);
	/**
	 * Remocao simbolica
	 * @param id
	 * @return
	 */
	boolean removed(long id);
	/**
	 * Desfaz a remocao simbolica
	 * @param id
	 * @return
	 */
	boolean unRemoved(long id);
	/**
	 * Retorna um usuario pelo email/username e senha
	 * @param email
	 * @param password
	 * @return
	 */
	User findByEmailAndPassword(String email, String password);
	/**
	 * Salva ou atualiza
	 * @param u
	 * @return
	 */
	User saveOrUpdate(User u);
	/**
	 * Busca o total de uma consulta dinamica
	 * @param params
	 * @return
	 */
	Long searchTotal(ListMultimap<String, Object> params);
	/**
	 * Consulta Dinamica
	 * @param params
	 * @param pagerequest
	 * @return
	 */
	List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);
	/**
	 * Consulta pelo id com retorno parcial dos dados
	 * @param id
	 * @return
	 */
	User findParticipantById(long id);
	/**
	 * Consulta customizada
	 * @param conditions
	 * @param pageable
	 * @return
	 */
	List<User> filter(ListMultimap<String, Object> conditions, PageRequest pageable);
}
