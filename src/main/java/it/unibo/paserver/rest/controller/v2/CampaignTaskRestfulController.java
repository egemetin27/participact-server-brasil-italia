package it.unibo.paserver.rest.controller.v2;

import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.JsonArray;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.domain.flat.ActionFlat;
import it.unibo.paserver.domain.flat.TaskFlat;
import it.unibo.paserver.domain.support.TaskUserIncludedBuilder;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.TaskUserIncludedService;
import it.unibo.paserver.service.UserListTaskService;
import it.unibo.paserver.web.controller.ParticipantController;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Campanhas publicadas
 *
 * @author Claudio
 */
@SuppressWarnings("Duplicates")
@RestController
public class CampaignTaskRestfulController extends ApplicationRestfulController {
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected TaskUserIncludedService taskUserIncludedService;
    @Autowired
    private UserListTaskService userListTaskService;
    @Autowired
    private TaskReportService taskReportService;
    @Autowired
    private ParticipantController participantController;
    /**
     * Var locais
     */
    private Long sensingProgress = 0L;
    private Integer photoProgress = 0;
    private Integer questionnaireProgress = 0;

    /**
     * Lista de Campanha
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/campaigns"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonTask searchCampaigns(@RequestBody String json, HttpServletRequest request) throws Exception {
        ResponseJsonTask response = new ResponseJsonTask();
        System.out.println("searchCampaigns");
        // Details
        isUserDetails(request);
        Long userId = getUserId();
        long progenitorId = getProgenitorId(userId);
        long orProgenitorId = 0L;
        if (userId.longValue() != progenitorId && progenitorId > 0) {
            orProgenitorId = progenitorId;
        }
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(userId);
        // Request
        ReceiveJson r = new ReceiveJson(json);
        int count = r.getAsInt("count");
        count = count == 0 ? 10 : count;
        int offset = r.getAsInt("offset");
        // Sensors
        ArrayList<Integer> sensorslist = new ArrayList<Integer>();
        JsonArray availableSensors = r.getAsJsonArray("availableSensors");
        if (availableSensors != null && availableSensors.isJsonArray() && availableSensors.size() > 0) {
            int len = availableSensors.size();
            for (int i = 0; i < len; i++) {
                Integer keySensor = availableSensors.get(i).getAsInt();
                sensorslist.add(keySensor);
                System.out.println(keySensor);
            }
        }
        // Version
        String version = r.getAsString("version");
        // DateTime updateDate = new DateTime().minusYears(1);
        DateTime updateDate = null;
        if (Validator.isValidDateFormat(version, "yyyy-MM-dd HH:mm:ss")) {
            updateDate = Useful.converteStringToDate(version);
            response.setVersion(updateDate);
        } else {
            response.setVersion(new DateTime());
        }
        // Auxiliares
        String assignFilter = "[]";
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        try {
            // CATALINA.OUT JSON
            System.out.println(json.toString());
            System.out.println(String.valueOf(updateDate));
            // Query
            List<TaskFlat> listTask = new ArrayList<TaskFlat>();
            // Publicas
            List<Task> listTaskPublic = userListTaskService.fetchAllAndPublicPublish(updateDate, PaginationUtil.pagerequest(offset, count));
            if (listTaskPublic.size() > 0) {
                // Fix
                listTask = fixTaskDuration(listTaskPublic);
            }
            // Restrito
            List<UserListTask> userListTaskRegistered = userListTaskService.fetchAllSelector(AudienceSelector.SELECTOR_RESTRICTED, updateDate, PaginationUtil.pagerequest(offset, count));
            if (userListTaskRegistered.size() > 0) {
                for (UserListTask x : userListTaskRegistered) {
                    assignFilter = x.getUserListId().getHashmap();
                    // Converter
                    r = new ReceiveJson("{hashmap:" + assignFilter + "}");
                    ReceiveAdvancedSearch[] search = r.getAsAdvancedSearch("hashmap");
                    // Add Filter
                    ReceiveAdvancedSearch filterUserId = new ReceiveAdvancedSearch();
                    filterUserId.setKey("FILTER_USERID");
                    filterUserId.setValue(userId.toString());
                    filterUserId.setItem(userId.toString());
                    filterUserId.setLabel("info");
                    //Search
                    search = ArrayUtils.add(search, filterUserId);
                    // Params/Filter
                    params = participantController.advancedQueryParameters(search, params);
                    // Check
                    if (participantController.searchTotal(params) > 0) {
                        listTask.add(fixTaskDuration(x.getTaskId()));
                    }
                }
            }
            // Fechada
            List<Task> listTaskClosed = userListTaskService.fetchAllAndClosed(getUserId(), updateDate, PaginationUtil.pagerequest(offset, count));
            if (listTaskClosed.size() > 0) {
                // Fix
                listTask = ListUtils.union(listTask, fixTaskDuration(listTaskClosed));
            }
            // Vinculadas
            //long progenitorId = getProgenitorId(getUserId());
            if (progenitorId > 0) {
                List<Task> listProgenitor = userListTaskService.fetchAllAndClosed(progenitorId, updateDate, PaginationUtil.pagerequest(offset, count));
                if (listProgenitor.size() > 0) {
                    // Fix
                    listTask = ListUtils.union(listTask, fixTaskDuration(listProgenitor));
                }
            }
            if (orProgenitorId > 0) {
                List<Task> listOrProgenitor = userListTaskService.fetchAllAndClosed(orProgenitorId, updateDate, PaginationUtil.pagerequest(offset, count));
                if (listOrProgenitor.size() > 0) {
                    // Fix
                    listTask = ListUtils.union(listTask, fixTaskDuration(listOrProgenitor));
                }
            }
            // Fix Sensor List
            if (!sensorslist.isEmpty() && !listTask.isEmpty()) {
                ArrayList<TaskFlat> removeTaskList = new ArrayList<TaskFlat>();
                for (TaskFlat taskFlat : listTask) {
                    Set<ActionFlat> actionList = taskFlat.getActions();
                    ArrayList<ActionFlat> removeActionList = new ArrayList<ActionFlat>();
                    if (!actionList.isEmpty()) {
                        for (ActionFlat actionItem : actionList) {
                            // Type
                            ActionType actionType = actionItem.getType();
                            // Flag
                            boolean hasInputType = false;
                            for (Integer sensorItem : sensorslist) {
                                if (sensorItem.intValue() == actionItem.getInput_type().intValue() || actionType.equals(ActionType.QUESTIONNAIRE) || actionType.equals(ActionType.PHOTO)) {
                                    System.out.println(actionItem.getInput_type());
                                    hasInputType = true;
                                    break;
                                }
                            }

                            // Check
                            if (!hasInputType) {
                                removeActionList.add(actionItem);
                                // actionList.remove(actionItem);
                            }

                        }
                        // ReSet
                        if (!removeActionList.isEmpty()) {
                            for (ActionFlat removeItem : removeActionList) {
                                actionList.remove(removeItem);
                            }
                            // Refresh
                            taskFlat.setActions(actionList);
                        }
                        // Check minimal task
                        if (actionList.isEmpty() && !taskFlat.getHasPhotos() && !taskFlat.getHasQuestionnaire()) {
                            removeTaskList.add(taskFlat);
                        }
                    }
                }
                // Remove unavailable
                if (!removeTaskList.isEmpty()) {
                    for (TaskFlat removeItem : removeTaskList) {
                        listTask.remove(removeItem);
                    }
                }
            }
            // Resultado
            response.setStatus(true);
            response.setMessage(null);
            if (listTask.size() > 0) {
                response.setTasks(listTask);
                response.setCount(listTask.size());
                Task v = userListTaskService.findLastUpdate();
                if (v != null) {
                    response.setVersion(v.getUpdateDate().plusMinutes(1));
                }
            } else {
                response.setVersion(null);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Campanhas removidas
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/campaigns/deleted"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonTask deletedTask(@RequestBody String json, HttpServletRequest request) throws Exception {
        ResponseJsonTask response = new ResponseJsonTask();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        int count = r.getAsInt("count");
        count = count == 0 ? 10 : count;
        int offset = r.getAsInt("offset");
        // Version
        String version = r.getAsString("version");
        // DateTime updateDate = new DateTime().minusYears(1);
        DateTime updateDate = null;
        if (Validator.isValidDateFormat(version, "yyyy-MM-dd HH:mm:ss")) {
            updateDate = Useful.converteStringToDate(version);
            response.setVersion(updateDate);
        } else {
            response.setVersion(new DateTime());
        }
        try {
            // CATALINA.OUT JSON
            System.out.println(json.toString());
            System.out.println(String.valueOf(updateDate));
            // Query
            List<Task> listTask = taskService.fetchAllByRemoved(PaginationUtil.pagerequest(offset, count), updateDate);
            if (listTask.size() > 0) {
                // Fix
                List<TaskFlat> f = fixTaskDuration(listTask);
                // Return
                response.setTasks(f);
                response.setCount(f.size());
            }
            response.setStatus(true);
            response.setMessage(null);
            if (listTask.size() > 0) {
                Task v = userListTaskService.findLastUpdateRemoved();
                if (v != null) {
                    response.setVersion(v.getUpdateDate().plusMinutes(1));
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Historico de Campanhas
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/campaigns/history"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonTask historyTask(@RequestBody String json, HttpServletRequest request) throws Exception {
        ResponseJsonTask response = new ResponseJsonTask();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        int count = r.getAsInt("count");
        count = count == 0 ? 10 : count;
        int offset = r.getAsInt("offset");
        try {
            // CATALINA.OUT JSON
            System.out.println(json.toString());
            // Query
            List<Task> listTask = taskUserIncludedService.fetchAllByUser(getUserId(), PaginationUtil.pagerequest(offset, count));
            if (listTask.size() > 0) {
                // Fix
                List<TaskFlat> f = fixTaskDuration(listTask);
                // Return
                response.setStatus(true);
                response.setMessage(null);
                response.setTasks(f);
                response.setCount(f.size());
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Aceitando Campanha
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/campaigns/accept"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest acceptTask(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        Long taskId = r.getAsLong("taskId");
        try {
            // CATALINA.OUT JSON
            System.out.println(json.toString());

            if (taskId > 0) {
                Task task = userListTaskService.findUserTask(taskId);
                User user = authenticatorService.doUser(getUserId());
                if (task != null && user != null) {
                    // Incluindo na relacao de inscritos
                    if (!taskUserIncludedService.isIncluded(task.getId(), user.getId())) {
                        TaskUserIncludedBuilder tuib = new TaskUserIncludedBuilder();
                        TaskUserIncluded tui = tuib.setAll(0L, task, user).build(true);
                        taskUserIncludedService.saveOrUpdate(tui);
                    }
                    // Initial
                    boolean isThere = isThereInitialReportHistory(task, user);
                    if (isThere) {
                        // Add History
                        TaskReport taskReport = addTaskReportHistory(task, user, TaskState.RUNNING);
                        // Result
                        if (taskReport != null) {
                            response.setStatus(true);
                            response.setOutcome(taskReport.getId());
                            response.setMessage(null);
                        } else {
                            throw new Exception("TaskReport not found");
                        }
                    } else {
                        response.setStatus(true);
                    }
                } else {
                    throw new Exception("Task not found");
                }
            } else {
                throw new Exception("Invalid TaskId");
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Rejeitando Campanha
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/campaigns/reject"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest rejectTask(@RequestBody String json, HttpServletRequest request) throws Exception {
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson rc = new ReceiveJson(json);
        Long taskId = rc.getAsLong("taskId");
        try {
            // CATALINA.OUT JSON
            System.out.println(json.toString());

            if (taskId > 0) {
                Task task = userListTaskService.findUserTask(taskId);
                User user = authenticatorService.doUser(getUserId());
                if (task != null && user != null) {
                    // Initial
                    isThereInitialReportHistory(task, user);
                    // Add History
                    TaskReport taskReport = addTaskReportHistory(task, user, TaskState.REJECTED);
                    // Result
                    if (taskReport != null) {
                        response.setStatus(true);
                        response.setOutcome(taskReport.getId());
                        response.setMessage(null);
                    } else {
                        throw new Exception("TaskReport not found");
                    }
                } else {
                    throw new Exception("Task not found");
                }
            } else {
                throw new Exception("Invalid TaskId");
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Completa com SUCESSO a campanha
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/campaigns/complete"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest completeTask(@RequestBody String json, HttpServletRequest request) throws Exception {
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson rc = new ReceiveJson(json);
        Long taskId = rc.getAsLong("taskId");
        try {
            // CATALINA.OUT JSON
            System.out.println(json.toString());

            if (taskId > 0) {
                Task task = userListTaskService.findUserTask(taskId);
                User user = authenticatorService.doUser(getUserId());
                if (task != null && user != null) {
                    // Initial
                    isThereInitialReportHistory(task, user);
                    // Add History
                    TaskReport taskReport = addTaskReportHistory(task, user, TaskState.COMPLETED_WITH_SUCCESS);
                    // Result
                    if (taskReport != null) {
                        response.setStatus(true);
                        response.setOutcome(taskReport.getId());
                        response.setMessage(null);
                    } else {
                        throw new Exception("TaskReport not found");
                    }
                } else {
                    throw new Exception("Task not found");
                }
            } else {
                throw new Exception("Invalid TaskId");
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Completa com ERRO a campanha
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/campaigns/incomplete"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest incompleteTask(@RequestBody String json, HttpServletRequest request) throws Exception {
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        Long taskId = r.getAsLong("taskId");
        sensingProgress = r.getAsLong("sensingProgress");
        photoProgress = r.getAsInt("photoProgress");
        questionnaireProgress = r.getAsInt("questionnaireProgress");
        try {
            // CATALINA.OUT JSON
            System.out.println(json.toString());

            if (taskId > 0) {
                Task task = userListTaskService.findUserTask(taskId);
                User user = authenticatorService.doUser(getUserId());
                if (task != null && user != null) {
                    // Initial
                    isThereInitialReportHistory(task, user);
                    // Add History
                    TaskReport taskReport = addTaskReportHistory(task, user, TaskState.COMPLETED_WITH_FAILURE);
                    // Result
                    if (taskReport != null) {
                        response.setStatus(true);
                        response.setOutcome(taskReport.getId());
                        response.setMessage(null);
                    } else {
                        throw new Exception("TaskReport not found");
                    }
                } else {
                    throw new Exception("Task not found");
                }
            } else {
                throw new Exception("Invalid TaskId");
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Adicionando no historico/report a resposta do usuario
     *
     * @param task
     * @param taskState
     * @return
     */
    private TaskReport addTaskReportHistory(Task task, User user, TaskState taskState) {
        TaskReport taskReport = taskReportService.findByUserAndTask(user.getId(), task.getId());
        if (taskReport != null) {
            TaskHistory taskHistory = new TaskHistory();
            taskHistory.setState(taskState);
            taskHistory.setTaskReport(taskReport);
            taskHistory.setTimestamp(new DateTime());
            if (taskState.equals(TaskState.ACCEPTED) || taskState.equals(TaskState.RUNNING)) {
                taskReport.setAcceptedDateTime(new DateTime());
                Long duration = taskReport.getTask().getDuration();
                if (duration != null) {
                    duration = duration > 60 ? duration / 60 : 0;
                    DateTime expiryDateTime = taskReport.getAcceptedDateTime().plusMinutes(duration.intValue());
                    taskReport.setExpirationDateTime(expiryDateTime);
                }
            } else if (taskState.equals(TaskState.COMPLETED_WITH_FAILURE)) {
                taskHistory.setPhotoProgress(photoProgress);
                taskHistory.setSensingProgress(sensingProgress);
                taskHistory.setQuestionnaireProgress(questionnaireProgress);

                taskReport.setPhotoProgress(photoProgress);
                taskReport.setSensingProgress(sensingProgress);
                taskReport.setQuestionnaireProgress(questionnaireProgress);
            }
            taskReport.addHistory(taskHistory);
            return taskReportService.save(taskReport);
        }
        return null;
    }

