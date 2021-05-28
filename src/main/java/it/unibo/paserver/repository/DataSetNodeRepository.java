package it.unibo.paserver.repository;

import it.unibo.paserver.domain.DataSetNode;

/**
 * @author Claudio
 * @project participact-server
 * @date 01/03/2019
 **/
public interface DataSetNodeRepository {
    DataSetNode saveOrUpdate(DataSetNode n);
}
