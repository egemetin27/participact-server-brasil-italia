<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<section ng-controller="ParticipantCtrl" ng-cloak>
<div class="row" ng-controller="CampaignTaskAssignCtrl" ng-init="getCampaignTaskAssign(${campaign_id},${assign_available});">
<div class="col-md-12">
	<div class="portlet">
	<!-- BREADCRUMB -->
	<jsp:include page="/WEB-INF/views/protected/campaign-task-assign/breadcrumb.jsp" />
	<!-- BEGIN FORM -->
	<div class="portlet-body">
	<!-- BEGIN FORM BODY -->
     <div class="portlet portlet-fit">
	<!-- LIST -->	     
     <div class="portlet-body margin-top-20">
     	<input type="text" class="hide" ng-show="false" id="isCampaignTaskAssignForm" value="true" />
         <div class="mt-element-list">
             <div class="mt-list-head list-default green-haze">
                 <div class="row">
                     <div class="col-xs-8">
                         <div class="list-head-title-container">
                             <h3 class="list-title uppercase sbold">${campaign_name}</h3>
                             <div class="list-date"><c:if test="${campaign_canBeRefused}"><small class="help-block"><mark><spring:message code="campaign.refused.message" text="This task can be refused by users" /></mark></small></c:if></div>
                         </div>
                     </div>
                     <div class="col-xs-4">
                         <div class="list-head-summary-container">
                             <div class="list-pending">
                                 <div class="badge badge-default list-count">${assign_available}</div>
                                 <div class="list-label"><spring:message code="available.title" text="Available" /></div>
                             </div>
                             <div class="list-done">
                                 <div class="list-count badge badge-default last"><span ng-show="assign.isSelectAll">${assign_available}</span><span ng-hide="assign.isSelectAll">{{totalItems||assign.assign_selected}}</span></div>
                                 <div class="list-label"><spring:message code="selected.title" text="Selected" /></div>
                             </div>
                         </div>
                     </div>
                 </div>
             </div>
             <div class="mt-list-container list-default">
                 <ul><!-- ALL -->
                 	<li class="mt-list-item font-green" ng-show="assign.isSelectAll">
                         <div class="list-item-content">
                             <h4 class="uppercase bold font-green"><span class="label label-primary">${assign_available}</span>&nbsp;&nbsp;<spring:message code="selected.users.title" text="Selected all active users." /></h4>
                         </div>
                     </li>
                     <!-- FILTER -->
                     <li class="mt-list-item" ng-show="!assign.isSelectAll" style="padding: 0px !important;">
						<jsp:include page="/WEB-INF/views/protected/campaign-task-assign/filter.jsp" />
                     </li>
                 </ul>
             </div>
         </div>
     </div>
	<!-- BEGIN TABLE -->
	<div class="portlet-body" ng-show="participants.length > '0'&&!assign.isSelectAll">
		<jsp:include page="/WEB-INF/views/protected/campaign-task-assign/table.jsp" />
	</div>
	<!-- END TABLE -->
    </div>
	</div>
	<!-- END FORM -->
	<div class="portlet-body">
		<!-- BUTTON -->
		<div class="form-group margin-top-40 margin-bottom-10 col-md-9">		
			<button type="button" class="btn green col-xs-6 col-sm-4 col-md-offset-3 col-md-2 col-lg-2 margin-right-10" ng-click="saveCampaignTaskAssign(${campaign_id});" ng-disabled="totalItems=='0'&&!assign.isSelectAll"><spring:message code="save.title" text="Save" /></button>
			<a href="<c:url value="/protected/campaign-task/index/${campaign_id}"/>" class="btn default col-xs-6 col-sm-4 col-md-2 col-lg-2 pull-right" id="fixgoback"><spring:message code="goback.title" text="Go Back" /></a>
		</div>		
	</div>	
	</div>	
</div>
</div>
</section>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />	