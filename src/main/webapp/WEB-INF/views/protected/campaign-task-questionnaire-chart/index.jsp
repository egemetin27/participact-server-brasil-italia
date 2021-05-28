<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CampaignTaskQuestionnaireCtrl" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/campaign-task-questionnaire-chart/breadcrumb.jsp" />
		<!-- BEGIN TABLE -->
		<div class="portlet-body">
			<!-- BEGIN FORM BODY -->
			<c:forEach items="${questions}" var="h">
				<c:if test="${h.getIsClosedAnswers()||h.getIsMultipleAnswers()||h.getPhoto()}">
					<div class="panel " ng-class="${h.getIsMultipleAnswers()}?'panel-success':'panel-info'">
						<div class="panel-heading">${h.getQuestionOrder()+1})&nbsp;${h.getQuestion()}</div>
						<div class="panel-body" ng-init="initCampaignTaskQuestionnaireChart(${h.getId()}, ${h.getPhoto()});">
							<!-- DEFAULT LOADING ... -->
							<div class="text-center" id="pa-painel-hourglass-${h.getId()}"><div class="lds-hourglass"></div></div>
							<!-- CHART -->
							<div id="pa-painel-chart-${h.getId()}"></div>
							<!-- GALLERY -->
							<div id="pa-painel-galleria-${h.getId()}" class="galleria"></div>
							<!-- NO DATA -->
							<div id="pa-painel-no-data-${h.getId()}" class="text-center hidden"><h4><spring:message code="nodata.title" text="No data to display!" /></h4></div>
						</div>
					</div>
				</c:if>
			</c:forEach>
			<!-- END FORM BODY -->			
		</div>
		<!-- END TABLE -->
		</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />