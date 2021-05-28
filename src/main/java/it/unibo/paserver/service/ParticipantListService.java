package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.domain.UserList;
import it.unibo.paserver.domain.UserListItem;
import it.unibo.paserver.domain.UserListPush;
import it.unibo.paserver.domain.UserListTask;
import it.unibo.paserver.domain.Task;

public interface ParticipantListService {
	//UsetList
	UserList addUserList(UserList l);
	UserList findUserList(Long userListId);	
	boolean removeUserList(Long userListId);
	//UserListItem
	UserListItem addUserListItem(UserListItem i);
	UserListItem addUserListItem(Long userListId, Long userId);
	boolean removeUserListItem(Long userListId, Long userId);
	List<Object[]> searchUserListItem(ListMultimap<String, Object> params, PageRequest pagerequest);
	Long searchTotalUserListItem(ListMultimap<String, Object> params);
	//UserListPush
	UserListPush findUserListPush(Long pushId);
	PushNotifications findUserPush(Long pushId);
	UserListPush addUserListPush(UserListPush userListPush);
	//UserListTask
	UserListTask findUserListTask(Long id);
	Task findUserTask(Long id);
	UserListTask addUserListTask(UserListTask ult);
}