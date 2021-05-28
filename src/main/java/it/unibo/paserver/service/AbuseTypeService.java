package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.IssueAbuseType;

public interface AbuseTypeService {
	// Sava ou atualiza
	IssueAbuseType saveOrUpdate(IssueAbuseType a);

	// Busca pelo id
	IssueAbuseType findById(long id);

	// Todos os itens
	List<IssueAbuseType> findAll();

	// Total de registros
	Long getCount();

	// Removed um item
	boolean removed(long id);

	List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);

	Long searchTotal(ListMultimap<String, Object> params);

	List<IssueAbuseType> filter(ListMultimap<String, Object> params, PageRequest pagerequest);
}
