package it.unibo.paserver.service;

import it.unibo.paserver.gamificationlogic.PointsStrategy;

import java.util.List;

/**
 * Service for PointsStrategies.
 * 
 * @author danielecampogiani
 * @see PointsStrategy
 *
 */
public interface PointsStrategyService {

	List<PointsStrategy> getAllStrategies();

	PointsStrategy getStrategyById(int id);

	PointsStrategy getStrategyByName(String name);

}
