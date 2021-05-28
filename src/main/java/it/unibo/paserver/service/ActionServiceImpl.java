package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ClosedAnswer;
import it.unibo.paserver.domain.Question;
import it.unibo.paserver.repository.ActionRepository;
import it.unibo.paserver.repository.JpaActionRepository;

@Service
@Transactional(readOnly = true)
public class ActionServiceImpl implements ActionService {

	@Autowired
	ActionRepository actionRepository;

	@Override
	@Transactional(readOnly = false)
	public Action save(Action action) {
		if (actionRepository == null)
			actionRepository = new JpaActionRepository();
		return actionRepository.save(action);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteAction(long id) {
		return actionRepository.deleteAction(id);
	}

	@Override
	public Action findById(long id) {
		return actionRepository.findById(id);
	}

	@Override
	public Action findByIdAndDetach(Long id) {
		
		return actionRepository.findByIdAndDetach(id);
	}
	
	@Override
	public Action findByName(String name) {
		return actionRepository.findByName(name);
	}

	@Override
	public List<Action> getActions() {
		return actionRepository.getActions();
	}

	@Override
	public Long getActionsCount() {
		return actionRepository.getActionsCount();
	}

	@Override
	public Long getActionsCount(long taskId) {
		return actionRepository.getActionsCount(taskId);
	}

	@Override
	public List<Action> getActions(long taskId) {
		return actionRepository.getActions();
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Action> fetchAll(long taskId) {
		
		return actionRepository.fetchAll(taskId);
	}

	@Override
	public Long getActionsCountAndNumeric(long taskId, int typeId) {
		
		return actionRepository.getActionsCountAndNumeric(taskId, typeId);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public Long whatIsMyTaskId(long actionId) {
		
		return actionRepository.whatIsMyTaskId(actionId);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public Long getCountQuestionaire(long taskId) {
		
		return actionRepository.getCountQuestionaire(taskId);
	}

	@Override
	public List<Object[]> fetchClosedAnswer(long questionId) {
		
		return actionRepository.fetchClosedAnswer(questionId);
	}
}
