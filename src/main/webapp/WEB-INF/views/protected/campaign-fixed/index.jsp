<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%  String lang = (new RequestContext(request)).getLocale().getLanguage(); lang = lang=="en_us"?"en":lang; %>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
<div class="row" ng-controller="CampaignFixedCtrl" ng-init="initCampaignFixed();" ng-cloak>
	<div class="col-md-12">
		<div class="portlet">
			<!-- BREADCRUMB -->
			<jsp:include page="/WEB-INF/views/protected/campaign-fixed/breadcrumb.jsp" />
			<!--  BEGIN FORM -->
			<div class="portlet-body">
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal" role="form" name="formCampaignFixed">
							<input type="text" class="hidden" id="HeyCompaignFixed" />
							<div class="form-group">
								<div class="col-lg-2 col-md-3 col-sm-4 col-xs-12">
									<input type="time" id="intervalTime" name="intervalTime" ng-model="form.intervalTime" class="form-control" required="required" placeholder="hh:mm"/>
									<small class="help-block"><i class="fa fa-clock-o"></i>&nbsp;<spring:message code="interval.time.title" text="Interval Time" />&nbsp;</small>
								</div>
								
								<div class="col-lg-2 col-md-3 col-sm-4 col-xs-12">
									<toggle-switch ng-model="form.isEnabled" on-label="<spring:message code="yes.title" text="Yes" />" off-label="<spring:message code="no.title" text="No" />"></toggle-switch>
									<small class="help-block"><i class="fa fa-power-off" aria-hidden="true"></i>&nbsp;<spring:message code="on.off.title" text="ON / OFF" />&nbsp;</small>
								</div>
								
								<c:if test="${isDeveloper}">
								<div class="col-lg-2 col-md-3 col-sm-4 col-xs-12">
									<toggle-switch ng-model="form.inAppleReview" on-label="<spring:message code="yes.title" text="Yes" />" off-label="<spring:message code="no.title" text="No" />"></toggle-switch>
									<small class="help-block"><i class="fa fa-apple" aria-hidden="true"></i>&nbsp;<spring:message code="in.apple.review" text="App in Apple Review" />&nbsp;</small>
								</div>					
								</c:if>			
								
								<sec:authorize access="hasAnyRole('ROLE_ADMIN')">								
								<div class="col-lg-2 col-md-3 col-sm-12 col-xs-12">
									<button class="btn btn-success btn-block" type="button" ng-click="saveCampaignFixed();"><spring:message code="save.title" text="Save"/></button>
								</div>				
								</sec:authorize>	
							</div>
						</form>					
					</div>
				</div>
			</div>				
		</div>		
		<!--  END FORM -->
		<!-- BEGIN DETAILS -->
		<div class="portlet" ng-init="initParticipantList();" ng-cloak>
			<!-- BEGIN FILTER -->
			<div class="portlet-title">
				<div class="row">
					<div class="col-md-6 col-sm-12 col-xs-12">
						<div class="caption"><span class="caption-subject font-green sbold uppercase"><spring:message code="participants.title" text="Participants" /></span></div>
					</div>
					<div class="col-md-6 col-sm-12 col-xs-12">
						<div class="col-lg-10 col-md-9 col-sm-7 col-xs-12">
						   	<div class="input-group">
						      <input type="text" class="form-control pa-filter" placeholder="<spring:message code="search.title" text="Search" />" ng-model="form.search" />
						      <span class="input-group-btn">
						        <button title="<spring:message code="search.title" text="Search" />" class="btn blue" type="button" ng-click="initParticipantList();"><i class="fa fa-search"></i></button>
						      </span>
						    </div><!-- /input-group -->
					    </div>
					    <button title="<spring:message code="export.title" text="Export" />" type="button" class="btn blue-steel pull-right col-lg-1 col-md-2 col-sm-3 col-xs-12" ng-click="initGpsListExport();"><i class="fa fa-download"></i></button>
					</div>
				</div>	
			</div>	
			<!-- END FILTER -->					
			<!-- BEGIN CONTENT -->
			<!-- BEGIN TABLE -->
			<div class="portlet-body" ng-show="items.length > '0'">
				<div class="table-scrollable">
					<table class="table table-striped table-bordered table-hover table-checkable" id="<%UUID.randomUUID().toString();%>">
						<tbody>
							<tr ng-repeat="item in items">
								<td style="text-align: left !important; margin-left: 10px; padding-left: 10px; "><a href="<c:url value="/protected/campaign-fixed/form/{{item[0]}}"/>">
										{{item[1]}}<br/>{{item[3]}}
								</a></td>
								<td>{{item[17]}}</td>
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
			<div class="portlet-body" ng-show="items.length == '0'" id="no-data-to-display">
			<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
			</div>
			<!-- END DISPLAY -->					
			<!-- END CONTENT -->			
		</div>
	</div>
</div>		
<!--  END DETAILS -->
</div>
</sec:authorize>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />