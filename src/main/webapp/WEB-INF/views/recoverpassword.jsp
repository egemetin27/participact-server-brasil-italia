<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/login/header.jsp"></jsp:include>    
<!-- BEGIN FORGOT PASSWORD FORM -->
<section>
<spring:url value="/recoverpassword" var="recoverpassword" />
<form name="forgetForm" class="forget-form" action="${recoverpassword}" method="post">
		<h3 class="font-green"><spring:message code="label.title.reset" text="Reset your password" /></h3>
		<p><spring:message code="label.text.reset" text="Enter your email address and we will send you a link to reset your password." /></p>
		<div class="form-group">
			<input class="form-control placeholder-no-fix" type="email" autocomplete="off" ng-model="form.email" placeholder="Email" name="email" required="required" />
			<div ng-show="forgetForm.email.$touched">
				<span class="help-block" ng-show="forgetForm.email.$error.required"><spring:message code="label.login.required.email" text="Can't find that email, sorry." /></span> 
				<span class="help-block" ng-show="forgetForm.email.$error.email"><spring:message code="label.login.invalid.email" text="The email is invalid." /></span>
			</div>
		</div>
		<div class="">
			<a href="<c:url value="/"/>" id="back-btn" class="btn green btn-outline"><spring:message code="label.title" text="Login" /></a>
			<button type="submit" class="btn btn-success uppercase pull-right" ng-disabled="forgetForm.$invalid"><spring:message code="label.button.send_reset" text="Send password reset" /></button>
		</div>
	</form>
</section>
<!-- END FORGOT PASSWORD FORM -->
<jsp:include page="/WEB-INF/templates/login/footer.jsp"></jsp:include>    