package it.unibo.paserver.web.controller;

import br.com.bergmannsoft.utils.PaginationUtil;
import br.gov.cgu.eouv.domain.CguIbgeMun;
import br.gov.cgu.eouv.service.CguIbgeMunService;
import com.fasterxml.jackson.core.JsonProcessingException;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 10/04/2019
 **/

@SuppressWarnings("Duplicates")
@Controller
public class CguIbgeMunController extends ApplicationController {
    @Autowired
    private CguIbgeMunService cguIbgeMunService;

    // ######################   ######################
    // ######################   Default
    // ######################   ######################

    /**
     * Pagina inicial
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/cgu-ibge-mun/index", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/cgu-ibge-mun/index");
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
    @RequestMapping(value = "/protected/cgu-ibge-mun/form", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView form(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/cgu-ibge-mun/form");
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
    @RequestMapping(value = "/protected/cgu-ibge-mun/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView edit(@PathVariable("id") long id, ModelAndView modelAndView) {
        // Model view
        modelAndView.setViewName("/protected/cgu-ibge-mun/form");
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
    @RequestMapping(value = "/protected/cgu-ibge-mun/edit/{id}/find", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    ResponseJson find(@PathVariable("id") long id) throws JsonProcessingException {
        // Response
        ResponseJson response = new ResponseJson();
        //Return
        return response;
    }

    /**
     * Busca customizada
     *
     * @param json
     * @param count
     * @param offset
     * @return
     */
    @RequestMapping(value = "/protected/cgu-ibge-mun/search/{count}/{offset}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    ResponseJson search(@RequestBody String json, @PathVariable int count, @PathVariable int offset) {
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(true);
        response.setCount(count);
        response.setOffset(offset);
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Search
        String search = r.getAsString("search");
        List<CguIbgeMun> rs = cguIbgeMunService.filter( search, PaginationUtil.pagerequest(offset, count));
        if (rs != null && rs.size() > 0) {
            response.setItem(rs);
            response.setTotal(cguIbgeMunService.searchTotal(search));
        }
        // return
        return response;
    }
}
