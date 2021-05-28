<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/login/header.jsp"></jsp:include>    
<!-- BEGIN LOGIN FORM -->
<section>
<spring:url value="/j_spring_security_check" var="login" />
<form name="loginForm" class="login-form" action="${login}" method="post">
    <h3 class="form-title font-green"><spring:message code="label.login.welcome" text="Welcome" /></h3>
    <c:if test="${not empty param.authenticationNok}">
    <div class="alert alert-danger display-show">
        <button class="close" data-close="alert">&times;</button>
        <span><spring:message code="label.login.failed" arguments="${SPRING_SECURITY_LAST_EXCEPTION.message}" /></span>
    </div>
    </c:if>
    <div class="form-group">
        <label class="control-label visible-ie8 visible-ie9"><spring:message code="label.login.username" text="Username" /></label>
        <input class="form-control form-control-solid placeholder-no-fix" ng-model="form.username" type="text" autocomplete="on" placeholder="<spring:message code="label.login.username" text="Username" />" name="j_username" required/> </div>
    <div class="form-group">
        <label class="control-label visible-ie8 visible-ie9"><spring:message code="label.login.password" text="Password" /></label>
        <input class="form-control form-control-solid placeholder-no-fix" ng-model="form.password" type="password" autocomplete="off" placeholder="<spring:message code="label.login.password" text="Password" />" name="j_password" required/> 
     </div>
    <div class="">
        <button type="submit" class="btn green uppercase" ng-disabled="loginForm.$invalid"><spring:message code="label.login.signin" text="Login" /></button>
        <a href="<c:url value="/recoverpassword"/>" id="forget-password" class="forget-password"><spring:message code="label.login.forgot" text="Forgot password?" /></a>
    </div>
</form>
</section>
<!-- END LOGIN FORM -->
<jsp:include page="/WEB-INF/templates/login/footer.jsp"></jsp:include>