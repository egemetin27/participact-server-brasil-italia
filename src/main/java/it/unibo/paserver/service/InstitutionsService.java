package it.unibo.paserver.service;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.Institutions;

public interface InstitutionsService {
	// Sava ou atualiza
	Institutions saveOrUpdate(Institutions i);

	// Busca pelo nome
	Institutions findByName(String name);

	// Busca pelo id
	Institutions findById(long id);

	// Todos os itens
	List<Institutions> findAll();

	// Total de registros
	Long getCount();

	// Removed um item
	boolean removed(long id);

	// Busca customizada
	List<Object[]> search(String name, String address, String email, String phone, PageRequest pageable);

	// Total da busca customizada
	long searchTotal(String name, String address, String email, String phone);

	List<Institutions> filter(ListMultimap<String, Object> params, PageRequest pagerequest);
}
