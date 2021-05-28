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
								<th class="col-md-2"><spring:message code="name.title" text="Name" /></th>
								<th class="col-md-2"><spring:message code="date.title" text="Data" /></th>
								<c:forEach items="${questions}" var="h">
									<th class="col-md-2 text-left" style="word-wrap: break-word;min-width: 160px;max-width: 160px;white-space:normal; font-size: small;">${h.getQuestion()}</th>
								</c:forEach>
							</tr>
						</thead>
						<tbody ng-repeat="(k0, i0) in responses[${actionId}]">
							<tr ng-repeat="(k1, i1) in i0.dates">
								<td class="text-left">
									<small>{{i0.name}}&nbsp;</small>
									<small class="help-block">{{i0.email}}</small>
								</td>
								<td class="text-left">
									<small class="bold">{{i1}}</small>
								</td>
								<c:forEach items="${questions}" var="r">
									<td class="text-left ">
										<ul class="list-unstyled">
											<li ng-repeat="(k2,i2) in i0[i1]['${r.getId()}']||[]">
										<span ng-show="i2.isPhoto">
                                            <a ng-href="{{i2.answer|userimage}}" data-toggle="lightbox" data-gallery="gallery-{{k0}}" class="col-lg-1 col-md-2 col-sm-1">
                                                <img ng-src="{{i2.answer|userimage}}" class="img-fluid" width="96" />
                                            </a>
                                        </span>
												<span ng-hide="i2.isPhoto">{{i2.answer}}</span>
											</li>
										</ul>
									</td>
								</c:forEach>
							</tr>
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