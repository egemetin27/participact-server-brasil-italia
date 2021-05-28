package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.SchoolCourse;

public interface SchoolCourseService {
	// Retorna todos os items
	List<SchoolCourse> findAll();

	// Salva ou atualiza
	SchoolCourse saveOrUpdate(SchoolCourse s);

	SchoolCourse findById(long id);

	boolean removed(long id);

	List<Object[]> search(String name, String description, String uniCourseId, PageRequest pageable);

	Long searchTotal(String name, String description, String uniCourseId);

	SchoolCourse findByName(String schoolCourse);

	List<SchoolCourse> filter(ListMultimap<String, Object> params, PageRequest pagerequest);
}