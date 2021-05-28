package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.UserList;
import it.unibo.paserver.domain.UserListItem;
import it.unibo.paserver.domain.UserListPush;
import it.unibo.paserver.domain.UserListTask;
import it.unibo.paserver.domain.support.UserListItemBuilder;

@Service
@Transactional(readOnly = true)
public class ParticipantListServiceImpl implements ParticipantListService {
	@Autowired
	private UserListService userListService;
	@Autowired
	private UserListItemService userListItemService;
	@Autowired
	private UserListPushService userListPushService;
	@Autowired
	private UserListTaskService userListTaskService;

	// Lists
	@Override
	@Transactional(readOnly = false)
	public UserList addUserList(UserList l) {
		
		return userListService.saveOrUpdate(l);
	}

	@Override
	@Transactional(readOnly = true)
	public UserList findUserList(Long userListId) {
		
		return userListService.find(userListId);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removeUserList(Long userListId) {
		
		return userListService.removed(userListId);
	}

	// Items
	@Override
	@Transactional(readOnly = false)
	public UserListItem addUserListItem(Long userListId, Long userId) {
		UserListItem i = userListItemService.findByListId(userListId, userId);
		if (i == null) {
			UserListItemBuilder ib = new UserListItemBuilder();
			i = addUserListItem(ib.setAll(0L, userId, userListId, false).build(true));
		} else {
			i.setRemoved(false);
			addUserListItem(i);
		}
		// Return
		return i;
	}

	@Override
	@Transactional(readOnly = false)
	public UserListItem addUserListItem(UserListItem i) {
		
		return userListItemService.saveOrUpdate(i);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removeUserListItem(Long userListId, Long userId) {
		
		return userListItemService.removed(userListId, userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> searchUserListItem(ListMultimap<String, Object> params, PageRequest pagerequest) {
		
		return userListItemService.searchUserListItem(params, pagerequest);
	}

	@Override
	@Transactional(readOnly = true)
	public Long searchTotalUserListItem(ListMultimap<String, Object> params) {
		
		return userListItemService.searchTotalUserListItem(params);
	}

	// Push
	@Override
	@Transactional(readOnly = true)
	public UserListPush findUserListPush(Long pushId) {
		
		return userListPushService.findUserListPush(pushId);
	}

	@Override
	@Transactional(readOnly = true)
	public PushNotifications findUserPush(Long pushId) {
		
		return userListPushService.findUserPush(pushId);
	}

	@Override
	@Transactional(readOnly = false)
	public UserListPush addUserListPush(UserListPush userListPush) {
		
		return userListPushService.addUserListPush(userListPush);
	}

	// Task
	@Override
	@Transactional(readOnly = true)
	public UserListTask findUserListTask(Long taskId) {
		
		return userListTaskService.findUserListTask(taskId);
	}

	@Override
	@Transactional(readOnly = true)
	public Task findUserTask(Long TaskId) {
		
		return userListTaskService.findUserTask(TaskId);
	}

	@Override
	@Transactional(readOnly = false)
	public UserListTask addUserListTask(UserListTask userListTask) {
		
		return userListTaskService.addUserListTask(userListTask);
	}
}
