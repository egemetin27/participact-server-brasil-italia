<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- CORPO DO FORMULARIO -->
		<div class="form-group">
			<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="information.title" text="Informations" /></label>
		</div>					
	
		<!-- NAME -->
		<div class="form-group" ng-class="formInstitutions.name.$invalid?'has-error':'has-success'">
			<label class="col-md-2 control-label bold"><spring:message code="name.title" text="Name" /></label>
			<div class="col-md-7">
				<input type="text" ng-model="form.name" name="name" class="form-control"  maxlength="255" placeholder="<spring:message code="institutions.tips.name" text="Headquarters, subsidiary or name, etc." />" required="required" />
			</div>
		</div>
		
	<!-- ADDRESS -->
	<jsp:include page="/WEB-INF/templates/partial/places.jsp" />
					
	<!-- CONTACT -->	
	<div class="form-group">
		<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="contact.title" text="Contact" /></label>
	</div>					
	
	<div class="form-group">
	<label class="col-md-2 control-label"><spring:message code="contact.name.title" text="Contact Name" /></label>
	<div class="col-md-7">
		<input type="text" ng-model="form.contact" class="form-control" placeholder="<spring:message code="contact.name.title" text="Contact Name" />"  maxlength="255" />
	</div>
	</div>
	
	<div class="form-group">
	<label class="col-md-2 control-label"><spring:message code="email.title" text="Email" /></label>
	<div class="col-md-3">
		<input type="email" ng-model="form.email" class="form-control"  maxlength="255"/>
	</div>
	<label class="col-md-1 control-label"><spring:message code="phone.title" text="Phone" /></label>
	<div class="col-md-3">
		<input type="text" ng-model="form.phone" ui-mask="(99) 9999-9999?9" class="input-number form-control" maxlength="30" />
	</div>
	</div>			
	