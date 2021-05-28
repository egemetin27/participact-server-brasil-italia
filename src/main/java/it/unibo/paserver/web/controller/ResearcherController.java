package it.unibo.paserver.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.unibo.paserver.service.InstitutionsService;

@Controller
public class ResearcherController {
	@Autowired
	private InstitutionsService institutionsService;
	/**
	 * Pagina inicial
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/researcher/index", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/researcher/index");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		return modelAndView;
	}

	/**
	 * Formulario
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/researcher/form", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public ModelAndView form(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/researcher/form");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		modelAndView.addObject("institutions", institutionsService.findAll());
		return modelAndView;
	}

	/**
	 * Edicao de usuario
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/researcher/edit/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
	public ModelAndView edit(@PathVariable("id") long id, ModelAndView modelAndView) {
		// Model view
		modelAndView.setViewName("/protected/researcher/form");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		modelAndView.addObject("institutions", institutionsService.findAll());
		modelAndView.addObject("form", id);
		return modelAndView;
	}
}
