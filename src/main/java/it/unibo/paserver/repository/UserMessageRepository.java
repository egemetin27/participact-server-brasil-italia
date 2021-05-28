package it.unibo.paserver.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.UserMessage;

public interface UserMessageRepository {
	// Sava ou atualiza
	UserMessage saveOrUpdate(UserMessage m);
	// Busca pelo id
	UserMessage findById(long id);
	// Seta todas mensagens como lidas
	boolean readByUserId(long userId);
	// Removed um item
	boolean removed(long id);
	// Busca customizada
	List<Object[]> search(ListMultimap<String, Object> params, PageRequest pageable);
	// Total da busca customizada
	Long searchTotal(ListMultimap<String, Object> params);
	//Retorna todas nao lidas
	List<Object[]> fetchAllUnread(Long userId);	
	List<UserMessage> fetchAllUnread(Long userId, boolean isRead) ;
}
	