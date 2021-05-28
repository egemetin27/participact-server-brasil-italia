package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import it.unibo.paserver.domain.NotificationBar;

public interface NotificationBarService {
	List<NotificationBar> findAll();

	NotificationBar saveOrUpdate(NotificationBar n);

	NotificationBar findById(long id);

	boolean unread(long id);
	
	boolean unreadAll(long parentId);

	List<Object[]> search(long parentId, PageRequest pagerequest);

	Long searchTotal(long parentId);
}