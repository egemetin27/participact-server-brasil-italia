package it.unibo.paserver.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.SchoolCourse;;

public interface SchoolCourseRepository {
	// Sava ou atualiza
	SchoolCourse saveOrUpdate(SchoolCourse s);

	// Busca pelo id
	SchoolCourse findById(long id);

	// Todos os itens
	List<SchoolCourse> findAll();

	// Removed um item
	boolean removed(long id);

	// Busca customizada
	List<Object[]> search(String name, String description, String uniCourseId, PageRequest pageable);

	// Total da busca customizada
	long searchTotal(String name, String description, String uniCourseId);

	SchoolCourse findByName(String schoolCourse);

	List<SchoolCourse> filter(ListMultimap<String, Object> conditions, PageRequest pageable);
}
