package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.UserListItem;
import it.unibo.paserver.repository.UserListItemRepository;

@Service
@Transactional(readOnly = true)
public class UserListItemServiceImpl implements UserListItemService {
	@Autowired
	UserListItemRepository repos;

	@Override
	@Transactional(readOnly = false)
	public UserListItem saveOrUpdate(UserListItem i) {
		
		return repos.saveOrUpdate(i);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserListItem> findAll() {
		
		return repos.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public UserListItem find(Long id) {
		
		return repos.find(id);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removed(Long userListId, Long userId) {
		
		return repos.removed(userListId, userId);
	}

	@Override
	@Transactional(readOnly = true)
	public UserListItem findByListId(Long userListId, Long userId) {
		
		return repos.findByListId(userListId, userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> searchUserListItem(ListMultimap<String, Object> params, PageRequest pagerequest) {
		
		return repos.searchUserListItem(params, pagerequest);
	}

	@Override
	@Transactional(readOnly = true)
	public Long searchTotalUserListItem(ListMultimap<String, Object> params) {
		
		return repos.searchTotalUserListItem(params);
	}
	
	
}