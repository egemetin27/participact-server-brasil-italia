package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import it.unibo.paserver.domain.SystemPage;
import it.unibo.paserver.domain.SystemPageType;

public interface SystemPageService {
	// Busca pelo tipo
	SystemPage findByType(SystemPageType t);

	// Retorna todos os items
	List<SystemPage> findAll();

	// Salva ou atualiza
	SystemPage saveOrUpdate(SystemPage p);

	SystemPage findById(long id);
	
	boolean delete(long id);

	List<Object[]> search(PageRequest pagerequest);

	Long searchTotal();

	List<Object[]> fetchAllActivePages();
}