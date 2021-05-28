package it.unibo.paserver.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import it.unibo.participact.domain.PANotification;
import it.unibo.paserver.domain.PushNotifications;

public interface PushNotificationsRepository {
	PushNotifications findByType(PANotification.Type t);

	List<PushNotifications> findAll();

	PushNotifications saveOrUpdate(PushNotifications p);

	PushNotifications findById(long id);

	boolean removed(long id);

	List<Object[]> search(PageRequest pagerequest, boolean isMail);

	Long searchTotal(boolean isMail);
}