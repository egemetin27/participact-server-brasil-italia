<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="PushNotificationsLogsCtrl" ng-init="pushNotificationsId=${id};initPushNotificationsLogs();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/push-notifications-logs/breadcrumb.jsp" />
		<!-- MESSAGE -->
		<div class="portlet-body">
			<blockquote><footer>${pushMessage}</footer></blockquote>			
		</div>
		<!-- BEGIN TABLE -->
		<div class="portlet-body" ng-show="pushies.length > '0'">
			<div class="table-scrollable">
				<table class="table table-striped table-bordered table-hover table-checkable" id="<%UUID.randomUUID().toString();%>">
					<thead>
						<tr role="row" class="heading">
							<th class="col-md-2 text-left"><spring:message code="name.title" text="Name" /></th>
							<th class="col-md-1">#</th>
							<th class="col-md-1 uppercase">multicast_id</th>
							<th class="col-md-1 uppercase">success</th>
							<th class="col-md-1 uppercase">failure</th>							
							<th class="col-md-2 uppercase">canonical_ids</th>
							
							<th class="col-md-1 uppercase">message_id</th>
							<th class="col-md-1 uppercase">registration_id</th>
							<th class="col-md-1 uppercase">error</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="item in pushies" id="pushies-tr-{{item[0]}}" ng-class="item[3]?'success':'danger'">
							<td class="text-left">
								<small class="help-block">{{item[8] | stripslashes}}</small>
								<small class="help-block">{{item[9] | stripslashes}}</small>
							</td>
							<td><small>{{item[0]||0}}</small></td>
							<td><small>{{item[1]||0}}</small></td>
							<td><small>{{item[3]||0}}</small></td>
							<td><small>{{item[4]||0}}</small></td>
							<td><small>{{item[2]||0}}</small></td>
							
							<td><small>{{item[6]}}</small></td>
							<td><small>{{item[7]}}</small></td>
							<td><small>{{item[5]}}</small></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- BEGIN PAGINATION -->
			<jsp:include page="/WEB-INF/templates/partial/paginationPerItem.jsp" />
			<!-- END PAGINATION -->
			<!-- INFO -->
			<ul class="list-unstyled small text-muted margin-top-40">
				<li><b class="uppercase">multicast_id</b>&nbsp;:&nbsp;Unique ID (number) identifying the multicast message.</li>
				<li><b class="uppercase">success</b>&nbsp;:&nbsp;Number of messages that were processed without an error.</li>
				<li><b class="uppercase">failure</b>&nbsp;:&nbsp;Number of messages that could not be processed.</li>
				<li><b class="uppercase">canonical_ids</b>&nbsp;:&nbsp;Number of results that contain a canonical registration token.</li>
				<li><b class="uppercase">message_id</b>&nbsp;:&nbsp;String specifying a unique ID for each successfully processed message.</li>
				<li><b class="uppercase">registration_id</b>&nbsp;:&nbsp;Optional string specifying the canonical registration token for the client app that the message was processed and sent to. </li>
				<li><b class="uppercase">error</b>&nbsp;:&nbsp;String specifying the error that occurred when processing the message for the recipient.</li>
				<li><a href="https://developers.google.com/cloud-messaging/http-server-ref" target="_blank">REFERENCE TABLE</a></li>
			</ul>	
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