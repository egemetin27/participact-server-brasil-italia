package br.gov.cgu.eouv.service;

import br.gov.cgu.eouv.domain.CguCrontab;

/**
 * @author Claudio
 * @project participact-server
 * @date 16/07/2019
 **/
public interface CguCrontabService {
    CguCrontab saveOrUpdate(CguCrontab c);

    CguCrontab find(Long id);

    CguCrontab first();
}
