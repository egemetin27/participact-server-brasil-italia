<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<select class="selectpicker pull-right form-control" data-live-search="true" id="emailSystemId" data-size="20" ng-model="form.emailSystemId" title="<spring:message code="fromEmail.title" text="From Email" />">
  <option></option>
  <c:forEach items="${systemEmail}" var="item">
  <option value="${item.id}" data-subtext="${item.smtpHost}" data-id="${item.id}">${item.fromName}</option>
  </c:forEach>
</select>