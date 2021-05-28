<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="portlet-title">
	<c:choose>
		<c:when test="${breadcrumb eq '/protected/participant/details'}">
			<div class="caption"><span class="caption-subject font-green sbold uppercase"><spring:message code="details.title" text="Details" /></span></div>
			<!--  RETURN -->
			<a href="javascript:history.back();" class="btn default pull-right" role="button"><i class="fa fa-angle-left"></i>&nbsp;&nbsp;<spring:message code="goback.title" text="Back" />&nbsp;&nbsp;</a>
		</c:when>    
		 <c:otherwise>
		<div class="caption"><span class="caption-subject font-green sbold uppercase"><spring:message code="participants.title" text="Participants" /></span></div>
		</c:otherwise>
	</c:choose>
	<c:if test="${breadcrumb eq '/protected/participant/index'}">
	<form class="pull-right" name="breadcrumbForm">
		<a title="<spring:message code="new.title" text="New" />" href="<c:url value="/protected/participant/form"/>" class="btn green" ng-class="(participants.length == '0')?'animated infinite flash':''"><i class="fa fa-plus"></i></a>
		<button ng-controller="FileUploadCtrl" ng-click="openUploadForm('${controller}')" title="<spring:message code="import.title" text="Import" />" class="btn green-seagreen animated bounce"><i class="fa fa-cloud-upload"></i></button>
		<button title="<spring:message code="export.title" text="Export" />" type="button" class="btn blue-steel" ng-disabled="participants.length=='0'" ng-class="participants.length>0?'animated bounce':'';" ng-click="initCloudDownload();"><i class="fa fa-download"></i></button>
		<button title="<spring:message code="remove.title" text="Remove" />" type="button" class="btn red" ng-show="participants.length > 0" ng-disabled="selected_items == '0'" ng-class="selected_items != '0'?'animated bounce':'';" ng-click="removeSelected('<spring:message code="remove.title" text="Remove" />', '<spring:message code="label.negative.title" text="No" />', '<spring:message code="confirmation.title" text="Are you sure?" />', '<spring:message code="confirmation.remove.users" text="You will remove the Users" />', '<spring:message code="confirmation.required.select" text="Please, click inside the box and select the action. You can select one or more." />')"><i class="fa fa-trash"></i></button>
	</form>
	</c:if>
	
	<c:if test="${breadcrumb eq '/protected/campaign-fixed/form'}">
	<a href="<c:url value="/protected/campaign-fixed/index"/>" class="btn default col-xs-6 col-sm-4 col-md-2 col-lg-2 pull-right" id="fixgoback"><i class="icon-flag"></i>&nbsp;<spring:message code="campaign.fixed" text="Fixed Campaign" /></a>
	</c:if>
</div>