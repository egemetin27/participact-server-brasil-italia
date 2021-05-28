<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/login/header.jsp"></jsp:include>    
<!-- BEGIN PAGE-->
<section id="reset"><p><spring:message code="label.reset.error.expire" text="This request to reset your password has expired or is invalid. If you need to reset your password, please," /> <a href='<c:url value="/recoverpassword"/>'><spring:message code="label.click.here" text="click here" /></a>.</p></section>
<a href="<c:url value="/"/>" id="back-btn" class="btn green btn-outline"><spring:message code="login.title" text="Login" /></a>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/login/footer.jsp"></jsp:include>   