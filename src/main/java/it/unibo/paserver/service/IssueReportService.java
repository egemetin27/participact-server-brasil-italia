package it.unibo.paserver.service;

import com.google.common.collect.ListMultimap;
import it.unibo.paserver.domain.IssueReport;
import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IssueReportService {
	// Sava ou atualiza
	IssueReport saveOrUpdate(IssueReport ir);

	// Busca pelo id
	IssueReport findById(long id);

	IssueReport find(long id);
	// Todos os itens
	List<IssueReport> findAll();

	// Total de registros
	Long getCount();

	// Removed um item
	boolean removed(long id);

	List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);
	List<Object[]> searchByStats(ListMultimap<String, Object> params, DateTime queryStart, DateTime queryEnd,PageRequest pagerequest);
	List<Object[]> searchByStatsGrouped(ListMultimap<String, Object> params, DateTime queryStart, DateTime queryEnd, PageRequest pagerequest);
	
	List<IssueReport> filter(ListMultimap<String, Object> params, PageRequest pagerequest);

	Long searchTotal(ListMultimap<String, Object> params);

	void incrementNegativeScore(Long reportId);

	List<IssueReport> fetchAll();

    IssueReport findByPublicProtocol(String numProtocolo);

    IssueReport findByPrivateProtocol(Integer idManifestacao);

    List<Object[]> searchByNativeQuery(ListMultimap<String, Object> params, PageRequest pagerequest);

    List<IssueReport> fetchAll(String userMunicipality);
}
