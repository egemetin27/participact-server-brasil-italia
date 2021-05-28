package br.gov.cgu.eouv.service;

import br.gov.cgu.eouv.domain.ManifestacaoResposta;
import br.gov.cgu.eouv.repository.ManifestacaoRespostaRepository;
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
public class ManifestacaoRespostaServiceImpl implements ManifestacaoRespostaService {
    @Autowired
    ManifestacaoRespostaRepository repos;

    @Override
    @Transactional(readOnly = false)
    public ManifestacaoResposta saveOrUpdate(ManifestacaoResposta m) {
        return repos.saveOrUpdate(m);
    }

    @Override
    @Transactional(readOnly = true)
    public ManifestacaoResposta find(Long id) {
        return repos.find(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManifestacaoResposta> fetchAll() {
        return repos.fetchAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManifestacaoResposta> filter(String search) {
        return repos.filter(search);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteAll(Long id) {
        return repos.deleteAll(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManifestacaoResposta> fetchAllByRelationId(Long issueId) {
        return repos.fetchAllByRelationId(issueId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean itContaimItem(Long relationshipId, DateTime dataResposta) {
        return repos.itContaimItem(relationshipId, dataResposta);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean itContaimItemById(Long relationshipId, Integer idRespostaManifestacao) {
        return repos.itContaimItemById(relationshipId, idRespostaManifestacao);
    }

    @Override
    @Transactional(readOnly = true)
    public ManifestacaoResposta getLastManifestacaoResposta(Long issueId) {
        return repos.getLastManifestacaoResposta(issueId);
    }
}
