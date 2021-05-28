<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="portlet-title">
	<div class="caption"><span class="caption-subject font-green sbold uppercase"><spring:message code="protected.campaigns.title" text="Campaigns" /></span></div>
	<c:if test="${breadcrumb eq '/protected/campaign/index'||breadcrumb eq '/protected/campaign/summary'}">
	<sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST') or hasRole('ROLE_COOPERATION_AGREEMENT') or hasRole('ROLE_RESEARCHER_OMBUDSMAN')">
	<div class="pull-right" name="breadcrumbForm">
		<a title="<spring:message code="new.title" text="New" />" href="<c:url value="/protected/campaign/form"/>" class="btn green" ng-class="(campaigns.length == '0')?'animated infinite flash':''"><i class="fa fa-plus"></i></a>
		<button title="<spring:message code="export.title" text="Export" />" type="button" class="btn blue-steel" ng-show="campaigns.length > 0" ng-disabled="selected_items == '0'" ng-class="selected_items != '0'?'animated bounce':'';" ng-click="initCloudExport();"><i class="fa fa-download"></i></button>
		<sec:authorize access="!hasRole('ROLE_COOPERATION_AGREEMENT')&&!hasRole('ROLE_RESEARCHER_OMBUDSMAN')">
			<button title="<spring:message code="remove.title" text="Remove" />" type="button" class="btn red" 		  ng-show="campaigns.length > 0" ng-disabled="selected_items == '0'" ng-class="selected_items != '0'?'animated bounce':'';" ng-click="removeSelected('<spring:message code="remove.title" text="Remove" />', '<spring:message code="label.negative.title" text="No" />', '<spring:message code="confirmation.title" text="Are you sure?" />', '<spring:message code="confirmation.remove.campaigns" text="You will remove the Campaigns" />', '<spring:message code="confirmation.required.select" text="Please, click inside the box and select the action. You can select one or more." />')"><i class="fa fa-trash"></i></button>
			<jsp:include page="/WEB-INF/templates/partial/uploadProgress.jsp" />
		</sec:authorize>
	</div>
	</sec:authorize>
	<c:if test="${isAdmin eq false and isResearcher eq false}">
	<sec:authorize access="hasRole('ROLE_RESEARCHER_SECOND') and !hasRole('ROLE_COOPERATION_AGREEMENT') and !hasRole('ROLE_RESEARCHER_OMBUDSMAN')">
	<div class="pull-right" name="breadcrumbForm">
	<button title="<spring:message code="export.title" text="Export" />" type="button" class="btn blue-steel" ng-show="campaigns.length > 0" ng-disabled="selected_items == '0'" ng-class="selected_items != '0'?'animated bounce':'';" ng-click="initCloudExport();"><i class="fa fa-download"></i></button>
	<jsp:include page="/WEB-INF/templates/partial/uploadProgress.jsp" />
	</div>
	</sec:authorize>
	</c:if>
	</c:if>
</div>