package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.UserListItem;

public interface UserListItemService {
	UserListItem saveOrUpdate(UserListItem i);

	List<UserListItem> findAll();

	UserListItem find(Long id);

	boolean removed(Long userListId, Long userId);

	UserListItem findByListId(Long userListId, Long userId);

	List<Object[]> searchUserListItem(ListMultimap<String, Object> params, PageRequest pagerequest);

	Long searchTotalUserListItem(ListMultimap<String, Object> params);
}