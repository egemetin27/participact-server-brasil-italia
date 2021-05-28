package it.unibo.paserver.rest.controller.v1;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import br.com.bergmannsoft.utils.Useful;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.ResponseMessage;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.UserMessageService;
import it.unibo.paserver.service.UserService;

/**
 * Mensagens enviadas dos push
 * 
 * @author Claudio
 */
@Controller
public class UserMessageRestController {
	@Autowired
	private UserMessageService userMessageService;
	@Autowired
	private UserService userService;

	/**
	 * Retorna as mensagens de um participante/usuario
	 * 
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/me/messages", method = RequestMethod.GET)
	public @ResponseBody ResponseJson getUnreadMessages(Principal principal) {
		// Response
		ResponseJson response = new ResponseJson();
		// Messages
		try {
			User user = userService.getUser(principal.getName());
			if (user != null) {
				// Messagens
				List<Object[]> items = userMessageService.fetchAllUnread(user.getId());
				if (items.size() > 0) {
					// Status
					response.setStatus(true);
					response.setItems(items);
					response.setTotal(items.size());
					//Update
					userMessageService.readByUserId(user.getId());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
			response.setMessage(Useful.replaceNull(e.getMessage()));
		}
		return response;
	}
}
