package it.unibo.paserver.service;

import it.unibo.paserver.gamificationlogic.PointsStrategy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PointsStrategyServiceImpl implements PointsStrategyService {

	@Autowired
	@Qualifier("PointsStrategyByAverageReputation")
	PointsStrategy averageReputationStrategy;

	@Autowired
	@Qualifier("PointsStrategyByAverageLevel")
	PointsStrategy averageLevelStrategy;
	
	@Autowired
	@Qualifier("PointsStrategyBySumReputation")
	PointsStrategy sumReputationStrategy;

	@Autowired
	@Qualifier("PointsStrategyBySumLevel")
	PointsStrategy sumLevelStrategy;

	List<PointsStrategy> allStrategies;

	@Override
	public List<PointsStrategy> getAllStrategies() {

		if (allStrategies == null) {
			allStrategies = new ArrayList<PointsStrategy>(2);
			allStrategies.add(averageLevelStrategy);
			allStrategies.add(averageReputationStrategy);
			allStrategies.add(sumLevelStrategy);
			allStrategies.add(sumReputationStrategy);
		}

		return allStrategies;
	}

	@Override
	public PointsStrategy getStrategyById(int id) {
		if (id == averageReputationStrategy.getId())
			return averageReputationStrategy;
		else if (id == averageLevelStrategy.getId())
			return averageLevelStrategy;
		else if (id == sumLevelStrategy.getId())
			return sumLevelStrategy;
		else if (id== sumReputationStrategy.getId())
			return sumReputationStrategy;
		return null;
	}

	@Override
	public PointsStrategy getStrategyByName(String name) {
		if (name != null) {
			if (name.equalsIgnoreCase(averageReputationStrategy.getName()))
				return averageReputationStrategy;
			else if (name.equalsIgnoreCase(averageLevelStrategy.getName()))
				return averageLevelStrategy;
			else if (name.equalsIgnoreCase(sumLevelStrategy.getName()))
				return sumLevelStrategy;
			else if (name.equalsIgnoreCase(sumReputationStrategy.getName()))
				return sumReputationStrategy;
		}
		return null;
	}

}
