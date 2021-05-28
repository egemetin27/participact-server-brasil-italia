<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/login/header.jsp"></jsp:include>   
<!-- BEGIN PAGE-->
<section id="reset">
<h3><spring:message code="label.reset.success.text" text="New password set successfully" />.</h3>
<p><spring:message code="label.title.contact.suport" text="If you run into problems, please contact support by e-mail" />.</p>
</section>
<a href="<c:url value="/"/>" id="back-btn" class="btn green btn-outline"><spring:message code="login.title" text="Login" /></a>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/login/footer.jsp"></jsp:include>   