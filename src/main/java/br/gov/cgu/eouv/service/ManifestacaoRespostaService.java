package br.gov.cgu.eouv.service;

import br.gov.cgu.eouv.domain.ManifestacaoResposta;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/04/2019
 **/
public interface ManifestacaoRespostaService {
    ManifestacaoResposta saveOrUpdate(ManifestacaoResposta m);

    ManifestacaoResposta find(Long id);

    List<ManifestacaoResposta> fetchAll();

    List<ManifestacaoResposta> filter(String search);

    boolean deleteAll(Long id);

    List<ManifestacaoResposta> fetchAllByRelationId(Long issueId);

    boolean itContaimItem(Long id, DateTime dataResposta);

    boolean itContaimItemById(Long id, Integer idRespostaManifestacao);

    ManifestacaoResposta getLastManifestacaoResposta(Long issueId);
}
