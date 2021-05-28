package it.unibo.paserver.web.controller;

import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.*;
import it.unibo.paserver.web.security.v1.AccountAdminDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Herancao dos principais atributos usados nos controllers
 *
 * @author Claudio
 */
@Controller
public class ApplicationController {
    protected boolean isAdmin = false;
    protected boolean isResearcher = false;
    protected boolean isAgreement = false;
    protected boolean isOmbudsman = false;
    protected boolean isPublish = false;
    protected boolean isExpired = false;
    protected boolean isAssign = false;
    protected boolean hasQuestionnaire = false;
    protected long parentId = 0L;
    protected long userId = 0L;
    protected Task hasTask = null;
    protected String userMunicipality = null;
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected TaskReportService taskReportService;
    @Autowired
    protected CampaignService campaignService;
    @Autowired
    protected ParticipantService participantService;
    @Autowired
    protected ActionService actionService;
    @Autowired
    protected MessageSource messageSource;

    /**
     * Se eh admin
     *
     * @param request
     * @return
     */
    protected boolean isRoot(final HttpServletRequest request) {
        //Get
        AccountAdminDetails current = (AccountAdminDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userId = current.getId();
        userMunicipality = current.getMunicipality();
        // isAdmin
        isAdmin = request.isUserInRole("ROLE_ADMIN");
        isResearcher = request.isUserInRole("ROLE_RESEARCHER_FIRST");
        isAgreement = request.isUserInRole("ROLE_COOPERATION_AGREEMENT");
        isOmbudsman = request.isUserInRole("ROLE_RESEARCHER_OMBUDSMAN");
        if (isAdmin) {
            parentId = 0L;
        } else if (isAgreement) {
            parentId = userId;
        } else if (!isAdmin && isResearcher) {
            parentId = userId;
        } else if (!isAdmin && isOmbudsman) {
            parentId = userId;
        } else if (!isAdmin && !isResearcher) {
            parentId = current.getParentId();
        }

        return isAdmin;
    }

    /**
     * Checagem na primeira requisicao
     *
     * @param id
     * @return
     */
    protected boolean isCheck(long id) {
        // Search
        Task task = campaignService.findByIdAndParentId(id, parentId);
        if (task != null) {
            isPublish = task.isPublish();
            isExpired = task.isExpired();
            isAssign = task.isAssign();
            hasQuestionnaire = task.getHasQuestionnaire();
            hasTask = task;
        }
        return isPublish;
    }

    /**
     * Se contem uma especifica Action Sensing
     *
     * @param taskId
     * @param typeId
     * @return
     */
    protected boolean hasAction(long taskId, int typeId) {
        return actionService.getActionsCountAndNumeric(taskId, typeId) > 0;
    }

    /**
     * Usuario com informacoes simples
     *
     * @param user, usuario do sistema
     * @return
     */
    protected HashMap<String, Object> getSmallObject(User user) {
        HashMap<String, Object> map = new HashMap<>();
        if (user != null) {
            map.put("name", user.getName());
            map.put("secondaryemail", user.getSecondaryEmail());
            map.put("projectemail", user.getProjectEmail());
            map.put("officialemail", user.getOfficialEmail());
            map.put("progenitorid", user.getProgenitorId());
        }
        return map;
    }
}
