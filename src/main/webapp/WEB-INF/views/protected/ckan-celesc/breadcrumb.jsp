<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="portlet-title">
	<div class="caption"><span class="caption-subject font-green sbold uppercase"><spring:message code="ckan.celesc.title" text="Celesc" /></span></div>
	<div class="pull-right" name="breadcrumbForm">
		<button title="<spring:message code="export.title" text="Export" />" type="button" class="btn blue-steel" ng-show="items.length > 0" ng-click="downloadCkanCelesc();"><i class="fa fa-download"></i></button>
	</div>
</div>