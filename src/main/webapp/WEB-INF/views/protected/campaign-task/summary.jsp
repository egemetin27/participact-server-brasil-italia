<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CampaignTaskCtrl" ng-init="initCampaignTasks();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/campaign-task/breadcrumb.jsp" />
		<!-- BEGIN DETAILS -->
		<div class="portlet-body">
		<div class="row">
			<!-- DETALHES -->
			<div class="col-md-12 col-sm-12" ng-controller="CampaignCtrl" ng-init="setCampaign(${id});">
				<div class="portlet blue-chambray box">
					<div class="portlet-title">
						<div class="caption qz-pointer" ng-click="collapseDetails(${id});"><i class="fa" ng-class="form.checkDetails?'fa-angle-down':'fa-angle-right'"></i>&nbsp;{{form.name | stripslashes}}</div>
						<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST')">
						<div class="actions">
						<c:if test="${isAdmin}">						
							<a href="<c:url value="/protected/campaign/edit/${id}"/>" class="btn yellow-saffron"> <i class="fa fa-pencil"></i>&nbsp;<spring:message code="edit.title" text="Edit" /></a>
							<a href="<c:url value="/protected/campaign-task/index/${id}"/>" class="btn green uppercase btn-lg"> <i class="fa fa-tasks"></i>&nbsp;<spring:message code="tasks.title" text="Tasks" /></a>
						</c:if>
						</div>
						</sec:authorize>
					</div>					
					<div class="portlet-body" ng-show="form.checkDetails">
						<div class="row static-info" ng-show="form.canBeRefused"><div class="col-md-12 name"><small class="help-block"><mark><spring:message code="campaign.refused.message" text="This task can be refused by users" /></mark></small></div></div>
						<div class="row static-info"><div class="col-md-12 name text-justify"><small class="help-block" ng-bind-html="form.description"></small></div></div>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
						<div class="row static-info" id="createdByAuthor">
							<div class="col-lg-2 col-md-3 name"><spring:message code="created.by.title" text="Created by" />&nbsp;:&nbsp;<i class="fa fa-user"></i>&nbsp;&nbsp;{{form.created_by||''}}</div>
						</div>
						</sec:authorize>
						<div class="row static-info">
							<div class="col-lg-2 col-md-3 name"><strong><spring:message code="start.title" text="Start" />&nbsp;:</strong><small class="help-block">{{form.dt_format_start}}</small></div>
							<div class="col-lg-2 col-md-3 name"><strong><spring:message code="end.title" text="End" />&nbsp;:</strong><small class="help-block">{{form.dt_format_end}}</small></div>
							<div class="col-lg-3 col-md-3 name pull-right font-blue">
								<strong><spring:message code="sensing.duration.title" text="Sensing Duration" />&nbsp;:</strong>
								<small class="help-block text-lowercase">{{time.sensingDuration.minutes}}<spring:message code="minutes.title" text="Minutes" />&nbsp;({{time.sensingDuration.d}}d&nbsp;{{time.sensingDuration.h}}h&nbsp;{{time.sensingDuration.m}}m)</small>
							</div>
							<div class="col-lg-3 col-md-3 name pull-right font-blue-chambray">
								<strong><spring:message code="task.duration.title" text="Task Duration" />&nbsp;:</strong>
								<small class="help-block text-lowercase">{{time.duration.minutes}}<spring:message code="minutes.title" text="Minutes" />&nbsp;({{time.duration.d}}d&nbsp;{{time.duration.h}}h&nbsp;{{time.duration.m}}m)</small>
							</div>	
						</div>
					</div>
				</div>
			</div>
		</div>
		</div>
		<!-- END DETAILS -->
		<!-- STATS -->
		<c:if test="${isPublish}"><jsp:include page="/WEB-INF/views/protected/campaign-task-statistics/index.jsp" /></c:if>
		<!-- REPORTS -->
		<c:if test="${isPublish}"><jsp:include page="/WEB-INF/views/protected/campaign-task-reports/index.jsp" /></c:if>
		</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />
