package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.SchoolCourse;
import it.unibo.paserver.repository.SchoolCourseRepository;

@Service
@Transactional(readOnly = true)
public class SchoolCourseServiceImpl implements SchoolCourseService {
	@Autowired
	SchoolCourseRepository repos;

	@Override
	@Transactional(readOnly = true)
	public List<SchoolCourse> findAll() {
		
		return repos.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public SchoolCourse saveOrUpdate(SchoolCourse s) {
		
		return repos.saveOrUpdate(s);
	}

	@Override
	@Transactional(readOnly = true)
	public SchoolCourse findById(long id) {
		
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removed(long id) {
		
		return repos.removed(id);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Object[]> search(String name, String description, String uniCourseId, PageRequest pageable) {
		
		return repos.search(name, description, uniCourseId, pageable);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Long searchTotal(String name, String description, String uniCourseId) {
		
		return repos.searchTotal(name, description, uniCourseId);
	}

	@Override
	public SchoolCourse findByName(String schoolCourse) {
		
		return repos.findByName(schoolCourse);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<SchoolCourse> filter(ListMultimap<String, Object> params, PageRequest pagerequest) {
		
		return repos.filter(params, pagerequest);
	}
}
