package it.unibo.paserver.service;

import it.unibo.paserver.domain.DataSetNode;
import it.unibo.paserver.repository.DataSetNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Claudio
 * @project participact-server
 * @date 01/03/2019
 **/
@Service
public class DataSetNodeServiceImpl implements DataSetNodeService  {
    @Autowired
    DataSetNodeRepository repos;

    @Override
    @Transactional(readOnly = false)
    public DataSetNode saveOrUpdate(DataSetNode n) {
        return repos.saveOrUpdate(n);
    }
}
