<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="it.unibo.participact.domain.PANotification.Type" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<select class="form-control input-xlarge" id="paNotification" ng-change="onChangePaNotification();" ng-model="paNotification" title="<spring:message code="type.title" text="Type" />" required="required">
	<option value="<%=Type.NEW_VERSION.name()%>"><spring:message code="push.type.new_version.title" text="New Version" /></option>
	<option value="<%=Type.MESSAGE.name()%>"><spring:message code="push.type.message.title" text="Message" /></option>
	<option value="<%=Type.NEWS.name()%>"><spring:message code="push.type.news.title" text="News" /></option>
</select>