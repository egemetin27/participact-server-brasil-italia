<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CguCategoryCtrl" ng-init="initCguCategory();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/cgu-category/breadcrumb.jsp" />
		<!-- FILTER -->
		<div class="portlet-body">
			<div class="row"><div class="col-md-12">
				<div class="pull-right">
					<form class="form-inline" role="form">
						<div class="input-group">
							<input type="text" class="form-control pa-filter" placeholder="<spring:message code="search.title" text="search" />" ng-model="form.search"/>
							<span class="input-group-btn">
								<button title="<spring:message code="search.title" text="Search" />" class="btn blue " type="button" ng-click="initCguCategory();"><i class="fa fa-search"></i></button>
							</span>
						</div>
					</form>
				</div>
			</div></div>
		</div>
		<!-- BEGIN TABLE -->
		<div class="portlet-body" ng-show="categories.length > '0'">
			<div class="table-scrollable">
				<table class="table table-striped table-bordered table-hover table-checkable" id="<%UUID.randomUUID().toString();%>">
					<thead>
						<tr role="row" class="heading">
							<th width="2%"><label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="group-checkable" ng-model="selectedAll" ng-click="checkAll()" /> <span></span></label></th>
							<th class="col-md-10"><spring:message code="name.title" text="Name" /></th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="item in categories" id="categories-tr-{{item[0]}}">
							<td><input type="checkbox" class="group-checkable" ng-model="item.Selected" ng-change="isSelected('checkbox-item-{{item[0]}}')" id="checkbox-item-{{item[0]}}"/></td>
							<td ng-click="editCguCategory(item[0]);" class="qz-pointer">{{item[1] | stripslashes}}</td>
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
		<div class="portlet-body" ng-show="categories.length == '0'" id="no-data-to-display">
		<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
		</div>
		<!-- END DISPLAY -->	
		</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />