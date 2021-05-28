package it.unibo.paserver.repository;

import java.util.List;

import it.unibo.paserver.domain.UserList;

public interface UserListRepository {

	public UserList saveOrUpdate(UserList l);

	public List<UserList> findAll();

	public UserList find(Long id);

	public boolean removed(Long userListId);
}