package it.unibo.paserver.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BlankPageController {
	@RequestMapping(value = "/protected/blank", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/blank/page");
		return modelAndView;
	}
}