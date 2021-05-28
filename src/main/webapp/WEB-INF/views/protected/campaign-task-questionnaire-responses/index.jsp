<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CampaignTaskQuestionnaireCtrl" ng-init="initPagination();initCampaignTaskQuestionnaireResponses(${campaign_id},${action_id});" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/campaign-task-questionnaire-responses/breadcrumb.jsp" />
		<!-- BEGIN TABLE -->
		<div class="portlet-body" ng-show="responses.length > '0'">
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
					<tbody ng-repeat="(k0, i0) in responses">
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
                                            <a ng-href="{{i2.isPhoto?i2.answer:null|userimage}}" data-toggle="{{i2.isPhoto?'lightbox':''}}" data-gallery="gallery-{{k0}}" class="help-block">
												<img ng-src="{{i2.isPhoto?i2.answer:null|userimage}}" width="64" class="img-thumbnail" />
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
			<!-- BEGIN PAGINATION -->
			<jsp:include page="/WEB-INF/templates/partial/paginationPerItem.jsp" />
			<!-- END PAGINATION -->			
			<!-- END FORM BODY -->			
		</div>
		<!-- END TABLE -->
		<!-- BEGIN DISPLAY -->
		<div class="portlet-body" ng-show="responses.length == '0'" id="no-data-to-display">
		<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
		</div>
		<!-- END DISPLAY -->		
		</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />