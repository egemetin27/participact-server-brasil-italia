<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="DevicesCtrl" ng-init="initDevices();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/devices/breadcrumb.jsp" />
		<!-- FILTER -->
		<div class="portlet-body">
		<div class="row"><div class="col-md-12">
		<div class="pull-right">
			<form class="form-inline" role="form">
				<div class="form-group"><input type="text" class="form-control input-sm pa-filter col-md-2" placeholder="<spring:message code="brand.title" text="Brand" />" ng-model="form.brand"></div>
				<div class="form-group"><input type="text" class="form-control input-sm pa-filter col-md-2" placeholder="<spring:message code="model.title" text="Model" />" ng-model="form.model"></div>
				<div class="form-group"><input type="text" class="form-control input-sm pa-filter col-md-2" placeholder="<spring:message code="tags.title" text="Device" />" ng-model="form.tags"></div>
				<div class="form-group"><input type="text" class="form-control input-sm pa-filter col-md-2" placeholder="<spring:message code="manufacturer.title" text="Manufacturer" />" ng-model="form.manufacturer"></div>
				<div class="form-group"><button title="<spring:message code="search.title" text="Search" />" class="btn blue btn-sm" type="button" ng-click="initDevices();"><i class="fa fa-search"></i></button></div>
			</form>
			</div>
		</div></div>	
		</div>
		<!-- BEGIN TABLE -->
		<div class="portlet-body" ng-show="devices.length > '0'">
			<div class="table-scrollable">
				<table class="table table-striped table-bordered table-hover table-checkable" id="<%UUID.randomUUID().toString();%>">
					<thead>
						<tr role="row" class="heading">
							<th width="2%"><label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="group-checkable" ng-model="selectedAll" ng-click="checkAll()" /> <span></span></label></th>
							<th class="col-md-2"><spring:message code="brand.title" text="Brand" /></th>
							<th class="col-md-2"><spring:message code="model.title" text="Model" /></th>
							<th class="col-md-2"><spring:message code="manufacturer.title" text="Manufacturer" /></th>
							<th class="col-md-2"><spring:message code="tags.title" text="Tags" /></th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="item in devices" id="devices-tr-{{item[0]}}">
							<td><input type="checkbox" class="group-checkable" ng-model="item.Selected" ng-change="isSelected('checkbox-{{item[0]}}')" id="checkbox-{{item[0]}}"/></td>
							<td ng-click="editDevices(item[0]);" class="qz-pointer">{{item[1] | stripslashes}}</td>
							<td ng-click="editDevices(item[0]);" class="qz-pointer">{{item[2] | stripslashes}}</td>
							<td ng-click="editDevices(item[0]);" class="qz-pointer">{{item[3] | stripslashes}}</td>
							<td ng-click="editDevices(item[0]);" class="qz-pointer">{{item[4] | stripslashes}}</td>							
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
		<div class="portlet-body" ng-show="devices.length == '0'" id="no-data-to-display">
		<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
		</div>
		<!-- END DISPLAY -->	
		</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />