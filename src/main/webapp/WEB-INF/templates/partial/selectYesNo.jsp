<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="it.unibo.paserver.domain.Gender" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<select class="selectpicker" id="yesandnot" data-live-search="true" ng-change="onChangeYesNo();" ng-model="form.yesandnot" title="<spring:message code="yes.title" text="Yes" />/<spring:message code="no.title" text="No" />">
	<option></option>
	<option value="YES"><spring:message code="yes.title" text="Yes" /></option>
	<option value="NOT"><spring:message code="no.title" text="No" /></option>
</select>