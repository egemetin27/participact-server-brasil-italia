package br.gov.cgu.eouv.service;

import br.gov.cgu.eouv.domain.Ouvidoria;

import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/04/2019
 **/
public interface OuvidoriaService {
    Ouvidoria saveOrUpdate(Ouvidoria o);
    Ouvidoria find(Long codOrg);
    List<Ouvidoria> fetchAll();
    List<Ouvidoria> filter(String search);
    boolean removeAll();

    List<Object[]> fetchAllowOmbudsmansOffice(String mun);
}
