package it.unibo.paserver.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import it.unibo.paserver.domain.QuestionnaireResponse;

public interface QuestionnaireResponseService {
	// Salva ou atualiza
	QuestionnaireResponse saveOrUpdate(QuestionnaireResponse s);
	QuestionnaireResponse save(QuestionnaireResponse qr);
	//Busca pela chave principal
	QuestionnaireResponse findById(long id);
	
	List<Object[]> search(long campaign_id, long action_id, long user_id, PageRequest pagerequest);
	
	Long searchTotal(long taskId, long actionId);

	boolean incrementClosedAnswer(Long questionId, Long answerId);

	List<QuestionnaireResponse> search(Long questionId, PageRequest pagerequest);
}