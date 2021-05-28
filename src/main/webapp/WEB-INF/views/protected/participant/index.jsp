<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%  String lang = (new RequestContext(request)).getLocale().getLanguage(); lang = lang=="en_us"?"en":lang; %>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="ParticipantCtrl" ng-init="initParticipant();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/participant/breadcrumb.jsp" />
		<!-- FILTER -->
		<div class="portlet-body">
		<div class="row"><div class="col-md-12"><div class="pull-right">
			<form class="form-inline" role="form">
				<section ng-hide="form.isAdvancedSearch">
					<div class="form-group"><input type="text" class="form-control pa-filter" placeholder="<spring:message code="name.title" text="Name" />" ng-model="form.name"></div>
					<div class="form-group"><input type="text" class="form-control pa-filter" placeholder="<spring:message code="username.title" text="Username" />" ng-model="form.username"></div>
					<div class="form-group"><jsp:include page="/WEB-INF/templates/partial/selectUniCourse.jsp" /></div>
					<div class="form-group"><jsp:include page="/WEB-INF/templates/partial/selectInstitution.jsp" /></div>
					<div class="form-group">
						<div class="btn-group">
							<button title="<spring:message code="search.title" text="Search" />" class="btn blue" type="button" ng-click="initCloudSearch();"><i class="fa fa-search"></i></button>
							<button title="<spring:message code="filter.add.title" text="Add item filter" />" class="btn yellow-crusta" type="button" ng-click="initAdvancedSearch();"><i class="fa fa-filter"></i></button>
						</div>
					</div>
				</section>
				<section ng-show="form.isAdvancedSearch">
					<div class="form-group"><jsp:include page="/WEB-INF/templates/partial/selectFilterBy.jsp" /></div>
					<div class="form-group">
						<div class="input-group">
							<input ng-show="filter.input=='TEXT'" type="text" class="form-control pa-advanced input-large" maxlength="200" placeholder="<spring:message code="value.title" text="Value" />" ng-model="filter.value">
							<input ng-controller="DatePickerCtrl" ng-show="filter.input=='DATEPICKER'" type="text" class="form-control pa-advanced input-large filterpicker-here" data-date-format="dd/mm/yyyy" readonly="" data-date-language="<%=lang%>" ng-model="filter.value">
							<div ng-show="filter.input=='FILTER_GENDER'"><jsp:include page="/WEB-INF/templates/partial/selectGender.jsp" /></div>
							<div ng-show="filter.input=='FILTER_UNICOURSE'"><jsp:include page="/WEB-INF/templates/partial/selectUniCourse.jsp" /></div>
							<div ng-show="filter.input=='FILTER_SCHOOLCOURSEID'"><jsp:include page="/WEB-INF/templates/partial/selectSchoolCourse.jsp" /></div>
							<div ng-show="filter.input=='FILTER_INSTITUTIONID'"><jsp:include page="/WEB-INF/templates/partial/selectInstitution.jsp" /></div>
							<div ng-show="filter.input=='FILTER_DOCUMENTIDTYPE'"><jsp:include page="/WEB-INF/templates/partial/selectDocumentIdType.jsp" /></div>
							<div class="input-group-btn"><button title="<spring:message code="filter.add.title" text="Add item" />" type="button" class="btn green" ng-disabled="filter.value.length=='0'||form.filterBy.length=='0'" ng-click="addFilterBy();"><i class="fa fa-search-plus"></i></button></div>
						</div>
					</div>
					<div class="form-group">
						<div class="btn-group">
							<button title="<spring:message code="search.title" text="Search" />" class="btn blue" type="button" ng-click="initCloudSearch();"><i class="fa fa-search"></i></button>
							<button title="<spring:message code="clean.title" text="Reset" />" class="btn default" type="button" ng-click="initSimpleSearch();"><i class="fa fa-eraser"></i></button>
						</div>
					</div>
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
		<div class="portlet-body" ng-show="participants.length > '0'">
			<div class="table-scrollable">
				<table class="table table-striped table-bordered table-hover table-checkable" id="<%UUID.randomUUID().toString();%>">
					<thead>
						<tr role="row" class="heading">
							<th width="2%"><label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="group-checkable" ng-model="selectedAll" ng-click="checkAll()" /> <span></span></label></th>
							<th class="col-md-4"><spring:message code="protected.participant.name" text="Name" /></th>
							<th class="col-md-6"><spring:message code="username.title" text="Username" /></th>
							<th width="2%" class="text-center"></th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="item in participants" id="participants-tr-{{item[0]}}" ng-hide="item[0]==26529">
							<td><input type="checkbox" class="group-checkable" ng-model="item.Selected" ng-change="isSelected('checkbox-{{item[0]}}')" id="checkbox-{{item[0]}}"/></td>
							<td ng-click="editParticipant(item[0]);" class="qz-pointer">
								<div class="media">
									<div class="media-left">
										<a href="javascript:;">
											<img class="media-object img-circle" ng-src="{{item[4]|userimage}}" alt="" width="32">
										</a>
									</div>
									<div class="media-body">
										<h5 class="media-heading">{{item[1] | stripslashes}}</h5>
										<small class="help-block">{{item[11] | stripslashes}}</small>
										<small class="help-block">{{item[7] | stripslashes}}&nbsp;{{item[15] | stripslashes}}&nbsp;{{item[8] | stripslashes}}&nbsp;{{item[9] | stripslashes}}&nbsp;{{item[10] | stripslashes}}</small>
									</div>
								</div>
							</td>
							<td ng-click="editParticipant(item[0]);" class="qz-pointer">
								<span class="text-danger"><i class="fa fa-first-order" aria-hidden="true"></i>&nbsp;{{item[3] | stripslashes}}</span>
								<small class="help-block text-warning"><i class="fa fa-rebel" aria-hidden="true"></i>&nbsp;{{item[26]||''}}</small>
								<small class="help-block">{{item[5] | stripslashes}}</small>
								<small class="help-block">{{item[6] | stripslashes}}</small>
								<p ng-show="item[27]">
									<small class="text-info"><i class="fa fa-grav" aria-hidden="true"></i>&nbsp;{{item[27].name||''}}</small>
									<small class="text-danger"><i class="fa fa-first-order" aria-hidden="true"></i>&nbsp;{{item[27].officialemail||''}}</small>
									<small class="text-warning"><i class="fa fa-rebel" aria-hidden="true"></i>&nbsp;{{item[27].secondaryemail||''}}</small>
									<small class="text-muted"><i class="fa fa-paragraph" aria-hidden="true"></i>&nbsp;{{item[27].progenitorid||'0'}}</small>
								</p>
							</td>
							<td class="text-center"><a href="<c:url value="/protected/participant/details/{{item[0]}}"/>" class="btn-block btn blue"><i class="fa fa-bar-chart"></i>&nbsp;<spring:message code="details.title" text="Details" /></a>
								<small class="help-block text-right hiper-small font-blue" title="<spring:message code="registration.date.title" text="Registration Date" />">{{item[16].millis | date:'dd/MM/yyyy HH:mm'}}&nbsp;<i class="fa fa-calendar" aria-hidden="true"></i></small>
								<small class="help-block text-right hiper-small font-blue" title="<spring:message code="device.title" text="Device" />">{{item[17]}}&nbsp;<i class="fa fa-mobile" aria-hidden="true"></i></small>
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
		<div class="portlet-body" ng-show="participants.length == '0'" id="no-data-to-display">
		<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
		</div>
		<!-- END DISPLAY -->	
		</div>	
</div>
<c:if test="${breadcrumb eq '/protected/participant/index'}">
<!-- UPLOAD -->
<jsp:include page="/WEB-INF/views/protected/import-file/form.jsp" />
</c:if>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />