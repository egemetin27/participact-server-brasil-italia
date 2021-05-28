<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<select class="selectpicker " data-live-search="true" ng-change="onChangeInstitutionId();" id="institutionId" data-size="20" ng-model="form.institutionId" title="<spring:message code="institution.title" text="Institution" />"><option></option>
	<option></option>
	<c:forEach items="${institutions}" var="item">
		<option value="${item.id}" data-subtext="${item.address}">${item.name}</option>
	</c:forEach>
</select>