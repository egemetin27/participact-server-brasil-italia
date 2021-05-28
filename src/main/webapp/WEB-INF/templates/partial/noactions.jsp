<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row"><div class="col-md-12">
<div class="col-md-12 well text-center">
	<div class="margin-top-20">
		<i class="icon-ghost fa-5x animated rollIn" ng-class="{rollOut: hover}" ng-mouseenter="hover = true"></i>
		<ul class="list-unstyled">
		<li class="bold"><p><spring:message code="add.tasks.help.message" text="Add task to campaign" /></p></li>
		<li><i class="fa fa-wifi"></i>&nbsp;<spring:message code="passive.sensing.help.message" text="Add a passive sensing action" /></li>
		<li><i class="fa fa-magic"></i>&nbsp;<spring:message code="activity.detection.help.message" text="Add a activity detection action" /></li>
		<li><i class="fa fa-camera"></i>&nbsp;<spring:message code="photo.action.help.message" text="Add a photo action" /></li>
		<li><i class="fa fa-list-ul"></i>&nbsp;<spring:message code="questionnaire.action.help.message" text="Add a questionnaire action" /></li>
		<li><i class="fa fa-map"></i>&nbsp;<spring:message code="notification.area.help.message" text="Select a notification area" /></li>
		<li><i class="fa fa-map-marker"></i>&nbsp;<spring:message code="activation.area.help.message" text="Select an activation area" /></li>
		<li><i class="fa fa-users"></i>&nbsp;<spring:message code="assign.users.help.message" text="Assign task to users" /></li>
		</ul>
	</div>
</div>
</div></div>