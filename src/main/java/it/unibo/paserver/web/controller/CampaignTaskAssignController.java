package it.unibo.paserver.web.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import br.com.bergmannsoft.utils.Useful;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.service.DevicesService;
import it.unibo.paserver.service.InstitutionsService;
import it.unibo.paserver.service.ParticipantService;
import it.unibo.paserver.service.SchoolCourseService;
import it.unibo.paserver.service.TaskPublishService;

/**
 * Controller dos usuarios vinculados
 * 
 * @author Claudio
 */
@Controller
public class CampaignTaskAssignController extends ApplicationController {
	@Autowired
	private TaskPublishService taskPublishService;
	@Autowired
	private ParticipantService participantService;
	@Autowired
	private DevicesService devicesService;
	@Autowired
	private InstitutionsService institutionsService;
	@Autowired
	private SchoolCourseService schoolCourseService;

	/**
	 * Formulario
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/campaign-task-assign/form/{campaign_id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public ModelAndView form(@PathVariable("campaign_id") long campaign_id, ModelAndView modelAndView, HttpServletRequest request) {
		// Security
		isRoot(request);
		// Check
		isCheck(campaign_id);
		// Vars
		modelAndView.setViewName("/protected/campaign-task-assign/form");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		// Infos
		String campaign_name = null;
		long assign_available = 0L;
		Boolean campaign_canBeRefused = false;
		Task t = campaignService.findById(campaign_id);
		if (t != null) {
			campaign_name = t.getName();
			campaign_canBeRefused = t.getCanBeRefused();
			assign_available = participantService.getUserCount();
		}
		modelAndView.addObject("campaign_id", campaign_id);
		modelAndView.addObject("campaign_name", campaign_name);
		modelAndView.addObject("campaign_canBeRefused", campaign_canBeRefused);
		modelAndView.addObject("assign_available", assign_available);
		// Lists
		modelAndView.addObject("devices", devicesService.findAll());
		modelAndView.addObject("institutions", institutionsService.findAll());
		modelAndView.addObject("courses", schoolCourseService.findAll());
		// Is
		modelAndView.addObject("isPublish", isPublish);
		modelAndView.addObject("isExpired", isExpired);
		// View
		return modelAndView;
	}

	/**
	 * Salva/Atualiza um item
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = { "/protected/campaign-task-assign/save/" }, method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public @ResponseBody ResponseJson save(@RequestBody String json) {
		// Response
		ResponseJson response = new ResponseJson();
		response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
		// Request
		ReceiveJson r = new ReceiveJson(json);
		// Values
		long campaign_id = Useful.convertStringToLong(r.getAsString("campaign_id"));
		boolean isSelectAll = r.getAsBoolean("isSelectAll");
		long assignAvailable = Useful.convertStringToLong(r.getAsString("assign_available"));
		long assignSelected = isSelectAll ? assignAvailable : Useful.convertStringToLong(r.getAsString("assign_selected"));
		JsonArray assign_filter = r.getAsJsonArray("assign_filter");
		if (assign_filter.isJsonArray()) {
			Gson gson = new Gson();
			String assignFilter = gson.toJson(assign_filter);

			// System.out.println(assignFilter.toString());
			// Campaign
			Task task = campaignService.findById(campaign_id);
			if (task != null) {
				if (!task.isPublish()) {
					task.setAssignAvailable(assignAvailable);
					task.setAssignSelected(assignSelected);
					task.setAssignFilter(assignFilter);
					task.setSelectAll(isSelectAll);
					task.setAssign(true);
					if (taskService.save(task) != null) {
						response.setStatus(true);
					}
				} else if (!task.isExpired()) {
					task.setAssignSelected(assignSelected);
					task.setAssignFilter(assignFilter);
					task.setSelectAll(isSelectAll);
					if (taskService.save(task) != null) {
						response.setStatus(true);
						taskPublishService.publish(task, false);
					}
				}

				// System.out.println(task.isExpired());
			} else {
				System.out.println("CampaignTaskAssignController NO TASKS");
			}

		}
		// Result
		return response;
	}

	/**
	 * Edicao de item
	 * 
	 * @param modelAndView
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/protected/campaign-task-assign/find/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public @ResponseBody ResponseJson find(@PathVariable("id") long id) throws JsonProcessingException {
		// Response
		ResponseJson response = new ResponseJson();
		// Search
		Task task = campaignService.findById(id);
		if (task != null && task.isAssign()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("assign_available", task.getAssignAvailable());
			map.put("assign_selected", task.getAssignSelected());
			map.put("campaign_id", id);

			boolean isSelectAll = false;
			String assign_filter = "[]";
			if (!task.isPublish()) {
				isSelectAll = task.isSelectAll();
				assign_filter = task.getAssignFilter();
			}
			map.put("isSelectAll", isSelectAll);
			map.put("assign_filter", assign_filter);
			response.setItem(map);
			response.setStatus(true);
		}
		return response;
	}

	/**
	 * Remove um item
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/protected/campaign-task-assign/removed/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
	public @ResponseBody ResponseJson removed(@PathVariable("id") long id) throws JsonProcessingException {
		// Response
		ResponseJson response = new ResponseJson();
		// Search
		Task task = campaignService.findById(id);
		if (task != null && task.isAssign()) {
			task.setAssignAvailable(0L);
			task.setAssignSelected(0L);
			task.setSelectAll(false);
			task.setAssign(false);
			if (taskService.save(task) != null) {
				response.setMessage(messageSource.getMessage("confirmation.remove.success", null, LocaleContextHolder.getLocale()));
				response.setStatus(true);
			}
		}
		return response;
	}
}
