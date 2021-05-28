<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<select class="selectpicker" data-live-search="true" ng-change="onChangeSchoolCourse();" id="schoolCourseId" data-size="20" 
	ng-model="form.schoolCourseId" title="<spring:message code="education.course.title" text="Course" />"
	data-container="body">
  <option></option>
  <c:forEach items="${courses}" var="item">
  <option value="${item.id}" data-subtext="${item.name}" data-id="${item.id}" data-education="${item.uniCourseId}">${item.name}</option>
  </c:forEach>
</select>