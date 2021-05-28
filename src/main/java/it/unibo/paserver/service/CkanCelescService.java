package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.google.common.collect.ListMultimap;

import it.unibo.paserver.domain.CkanCelesc;

public interface CkanCelescService {
	List<Object[]> search(ListMultimap<String, Object> params, String sortField, boolean ascending, PageRequest pagerequest);

	Long searchTotal(ListMultimap<String, Object> params);

	List<CkanCelesc> filter(ListMultimap<String, Object> params, String string, boolean b, PageRequest pagerequest);
}
