<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- BEGIN PAGE-->
<div class="row" ng-controller="AbuseReportCtrl" ng-init="form.issueId = '${form}'; initAbuseReport();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">		
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/abuse-report/breadcrumb.jsp" />
		<!-- BEGIN TABLE -->
		<div class="portlet-body" ng-show="abuses.length > '0'">
			<div class="table-scrollable">
				<table class="table table-striped table-bordered table-hover table-checkable" id="<%UUID.randomUUID().toString();%>">
					<thead>
						<tr role="row" class="heading">
							<th class="col-md-2 text-left"><spring:message code="who.title" text="Who" /></th>
							<th class="col-md-2 text-left"><spring:message code="type.title" text="Type" /></th>
							<th class="col-md-6 text-left"><spring:message code="abuse.title" text="Abuse" /></th>
							<th class="col-md-2 text-center"><spring:message code="action.title" text="Action" /></th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="(key, item) in abuses" id="abuses-tr-{{item[0]}}">
							<td class="text-left">{{item[8] | stripslashes}}<small class="help-block">{{item[2].millis | date:'dd/MM/yyyy HH:mm:ss'}}</small></td>
							<td class="text-left">{{item[6] | stripslashes}}</td>
							<td class="text-left">
								<textarea class="form-control" rows="2" cols="" ng-model="item[1]"></textarea>
							</td>
							<td class="text-left">
								<div class="btn-group btn-group-justified" role="group" aria-label="...">
								  <div class="btn-group" role="group">
								    <button type="button" class="btn green" ng-click="saveAbuseReport(key);"><i class="fa fa-save"></i>&nbsp;<spring:message code="save.title" text="save" /></button>
								  </div>
								  <div class="btn-group" role="group">
								    <button type="button" class="btn red" ng-click="removedAbuseReport(item[0]);"><i class="fa fa-trash"></i>&nbsp;<spring:message code="remove.title" text="Remove" /></button>
								  </div>
								</div>								
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- BEGIN PAGINATION -->
			<jsp:include page="/WEB-INF/templates/partial/paginationPerItem.jsp" />
			<!-- END PAGINATION -->		
		</div>
		<!-- END TABLE -->
		{{pages}}
		<!-- BEGIN DISPLAY -->
		<div class="portlet-body" ng-show="abuses.length == '0'" id="no-data-to-display">
		<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
		</div>
		<!-- END DISPLAY -->	
		</div>	
</div>
</div>
<!-- END PAGE-->