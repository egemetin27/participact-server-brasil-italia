<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="FeedbackReportCtrl" ng-init="initFeedbackReport();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/feedback-report/breadcrumb.jsp" />
		<!-- FILTER -->
		<div class="portlet-body">
		<div class="row"><div class="col-md-12">
		<div class="pull-right">
			<form class="form-inline" role="form">
				<div class="form-group">
					<small class="help-block">&nbsp;</small>
					<input type="text" class="form-control input pa-filter col-md-2" ng-model="form.search" placeholder="<spring:message code="search.title" text="Search" />">
				</div>
				<div class="form-group">
					<small class="help-block">&nbsp;</small>
					<select class="form-control" ng-model="form.feedback_type_id">
						<option value="-1"><spring:message code="all.title" text="All" /></option>
						 <c:forEach items="${types}" var="type">						
							<option value="${type.id}">${type.name}</option>
					     </c:forEach>							
					</select>
				</div>
				<div class="form-group">
					<small class="help-block"><spring:message code="start.date.title" text="Start Date" /></small>
					<input type="text" class="form-control input pa-filter col-md-2" ng-model="form.start" placeholder="dd/mm/aaaa" ui-date-mask="DD/MM/YYYY" parse="false" />
				</div>
				<div class="form-group">
					<small class="help-block"><spring:message code="end.date.title" text="End Date" /></small>
					<input type="text" class="form-control input pa-filter col-md-2" ng-model="form.end" placeholder="dd/mm/aaaa" ui-date-mask="DD/MM/YYYY" parse="false" />
				</div>
				<div class="form-group">
					<small class="help-block">&nbsp;</small>
					<button title="<spring:message code="search.title" text="Search" />" class="btn blue" type="button" ng-click="initFeedbackReport();"><i class="fa fa-search"></i></button>
				</div>
			</form>
			</div>
		</div></div>	
		</div>		
		<!-- BEGIN TABLE -->
		<div class="portlet-body" ng-show="feedbacks.length > '0'">
			<div class="table-scrollable">
				<table class="table table-striped table-bordered table-hover table-checkable" id="<%UUID.randomUUID().toString();%>">
					<thead>
						<tr role="row" class="heading">
							<th width="2%"><label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="group-checkable" ng-model="selectedAll" ng-click="checkAll()" /> <span></span></label></th>
							<th class="col-md-6"><spring:message code="name.title" text="Name" /></th>
							<th class="col-md-2"><spring:message code="feedback.type.title" text="Type" /></th>
							<th class="col-md-2"><spring:message code="comment.title" text="Comment" /></th>
							<th class="col-md-2"><spring:message code="created.title" text="Created" /></th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="(key, item) in feedbacks" id="feedbacks-tr-{{item[0]}}">
							<td><input type="checkbox" class="group-checkable" ng-model="item.Selected" ng-change="isSelected('checkbox-{{item[0]}}')" id="checkbox-{{item[0]}}"/></td>
							<td ng-click="editFeedbackReport(item[0]);" class="qz-pointer">{{item[1] | stripslashes}}
								<small class="help-block">{{item[2] | stripslashes}}</small>
								<ul class="list-unstyled list-inline" ng-init="initStorageFiles(key, item[0]);">
									<li ng-repeat="(fl_key, fl_item) in item.files"><img ng-src="{{fl_item[1]}}" class="img-rounded" alt="{{fl_key}}" width="36"></li>
								</ul>
							</td>
							<td ng-click="editFeedbackReport(item[0]);" class="qz-pointer">{{item[4]|stripslashes}}</td>
							<td ng-click="editFeedbackReport(item[0]);" class="qz-pointer">{{item[3]|stripslashes}}</td>
							<td ng-click="editFeedbackReport(item[0]);" class="qz-pointer">{{item[5].millis | date:'dd/MM/yyyy HH:mm:ss'}}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- BEGIN PAGINATION -->
			<jsp:include page="/WEB-INF/templates/partial/paginationPerItem.jsp" />
			<!-- END PAGINATION -->		
		</div>
		<!-- END TABLE -->
		{{pages}}
		<!-- BEGIN DISPLAY -->
		<div class="portlet-body" ng-show="feedbacks.length == '0'" id="no-data-to-display">
		<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
		</div>
		<!-- END DISPLAY -->	
		</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />