package it.unibo.paserver.web.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.unibo.paserver.domain.AbstractBadge;
import it.unibo.paserver.domain.Badge;
import it.unibo.paserver.domain.BadgeActions;
import it.unibo.paserver.service.BadgeService;
import it.unibo.paserver.web.ResourceNotFoundException;
import it.unibo.paserver.web.validator.EditBadgeFormValidator;

@Controller
public class BadgeWebController {

	@Autowired
	private BadgeService badgeService;

	@Autowired
	private EditBadgeFormValidator editBadgeFormValidator;

	private static final Logger logger = LoggerFactory
			.getLogger(BadgeWebController.class);

	@ModelAttribute("editBadgeForm")
	public EditBadgeForm getEditBadgeForm() {
		return new EditBadgeForm();
	}

	@InitBinder("editBadgeForm")
	public void initBinderEdit(WebDataBinder binder) {
		binder.setValidator(editBadgeFormValidator);
	}

	@RequestMapping(value = "/protected/badge", method = RequestMethod.GET)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PreAuthorize("hasRole('NOT_ALLOWED_ACCESS')")
	public ModelAndView listBadges(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/badge");
		@SuppressWarnings("unchecked")
		List<AbstractBadge> badges = (List<AbstractBadge>) badgeService
				.getBadges();
		modelAndView.addObject("badges", badges);
		modelAndView.addObject("totalBadges", badges.size());
		return modelAndView;
	}

	@RequestMapping(value = "/protected/badge/delete", method = RequestMethod.GET)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PreAuthorize("hasRole('NOT_ALLOWED_ACCESS')")
	public ModelAndView deleteBadge(@RequestParam Integer id,
			ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
		logger.trace("Received request to delete badge {}", id);
		if (badgeService.deleteBadge(id)) {
			redirectAttributes.addFlashAttribute("successmessage",
					String.format("Badge #\"%d\" successfully deleted", id));
		} else {
			String msg = String
					.format("Unabe to delete badge #\"%d\", please consult logs for further information",
							id);
			redirectAttributes.addFlashAttribute("errormessage", msg);
		}
		modelAndView.setViewName("redirect:/protected/badge");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/badge/show/{id}", method = RequestMethod.GET)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PreAuthorize("hasRole('NOT_ALLOWED_ACCESS')")
	public ModelAndView showBadge(@PathVariable Long id,
			ModelAndView modelAndView) {
		Badge badge = badgeService.findById(id);
		if (badge == null) {
			throw new ResourceNotFoundException();
		}
		modelAndView.setViewName("protected/badge/show");
		boolean isActions = (badge instanceof BadgeActions);
		modelAndView.addObject("badge", badge);
		modelAndView.addObject("isAction", isActions);
		return modelAndView;
	}

	@RequestMapping(value = "/protected/badge/edit/{id}", method = RequestMethod.GET)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PreAuthorize("hasRole('NOT_ALLOWED_ACCESS')")
	public ModelAndView editBadge(@PathVariable Long id,
			@ModelAttribute EditBadgeForm editBadgeForm,
			ModelAndView modelAndView) {
		Badge badge = badgeService.findById(id);
		if (badge == null) {
			throw new ResourceNotFoundException();
		}
		editBadgeForm.initFromBadge(badge);
		modelAndView.addObject("badgeId", id);
		modelAndView.setViewName("protected/badge/edit");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/badge/edit/{id}", method = RequestMethod.POST)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PreAuthorize("hasRole('NOT_ALLOWED_ACCESS')")
	public ModelAndView postBadgeEdit(@PathVariable Long id,
			@ModelAttribute @Validated EditBadgeForm ebf,
			BindingResult bindingResult, ModelAndView modelAndView,
			RedirectAttributes redirectAttributes, Principal principal) {
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("formerror", Boolean.TRUE);
			modelAndView.addObject("badgeId", id);
			modelAndView.setViewName("/protected/badge/edit");
			return modelAndView;
		}
		Badge badge = badgeService.findById(id);
		ebf.updateBadge(badge);
		badge = badgeService.save(badge);
		modelAndView.setViewName("redirect:/protected/badge/show/" + id);
		return modelAndView;
	}
}
