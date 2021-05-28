<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CampaignTaskStatsCtrl">
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/participant/breadcrumb.jsp" />
		<!-- BEGIN DETAILS -->
		<div class="portlet-body"><div class="row">
		<!-- DETALHES -->
		<div class="col-md-12 col-sm-12">
			<div class="portlet green box" ng-controller="ParticipantCtrl" ng-init="setParticipant(${id});">
				<div class="portlet-title">
					<div class="caption qz-pointer" ng-click="collapseDetails(${id});"><i class="fa" ng-class="checkDetails?'fa-angle-down':'fa-angle-right'"></i>&nbsp;{{form.name}}&nbsp;</div>
					<div class="actions">
					<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST')">
						<a href="<c:url value="/protected/participant/edit/${id}"/>" class="btn btn-default"> <i class="fa fa-pencil"></i>&nbsp;<spring:message code="edit.title" text="Edit" /></a>
					</sec:authorize>	
					</div>
				</div>
				<div class="portlet-body" ng-show="checkDetails">
					<div class="row static-info">
						<div class="col-md-4 name"><strong><spring:message code="username.title" text="Username" />&nbsp;:</strong><small class="help-block">{{form.officialEmail}}</small></div>
						<div class="col-md-4 name"><strong><spring:message code="filter.birthdate" text="Birthdate" />&nbsp;:</strong><small class="help-block">{{form.dt_format}}</small></div>
						<div class="col-md-3 name"><strong><spring:message code="address.title" text="address" />&nbsp;:</strong><small class="help-block">{{form.address}}&nbsp;{{form.addressNumber}}&nbsp;{{form.addressCity}}&nbsp;{{form.addressPostalCode}}</small></div>
					</div>
					<div class="row static-info">
						<div class="col-md-4 name"><strong><spring:message code="gender.title" text="Gender" />&nbsp;:</strong><small class="help-block">{{form.genderName}}</small></div>
						<div class="col-md-4 name"><strong><spring:message code="phone.title" text="Phone" />&nbsp;:</strong><small class="help-block">{{form.homePhoneNumber}}</small></div>
						<div class="col-md-4 name"><strong><spring:message code="cellphone.title" text="Cellphone" />&nbsp;:</strong><small class="help-block">{{form.contactPhoneNumber}}</small></div>
					</div>

					<div class="row static-info">
						<div class="col-md-4 name"><strong><spring:message code="institution.title" text="Institution" />&nbsp;:</strong><small class="help-block">{{form.institutionName}}</small></div>
						<div class="col-md-4 name"><strong><spring:message code="education.level.title" text="Education Level" />&nbsp;:</strong><small class="help-block">{{form.uniCourseName}}</small></div>
						<div class="col-md-4 name"><strong><spring:message code="education.course.title" text="Course" />&nbsp;:</strong><small class="help-block">{{form.schoolCourseName}}</small></div>
					</div>
					<div class="row static-info">
						<div class="col-md-12 name"><hr/>{{form.notes}}</div>
					</div>
				</div>
			</div>
		</div>
		</div></div>
		<!-- END DETAILS -->
		<!-- BEGIN CHART -->
		<div class="portlet-body" ng-controller="CampaignTaskStatsCtrl" ng-init="getTotalByStateAndUser(${id});"><div class="row">
		<div class="col-md-12 col-sm-12">
		<!-- BEGIN DATA -->
		<highchart id="chart1" config="chartConfig" ></highchart>
		<!-- END DATA -->
		</div>
		<div class="col-md-12 col-sm-12" ng-hide="hasChart">
		<h2 class="text-center"><spring:message code="nodata.title" text="No data to display!" /></h2>
		</div>
		</div></div>
		<!-- END CHART -->
		</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />