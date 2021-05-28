<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CampaignTaskQuestionnaireCtrl" ng-init="getCampaignTaskQuestionnaire(${campaign_id},${question_id});" ng-cloak>
<div class="col-md-12">
	<div class="portlet">
	<!-- BREADCRUMB -->
	<jsp:include page="/WEB-INF/views/protected/campaign-task-questionnaire/breadcrumb.jsp" />
	<!-- BEGIN FORM -->
	<div class="portlet-body form">
		<form class="form-horizontal" role="form" name="formCampaignTaskQuestionnaire">
		<input type="text" ng-show="false" ng-model="form.id" id="uuid" />
		<div class="form-body">
		<!-- BEGIN FORM BODY -->
			<div class="form-group" ng-class="formCampaignTaskQuestionnaire.title.$invalid?'has-error':'has-success'">
				<label class="col-lg-1 col-md-2 col-sm-12 col-xs-12 uppercase bold text-right"><spring:message code="name.title" text="Name" /></label>
				<div class="col-lg-6 col-md-5  col-sm-12 col-xs-12">
					<input type="text" class="form-control"  ng-model="form.title" name="title" maxlength="250" required="required"/>
				</div>

				<label class="col-lg-1 col-md-2 col-sm-12 col-xs-12 uppercase bold text-right"><spring:message code="repeat.title" text="Repeat" /></label>
				<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
					<input type="checkbox" class="" ng-model="form.isrepeat" name="isrepeat" />
					<span class="text-right uppercase bold" ng-show="form.isrepeat">&nbsp;&nbsp;<i class="fa fa-repeat" aria-hidden="true"></i>&nbsp;<spring:message code="yes.title" /></span>
					<span class="text-right uppercase bold" ng-hide="form.isrepeat">&nbsp;&nbsp;&nbsp;<spring:message code="no.title" /></span>
				</div>
		</div>			
		<!-- NOTES -->
		<div class="form-group">
			<label class="col-lg-1 col-md-2  col-sm-12 col-xs-12 uppercase bold text-right"><spring:message code="description.title" text="Description" /></label>
			<div class="col-lg-10 col-md-8  col-sm-12 col-xs-12">
				<textarea class="form-control" ng-model="form.description" rows="4"></textarea>
			</div>
		</div>		
		<!-- PERGUNTAS -->
		<hr/>
		<div class="form-group">
			<div class="caption"><span class="caption-subject font-green sbold uppercase"><i class="fa fa-sort-alpha-asc"></i>&nbsp;<spring:message code="question.title" text="Question(s)" /></span></div>
		</div>		
		<jsp:include page="/WEB-INF/views/protected/campaign-task-questionnaire/questions.jsp" />
		<!-- END FORM BODY -->
		<!-- BEGIN DEBUG -->
		<div class="col-md-12 margin-top-40">
			<i class="fa fa-bug font-grey pull-right qz-pointer" ng-click="showDebug=!showDebug"></i>
			<ul class="list-unstyled well" ng-show="showDebug">
				<li ng-repeat="(key, errors) in formCampaignTaskQuestionnaire.$error track by $index">
					<ol>
						<li ng-repeat="e in errors">{{ e.$name }}: <strong>{{ key }}</strong>.</li>
					</ol>
				</li>
			</ul>
		</div>
		<!-- END DEBUG -->
		<!-- BUTTON -->
		<div class="form-group margin-top-40 margin-bottom-10 col-md-9">		
			<button type="button" class="btn green col-xs-6 col-sm-4 col-md-offset-3 col-md-2 col-lg-2 margin-right-10" ng-click="saveCampaignTaskQuestionnaire(${campaign_id});" ng-disabled="formCampaignTaskQuestionnaire.$invalid"><spring:message code="save.title" text="Save" /></button>
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