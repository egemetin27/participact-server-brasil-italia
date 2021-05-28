package it.unibo.paserver.service;

import java.util.List;

import it.unibo.paserver.domain.UserMessage;

public interface UserMessageService {
	// Sava ou atualiza
	UserMessage saveOrUpdate(UserMessage m);
	// Busca pelo id
	UserMessage findById(long id);
	// Seta todas mensagens como lidas
	boolean readByUserId(long userId);
	// Removed um item
	boolean removed(long id);
	//Retorna todas nao lidas
	List<Object[]> fetchAllUnread(Long userId);
	List<UserMessage> fetchAllUnread(long uId,  boolean isRead);
}
