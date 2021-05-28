<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CampaignTaskSensingCtrl" ng-cloak>
<span ng-init="getCampaignTaskSensing();"/>
<div class="col-md-12">
	<div class="portlet">
	<!-- BREADCRUMB -->
	<jsp:include page="/WEB-INF/views/protected/campaign-task-sensing/breadcrumb.jsp" />
	<!-- BEGIN FORM -->
	<div class="portlet-body form">
		<form class="form-horizontal" role="form" name="formCampaignTaskSensing">
		<input type="text" ng-show="false" ng-model="form.id" id="uuid" />
		<div class="form-body">
		<!-- BEGIN FORM BODY -->
		<!-- PIPELINE -->
		<div class="form-group" ng-class="formCampaignTaskSensing.pipelineType.$invalid?'text-danger':'text-success'">
			<label class="col-md-offset-2 col-md-7  animated flash"><spring:message code="pipeline.type.title" text="Pipeline Type" /></label>
		</div>						
		<div class="form-group" ng-class="formCampaignTaskSensing.pipelineType.$invalid?'has-error':'has-success'">
			<div class="col-md-offset-2 col-md-7"><jsp:include page="/WEB-INF/templates/partial/selectPipelineType.jsp" /></div>
		</div>			
		<!-- DURACAO -->
		<div class="form-group margin-top-40">
			<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="duration.title" text="Duration" /></label>
		</div>		
		<!-- DURATION -->
		<div class="form-group" ng-controller="DatePickerCtrl">	
		<div class="col-md-offset-2 col-md-5" >
			<div class="input-group input-medium">
				<input type="text" id="duration-picker-2" name="sensorDuration" ng-model="form.sensorDuration" value="${sensorDuration}" ng-init="form.sensorDuration=${sensorDuration}" />
			</div>
			<small class="help-block"><spring:message code="sensing.duration.title" text="Duration of the sensing activity" />&nbsp;&nbsp;<i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="<spring:message code="sensing.duration.help.message" text="Duration of the sensing activity in minutes. This time is shared by all passive sensing actions." />"></i></small>			
		</div>	
		</div>
		<!-- WEEKDAY -->
		<div class="form-group margin-top-40">
			<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="sensing.days.title" text="Sensing Days" /></label>
		</div>			
		<div class="form-group">				
			<div class="col-md-offset-2 col-md-5" >
			<div class="mt-checkbox-inline">
				<label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.sensorWeekSun" ng-init="form.sensorWeekSun=${sensorWeekSun}"><small class="text-justify"><spring:message code="weekday.sunday" text="Sunday" /></small><span></span></label>
				<label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.sensorWeekMon" ng-init="form.sensorWeekMon=${sensorWeekMon}"><small class="text-justify"><spring:message code="weekday.monday" text="Monday " /></small><span></span></label>
				<label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.sensorWeekTue" ng-init="form.sensorWeekTue=${sensorWeekTue}"><small class="text-justify"><spring:message code="weekday.tuesday" text="Tuesday " /></small><span></span></label>
				<label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.sensorWeekWed" ng-init="form.sensorWeekWed=${sensorWeekWed}"><small class="text-justify"><spring:message code="weekday.wednesday" text="Wednesday " /></small><span></span></label>
				<label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.sensorWeekThu" ng-init="form.sensorWeekThu=${sensorWeekThu}"><small class="text-justify"><spring:message code="weekday.thursday" text="Thursday " /></small><span></span></label>
				<label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.sensorWeekFri" ng-init="form.sensorWeekFri=${sensorWeekFri}"><small class="text-justify"><spring:message code="weekday.friday" text="Friday " /></small><span></span></label>
				<label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.sensorWeekSat" ng-init="form.sensorWeekSat=${sensorWeekSat}"><small class="text-justify"><spring:message code="weekday.saturday" text="Saturday " /></small><span></span></label>	
			</div>						
			</div>
		</div>		
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
			<button type="button" class="btn green col-xs-6 col-sm-4 col-md-offset-3 col-md-2 col-lg-2 margin-right-10" ng-click="saveCampaignTaskSensing(${campaign_id});" ng-disabled="formCampaignTaskSensing.$invalid"><spring:message code="save.title" text="Save" /></button>
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