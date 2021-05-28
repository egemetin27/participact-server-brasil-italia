package it.unibo.paserver.service;

import java.util.List;

import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.domain.UserListPush;

public interface UserListPushService {
	UserListPush saveOrUpdate(UserListPush p);

	List<UserListPush> findAll();

	UserListPush find(Long id);

	UserListPush findUserListPush(Long pushId);

	PushNotifications findUserPush(Long pushId);

	UserListPush addUserListPush(UserListPush userListPush);
}