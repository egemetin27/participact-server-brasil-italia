<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<section ng-cloak>
<c:choose>
	<c:when test="${controller eq 'DashboardController'}">
		<div class="margin-top-20"></div>
	</c:when>
	<c:otherwise>
		<h3 class="page-title"><spring:message code="summary.title" text="Summary" /><small>&nbsp;&nbsp;<spring:message code="statistics.state.title" text="Task state statistics" /></small></h3>
	</c:otherwise>
</c:choose>
<c:choose>
<c:when test="${controller eq 'DashboardController'}"><div class="row"></c:when>
<c:otherwise><div class="row" ng-controller="CampaignTaskStatsCtrl" ng-init="getTotalByState(${id});"></c:otherwise>
</c:choose>
<div class="col-lg-3 col-md-2 col-sm-6 col-xs-12">
	<div class="dashboard-stat2 ">
		<div class="display">
			<div class="number"><h3 class="font-blue"><span data-counter="counterup" data-value="{{stats.ByState.ACCEPTED}}">{{stats.ByState.ACCEPTED<c:if test="${!isDashboard}">+stats.ByState.COMPLETED</c:if>}}</span></h3><small class="uppercase"><spring:message code="statistics.state.accepted" text="Accepted" /></small></div>
			<div class="icon"><i class="icon-like"></i></div>
		</div>
		<div class="progress-info">
			<div class="progress"><span style="width: {{percentage.ACCEPTED}}%;" class="progress-bar progress-bar-success blue"> <span class="sr-only">{{percentage.ACCEPTED}}% </span></span></div>
		</div>
	</div>
</div>
<div class="col-lg-3 col-md-2 col-sm-6 col-xs-12">
	<div class="dashboard-stat2 ">
		<div class="display">
			<div class="number"><h3 class="font-red-soft"><span data-counter="counterup" data-value="{{stats.ByState.REJECTED}}">{{stats.ByState.REJECTED}}</span></h3><small class="uppercase"><spring:message code="statistics.state.rejected" text="Rejected" /></small></div>
			<div class="icon"><i class="icon-dislike"></i></div>
		</div>
		<div class="progress-info">
			<div class="progress"><span style="width: {{percentage.REJECTED}}%;" class="progress-bar progress-bar-success red-soft"> <span class="sr-only">{{percentage.REJECTED}}% </span></span></div>
		</div>
	</div>
</div>
<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
	<div class="dashboard-stat2 ">
		<div class="display">
			<div class="number"><h3 class="font-green-sharp"><span data-counter="counterup" data-value="{{stats.ByState.AVAILABLE}}">{{stats.ByState.AVAILABLE}}</span></h3><small class="uppercase"><spring:message code="statistics.state.available" text="Available" /></small></div>
			<div class="icon"><i class="icon-pie-chart"></i></div>
		</div>
		<div class="progress-info">
			<div class="progress"><span style="width: {{percentage.AVAILABLE}}%;" class="progress-bar progress-bar-success green-sharp"><span class="sr-only">{{percentage.AVAILABLE}}%</span></span></div>
		</div>
	</div>
</div>

<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
	<div class="dashboard-stat2 ">
		<div class="display">
			<div class="number"><h3 class="font-grey-mint"><span data-counter="counterup" data-value="{{stats.ByState.COMPLETED}}">{{stats.ByState.COMPLETED}}</span></h3><small class="uppercase"><spring:message code="statistics.state.completed" text="Completed" /></small></div>
			<div class="icon"><i class="icon-check"></i></div>
		</div>
		<div class="progress-info">
			<div class="progress"><span style="width: {{percentage.COMPLETED}}%;" class="progress-bar progress-bar-success grey-mint"> <span class="sr-only">{{percentage.COMPLETED}}% </span></span></div>
		</div>
	</div>
</div>
<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
	<div class="dashboard-stat2 ">
		<div class="display">
			<div class="number"><h3 class="font-yellow-crusta"><span data-counter="counterup" data-value="{{stats.ByState.NONE}}">{{stats.ByState.NONE}}</span></h3><small class="uppercase"><spring:message code="statistics.state.none" text="Others" /></small></div>
			<div class="icon"><i class="icon-tag"></i></div>
		</div>
		<div class="progress-info">
			<div class="progress"><span style="width: {{percentage.NONE}}%;" class="progress-bar progress-bar-success yellow-crusta"> <span class="sr-only">{{percentage.NONE}}% </span></span></div>
		</div>
	</div>
</div>
</div>
<!-- END TILES -->
<!-- END PAGE TITLE-->
</section>