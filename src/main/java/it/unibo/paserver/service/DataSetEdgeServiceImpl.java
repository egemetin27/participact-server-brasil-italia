package it.unibo.paserver.service;

import it.unibo.paserver.domain.DataSetEdge;
import it.unibo.paserver.repository.DataSetEdgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Claudio
 * @project participact-server
 * @date 01/03/2019
 **/
@Service
public class DataSetEdgeServiceImpl implements DataSetEdgeService {
    @Autowired
    DataSetEdgeRepository repos;

    @Override
    @Transactional(readOnly = false)
    public DataSetEdge saveOrUpdate(DataSetEdge e) {
        return repos.saveOrUpdate(e);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean updateTargetNode(long fromId, int fromOrder, long targetId, int targetOrder) {
        return repos.updateTargetNode(fromId, fromOrder, targetId, targetOrder);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean updateTargetLeaf(long fromId, long leafId, int leafOrder, long toId, int toOrder) {
        return repos.updateTargetLeaf(fromId, leafId, leafOrder, toId, toOrder);
    }
}
