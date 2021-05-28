package it.unibo.paserver.web.controller;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import br.gov.cgu.eouv.domain.OAuthToken;
import br.gov.cgu.eouv.domain.OrgaoSiorgOuvidoria;
import br.gov.cgu.eouv.domain.Ouvidoria;
import br.gov.cgu.eouv.service.ManifestacaoService;
import br.gov.cgu.eouv.service.MeOUVService;
import br.gov.cgu.eouv.service.OrgaoSiorgOuvidoriaService;
import br.gov.cgu.eouv.service.OuvidoriaService;
import br.gov.cgu.eouv.ws.rest.MeOUVRestClient;
import br.gov.cgu.eouv.ws.soap.ServicoConsultaDadosCodigosClient;
import br.gov.cgu.eouv.ws.soap.ServicoOuvidoriasClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/04/2019
 **/

@SuppressWarnings("Duplicates")
@Controller
public class CguEouvController extends ApplicationController {
    @Autowired
    OrgaoSiorgOuvidoriaService orgaoSiorgOuvidoriaService;
    @Autowired
    OuvidoriaService ouvidoriaService;
    @Autowired
    ManifestacaoService manifestacaoService;
    @Autowired
    private MeOUVService meOUVService;

    // ######################   ######################
    // ######################   Default
    // ######################   ######################

    /**
     * Pagina inicial
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/cgu-eouv/index", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/cgu-eouv/index");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        return modelAndView;
    }

    /**
     * Formulario
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/cgu-eouv/form", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView form(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/cgu-eouv/form");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        return modelAndView;
    }

    /**
     * Edicao de item
     *
     * @param id
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/cgu-eouv/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView edit(@PathVariable("id") long id, ModelAndView modelAndView) {
        // Model view
        modelAndView.setViewName("/protected/cgu-eouv/form");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("form", id);
        return modelAndView;
    }

    /**
     * Edicao de item
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/protected/cgu-eouv/edit/{id}/find", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    ResponseJson find(@PathVariable("id") long id) throws JsonProcessingException {
        // Response
        ResponseJson response = new ResponseJson();
        //Return
        return response;
    }

    // ######################   ######################
    // ######################   GetListaOrgaosSiorg
    // ######################   ######################
    @RequestMapping(value = "/protected/cgu-eouv/orgaos-siorg-ouvidoria", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getOrgaosSiorgOuvidoria(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/cgu-eouv/orgaos-siorg-ouvidoria");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        System.out.print(modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        return modelAndView;
    }

    /**
     * Busca customizada
     *
     * @param json
     * @param count
     * @param offset
     * @return
     */
    @RequestMapping(value = "/protected/cgu-eouv/search/{count}/{offset}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    ResponseJson search(@RequestBody String json, @PathVariable int count, @PathVariable int offset) {
        /**
         * TESTE
         */
        // Response
        ResponseJson response = new ResponseJson();
//        response.setStatus(true);
//        response.setCount(count);
//        response.setOffset(offset);
//        // Request
//        ReceiveJson r = new ReceiveJson(json);
//        // Test
//        ServicoConsultaDadosCodigosClient client = new ServicoConsultaDadosCodigosClient();
//        client.getListaOrgaosSiorg();
        // return
        return response;
    }

    /**
     * Busca customizada
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/protected/cgu-eouv/get-lista-orgos-siorg", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_CONSULTANT', 'ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')")
    public @ResponseBody
    ResponseJson getListaOrgaosSiorg(@RequestBody String json, HttpServletRequest request) {
        // Root
        isRoot(request);
        // Response
        ResponseJson response = new ResponseJson();
        response.setCount(Config.SELECT_MAX_COUNT);
        response.setOffset(Config.SELECT_MIN_OFFSET);
        // Request
        ReceiveJson r = new ReceiveJson(json);
        boolean isSync = r.getAsBoolean("isSync");
        String search = r.getAsString("search");
        String protocol = r.getAsString("protocol");
        //Loading
        List<OrgaoSiorgOuvidoria> orgaosSiorg = new ArrayList<>();
        if (isSync && isAdmin) {
            if (protocol.equals(Config.CGU_PROTOCOL_REST)) {
                // Client
                MeOUVRestClient meOUVRestClient = new MeOUVRestClient();
                OAuthToken oAuthToken = meOUVService.getOAuthToken();
                if (oAuthToken != null) {
                    orgaosSiorg = meOUVRestClient.getOrgaosSiorg(oAuthToken.getAccessToken());
                }
            } else {
                ServicoConsultaDadosCodigosClient client = new ServicoConsultaDadosCodigosClient();
                orgaosSiorg = client.getListaOrgaosSiorg();
            }
            if (orgaosSiorg.size() > 0) {
                for (OrgaoSiorgOuvidoria o : orgaosSiorg) {
                    orgaoSiorgOuvidoriaService.saveOrUpdate(o);
                }
            }
        } else {
            if (!Validator.isEmptyString(search)) {
                orgaosSiorg = orgaoSiorgOuvidoriaService.filter(search);
            } else {
                orgaosSiorg = orgaoSiorgOuvidoriaService.fetchAll();
            }
        }
        //Result
        if (orgaosSiorg.size() > 0) {
            response.setStatus(true);
            response.setItem(orgaosSiorg);
        }
        // return
        return response;
    }
}
