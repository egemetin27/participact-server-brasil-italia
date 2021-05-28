<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<select class="selectpicker" id="filterBy" ng-change="onChangeFilterBy();" data-live-search="true" data-size="60"  ng-model="form.filterBy" title=" &nbsp; &nbsp;<spring:message code="filter.title" text="Filter by" />&nbsp;&nbsp;<spring:message code="by.title" text="by" /> &nbsp; &nbsp;">
	<option></option>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<option value="FILTER_PARENT_ID"><spring:message code="created.by.title" text="Created by" /></option>
	</sec:authorize>
	<option value="FILTER_NAME"><spring:message code="filter.name" text="Name" /></option>
	<option value="FILTER_DESCRIPTION"><spring:message code="description.title" text="Notes" /></option>
	<option value="FILTER_START"><spring:message code="fromof.title" text="From" /></option>
	<option value="FILTER_DEADLINE"><spring:message code="until.title" text="Until" /></option>
	<c:if test="${!isDashboard}">
		<option value="FILTER_PIPELINE_TYPE"><spring:message code="sensors.title" text="Sensors" /></option>
	</c:if>
	<option value="FILTER_CANBEREFUSED"><spring:message code="campaign.refused.title" text="Can be refused" /></option>
	<option value="FILTER_HAS_PHOTO"><spring:message code="photo.title" text="Photos" /></option>	
	<option value="FILTER_HAS_QUESTION"><spring:message code="questionnaire.title" text="Questionnaire" /></option>		
</select>