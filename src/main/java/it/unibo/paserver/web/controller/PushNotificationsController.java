package it.unibo.paserver.web.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.DefaultResourceLoader;
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

import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import it.unibo.participact.domain.PANotification;
import it.unibo.paserver.domain.PushNotifications;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.SystemPage;
import it.unibo.paserver.domain.SystemPageType;
import it.unibo.paserver.domain.UserListPush;
import it.unibo.paserver.domain.support.PushNotificationsBuilder;
import it.unibo.paserver.service.MailingHistoryService;
import it.unibo.paserver.service.PushNotificationsService;
import it.unibo.paserver.service.SystemEmailService;
import it.unibo.paserver.service.TaskPublishService;
import it.unibo.paserver.service.UserListPushService;

/**
 * Controller dos push notification
 * 
 * @author Claudio
 */
@SuppressWarnings("Duplicates")
@Controller
public class PushNotificationsController extends ApplicationController {
	@Autowired
	private PushNotificationsService pushNotificationsService;
	@Autowired
	private TaskPublishService taskPublishService;
	@Autowired
	private UserListPushService userListPushService;
	@Autowired
	private SystemEmailService systemEmailService;
	@Autowired
	private MailingHistoryService mailingHistoryService;

	/**
	 * Pagina Inicial
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/push-notifications/index", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public ModelAndView home(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/push-notifications/index");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("isMail", false);
		return modelAndView;
	}

	/**
	 * Novo Item
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/push-notifications/new", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST')")
	public ModelAndView newPush(ModelAndView modelAndView) {
		// Default
		modelAndView.addObject("isMail", false);
		// View
		Long pushId = 0L;
		PANotification.Type enumPaNotification = PANotification.Type.MESSAGE;
		PushNotificationsBuilder pb = new PushNotificationsBuilder();
		pb.setAll(pushId, parentId, enumPaNotification, "[]", true, " ");
		pb.setPublish(false);
		PushNotifications p = pb.build(true);
		PushNotifications rs = pushNotificationsService.saveOrUpdate(p);
		if (rs != null) {
			pushId = rs.getId();
		}
		// Return
		return new ModelAndView("redirect:/protected/push-notifications/form/" + pushId.toString());
	}

	/**
	 * Formulario
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/push-notifications/form/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public ModelAndView form(@PathVariable("id") long id, ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/push-notifications/form");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		// Push
		PushNotifications push = pushNotificationsService.findById(id);
		if (push != null) {
			// User
			UserListPush userListPush = userListPushService.findUserListPush(push.getId());
			if (userListPush != null) {
				modelAndView.addObject("isReady", true);
				modelAndView.addObject("userListId", userListPush.getUserListId().getId());
			} else {
				modelAndView.addObject("isReady", false);
				modelAndView.addObject("userListId", 0L);
			}
			// Form
			modelAndView.addObject("pushId", push.getId());
			modelAndView.addObject("paMessage", push.getMessage());
			modelAndView.addObject("isMail", push.isMail());
			// Email
			if (push.isMail()) {
				// Texto defualt
				String filename = "classpath:/META-INF/invite_task_mail_deault.txt";
				DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
				String altBody = push.getEmailBody();
				if (Validator.isEmptyString(altBody)) {
					try {
						// Email
						InputStream source = defaultResourceLoader.getResource(filename).getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(source, "UTF-8"));
						altBody = org.apache.commons.io.IOUtils.toString(reader);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace(System.out);
					}
				}else {
					altBody =  org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(altBody);
				}
				modelAndView.addObject("systemEmail", systemEmailService.findAll());
				modelAndView.addObject("altBody", altBody);
				modelAndView.addObject("emailSystemId", push.getEmailSystemId());
				modelAndView.addObject("emailSubject", push.getEmailSubject());
				modelAndView.addObject("historyEmail", mailingHistoryService.findAll());
			}
		} else {
			return new ModelAndView("redirect:/protected/push-notifications/index");
		}
		// View
		return modelAndView;
	}

	/**
	 * Salva/Atualiza um item
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = { "/protected/push-notifications/save/", "/protected/push-notifications/edit/save/" }, method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public @ResponseBody ResponseJson save(@RequestBody String json, HttpServletRequest request) {
		// Id do usuario
		isRoot(request);
		long parentId = userId;
		// Vars
		Gson gson = new Gson();
		ResponseJson response = new ResponseJson();
		response.setStatus(false);
		response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
		// Request
		ReceiveJson r = new ReceiveJson(json);
		// Fields
		JsonArray assign_filter = r.getAsJsonArray("hashmap");
		String assignFilter = assign_filter != null && assign_filter.isJsonArray() ? gson.toJson(assign_filter) : "[]";
		String paMessage = r.getAsString("paMessage");
		Long pushId = Useful.convertStringToLong(r.getAsString("pushId"));
		boolean isPublish = r.getAsBoolean("isPublish");
		// Email
		String emailSubject = r.getAsString("emailSubject");
		String emailBody = r.getAsString("emailBody");
		Long emailSystemId = r.getAsLong("emailSystemId");
		/**
		 * 2016-12-17 Claudionor: Desabilitado definicao de tipo, conforme solicitado
		 * pelo Rolt String paNotification = r.getAsString("paNotification");
		 * PANotification.Type enumPaNotification =
		 * EnumUtils.isValidEnum(PANotification.Type.class, paNotification)?
		 * PANotification.Type.valueOf(paNotification) : PANotification.Type.MESSAGE;
		 */
		try {
			if (pushId == 0) {
				throw new Exception(messageSource.getMessage("error.invalid.id", null, LocaleContextHolder.getLocale()));
			} else {
				PushNotifications pd = pushNotificationsService.findById(pushId);
				boolean isSendEmail = false;
				if (pd != null) {
					isSendEmail = pd.isMail();
				}
				if (isSendEmail) {
					paMessage = emailSubject;
				}
				// Save
				if (!isSendEmail && !Validator.isValidStringLength(paMessage, 1, 160)) {
					throw new Exception(messageSource.getMessage("error.push.message", null, LocaleContextHolder.getLocale()));
				} else {
					// Email
					if (isSendEmail && !Validator.isValidStringLength(emailBody, 100, 999000)) {
						throw new Exception(messageSource.getMessage("error.altbody.required", null, LocaleContextHolder.getLocale()));
					} else if (isSendEmail && (emailSystemId == null || emailSystemId == 0)) {
						throw new Exception(messageSource.getMessage("fromEmail.title", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("error.field.empty", null, LocaleContextHolder.getLocale()));
					}
					// Enum
					PANotification.Type enumPaNotification = isSendEmail ? PANotification.Type.ONLY_EMAIL : PANotification.Type.MESSAGE;
					// Data
					PushNotificationsBuilder pb = new PushNotificationsBuilder();
					pb.setAll(pushId, parentId, enumPaNotification, assignFilter, !isPublish, paMessage);
					pb.setPublish(isPublish);
					pb.setMail(isSendEmail);
					pb.setEmailSubject(emailSubject);
					pb.setEmailBody(emailBody);
					pb.setEmailSystemId(emailSystemId);
					PushNotifications p = pb.build(true);
					PushNotifications rs = pushNotificationsService.saveOrUpdate(p);
					if (rs != null) {
						response.setStatus(true);
						response.setOutcome(rs.getId());
						if (isPublish) {
							taskPublishService.sendParticipantListPush(p);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			response.setMessage(e.getMessage());
			e.printStackTrace(System.out);
		}
		// Result
		return response;
	}

	/**
	 * Removed um item
	 * 
	 * @param modelAndView
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/protected/push-notifications/removed/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public @ResponseBody ResponseJson removed(@PathVariable("id") long id) throws JsonProcessingException {
		// Removed
		boolean removed = pushNotificationsService.removed(id);
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(removed);
		response.setMessage((removed) ? messageSource.getMessage("confirmation.remove.success", null, LocaleContextHolder.getLocale()) : messageSource.getMessage("confirmation.remove.fail", null, LocaleContextHolder.getLocale()));
		return response;
	}

	/**
	 * Busca customizada
	 * 
	 * @param search,
	 *            NAO USADO, MAS RESERVADO
	 * @param count
	 * @param offset
	 * @return
	 */
	@RequestMapping(value = "/protected/push-notifications/search/{count}/{offset}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public @ResponseBody ResponseJson search(@RequestBody String json, @PathVariable int count, @PathVariable int offset) {
		// Response
		ResponseJson response = new ResponseJson();
		response.setStatus(true);
		response.setCount(count);
		response.setOffset(offset);
		// Json
		ReceiveJson r = new ReceiveJson(json);
		// Values
		boolean isMail = r.getAsBoolean("isMail");
		// Request
		List<Object[]> items = pushNotificationsService.search(PaginationUtil.pagerequest(offset, count), isMail);
		if (items.size() > 0) {
			// For
			for (Object[] item : items) {
				item[10] = WordUtils.wrap(item[10].toString(), 50, "\n", true);
				item[2] = messageSource.getMessage("push.type." + item[2].toString().toLowerCase() + ".title", null, LocaleContextHolder.getLocale());
			}
			// Total
			response.setTotal(pushNotificationsService.searchTotal(isMail));
		}
		response.setItems(items);
		return response;
	}
}