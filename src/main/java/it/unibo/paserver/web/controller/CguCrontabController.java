package it.unibo.paserver.web.controller;

import br.com.bergmannsoft.utils.Validator;
import br.gov.cgu.eouv.domain.CguCrontab;
import br.gov.cgu.eouv.service.CguCrontabService;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.ResultType;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@SuppressWarnings("Duplicates")
@Controller
public class CguCrontabController extends ApplicationController {
    @Autowired
    private CguCrontabService cguCrontabService;
    private CguCrontab crontab;

    /**
     * Pagina inicial
     *
     * @param modelAndView
     * @return
     */
    @SuppressWarnings("JavaDoc")
    @RequestMapping(value = "/protected/cgu-crontab/index", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/cgu-crontab/index");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        // Crontab
        crontab = cguCrontabService.first();
        modelAndView.addObject("hh", crontab != null ? crontab.getHh() : 0);
        // View
        return modelAndView;
    }

    @RequestMapping(value = "/protected/cgu-crontab/submit", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    ResponseJson submit(@RequestBody String json) {
        // Response
        ResponseJson response = new ResponseJson();
        // Request
        ReceiveJson r = new ReceiveJson(json);
        Integer hh = r.getAsInt("hh");
        if (hh == null || hh <= 0) {
            response.setResultType(ResultType.TYPE_ERROR);
            response.setMessage(messageSource.getMessage("interval.time.title", null, LocaleContextHolder.getLocale()));
        } else {
            if (crontab == null) {
                crontab = new CguCrontab();
            }
            crontab.setHh(hh);
            crontab.setDeliverydate(new DateTime().plusHours(hh));
            CguCrontab rs = cguCrontabService.saveOrUpdate(crontab);
            if (rs != null) {
                response.setResultType(ResultType.TYPE_SUCCESS);
                response.setMessage(messageSource.getMessage("updated.title", null, LocaleContextHolder.getLocale()));
            }
        }
        // return
        return response;
    }
}
