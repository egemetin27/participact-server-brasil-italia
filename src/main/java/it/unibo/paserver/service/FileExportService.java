package it.unibo.paserver.service;

import com.google.common.collect.ListMultimap;
import it.unibo.paserver.domain.Task;

import java.util.List;

public interface FileExportService {
	void createUserCsvFile(List<Object[]> items, long parentId);

	void createTaskCsvFile(List<Task> items, long userId);
	
	void createGpsCsvFile(long userId);

	void createIssueCsvFile(long userId, String userMunicipality);

	void createPrettyCSV(ListMultimap<String, Object> params, long userId);
}
