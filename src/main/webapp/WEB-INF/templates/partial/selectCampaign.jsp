<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="it.unibo.paserver.domain.UniCourse" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="input-group">
	<ui-select ng-model="form.campaign" theme="bootstrap" title="" style="min-width: 300px;" on-select="OnSelectedCampaignId($item)">
	<ui-select-match allow-clear="true" placeholder="">{{$select.selected[1]}}</ui-select-match>
	<ui-select-choices repeat="item in taskList | filter: $select.search" refresh-delay="300" refresh="onAsyncSelectedCampaign($select.search)" minimum-input-length="1">
		<div ng-bind-html="item[1] | highlight: $select.search"></div>
	</ui-select-choices> 
	</ui-select>

	<span class="input-group-btn">
	  <button type="button" ng-click="OnClickCampaignId();" class="btn uppercase" ng-class="filter.input=='FILTER_TASKID'?'btn-default':'btn-warning'">
	    <spring:message code="filter.only.active" text="Only Active" />
	  </button>
	</span>
</div>