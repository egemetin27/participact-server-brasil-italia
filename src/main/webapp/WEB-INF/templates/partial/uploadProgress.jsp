<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<small class="help-block text-info em animated flash infinite" ng-show="progress.length">{{progress}}</small>
<small class="help-block text-danger animated bounce" ng-show="breadcrumbForm.file.$error.maxSize"><spring:message code="error.file.large" text="File too large" />{{errorFile.size /1000000|number:1}}MB: max 5MB</small>