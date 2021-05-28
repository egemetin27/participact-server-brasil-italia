package it.unibo.paserver.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.FeedbackReport;

public interface FeedbackReportRepository {
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

	Long searchTotal(ListMultimap<String, Object> params);

	List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);

	List<FeedbackReport> filter(ListMultimap<String, Object> params, PageRequest pagerequest);
}
