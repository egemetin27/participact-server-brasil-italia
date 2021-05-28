package br.gov.cgu.eouv.service;

import br.gov.cgu.eouv.domain.CguCrontab;
import br.gov.cgu.eouv.repository.CguCrontabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 16/07/2019
 **/
@Service
@Transactional(readOnly = true)
public class CguCrontabServiceImpl implements CguCrontabService {
    @Autowired
    CguCrontabRepository repos;

    @Override
    @Transactional(readOnly = false)
    public CguCrontab saveOrUpdate(CguCrontab c) {
        return repos.saveOrUpdate(c);
    }

    @Override
    @Transactional(readOnly = true)
    public CguCrontab find(Long id) {
        return repos.find(id);
    }

    @Override
    @Transactional(readOnly = false)
    public CguCrontab first() {
        return repos.first();
    }
}
