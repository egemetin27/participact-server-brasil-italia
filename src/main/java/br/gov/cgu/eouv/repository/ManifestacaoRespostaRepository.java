package br.gov.cgu.eouv.repository;

import br.gov.cgu.eouv.domain.ManifestacaoResposta;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/05/2019
 **/
public interface ManifestacaoRespostaRepository {
    ManifestacaoResposta saveOrUpdate(ManifestacaoResposta m);

    ManifestacaoResposta find(Long id);

    List<ManifestacaoResposta> fetchAll();

    List<ManifestacaoResposta> filter(String search);

    List<ManifestacaoResposta> fetchAllByRelationId(Long relationshipId);

    boolean deleteAll(Long id);

    boolean itContaimItem(Long relationshipId, DateTime dataResposta);

    boolean itContaimItemById(Long relationshipId, Integer idRespostaManifestacao);

    ManifestacaoResposta getLastManifestacaoResposta(Long issueId);
}
