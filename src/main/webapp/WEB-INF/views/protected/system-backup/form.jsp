<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="SystemBackupCtrl" ng-cloak>
<c:choose><c:when test="${form eq null}"><span ng-init="getSystemBackup();"/></c:when><c:otherwise><span ng-init="setSystemBackup(${form})";/></c:otherwise></c:choose>
<div class="col-md-12">
	<div class="portlet">
	<!-- BREADCRUMB -->
	<jsp:include page="/WEB-INF/views/protected/system-backup/breadcrumb.jsp" />
	<!-- BEGIN FORM -->
	<div class="portlet-body form">
		<form class="form-horizontal" role="form" name="formSystemBackup">
		<input type="text" ng-show="false" ng-model="form.id" id="uuid" />
		<div class="form-body">
		<!-- BEGIN FORM BODY -->
		<jsp:include page="/WEB-INF/views/protected/system-backup/form-body.jsp" />	
		<!-- END FORM BODY -->
		<!-- BUTTON -->
		<div class="form-group margin-top-40 margin-bottom-10 col-md-9">		
			<button ng-hide="form.isActive" type="button" class="btn green col-xs-6 col-sm-4 col-md-offset-3 col-md-2 col-lg-2 margin-right-10" ng-click="saveSystemBackup();" ng-disabled="formSystemBackup.$invalid"><spring:message code="save.title" text="Save" /></button>
			<a href="<c:url value="/protected/system-backup/index"/>" class="btn default col-xs-6 col-sm-4 col-md-2 col-lg-2 pull-right" id="fixgoback"><spring:message code="goback.title" text="Go Back" /></a>
		</div>			
		</div>
		</form>	
	</div>
	<!-- END FORM -->	
	</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />	