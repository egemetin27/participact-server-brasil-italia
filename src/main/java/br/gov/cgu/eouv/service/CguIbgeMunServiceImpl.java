package br.gov.cgu.eouv.service;

import br.gov.cgu.eouv.domain.CguIbgeMun;
import br.gov.cgu.eouv.repository.CguIbgeMunRepository;
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
public class CguIbgeMunServiceImpl implements CguIbgeMunService {
    @Autowired
    CguIbgeMunRepository repos;

    @Override
    public CguIbgeMun saveOrUpdate(CguIbgeMun m) {
        return repos.saveOrUpdate(m);
    }

    @Override
    public CguIbgeMun find(Long id) {
        return repos.find(id);
    }

    @Override
    public List<CguIbgeMun> fetchAll() {
        return repos.fetchAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CguIbgeMun> filter(String search, PageRequest pagerequest) {
        return repos.filter(search, pagerequest);
    }

    @Override
    public Long searchTotal(String search) {
        return repos.searchTotal(search);
    }

    @Override
    public CguIbgeMun findByCodMun7(Long idMunicipio) {
        return repos.findByCodMun7(idMunicipio);
    }

    @Override
    public CguIbgeMun findByCodMun6(Long codMun6) {
        return repos.findByCodMun6(codMun6);
    }
}
