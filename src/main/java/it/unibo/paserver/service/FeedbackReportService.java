package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.FeedbackReport;

public interface FeedbackReportService {
	// Sava ou atualiza
	FeedbackReport saveOrUpdate(FeedbackReport fr);

	// Busca pelo id
	FeedbackReport findById(long id);

	// Todos os itens
	List<FeedbackReport> findAll();

	// Total de registros
	Long getCount();

	// Removed um item
	boolean removed(long id);

	List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);

	List<FeedbackReport> filter(ListMultimap<String, Object> params, PageRequest pagerequest);

	Long searchTotal(ListMultimap<String, Object> params);
}
