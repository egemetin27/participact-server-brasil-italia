<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="portlet-title">
	<div class="caption"><span class="caption-subject font-green sbold uppercase"><spring:message code="issues.title" text="Issues" /></span></div>
	<c:if test="${breadcrumb eq '/protected/issue-report/index'}">
	<div class="pull-right">
		<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR', 'ROLE_RESEARCHER_OMBUDSMAN_CONSULTANT', 'ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR')">
		<!-- MAP -->
		<a title="<spring:message code="map.title" text="Map" />" href="<c:url value="/protected/issue-report/map"/>" class="btn green" ng-class="(abuses.length == '0')?'animated infinite flash':''"><i class="fa fa-map"></i>&nbsp;<spring:message code="map.title" text="Map" />&nbsp;</a>
		<button title="<spring:message code="export.title" text="Export" />" type="button" class="btn blue-steel" ng-click="initIssueListExport();"><i class="fa fa-download"></i></button>&nbsp;&nbsp;
		</sec:authorize>
		<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
		<!-- ADD -->
		<a title="<spring:message code="new.title" text="New" />" href="<c:url value="/protected/issue-report/form"/>" class="btn green" ng-class="(items.length == '0')?'animated infinite flash':''"><i class="fa fa-plus"></i></a>
		</sec:authorize>
		<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')">
		<!-- REMOVE -->
		<button title="<spring:message code="remove.title" text="Remove" />" type="button" class="btn red" ng-show="issues.length > 0" ng-disabled="selected_items == '0'" ng-class="selected_items != '0'?'animated bounce':'';" ng-click="removeSelected('<spring:message code="remove.title" text="Remove" />', '<spring:message code="label.negative.title" text="No" />', '<spring:message code="confirmation.title" text="Are you sure?" />', '<spring:message code="confirmation.remove.issues" text="You will remove the Issues" />', '<spring:message code="confirmation.required.select" text="Please, click inside the box and select the action. You can select one or more." />')"><i class="fa fa-trash"></i></button>
		</sec:authorize>
	</div>
	</c:if>

	<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
	<c:if test="${breadcrumb eq '/protected/issue-report/map'}">
	<div class="pull-right">
		<a title="<spring:message code="list.title" text="List" />" href="<c:url value="/protected/issue-report/index"/>" class="btn green btn-block" ng-class="(abuses.length == '0')?'animated infinite flash':''"><i class="fa fa-th"></i>&nbsp;<spring:message code="list.title" text="list" />&nbsp;</a>
	</div>
	</c:if>
	</sec:authorize>
</div>