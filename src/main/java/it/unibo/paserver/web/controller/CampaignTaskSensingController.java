package it.unibo.paserver.web.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.EnumUtils;
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
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.Pipeline.Type;

/**
 * Controller das tarefas com sensores
 * 
 * @author Claudio
 */
@Controller
public class CampaignTaskSensingController extends ApplicationController{
	/**
	 * Formulario
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/campaign-task-sensing/form/{campaign_id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public ModelAndView form(@PathVariable("campaign_id") long campaign_id, ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/campaign-task-sensing/form");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		modelAndView.addObject("campaign_id", campaign_id);
		modelAndView.addObject("hasSensors", actionService.fetchAll(campaign_id));
		modelAndView.addObject("sensorDuration", campaignService.findById(campaign_id).getSensingDuration());
		
		modelAndView.addObject("sensorWeekSun", campaignService.findById(campaign_id).getSensingWeekSun());	
		modelAndView.addObject("sensorWeekMon", campaignService.findById(campaign_id).getSensingWeekMon());	
		modelAndView.addObject("sensorWeekTue", campaignService.findById(campaign_id).getSensingWeekTue());	
		modelAndView.addObject("sensorWeekWed", campaignService.findById(campaign_id).getSensingWeekWed());	
		modelAndView.addObject("sensorWeekThu", campaignService.findById(campaign_id).getSensingWeekThu());	
		modelAndView.addObject("sensorWeekFri", campaignService.findById(campaign_id).getSensingWeekFri());	
		modelAndView.addObject("sensorWeekSat", campaignService.findById(campaign_id).getSensingWeekSat());	
		//Return
		return modelAndView;
	}

	/**
	 * Salva/Atualiza um item
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = { "/protected/campaign-task-sensing/save/" }, method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public @ResponseBody ResponseJson save(@RequestBody String json,HttpServletRequest request) {
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
			String pipelineType = r.getAsString("pipelineType");
			Long sensorDuration = Useful.convertStringToLong(r.getAsString("sensorDuration"));
			boolean sensorWeekSun = r.getAsBoolean("sensorWeekSun");
			boolean sensorWeekMon = r.getAsBoolean("sensorWeekMon");
			boolean sensorWeekTue = r.getAsBoolean("sensorWeekTue");
			boolean sensorWeekWed = r.getAsBoolean("sensorWeekWed");
			boolean sensorWeekThu = r.getAsBoolean("sensorWeekThu");
			boolean sensorWeekFri = r.getAsBoolean("sensorWeekFri");
			boolean sensorWeekSat = r.getAsBoolean("sensorWeekSat");			
			// Campaign
			Task task = campaignService.findById(campaign_id);
			if (task != null) {
				Type enumPipelineType = EnumUtils.isValidEnum(Pipeline.Type.class, pipelineType)? Pipeline.Type.valueOf(pipelineType) : Pipeline.Type.DUMMY;
				ActionSensing a = new ActionSensing();
				a.setName(description);
				a.setInput_type(enumPipelineType.toInt());
				a.setSensorDuration(sensorDuration);
				a.setSensorWeekDay(sensorWeekSun, sensorWeekMon, sensorWeekTue, sensorWeekWed, sensorWeekThu, sensorWeekFri, sensorWeekSat);
				Action rs = actionService.save(a);
				if (rs != null) {
					// Actions
					Set<Action> as = task.getActions();
					as.add(a);
					// Add Actions
					task.setActions(as);
					taskService.save(task);
					// StatuS
					response.setStatus(true);
				}
			}
		}
		// Result
		return response;
	}
}
