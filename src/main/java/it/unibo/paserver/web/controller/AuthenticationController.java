package it.unibo.paserver.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthenticationController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(AuthenticationController.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView home(ModelAndView modelAndView) {
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName("/expired");
		return modelAndView;
	}
}
