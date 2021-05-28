package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.IssueSubCategory;

public interface IssueSubCategoryService {
	// Sava ou atualiza
	IssueSubCategory saveOrUpdate(IssueSubCategory is);

	// Busca pelo id
	IssueSubCategory findById(long id);

	// Todos os itens
	List<IssueSubCategory> findAll();

	// Total de registros
	Long getCount();

	// Removed um item
	boolean removed(long id);

	List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);

	List<IssueSubCategory> filter(ListMultimap<String, Object> params, PageRequest pagerequest);

	Long searchTotal(ListMultimap<String, Object> params);

    boolean removeAll(Long categoryId);

    boolean reorder(long id, int index);

    List<Object[]> getTotalBySubCategory();
}
