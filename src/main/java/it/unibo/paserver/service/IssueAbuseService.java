package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.IssueAbuse;

public interface IssueAbuseService {
	// Sava ou atualiza
	IssueAbuse saveOrUpdate(IssueAbuse ia);

	// Busca pelo id
	IssueAbuse findById(long id);

	// Todos os itens
	List<IssueAbuse> findAll();

	// Total de registros
	Long getCount();

	// Removed um item
	boolean removed(long id);

	List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);

	List<IssueAbuse> filter(ListMultimap<String, Object> params, PageRequest pagerequest);

	Long searchTotal(ListMultimap<String, Object> params);
}
