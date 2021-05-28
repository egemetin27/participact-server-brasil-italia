<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="PushNotificationsCtrl" ng-init="cleanPushNotifications(); initPushNotifications();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<c:if test="${!isMail}"><jsp:include page="/WEB-INF/views/protected/push-notifications/breadcrumb.jsp" /></c:if>
		<c:if test="${isMail}"><jsp:include page="/WEB-INF/views/protected/mail-notifications/breadcrumb.jsp" /></c:if>
		<!-- BEGIN TABLE -->
		<div class="portlet-body" ng-show="pushies.length > '0'">
			<div class="table-scrollable">
				<table class="table table-striped table-bordered table-hover table-checkable" id="<%UUID.randomUUID().toString();%>">
					<thead>
						<tr role="row" class="heading">
							<th width="2%"><label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="group-checkable" ng-model="selectedAll" ng-click="checkAll()" /> <span></span></label></th>
							<th class="col-md-2 "><spring:message code="type.title" text="Type" /></th>
							<th class="col-md-2  text-center"><spring:message code="total.processed.title" text="total Processed " /></th>
							<th class="col-md-2  text-center"><spring:message code="total.submitted.title" text="Total Submitted" /></th>
							<th class="col-md-2  text-center"><spring:message code="total.failed.title" text="Total Failed" /></th>
							<th class="col-md-1  text-right"><small><spring:message code="created.title" text="Created" /></small></th>
							<th class="col-md-1  text-right"><small><spring:message code="updated.title" text="Update" /></small></th>
							<td class="col-md-2 text-center"><i class="fa fa-pie-chart" aria-hidden="true"></i></td>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="item in pushies" id="pushies-tr-{{item[0]}}">
							<td><input type="checkbox" class="group-checkable" ng-model="item.Selected" ng-change="isSelected('checkbox-{{item[0]}}')" id="checkbox-{{item[0]}}"/></td>
							<td class="qz-pointer" ng-click="detailes(item[0], item[12]);">
								<small>{{item[11] | stripslashes}}</small>
								<small class="help-block"><mark ng-show="!item[4]"><spring:message code="processing.title" text="Processing" /></mark></small>
								<small ng-show="!item[12]" class="label label-default"><i class="fa fa-pencil"></i>&nbsp;<spring:message code="draft.title" text="Draft" /></small>
							</td>
							<td class="qz-pointer text-center" ng-click="detailes(item[0], item[12]);">{{item[6]||0}}</td>
							<td class="qz-pointer text-center" ng-click="detailes(item[0], item[12]);">{{item[5]||0}}</td>
							<td class="qz-pointer text-center" ng-click="detailes(item[0], item[12]);">{{item[7]||0}}</td>
							<td class="qz-pointer text-right" ng-click="detailes(item[0], item[12]);"><small>{{item[8].millis | date:'dd/MM/yyyy HH:mm:ss'}}</small></td>
							<td class="qz-pointer text-right" ng-click="detailes(item[0], item[12]);"><small>{{item[9].millis | date:'dd/MM/yyyy HH:mm:ss'}}</small></td>	
							<td>
								<canvas id="pie" class="chart chart-pie" chart-colors="['#45b7cd', '#ff6384', '#ff8e72']" chart-data="[item[5],item[7]]" chart-labels="['<spring:message code="total.submitted.title" text="Total Submitted" />','<spring:message code="total.failed.title" text="Total Failed" />']" chart-options="{}" style="height: 50px;"></canvas> 							
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
		<!-- BEGIN DISPLAY -->
		<div class="portlet-body" ng-show="pushies.length == '0'" id="no-data-to-display">
		<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
		</div>
		<!-- END DISPLAY -->	
		</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />