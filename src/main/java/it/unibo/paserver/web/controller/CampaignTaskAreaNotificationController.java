package it.unibo.paserver.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller das areas de notificacao
 * 
 * @author Claudio
 */
@Controller
public class CampaignTaskAreaNotificationController {
	private boolean isNotification = true;
	private boolean isActivation = false;
	/**
	 * Formulario
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/campaign-task-area-notification/form/{campaign_id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public ModelAndView form(@PathVariable("campaign_id") long campaign_id, ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/campaign-task-area-notification/form");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		modelAndView.addObject("campaign_id", campaign_id);
		modelAndView.addObject("isNotification", isNotification);
		modelAndView.addObject("isActivation", isActivation);		
		return modelAndView;
	}
}
