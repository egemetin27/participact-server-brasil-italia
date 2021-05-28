package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.StorageFile;

public interface StorageFileService {
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

	List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);

	List<StorageFile> filter(ListMultimap<String, Object> params, PageRequest pagerequest);

	Long searchTotal(ListMultimap<String, Object> params);

	List<Object[]> searchAllFeedback(ListMultimap<String, Object> params, PageRequest pagerequest);
}
