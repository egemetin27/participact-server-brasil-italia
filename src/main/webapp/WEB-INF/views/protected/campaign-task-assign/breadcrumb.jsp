<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="portlet-title">
	<div class="caption">
		<span class="caption-subject font-green sbold uppercase"><i class="fa fa-users"></i>&nbsp;<spring:message code="participants.title" text="Participants" /></span>
		<h6><spring:message code="assign.participants.instructions.message" text="List of users to add to the campaign. Users are identified by their official e-mail."/></h6>
	</div>
	<div class="pull-right">
		<span class="uppercase"><input type="checkbox" class="make-switch" id="isSelectAll" ng-model="assign.isSelectAll" data-on-color="success" data-on-text="<i class='fa fa-users'></i>&nbsp;<spring:message code="all.title" text="All" />&nbsp;" data-off-color="warning" data-off-text="<i class='fa fa-filter'></i>&nbsp;<spring:message code="filter.title" text="Filter" />&nbsp;"></span>
		<small class="help-block"><spring:message code="assign.participants.help.message" text="Select the profile of the participants or all." /></small>
	</div>
</div>