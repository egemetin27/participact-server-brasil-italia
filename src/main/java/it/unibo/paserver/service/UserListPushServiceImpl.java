package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.domain.UserListPush;
import it.unibo.paserver.repository.UserListPushRepository;

@Service
@Transactional(readOnly = true)
public class UserListPushServiceImpl implements UserListPushService {
	@Autowired
	UserListPushRepository repos;

	@Override
	@Transactional(readOnly = false)
	public UserListPush saveOrUpdate(UserListPush i) {
		
		return repos.saveOrUpdate(i);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserListPush> findAll() {
		
		return repos.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public UserListPush find(Long id) {
		
		return repos.find(id);
	}

	@Override
	@Transactional(readOnly = true)
	public UserListPush findUserListPush(Long pushId) {
		
		return repos.findUserListPush(pushId);
	}

	@Override
	@Transactional(readOnly = true)
	public PushNotifications findUserPush(Long pushId) {
		
		return repos.findUserPush(pushId);
	}

	@Override
	@Transactional(readOnly = false)
	public UserListPush addUserListPush(UserListPush userListPush) {
		
		return repos.saveOrUpdate(userListPush);
	}
}