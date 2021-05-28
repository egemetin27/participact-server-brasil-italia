package br.gov.cgu.eouv.repository;

import br.gov.cgu.eouv.domain.CguCrontab;

public interface CguCrontabRepository {
    CguCrontab saveOrUpdate(CguCrontab c);

    CguCrontab find(Long id);

    CguCrontab first();
}
