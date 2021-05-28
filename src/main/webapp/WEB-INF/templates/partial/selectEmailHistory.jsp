<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<section ng-controller="MailingHistoryCtrl">
<select class="selectpicker pull-right form-control" data-live-search="true" id="emailHistoryId" data-size="20" data-style="btn-primary" ng-model="form.emailHistoryId" ng-change="getMailingHistory();" title="<spring:message code="subject.title" text="Subject" />">
  <option></option>
  <c:forEach items="${historyEmail}" var="item">
  	<option value="${item.id}" data-subtext="<joda:format value="${item.creationDate}" pattern="dd/MM/yyyy HH:mm" /> " data-id="${item.id}">${item.emailSubject}</option>
  </c:forEach>
</select>
</section>