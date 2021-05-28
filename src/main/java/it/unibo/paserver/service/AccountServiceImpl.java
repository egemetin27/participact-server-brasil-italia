package it.unibo.paserver.service;

import it.unibo.paserver.domain.Account;
import it.unibo.paserver.domain.AuthenticationException;
import it.unibo.paserver.repository.AccountRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Override
	@Transactional(readOnly = true)
	public Account login(String username, String password) throws AuthenticationException {
		Account account = this.accountRepository.findByUsername(username);
		if (account == null) {
			throw new AuthenticationException("Wrong username/password combination.");
		}

		return account;
	}

	@Override
	@Transactional(readOnly = true)
	public Account getAccount(String username) {
		return accountRepository.findByUsername(username);
	}

	@Override
	@Transactional(readOnly = false)
	public Account save(Account account) {
		return accountRepository.save(account);
	}

	@Override
	@Transactional(readOnly = true)
	public Long getAccountsCount() {
		return accountRepository.getAccountsCount();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Account> getAccounts() {
		return accountRepository.getAccounts();
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteAccount(long id, long parentId) {
		return accountRepository.deleteAccount(id, parentId);
	}

	@Override
	public List<Object[]> search(boolean isResearcher, long parentId, String name, String username, String email,
			String phone, String last, PageRequest pageable) {
		
		return accountRepository.search(isResearcher, parentId, name, username, email, phone, last, pageable);
	}

	@Override
	public long searchTotal(boolean isResearcher, long parentId, String name, String username, String email,
			String phone, String last) {
		
		return accountRepository.searchTotal(isResearcher, parentId, name, username, email, phone, last);
	}

	@Override
	@Transactional(readOnly = false)
	public Account saveOrUpdate(Account u) {
		
		return accountRepository.saveOrUpdate(u);
	}

	@Override
	public Account findById(long id) {
		
		return accountRepository.findById(id);
	}

	@Override
	public Account findByIdAndParentId(long id, long parentId) {
		
		return accountRepository.findByIdAndParentId(id, parentId);
	}
}
