package it.unibo.paserver.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import br.com.bergmannsoft.utils.PaginationUtil;
import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.service.PushNotificationsLogsService;
import it.unibo.paserver.service.PushNotificationsService;

/**
 * Logs dos envios de uma acao de push
 * 
 * @author Claudio
 */
@Controller
public class PushNotificationsLogsController extends ApplicationController {
	@Autowired
	private PushNotificationsLogsService pushNotificationsLogsService;
	@Autowired
	private PushNotificationsService pushNotificationsService;
	/**
	 * Pagina Inicial
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/push-notifications-logs/index/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public ModelAndView home(ModelAndView modelAndView, @PathVariable("id") long id) {
		modelAndView.setViewName("/protected/push-notifications-logs/index");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("id", id);
		String msg = null;
		PushNotifications ps = pushNotificationsService.findById(id);
		if(ps != null) {
			msg = ps.getMessage();
		}
		modelAndView.addObject("pushMessage", msg);
		return modelAndView;
	}

	/**
	 * Busca customizada
	 * 
	 * @param search,
	 * @param count
	 * @param offset
	 * @return
	 */
	@RequestMapping(value = "/protected/push-notifications-logs/search/{count}/{offset}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public @ResponseBody ResponseJson search(@RequestBody String json, @PathVariable int count, @PathVariable int offset) {
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(true);
		response.setCount(count);
		response.setOffset(offset);
		ListMultimap<String, Object> params = ArrayListMultimap.create();
		//Params
		ReceiveJson r = new ReceiveJson(json);
		Long pushNotificationsId = r.getAsLong("pushNotificationsId");
		if(pushNotificationsId>0) {
			PushNotifications push = new PushNotifications();
			push.setId(pushNotificationsId);
			params.put("pushnotificationsId", pushNotificationsId);
		}
		// Request
		List<Object[]> items = pushNotificationsLogsService.filter(params, PaginationUtil.pagerequest(offset, count));
		if (items.size() > 0) {
			// Total		
			response.setTotal(pushNotificationsLogsService.filterTotal(params));
		}
		response.setItems(items);
		return response;
	}
}