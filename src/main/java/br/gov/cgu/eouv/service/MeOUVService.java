package br.gov.cgu.eouv.service;

import br.gov.cgu.eouv.domain.OAuthToken;
import br.gov.cgu.eouv.result.rest.ManifestacaoConsultaRespostaDTO;
import br.gov.cgu.eouv.result.rest.ManifestacaoDTO;
import br.gov.cgu.eouv.result.soap.RegistrarManifestacaoResult;
import it.unibo.paserver.domain.IssueReport;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
public interface MeOUVService {

    OAuthToken getOAuthToken();

    RegistrarManifestacaoResult postManifestacaoInclusao(IssueReport issue);

    ManifestacaoConsultaRespostaDTO[] getManifestacaoConsulta(Integer idSituacaoManifestacao, String dataAtualizacaoInicio, String dataAtualizacaoFim);

    // ManifestacaoConsultaRespostaDTO[] getListaManifestacao(Integer idSituacaoManifestacao, String dataAtualizacaoInicio, String dataAtualizacaoFim);

    ManifestacaoDTO getDetalhaManifestacao(Integer IdManifestacao);
}
