<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/1.0/css/bootstrap.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/1.0/css/bootstrap-responsive.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/1.0/css/docs.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/1.0/css/font-awesome.min.css"/>">
<script src="<c:url value="/resources/1.0/js/jquery-1.9.0.js"/>"></script>
<script src="<c:url value="/resources/1.0/js/bootstrap.js"/>"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
	<div class="portlet">
	<div class="portlet-title">
		<div class="caption">
		<span class="caption-subject font-green sbold uppercase"><spring:message code="conf.title" text="Confirm Device" /></span>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span12">
				<div class="page-header">
					<h3>
						Device <c:out value="${userdevice.name}" />  created.
					</h3>
				</div>
				<div class="row">
					<a class="btn btn-primary btn-large "
						href='<c:url value="/protected/userdevice" />'>OK</a>
				</div>
			</div>
		</div>
	</div>
	</div>
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />
</body>
</html>