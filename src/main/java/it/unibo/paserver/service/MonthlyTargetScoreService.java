package it.unibo.paserver.service;

import it.unibo.paserver.domain.MonthlyTargetScore;

import java.util.List;

public interface MonthlyTargetScoreService {

	public MonthlyTargetScore save(MonthlyTargetScore monthlyTargetScore);

	public MonthlyTargetScore findById(long id);

	public MonthlyTargetScore findByYearMonth(int year, int month);

	public List<MonthlyTargetScore> getAll();

	public boolean delete(long id);

}
