package br.gov.cgu.eouv.service;

import br.gov.cgu.eouv.domain.Ouvidoria;
import br.gov.cgu.eouv.repository.OuvidoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/04/2019
 **/
@Service
@Transactional(readOnly = true)
public class OuvidoriaServiceImpl implements OuvidoriaService {
    @Autowired
    OuvidoriaRepository repos;

    @Override
    @Transactional(readOnly = false)
    public Ouvidoria saveOrUpdate(Ouvidoria o) {
        return repos.saveOrUpdate(o);
    }

    @Override
    @Transactional(readOnly = true)
    public Ouvidoria find(Long codOrg) {
        return repos.find(codOrg);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ouvidoria> fetchAll() {
        return repos.fetchAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ouvidoria> filter(String search) {
        return repos.filter(search);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean removeAll() {
        return repos.removeAll();
    }

    @Override
    public List<Object[]> fetchAllowOmbudsmansOffice(String mun) {
        return repos.fetchAllowOmbudsmansOffice(mun);
    }
}
