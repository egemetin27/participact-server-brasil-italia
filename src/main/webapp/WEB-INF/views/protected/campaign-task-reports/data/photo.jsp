<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div tabindex="-1" class="modal fade in" id="fullPHOTO" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" aria-hidden="true" type="button" data-dismiss="modal"></button>
				<h4 class="modal-title"><jsp:include page="/WEB-INF/views/protected/campaign-task-photo/caption-subject.jsp" /></h4>
			</div>
			<div class="modal-body" ng-controller="CampaignTaskSensingCtrl" ng-init="taskId=${taskId}; actionId=${actionId}; userId=${userId};">
			<!-- BEGIN TABLE -->
			<section ng-show="records.length > '0'">
			<input type="hidden" id="HeyPHOTO" value="0"/>
			<!-- END TABLE -->	
			<div class="row"><div class="col-md-12">
			<div class="col-md-6" ng-repeat="(kf, img) in records">
				<img ng-src="<c:url value="/protected/photo/" />{{img[4]}}" class="img-responsive">
			</div>
			</div></div>
			</section>
			<!-- BEGIN DISPLAY -->
			<div class="portlet-body" ng-show="records.length == '0'" id="no-data-to-display">
				<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
			</div>
			<!-- END DISPLAY -->	
			</div>
			<div class="modal-footer">
				<button class="btn dark btn-outline" type="button" data-dismiss="modal"><spring:message code="close.title" text="Close" /></button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>