package br.gov.cgu.eouv.service;

import br.gov.cgu.eouv.domain.OrgaoSiorgOuvidoria;

import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 09/04/2019
 **/
public interface OrgaoSiorgOuvidoriaService {
    OrgaoSiorgOuvidoria saveOrUpdate(OrgaoSiorgOuvidoria o);
    OrgaoSiorgOuvidoria find(Long codOrg);
    List<OrgaoSiorgOuvidoria> fetchAll();

    List<OrgaoSiorgOuvidoria> filter(String search);
}
