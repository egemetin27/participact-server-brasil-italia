package br.gov.cgu.eouv.service;

import br.gov.cgu.eouv.domain.ManifestacaoHistorico;
import br.gov.cgu.eouv.repository.ManifestacaoHistoricoRepository;
import org.joda.time.DateTime;
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
public class ManifestacaoHistoricoServiceImpl implements ManifestacaoHistoricoService {
    @Autowired
    ManifestacaoHistoricoRepository repos;

    @Override
    @Transactional(readOnly = false)
    public ManifestacaoHistorico saveOrUpdate(ManifestacaoHistorico m) {
        return repos.saveOrUpdate(m);
    }

    @Override
    @Transactional(readOnly = true)
    public ManifestacaoHistorico find(Long id) {
        return repos.find(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManifestacaoHistorico> fetchAll() {
        return repos.fetchAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManifestacaoHistorico> fetchAllByRelationId(Long relationshipId){
        return repos.fetchAllByRelationId(relationshipId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManifestacaoHistorico> filter(String search) {
        return repos.filter(search);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteAll(Long id) {
        return repos.deleteAll(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean itContaimItem(Long relationshipId, DateTime dataAcao) {
        return repos.itContaimItem(relationshipId, dataAcao);
    }
}
