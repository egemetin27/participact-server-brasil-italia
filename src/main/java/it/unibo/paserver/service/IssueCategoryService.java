package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.IssueCategory;

public interface IssueCategoryService {
	// Sava ou atualiza
	IssueCategory saveOrUpdate(IssueCategory ic);

	// Busca pelo id
	IssueCategory findById(long id);

	// Todos os itens
	List<IssueCategory> findAll();

	// Total de registros
	Long getCount();

	// Removed um item
	boolean removed(long id);

	List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);

	List<IssueCategory> filter(ListMultimap<String, Object> params, PageRequest pagerequest);

	Long searchTotal(ListMultimap<String, Object> params);

	boolean removeAll();

    boolean reorder(long id, int index);

	List<Object[]> getTotalByCategory();

	Long getTotalByCategoryDatePart(Long id, int year, int monthOfYear);
}
