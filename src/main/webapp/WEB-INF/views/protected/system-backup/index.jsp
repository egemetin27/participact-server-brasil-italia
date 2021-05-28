<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="SystemBackupCtrl" ng-init="initSystemBackup();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/system-backup/breadcrumb.jsp" />
		<!-- BEGIN TABLE -->
		<div class="portlet-body" ng-show="system_backups.length > '0'">
			<div class="table-scrollable">
				<table class="table table-striped table-bordered table-hover table-checkable" id="<%UUID.randomUUID().toString();%>">
					<thead>
						<tr role="row" class="heading">
							<th width="2%"><label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="group-checkable" ng-model="selectedAll" ng-click="checkAll()" /> <span></span></label></th>
							<th class="col-md-4"><spring:message code="name.title" text="Name" /></th>
							<th class="col-md-2"><spring:message code="username.title" text="Username" /></th>
							<th class="col-md-2"><spring:message code="hostname.title" text="Server Host" /></th>
							<th class="col-md-2"><spring:message code="port.title" text="Server Port" /></th>
							<th class="col-md-2"><spring:message code="created.title" text="Created" /></th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="item in system_backups" id="system_backups-tr-{{item[0]}}">
							<td><input type="checkbox" class="group-checkable" ng-model="item.Selected" ng-change="isSelected('checkbox-{{item[0]}}')" id="checkbox-{{item[0]}}"/></td>
							<td ng-click="editSystemBackup(item[0]);" class="qz-pointer">{{item[1] | stripslashes}}</td>
							<td ng-click="editSystemBackup(item[0]);" class="qz-pointer">{{item[2] | stripslashes}}</td>
							<td ng-click="editSystemBackup(item[0]);" class="qz-pointer">{{item[3] | stripslashes}}</td>
							<td ng-click="editSystemBackup(item[0]);" class="qz-pointer">{{item[4] | stripslashes}}</td>
							<td ng-click="editSystemBackup(item[0]);" class="qz-pointer">{{item[5].millis | date:'dd/MM/yyyy HH:mm:ss'}}</td>
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
		<div class="portlet-body" ng-show="system_backups.length == '0'" id="no-data-to-display">
		<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
		</div>
		<!-- END DISPLAY -->	
		</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />