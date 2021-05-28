<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="portlet-title">
	<div class="caption"><span class="caption-subject font-green sbold uppercase"><i class="fa fa-map-pin"></i>&nbsp;&nbsp;<spring:message code="directions.routes.title" text="Directions and Routes" /></span></div>
	<a href="<c:url value="/protected/campaign-task/index/${taskId}"/>" class="btn default col-xs-6 col-sm-4 col-md-2 col-lg-2 pull-right" id="fixgoback"><i class="icon-action-undo"></i>&nbsp;<spring:message code="campaign.title" text="Campaign" /></a>
	<c:if test="${userId > 0}">
		<a href="<c:url value="/protected/campaign-task-reports/user/${taskId}/${userId}"/>" class="btn blue col-xs-6 col-sm-4 col-md-2 col-lg-2 pull-right margin-left-10 margin-right-10"><i class="icon-user"></i>&nbsp;<spring:message code="participant.title" text="Participant" /></a>
	</c:if>
</div>