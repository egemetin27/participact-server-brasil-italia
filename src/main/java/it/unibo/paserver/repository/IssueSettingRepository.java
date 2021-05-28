package it.unibo.paserver.repository;

import it.unibo.paserver.domain.IssueSetting;

public interface IssueSettingRepository {
	// Sava ou atualiza
	IssueSetting saveOrUpdate(IssueSetting is);

	// Busca pelo id
	IssueSetting findById(long id);

	boolean inAppleReview(long l);
}
