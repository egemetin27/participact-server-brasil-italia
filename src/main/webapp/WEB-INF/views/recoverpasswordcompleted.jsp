<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/login/header.jsp"></jsp:include>
<!-- BEGIN REC PASS COMPLET -->
<section><p><spring:message code="label.reset.password.title" text="Check your email" /> (<em class="font-red"><c:out value="${targetemail}" /></em>) <spring:message code="label.reset.password.text" text="for a link to reset your password. If it doesn't appear within a few minutes, check your spam folder." /></p></section>
<a href="<c:url value="/"/>" id="back-btn" class="btn green btn-outline"><spring:message code="label.return.signin" text="Return to sign in" /></a>
<!-- END REC PASS COMPLET -->		
<jsp:include page="/WEB-INF/templates/login/footer.jsp"></jsp:include>