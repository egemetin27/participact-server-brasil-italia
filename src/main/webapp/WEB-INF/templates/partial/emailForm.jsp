<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- HISTORY -->
<div <c:if test="${breadcrumb eq '/protected/campaign/form'}">class="col-md-offset-2 col-md-7"</c:if>>
	<small class="help-block"><spring:message code="emailHistory.title" text="History" /></small>
	<jsp:include page="/WEB-INF/templates/partial/selectEmailHistory.jsp" />									
</div>		
<p>&nbsp;</p>
<!-- EMAIL -->
<div <c:if test="${breadcrumb eq '/protected/campaign/form'}">class="col-md-offset-2 col-md-7"</c:if>>
	<small class="help-block"><spring:message code="fromEmail.title" text="Email From" /></small>
	<jsp:include page="/WEB-INF/templates/partial/selectEmailSystem.jsp" />									
</div>									
<p>&nbsp;</p>
<div <c:if test="${breadcrumb eq '/protected/campaign/form'}">class="col-md-offset-2 col-md-7"</c:if>>
	<small class="help-block"><spring:message code="email.subject.title" text="Email subject" /></small>
	<input type="text" ng-model="form.emailSubject" name="emailSubject" class="form-control margin-bottom-40"  maxlength="100" placeholder="<spring:message code="email.subject.invite2" text="[ParticipAct] Invitation - research participation" />"/>
</div>

<div <c:if test="${breadcrumb eq '/protected/campaign/form'}">class="col-md-offset-2 col-md-7" ng-controller="SummernoteCtrl"</c:if>>
	<small class="help-block"><spring:message code="email.text.title" text="Email Text" /></small>
	<summernote config="options" ng-model="form.emailBody" ng-init="form.emailBody='${altBody}'"></summernote>
</div>