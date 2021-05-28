package it.unibo.paserver.service;

import it.unibo.paserver.domain.Account;
import it.unibo.paserver.domain.AuthenticationException;

import java.util.List;

import org.springframework.data.domain.PageRequest;

public interface AccountService {

	Account save(Account account);

	Account login(String username, String password) throws AuthenticationException;

	boolean deleteAccount(long id, long parentId);

	Account getAccount(String username);

	List<Account> getAccounts();

	/**
	 * Gets the total number of accounts.
	 * 
	 * @return Total accounts count.
	 */
	Long getAccountsCount();

	List<Object[]> search(boolean isResearcher, long parentId, String name, String username, String email, String phone,
			String last, PageRequest pageable);

	long searchTotal(boolean isResearcher, long parentId, String name, String username, String email, String phone,
			String last);

	Account saveOrUpdate(Account u);

	Account findById(long id);

	Account findByIdAndParentId(long id, long parentId);
}
