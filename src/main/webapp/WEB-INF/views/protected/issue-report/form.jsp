<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="IssueReportCtrl" ng-cloak>
<c:choose><c:when test="${form eq null}"><span ng-init="getIssueReport();"/></c:when><c:otherwise><span ng-init="setIssueReport(${form})";/></c:otherwise></c:choose>
<div class="col-md-12">
	<div class="portlet">
	<!-- BREADCRUMB -->
	<jsp:include page="/WEB-INF/views/protected/issue-report/breadcrumb.jsp" />
	<!-- BEGIN FORM -->
	<div class="portlet-body form">
		<form class="form-horizontal" role="form" name="formIssueReport">
			<input type="text" ng-show="false" ng-model="form.id" id="uuid" />
				<div class="form-body">
				<!-- BEGIN FORM BODY -->
				<jsp:include page="/WEB-INF/views/protected/issue-report/form-body.jsp" />
				<!-- END FORM BODY -->
				<!-- BUTTON -->
				<div class="form-group margin-top-40 margin-bottom-10 col-md-9">
					<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR','ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR')">
					<button type="button" class="btn green col-xs-6 col-sm-4 col-md-offset-3 col-md-2 col-lg-2 margin-right-10" ng-click="saveIssueReport();"
							ng-disabled="formIssueReport.$invalid"><spring:message code="save.title" text="Save" /></button>
					</sec:authorize>
					<a href="<c:url value="/protected/issue-report/index"/>" class="btn default col-xs-6 col-sm-4 col-md-2 col-lg-2 pull-right" id="fixgoback"><spring:message code="goback.title" text="Go Back" /></a>
				</div>
			</div>
			<!-- BEGIN DEBUG -->
			<div class="col-md-12 margin-top-40">
				<i class="fa fa-bug font-grey pull-right qz-pointer" ng-click="showDebug=!showDebug"></i>
				<ul class="list-unstyled well" ng-show="showDebug">
					<li ng-repeat="(key, errors) in formIssueReport.$error track by $index">
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
<!-- DENUNCIAS -->
<div class="row margin-top-40 margin-bottom-40">
<div class="col-md-offset-1 col-md-10">
	<jsp:include page="/WEB-INF/views/protected/abuse-report/index.jsp" />
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />	