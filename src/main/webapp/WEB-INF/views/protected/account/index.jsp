<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="UserSystemCtrl" ng-init="initUserSystem();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/account/breadcrumb.jsp" />
		<!-- FILTER -->
		<div class="portlet-body">
		<div class="row"><div class="col-md-12">
		<div class="pull-right">
			<form class="form-inline" role="form">
				<div class="form-group"><input type="text" class="form-control input-sm pa-filter col-md-2" placeholder="<spring:message code="protected.account.name" text="Name" />" ng-model="form.name"></div>
				<div class="form-group"><input type="text" class="form-control input-sm pa-filter col-md-2" placeholder="<spring:message code="protected.account.username" text="Username" />" ng-model="form.username"></div>
				<div class="form-group"><input type="text" class="form-control input-sm pa-filter col-md-2" placeholder="<spring:message code="protected.account.email" text="Email" />" ng-model="form.email"></div>
				<div class="form-group"><input type="text" class="form-control input-sm pa-filter col-md-2" placeholder="<spring:message code="protected.account.phone" text="Phone" />" ng-model="form.phone"></div>
				<div class="form-group"><button title="<spring:message code="search.title" text="Search" />" class="btn blue btn-sm" type="button" ng-click="initUserSystem();"><i class="fa fa-search"></i></button></div>
			</form>
			</div>
		</div></div>	
		</div>
		<!-- BEGIN TABLE -->
		<div class="portlet-body" ng-show="system_users.length > '0'">
			<div class="table-scrollable">
				<table class="table table-striped table-bordered table-hover table-checkable" id="<%UUID.randomUUID().toString();%>">
					<thead>
						<tr role="row" class="heading">
							<th width="2%"><label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="group-checkable" ng-model="selectedAll" ng-click="checkAll()" /> <span></span></label></th>
							<th class="col-md-3"><spring:message code="protected.account.name" text="Name" /></th>
							<th class="col-md-2"><spring:message code="protected.account.username" text="Username" /></th>
							<th class="col-md-2"><spring:message code="protected.account.email" text="Email" /></th>
							<th class="col-md-2"><spring:message code="protected.account.phone" text="Phone" /></th>
							<c:if test="${breadcrumb eq '/protected/researcher/index'}"><th class="col-md-2"><spring:message code="roles.title" text="User Type" /></th></c:if>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="item in system_users" id="system_users-tr-{{item[0]}}">
							<td><input type="checkbox" class="group-checkable" ng-model="item.Selected" ng-change="isSelected('checkbox-{{item[0]}}')" id="checkbox-{{item[0]}}"/></td>
							<td ng-click="editUserSystem(item[0]);" class="qz-pointer">{{item[1] | stripslashes}}
							<small class="help-block">{{item[8] | stripslashes}}</small></td>
							<td ng-click="editUserSystem(item[0]);" class="qz-pointer">{{item[5] | stripslashes}}</td>
							<td ng-click="editUserSystem(item[0]);" class="qz-pointer">{{item[2] | stripslashes}}</td>
							<td ng-click="editUserSystem(item[0]);" class="qz-pointer">{{item[3] | stripslashes}}</td>
							<c:if test="${breadcrumb eq '/protected/researcher/index'}">
								<td class="col-md-2">
									<span ng-show="item[6]==4"><spring:message code="roles.researcher.second" text="Researcher Second"/></span>
									<span ng-show="item[6]==3"><spring:message code="roles.researcher.first" text="Researcher First"/></span>
									<span ng-show="item[6]==6"><spring:message code="roles.researcher.agreement" text="Researcher - Partner (Cooperation Agreement)" /></span>
									<span ng-show="item[6]==7"><spring:message code="roles.researcher.admin" text="Researcher - Admin/Designer" /></span>

									<span ng-show="item[6]==8"><spring:message code="roles.researcher.consultant" text="Researcher - Consultant" /></span>
									<span ng-show="item[6]==9"><spring:message code="roles.researcher.collaborator" text="Researcher - Collaborator" /></span>
									<span ng-show="item[6]==10"><spring:message code="roles.researcher.editor" text="Researcher - Editor" /></span>
								</td>
							</c:if>
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
		<div class="portlet-body" ng-show="system_users.length == '0'" id="no-data-to-display">
		<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
		</div>
		<!-- END DISPLAY -->	
		</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />