<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div ng-controller="CampaignTaskResendCtrl" ng-init="initCampaignTaskResend(${taskId});" ng-cloak>
<div class="margin-bottom-20">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/campaign-task-resend/breadcrumb.jsp" />
		<!-- BEGIN FILTER -->
	    <div class="portlet" ng-controller="ParticipantFilterCtrl" ng-init="initParticipantFilter(${taskId});">
	    	<div class="row">
	    		<!-- FILTER FORM -->
	    		<label class="col-md-12 uppercase bold"><spring:message code="filter.title" text="Filter" /></label>
	    		<div class="col-md-12">
	    			<div class="pull-left" ng-show="!form.isSelectAll">
						<p><jsp:include page="/WEB-INF/views/protected/campaign-task-assign/filter.jsp" /></p>
					</div>
					
					<div class="pull-right">
						<span class="uppercase"><input type="checkbox" class="make-switch" id="isSelectAll" ng-model="form.isSelectAll" data-on-color="success" data-on-text="<i class='fa fa-users'></i>&nbsp;<spring:message code="all.title" text="All" />&nbsp;" data-off-color="warning" data-off-text="<i class='fa fa-filter'></i>&nbsp;<spring:message code="filter.title" text="Filter" />&nbsp;"></span>
						<small class="help-block"><spring:message code="assign.participants.help.message" text="Select the profile of the participants or all." /></small>
					</div>					
				</div>
				<!-- FILTER RESULT -->
				<div class="col-md-12" ng-show="participants.length > '0'&&!form.isSelectAll">
					<div id="isParticipantFilter"></div>
					<div class="table-scrollable" ng-cloak>
						<table class="table table-striped table-bordered table-condensed">
							<thead>
								<tr role="row" class="heading">
									<th class="col-md-4"><spring:message code="protected.participant.name" text="Name" /></th>
									<th class="col-md-2"><spring:message code="protected.participant.username" text="Username" /></th>
									<th class="col-md-3"><spring:message code="education.title" text="Education" /></th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="item in participants" id="participants-tr-{{item[0]}}" ng-cloak>
									<td>{{item[1] | stripslashes}}&nbsp;{{item[2] | stripslashes}}
										<small class="help-block">{{item[11] | stripslashes}}</small>
										<small class="help-block">{{item[7] | stripslashes}}&nbsp;{{item[8] | stripslashes}}&nbsp;{{item[9] | stripslashes}}&nbsp;{{item[10] | stripslashes}}</small></td>
									<td>{{item[3] | stripslashes}}
										<small class="help-block">{{item[5] | stripslashes}}</small>
										<small class="help-block">{{item[6] | stripslashes}}</small></td>
									<td>{{item[13] | stripslashes}}
										<small class="help-block">{{item[14] | stripslashes}}</small>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- BEGIN PAGINATION -->
					<div ng-show="totalItemsU > 10">
						<ul uib-pagination ng-change="pageChanged()" items-per-page="itemsPerPage" total-items="totalItems" ng-model="currentPage" max-size="maxSize" class="pagination-sm" boundary-links="true" rotate="false" num-pages="numPages" previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;" last-text="&raquo;"></ul>
						<div class="pull-right">
						<select class="form-control input-sm" ng-model="radioModel" ng-change="pageChanged()">
							<option value="10">10</option>
							<option value="25">25</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="500">500</option>
							<option value="1000">1000</option>
						</select>
						</div>				
					</div>				
				</div>	
	    	</div>
	    </div>
		<!-- END FILTER -->
				
		<!-- BEGIN FORM -->
		<div class="portlet-body form">
			<div class="row">
			<hr/>
    		<!-- LABEL -->
    		<label class=" col-md-12 uppercase bold margin-bottom-20"><spring:message code="email.title" text="Email" /></label>
			<form class="form-horizontal" role="form" name="formCampaignTaskResend">
				<input type="text" ng-show="false" ng-model="form.id" id="uuid" />
				<input type="hidden" id="HeyUpdateTheResend"/>
				<div class="form-body">
				<!-- BEGIN FORM BODY -->
				<jsp:include page="/WEB-INF/templates/partial/emailForm.jsp" />
				<!-- END FORM BODY -->
				<div class="form-group margin-top-40 margin-bottom-10 col-md-9">
					<button class="btn blue pull-right col-xs-6 col-sm-4 col-md-2 col-lg-2" type="button" ng-click="resendEmail(${taskId});"><i class="fa fa-envelope"></i>&nbsp;<spring:message code="resend.title" text="Resend" /></button>
					<a href="<c:url value="/protected/campaign-task/index/${taskId}"/>" class="btn default col-xs-6 col-sm-4 col-md-2 col-lg-2 pull-left" id="fixgoback"><spring:message code="goback.title" text="Go Back" /></a>
				</div>			
				</div>
			</form>	
			</div>
			<!-- BUTTON -->
		</div>
		<!-- END FORM -->
		</div>
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />