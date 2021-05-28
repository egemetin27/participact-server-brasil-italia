<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="portlet-title">
	<div class="caption"><span class="caption-subject font-green sbold uppercase"><spring:message code="details.title" text="Details" /></span></div>
	<c:if test="${breadcrumb eq '/protected/campaign-task/index'||breadcrumb eq '/protected/campaign-task/summary'}">
		<a href="<c:url value="/protected/campaign/index"/>" class="btn default col-xs-6 col-sm-4 col-md-2 col-lg-2 pull-right" id="fixgoback"><i class="icon-layers"></i>&nbsp;<spring:message code="protected.campaigns.title" text="Campaigns" /></a>
	</c:if>
	<c:if test="${breadcrumb eq '/protected/campaign-task-reports/user'}">
	<a href="<c:url value="/protected/campaign-task/summary/${taskId}"/>" class="btn default col-xs-6 col-sm-4 col-md-2 col-lg-2 pull-right" id="fixgoback"><i class="icon-action-undo"></i>&nbsp;<spring:message code="campaign.title" text="Campaign" /></a>
	</c:if>
</div>