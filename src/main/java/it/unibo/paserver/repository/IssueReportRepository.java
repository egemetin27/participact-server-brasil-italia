package it.unibo.paserver.repository;

import com.google.common.collect.ListMultimap;
import it.unibo.paserver.domain.IssueReport;
import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IssueReportRepository {
	// Sava ou atualiza
	IssueReport saveOrUpdate(IssueReport ir);

    IssueReport find(long id);

    // Busca pelo id
	IssueReport findById(long id);

	// Todos os itens
	List<IssueReport> findAll();
	List<IssueReport> fetchAll();
	// Total de registros
	Long getCount();

	// Removed um item
	boolean removed(long id);

	Long searchTotal(ListMultimap<String, Object> params);

	List<Object[]> search(ListMultimap<String, Object> params, PageRequest pagerequest);

    List<Object[]> searchByNativeQuery(ListMultimap<String, Object> conditions, PageRequest pageable);

    List<Object[]> searchByStats(ListMultimap<String, Object> conditions, DateTime queryStart, DateTime queryEnd, PageRequest pageable);
	List<Object[]> searchByStatsGroupd(ListMultimap<String, Object> conditions, DateTime queryStart, DateTime queryEnd, PageRequest pageable);
	
	List<IssueReport> filter(ListMultimap<String, Object> params, PageRequest pagerequest);

	void incrementNegativeScore(Long reportId);
    IssueReport findByPublicProtocol(String numProtocolo);
    IssueReport findByPrivateProtocol(Integer numProtocolo);

	List<IssueReport> fetchAll(String city);
}
