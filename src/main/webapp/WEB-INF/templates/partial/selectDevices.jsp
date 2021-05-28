<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<select class="selectpicker" id="device" data-live-search="true" ng-model="form.device" title="<spring:message code="device.title" text="Device" />">
	<option></option>
	<c:forEach items="${devices}" var="item">
	<option value="${item.model}" data-subtext="${item.brand}">${item.model}</option>
	</c:forEach>						
</select>