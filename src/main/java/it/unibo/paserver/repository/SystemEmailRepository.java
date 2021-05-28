package it.unibo.paserver.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import it.unibo.paserver.domain.SystemEmail;

public interface SystemEmailRepository {
	List<SystemEmail> findAll();

	SystemEmail saveOrUpdate(SystemEmail p);

	SystemEmail findById(long id);

	boolean delete(long id);

	List<Object[]> search(PageRequest pagerequest);

	Long searchTotal();

	List<SystemEmail> findAllNotProcessing();

	boolean cleanProcessed();
}