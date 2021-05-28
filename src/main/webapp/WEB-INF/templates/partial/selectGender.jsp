<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="it.unibo.paserver.domain.Gender" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<select class="selectpicker" id="gender" data-live-search="true" ng-change="onChangeGender();" ng-model="form.gender" title="<spring:message code="gender.title" text="Gender" />">
	<option></option>
	<option value="<%=Gender.FEMALE.name()%>"><spring:message code="female.title" text="Female" /></option>
	<option value="<%=Gender.MALE.name()%>"><spring:message code="male.title" text="Male" /></option>
	<option value="<%=Gender.OTHER.name()%>"><spring:message code="other.title" text="Other" /></option>
</select>