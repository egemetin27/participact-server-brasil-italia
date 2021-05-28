<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<select class="selectpicker pull-right" data-live-search="true" ng-change="onChangeParentUser();" id="parentId" data-size="20" ng-model="form.parentId" title="<spring:message code="created.by.title" text="Created by" />">
	<option></option>
	<c:forEach items="${accounts}" var="item">
		<option value="${item.id}" data-subtext="${item.username}">${item.name}</option>
	</c:forEach>
</select>