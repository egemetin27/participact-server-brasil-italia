package it.unibo.paserver.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.StorageFile;

public interface StorageFileRepository {
	// Sava ou atualiza
	StorageFile saveOrUpdate(StorageFile s);

	// Busca pelo id
	StorageFile findById(long id);

	// Todos os itens
	List<StorageFile> findAll();

	// Total de registros
	Long getCount();

	// Removed um item
	boolean removed(long id);

	Long searchTotal(ListMultimap<String, Object> params);

	List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);

	List<StorageFile> filter(ListMultimap<String, Object> params, PageRequest pagerequest);

	List<Object[]> searchAllFeedback(ListMultimap<String, Object> conditions, PageRequest pageable);
}
