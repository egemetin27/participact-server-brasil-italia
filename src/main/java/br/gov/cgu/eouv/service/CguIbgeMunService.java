package br.gov.cgu.eouv.service;

import br.gov.cgu.eouv.domain.CguIbgeMun;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 16/07/2019
 **/
public interface CguIbgeMunService {
    CguIbgeMun saveOrUpdate(CguIbgeMun m);
    CguIbgeMun find(Long id);
    List<CguIbgeMun> fetchAll();
    List<CguIbgeMun> filter(String search, PageRequest pagerequest);

    Long searchTotal(String search);

    CguIbgeMun findByCodMun7(Long codMun7);

    CguIbgeMun findByCodMun6(Long codMun6);
}
