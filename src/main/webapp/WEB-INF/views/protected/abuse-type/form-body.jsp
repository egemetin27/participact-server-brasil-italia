<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="it.unibo.paserver.domain.UniCourse" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- TITLE -->
<div class="form-group"  ng-class="formAbuseType.name.$invalid?'has-error':'has-success'">
	<label class="col-md-2 control-label bold"><spring:message code="name.title" text="Name" /></label>	
	<div class="col-md-5">
		<input type="text" ng-model="form.name" name="name" id="name" class="form-control"  maxlength="255" required="required" />
	</div>
</div>
<!-- TEXT -->	
<div class="form-group">
	<div class="col-md-offset-2 col-md-7">
		<textarea rows="6" cols="" class="form-control" ng-model="form.description"></textarea>
	</div>
</div>					
