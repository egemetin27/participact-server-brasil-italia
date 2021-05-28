<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%  String lang = (new RequestContext(request)).getLocale().getLanguage(); lang = lang=="en_us"?"en":lang; %>
<%@ page import="java.util.UUID" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CampaignCtrl" ng-cloak>
<div class="col-md-12" ng-controller="DashboardCtrl" ng-init="initDashboard();">
	<div class="portlet">
	<!-- BREADCRUMB -->
	<jsp:include page="/WEB-INF/views/protected/dashboard/breadcrumb.jsp" />
	<!-- BEGIN FILTER -->
	<div class="portlet-body">
		<div class="row"><div class="col-md-12"><div class="pull-right">
		<form class="form-inline" role="form">
		<input type="hidden" id="isDashboard" value="true">
			<section>
				<div class="form-group"><jsp:include page="/WEB-INF/templates/partial/selectFilterByCampaign.jsp" /></div>
				<div class="form-group">
					<div class="input-group">
						<input ng-show="filter.input=='TEXT'" type="text" class="form-control pa-advanced input-large" maxlength="200" placeholder="<spring:message code="value.title" text="Value" />" ng-model="filter.value">
						<input ng-controller="DatePickerCtrl" ng-show="filter.input=='DATEPICKER'" type="text" class="form-control pa-advanced input-large filterpicker-here" readonly="" data-language="<%=lang%>" ng-model="filter.value">
						<div ng-show="filter.input=='FILTER_CANBEREFUSED'||filter.input=='FILTER_HAS_QUESTION'||filter.input=='FILTER_HAS_PHOTO'"><jsp:include page="/WEB-INF/templates/partial/selectYesNo.jsp" /></div>
						<div ng-show="filter.input=='FILTER_PIPELINE_TYPE'"><jsp:include page="/WEB-INF/templates/partial/selectPipelineType.jsp" /></div>
						<div class="input-group-btn"><button title="<spring:message code="filter.add.title" text="Add item" />" type="button" class="btn green" ng-disabled="filter.value.length=='0'||form.filterBy.length=='0'" ng-click="addFilterBy();"><i class="fa fa-search-plus"></i></button></div>
					</div>
				</div>
			</section>
		</form>
		</div></div></div>	
	</div>
	<div class="portlet-body margin-top-20"  ng-show="hashmap.length>'0'">
		<div class="row"><div class="col-md-12"><div class="pull-right">
			<ul class="list-inline"><li ng-repeat="(k,h) in hashmap" class="margin-top-10 "><span class="label label-{{h.label}} margin-top-20">{{h.value}}&nbsp;<i class="fa fa-close qz-pointer" ng-click="removeFilterBy(k);"></i></span></li></ul>
		</div></div></div>		
	</div>	
	<!-- END FILTER -->
	<!-- BEGIN BODY -->
	<div class="portlet-body">
	<jsp:include page="/WEB-INF/views/protected/campaign-task-statistics/index.jsp" />
	</div>
	<div class="portlet-body">
	<highchart id="chartBar" config="chartColumn"></highchart>
	</div>
	<!-- END BODY -->
	</div>
</div>
</div>		
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />