<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div tabindex="-1" class="modal fade in" id="fullLOCATION" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-full">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" aria-hidden="true" type="button" data-dismiss="modal"></button>
				<h4 class="modal-title"><jsp:include page="/WEB-INF/views/protected/campaign-task-sensing/caption-subject.jsp" /></h4>
			</div>
			<div class="modal-body" ng-controller="CampaignTaskSensingCtrl" ng-init="taskId=${taskId}; actionId=${actionId}; userId=${userId}; setSelectedItem(['${userId}',${user.id},'${user.name}','${user.surname}','${user.officialEmail}']);">
			<!-- BEGIN MAP -->
			<div id="map-canvas" style="height: 300px;"></div>
			<button type="button" ng-click="getTaskRoutes(${taskId},${userId});" class="btn btn-sm blue-soft pull-right margin-bottom-10"><i class="fa fa-map-pin"></i> &nbsp;<spring:message code="view.routes.title" text="View Routes" />&nbsp;&nbsp;</button>
			<button type="button" class="btn btn-sm blue pull-right" ng-click="fullScreenClick();"><i class="fa" ng-class="fullscreen?'fa-toggle-off':'fa-toggle-on'"></i>&nbsp;&nbsp;FULL SCREEN&nbsp;&nbsp;</button>					
			<!-- END MAP -->
			<!-- BEGIN TABLE -->
			<section ng-show="records.length > '0'">
			<input type="hidden" id="HeyLOCATION" value="0"/>
			<div class="table-scrollable">
				<table class="table table-striped table-bordered table-hover table-condensed" id="<%UUID.randomUUID().toString();%>">
					<thead>
						<tr role="row" class="heading">
							<th>#</th>
							<th class="col-md-1"><spring:message code="username.title" text="User" /></th>
							<th class="col-md-1 text-center"><spring:message code="timestamp.title" text="Timestamp" /></th>
							<th class="col-md-3 text-center">Latitude</th>
							<th class="col-md-3 text-center">Longitude</th>
							<th class="col-md-3 text-center">Accuracy</th>
							<th class="col-md-3 text-center">Provider</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="(key_data, item_data) in records" id="records-tr-{{item[0]}}">
							<td>{{item_data[0]}}</td>
							<td>{{item_data[2]}}</td>
							<td class="text-center">{{item_data[3].millis | date:'dd/MM/yyyy HH:mm:ss'}}</td>
							<td class="text-center">{{item_data[4]}}</td>
							<td class="text-center">{{item_data[5]}}</td>
							<td class="text-center">{{item_data[6]}}</td>
							<td class="text-center">{{item_data[7]}}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- BEGIN PAGINATION -->
			<jsp:include page="/WEB-INF/templates/partial/paginationPerData.jsp" />
			<!-- END PAGINATION -->		
			</section>	
			<!-- END TABLE -->	
			<!-- BEGIN DISPLAY -->
			<div class="portlet-body" ng-show="records.length == '0'" id="no-data-to-display">
			<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
			</div>
			<!-- END DISPLAY -->	
			</div>
			<div class="modal-footer">
				<button class="btn dark btn-outline" type="button" data-dismiss="modal"><spring:message code="close.title" text="Close" /></button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>