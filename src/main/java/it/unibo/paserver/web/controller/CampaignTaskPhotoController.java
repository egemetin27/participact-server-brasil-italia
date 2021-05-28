package it.unibo.paserver.web.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.bergmannsoft.utils.Useful;
import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionPhoto;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.Task;

/**
 * Controller das tarefas com fotos
 * 
 * @author Claudio
 */
@Controller
public class CampaignTaskPhotoController extends ApplicationController {
	/**
	 * Formulario
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/campaign-task-photo/form/{campaign_id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public ModelAndView form(@PathVariable("campaign_id") long campaign_id, ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/campaign-task-photo/form");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		modelAndView.addObject("campaign_id", campaign_id);
		return modelAndView;
	}

	/**
	 * Salva/Atualiza um item
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = { "/protected/campaign-task-photo/save/" }, method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public @ResponseBody ResponseJson save(@RequestBody String json, HttpServletRequest request) {
		// Response
		ResponseJson response = new ResponseJson();
		response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
		// Request
		ReceiveJson r = new ReceiveJson(json);
		// Values
		long campaign_id = Useful.convertStringToLong(r.getAsString("campaign_id"));
		// Check
		isCheck(campaign_id);
		// Security
		isRoot(request);
		//Validate
		if (!isPublish||isAdmin) {
			String description = r.getAsString("description");
			int numericThreshold = r.getAsInt("numericThreshold");
			if (numericThreshold < 1 || numericThreshold > 10) {
				response.setMessage(messageSource.getMessage("error.task.numeric.threshold", null, LocaleContextHolder.getLocale()));
			} else {
				// Campaign
				Task task = campaignService.findById(campaign_id);
				if (task != null) {
					ActionPhoto a = new ActionPhoto();
					a.setName(description);
					a.setNumeric_threshold(numericThreshold);
					Action rs = actionService.save(a);
					if (rs != null) {
						// Actions
						Set<Action> as = task.getActions();
						as.add(a);
						// Add Actions
						task.setActions(as);
						task.setHasPhotos(true);
						taskService.save(task);
						// status
						response.setStatus(true);
					}
				}
			}
		}
		// Result
		return response;
	}
}
