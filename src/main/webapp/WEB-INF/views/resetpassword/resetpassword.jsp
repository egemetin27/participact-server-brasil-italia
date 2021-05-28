<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/templates/login/header.jsp"></jsp:include>
<section id="reset">
<form:form modelAttribute="resetPasswordForm" action="${flowExecutionUrl}" method="post">

	<span class="text-justify"><spring:message code="label.reset.change.title" text="Change password for" /> &nbsp; <strong><c:out value="${recoverPassword.user.name}" /> </strong>&nbsp;</span>
	<small class="help-block"><spring:message code="label.password.tips" text="Password must contain one lowercase letter, one number, and be at least 7 characters long." /></small>
	<p>
     <t:password path="password" label="Password*" inputprepend="fa fa-key" />
     <t:password path="confirmPassword" label="Confirm password*" inputprepend="fa fa-key" />
    </p>
    
    <p> 
     <button class="btn btn-primary pull-left" type="submit" name="_eventId_change"><spring:message code="label.reset.change.button" text="Change Password" /></button>
     <button class="btn btn-default pull-right" type="submit" name="_eventId_cancel"><spring:message code="label.button.cancel" text="Cancel" /></button>
    </p> 
</form:form>	
</section>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/login/footer.jsp"></jsp:include>   