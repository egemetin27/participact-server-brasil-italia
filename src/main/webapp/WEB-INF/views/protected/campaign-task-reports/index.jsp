<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- BEGIN PAGE TITLE-->
<div class="row" ng-controller="CampaignTaskStatsCtrl" ng-init="getTaskReports(${id});" ng-cloak>
<!-- BEGIN FORM -->
	<div class="portlet-body">
	<!-- BEGIN FORM BODY -->
     <div class="portlet">
	<!-- SEARCH -->	     

	<!-- BEGIN TABLE -->
	<div class="portlet-body" ng-show="reports.length > '0'&&!assign.isSelectAll">
		<c:if test="${hasQuestionnaire}">
			<a ng-repeat="(k,i) in campaignTasks.actions | filter:{ type:'QUESTIONNAIRE'}" href="<c:url value="/protected/campaign-task-questionnaire-chart/index/${id}/{{i.id}}"/>" class="btn blue col-md-2 pull-right margin-bottom-10"><i class="fa fa-list-alt" aria-hidden="true"></i>&nbsp;<small>{{i.name.substr(0, 15)}} ... </small></a>
		</c:if>
		<button type="button" ng-click="getTaskRoutes(${id});" class="btn blue col-md-2 pull-right margin-bottom-10" ng-class="(selected_ids.length > 0)?'animated flash':'';" ng-disabled="(selected_ids.length > 0) ? false: true;"><i class="fa fa-map-pin"></i> &nbsp;<spring:message code="view.routes.title" text="View Routes" />&nbsp;&nbsp;<span class="badge badge-danger"> {{selected_ids.length || 0}} </span></button>
		<div class="table-scrollable">			
			<table class="table table-striped table-bordered table-hover table-condensed" id="<%UUID.randomUUID().toString();%>">
				<thead>
					<tr role="row" class="heading">
						<c:if test="${hasLocation}"><th class="text-center" width="1%"><input type="checkbox" ng-model="selectedAll" ng-click="checkAll()" /></th></c:if>
						<th class="col-md-4"><spring:message code="name.title" text="Name" /></th>
						<th class="col-md-2 text-center qz-pointer" ng-class="orderByColumn=='Status'?'font-blue':''" ng-click="setOrderByTask('Status',${id});"><spring:message code="current.state.title" text="Current State" /><i class="fa pull-right" ng-class="orderByDesc?'fa-sort-desc':'fa-sort-asc';"></i></th>
						<th class="col-md-2 text-center"><spring:message code="accpepted.date.title" text="Accepted datetime" /></th>
						<th class="col-md-2 text-center"><spring:message code="expiration.date.title" text="Exipration datetime" /></th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="item in reports" id="reports-tr-{{item[0]}}">
						<c:if test="${hasLocation}"><td class="text-center"><input type="checkbox" ng-disabled="!item[8]" ng-model="item.Selected"  ng-change="isSelected('checkbox-{{item[1]}}', item[1])" id="checkbox-{{item[1]}}" /></td></c:if>
						<td ng-click="gotoTaskDetails(${id},item[1]);" class="qz-pointer">
							<span ng-class="item[14]?'text-muted':'text-info bold'">{{item[2] | stripslashes}}&nbsp;{{item[3] | stripslashes}}</span><small class="help-block">{{item[4] | stripslashes}}</small>
						</td>
						<td ng-click="gotoTaskDetails(${id},item[1]);" class="qz-pointer text-center"><span class="bold theme-font">{{item[5]}}</span><small class="help-block">{{item[9]|stripslashes}}</small></td>
						<td ng-click="gotoTaskDetails(${id},item[1]);" class="qz-pointer text-center">{{item[6].millis | date:'dd/MM/yyyy HH:mm:ss'}}</td>
						<td ng-click="gotoTaskDetails(${id},item[1]);" class="qz-pointer text-center">{{item[7].millis | date:'dd/MM/yyyy HH:mm:ss'}}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- BEGIN PAGINATION -->
		<jsp:include page="/WEB-INF/templates/partial/paginationPerItem.jsp" />
		<!-- END PAGINATION -->		
	</div>
	<!-- END TABLE -->
    </div>
	</div>
	<!-- END FORM -->
</div>
<!-- END PAGE TITLE-->
