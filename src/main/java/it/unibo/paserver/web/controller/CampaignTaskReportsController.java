package it.unibo.paserver.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionActivityDetection;
import it.unibo.paserver.domain.ActionPhoto;
import it.unibo.paserver.domain.ActionQuestionaire;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.DataActivityRecognitionCompare;
import it.unibo.paserver.domain.DataPhoto;
import it.unibo.paserver.domain.MailingLogs;
import it.unibo.paserver.domain.Question;
import it.unibo.paserver.domain.ReceiveAdvancedSearch;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.DataService;
import it.unibo.paserver.service.MailingLogsService;
import it.unibo.paserver.service.TaskResultService;
import it.unibo.paserver.web.functions.PAServerUtils;

/**
 * Relatorios das tarefas/campanhas
 * 
 * @author Claudio
 *
 */
@Controller
public class CampaignTaskReportsController extends ApplicationController {
	@Autowired
	TaskResultService taskResultService;
	@Autowired
	MailingLogsService mailingLogsService;
	@Autowired
	DataService dataService;
	private ResponseJson response = new ResponseJson();

	/**
	 * Pagina com os relatorios do usuario
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/protected/campaign-task-reports/user/{taskId}/{userId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public ModelAndView index(@PathVariable("taskId") long taskId, @PathVariable("userId") long userId, ModelAndView modelAndView, HttpServletRequest request) {
		// Security
		isRoot(request);
		// Check
		isCheck(taskId);
		// View
		ArrayList<Question> questions = new ArrayList<Question>();
		try {
			User u = participantService.findById(userId);
			modelAndView.addObject("user", u);
			TaskReport taskReport = taskReportService.findByUserAndTask(userId, taskId);
			modelAndView.addObject("taskReport", taskReport);
			Set<Action> actions = taskReport.getTask().getActions();
			if (actions.size() > 0) {
				for (Action a : actions) {
					a.setTranslated(messageSource.getMessage("pipeline.type." + Useful.getTranslatedActionType(a), null, LocaleContextHolder.getLocale()));

					if (a != null && a instanceof ActionQuestionaire) {
						// Questionaire
						ActionQuestionaire questionaire = (ActionQuestionaire) a;
						int index = 0;
						// Questions
						for (Question q : questionaire.getQuestions()) {
							questions.add(index++, q);
						}
					}
				}
			}
			// Reordenando
			List<Action> orderedActions = new ArrayList<Action>();
			orderedActions.addAll(actions);
			Collections.sort(orderedActions, new Comparator<Action>() {
				@Override
				public int compare(Action o1, Action o2) {
					String o1name = PAServerUtils.actionToString(o1);
					String o2name = PAServerUtils.actionToString(o2);
					return o1name.compareTo(o2name);
				}
			});
			// Historico de Mensagens/Convites
			List<MailingLogs> mqList = mailingLogsService.findAllTaskIdAndUserId(taskId, userId);
			modelAndView.addObject("mailingLogs", mqList);
			// Resultado
			modelAndView.addObject("actions", orderedActions);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		modelAndView.setViewName("/protected/campaign-task-reports/user");
		modelAndView.addObject("breadcrumb", modelAndView.getViewName());
		modelAndView.addObject("taskId", taskId);
		modelAndView.addObject("userId", userId);
		// Questions
		modelAndView.addObject("questions", questions);
		// Response
		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/protected/campaign-task-reports/action/{count}/{offset}", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public @ResponseBody ResponseJson getListTaskDatas(@RequestBody String json, @PathVariable int count, @PathVariable int offset, HttpServletRequest request) {
		// Response
		response.setStatus(true);
		response.setCount(count);
		response.setOffset(offset);
		// Get
		try {
			ReceiveJson r = new ReceiveJson(json);
			long taskId = Useful.convertStringToLong(r.getAsString("taskId"));
			long userId = Useful.convertStringToLong(r.getAsString("userId"));
			long actionId = Useful.convertStringToLong(r.getAsString("actionId"));
			Action action = actionService.findById(actionId);
			Task task = campaignService.findById(taskId);
			// Hashmap
			ListMultimap<String, Object> params = ArrayListMultimap.create();

			if (action != null && task != null) {
				if (action instanceof ActionSensing) {
					// process sensing actions
					Class<? extends Data> clazz = ((ActionSensing) action).getDataClass();
					String className = clazz.getName();
					ReceiveAdvancedSearch[] res = r.getAsAdvancedSearch("hashmap");
					params = Useful.getDataQueryParameters(res, params, className);
					// Find
					List<? extends Data> items = dataService.search((Class<? extends Data>) Class.forName(className), task.getStart(), task.getDeadline(), userId, params, PaginationUtil.pagerequest(offset, count));
					response.setItems(Useful.getDataToObject(items, className));
					if (items.size() > 0) {
						response.setTotal(dataService.searchTotal((Class<? extends Data>) Class.forName(className), task.getStart(), task.getDeadline(), userId, params));
					}
				} else if (action instanceof ActionActivityDetection) {
					String className = DataActivityRecognitionCompare.class.getName();
					List<? extends Data> items = dataService.search((Class<? extends Data>) Class.forName(className), task.getStart(), task.getDeadline(), userId, params, PaginationUtil.pagerequest(offset, count));
					response.setItems(Useful.getDataToObject(items, className));
					if (items.size() > 0) {
						response.setTotal(dataService.searchTotal((Class<? extends Data>) Class.forName(className), task.getStart(), task.getDeadline(), userId, params));
					}
				} else if (action instanceof ActionPhoto) {
					List<DataPhoto> items = dataService.searchActionPhoto(taskId, userId, actionId, PaginationUtil.pagerequest(offset, count));
					response.setTotal(items.size());
					response.setItems(Useful.getDataToObject(items, DataPhoto.class.getName()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		// Return
		return response;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/protected/campaign-task-reports/action/chart", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public @ResponseBody ResponseJson getChartTaskDatas(@RequestBody String json, HttpServletRequest request) {
		// Get
		try {
			ReceiveJson r = new ReceiveJson(json);
			long taskId = Useful.convertStringToLong(r.getAsString("taskId"));
			long userId = Useful.convertStringToLong(r.getAsString("userId"));
			long actionId = Useful.convertStringToLong(r.getAsString("actionId"));

			Action action = actionService.findById(actionId);
			Task task = campaignService.findById(taskId);
			if (action != null && task != null) {
				// Chart
				if (action instanceof ActionSensing) {
					// process sensing actions
					Class<? extends Data> clazz = ((ActionSensing) action).getDataClass();
					String className = clazz.getName();
					List<Object[]> items = dataService.searchToChart((Class<? extends Data>) Class.forName(className), task.getStart(), task.getDeadline(), userId, Useful.getDataGroupBy(className));
					if (items.size() > 0) {
						List<HashMap<String, Object>> chart = new ArrayList<HashMap<String, Object>>();
						ArrayList<String> listed = new ArrayList<String>();
						long total = 0L;
						for (Object[] item : items) {
							long value = Long.parseLong(item[1].toString());
							total = total + value;
						}
						for (Object[] item : items) {
							// Vars
							String key = (String) item[0].toString();
							long value = Long.parseLong(item[1].toString());
							// Hash
							HashMap<String, Object> m = new HashMap<String, Object>();
							m.put("name", key);
							m.put("data", new float[] { Useful.getPercent(value, total) });
							m.put("id", key);
							chart.add(m);
							// Lista auxiliar
							listed.add(key);
						}
						// Data
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("series", chart);
						map.put("title", messageSource.getMessage("statistics.percent.total", null, LocaleContextHolder.getLocale()));
						map.put("listed", listed);
						// Set
						response.setChart(map);
						// Status
						response.setStatus(true);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		// Return
		return response;
	}

	/**
	 * Relatorios das campanhas
	 * 
	 * @param json
	 * @param count
	 * @param offset
	 * @return
	 */
	@RequestMapping(value = "/protected/campaign-task-reports/search/{count}/{offset}", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')")
	public @ResponseBody ResponseJson getListTaskReports(@RequestBody String json, @PathVariable int count, @PathVariable int offset) {
		// Response
		response.setStatus(true);
		response.setCount(count);
		response.setOffset(offset);
		String orderByDesc = " DESC ";
		try {
			ReceiveJson r = new ReceiveJson(json);
			orderByDesc = r.getAsBoolean("orderByDesc") ? " ASC " : " DESC ";
			// orderByColumn = r.getAsString("orderByColumn");
			String orderByColumn = "t.currentState " + orderByDesc;

			long id = Useful.convertStringToLong(r.getAsString("id"));
			String search = r.getAsString("search");
			if (!Validator.isValidStringLength(search, 1, 20)) {
				search = null;
			}

			if (id == 0) {
				response.setMessage(messageSource.getMessage("error.invalid.id", null, LocaleContextHolder.getLocale()));
			} else {
				Task t = campaignService.findById(id);
				if (t != null) {
					// Params / MultiMap
					ListMultimap<String, Object> params = ArrayListMultimap.create();
					// Busca
					List<Object[]> items = taskReportService.search(id, search, null, PaginationUtil.pagerequest(offset, count), orderByColumn);
					if (items.size() > 0) {
						// Translate
						String[] haystack = { TaskState.AVAILABLE.name(), TaskState.REJECTED.name(), TaskState.IGNORED.name() };
						for (Object[] item : items) {
							// Details
							if (item[5].toString() == "COMPLETED_WITH_FAILURE") {
								String details = "";
								Long userId = Long.parseLong(item[1].toString());
								Long sensingProgress = Long.parseLong(item[10].toString());
								Integer photoProgress = Integer.parseInt(item[11].toString());
								Integer questionnaireProgress = Integer.parseInt(item[10].toString());
								boolean isGuest = Boolean.parseBoolean(item[14].toString());
								Long progenitorId = Long.parseLong(item[13].toString());
								if(isGuest && progenitorId != null && progenitorId > 0 && progenitorId.longValue() != userId.longValue()) {
									User u = participantService.findParticipantById(progenitorId);
									if(u != null) {
										item[1] = u.getName(); 
									}
								}

								if (t.getHasPhotos() && photoProgress < 100) {
									details += messageSource.getMessage("photo.title", null, LocaleContextHolder.getLocale()) + " " + photoProgress + "%. ";
								}

								if (t.getHasQuestionnaire() && questionnaireProgress < 100) {
									details += messageSource.getMessage("pipeline.type.questionnaire", null, LocaleContextHolder.getLocale()) + " " + questionnaireProgress + "%. ";
								}

								if (t.getSensingDuration() > 0 && sensingProgress < 100) {
									details += messageSource.getMessage("passive.sensing.title", null, LocaleContextHolder.getLocale()) + " " + sensingProgress + "%. ";
								}
								item[9] = details;
							}
							// Info
							item[8] = !Validator.isValueinArray(haystack, item[5].toString());
							item[5] = messageSource.getMessage("statistics.state." + item[5].toString().toLowerCase(), null, LocaleContextHolder.getLocale());
						}
						// Total
						response.setTotal(taskReportService.searchTotal(id, search, null));
					}
					response.setItems(items);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		// Return
		return response;
	}
}
