package it.unibo.paserver.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import it.unibo.paserver.domain.QuestionnaireResponse;

public interface QuestionnaireResponseRepository {
	// Sava ou atualiza
	QuestionnaireResponse saveOrUpdate(QuestionnaireResponse s);

	// Busca pelo id
	QuestionnaireResponse findById(long id);

	QuestionnaireResponse saved(QuestionnaireResponse s);

	QuestionnaireResponse updated(QuestionnaireResponse s);

	QuestionnaireResponse findByIds(Long taskId, Long actionId, Long questionId, Long userId);

	List<Object[]> search(long taskId, long actionId, long userId, PageRequest pagerequest);

	Long searchTotal(long taskId, long actionId);

	boolean incrementClosedAnswer(Long questionId, Long answerId);

    List<QuestionnaireResponse> search(Long questionId, PageRequest pagerequest);
}
