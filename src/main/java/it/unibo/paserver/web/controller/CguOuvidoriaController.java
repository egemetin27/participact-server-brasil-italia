package it.unibo.paserver.web.controller;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import br.gov.cgu.eouv.domain.CguIbgeMun;
import br.gov.cgu.eouv.domain.OAuthToken;
import br.gov.cgu.eouv.domain.Ouvidoria;
import br.gov.cgu.eouv.service.*;
import br.gov.cgu.eouv.ws.rest.MeOUVRestClient;
import br.gov.cgu.eouv.ws.soap.ServicoOuvidoriasClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.service.IssueCategoryService;
import it.unibo.paserver.service.IssueSubCategoryHasRelationshipService;
import it.unibo.paserver.service.IssueSubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 17/07/2019
 **/
@SuppressWarnings("Duplicates")
@Controller
public class CguOuvidoriaController extends ApplicationController {
    @Autowired
    OrgaoSiorgOuvidoriaService orgaoSiorgOuvidoriaService;
    @Autowired
    OuvidoriaService ouvidoriaService;
    @Autowired
    ManifestacaoService manifestacaoService;
    @Autowired
    private IssueCategoryService issueCategoryService;
    @Autowired
    private IssueSubCategoryHasRelationshipService issueSubCategoryHasRelationshipService;
    @Autowired
    private MeOUVService meOUVService;
    @Autowired
    private CguIbgeMunService cguIbgeMunService;

