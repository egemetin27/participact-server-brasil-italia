<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div tabindex="-1" class="modal fade in" id="fullAPP_ON_SCREEN" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-full">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" aria-hidden="true" type="button" data-dismiss="modal"></button>
				<h4 class="modal-title"><jsp:include page="/WEB-INF/views/protected/campaign-task-sensing/caption-subject.jsp" /></h4>
			</div>
			<div class="modal-body" ng-controller="CampaignTaskSensingCtrl" ng-init="taskId=${taskId}; actionId=${actionId}; userId=${userId};">
			<!-- BEGIN CHART -->
			<div class="row">
			<div class="col-md-12">
				<highchart id="chart1" config="chartConfig" style="width:90%;"></highchart>
			</div>
			<div class="col-md-12">
			<select class="selectpicker form-control" multiple id="selectAPP_ON_SCREEN" data-live-search="true" ng-change="onChangeSensingSelect();" ng-model="form.hashmap" title="<spring:message code="filter.title" text="Filter" />">
				<option></option>
				<option ng-repeat="(lk, li) in listed" value="{{li}}">{{li}}</option>
			</select>
			</div>
			</div>
			<!-- END CHART -->
			<!-- BEGIN TABLE -->
			<section ng-show="records.length > '0'">
			<input type="hidden" id="HeyAPP_ON_SCREEN" value="0"/>
			<div class="table-scrollable">
				<table class="table table-striped table-bordered table-hover table-condensed" id="<%UUID.randomUUID().toString();%>">
					<thead>
						<tr role="row" class="heading">
							<th>#</th>
							<th class="col-md-1"><spring:message code="username.title" text="User" /></th>
							<th class="col-md-1 text-center"><spring:message code="timestamp.title" text="Timestamp" /></th>
							<th class="col-md-3 text-center"><spring:message code="app.name.title" text="App" /></th>
							<th class="col-md-3 text-center"><spring:message code="start.time.title" text="Start time" /></th>
							<th class="col-md-3 text-center"><spring:message code="end.time.title" text="End time" /></th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="(key_data, item_data) in records" id="records-tr-{{item[0]}}">
							<td>{{item_data[0]}}</td>
							<td>{{item_data[2]}}</td>
							<td class="text-center">{{item_data[3].millis | date:'dd/MM/yyyy HH:mm:ss'}}</td>
							<td class="text-center">{{item_data[4]}}</td>
							<td class="text-center">{{item_data[5].millis | date:'dd/MM/yyyy HH:mm:ss'}}</td>
							<td class="text-center">{{item_data[6].millis | date:'dd/MM/yyyy HH:mm:ss'}}</td>
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