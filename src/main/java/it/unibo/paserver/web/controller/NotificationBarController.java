package it.unibo.paserver.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.bergmannsoft.utils.PaginationUtil;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.service.NotificationBarService;
import it.unibo.paserver.web.security.v1.AccountAdminDetails;
/**
 * Controller para notificacoes na central
 * @author Claudio
 */
@Controller
public class NotificationBarController {
	@Autowired
	private NotificationBarService notificationBarService;
	
	@RequestMapping(value = "/protected/notification-bar/unread-all", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public @ResponseBody ResponseJson unreadAll(){
		//Parent
		AccountAdminDetails current = (AccountAdminDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long parentId = current.getId();
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(notificationBarService.unreadAll(parentId));
		return response;		
	}
	/**
	 * Busca customizada
	 * 
	 * @param search
	 * @param count
	 * @param offset
	 * @return
	 */
	@RequestMapping(value = "/protected/notification-bar/search/{count}/{offset}", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public @ResponseBody ResponseJson search(@RequestBody String json, @PathVariable int count, @PathVariable int offset) {
		//Parent
		AccountAdminDetails current = (AccountAdminDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long parentId = current.getId();
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(true);
		response.setCount(count);
		response.setOffset(offset);
		// Request
		List<Object[]> items = notificationBarService.search(parentId, PaginationUtil.pagerequest(offset, count));
		response.setItems(items);
		if (items.size() > 0) {
			response.setTotal(notificationBarService.searchTotal(parentId));
		}
		return response;
	}
}
