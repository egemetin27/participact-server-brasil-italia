package it.unibo.paserver.web.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.unibo.paserver.domain.MonthlyTargetScore;
import it.unibo.paserver.service.MonthlyTargetScoreService;
import it.unibo.paserver.web.validator.MonthlyTargetScoreValidator;

@Controller
public class MonthlyTargetScoreController {

	@Autowired
	MonthlyTargetScoreService monthlyTargetScoreService;

	@Autowired
	MonthlyTargetScoreValidator monthlyTargetScoreValidator;

	private static final Logger logger = LoggerFactory
			.getLogger(MonthlyTargetScoreController.class);

	@ModelAttribute("monthlyTargetScore")
	public MonthlyTargetScore getMonthlyTargetScore() {
		return new MonthlyTargetScore();
	}

	@InitBinder("monthlyTargetScore")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(monthlyTargetScoreValidator);
	}

	@RequestMapping(value = "/protected/scores/list", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView listScores(ModelAndView modelAndView) {
		List<MonthlyTargetScore> scoresList = monthlyTargetScoreService
				.getAll();
		modelAndView.addObject("scores", scoresList);
		modelAndView.setViewName("/protected/scores/list");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/scores/add", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView addScore(ModelAndView modelAndView) {
		modelAndView.addObject("monthlyTargetScore", new MonthlyTargetScore());
		modelAndView.setViewName("/protected/scores/add");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/scores/add", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView sendRequest(
			@ModelAttribute @Validated MonthlyTargetScore monthlyTargetScore,
			BindingResult bindingResult, ModelAndView modelAndView) {
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("/protected/scores/add");
			return modelAndView;
		}
		monthlyTargetScoreService.save(monthlyTargetScore);
		logger.info("Added monthly target score {}", monthlyTargetScore);
		modelAndView.setViewName("redirect:/protected/scores/list");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/scores/delete/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView deleteMonthlyScore(ModelAndView modelAndView,
			@RequestParam Long id, RedirectAttributes redirectAttributes) {
		monthlyTargetScoreService.delete(id);
		modelAndView.setViewName("redirect:/protected/scores/list");
		return modelAndView;
	}

}
