<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="it.unibo.paserver.domain.SystemPageType" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- TITLE -->
<div class="form-group has-error">
	<label class="col-md-2 control-label bold"><spring:message code="title.title" text="Title" /></label>
	<div class="col-md-5">
		<input type="text" ng-model="form.title" class="form-control"  maxlength="255" required="required" />
	</div>
	<div class="col-md-2">
		<select class="selectpicker" data-live-search="true" id="type_id" data-style="btn-primary" ng-change="isPublish();" ng-model="form.typeId" title="<spring:message code="pages.title" text="Pages" />">
			<option></option>
			<option value="<%=SystemPageType.PAGE_FAQ.ordinal()%>"><spring:message code="page.faq.title" text="FAQ" /></option>
			<option value="<%=SystemPageType.PAGE_ABOUT.ordinal()%>"><spring:message code="page.about.title" text="About" /></option>
			<option value="<%=SystemPageType.PAGE_PRIVACY.ordinal()%>"><spring:message code="page.privacy.title" text="Privacy &#38; Terms" /></option>
			<option value="<%=SystemPageType.PAGE_LICENSE.ordinal()%>"><spring:message code="page.license.title" text="License" /></option>
			<option value="<%=SystemPageType.PAGE_DRAFT.ordinal()%>"><spring:message code="page.draf.title" text="Draf" /></option>
		</select>	
	</div>
</div>

<!-- TEXT -->	
<div class="form-group">
	<div class="col-md-offset-2 col-md-7" ng-controller="SummernoteCtrl">
		<summernote config="options" ng-model="form.content"></summernote>
	</div>
</div>					
