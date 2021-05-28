<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%  
	String lang = (new RequestContext(request)).getLocale().getLanguage(); 
	lang = lang=="en_us"?"en":lang; 
%>
<%@ page import="it.unibo.paserver.domain.Gender" %>
<%@ page import="java.util.UUID" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CampaignCtrl" ng-cloak>
<c:choose><c:when test="${form eq null}"><span ng-init="getCampaign();"/></c:when><c:otherwise><span ng-init="setCampaign(${form})";/></c:otherwise></c:choose>
<div class="col-md-12">
	<div class="portlet">
	<!-- BREADCRUMB -->
	<jsp:include page="/WEB-INF/views/protected/campaign/breadcrumb.jsp" />
	<!-- BEGIN FORM -->
	<div class="portlet-body form">
		<form class="form-horizontal" role="form" name="formCampaign">
			<input type="text" class="hidden" id="HeyLocale" />
				<div class="form-body">
					<!-- ACCESS -->	
					<c:if test="${!isPublish}">						
					<div class="form-group">
						<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="information.title" text="Information" /></label>
					</div>
					</c:if>					
					<!-- INFO -->
					<div class="form-group">
						<label class="col-md-2 control-label bold" ng-class="formCampaign.name.$invalid||formCampaign.surname.$invalid?'font-red':'font-green'"><spring:message code="name.title" text="Name" /></label>
						<div class="col-md-7"><input id="name" type="text" ng-model="form.name" name="name" class="form-control"  maxlength="100" placeholder="<spring:message code="name.title" text="Name" />" required="required" /></div>
					</div>
					
					<div class="form-group">
						<label class="col-md-2 control-label bold"><spring:message code="color.title" text="Color" /></label>
						<div class="col-md-2 col-sm-4 col-xs-12">	
							<input id="color" name="color" list="colors" class="form-control" type="color" ng-model="form.color"/>
							<datalist id="colors">
							  <option>#7E986C</option>
							  <option>#708689</option>
							  <option>#707489</option>
							  <option>#2D87DB</option>
							  <option>#BFA600</option>
							  
							  <option>#5E6369</option>
							  <option>#897C70</option>
							  <option>#A88B71</option>
							  <option>#7CA340</option>
							  <option>#F2994A</option>
							  
							  <option>#13AE5C</option>
							  <option>#00BCD6</option>
							</datalist>							
						</div>
					</div>	
					
					<div class="form-group">
						<label class="col-md-2 control-label bold"><spring:message code="icon.title" text="Icon" /></label>
						<div class="col-md-4">
							<div class="input-group">
							   		<span class="input-group-btn">
							   		<button type="button" class="btn blue" ng-controller="FileUploadCtrl" type="button" ngf-select="uploadStorage($file, 'c','iconUrl');" ng-model="file" name="file" ngf-pattern="'image/*'" ngf-accept="'image/*'" ngf-max-size="1MB"><i class="fa fa-cloud-upload" aria-hidden="true"></i></button></span>
							      	<input type="url" class="form-control"  name="iconUrl" id="iconUrl" ng-model="form.iconUrl"/>
							      	<span class="input-group-btn"><a ng-href="{{form.iconUrl}}" class="btn blue"  target="_blank"><i class="fa fa-external-link" aria-hidden="true"></i></a></span>
							    </div><!-- /input-group -->
							</div>
							<!-- ICON -->
							<div class="col-md-3" ng-show="form.iconUrl.length>0">
								<img ng-src="{{form.iconUrl}}" alt="" class="img-thumbnail" style="height: 52px; max-width: 61px;" ng-style="{'background-color': form.color||'white'}"/>
							</div>
						</div>						
					</div>
					
					<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
					<div class="form-group">
						<div class="col-md-offset-2 col-md-7">
							<div class="mt-checkbox-list"><label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.canBeRefused" /><small class="text-justify" ng-class="form.canBeRefused?'mark':''"><spring:message code="campaign.refused.message" text="This task can be refused by users" /></small><span></span></label></div>
						</div>
					</div>
					</sec:authorize>

					<c:if test="${isPublish}">
					<div class="form-group">
						<div class="col-md-offset-2 col-md-7">
							<div class="mt-checkbox-list"><label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.enabled" /><small class="text-justify" ng-class="form.enabled?'mark':'bg-danger'"><spring:message code="campaing.enabled.help" text="If enabled, the campaign will be active in the APIs" /></small><span></span></label></div>
						</div>
					</div>
					</c:if>
					<!-- DATES -->	
					<div class="form-group">
						<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="dates.title" text="Dates" />&nbsp;&nbsp;<i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="<spring:message code="daterange.help.message" text="Start and deadline of this task" />"></i></label>
					</div>					
					<section>
					<!-- START -->
					<div class="form-group">
						<label class="col-md-2 control-label bold" ng-class="formCampaign.dt_start.$invalid?'font-red':'font-green'"><spring:message code="start.time.title" text="Start Date" /></label>
						<div class="form-inline col-lg-3 col-md-5 col-sm-6 col-xs-12">
							<div class="form-group col-md-8">
								<input type="text" class="form-control" name="dt_start" id="dt_start" ng-model="form.dt_start" required="required"  placeholder="dd/mm/aaaa" ui-date-mask="DD/MM/YYYY" parse="false"/>
								<small class="help-block"><spring:message code="date.title" text="Date" /></small>
							</div>
							<div class="form-group col-md-4">
								<input type="text" class="form-control" name="time_start" id="time_start" ng-model="form.time_start" required="required"  placeholder="HH:mm" ui-time-mask="short"/>
								<small class="help-block"><spring:message code="hour.title" text="Hour" /></small>
							</div>
						</div>
					</div>
					<!-- END -->
					<div class="form-group">
						<label class="col-md-2 control-label bold" ng-class="formCampaign.dt_end.$invalid?'font-red':'font-green'"><spring:message code="end.time.title" text="End Date" /></label>
						<div class="form-inline col-lg-3 col-md-5 col-sm-6 col-xs-12">
							<div class="form-group col-md-8">
								<input type="text" class="form-control" name="dt_end" id="dt_end" ng-model="form.dt_end" required="required"  placeholder="dd/mm/aaaa" ui-date-mask="DD/MM/YYYY" parse="false"/>
								<small class="help-block"><spring:message code="date.title" text="Date" /></small>
							</div>
							<div class="form-group col-md-4">
								<input type="text" class="form-control" name="time_end" id="time_end" ng-model="form.time_end" required="required"  placeholder="HH:mm" ui-time-mask="short"/>
								<small class="help-block"><spring:message code="hour.title" text="Hour" /></small>
							</div>
						</div>
					</div>

					<sec:authorize access="!hasRole('ROLE_COOPERATION_AGREEMENT')&&!hasRole('ROLE_RESEARCHER_OMBUDSMAN')">
					<!-- DURACAO -->
					<div class="form-group margin-top-40">
						<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="duration.title" text="Duration" /></label>
					</div>		
										
					<!-- IS MATCH DURATION -->
					<div class="form-group">
						<label class="col-md-2 control-label bold"><spring:message code="duration.help.message" text="Duration of the task" /></label>
						<div class="col-md-7">
							<div class="mt-checkbox-list"><label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.isDuration" /><small class="text-justify"><spring:message code="duration.equal.message" text="Duration equal to date" /></small><span></span></label></div>
						</div>
					</div>
					
					<!-- DURATION -->
					<div class="form-group">					
					<div class="col-md-offset-2 col-md-5" ng-hide="form.isDuration">
						<div class="input-group input-medium">
							<input type="text" id="duration-picker" ng-model="form.duration" value="0"/>
						</div>
						<small class="help-block" ng-class="form.isDuration.length!='0'?'font-green':'font-red'"><spring:message code="duration.help.message" text="Duration of the task" /></small>			
					</div>
					</div>
					
					<!-- SENSING DURATION -->
					<div class="form-group">	
						<label class="col-md-2 control-label bold"><spring:message code="sensing.duration.title" text="Sensing" /></label>
						<div class="col-md-5" >
							<div class="input-group input-medium">
								<input type="text" id="duration-picker-2" ng-model="form.sensingDuration" value="86400" />
							</div>
							<small class="help-block"><spring:message code="sensing.duration.title" text="Duration of the sensing activity" />&nbsp;&nbsp;<i class="fa fa-question-circle" data-toggle="tooltip" data-placement="top" title="<spring:message code="sensing.duration.help.message" text="Duration of the sensing activity in minutes. This time is shared by all passive sensing actions." />"></i></small>			
						</div>	
					</div>
					
					<div class="form-group">	
						<label class="col-md-2 control-label bold"><spring:message code="sensing.days.title" text="Sensing Days" /></label>
						<div class="col-md-5" >
						<div class="mt-checkbox-inline">
							<label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.sensingWeekSun"><small class="text-justify"><spring:message code="weekday.sunday" text="Sunday" /></small><span></span></label>
							<label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.sensingWeekMon"><small class="text-justify"><spring:message code="weekday.monday" text="Monday " /></small><span></span></label>
							<label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.sensingWeekTue"><small class="text-justify"><spring:message code="weekday.tuesday" text="Tuesday " /></small><span></span></label>
							<label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.sensingWeekWed"><small class="text-justify"><spring:message code="weekday.wednesday" text="Wednesday " /></small><span></span></label>
							<label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.sensingWeekThu"><small class="text-justify"><spring:message code="weekday.thursday" text="Thursday " /></small><span></span></label>
							<label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.sensingWeekFri"><small class="text-justify"><spring:message code="weekday.friday" text="Friday " /></small><span></span></label>
							<label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.sensingWeekSat"><small class="text-justify"><spring:message code="weekday.saturday" text="Saturday " /></small><span></span></label>	
						</div>						
						</div>
					</div>
					</section>
					</sec:authorize>

					<!-- Site/WordPress -->
					<div class="form-group">	
						<label class="col-md-2 control-label bold"><spring:message code="publish.page.title" text="Publish Page" /></label>
						<div class="col-md-5" >						
							<div class="mt-checkbox-inline">
							<label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.wpPublishPage"><small class="text-justify"><spring:message code="wp.publish.page" text="Available in Site" /></small><span></span></label>
							<label class="mt-checkbox mt-checkbox-outline" ng-show="form.wpPublishPage"><input type="checkbox" ng-model="form.wpPostDescription"><small class="text-justify"><spring:message code="wp.post.descrption" text="Description " /></small><span></span></label>
							<label class="mt-checkbox mt-checkbox-outline" ng-show="form.wpPublishPage"><input type="checkbox" ng-model="form.wpPostQuestionnaire"><small class="text-justify"><spring:message code="wp.post.questionnaire" text="Questinnaire " /></small><span></span></label>
							<label class="mt-checkbox mt-checkbox-outline" ng-show="form.wpPublishPage"><input type="checkbox" ng-model="form.wpPostSensorList"><small class="text-justify"><spring:message code="wp.post.sensorlist" text="Sensor List " /></small><span></span></label>
							<label class="mt-checkbox mt-checkbox-outline" ng-show="form.wpPublishPage"><input type="checkbox" ng-model="form.wpPostCampaignStats"><small class="text-justify"><spring:message code="wp.post.stats" text="Stats " /></small><span></span></label>								
							</div>
						</div>	
					</div>		
					
					
					<div class="form-group" ng-show="form.wpPublishPage">	
						<label class="col-md-2 control-label bold"><spring:message code="wp.publish.content" text="Page Content" /></label>
						<div class="col-md-7">
						<summernote config="{}" ng-model="form.wpPublishText" height="300" id="wpPublishText" ng-init="form.wpPublishText='${wpPublishText}'"></summernote>	
						</div>
					</div>

					<sec:authorize access="!hasRole('ROLE_COOPERATION_AGREEMENT')&&!hasRole('ROLE_RESEARCHER_OMBUDSMAN')">
					<!-- EMAIL -->
					<div class="form-group margin-top-40">
						<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="email.title" text="Email" /></label>
					</div>	
					<div class="form-group">
						<div class="col-md-offset-2 col-md-7">
							<div class="mt-checkbox-list"><label class="mt-checkbox mt-checkbox-outline"><input type="checkbox" ng-model="form.isSendEmail" /><small class="text-justify"><spring:message code="issend.email.message" text="Is it to send email?" /></small><span></span></label></div>
						</div>
					</div>
					<!-- TEXT -->	
					<div class="form-group" ng-show="form.isSendEmail">
						<jsp:include page="/WEB-INF/templates/partial/emailForm.jsp" />
					</div>
					</sec:authorize>
					<!-- AGREEMENT -->
					<div class="form-group">
						<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="term.consent.title" text="Term of Consent" /></label>
					</div>
					<div class="form-group">
						<div class="col-md-offset-2 col-md-7">
						<summernote config="{}" ng-model="form.agreement" height="300" id="summernoteAgreement" ng-init="form.agreement='${agreement}'"></summernote>	
						</div>
					</div>					
					<!-- NOTES -->
					<div class="form-group">
						<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="description.title" text="Additional Notes" /></label>
					</div>						
					<div class="form-group">
						<div class="col-md-offset-2 col-md-7">
							<textarea class="form-control" ng-model="form.description" rows="6"></textarea>
							<small class="text-info"><spring:message code="notes.help.message" text="The text entered into comments will be displayed in the application." /></small>
						</div>
					</div>
					<!-- BUTTON -->
					<div class="form-group margin-top-40 margin-bottom-10 col-md-9">
						<button type="button" class="btn green col-xs-6 col-sm-4 col-md-offset-2 col-md-2 col-lg-2 margin-right-10" ng-click="saveCampaign();" ng-disabled="formCampaign.$invalid"><spring:message code="save.title" text="Save" /></button>
						<a href="<c:url value="/protected/campaign/index"/>" class="btn default col-xs-6 col-sm-4 col-md-2 col-lg-2 pull-right" id="fixgoback"><spring:message code="goback.title" text="Go Back" /></a>						
						<a ng-show="false" href="<c:url value="/protected/campaign-task/index/"/>{{outcome}}" id="fixgotask"></a>
					</div>
				</div>
				<!-- BEGIN DEBUG -->
				<div class="col-md-12 margin-top-40">
					<i class="fa fa-bug font-grey pull-right qz-pointer" ng-click="showDebug=!showDebug"></i>
					<ul class="list-unstyled well" ng-show="showDebug">
						<li ng-repeat="(key, errors) in formCampaign.$error track by $index">
							<ol>
								<li ng-repeat="e in errors">{{ e.$name }}: <strong>{{ key }}</strong>.</li>
							</ol>
						</li>
					</ul>
				</div>
				<!-- END DEBUG -->
			</form>
	</div>
	<!-- END FORM -->
	</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />