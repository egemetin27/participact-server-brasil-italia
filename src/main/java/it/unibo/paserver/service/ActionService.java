package it.unibo.paserver.service;

import java.util.List;

import it.unibo.paserver.domain.Action;

public interface ActionService {

	Action findById(long id);

	Action findByIdAndDetach(Long id);
	
	Action findByName(String name);

	Action save(Action action);

	Long getActionsCount();

	Long getActionsCount(long taskId);

	List<Action> getActions();

	List<Action> getActions(long taskId);
	
	List<Action> fetchAll(long taskId);

	boolean deleteAction(long id);

	Long getActionsCountAndNumeric(long taskId, int typeId);

	Long whatIsMyTaskId(long actionId);

	Long getCountQuestionaire(long taskId);

	List<Object[]> fetchClosedAnswer(long questionId);
}