    /**
     * Se contem relatorio inicial, senao cria
     *
     * @param task
     * @param user
     */
    private boolean isThereInitialReportHistory(Task task, User user) {
        // Contem historico?
        if (taskReportService.getTaskReportsCountByUserAndTask(user.getId(), task.getId()) == 0) {
            TaskReport e = new TaskReport();
            e.setUser(user);
            e.setTask(task);
            e.setCurrentState(TaskState.AVAILABLE);
            e.setHistory(new ArrayList<TaskHistory>());
            Set<TaskReport> r = new HashSet<TaskReport>();
            r.add(e);
            task.setTaskReport(r);

            TaskHistory h = new TaskHistory();
            h.setState(TaskState.AVAILABLE);
            h.setTaskReport(e);
            h.setTimestamp(new DateTime());
            e.addHistory(h);
            taskService.save(task);
        }
        return true;
    }

    /**
     * Fixa o tempo das duracoes no formato do app para um a lista de tarefas
     *
     * @param listTask
     * @return
     */
    private List<TaskFlat> fixTaskDuration(List<Task> listTask) {

        List<TaskFlat> f = new ArrayList<TaskFlat>();
        for (Task currentTask : listTask) {
            f.add(fixTaskDuration(currentTask));
        }
        return f;
    }

    /**
     * Fixa o tempo para uma tarefa
     *
     * @param currentTask
     * @return
     */
    private TaskFlat fixTaskDuration(Task currentTask) {
        /**
         * Fix issue, painel salva em segundos e app espera em minutos
         */
        long duration = currentTask.getDuration();
        long sensingDuration = currentTask.getSensingDuration();
        duration = duration > 60 ? duration / 60 : 0;
        sensingDuration = sensingDuration > 60 ? sensingDuration / 60 : 0;
        currentTask.setSensingDuration(sensingDuration);
        currentTask.setDuration(duration);

        TaskFlat f = new TaskFlat(currentTask);

        return f;
    }
}