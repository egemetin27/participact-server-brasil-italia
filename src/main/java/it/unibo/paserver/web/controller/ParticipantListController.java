package it.unibo.paserver.web.controller;

import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.domain.support.UserListBuilder;
import it.unibo.paserver.domain.support.UserListPushBuilder;
import it.unibo.paserver.domain.support.UserListTaskBuilder;
import it.unibo.paserver.service.InstitutionsService;
import it.unibo.paserver.service.ParticipantListService;
import it.unibo.paserver.service.SchoolCourseService;
import it.unibo.paserver.service.TaskPublishService;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Filtro de Consulta dos Participantes
 *
 * @author Claudio
 */
@Controller
public class ParticipantListController extends ApplicationController {
    ResponseJson response = new ResponseJson();
    @Autowired
    private InstitutionsService institutionsService;
    @Autowired
    private SchoolCourseService schoolCourseService;
    @Autowired
    private TaskPublishService taskPublishService;
    @Autowired
    private ParticipantListService participantListService;
    @Autowired
    private MessageSource messageSource;
    private String wayback;

    /**
     * Pagina inicial
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/participant-list/index", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView index(ModelAndView modelAndView) {
        // View
        Long userListId = isValidUserListId().getId();
        // Return
        return new ModelAndView("redirect:/protected/participant-list/index/" + userListId.toString());
    }

    /**
     * Carregando inicial para o Push
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/participant-list/push/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView push(@PathVariable("id") Long id, ModelAndView modelAndView) {
        // Id
        Long userListId = 0L;
        this.wayback = "/protected/push-notifications/form/" + id;
        // Verifica se ja contem
        UserListPush userListPush = participantListService.findUserListPush(id);
        if (userListPush != null) {
            userListId = userListPush.getUserListId().getId();
        } else {
            UserList userList = isValidUserListId();
            // Get Push
            PushNotifications push = participantListService.findUserPush(id);
            // Add
            UserListPushBuilder ulpb = new UserListPushBuilder();
            UserListPush ulp = ulpb.setAll(0L, userList, push, false).build(true);
            participantListService.addUserListPush(ulp);
            // Id
            userListId = userList.getId();
        }
        // Return
        return new ModelAndView("redirect:/protected/participant-list/index/" + userListId.toString());
    }

    /**
     * Carregando inicial para Campanha
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/participant-list/task/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView task(@PathVariable("id") Long id, ModelAndView modelAndView) {
        // Id
        Long userListId = 0L;
        this.wayback = "/protected/participant-list/assign/" + id;
        // Verifica se ja contem
        UserListTask userListTask = participantListService.findUserListTask(id);
        if (userListTask != null) {
            userListId = userListTask.getUserListId().getId();
        } else {
            UserList userList = isValidUserListId();
            // Get Task
            Task task = campaignService.findById(id);
            // Add
            UserListTaskBuilder ultb = new UserListTaskBuilder();
            UserListTask ult = ultb.setAll(0L, userList, task, false).build(true);
            participantListService.addUserListTask(ult);
            // Id
            userListId = userList.getId();
        }
        // Return
        return new ModelAndView("redirect:/protected/participant-list/index/" + userListId.toString());
    }

    /**
     * Vinculando Campanha/Participantes
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/participant-list/assign/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView assign(@PathVariable("id") Long id, ModelAndView modelAndView) {
        UserListTask userListTask = participantListService.findUserListTask(id);
        // Lista
        if (userListTask != null) {
            Task task = userListTask.getTaskId();
            UserList userList = userListTask.getUserListId();
            if (task != null) {
                boolean isSelectAll = false;
                Long assignAvailable = 0L;
                Long assignSelected = 0L;
                String assignFilter = userList.getHashmap();
                AudienceSelector audienceSelector = userList.getAudienceSelector();
                if (audienceSelector.equals(AudienceSelector.SELECTOR_ALL)) {
                    isSelectAll = true;
                    assignAvailable = assignSelected = participantService.getUserCount();
                } else if (audienceSelector.equals(AudienceSelector.SELECTOR_CLOSED)) {
                    ListMultimap<String, Object> params = ArrayListMultimap.create();
                    params.put("userListId", userList.getId());

                    assignSelected = participantListService.searchTotalUserListItem(params);
                }
                // Campanha
                if (!task.isPublish()) {
                    task.setAssignAvailable(assignAvailable);
                    task.setAssignSelected(assignSelected);
                    task.setAssignFilter(assignFilter);
                    task.setSelectAll(isSelectAll);
                    task.setAssign(true);
                    if (taskService.save(task) != null) {
                        response.setStatus(true);
                    }
                } else if (!task.isExpired()) {
                    task.setAssignSelected(assignSelected);
                    task.setAssignFilter(assignFilter);
                    task.setSelectAll(isSelectAll);
                    if (taskService.save(task) != null) {
                        response.setStatus(true);
                        taskPublishService.sendParticipantListTask(task, false);
                    }
                }
            }
        } else {
            System.out.println("CampaignTaskAssignController NO TASKS");
        }
        // Return
        return new ModelAndView("redirect:/protected/campaign-task/index/" + id.toString());
    }

    /**
     * Pagina inicial com Id
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/participant-list/index/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView index(@PathVariable("id") Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/participant-list/index");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("institutions", institutionsService.findAll());
        modelAndView.addObject("courses", schoolCourseService.findAll());
        modelAndView.addObject("userListId", id);
        modelAndView.addObject("taskId", 0L);
        // Return
        return modelAndView;
    }

    /**
     * Salva/Atualiza um item
     *
     * @param json
     * @return
     */
    @RequestMapping(value = {"/protected/participant-list/item/save/"}, method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson save(@RequestBody String json) {
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        // Request
        ReceiveJson r = new ReceiveJson(json);
        try {
            Long userId = Useful.convertStringToLong(r.getAsString("userId"));
            Long userListId = Useful.convertStringToLong(r.getAsString("userListId"));
            // Validate
            if (userId > 0 && userListId > 0) {
                UserListItem rs = participantListService.addUserListItem(userListId, userId);
                if (rs != null) {
                    response.setStatus(true);
                    response.setOutcome(rs.getId());
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
     * Salva/Atualiza uma lista
     *
     * @param json
     * @return
     */
    @RequestMapping(value = {"/protected/participant-list/save/"}, method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson updated(@RequestBody String json) {
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        // Request
        ReceiveJson r = new ReceiveJson(json);
        try {
            // Hashmap
            JsonArray filter = r.getAsJsonArray("hashmap");
            Gson gson = new Gson();
            String hashmap = filter != null && filter.isJsonArray() ? gson.toJson(filter) : "[]";
            // ListId
            Long userListId = Useful.convertStringToLong(r.getAsString("userListId"));
            // Audience
            String audienceSelector = r.getAsString("audienceSelector");
            AudienceSelector enumAudienceSelector = EnumUtils.isValidEnum(AudienceSelector.class, audienceSelector) ? AudienceSelector.valueOf(audienceSelector) : AudienceSelector.SELECTOR_NONE;
            // Validate
            if (userListId > 0) {
                UserList userList = participantListService.findUserList(userListId);
                if (userList != null) {
                    userList.setAudienceSelector(enumAudienceSelector);
                    userList.setHashmap(hashmap);
                    // Update
                    UserList rs = participantListService.addUserList(userList);
                    if (rs != null) {
                        response.setStatus(true);
                        response.setOutcome(rs.getId());
                        response.setMessage(this.wayback);
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
     * Remove um item
     *
     * @param json
     * @return
     */
    @RequestMapping(value = {"/protected/participant-list/item/removed/"}, method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson removed(@RequestBody String json) {
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        // Request
        ReceiveJson r = new ReceiveJson(json);
        try {
            Long userId = Useful.convertStringToLong(r.getAsString("userId"));
            Long userListId = Useful.convertStringToLong(r.getAsString("userListId"));
            // Validate
            if (userId > 0 && userListId > 0) {
                if (participantListService.removeUserListItem(userListId, userId)) {
                    response.setStatus(true);
                    response.setOutcome(userId);
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
     * Dados de uma lista
     *
     * @param id
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/protected/participant-list/edit/{id}/find", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson find(@PathVariable("id") long id, HttpServletRequest request) throws JsonProcessingException {
        isRoot(request);
        UserList userList = participantListService.findUserList(id);
        if (userList != null) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("hashmap", userList.getHashmap());
            item.put("audienceSelector", userList.getAudienceSelector());
            // Return
            response.setStatus(true);
            response.setItem(item);
        }
        return response;
    }

    /**
     * Busca customizada
     * *
     *
     * @param json
     * @param count
     * @param offset
     * @return
     */
    @RequestMapping(value = "/protected/participant-list/item/search/{count}/{offset}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson search(@RequestBody String json, @PathVariable int count, @PathVariable int offset) {
        // Response
        response.setStatus(true);
        response.setCount(count);
        response.setOffset(offset);
        // Params / MultiMap
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        try {
            ReceiveJson r = new ReceiveJson(json);
            // Task Id
            Long taskId = r.getAsLong("taskId");
            if (taskId > 0) {
                params.put("taskId", taskId);
            }
            // Task Id
            Long userListId = r.getAsLong("userListId");
            if (userListId > 0) {
                params.put("userListId", userListId);
                // Busca
                List<Object[]> items = participantListService.searchUserListItem(params, PaginationUtil.pagerequest(offset, count));
                if (items.size() > 0) {
                    // Total
                    response.setTotal(participantListService.searchTotalUserListItem(params));
                }
                response.setItems(items);
            }
        } catch (Exception e) {
            // e.printStackTrace(System.out);
            System.out.println("ParticipantListController search " + e.getMessage());
        }
        // return
        return response;
    }

    /**
     * Retorna o UserList se ja houver
     *
     * @return
     */
    private UserList isValidUserListId() {
        // New
        UserListBuilder ulb = new UserListBuilder();
        UserList l = ulb.setAll(0L, parentId, false).build(true);
        return participantListService.addUserList(l);
    }
}