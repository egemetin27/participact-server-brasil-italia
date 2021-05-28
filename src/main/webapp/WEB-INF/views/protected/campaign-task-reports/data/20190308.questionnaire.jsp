<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.util.UUID" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div tabindex="-1" class="modal fade in" id="fullQUESTIONNAIRE${actionId}" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" aria-hidden="true" type="button" data-dismiss="modal"></button>
				<h4 class="modal-title"><jsp:include page="/WEB-INF/views/protected/campaign-task-questionnaire/caption-subject.jsp" /></h4>
			</div>
			<div class="modal-body" ng-controller="CampaignTaskQuestionnaireCtrl" ng-init="initPagination(); initCampaignTaskQuestionnaireResponses(${taskId},${actionId},${userId});">
				<!-- BEGIN FORM BODY -->
				<div class="table-scrollable">
					<table class="table table-striped table-bordered table-hover table-condensed" id="<%UUID.randomUUID().toString();%>">
						<thead>
							<tr role="row" class="heading">
								<th class="col-md-6 text-left"><spring:message code="question.title" text="Question" /></th>
								<th class="col-md-6 text-right"><spring:message code="answer.title" text="Answer" /></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${questions}" var="h">
						
							<tr>
								<td>${h.getQuestion()}<small class="help-block">${h.getId()}</small></td>
								<td>
								<strong ng-repeat="(key, item) in responses[${actionId}]">
									{{item.answers[${h.getId()}]||''}}
								</strong>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>			
				<!-- END FORM BODY -->	
			</div>
			<div class="modal-footer">
				<button class="btn dark btn-outline" type="button" data-dismiss="modal"><spring:message code="close.title" text="Close" /></button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>