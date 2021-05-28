package br.gov.cgu.eouv.service;

import br.gov.cgu.eouv.domain.ManifestacaoHistorico;
import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/04/2019
 **/
public interface ManifestacaoHistoricoService {
    ManifestacaoHistorico saveOrUpdate(ManifestacaoHistorico m);

    ManifestacaoHistorico find(Long id);

    List<ManifestacaoHistorico> fetchAll();

    List<ManifestacaoHistorico> fetchAllByRelationId(Long relationshipId);

    List<ManifestacaoHistorico> filter(String search);

    boolean deleteAll(Long id);

    boolean itContaimItem(Long id, DateTime dataAcao);
}
