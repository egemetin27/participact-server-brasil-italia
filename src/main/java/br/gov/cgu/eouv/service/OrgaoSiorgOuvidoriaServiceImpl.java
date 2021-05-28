package br.gov.cgu.eouv.service;

import br.gov.cgu.eouv.domain.OrgaoSiorgOuvidoria;
import br.gov.cgu.eouv.repository.OrgaoSiorgOuvidoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 09/04/2019
 **/
@Service
@Transactional(readOnly = true)
public class OrgaoSiorgOuvidoriaServiceImpl implements OrgaoSiorgOuvidoriaService {
    @Autowired
    OrgaoSiorgOuvidoriaRepository repos;

    @Override
    @Transactional(readOnly = false)
    public OrgaoSiorgOuvidoria saveOrUpdate(OrgaoSiorgOuvidoria o) {
        return repos.saveOrUpdate(o);
    }

    @Override
    @Transactional(readOnly = true)
    public OrgaoSiorgOuvidoria find(Long codOrg) {
        return repos.find(codOrg);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrgaoSiorgOuvidoria> fetchAll() {
        return repos.fetchAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrgaoSiorgOuvidoria> filter(String search) {
        return repos.filter(search);
    }
}
