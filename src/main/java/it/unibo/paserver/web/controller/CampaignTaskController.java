package it.unibo.paserver.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.domain.support.Pipeline;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Tela do cadastro de tarefas
 *
 * @author Claudio
 */
@Controller
public class CampaignTaskController extends ApplicationController {
    /**
     * Pagina inicial
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/campaign-task/index/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
    public ModelAndView index(@PathVariable("id") long id, ModelAndView modelAndView, HttpServletRequest request) {
        // Security
        isRoot(request);
        // Check
        isCheck(id);
        // View
        modelAndView.setViewName("/protected/campaign-task/index");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("id", id);
        modelAndView.addObject("isPublish", isPublish);
        modelAndView.addObject("isExpired", isExpired);
        modelAndView.addObject("isAssign", isAssign);
        modelAndView.addObject("isAdmin", isAdmin);
        modelAndView.addObject("isAgreement", isAgreement);
        modelAndView.addObject("hasQuestionnaire", hasQuestionnaire);
        //modelAndView.addObject("systemEmail",systemEmailService.findAll());
        // Has location
        modelAndView.addObject("hasLocation", hasAction(id, Pipeline.Type.LOCATION.toInt()));
        // Response
        return modelAndView;
    }

    /**
     * Sumario
     *
     * @param id
     * @param modelAndView
     * @param request
     * @return
     */
    @RequestMapping(value = "/protected/campaign-task/summary/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
    public ModelAndView summary(@PathVariable("id") long id, ModelAndView modelAndView, HttpServletRequest request) {
        // Security
        isRoot(request);
        // Check
        isCheck(id);
        // View
        modelAndView.setViewName("/protected/campaign-task/summary");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("id", id);
        modelAndView.addObject("isPublish", isPublish);
        modelAndView.addObject("isExpired", isExpired);
        modelAndView.addObject("isAssign", isAssign);
        modelAndView.addObject("isAdmin", isAdmin);
        modelAndView.addObject("hasQuestionnaire", hasQuestionnaire);
        // Has location
        modelAndView.addObject("hasLocation", hasAction(id, Pipeline.Type.LOCATION.toInt()));
        // Response
        return modelAndView;
    }

    /**
     * Removed um item
     *
     * @param id
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/protected/campaign-task/removed/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson removed(@PathVariable("id") long id, HttpServletRequest request)
            throws JsonProcessingException {
        // Security
        isRoot(request);
        // Response
        ResponseJson response = new ResponseJson();
        // Is photo or questionnario?
        Action action = actionService.findById(id);
        if (action != null) {
            Long taskId = actionService.whatIsMyTaskId(id);
            if (taskId > 0) {
                Task task = campaignService.findById(taskId);
                if (action instanceof ActionPhoto) {
                    task.setHasPhotos(false);
                    taskService.save(task);
                } else if (action instanceof ActionQuestionaire) {
                    if (actionService.getCountQuestionaire(taskId) <= 1) {
                        task.setHasQuestionnaire(false);
                        taskService.save(task);
                    }
                }
            }
        }
        // Removed
        try {
            boolean removed = actionService.deleteAction(id);
            response.setStatus(removed);
            response.setMessage((removed)
                    ? messageSource.getMessage("confirmation.remove.success", null, LocaleContextHolder.getLocale())
                    : messageSource.getMessage("confirmation.remove.fail", null, LocaleContextHolder.getLocale()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setMessage(e.getMessage());
        }
        // Return
        return response;
    }
}
