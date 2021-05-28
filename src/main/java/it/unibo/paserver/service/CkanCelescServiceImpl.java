package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.CkanCelesc;
import it.unibo.paserver.repository.CkanCelescRepository;

@Service
@Transactional(readOnly = true)
public class CkanCelescServiceImpl implements CkanCelescService {

	@Autowired
	CkanCelescRepository repos;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Object[]> search(ListMultimap<String, Object> params, String sortField, boolean ascending, PageRequest pagerequest) {
		
		return repos.search(params, sortField, ascending, pagerequest);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Long searchTotal(ListMultimap<String, Object> params) {
		
		return repos.searchTotal(params);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<CkanCelesc> filter(ListMultimap<String, Object> params, String sortField, boolean ascending, PageRequest pagerequest) {
		
		return repos.filter(params, sortField, ascending, pagerequest);
	}
}