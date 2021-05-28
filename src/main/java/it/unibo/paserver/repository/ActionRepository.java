package it.unibo.paserver.repository;

import java.util.List;

import it.unibo.paserver.domain.Action;

public interface ActionRepository {

	Action findById(long id);
	
	Action findByIdAndDetach(Long id);

	Action findByName(String name);

	Action save(Action action);

	Long getActionsCount();

	Long getActionsCount(long taskId);

	List<Action> getActions();

	List<Action> getActions(long taskId);

	boolean deleteAction(long id);

	List<Action> fetchAll(long taskId);

	Long getActionsCountAndNumeric(long taskId, int numberAction);

	Long whatIsMyTaskId(long actionId);

	Long getCountQuestionaire(long taskId);

	List<Object[]> fetchClosedAnswer(long questionId);
}
