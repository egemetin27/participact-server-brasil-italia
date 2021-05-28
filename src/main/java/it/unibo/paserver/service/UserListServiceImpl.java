package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.UserList;
import it.unibo.paserver.repository.UserListRepository;

@Service
@Transactional(readOnly = true)
public class UserListServiceImpl implements UserListService {
	@Autowired
	UserListRepository repos;

	@Override
	@Transactional(readOnly = false)
	public UserList saveOrUpdate(UserList l) {
		
		return repos.saveOrUpdate(l);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserList> findAll() {
		
		return repos.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public UserList find(Long id) {
		
		return repos.find(id);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removed(Long userListId) {
		
		return repos.removed(userListId);
	}
}