    /**
     * @param id
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/cgu-ouvidoria/form/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView form(@PathVariable("id") long id, ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/cgu-ouvidoria/form");
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("form", id);
        // return
        return modelAndView;
    }

    /**
     * Edicao de um item
     *
     * @param id
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/protected/cgu-ouvidoria/edit/{id}/find", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson find(@PathVariable("id") long id, HttpServletRequest request) throws JsonProcessingException {
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(false);
        // Search
        Ouvidoria ouv = ouvidoriaService.find(id);
        if (ouv != null) {
            HashMap<String, Object> item = new HashMap<>();
            // Ouv
            item.put("idOuvidoria", ouv.getIdOuvidoria());
            item.put("hasAllowOmbudsman", ouv.getHasAllowOmbudsman());
            item.put("nomeOrgaoOuvidoria", ouv.getNomeOrgaoOuvidoria());
            item.put("descEsfera", ouv.getDescEsfera());
            item.put("subAssuntosOuvidoria", ouv.getSubAssuntosOuvidoria());
            // Mun
            CguIbgeMun mun = cguIbgeMunService.findByCodMun6(ouv.getIdMunicipio());
            if (mun != null) {
                item.put("nomemun", mun.getNomemun());
                item.put("nomeuf", mun.getNomeuf());
                item.put("geoLat", mun.getGeoLat());
                item.put("geoLong", mun.getGeoLong());
            }
            // Categorias
            List<IssueCategory> listCategory = issueCategoryService.findAll();
            HashMap<Long, IssueCategory> hCategory = new HashMap<>();
            if (listCategory != null && listCategory.size() > 0) {
                for (IssueCategory ic : listCategory) {
                    //Sub Categorias
                    List<HashMap<String, Object>> map = new ArrayList<HashMap<String, Object>>();
                    List<IssueSubCategory> subcategories = ic.getSubcategories();
                    if (subcategories != null && subcategories.size() > 0) {
                        for (IssueSubCategory is_item : subcategories) {
                            HashMap<String, Object> value = new HashMap<String, Object>();
                            if (!is_item.isRemoved()) {
                                //Map
                                value.put("id", is_item.getId());
                                value.put("urlAsset", is_item.getUrlAsset());
                                value.put("name", is_item.getName());
                                value.put("sequence", is_item.getSequence());
                                value.put("hasAllowOmbudsman", issueSubCategoryHasRelationshipService.ItContains(is_item.getId(), id));
                                map.add(value);
                            }
                        }
                    }
                    ic.setMap(map);
                    hCategory.put(ic.getId(), ic);
                }
            }
            item.put("categories", hCategory);
            // Res
            response.setStatus(true);
            response.setItem(item);
        }
        // Return
        return response;
    }

    // ######################   ######################
    // ######################   GetOuvidorias
    // ######################   ######################
    @RequestMapping(value = "/protected/cgu-ouvidoria/index", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/cgu-ouvidoria/index");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        System.out.print(modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        return modelAndView;
    }

    /**
     * Busca customizada
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/protected/cgu-ouvidoria/get-ouvidorias", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')")
    public @ResponseBody
    ResponseJson getOuvidorias(@RequestBody String json, HttpServletRequest request) {
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
        List<Ouvidoria> ouvidorias = new ArrayList<>();
        if (isSync && isAdmin) {
            if (protocol.equals(Config.CGU_PROTOCOL_REST)) {
                // Client
                MeOUVRestClient meOUVRestClient = new MeOUVRestClient();
                OAuthToken oAuthToken = meOUVService.getOAuthToken();
                if (oAuthToken != null) {
                    ouvidorias = meOUVRestClient.getOuvidoriasSomenteOuvidoriasAtivas(oAuthToken.getAccessToken());
                }
            } else {
                ServicoOuvidoriasClient client = new ServicoOuvidoriasClient();
                ouvidorias = client.getOuvidorias();
            }

            if (ouvidorias.size() > 0) {
                ouvidoriaService.removeAll();
                if (Useful.uSleep(100)) {
                    for (Ouvidoria o : ouvidorias) {
                        o.setRemoved(false);
                        Ouvidoria old = ouvidoriaService.find(o.getIdOuvidoria());
                        if (old != null) {
                            o.setHasAllowOmbudsman(old.getHasAllowOmbudsman());
                        }
                        ouvidoriaService.saveOrUpdate(o);
                    }
                }
            }
        } else {
            if (!Validator.isEmptyString(search)) {
                ouvidorias = ouvidoriaService.filter(search);
            } else {
                ouvidorias = ouvidoriaService.fetchAll();
            }
        }
//        //Result
        if (ouvidorias.size() > 0) {
            response.setStatus(true);
            List<HashMap<String, Object>> res = new ArrayList<>();
            for (Ouvidoria ouv : ouvidorias) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("idOuvidoria", ouv.getIdOuvidoria());
                map.put("dataAdesaoEOuvPadrao", ouv.getDataAdesaoEOuvPadrao());
                map.put("descEsfera", ouv.getDescEsfera());
                map.put("descMunicipio", ouv.getDescMunicipio());
                map.put("descUf", ouv.getDescUf());
                map.put("idEsfera", ouv.getIdEsfera());
                map.put("idMunicipio", ouv.getIdMunicipio());
                map.put("idOuvidoria", ouv.getIdOuvidoria());
                map.put("nomeOrgaoOuvidoria", ouv.getNomeOrgaoOuvidoria());
                map.put("sigUf", ouv.getSigUf());
                map.put("removed", ouv.isRemoved());
                map.put("subAssuntosOuvidoria", ouv.getSubAssuntosOuvidoria());
                map.put("hasAllowOmbudsman", ouv.getHasAllowOmbudsman());
                //Categorias
                List<String> categorias = new ArrayList<>();
                //Relacoes
                if (ouv.getIdOuvidoria() == 449) {
                    List<Object[]> rels = issueSubCategoryHasRelationshipService.getListRelationSubCategory(ouv.getIdOuvidoria());
                    if (rels != null && rels.size() > 0) {
                        for (Object[] rel : rels) {
                            String nomeOuvidoria = rel[2].toString();
                            //Add
                            categorias.add(nomeOuvidoria);
                        }
                    }
                }
                map.put("categorias", categorias);
                //Add
                res.add(map);
            }
            response.setItem(res);
        }
        // return
        return response;
    }


    @RequestMapping(value = "/protected/cgu-ouvidoria/get-lista-manifestacao-ouvidoria", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    ResponseJson getListaManifestacaoOuvidoria(@RequestBody String json) {
        // Response
        ResponseJson response = new ResponseJson();
        response.setItems(new ArrayList<Object[]>());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        try {
            //Json
            Long issueId = r.getAsLong("issueId");
            response.setOutcome(issueId);
            //Return
            response.setStatus(manifestacaoService.getListaManifestacaoOuvidoria(null, issueId));
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println(String.format("getListaManifestacaoOuvidoria %s ", e.getMessage()));
        }
        // return
        return response;
    }

    @RequestMapping(value = "/protected/cgu-ouvidoria/toggle-ombudsman/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public @ResponseBody
    ResponseJson toggleOmbudsman(@PathVariable("id") long id, HttpServletRequest request) throws JsonProcessingException {
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(false);
        // Search
        Ouvidoria ouv = ouvidoriaService.find(id);
        if (ouv != null) {
            boolean value = !ouv.getHasAllowOmbudsman();
            ouv.setHasAllowOmbudsman(value);
            if (ouvidoriaService.saveOrUpdate(ouv) != null) {
                // Res
                response.setStatus(true);
                response.setItem(value);
            }
        }
        // Return
        return response;
    }

    @RequestMapping(value = "/protected/cgu-ouvidoria/toggle-subcategory/{id}/{sid}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson toggleSubcategory(@PathVariable("id") long id, @PathVariable("sid") long sid, HttpServletRequest request) throws JsonProcessingException {
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(false);
        // Search
        if (issueSubCategoryHasRelationshipService.ItContains(sid, id)) {
            if (issueSubCategoryHasRelationshipService.deleteByIds(id, sid)) {
                response.setItem(false);
                response.setStatus(true);
            }
        } else {
            IssueSubCategoryHasRelationship rel = new IssueSubCategoryHasRelationship();
            rel.setIssueSubcategoryId(sid);
            rel.setRelationshipId(id);
            //Save
            IssueSubCategoryHasRelationship i = issueSubCategoryHasRelationshipService.save(rel);
            if (i != null) {
                response.setItem(true);
                response.setStatus(true);
            }
        }
        // Return
        return response;
    }
}
