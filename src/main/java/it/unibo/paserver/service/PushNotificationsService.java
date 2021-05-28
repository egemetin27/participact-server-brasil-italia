package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import it.unibo.participact.domain.PANotification;
import it.unibo.paserver.domain.PushNotifications;

public interface PushNotificationsService {
	// Busca pelo tipo
	PushNotifications findByType(PANotification.Type t);

	// Retorna todos os items
	List<PushNotifications> findAll();

	// Salva ou atualiza
	PushNotifications saveOrUpdate(PushNotifications p);

	PushNotifications findById(long id);
	
	boolean removed(long id);

	List<Object[]> search(PageRequest pagerequest, boolean isMail);
	
	Long searchTotal(boolean isMail);
}