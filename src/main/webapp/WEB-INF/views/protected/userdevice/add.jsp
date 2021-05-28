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
<script src="<c:url value="/resources/js/bootstrap-datepicker.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/datepicker.css"/>">
	
</head>
<body>
	<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
	<div class="portlet">
	<div class="portlet-title">
		<div class="caption">
		<span class="caption-subject font-green sbold uppercase"><spring:message code="add.title" text="Add Device" /></span>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/userdeviceSidenav.jsp">
					<jsp:param value="protected.userdevice.add" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h3>New Device</h3>
				</div>
				<c:if test="${not empty formerror}">
					<div class="alert alert-error fade in" id="loginerror">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						Error creating the User's Device. Please check the form.
					</div>
				</c:if>
				<spring:url value="/protected/userdevice/add" var="addDevice"></spring:url>
				<form:form method="POST" modelAttribute="addUserDeviceForm"
					action="${addUser}" enctype="multipart/form-data">
					<fieldset>
						<div class="span9">
   							 <t:input path="name" label="Name" placeholder="Cellulare Principale"/>
   							 <t:select label="Device" path="deviceId" items="${devices}"></t:select>
   							 <t:select label="User" path="userId" items="${users}"></t:select>
   							 <t:input path="uuid" label="UUID" placeholder="1234567890QWERTY" />
   							 <t:input path="priority" label="Priority" placeholder="99"/>
						</div>
						<div class="span2">
							<button class="btn btn-primary" type="submit">Save</button>
							<a class="btn btn-danger" type="submit"
								href='<c:url value="/protected/userdevice"/>'>Cancel</a>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
	</div>
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />
</body>
</html>