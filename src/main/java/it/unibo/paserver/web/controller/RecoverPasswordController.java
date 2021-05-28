package it.unibo.paserver.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletRequest;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.webflow.action.EventFactorySupport;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.execution.Event;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.RecoverPassword;
import it.unibo.paserver.domain.ResponseMessage;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.RecoverPasswordService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.validator.RecoverPasswordFormValidator;

/**
 * Handles requests for password recoveries.
 */
@Controller
public class RecoverPasswordController implements ResourceLoaderAware {
	private static final Logger logger = LoggerFactory.getLogger(RecoverPasswordController.class);
	@Autowired
	private RecoverPasswordFormValidator recoverPasswordFormValidator;

	@Autowired
	private RecoverPasswordService recoverPasswordService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	protected UserService userService;

	private ResourceLoader resourceLoader;

	@ModelAttribute
	private RecoverPasswordForm getRecoverPasswordForm() {
		return new RecoverPasswordForm();
	}

	@InitBinder("recoverPasswordForm")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(recoverPasswordFormValidator);
	}

	public RecoverPassword getRecoverPassword(ExternalContext context) {
		String token = context.getRequestParameterMap().get("token");
		RecoverPassword recoverPassword = recoverPasswordService.findByToken(token);
		return recoverPassword;
	}

	public Event setPassword(RecoverPassword rp, ResetPasswordForm rpf) {
		User u = rp.getUser();
		u.setCredentials(rpf.getPassword());
		userService.save(u);
		logger.info("Changed password for user {}", u.getOfficialEmail());
		return new EventFactorySupport().success(rpf);
	}

	public ResetPasswordForm initResetPasswordForm() {
		return new ResetPasswordForm();
	}

	@RequestMapping(value = "/recoverpassword", method = RequestMethod.GET)
	public ModelAndView recoverPasswordGet(@ModelAttribute RecoverPasswordForm recoverPasswordForm, ModelAndView model) {
		model.setViewName("/recoverpassword");
		model.addObject(recoverPasswordForm);
		return model;
	}

	@RequestMapping(value = "/recoverpassword", method = RequestMethod.POST)
	public ModelAndView recoverPasswordPost(@ModelAttribute @Validated RecoverPasswordForm recoverPasswordForm, BindingResult bindingResult, ServletRequest servletRequest, ModelAndView modelAndView) {
		
		System.out.println("recoverPasswordPost ");
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("formerror", Boolean.TRUE);
			modelAndView.setViewName("/recoverpassword");
			return modelAndView;
		}
		modelAndView.addObject("targetemail", recoverPasswordForm.getEmail());
		modelAndView.setViewName("/recoverpasswordcompleted");
		User u = userService.getUser(recoverPasswordForm.getEmail());
		if (u != null) {
			System.out.println("Created password recovery request for user  from IP ");
			logger.info("Created password recovery request for user {} from IP {}", u.getOfficialEmail(), servletRequest.getRemoteAddr());
			setupRecovery(u, servletRequest);
		}else {
			System.out.println("Created password recovery request for user  from IP ");
			logger.info("USER NO EXIST");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/api/v1/public/forgot", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage forgot(@RequestBody String data, ServletRequest request) {
		ResponseMessage response = new ResponseMessage();
		response.setResultCode(200);
		String locale = Config.PRODUCTION_LANG;
		logger.info(data.toString());
		// Json
		try {
			JSONObject json = new JSONObject(data);
			String email = (json.has("email")) ? json.getString("email").trim() : "";
			locale = (json.has("locale")) ? json.getString("locale").trim() : null;
			// Validacao basica
			if (!Validator.isValidEmail(email)) {
				logger.info(email + " error.email.empty");
				throw new Exception(messageSource.getMessage("error.email.empty", null, Useful.getLocaleFromString(locale)));
			}
			// Executando
			User user = userService.getUser(email);
			if (user == null) {
				logger.info(email + " error.email.notfound");
				throw new Exception(messageSource.getMessage("error.email.notfound", null, Useful.getLocaleFromString(locale)));
			} else {
				setupRecovery(user, request);

				response.setProperty("status", "true");
				response.setProperty("message", Useful.replaceNull(messageSource.getMessage("label.reset.send_email", null, Useful.getLocaleFromString(locale))));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			response.setResultCode(500);
			response.setProperty("status", "false");
			response.setProperty("message", Useful.replaceNull(messageSource.getMessage("error.email.notfound", null, Useful.getLocaleFromString(locale))));
		}

		return response;
	}

	@Async
	private void setupRecovery(User u, ServletRequest servletRequest) {
		try {
			String token = Useful.secureRandomString();
			RecoverPassword recoverPassword = new RecoverPassword();
			DateTime now = new DateTime();
			DateTime end = now.plusHours(1);
			recoverPassword.setStartValidity(now);
			recoverPassword.setEndValidity(end);
			recoverPassword.setUser(u);
			recoverPassword.setToken(token);
			recoverPassword.setUser(u);
			recoverPassword = recoverPasswordService.save(recoverPassword);

			logger.debug("Created recover password id {} for user {} with token {}", recoverPassword.getId(), u.getOfficialEmail(), token);

			String url = String.format(Config.PRODUCTION_HOST + "resetpassword?token=%s", token);
			if (!url.contains("localhost")) {
				url = String.format(Config.PRODUCTION_URL + Config.PRODUCTION_HOST + "resetpassword?token=%s", token);
			}
			String ip = servletRequest.getRemoteAddr();
			String rcptTo = u.getOfficialEmail();
			Resource htmlres = resourceLoader.getResource("classpath:/META-INF/recoverpassword_mail_html_pt_br.txt");
			Resource txtres = resourceLoader.getResource("classpath:/META-INF/recoverpassword_mail_txt_pt_br.txt");
			String htmltemplate = resourceToString(htmlres);
			String txttemplate = resourceToString(txtres);
			HtmlEmail email = new HtmlEmail();
			email.setHostName(Config.emailHostName);
			email.addTo(rcptTo);
			email.setFrom(Config.emailFrom);
			email.addBcc(Config.emailFrom);
			email.setSmtpPort(Config.emailSmtpPort);
			email.setAuthenticator(new DefaultAuthenticator(Config.emailAuthenticatorUsername, Config.emailAuthenticatorPassword));
			email.setSSLOnConnect(true);
			email.setSubject("[ParticipAct] Please reset your password");
			String htmlcontent = setupEmail(htmltemplate, u, url, ip);
			String txtcontent = setupEmail(txttemplate, u, url, ip);
			email.setHtmlMsg(htmlcontent);
			email.setTextMsg(txtcontent);
			email.send();
		} catch (Exception e) {
			logger.error("Error while setting up password recovery e-mail", e);
			System.out.println("Error while setting up password recovery e-mail" + e.getMessage());
		}
	}

	/**
	 * 
	 * @param res
	 * @return
	 * @throws IOException
	 */
	private String resourceToString(Resource res) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(res.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			return sb.toString();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("Error while closing resource", e);
				}
			}
		}
	}

	private String setupEmail(String text, User u, String url, String ip) {
		String result = text.replaceAll("\\$name\\$", u.getName());
		result = result.replaceAll("\\$url\\$", url).replaceAll("\\$ip\\$", ip);
		return result;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
}
