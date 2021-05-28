package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import it.unibo.paserver.domain.SystemEmail;

public interface SystemEmailService {
	// Retorna todos os items
	List<SystemEmail> findAll();

	// Salva ou atualiza
	SystemEmail saveOrUpdate(SystemEmail p);

	SystemEmail findById(long id);

	boolean delete(long id);

	List<Object[]> search(PageRequest pagerequest);

	Long searchTotal();

	List<SystemEmail> findAllNotProcessing();

	boolean cleanProcessed();
}