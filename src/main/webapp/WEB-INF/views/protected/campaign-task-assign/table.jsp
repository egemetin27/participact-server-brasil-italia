<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.UUID" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="table-scrollable" ng-cloak>
	<table class="table table-striped table-bordered table-condensed" id="<%UUID.randomUUID().toString();%>">
		<thead>
			<tr role="row" class="heading">
				<th style="width: 1%"><small><spring:message code="excluded.title" text="Excluded" /></small></th>
				<th class="col-md-4"><spring:message code="protected.participant.name" text="Name" /></th>
				<th class="col-md-2"><spring:message code="protected.participant.username" text="Username" /></th>
				<th class="col-md-3"><spring:message code="education.title" text="Education" /></th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="item in participants" id="participants-tr-{{item[0]}}" ng-cloak ng-class="item.Selected?'warning':'';">
				<td><input type="checkbox" class="group-checkable" ng-model="item.Selected" ng-change="isExcluded('checkbox-{{item[0]}}',${campaign_id}, item[0])" id="checkbox-{{item[0]}}"/></td>
				<td>{{item[1] | stripslashes}}&nbsp;{{item[2] | stripslashes}}
					<small class="help-block">{{item[11] | stripslashes}}</small>
					<small class="help-block">{{item[7] | stripslashes}}&nbsp;{{item[8] | stripslashes}}&nbsp;{{item[9] | stripslashes}}&nbsp;{{item[10] | stripslashes}}</small>
					<small class="help-block">{{item[16].millis | date:'dd/MM/yyyy HH:mm'}}</small>
				</td>
				<td>{{item[3] | stripslashes}}
					<small class="help-block">{{item[5] | stripslashes}}</small>
					<small class="help-block">{{item[6] | stripslashes}}</small></td>
				<td>{{item[13] | stripslashes}}
					<small class="help-block">{{item[14] | stripslashes}}</small>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<!-- BEGIN PAGINATION -->
<jsp:include page="/WEB-INF/templates/partial/paginationPerItem.jsp" />
<!-- END PAGINATION -->