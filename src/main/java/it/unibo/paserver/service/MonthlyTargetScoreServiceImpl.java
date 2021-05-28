package it.unibo.paserver.service;

import it.unibo.paserver.domain.MonthlyTargetScore;
import it.unibo.paserver.repository.MonthlyTargetScoreRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonthlyTargetScoreServiceImpl implements MonthlyTargetScoreService {

	@Autowired
	MonthlyTargetScoreRepository monthlyTargetScoreRepository;

	@Override
	@Transactional(readOnly = false)
	public MonthlyTargetScore save(MonthlyTargetScore monthlyTargetScore) {
		return monthlyTargetScoreRepository.save(monthlyTargetScore);
	}

	@Override
	@Transactional(readOnly = true)
	public MonthlyTargetScore findById(long id) {
		return monthlyTargetScoreRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public MonthlyTargetScore findByYearMonth(int year, int month) {
		return monthlyTargetScoreRepository.findByYearMonth(year, month);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean delete(long id) {
		return monthlyTargetScoreRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public List<MonthlyTargetScore> getAll() {
		return monthlyTargetScoreRepository.getAll();
	}

}
