package it.unibo.paserver.service;

import java.util.List;

import it.unibo.paserver.domain.UserList;

public interface UserListService {
	UserList saveOrUpdate(UserList l);

	List<UserList> findAll();

	UserList find(Long id);

	boolean removed(Long userListId);	
}
