<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%  String lang = (new RequestContext(request)).getLocale().getLanguage(); lang = lang=="en_us"?"en":lang; %>
<%@ page import="java.util.UUID" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CampaignCtrl" ng-init="initCampaign();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/campaign/breadcrumb.jsp" />
		<!-- FILTER -->
		<div class="portlet-body">
		<div class="row"><div class="col-md-12"><div class="pull-right">
			<form class="form-inline" role="form">
				<section ng-hide="form.isAdvancedSearch">
					<div class="form-group"><input type="text" class="form-control pa-filter col-md-2" placeholder="<spring:message code="name.title" text="Name" />" ng-model="form.name"></div>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<div class="form-group"><jsp:include page="/WEB-INF/templates/partial/selectFilterByParentUser.jsp" /></div>
					</sec:authorize>
					<div class="form-group"><button title="<spring:message code="search.title" text="Search" />" class="btn blue" type="button" ng-click="initCloudSearch();"><i class="fa fa-search"></i></button></div>
					<div class="form-group"><button title="<spring:message code="filter.add.title" text="Add item filter" />" class="btn yellow-crusta" type="button" ng-click="initAdvancedSearch();"><i class="fa fa-filter"></i></button></div>
				</section>
				<section ng-show="form.isAdvancedSearch">
					<div class="form-group"><jsp:include page="/WEB-INF/templates/partial/selectFilterByCampaign.jsp" /></div>
					<div class="form-group">
						<div class="input-group">
							<input ng-show="filter.input=='TEXT'" type="text" class="form-control pa-advanced input-large" maxlength="200" placeholder="<spring:message code="value.title" text="Value" />" ng-model="filter.value">
							<input ng-controller="DatePickerCtrl" ng-show="filter.input=='DATEPICKER'" type="text" class="form-control pa-advanced input-large filterpicker-here" data-date-format="dd/mm/yyyy" readonly="" data-date-language="<%=lang%>" ng-model="filter.value">
							<input type="text" class="hidden" ng-model="filter.value" id="filterpicker" />
							<div ng-show="filter.input=='FILTER_CANBEREFUSED'||filter.input=='FILTER_HAS_QUESTION'||filter.input=='FILTER_HAS_PHOTO'"><jsp:include page="/WEB-INF/templates/partial/selectYesNo.jsp" /></div>
							<div ng-show="filter.input=='FILTER_PIPELINE_TYPE'"><jsp:include page="/WEB-INF/templates/partial/selectPipelineType.jsp" /></div>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
							<div ng-show="filter.input=='FILTER_PARENT_ID'"><jsp:include page="/WEB-INF/templates/partial/selectFilterByParentUser.jsp" /></div>
							</sec:authorize>
							<div class="input-group-btn"><button title="<spring:message code="filter.add.title" text="Add item" />" type="button" class="btn green" ng-disabled="filter.value.length=='0'||form.filterBy.length=='0'" ng-click="addFilterBy();"><i class="fa fa-search-plus"></i></button></div>
						</div>
					</div>
					<div class="form-group"><button title="<spring:message code="search.title" text="Search" />" class="btn blue" type="button" ng-click="initCloudSearch();"><i class="fa fa-search"></i></button></div>
					<div class="form-group"><button title="<spring:message code="clean.title" text="Reset" />" class="btn default" type="button" ng-click="initSimpleSearch();"><i class="fa fa-eraser"></i></button></div>
				</section>

			</form>
			</div></div></div>	
		</div>
		<div class="portlet-body margin-top-20"  ng-show="form.isAdvancedSearch && hashmap.length>'0'">
			<div class="row"><div class="col-md-12"><div class="pull-right">
				<ul class="list-inline"><li ng-repeat="(k,h) in hashmap" class="margin-top-10 "><span class="label label-{{h.label}} margin-top-20">{{h.value}}&nbsp;<i class="fa fa-close qz-pointer" ng-click="removeFilterBy(k);"></i></span></li></ul>
			</div></div></div>		
		</div>
		<!-- BEGIN TABLE -->
		<div class="portlet-body">
			<div class="table-scrollable">
				<table class="table table-striped table-bordered table-hover table-checkable" id="<%UUID.randomUUID().toString();%>">
					<thead>
						<tr role="row" class="heading">
							<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')"><th width="2%"><label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="group-checkable" ng-model="selectedAll" ng-click="checkAll()" /> <span></span></label></th></sec:authorize>
							<th class="col-md-3 qz-pointer" ng-class="orderByColumn=='Name'?'font-blue':''" ng-click="setOrderBy('Name');"><spring:message code="campaign.title" text="Name" /><i class="fa pull-right" ng-class="orderByDesc?'fa-sort-desc':'fa-sort-asc';"></i></th>
							<th class="col-md-2 qz-pointer" ng-class="orderByColumn=='Dates'?'font-blue':''" ng-click="setOrderBy('Dates');"><spring:message code="dates.title" text="Dates" /><i class="fa pull-right" ng-class="orderByDesc?'fa-sort-desc':'fa-sort-asc';"></i></th>
							<th class="col-md-2 qz-pointer" ng-class="orderByColumn=='Durations'?'font-blue':''" ng-click="setOrderBy('Durations');"><spring:message code="durations.title" text="Durations" /><i class="fa pull-right" ng-class="orderByDesc?'fa-sort-desc':'fa-sort-asc';"></i></th>
							<th class="col-md-3 qz-pointer" ng-class="orderByColumn=='Actions'?'font-blue':''" ng-click="setOrderBy('Actions');"><spring:message code="actions.title" text="Actions" /><i class="fa pull-right" ng-class="orderByDesc?'fa-sort-desc':'fa-sort-asc';"></i></th>
							<th class="col-md-1 text-center qz-pointer" ng-class="orderByColumn=='Status'?'font-blue':''" ng-click="setOrderBy('Status');"><spring:message code="status.title" text="Status" /><i class="fa pull-right" ng-class="orderByDesc?'fa-sort-desc':'fa-sort-asc';"></i></th>
						</tr>
					</thead>
					<!--  RELATOS -->
					<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
					<tbody>
						<tr class="info">
							<th width="2%"><i class="fa fa-ban"></i></th>
							<td colspan="4"><p><i class="icon-flag"></i>&nbsp;<spring:message code="campaign.fixed" text="Fixed Campaign" /></p></td>
							<td><a href="<c:url value="/protected/campaign-fixed/index"/>" class="btn uppercase blue-chambray btn-block"> <i class="fa fa-newspaper-o" aria-hidden="true"></i>&nbsp;<spring:message code="summary.title" text="Summary" /></a></td>
						</tr>
					</tbody>
					</sec:authorize>
					<!--  CAMPANHAS -->
					<tbody ng-show="campaigns.length > '0'">
						<tr ng-repeat="(key_item, item) in campaigns" id="campaigns-tr-{{item[0]}}">
							<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')"><td><input type="checkbox" class="group-checkable" ng-model="item.Selected" ng-change="isSelected('checkbox-{{item[0]}}')" id="checkbox-{{item[0]}}"/></td></sec:authorize>
							<td ng-click="gotoTasks(item[0]);" class="qz-pointer text-left">
								<div class="media">
									<div class="media-left">
										<a href="#">
											<img class="media-object img-circle" ng-src="{{item[27]|userimage}}" alt="{{item[1] | stripslashes}}" ng-style="{'background-color': item[26]}" width="32">
										</a>
									</div>
									<div class="media-body">
										<span>{{item[1] | stripslashes}}</span>
										<sec:authorize access="hasRole('ROLE_ADMIN')"><small class="help-block"><i class="fa fa-user"></i>&nbsp;{{item[17] | stripslashes}}</small></sec:authorize>
										<small ng-show="item[10]" class="help-block"><i class="fa fa-ban"></i><mark><spring:message code="campaign.refused.message" text="This task can be refused by users" /></mark></small>
										<small ng-show="!item[28]" class="help-block text-danger"><i class="fa fa-eye-slash"></i>&nbsp;<spring:message code="campaign.disabled.help" text="Disabled in APIs" /></small>
									</div>
								</div>
							</td>
							<td ng-click="gotoTasks(item[0]);" class="qz-pointer">
								<small class="font-blue"><spring:message code="datestart.title" text="Start" />&nbsp;:<br/><strong class="text-lowercase">{{item[3].millis | date:'dd/MM/yyyy HH:mm'}}</strong></small><br/>
								<small class="font-blue-chambray"><spring:message code="dateend.title" text="Date end" />&nbsp;:<br/><strong class="text-lowercase">{{item[4].millis | date:'dd/MM/yyyy HH:mm'}}</strong></small><br/>
							</td>	
							<td ng-click="gotoTasks(item[0]);" class="qz-pointer">
								<small class="font-blue"><spring:message code="task.duration.title" text="Task Duration" />&nbsp;:<br/><strong class="text-lowercase">{{item.duration.minutes}}<spring:message code="minutes.title" text="Minutes" />&nbsp;({{item.duration.d}}d&nbsp;{{item.duration.h}}h&nbsp;{{item.duration.m}}m)</strong></small><br/>
								<small class="font-blue-chambray"><spring:message code="sensing.duration.title" text="Sensing duration" />&nbsp;:<br/><strong class="text-lowercase">{{item.sensingDuration.minutes}}<spring:message code="minutes.title" text="Minutes" />&nbsp;({{item.sensingDuration.d}}d&nbsp;{{item.sensingDuration.h}}h&nbsp;{{item.sensingDuration.m}}m)</strong></small><br/>
								
								<small class="text-muted">
									<span ng-show="item[18]"><spring:message code="weekday.sunday" text="Sunday" />&nbsp;&#124&nbsp;</span>
									<span ng-show="item[19]"><spring:message code="weekday.monday" text="Monday" />&nbsp;&#124&nbsp;</span>
									<span ng-show="item[20]"><spring:message code="weekday.tuesday" text="Tuesday"/>&nbsp;&#124&nbsp;</span>
									<span ng-show="item[21]"><spring:message code="weekday.wednesday" text="Wednesday"/>&nbsp;&#124&nbsp;</span>
									<span ng-show="item[22]"><spring:message code="weekday.thursday" text="Thursday" />&nbsp;&#124&nbsp;</span>
									<span ng-show="item[23]"><spring:message code="weekday.friday" text="Friday" />&nbsp;&#124&nbsp;</span>
									<span ng-show="item[24]"><spring:message code="weekday.saturday" text="Saturday" /></span>
								</small>
							</td>
							<td>
								<ul class="list-unstyled">
								  <li class="uppercase small" ng-class="item[16]>0?'font-green-seagreen':'font-default'"><span class="badge" ng-class="item[16]>0?'badge-success':'badge-default'">{{item[16]}}</span>&nbsp;<spring:message code="tasks.title" text="Tasks" />&nbsp;</li>
								  <li class="uppercase small" ng-class="item[12]?'font-green-seagreen':'font-default'"><i class="fa fa-users"></i>&nbsp;<spring:message code="participants.title" text="Participants" />&nbsp;</li>
								  <li class="uppercase small" ng-class="item[14]?'font-green-seagreen':'font-default'"><i class="fa fa-map"></i>&nbsp;<spring:message code="notification.area.title" text="Notification Area"/>&nbsp;</li>
								  <li class="uppercase small" ng-class="item[15]?'font-green-seagreen':'font-default'"><i class="fa fa-map-marker"></i>&nbsp;<spring:message code="activation.area.title" text="Activation Area"/>&nbsp;</li>
								</ul>
							</td>
							<td class="text-center">
							<div ng-switch on="item.status">
							    <div ng-switch-when="0">
							    	<div ng-hide="item[16]>0&&item[12]" class="bg-font-grey text-uppercase padding-top-bottom-10 small bold"><i class="fa fa-calendar-times-o"></i>&nbsp;<spring:message code="draft.title" text="Draft" /></div>
							    	<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')">
										<button  ng-show="item[16]>0&&item[12]&&!item[8]&&item[9]" class="btn purple-seance btn-block animated flash" ng-click="beginPublishing(key_item);"><i class="fa fa-send"></i>&nbsp;<spring:message code="publish.title" text="Publish" /></button>
									</sec:authorize>
							    </div>
							    <div ng-switch-when="2">
							    	<div class="font-yellow-casablanca text-uppercase padding-top-bottom-10 small bold"><i class="fa fa-calendar-check-o"></i>&nbsp;<spring:message code="enabled.title" text="Enabled" /></div>
									<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')">
										<!-- CONVITE -->
										<button class="btn blue-steel btn-block uppercase" ng-click="inviteTask(item[0]);" ng-hide="item[25]"><i class="fa fa-key"></i>&nbsp;<spring:message code="invite.title" text="Invite" /></button>
										<button class="btn dark btn-outline btn-block uppercase" copy-to-clipboard="{{item[25]}}" ng-show="item[25]"><i class="fa fa-key"></i>&nbsp;{{item[25]}}</button>
									</sec:authorize>
							    </div>	
							    <div ng-switch-default  class="bg-blue bg-font-blue text-uppercase padding-top-bottom-10 small bold">
									<i class="fa fa-calendar-plus-o"></i>&nbsp;<spring:message code="ended.title" text="Ended" />
								</div>
							    <button class="btn green btn-block uppercase" ng-click="gotoTasks(item[0]);"><i class="fa fa-tasks"></i>&nbsp;<spring:message code="tasks.title" text="Tasks" /></button>
							    <a ng-hide="!item[12]" href="<c:url value="/protected/campaign-task/summary/"/>{{item[0]}}" class="btn uppercase blue-chambray btn-block"> <i class="fa fa-newspaper-o" aria-hidden="true"></i>&nbsp;<spring:message code="summary.title" text="Summary" /></a>
							    <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')">
							    	<button class="btn yellow-casablanca btn-block uppercase" ng-click="copyTask(item[0]);"><i class="fa fa-copy"></i>&nbsp;<spring:message code="copy.title" text="Copy" /></button>
							    </sec:authorize>
							</div>							
							</td>
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
		<div class="portlet-body" ng-show="campaigns.length == '0'" id="no-data-to-display">
		<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
		</div>
		<!-- END DISPLAY -->	
		</div>	
</div>
<!-- MODAL -->
<jsp:include page="/WEB-INF/templates/partial/confirmPublish.jsp" />
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />