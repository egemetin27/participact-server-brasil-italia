<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%  String lang = (new RequestContext(request)).getLocale().getLanguage(); lang = lang=="en_us"?"en":lang; %>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--  HEADER -->
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!--  BEGIN PAGE -->
<!-- BEGIN PAGE-->
<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
<div class="row" ng-controller="CampaignFixedCtrl" ng-init="initMap(); getListGpsUser(${userId});" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/participant/breadcrumb.jsp" />
		<!-- FILTER -->
		<div class="portlet-body">
			<!-- BEGIN BODY -->
			<div class="modal-body">
			<!-- BEGIN MAP -->
			<div id="map-canvas" style="height: 400px;"></div>
			<!-- END MAP -->
			<!-- BEGIN TABLE -->
			<section ng-show="items.length > '0'">
			<input type="hidden" id="HeyLOCATION" value="0"/>
			<div class="table-scrollable">
				<table class="table table-striped table-bordered table-hover table-condensed" id="<%UUID.randomUUID().toString();%>">
					<thead>
						<tr role="row" class="heading">
							<th>#</th>
							<th class="col-md-1"><spring:message code="username.title" text="User" /></th>
							<th class="col-md-1 text-center"><spring:message code="timestamp.title" text="Timestamp" /></th>
							<th class="col-md-3 text-center">Latitude</th>
							<th class="col-md-3 text-center">Longitude</th>
							<th class="col-md-3 text-center">Accuracy</th>
							<th class="col-md-3 text-center">Provider</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="(key_data, item_data) in items" id="items-tr-{{item[0]}}">
							<td>{{item_data[0]}}</td>
							<td><small>{{item_data[2]}}</small><small class="help-block">{{item_data[1]}}</small></td>
							<td class="text-center">{{item_data[3].millis | date:'dd/MM/yyyy HH:mm:ss'}}</td>
							<td class="text-center">{{item_data[4]}}</td>
							<td class="text-center">{{item_data[5]}}</td>
							<td class="text-center">{{item_data[6]}}</td>
							<td class="text-center">{{item_data[7]}}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- BEGIN PAGINATION -->
			<jsp:include page="/WEB-INF/templates/partial/paginationPerData.jsp" />
			<!-- END PAGINATION -->		
			</section>	
			<!-- END TABLE -->	
			<!-- BEGIN DISPLAY -->
			<div class="portlet-body" ng-show="items.length == '0'" id="no-data-to-display">
			<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
			</div>
			<!-- END DISPLAY -->	
			</div>			
			<!-- END BODY -->
		</div>
		</div>
</div>
</div>
</sec:authorize>
<!--  END PAGE -->
<!-- FOOTER-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />