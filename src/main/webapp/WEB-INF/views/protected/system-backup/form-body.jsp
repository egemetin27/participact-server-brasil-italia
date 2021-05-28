<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="form-group">
	<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="credentials.title" text="Credentials" /></label>
</div>					

<div class="form-group">
	<label class="col-md-2 control-label" ng-class="formSystemEmail.fromName.$invalid?'font-red':'font-green'"><spring:message code="Name.title" text="Name" /></label>
	<div class="col-md-5">
		<input type="text" class="form-control" name="fromName" maxlength="20" ng-model="form.name" placeholder="<spring:message code="name.title" text="Specify the name." />" required="required">
	</div>
</div>

<div class="form-group">
	<label class="col-md-2 control-label" ng-class="formSystemEmail.smtpHost.$invalid?'font-red':'font-green'"><spring:message code="Host.title" text="Server Host" /></label>
	<div class="col-md-5"><input type="text" name="hostname" maxlength="100" ng-model="form.hostname" class="form-control" placeholder="" required="required"></div>
</div>

<div class="form-group">
	<label class="col-md-2 control-label" ng-class="formSystemEmail.smtpPort.$invalid?'font-red':'font-green'"><spring:message code="port.title" text="Server Port" /></label>
	<div class="col-md-5"><input type="number" string-to-number name="port" maxlength="5" ng-model="form.port"  min="0" max="6045" class="form-control" placeholder="" required="required"></div>
</div>

<div class="form-group">
	<label class="col-md-2 control-label" ng-class="formSystemEmail.username.$invalid?'font-red':'font-green'"><spring:message code="username.title" text="Username" /></label>
	<div class="col-md-5"><input type="text" name="username" maxlength="150" ng-model="form.username" class="form-control" placeholder="example@gmail.com" required="required"></div>
</div>

<div class="form-group">
	<label class="col-md-2 control-label" ng-class="formSystemEmail.password.$invalid?'font-red':'font-green'"><spring:message code="password.title" text="Password" /></label>
	<div class="col-md-5"><input type="password" name="password" maxlength="20"  ng-model="form.password" class="form-control" placeholder="<spring:message code="password.title" text="Password" />" required="required"></div>
</div>