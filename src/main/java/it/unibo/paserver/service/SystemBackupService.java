package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import it.unibo.paserver.domain.SystemBackup;

public interface SystemBackupService {
	// Retorna todos os items
	List<SystemBackup> findAll();

	// Salva ou atualiza
	SystemBackup saveOrUpdate(SystemBackup b);

	SystemBackup findById(long id);

	boolean delete(long id);

	List<Object[]> search(PageRequest pagerequest);

	Long searchTotal();
}