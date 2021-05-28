<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CampaignTaskDetectionCtrl" ng-cloak>
<span ng-init="getCampaignTaskDetection();"/>
<div class="col-md-12">
	<div class="portlet">
	<!-- BREADCRUMB -->
	<jsp:include page="/WEB-INF/views/protected/campaign-task-detection/breadcrumb.jsp" />
	<!-- BEGIN FORM -->
	<div class="portlet-body form">
		<form class="form-horizontal" role="form" name="formCampaignTaskDetection">
		<input type="text" ng-show="false" ng-model="form.id" id="uuid" />
		<div class="form-body">
		<!-- BEGIN FORM BODY -->
		<!-- PIPELINE -->
		<!-- NOTES -->
		<div class="form-group" ng-class="formCampaignTaskDetection.durationThreshold.$invalid?'text-danger':'text-success'">
			<label class="col-md-offset-2 col-md-7"><spring:message code="duration.threshold.message" text="Threshold of the activity detection." /></label>
		</div>						
		<section ng-controller="DatePickerCtrl">
		<div class="form-group" ng-class="formCampaignTaskDetection.durationThreshold.$invalid?'has-error':'has-success'">
			<div class="col-md-offset-2 col-md-7">
			<div class="input-group input-medium">
				<input type="text" id="duration-picker-2" name="durationThreshold" ng-model="form.durationThreshold" required="required"/>
			</div>
			</div>
		</div>		
		</section>	
		<!-- NOTES -->
		<div class="form-group">
			<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="description.title" text="Description" /></label>
		</div>						
		<div class="form-group">
			<div class="col-md-offset-2 col-md-7">
				<textarea class="form-control" ng-model="form.description" rows="6"></textarea>
			</div>
		</div>		
		<!-- END FORM BODY -->
		<!-- BUTTON -->
		<div class="form-group margin-top-40 margin-bottom-10 col-md-9">		
			<button type="button" class="btn green col-xs-6 col-sm-4 col-md-offset-3 col-md-2 col-lg-2 margin-right-10" ng-click="saveCampaignTaskDetection(${campaign_id});" ng-disabled="formCampaignTaskDetection.$invalid"><spring:message code="save.title" text="Save" /></button>
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