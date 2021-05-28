package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.FeedbackType;

public interface FeedbackTypeService {
	// Sava ou atualiza
	FeedbackType saveOrUpdate(FeedbackType f);

	// Busca pelo id
	FeedbackType findById(long id);

	// Todos os itens
	List<FeedbackType> findAll();

	// Total de registros
	Long getCount();

	// Removed um item
	boolean removed(long id);

	List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);
	List<FeedbackType> filter(ListMultimap<String, Object> params, PageRequest pagerequest);

	Long searchTotal(ListMultimap<String, Object> params);
}
