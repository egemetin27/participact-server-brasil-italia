package br.gov.cgu.eouv.service;

import br.com.bergmannsoft.utils.Validator;
import br.gov.cgu.eouv.domain.OAuthToken;
import br.gov.cgu.eouv.result.rest.*;
import br.gov.cgu.eouv.result.soap.RegistrarManifestacaoResult;
import br.gov.cgu.eouv.ws.rest.MeOUVRestClient;
import it.unibo.paserver.domain.IssueReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Claudio
 * @project participact-server
 * @date 11/07/2019
 **/
@Service
@Transactional(readOnly = true)
public class MeOUVServiceImpl implements MeOUVService {
    @Autowired
    private OAuthTokenRestService oAuthTokenRestService;

    @Override
    @Transactional(readOnly = false)
    public OAuthToken getOAuthToken() {
        // Obtem ultimo token
        OAuthToken oAuthToken = oAuthTokenRestService.getLastAvaliable();
        if (oAuthToken != null && !oAuthToken.isExpired()) {
            return oAuthToken;
        } else {
            // Client
            MeOUVRestClient client = new MeOUVRestClient();
            OAuthTokenRest oAuthTokenRest = client.getAuthToken();
            if (oAuthTokenRest != null) {
                oAuthToken = new OAuthToken(oAuthTokenRest);
                OAuthToken rowAuthToken = oAuthTokenRestService.saveOrUpdate(oAuthToken);
                if (rowAuthToken != null) {
                    return rowAuthToken;
                }
            }
        }
        return null;
    }

    @Override
    public RegistrarManifestacaoResult postManifestacaoInclusao(IssueReport issue) {
        //Result
        RegistrarManifestacaoResult rs = new RegistrarManifestacaoResult();
        rs.setCodigoErro(500);
        rs.setDescricaoErro("Error");
        // Token
        OAuthToken oAuthToken = getOAuthToken();
        if (oAuthToken != null) {
            // Client
            MeOUVRestClient client = new MeOUVRestClient();
            // Request
            ManifestacaoInclusaoRespostaDTO respostaInclusao = client.postInclusaoManifestacao(oAuthToken.getAccessToken(), issue);
            if (respostaInclusao != null && !Validator.isEmptyString(respostaInclusao.getNumeroProtocolo())) {
                rs.setCodigoErro(0);
                rs.setDescricaoErro("Protocolo Registrado no Fala.BR");
                rs.setProtocolo(respostaInclusao.getNumeroProtocolo());
                rs.setCodigoAcesso(respostaInclusao.getCodigoAcesso());
                rs.setIdManifestacao(respostaInclusao.getIdManifestacao());
                // Links
                LinkDTO[] links = respostaInclusao.getLinks();
                if (links != null && links.length > 0) {
                    for (LinkDTO link : links) {
                        if (link.getRel().equals("self")) {
                            rs.setUrlSelf(link.getHref());
                        } else if (link.getRel().equals("eouv")||link.getRel().equals("fala.br")||link.getRel().equals("falabr")) {
                            rs.setUrlOuv(link.getHref());
                        }
                    }
                }
            }
        }
        // Res
        return rs;
    }

    @Override
    public ManifestacaoConsultaRespostaDTO[] getManifestacaoConsulta(Integer idSituacaoManifestacao, String dataAtualizacaoInicio, String dataAtualizacaoFim) {
        //Result
        ManifestacaoConsultaRespostaDTO[] rs = new ManifestacaoConsultaRespostaDTO[0];
        // Token
        OAuthToken oAuthToken = getOAuthToken();
        if (oAuthToken != null) {
            // Client
            MeOUVRestClient client = new MeOUVRestClient();
            rs = client.getListaManifestacao(oAuthToken.getAccessToken(), idSituacaoManifestacao, dataAtualizacaoInicio, dataAtualizacaoFim);
        }
        // Res
        return rs;
    }

    @Override
    public ManifestacaoDTO getDetalhaManifestacao(Integer idManifestacao){
        // Default
        ManifestacaoDTO rs = new ManifestacaoDTO();
        // Token
        OAuthToken oAuthToken = getOAuthToken();
        if (oAuthToken != null) {
            // Client
            MeOUVRestClient client = new MeOUVRestClient();
            rs = client.getDetalhaManifestacao(oAuthToken.getAccessToken(), idManifestacao);
        }
        // Return
        return rs;
    }
}
