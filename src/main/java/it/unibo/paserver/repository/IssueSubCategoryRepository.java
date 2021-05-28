package it.unibo.paserver.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.IssueSubCategory;

public interface IssueSubCategoryRepository {
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

	Long searchTotal(ListMultimap<String, Object> params);

	List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);

	List<IssueSubCategory> filter(ListMultimap<String, Object> params, PageRequest pagerequest);

    boolean removeAll(Long categoryId);

    boolean reorder(long id, int index);

    List<Object[]> getTotalBySubCategory();
}
