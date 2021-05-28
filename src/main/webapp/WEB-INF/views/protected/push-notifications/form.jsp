<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="col-md-12" ng-controller="PushNotificationsCtrl" ng-cloak>
	<div class="portlet">
	<!-- BREADCRUMB -->
	<c:if test="${!isMail}"><jsp:include page="/WEB-INF/views/protected/push-notifications/breadcrumb.jsp" /></c:if>
	<c:if test="${isMail}"><jsp:include page="/WEB-INF/views/protected/mail-notifications/breadcrumb.jsp" /></c:if>
	<!-- BEGIN FORM -->
	<div id="isPushNotification"></div>
	<div class="portlet-body form col-md-offset-2 col-lg-7 col-md-9 col-xs-12 col-sm-12">
		<form class="form" role="form" name="formPushNotifications">
			<input type="hidden" id="isPushNotifications" value="true"/>
			<!-- BEGIN FORM BODY -->
			<!-- LISTA -->
			<div class="form-group margin-top-40 margin-bottom-10">
				<label class="uppercase bold"><spring:message code="participants.title" text="Participants" />&nbsp;(<spring:message code="list.title" text="List" />)</label>
				<br/> 
				<c:choose>
					<c:when test="${userListId>0}">
						<a href="<c:url value="/protected/participant-list/push/${pushId}"/>" class="btn blue btn-block">&nbsp;&nbsp;<i class="fa fa-pencil" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;<spring:message code="participants.title" text="Participants" /></a>
					</c:when>
					<c:otherwise>
						<a href="<c:url value="/protected/participant-list/push/${pushId}"/>" class="btn default btn-block">&nbsp;&nbsp;<i class="fa fa-pencil" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;<spring:message code="choose.following" text="Select ..." /></a>
					</c:otherwise>	
				</c:choose>
			</div>			
			<c:if test="${!isMail}">
				<!-- TEXTO -->
				<div class="form-group margin-top-40 margin-bottom-10"> 
					<label class="uppercase bold"><spring:message code="text.title" text="text" /></label>
					<textarea class="form-control" ng-model="paMessage" rows="3" maxlength="140" required="required" ng-init="paMessage='${paMessage}'"></textarea>
					<small class="help-block">{{paMessage.length||0}}</small>
				</div>
			</c:if>
			<c:if test="${isMail}">
				<input type="text" id="paEmailSystemId" ng-model="form.emailSystemId" ng-init="form.emailSystemId='${emailSystemId}'" class="hide" ng-hide="true"/>
				<input type="text" id="paEmailSubject" ng-model="form.emailSubject" ng-init="form.emailSubject='${emailSubject}'"class="hide" ng-hide="true"/>
				<!-- EMAIL -->	
				<div class="form-group"> 
					<jsp:include page="/WEB-INF/templates/partial/emailForm.jsp" />
				</div>
			</c:if>		
			<!-- END FORM BODY -->
			<p>&nbsp;&nbsp;</p>
			<!-- BUTTON -->
			<div class="form-group margin-top-40 margin-bottom-10">		
				<button type="button" class="btn green col-xs-4 col-sm-4 col-md-2 col-lg-2 " ng-click="savePushNotifications(${pushId});" ng-disabled="formPushNotifications.$invalid"><spring:message code="save.title" text="Save" /></button>
				<c:choose>
					<c:when test="${isReady}">
						<button type="button" class="btn purple col-xs-4 col-sm-4 col-md-2 col-lg-2 animated bounce" ng-click="publishPushNotifications(${pushId});" ng-disabled="formPushNotifications.$invalid"><i class="fa fa-paper-plane" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;<spring:message code="send.title" text="Send" /></button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn purple col-xs-4 col-sm-4 col-md-2 col-lg-2 disabled"><spring:message code="send.title" text="Send" /></button>
					</c:otherwise>	
				</c:choose>	
				<c:if test="${!isMail}">
					<a href="<c:url value="/protected/push-notifications/index"/>" class="btn default col-xs-4 col-sm-4 col-md-2 col-lg-2 pull-right" id="fixgoback"><spring:message code="goback.title" text="Go Back" /></a>
				</c:if>
				<c:if test="${isMail}">
					<a href="<c:url value="/protected/mail-notifications/index"/>" class="btn default col-xs-4 col-sm-4 col-md-2 col-lg-2 pull-right" id="fixgoback"><spring:message code="goback.title" text="Go Back" /></a>
				</c:if>
			</div>			
		</form>	
	</div>
	<!-- END FORM -->	
	</div>	
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />	