package it.unibo.paserver.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import it.unibo.paserver.domain.SystemBackup;

public interface SystemBackupRepository {
	List<SystemBackup> findAll();

	SystemBackup saveOrUpdate(SystemBackup b);

	SystemBackup findById(long id);

	boolean delete(long id);

	List<Object[]> search(PageRequest pagerequest);

	Long searchTotal();
}