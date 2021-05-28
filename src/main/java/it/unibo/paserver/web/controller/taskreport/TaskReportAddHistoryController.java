package it.unibo.paserver.web.controller.taskreport;

import it.unibo.paserver.domain.TaskHistory;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.web.ResourceNotFoundException;
import it.unibo.paserver.web.validator.TaskHistoryValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import org.joda.time.DateTime;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TaskReportAddHistoryController {

	@Autowired
	TaskReportService taskReportService;

	@ModelAttribute("taskHistory")
	public TaskHistory getTaskHistory() {
		return new TaskHistory();
	}

	@InitBinder("taskHistory")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new TaskHistoryValidator());
	}

	private static final Logger logger = LoggerFactory
			.getLogger(TaskReportAddHistoryController.class);

	@RequestMapping(value = "/protected/taskreport/addhistory/{taskReportId}", method = RequestMethod.GET)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PreAuthorize("hasRole('NOT_ALLOWED_ACCESS')")
	public ModelAndView showAddHistory(@PathVariable Long taskReportId,
			ModelAndView modelAndView) {
		try {
			TaskReport taskReport = taskReportService.findById(taskReportId);
			modelAndView.addObject("taskReport", taskReport);
			modelAndView.addObject("taskHistory", new TaskHistory());
			modelAndView.setViewName("/protected/taskreport/addhistory");
			return modelAndView;
		} catch (NoResultException e) {
			logger.warn("Unable to find taskReport with id {}", taskReportId);
			throw new ResourceNotFoundException(e);
		}
	}

	@RequestMapping(value = "/protected/taskreport/addhistory/{taskReportId}", method = RequestMethod.POST)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PreAuthorize("hasRole('NOT_ALLOWED_ACCESS')")
	public ModelAndView postAddHistory(@PathVariable Long taskReportId,
			@Validated @ModelAttribute TaskHistory taskHistory,
			ModelAndView modelAndView, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		try {
			if (bindingResult.hasErrors()) {
				modelAndView.setViewName("/protected/taskreport/addhistory");
				return modelAndView;
			}
			TaskReport taskReport = taskReportService.findById(taskReportId);
			taskHistory.setTaskReport(taskReport);
			taskHistory.setTimestamp(new DateTime());
			taskReport.addHistory(taskHistory);
			taskReportService.save(taskReport);
			redirectAttributes.addFlashAttribute("taskhistorysuccessful",
					"taskhistorysuccessful");
			String destView = String.format(
					"redirect:/protected/taskreport/show/%d", taskReportId);
			modelAndView.setViewName(destView);
			return modelAndView;
		} catch (NoResultException e) {
			logger.warn("Unable to find taskReport with id {}", taskReportId);
			throw new ResourceNotFoundException(e);
		}
	}

	@ModelAttribute("taskStates")
	private List<String> getTaskStates() {
		List<String> taskStatesList = new ArrayList<String>();
		for (TaskState taskState : TaskState.values()) {
			taskStatesList.add(taskState.toString());
		}
		Collections.sort(taskStatesList);
		return taskStatesList;
	}
}