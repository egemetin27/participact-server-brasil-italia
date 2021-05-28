package it.unibo.paserver.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.DataLocation;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.service.DataService;

@Controller
public class CampaignTaskRoutes extends ApplicationController {
	@Autowired
	DataService dataService;
	/**
	 * Pagina inicial
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/campaign-task-routes/index/{taskId}/{userId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public ModelAndView index(@PathVariable("taskId") long taskId, @PathVariable("userId") long userId, ModelAndView modelAndView) {
		//Vars
		modelAndView.setViewName("/protected/campaign-task-routes/index");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		modelAndView.addObject("taskId", taskId);
		modelAndView.addObject("userId", userId);
		//View
		return modelAndView;
	}
	
	/**
	 * Pagina inicial
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/campaign-task-routes/directions/{taskId}/{userId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public @ResponseBody ResponseJson getDirections(@PathVariable("taskId") long taskId, @PathVariable("userId") long userId) {
		// Response
		ResponseJson response = new ResponseJson();
		//Hashmap
		ListMultimap<String, Object> params = ArrayListMultimap.create();					
		//Task
		Task task = campaignService.findById(taskId);
		if(task != null){
			List<DataLocation> items = dataService.search(DataLocation.class,task.getStart(), task.getDeadline(), userId, params, PaginationUtil.pagerequest(Config.SELECT_MIN_OFFSET, Config.SELECT_MAX_COUNT));
			if(items.size() > 0){
				response.setStatus(true);
				response.setTotal(items.size());
				response.setItems(Useful.getDataToObject(items, DataLocation.class.getName()));
				response.setItem(Useful.getRandomRGB());
			}
		}
		//Return
		return response;
	}	
}
