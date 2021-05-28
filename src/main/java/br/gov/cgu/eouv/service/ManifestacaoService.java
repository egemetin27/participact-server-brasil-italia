package br.gov.cgu.eouv.service;

import br.gov.cgu.eouv.domain.ManifestacaoHistorico;
import br.gov.cgu.eouv.domain.ManifestacaoResposta;
import it.unibo.paserver.domain.IssueReport;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/05/2019
 **/
public interface ManifestacaoService {
    boolean getListaManifestacaoOuvidoria(String numProtocolo, Long issueId);

    @Transactional(readOnly = false)
    @Async
    void registerThirdPartyManifestation(IssueReport issue);

    @Transactional(readOnly = false)
    IssueReport includeReportOmbudsman(IssueReport issue);

    @Async
    @Transactional(readOnly = false)
    void pushOmdudsman(Long userId, String message);

    @Transactional(readOnly = false)
    void disableOmbudsman(IssueReport issue);

    List<ManifestacaoHistorico> getListaManifestacaoHistorico(Long issueId);

    List<ManifestacaoResposta> getListaManifestacaoResposta(Long issueId);

    ManifestacaoResposta getLastManifestacaoResposta(Long issueId);
}
