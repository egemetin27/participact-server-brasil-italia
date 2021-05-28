<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CampaignTaskCtrl" ng-init="initCampaignTasks();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/campaign-task/breadcrumb.jsp" />
		<!-- BEGIN DETAILS -->
		<div class="portlet-body">
		<div class="row">
			<!-- DETALHES -->
			<div class="col-md-12 col-sm-12" ng-controller="CampaignCtrl" ng-init="setCampaign(${id});">
				<div class="portlet green-dark box">
					<div class="portlet-title">
						<div class="caption qz-pointer" ng-click="collapseDetails(${id});"><i class="fa" ng-class="form.checkDetails?'fa-angle-down':'fa-angle-right'"></i>&nbsp;{{form.name | stripslashes}}</div>
						<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')">
						<div class="actions">						
							<a href="<c:url value="/protected/campaign/edit/${id}"/>" class="btn yellow-saffron"> <i class="fa fa-pencil"></i>&nbsp;<spring:message code="edit.title" text="Edit" /></a>
						<c:if test="${!isPublish}">	
							<c:if test="${!isExpired&&isAssign}"><button ng-show="campaignTasks.hasActions" class="btn purple-seance animated flash" ng-click="beginPublishingById(${id});"><i class="fa fa-send"></i>&nbsp;<spring:message code="publish.title" text="Publish" /></button></c:if>
						</c:if>
						<c:if test="${isPublish}">
							<a href="<c:url value="/protected/campaign-task/summary/${id}"/>" class="btn uppercase blue-chambray"> <i class="fa fa-newspaper-o" aria-hidden="true"></i>&nbsp;<spring:message code="summary.title" text="Summary" /></a>	
						</c:if>
						<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST')">
						<c:if test="${isPublish&&!isExpired&&isAssign}">
							<a href="<c:url value="/protected/campaign-task-resend/index/${id}"/>" class="btn uppercase blue"><i class="fa fa-envelope"></i>&nbsp;<spring:message code="resend.email.title" text="Resend Email" /></a>
						</c:if>
						</sec:authorize>
						</div>
						</sec:authorize>
					</div>					
					<div class="portlet-body" ng-show="form.checkDetails">
						<div class="row static-info" ng-show="form.canBeRefused"><div class="col-md-12 name"><small class="help-block"><mark><spring:message code="campaign.refused.message" text="This task can be refused by users" /></mark></small></div></div>
						<div class="row static-info"><div class="col-md-12 name text-justify"><small class="help-block" ng-bind-html="form.description"></small></div></div>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
						<div class="row static-info" id="createdByAuthor">
							<div class="col-lg-2 col-md-3 name"><spring:message code="created.by.title" text="Created by" />&nbsp;:&nbsp;<i class="fa fa-user"></i>&nbsp;&nbsp;{{form.created_by||''}}</div>
						</div>
						</sec:authorize>
						<div class="row static-info">
							<div class="col-lg-2 col-md-3 name"><strong><spring:message code="start.title" text="Start" />&nbsp;:</strong><small class="help-block">{{form.dt_format_start}}</small></div>
							<div class="col-lg-2 col-md-3 name"><strong><spring:message code="end.title" text="End" />&nbsp;:</strong><small class="help-block">{{form.dt_format_end}}</small></div>
							<div class="col-lg-3 col-md-3 name pull-right font-blue">
								<strong><spring:message code="sensing.duration.title" text="Sensing Duration" />&nbsp;:</strong>
								<small class="help-block text-lowercase">{{time.sensingDuration.minutes}}<spring:message code="minutes.title" text="Minutes" />&nbsp;({{time.sensingDuration.d}}d&nbsp;{{time.sensingDuration.h}}h&nbsp;{{time.sensingDuration.m}}m)</small>
							</div>
							<div class="col-lg-3 col-md-3 name pull-right font-blue-chambray">
								<strong><spring:message code="task.duration.title" text="Task Duration" />&nbsp;:</strong>
								<small class="help-block text-lowercase">{{time.duration.minutes}}<spring:message code="minutes.title" text="Minutes" />&nbsp;({{time.duration.d}}d&nbsp;{{time.duration.h}}h&nbsp;{{time.duration.m}}m)</small>
							</div>
							
							<div class="col-lg-12 col-md-12 name pull-right font-blue-chambray" ng-cloak>
								<p>&nbsp;</p>
								<ul class="list-inline">
									<li ng-show="form.sensingWeekSun" class="label-info label"><spring:message code="weekday.sunday" text="Sunday" />&nbsp;</li>
									<li ng-show="form.sensingWeekMon" class="label-info label"><spring:message code="weekday.monday" text="Monday" />&nbsp;</li>
									<li ng-show="form.sensingWeekTue" class="label-info label"><spring:message code="weekday.tuesday" text="Tuesday"/>&nbsp;</li>
									<li ng-show="form.sensingWeekWed" class="label-info label"><spring:message code="weekday.wednesday" text="Wednesday"/>&nbsp;</li>
									<li ng-show="form.sensingWeekThu" class="label-info label"><spring:message code="weekday.thursday" text="Thursday" />&nbsp;</li>
									<li ng-show="form.sensingWeekFri" class="label-info label"><spring:message code="weekday.friday" text="Friday" />&nbsp;</li>
									<li ng-show="form.sensingWeekSat" class="label-info label"><spring:message code="weekday.saturday" text="Saturday"/>&nbsp;</li>
								</ul>
							</div>	
						</div>
						<c:if test="${isPublish&&!isAdmin}">
							<div class="row static-info"><div class="col-md-12 name bold"><p><h3><spring:message code="tasks.title" text="Tasks" /></h3></p></div></div>
							<div class="row static-info" id="compaign-task-public-notadmin"><div class="col-md-12 name">
							<ol>
								<li><i class="fa fa-users"></i>&nbsp;<spring:message code="participants.title" text="Participants" /></li>
								<li ng-class="i.type" ng-repeat="(k,i) in campaignTasks.actions | orderBy:'type'" id="campaign-task-{{i.id}}">
									<h5 class="block">{{i.translated}}&nbsp;<span ng-show="campaignTasks.hasPhotos" class="text-lowercase bold badge">{{i.numeric_threshold}}</span></h5>
									<small ng-show="i.hasDuration" class="help-block text-lowercase">{{i.duration.minutes}}<spring:message code="minutes.title" text="Minutes" />&nbsp;({{i.duration.d}}d&nbsp;{{i.duration.h}}h&nbsp;{{i.duration.m}}m)</small>
									<small>{{i.name}}</small>
									<section ng-show="i.type == 'QUESTIONNAIRE'"><hr/>
									<small ng-repeat="(q_k, q_i) in i.questions">{{q_k+1}} . {{q_i.question}}<br/>
										<small class="help-block" ng-repeat="(qa_k, qa_i) in q_i.closed_answers | orderBy:'answerOrder'" ng-show="q_i.isClosedAnswers||q_i.isMultipleAnswers">{{qa_i.answerDescription}}</small>
									</small>
									</section>
								</li>
								<li ng-show="campaignTasks.hasNotificationArea"><i class="fa fa-map"></i>&nbsp;<spring:message code="notification.area.title" text="Notification Area"/>
								 	<section ng-controller="CampaignTaskGeoDrawingCtrl" ng-init="createStaticMap(true, false, ${id}, 'mapNotificationArea');"><div id="mapNotificationArea"></div></section>
								</li>
								<li ng-show="campaignTasks.hasActivationArea"><i class="fa fa-map-marker"></i>&nbsp;<spring:message code="activation.area.title" text="Activation Area"/>
									<section ng-controller="CampaignTaskGeoDrawingCtrl"  ng-init="createStaticMap(false, true, ${id}, 'mapActivationArea');"><div id="mapActivationArea"></div></section>
								</li>
							</ol>
							</div></div>
						</c:if>
					</div>
				</div>
				<c:if test="${!isPublish&&!isExpired&&isAssign}">
				<!-- MODAL PUBLISH -->
				<jsp:include page="/WEB-INF/templates/partial/confirmPublish.jsp" />
				</c:if>
			</div>
			<c:if test="${!isPublish||isAdmin}">
			<!-- BUTTONS -->
			<div class="col-md-12 col-sm-12 animated bounce">
			<div class="portlet">
			<div class="portlet-title"><div class="caption"><span class="caption-subject font-green uppercase"><i class="fa fa-plus"></i>&nbsp;<spring:message code="add.action.caption" text="Add actions to campaign" /></span></div></div>
			<div class="portlet-body">
			<div class="btn-group btn-group-justified" role="group">
				<sec:authorize access="!hasAnyRole('ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')">
			  <div class="btn-group" role="group" title="<spring:message code="passive.sensing.title" text="Passive Sensing" />"><button type="button" class="btn uppercase green-seagreen border-left-sold-1" ng-click="gotoCampaignTaskForm('sensing',${id});"><i class="fa fa-wifi"></i><small class="hidden-xs hidden-sm ">&nbsp;<spring:message code="passive.sensing.title" text="Passive Sensing" /></small></button></div>
			  <!-- <div class="btn-group" role="group" title="<spring:message code="activity.detection.title" text="Activity Detection" />"><button type="button" class="btn uppercase green-seagreen border-left-sold-1" ng-click="gotoCampaignTaskForm('detection',${id});" ng-disabled="campaignTasks.hasActivityDetection"><i class="fa fa-magic"></i>&nbsp;<small class="hidden-xs hidden-sm "><spring:message code="activity.detection.title" text="Activity Detection" /></small></button></div> -->
			  <div class="btn-group" role="group" title="<spring:message code="photo.title" text="Photo"/>"><button type="button" class="btn uppercase green-seagreen border-left-sold-1" ng-click="gotoCampaignTaskForm('photo',${id});" ng-disabled="campaignTasks.hasPhotos"><i class="fa fa-camera"></i>&nbsp;<small class="hidden-xs hidden-sm "><spring:message code="photo.title" text="Photo"/></small></button></div>
				</sec:authorize>
			  <div class="btn-group" role="group" title="<spring:message code="questionnaire.title" text="Questionnaire"/>"><button type="button" class="btn uppercase green-seagreen border-left-sold-1" ng-click="gotoCampaignTaskForm('questionnaire',${id});"><i class="fa fa-list-ul"></i>&nbsp;<small class="hidden-xs hidden-sm "><spring:message code="questionnaire.title" text="Questionnaire"/></small></button></div>
				<sec:authorize access="!hasAnyRole('ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')">
			  <div class="btn-group" role="group" title="<spring:message code="notification.area.title" text="Notification Area"/>"><button type="button" class="btn uppercase green-seagreen border-left-sold-1" ng-click="gotoCampaignTaskForm('area-notification',${id});"><i class="fa fa-map"></i>&nbsp;<small class="hidden-xs hidden-sm "><spring:message code="notification.area.title" text="Notification Area"/></small></button></div>
			  <div class="btn-group" role="group" title="<spring:message code="activation.area.title" text="Activation Area"/>"><button type="button" class="btn uppercase green-seagreen border-left-sold-1" ng-click="gotoCampaignTaskForm('area-activation',${id});"><i class="fa fa-map-marker"></i>&nbsp;<small class="hidden-xs hidden-sm "><spring:message code="activation.area.title" text="Activation Area"/></small></button></div>
				</sec:authorize>
			  <div class="btn-group" role="group" title="<spring:message code="participants.add.title" text="Add Participants" />"><a href="<c:url value="/protected/participant-list/task/${id}"/>" class="btn uppercase green border-left-sold-1"><i class="fa fa-users"></i>&nbsp;<small class="hidden-xs hidden-sm "><spring:message code="participants.add.title" text="Add Participants" /></small></a></div>
			</div> 
			</div>
			</div>
			</div>
			</c:if>
		</div>
		</div>
		<!-- END DETAILS -->
		<c:if test="${!isPublish||isAdmin||isAgreement}">
		<!-- BEGIN TABLE -->
		<div class="portlet-body" ng-show="campaignTasks.hasActions">
			<c:if test="${!isPublish}">
			<!-- USERS -->
			<div ng-show="campaignTasks.hasAssign" class="note note-default ASSIGN" id="campaign-task-assign">
				<h5 class="block uppercase"><i class="fa fa-users"></i>&nbsp;<spring:message code="participants.title" text="Participants" />
				<button title="<spring:message code="remove.title" text="Remove" />" type="button" class="btn red btn-sm pull-right border-left-sold-1" ng-click="removeSelectedAssign(${id})"><i class="fa fa-trash"></i></button>
				<a href="<c:url value="/protected/participant-list/task/${id}"/>" class="btn yellow-crusta btn-sm pull-right border-left-sold-1"><i class="fa fa-pencil"></i>&nbsp;<spring:message code="edit.title" text="Edit" /></a>					
				</h5>
				<h5 ng-show="campaignTasks.selectAll" class="uppercase bold font-green"><span class="label label-primary">{{campaignTasks.assignAvailable}}</span>&nbsp;&nbsp;<spring:message code="selected.users.title" text="Selected all active users." /></h5>
				<ul ng-hide="campaignTasks.selectAll" class="list-inline"><li class="label label-primary small">{{campaignTasks.assignSelected}}</li><li ng-repeat="(a_key,a_item ) in campaignTasks.assignFilter" class="label label-info border-left-sold-1">{{a_item.value}}</li></ul>
			</div>
			</c:if>			
			<!-- SENSOR -->
			<div class="note note-info" ng-class="i.type" ng-repeat="(k,i) in campaignTasks.actions | orderBy:'type'" id="campaign-task-{{i.id}}">
				<h5 class="block">{{i.translated}}&nbsp;<span ng-show="campaignTasks.hasPhotos" class="text-lowercase bold badge">{{i.numeric_threshold}}</span><button title="<spring:message code="remove.title" text="Remove" />" type="button" class="btn red pull-right btn-sm" ng-click="removeSelected(i.id,'<spring:message code="remove.title" text="Remove" />', '<spring:message code="label.negative.title" text="No" />', '<spring:message code="confirmation.title" text="Are you sure?" />', '<spring:message code="confirmation.remove.tasks" text="You will remove the Tasks" />', '')"><i class="fa fa-trash"></i></button>
				<a ng-show="i.type == 'QUESTIONNAIRE'" href="<c:url value="/protected/campaign-task-questionnaire/form/${id}/{{i.id}}"/>" class="btn yellow-crusta btn-sm pull-right border-left-sold-1"><i class="fa fa-pencil"></i>&nbsp;<spring:message code="edit.title" text="Edit" /></a>		
				</h5>
				
				<small ng-show="i.hasDuration" class="help-block text-lowercase">{{i.duration.minutes}}<spring:message code="minutes.title" text="Minutes" />&nbsp;({{i.duration.d}}d&nbsp;{{i.duration.h}}h&nbsp;{{i.duration.m}}m)</small>
				<small>{{i.name}}</small>
				<section ng-show="i.type == 'QUESTIONNAIRE'"><hr/>
					<ul class="list-unstyled">
					<li ng-repeat="(q_k, q_i) in i.questions">
						<!-- ICON -->
						<i class="fa fa-font fa-lg" ng-show="!q_i.isClosedAnswers&&!q_i.isMultipleAnswers&&!q_i.schoolsFromGPS&&!q_i.isDate&&!q_i.photo"></i>
						<i class="fa fa-university" ng-show="q_i.schoolsFromGPS"></i>
						<i class="fa fa-calendar" ng-show="q_i.isDate"></i>
						<i class="fa fa-circle-o" ng-show="q_i.isClosedAnswers"></i>
						<i class="fa fa-square-o" ng-show="q_i.isMultipleAnswers"></i>
						<i class="fa fa-image" ng-show="q_i.photo"></i>
						<i class="fa fa-asterisk" ng-show="q_i.required"></i>
						<!-- NAME -->
						{{q_k+1}} . {{q_i.question}}
						<!-- OPCOES -->
						<ol ng-show="q_i.isClosedAnswers||q_i.isMultipleAnswers">
							<li class="help-block" ng-repeat="(qa_k, qa_i) in q_i.closed_answers | orderBy:'answerOrder'">{{qa_i.answerDescription}}</li>
						</ol>
					</li>
					</ul>
				</section>
				
				<p>&nbsp;</p>
				<ul class="list-inline" ng-show="i.type != 'QUESTIONNAIRE'">
					<li ng-show="i.sensorWeekSun" class="label-info label"><spring:message code="weekday.sunday" text="Sunday" />&nbsp;</li>
					<li ng-show="i.sensorWeekMon" class="label-info label"><spring:message code="weekday.monday" text="Monday" />&nbsp;</li>
					<li ng-show="i.sensorWeekTue" class="label-info label"><spring:message code="weekday.tuesday" text="Tuesday"/>&nbsp;</li>
					<li ng-show="i.sensorWeekWed" class="label-info label"><spring:message code="weekday.wednesday" text="Wednesday"/>&nbsp;</li>
					<li ng-show="i.sensorWeekThu" class="label-info label"><spring:message code="weekday.thursday" text="Thursday" />&nbsp;</li>
					<li ng-show="i.sensorWeekFri" class="label-info label"><spring:message code="weekday.friday" text="Friday" />&nbsp;</li>
					<li ng-show="i.sensorWeekSat" class="label-info label"><spring:message code="weekday.saturday" text="Saturday"/>&nbsp;</li>
				</ul>				
			</div>
			<!-- NOTIFICATION AREA -->
			<div ng-show="campaignTasks.hasNotificationArea" class="note note-default NOTIFICATION_AREA" id="campaign-task-area-notification">
				<h5 class="block uppercase"><i class="fa fa-map"></i>&nbsp;<spring:message code="notification.area.title" text="Notification Area"/>
					<button title="<spring:message code="remove.title" text="Remove" />" type="button" class="btn red btn-sm pull-right border-left-sold-1" ng-click="removeSelectedArea(true,'notification',${id})"><i class="fa fa-trash"></i></button>
					<button title="<spring:message code="copy.title" text="Copy" />" type="button" class="btn yellow btn-sm pull-right border-left-sold-1" ng-click="copySelectedArea(true,'notification',${id})"><i class="fa fa-clone"></i>&nbsp;<small class="font-white"><spring:message code="copy.notification.area.message" text="Copy NOTIFICATION area to ACTIVATION area"/></small></button>
					<a href="<c:url value="/protected/campaign-task-area-notification/form/${id}"/>" class="btn yellow-crusta btn-sm pull-right border-left-sold-1"><i class="fa fa-pencil"></i>&nbsp;<spring:message code="edit.title" text="Edit" /></a>
				</h5>
				<section ng-controller="CampaignTaskGeoDrawingCtrl" ng-init="createStaticMap(true, false, ${id}, 'mapNotificationArea1');"><div id="mapNotificationArea1"></div></section>
			</div>
			<!-- ATIVACAO AREA -->
			<div ng-show="campaignTasks.hasActivationArea" class="note note-default ACTIVATION_AREA" id="campaign-task-area-activation">
				<h5 class="block uppercase"><i class="fa fa-map-marker"></i>&nbsp;<spring:message code="activation.area.title" text="Activation Area"/>
					<button title="<spring:message code="remove.title" text="Remove" />" type="button" class="btn red btn-sm pull-right border-left-sold-1" ng-click="removeSelectedArea(false,'activation',${id})"><i class="fa fa-trash"></i></button>
					<button title="<spring:message code="copy.title" text="Copy" />" type="button" class="btn yellow btn-sm pull-right border-left-sold-1" ng-click="copySelectedArea(false,'activation',${id})"><i class="fa fa-clone"></i>&nbsp;<small class="font-white"><spring:message code="copy.activation.area.message" text="Copy ACTIVATION area to NOTIFICATION area"/></small></button>
					<a href="<c:url value="/protected/campaign-task-area-activation/form/${id}"/>" class="btn yellow-crusta btn-sm pull-right border-left-sold-1"><i class="fa fa-pencil"></i>&nbsp;<spring:message code="edit.title" text="Edit" /></a>
				</h5>
				<section ng-controller="CampaignTaskGeoDrawingCtrl"  ng-init="createStaticMap(false, true, ${id}, 'mapActivationArea1');"><div id="mapActivationArea1"></div></section>
			</div>			
		</div>
		<!-- END TABLE -->
		<!-- BEGIN DISPLAY -->
		<div class="portlet-body" ng-hide="campaignTasks.hasActions"><jsp:include page="/WEB-INF/templates/partial/noactions.jsp" /></div>
		<!-- END DISPLAY -->
		</c:if>			
		<!--  2018-02-27: DESATILIBADO -->		  
		<!-- STATS -->
		<!--  <c:if test="${isPublish&&false}"><jsp:include page="/WEB-INF/views/protected/campaign-task-statistics/index.jsp" /></c:if> -->
		<!-- REPORTS -->
		<!-- <c:if test="${isPublish&&false}"><jsp:include page="/WEB-INF/views/protected/campaign-task-reports/index.jsp" /></c:if> -->		 
		</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />
