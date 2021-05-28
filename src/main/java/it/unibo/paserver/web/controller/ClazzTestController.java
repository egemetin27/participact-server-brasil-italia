package it.unibo.paserver.web.controller;

import br.gov.cgu.eouv.domain.OAuthToken;
import br.gov.cgu.eouv.domain.OrgaoSiorgOuvidoria;
import br.gov.cgu.eouv.domain.Ouvidoria;
import br.gov.cgu.eouv.result.rest.ManifestacaoConsultaRespostaDTO;
import br.gov.cgu.eouv.result.rest.ManifestacaoDTO;
import br.gov.cgu.eouv.result.rest.OAuthTokenRest;
import br.gov.cgu.eouv.service.MeOUVService;
import br.gov.cgu.eouv.service.OAuthTokenRestService;
import br.gov.cgu.eouv.ws.rest.MeOUVRestClient;
import it.unibo.paserver.domain.ResponseJson;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * NAO USE / DON'T USE
 * Classe para testes rapidos, sem funcao especifica.
 *
 * @author Claudio
 * @project participact-server
 * @date 10/07/2019
 **/
@SuppressWarnings("Duplicates")
@Controller
public class ClazzTestController {
    ResponseJson response = new ResponseJson();
    @Autowired
    private OAuthTokenRestService oAuthTokenRestService;
    @Autowired
    private MeOUVService meOUVService;

    @RequestMapping(value = "/protected/clazz-test/cgu-rest/oauth", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    ResponseJson oauthToken(HttpServletRequest request) {
        response.setStatus(false);
        // Obtem ultimo token
        OAuthToken oAuthToken = oAuthTokenRestService.getLastAvaliable();
        if (oAuthToken != null && !oAuthToken.isExpired()) {
            response.setMessage(oAuthToken.toString());
            response.setStatus(true);
        } else {
            // Client
            MeOUVRestClient meOUVRestClient = new MeOUVRestClient();
            OAuthTokenRest oAuthTokenRest = meOUVRestClient.getAuthToken();
            if (oAuthTokenRest != null) {
                oAuthToken = new OAuthToken(oAuthTokenRest);
                OAuthToken rowAuthToken = oAuthTokenRestService.saveOrUpdate(oAuthToken);
                if (rowAuthToken != null) {
                    response.setStatus(true);
                    response.setMessage(rowAuthToken.toString());
                }
            }
        }
        // return
        return response;
    }

    @RequestMapping(value = "/protected/clazz-test/cgu-rest/orgaos-siorg", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    ResponseJson orgaosSiorg(HttpServletRequest request) {
        response.setStatus(false);
        // Client
        MeOUVRestClient meOUVRestClient = new MeOUVRestClient();
        OAuthToken oAuthToken = meOUVService.getOAuthToken();
        if (oAuthToken != null) {
            List<OrgaoSiorgOuvidoria> orgaosSiorg = meOUVRestClient.getOrgaosSiorg(oAuthToken.getAccessToken());
            response.setStatus(true);
            response.setItem(orgaosSiorg);
        }
        // return
        return response;
    }

    @RequestMapping(value = "/protected/clazz-test/cgu-rest/ouvidorias", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    ResponseJson ouvidorias(HttpServletRequest request) {
        response.setStatus(false);
        // Client
        MeOUVRestClient meOUVRestClient = new MeOUVRestClient();
        OAuthToken oAuthToken = meOUVService.getOAuthToken();
        if (oAuthToken != null) {
            List<Ouvidoria> ouvidorias = meOUVRestClient.getOuvidoriasSomenteOuvidoriasAtivas(oAuthToken.getAccessToken());
            response.setStatus(true);
            response.setItem(ouvidorias);
        }
        // return
        return response;
    }

    @RequestMapping(value = "/protected/clazz-test/cgu-rest/consulta-manifestacao", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    ResponseJson consultaManifestacao(HttpServletRequest request) {
        response.setStatus(false);
        // Client
        MeOUVRestClient meOUVRestClient = new MeOUVRestClient();
        OAuthToken oAuthToken = meOUVService.getOAuthToken();
        if (oAuthToken != null) {
            DateTime now = new DateTime();
            String dataAtualizacaoInicio = now.minusDays(0).toString("dd/MM/yyyy");
            String dataAtualizacaoFim = now.minusDays(0).toString("dd/MM/yyyy");

            ManifestacaoConsultaRespostaDTO[] listaManifestacao = meOUVRestClient.getListaManifestacao(oAuthToken.getAccessToken(), 6, dataAtualizacaoInicio, dataAtualizacaoFim);
            response.setStatus(true);
            response.setItem(listaManifestacao);
        }
        // return
        return response;
    }

    @RequestMapping(value = "/protected/clazz-test/cgu-rest/detalha-manifestacao", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    ResponseJson detalhaManifestacao(HttpServletRequest request) {
        response.setStatus(false);
        // Client
        MeOUVRestClient meOUVRestClient = new MeOUVRestClient();
        OAuthToken oAuthToken = meOUVService.getOAuthToken();
        if (oAuthToken != null) {
            Integer idManifestacao = 26688;
            ManifestacaoDTO manifestacao = meOUVRestClient.getDetalhaManifestacao(oAuthToken.getAccessToken(), idManifestacao);
            response.setStatus(true);
            response.setItem(manifestacao);
        }
        // return
        return response;
    }
}
