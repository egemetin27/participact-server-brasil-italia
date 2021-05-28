package it.unibo.paserver.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.IssueCategory;

public interface IssueCategoryRepository {
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

	Long searchTotal(ListMultimap<String, Object> params);

	List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);

	List<IssueCategory> filter(ListMultimap<String, Object> params, PageRequest pagerequest);

	boolean removeAll();

    boolean reorder(long id, int index);

    List<Object[]> getTotalByCategory();

    Long getTotalByCategoryDatePart(Long id, int year, int monthOfYear);
}
