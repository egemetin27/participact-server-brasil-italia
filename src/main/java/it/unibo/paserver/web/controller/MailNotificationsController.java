package it.unibo.paserver.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.unibo.participact.domain.PANotification;
import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.domain.support.PushNotificationsBuilder;
import it.unibo.paserver.service.PushNotificationsService;

/**
 * Envio de Emails
 * 
 * @author Claudio
 */
@Controller
public class MailNotificationsController extends ApplicationController {
	@Autowired
	private PushNotificationsService pushNotificationsService;

	/**
	 * Pagina Inicial
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/mail-notifications/index", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public ModelAndView home(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/push-notifications/index");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("isMail", true);
		return modelAndView;
	}

	/**
	 * Novo Item
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/mail-notifications/new", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST')")
	public ModelAndView newPush(ModelAndView modelAndView) {
		//Default
		modelAndView.addObject("isMail", true);
		// View
		Long pushId = 0L;
		PANotification.Type enumPaNotification = PANotification.Type.ONLY_EMAIL;
		PushNotificationsBuilder pb = new PushNotificationsBuilder();
		pb.setAll(pushId, parentId, enumPaNotification, "[]", true, " ");
		pb.setPublish(false);
		pb.setMail(true);
		PushNotifications p = pb.build(true);
		PushNotifications rs = pushNotificationsService.saveOrUpdate(p);
		if (rs != null) {
			pushId = rs.getId();
		}
		// Return
		return new ModelAndView("redirect:/protected/push-notifications/form/" + pushId.toString());
	}
}
