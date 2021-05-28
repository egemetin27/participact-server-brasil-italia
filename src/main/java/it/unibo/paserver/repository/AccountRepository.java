package it.unibo.paserver.repository;

import it.unibo.paserver.domain.Account;

import java.util.List;

import org.springframework.data.domain.PageRequest;

public interface AccountRepository {

	Account findByUsername(String username);

	Account findById(long id);

	Account save(Account account);

	/**
	 * Gets the total number of accounts.
	 * 
	 * @return Total accounts count.
	 */
	Long getAccountsCount();

	List<Account> getAccounts();

	boolean deleteAccount(long id, long parentId);
	/**
	 * Consulta customizada por paginacao
	 * @param parentId 
	 * @param isResearcher 
	 * @param pageRequest
	 * @return
	 */
	List<Object[]> search(boolean isResearcher, long parentId, String name, String username, String email, String phone, String last, PageRequest pageable);

	long searchTotal(boolean isResearcher, long parentId, String name, String username, String email, String phone, String last);

	Account saveOrUpdate(Account u);

	Account findByIdAndParentId(long id, long parentId);
}
