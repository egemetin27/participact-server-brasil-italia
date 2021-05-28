<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="it.unibo.paserver.domain.UniCourse" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- TITLE -->
<div class="form-group has-error">
	<label class="col-md-2 control-label bold"><spring:message code="name.title" text="Name" /></label>
	<div class="col-md-5">
		<input type="text" ng-model="form.name" class="form-control"  maxlength="255" required="required" />
	</div>
	<div class="col-md-2">
		<select class="selectpicker" data-live-search="true" id="uniCourseId" data-style="btn-default" ng-model="form.uniCourseId" title="<spring:message code="education.level.title" text="Education Level" />">
		  <option></option>
		  <option value="<%=UniCourse.CHILD_EDUCATION.name()%>"><spring:message code="education.child.title" text="Child Education" /></option>
		  <option value="<%=UniCourse.ELEMENTARY_SCHOOL.name()%>"><spring:message code="education.elementary.title" text="Elementary School" /></option>
		  <option value="<%=UniCourse.HIGH_SCHOOL.name()%>"><spring:message code="education.high.title" text="High School" /></option>
		  <option value="<%=UniCourse.TECHNICAL_EDUCATION.name()%>"><spring:message code="education.techincal.title" text="Technical Education" /></option>
		  <option value="<%=UniCourse.GRADUATION.name()%>"><spring:message code="education.graduation.title" text="Degree" /></option>
		  <option value="<%=UniCourse.MASTER_DEGREE.name()%>"><spring:message code="education.master.title" text="Master's Degree" /></option>
		  <option value="<%=UniCourse.DOCTORATE_DEGREE.name()%>"><spring:message code="education.doctorate.title" text="Doctorate Degree" /></option>
		  <option value="<%=UniCourse.POSTDOCTORAL.name()%>"><spring:message code="education.postdoctoral.title" text="PhD" /></option>
		</select>	
	</div>
</div>
<!-- TEXT -->	
<div class="form-group">
	<div class="col-md-offset-2 col-md-7">
		<textarea rows="6" cols="" class="form-control" ng-model="form.description"></textarea>
	</div>
</div>					
