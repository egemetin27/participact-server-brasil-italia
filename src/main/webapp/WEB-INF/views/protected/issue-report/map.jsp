<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="IssueReportCtrl" ng-init="initIssueReport();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/issue-report/breadcrumb.jsp" />
		<!-- BEGIN MAP -->
		<div class="portlet-body">
			<div id="map-full-canvas">
				<ng-map zoom="13" center=" -27.586347, -48.502900" class="ng-map-full">
					<marker ng-repeat="(m_key, m_item) in organizations" position="{{m_item.position}}" draggable="true" icon="{{m_item.ev_icon}}" on-click="vm.click(m_item.id)" animation="DROP"></marker>
				</ng-map>
		</div>
		<!-- END MAP -->
		</div>	
		</div>
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />