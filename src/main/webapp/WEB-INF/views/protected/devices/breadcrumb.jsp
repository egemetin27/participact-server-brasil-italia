<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="portlet-title">
	<div class="caption"><span class="caption-subject font-green sbold uppercase"><spring:message code="devices.title" text="Devices" /></span></div>
	<c:if test="${breadcrumb eq '/protected/devices/index'}">
	<div class="pull-right">
		<a title="<spring:message code="new.title" text="New" />" href="<c:url value="/protected/devices/form"/>" class="btn green" ng-class="(devices.length == '0')?'animated infinite flash':''"><i class="fa fa-plus"></i></a> 
		<button title="<spring:message code="remove.title" text="Remove" />" type="button" class="btn red" ng-show="devices.length > 0" ng-disabled="selected_items == '0'" ng-class="selected_items != '0'?'animated bounce':'';" ng-click="removeSelected('<spring:message code="remove.title" text="Remove" />', '<spring:message code="label.negative.title" text="No" />', '<spring:message code="confirmation.title" text="Are you sure?" />', '<spring:message code="confirmation.remove.devices" text="You will remove the Devices" />', '<spring:message code="confirmation.required.select" text="Please, click inside the box and select the action. You can select one or more." />')"><i class="fa fa-trash"></i></button>
	</div>
	</c:if>
</div>