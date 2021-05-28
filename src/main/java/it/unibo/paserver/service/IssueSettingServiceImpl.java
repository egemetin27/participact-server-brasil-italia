package it.unibo.paserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unibo.paserver.domain.IssueSetting;
import it.unibo.paserver.repository.IssueSettingRepository;

@Service
@Transactional(readOnly = true)
public class IssueSettingServiceImpl implements IssueSettingService {
	@Autowired
	IssueSettingRepository repos;

	@Override
	@Transactional(readOnly = false)
	public IssueSetting saveOrUpdate(IssueSetting is) {
		
		return repos.saveOrUpdate(is);
	}

	@Override
	@Transactional(readOnly = true)
	public IssueSetting findById(long id) {
		
		return repos.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean inAppleReview(long l) {
		
		return repos.inAppleReview(l);
	}
}