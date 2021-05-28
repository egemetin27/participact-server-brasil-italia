package it.unibo.paserver.repository;

import java.util.List;

import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.domain.UserListPush;

public interface UserListPushRepository {

	public UserListPush saveOrUpdate(UserListPush i);

	public List<UserListPush> findAll();

	public UserListPush find(Long id);

	public UserListPush findUserListPush(Long pushId);

	public PushNotifications findUserPush(Long pushId);
}