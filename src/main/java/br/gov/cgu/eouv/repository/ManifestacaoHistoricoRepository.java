package br.gov.cgu.eouv.repository;

import br.gov.cgu.eouv.domain.ManifestacaoHistorico;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/05/2019
 **/
public interface ManifestacaoHistoricoRepository {
    ManifestacaoHistorico saveOrUpdate(ManifestacaoHistorico m);

    ManifestacaoHistorico find(Long id);

    List<ManifestacaoHistorico> fetchAll();

    List<ManifestacaoHistorico> filter(String search);

    List<ManifestacaoHistorico> fetchAllByRelationId(Long relationshipId);

    boolean deleteAll(Long id);

    boolean itContaimItem(Long relationshipId, DateTime dataAcao);
}
