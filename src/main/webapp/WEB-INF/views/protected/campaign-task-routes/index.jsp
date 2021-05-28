<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CampaignTaskGeoRoutesCtrl" ng-init="initCampaignTaskGeoRoutes();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/campaign-task-routes/breadcrumb.jsp" />
		<!-- BEGIN TABLE -->
		<div class="portlet-body form">
			<form class="form-horizontal" role="form" name="formCampaignTaskRoutes">
			<input type="text" ng-show="false" ng-model="form.id" id="uuid" />
			<input type="hidden" id="HeyUpdateTheRoutes"/>
			<div class="form-body">
			<!-- BEGIN FORM BODY -->
			<!-- TEXT -->
			<div class="form-group" ng-show="loadingbar">
				<div class="progress progress-striped">
				<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="{{progressbar}}" aria-valuemin="0" aria-valuemax="100" style="width: {{progressbar}}%">
					<span class="sr-only animated flash inifite" > {{progressbar}}% <spring:message code="loading.title" text="Loading ..." /> </span>
				</div>
				</div>
			</div>					
			<!--BEGIN MAP -->
			<div id="map-canvas" style="height: 300px;"></div>				
			<!-- END MAP -->
			<!-- LEGENDAS -->
			<!-- END FORM BODY -->			
			</div>
			</form>	
		</div>
		<!-- END TABLE -->
		<!-- BEGIN TAGS -->
		<div class="portlet-body margin-top-20" >
			<div class="row"><div class="col-md-12"><div class="pull-right">
				<ul class="list-inline"><li ng-repeat="le in legends" class="margin-top-10 "><span class="label label-info margin-top-20" style="background-color: #{{le.color}} !important">{{le.name}}&nbsp;</span></li></ul>
			</div></div></div>		
		</div>
		<!-- end TAGS -->
		</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />