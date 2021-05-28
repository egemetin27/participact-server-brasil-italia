<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CampaignTaskGeoDrawingCtrl" ng-init="getCampaignTaskGeoDrawing(${isNotification},${isActivation}, ${campaign_id});" ng-cloak>
<div class="col-md-12">
	<div class="portlet">
	<!-- BREADCRUMB -->
	<jsp:include page="/WEB-INF/views/protected/campaign-task-area-notification/breadcrumb.jsp" />
	<!-- BEGIN FORM -->
	<div class="portlet-body form">
		<form class="form-horizontal" role="form" name="formCampaignTaskGeoDrawing">
		<input type="text" ng-show="false" ng-model="form.id" id="uuid" />
		<input type="hidden" id="HeyUpdateTheList"/>
		<div class="form-body">
		<!-- BEGIN FORM BODY -->
		<!-- MAP -->
		<div class="form-group">
			<label class="col-md-offset-2 col-md-7"><spring:message code="notification.area.help.message" text="Draw notification area" /></label>
			<small class="col-md-offset-2 col-md-7 help-block"><spring:message code="notification.area.instructions.message" text="Whenever a user enters this area, the task will be notified to him and he will be able to accept or reject it (if the task can be rejected)."/></small>
		</div>					
		<!-- MAP -->
		<jsp:include page="/WEB-INF/templates/partial/drawingManager.jsp" />
		<!-- END FORM BODY -->
		<!-- BUTTON -->
		<div class="form-group margin-top-40 margin-bottom-10 col-md-9">		
			<button type="button" class="btn green col-xs-6 col-sm-4 col-md-offset-3 col-md-2 col-lg-2 margin-right-10" ng-click="saveCampaignTaskGeoDrawing(${campaign_id});" ng-disabled="all_overlays.length=='0'"><spring:message code="save.title" text="Save" /></button>
			<a href="<c:url value="/protected/campaign-task/index/${campaign_id}"/>" class="btn default col-xs-6 col-sm-4 col-md-2 col-lg-2 pull-right" id="fixgoback"><spring:message code="goback.title" text="Go Back" /></a>
		</div>			
		</div>
		</form>	
	</div>
	<!-- END FORM -->	
	</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />	