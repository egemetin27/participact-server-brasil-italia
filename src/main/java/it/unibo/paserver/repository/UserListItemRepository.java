package it.unibo.paserver.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.UserListItem;

public interface UserListItemRepository {

	public UserListItem saveOrUpdate(UserListItem i);

	public List<UserListItem> findAll();

	public UserListItem find(Long id);

	public boolean removed(Long userListId, Long userId);

	public UserListItem findByListId(Long userListId, Long userId);

	public List<Object[]> searchUserListItem(ListMultimap<String, Object> params, PageRequest pagerequest);

	public Long searchTotalUserListItem(ListMultimap<String, Object> params);
}