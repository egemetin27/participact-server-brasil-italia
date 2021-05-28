<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="it.unibo.paserver.domain.DocumentIdType" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<select class="selectpicker" id="documentIdType" ng-change="onChangeDocumentIdType();" data-live-search="true" ng-model="form.documentIdType" title="<spring:message code="identity.title" text="Identity" />">
	<option></option>
	<option value="<%=DocumentIdType.NATIONAL_ID.name()%>"><spring:message code="nationalid.title" text="National ID" /></option>
	<option value="<%=DocumentIdType.CF.name()%>"><spring:message code="cf.title" text="CF" /></option>
	<option value="<%=DocumentIdType.DRIVING_LICENCE.name()%>"><spring:message code="driving.licence.title" text="Driving Licence" /></option>
	<option value="<%=DocumentIdType.PASSPORT.name()%>"><spring:message code="passport.title" text="Passport" /></option>		
</select>