<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="it.unibo.paserver.domain.UniCourse" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<input type="text" ng-model="asyncSelected"
	uib-typeahead="id for task in onAsyncSelectedCampaign($viewValue)"
	typeahead-loading="loadingLocations" typeahead-no-results="noResults"
	class="form-control">
