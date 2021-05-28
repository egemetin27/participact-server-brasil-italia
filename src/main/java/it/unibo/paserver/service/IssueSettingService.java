package it.unibo.paserver.service;

import it.unibo.paserver.domain.IssueSetting;

public interface IssueSettingService {
	// Sava ou atualiza
	IssueSetting saveOrUpdate(IssueSetting is);

	// Busca pelo id
	IssueSetting findById(long id);

	boolean inAppleReview(long l);
}
