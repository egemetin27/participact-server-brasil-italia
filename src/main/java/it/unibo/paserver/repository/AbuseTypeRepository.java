package it.unibo.paserver.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.IssueAbuseType;

public interface AbuseTypeRepository {
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

	Long searchTotal(ListMultimap<String, Object> params);

	List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);

	List<IssueAbuseType> filter(ListMultimap<String, Object> params, PageRequest pagerequest);
}
