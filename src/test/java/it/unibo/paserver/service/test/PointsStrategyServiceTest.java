package it.unibo.paserver.service.test;

import static org.junit.Assert.assertEquals;
import it.unibo.paserver.config.TestDataContextConfiguration;
import it.unibo.paserver.config.test.InfrastructureContextConfiguration;
import it.unibo.paserver.gamificationlogic.PointsStrategy;
import it.unibo.paserver.service.PointsStrategyService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { InfrastructureContextConfiguration.class,
		TestDataContextConfiguration.class })
@Transactional
public class PointsStrategyServiceTest {

	@Autowired
	PointsStrategyService pointsStrategyService;

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

	@Test
	public void getAllStrategies() {
		List<PointsStrategy> foundList = pointsStrategyService
				.getAllStrategies();
		Set<PointsStrategy> foundSet = new HashSet<PointsStrategy>(foundList);
		Set<PointsStrategy> expectedSet = new HashSet<PointsStrategy>();
		expectedSet.add(averageReputationStrategy);
		expectedSet.add(averageLevelStrategy);
		expectedSet.add(sumLevelStrategy);
		expectedSet.add(sumReputationStrategy);
		assertEquals(expectedSet, foundSet);
	}

	@Test
	public void getStrategyById() {
		PointsStrategy found = pointsStrategyService
				.getStrategyById(averageReputationStrategy.getId());
		PointsStrategy found2 = pointsStrategyService
				.getStrategyById(averageLevelStrategy.getId());
		PointsStrategy found3 = pointsStrategyService
				.getStrategyById(sumLevelStrategy.getId());
		PointsStrategy found4 = pointsStrategyService
				.getStrategyById(sumReputationStrategy.getId());
		assertEquals(found, averageReputationStrategy);
		assertEquals(found2, averageLevelStrategy);
		assertEquals(found3, sumLevelStrategy);
		assertEquals(found4, sumReputationStrategy);
	}

	@Test
	public void getStrategyByName() {
		PointsStrategy found = pointsStrategyService
				.getStrategyByName(averageReputationStrategy.getName());
		PointsStrategy found2 = pointsStrategyService
				.getStrategyByName(averageLevelStrategy.getName());
		assertEquals(found, averageReputationStrategy);
		assertEquals(found2, averageLevelStrategy);
	}

}
