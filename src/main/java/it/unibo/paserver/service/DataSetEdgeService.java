package it.unibo.paserver.service;

import it.unibo.paserver.domain.DataSetEdge;

/**
 * @author Claudio
 * @project participact-server
 * @date 01/03/2019
 **/
public interface DataSetEdgeService {

    DataSetEdge saveOrUpdate(DataSetEdge e);

    boolean updateTargetNode(long fromId, int fromOrder, long targetId, int targetOrder);

    boolean updateTargetLeaf(long fromId, long leafId, int leafOrder, long toId, int toOrder);
}
