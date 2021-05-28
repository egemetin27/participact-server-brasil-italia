<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="form-group">
	<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="credentials.title" text="Credentials" /></label>
</div>					
			
<div class="form-group">
	<label class="col-md-2 control-label" ng-class="formSystemEmail.from_email.$invalid?'font-red':'font-green'"><spring:message code="fromEmail.title" text="From Email" /></label>
	<div class="col-md-5">
		<input type="email" class="form-control" name="fromEmail" maxlength="200" ng-model="form.fromEmail" placeholder="<spring:message code="help.fromEmail.title" text="Specify the email address that emails should be sent from." />" required="required">
	</div>
</div>

<div class="form-group">
	<label class="col-md-2 control-label" ng-class="formSystemEmail.fromName.$invalid?'font-red':'font-green'"><spring:message code="fromName.title" text="From Name" /></label>
	<div class="col-md-5">
		<input type="text" class="form-control" name="fromName" maxlength="20" ng-model="form.fromName" placeholder="<spring:message code="help.fromName.title" text="Specify the name that emails should be sent from." />" required="required">
	</div>
</div>

<div class="form-group">
	<label class="col-md-2 control-label" ng-class="formSystemEmail.smtpHost.$invalid?'font-red':'font-green'"><spring:message code="smtpHost.title" text="SMTP Host" /></label>
	<div class="col-md-5"><input type="text" name="smtpHost" maxlength="100" ng-model="form.smtpHost" class="form-control" placeholder="" required="required"></div>
</div>

<div class="form-group">
	<label class="col-md-2 control-label" ng-class="formSystemEmail.smtpPort.$invalid?'font-red':'font-green'"><spring:message code="smtpPort.title" text="SMTP Port" /></label>
	<div class="col-md-5"><input type="number" string-to-number name="smtpPort" maxlength="5" ng-model="form.smtpPort"  min="0" max="6045" class="form-control" placeholder="" required="required"></div>
</div>

<div class="form-group">
	<label class="col-md-2 control-label" ng-class="formSystemEmail.username.$invalid?'font-red':'font-green'"><spring:message code="username.title" text="Username" /></label>
	<div class="col-md-5"><input type="text" name="username" maxlength="150" ng-model="form.username" class="form-control" placeholder="example@gmail.com" required="required"></div>
</div>

<div class="form-group">
	<label class="col-md-2 control-label" ng-class="formSystemEmail.password.$invalid?'font-red':'font-green'"><spring:message code="password.title" text="Password" /></label>
	<div class="col-md-5"><input type="password" name="password" maxlength="20"  ng-model="form.password" class="form-control" placeholder="<spring:message code="password.title" text="Mail password" />" required="required"></div>
</div>

<div class="form-group">
	<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="encryption.title" text="Encryption" /></label>
</div>					
						
<div class="form-group">
	<label class="col-md-2 control-label">&nbsp;</label>
	<div class="col-md-5">
		<div class="col-md-12">
			<div class="mt-radio-list">
				<label class="mt-radio mt-radio-outline">
					<input type="radio" name="optionsRadios" id="optionsRadios22" value="NO" ng-model="form.encryption"><spring:message code="no.encryption.title" text="No encryption" /><span></span>
				</label> 
				<label class="mt-radio mt-radio-outline">
					<input type="radio" name="optionsRadios" id="optionsRadios23" value="SSL" ng-model="form.encryption"><spring:message code="encryption.ssl.title" text="Use SSL encryption" /> <span></span>
				</label> 
				<label class="mt-radio mt-radio-outline"> 
					<input type="radio" name="optionsRadios" id="optionsRadios24" value="TLS" ng-model="form.encryption"><spring:message code="encryption.tls.title" text="Use TLS encryption" /> <span></span>
				</label>
				<small class="help-block"><spring:message code="help.tls.recommended.title" text="TLS is not the same as STARTTLS. For most servers SSL is the recommended option" /></small>
			</div>									
		</div>
	</div>
</div>
			
<div class="form-group">
	<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="sendingLimits.title" text="Sending limits" /></label>
</div>				

<div class="form-group">
	<label class="col-md-2 control-label" ng-class="formSystemEmail.limitSending.$invalid?'font-red':'font-green'"><spring:message code="limit.title" text="Limit" /></label>
	<div class="col-md-2">
		<input type="number" string-to-number name="limitSending" maxlength="5" min="0" max="2000" ng-model="form.limitSending" class="form-control" placeholder="" required="required">
	</div>
</div>			

<div class="form-group">
	<label class="col-md-2 control-label" ng-class="formSystemEmail.limitPeriod.$invalid?'font-red':'font-green'"><spring:message code="limitPeriod.title" text="Period" /></label>
	<div class="col-md-2">
		<input type="number" string-to-number name="limitPeriod" maxlength="2" min="0" max="24" ng-model="form.limitPeriod" class="form-control" placeholder="" required="required">
	</div>
</div>									

<div class="form-group">
	<label class="col-md-2 control-label" ng-class="formSystemEmail.limitPer.$invalid?'font-red':'font-green'"><spring:message code="messagePer.title" text="Message Per" /></label>
	<div class="col-md-2">
		<select class="form-control" name="limitPer" id="limitPer" ng-model="form.limitPer" required="required">
			<option value="0"><spring:message code="day.title" text="Day" /></option>
			<option value="1"><spring:message code="hour.title" text="Hour" /></option>
			<option value="2"><spring:message code="minute.title" text="Minute" /></option>
		</select>
	</div>
</div>									
						
