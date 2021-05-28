package it.unibo.paserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.QuestionnaireResponse;
import it.unibo.paserver.repository.QuestionnaireResponseRepository;

@Service
@Transactional(readOnly = true)
public class QuestionnaireResponseServiceImpl implements QuestionnaireResponseService {
	@Autowired
	QuestionnaireResponseRepository repos;
	
	@Override
	@Transactional(readOnly = false)
	public QuestionnaireResponse saveOrUpdate(QuestionnaireResponse s) {
		return repos.saveOrUpdate(s);
	}

	@Override
	@Transactional(readOnly = false)
	public QuestionnaireResponse save(QuestionnaireResponse s) {
		return repos.saved(s);
	}

	@Override
	public QuestionnaireResponse findById(long id) {
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public List<Object[]> search(long taskId, long actionId, long userId, PageRequest pagerequest) {
		return repos.search(taskId, actionId, userId, pagerequest);
	}

	@Override
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
	public Long searchTotal(long taskId, long actionId) {
		return repos.searchTotal(taskId, actionId);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean incrementClosedAnswer(Long questionId, Long answerId) {
		return repos.incrementClosedAnswer(questionId, answerId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<QuestionnaireResponse> search(Long questionId, PageRequest pagerequest) {
		return repos.search(questionId, pagerequest);
	}
}
