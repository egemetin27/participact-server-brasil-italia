package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.PushNotificationsLogs;

public interface PushNotificationsLogsService {
	// Retorna todos os items
	List<PushNotificationsLogs> findAll();

	// Salva ou atualiza
	PushNotificationsLogs saveOrUpdate(PushNotificationsLogs l);

	PushNotificationsLogs findById(long id);
	
	boolean removed(long id);

	List<Object[]> search(ListMultimap<String, Object> conditions,PageRequest pagerequest);

	Long searchTotal(ListMultimap<String, Object> conditions);

	List<Object[]> filter(ListMultimap<String, Object> params, PageRequest pagerequest);

	Long filterTotal(ListMultimap<String, Object> params);
}