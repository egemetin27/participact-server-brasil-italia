<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- BEGIN PAGE TITLE-->
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CampaignTaskStatsCtrl" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/campaign-task/breadcrumb.jsp" />
		<!-- BEGIN DETAILS -->
		<!-- BEGIN DETAILS -->
		<div class="portlet-body">
		<div class="row">
			<!-- DETALHES -->
			<div class="col-md-12 col-sm-12">
				<div class="portlet blue box">
					<div class="portlet-title">
						<div class="caption qz-pointer" ng-click="collapseUserDetails();"><i class="fa" ng-class="checkUserDetails?'fa-angle-down':'fa-angle-right'"></i>&nbsp;${user.getName()}&nbsp;${user.getSurname()}</div>
						<div class="actions"><small><joda:format value="${taskReport.taskResult.lastDataUpdate}" pattern="dd/MM/yyyy HH:mm:ss" /></small></div>
					</div>
					<div class="portlet-body" ng-show="checkUserDetails">
						<div class="row static-info">
							<!-- REPORTS -->
							<div class="col-lg-12 col-md-12 name">
								<div class="table-scrollable">
									<table class="table table-striped table-bordered table-hover table-condensed" id="<%UUID.randomUUID().toString();%>">
										<thead>
											<tr role="row" class="heading">
												<th class="text-center" width="1%">#</th>
												<th class="text-center"><spring:message code="date.title" text="Date" /></th>
												<th class="text-center"><spring:message code="status.title" text="Status" /></th>
											</tr>
										</thead>
										<tbody>
										<c:forEach items="${taskReport.history}" var="entry">
											<tr>
												<td class="text-center"><c:out value="${entry.id}" /></td>
												<td class="text-center"><joda:format value="${entry.timestamp}" pattern="dd/MM/yyyy HH:mm" /></td>
												<td class="text-center"><c:out value="${entry.state}" /></td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
								</div>							
							</div>	
							<!-- MAIL LOGS -->
							<div class="col-lg-12 col-md-12 name">
								<div class="table-scrollable">
									<table class="table table-striped table-bordered table-hover table-condensed" id="<%UUID.randomUUID().toString();%>">
										<thead>
											<tr role="row" class="heading">
												<th class="text-center" width="1%">#</th>
												<th class="text-center"><spring:message code="date.title" text="Date" /></th>
												<th class="text-center"><spring:message code="email.title" text="Email" /></th>
												<th class="text-center"><spring:message code="device.title" text="Device" /></th>
												
												<th class="text-center"><spring:message code="accepted.title" text="Accepted" /></th>
												<th class="text-center"><spring:message code="delivered.title" text="Delivered" /></th>
												<th class="text-center"><spring:message code="dropped.title" text="Dropped" /></th>
												<th class="text-center"><spring:message code="processed.title" text="Processed" /></th>
												<th class="text-center"><spring:message code="pushed.title" text="Pushed" /></th>
												<th class="text-center"><spring:message code="queued.title" text="Queued" /></th>
												<th class="text-center"><spring:message code="rejected.title" text="Rejected" /></th>
												<th class="text-center"><spring:message code="resend.title" text="Resend" /></th>
												
												<th class="text-center"><spring:message code="delivery.title" text="Delivery" /></th>
												
											</tr>
										</thead>
										<tbody>
										<c:forEach items="${mailingLogs}" var="mq">
											<tr>
												<td class="text-center"><c:out value="${mq.getId()}" /></td>
												<td class="text-center"><joda:format value="${mq.getCreationDate()}" pattern="dd/MM/yyyy HH:mm" /></td>
												<td class="text-center"><c:out value="${mq.getUserEmail()}" /></td>
												<td class="text-center"><c:out value="${mq.getUserDevice()}" /></td>
												
												<td class="text-center"><c:choose><c:when test="${mq.isAccepted()}"><spring:message code="yes.title" text="Yes" /></c:when><c:otherwise><span class="text-danger"><spring:message code="no.title" text="No" /></span></c:otherwise></c:choose></td>
												<td class="text-center"><c:choose><c:when test="${mq.isDelivered()}"><spring:message code="yes.title" text="Yes" /></c:when><c:otherwise><span class="text-danger"><spring:message code="no.title" text="No" /></span></c:otherwise></c:choose></td>
												<td class="text-center"><c:choose><c:when test="${mq.isDropped()}"><spring:message code="yes.title" text="Yes" /></c:when><c:otherwise><span class="text-danger"><spring:message code="no.title" text="No" /></span></c:otherwise></c:choose></td>
												<td class="text-center"><c:choose><c:when test="${mq.isProcessed()}"><spring:message code="yes.title" text="Yes" /></c:when><c:otherwise><span class="text-danger"><spring:message code="no.title" text="No" /></span></c:otherwise></c:choose></td>
												<td class="text-center"><c:choose><c:when test="${mq.isPushed()}"><spring:message code="yes.title" text="Yes" /></c:when><c:otherwise><span class="text-danger"><spring:message code="no.title" text="No" /></span></c:otherwise></c:choose></td>
												<td class="text-center"><c:choose><c:when test="${mq.isQueued()}"><spring:message code="yes.title" text="Yes" /></c:when><c:otherwise><span class="text-danger"><spring:message code="no.title" text="No" /></span></c:otherwise></c:choose></td>
												<td class="text-center"><c:choose><c:when test="${mq.isRejected()}"><spring:message code="yes.title" text="Yes" /></c:when><c:otherwise><span class="text-danger"><spring:message code="no.title" text="No" /></span></c:otherwise></c:choose></td>
												<td class="text-center"><c:choose><c:when test="${mq.isResend()}"><spring:message code="yes.title" text="Yes" /></c:when><c:otherwise><span class="text-danger"><spring:message code="no.title" text="No" /></span></c:otherwise></c:choose></td>
												
												<td class="text-center"><joda:format value="${mq.getDeliveryDate()}" pattern="dd/MM/yyyy HH:mm" /></td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
								</div>							
							</div>								
						</div>
					</div>
				</div>
			</div>
		</div>
		</div>
		<!-- END DETAILS -->
		<h3 class="page-title"><spring:message code="tasks.title" text="Tasks" />&nbsp;&nbsp;</h3>
         <div class="portlet-body">
		 <ul class="feeds">
		 <c:forEach items="${actions}" var="action">
		 <c:set var="currentId" value="${action.getId()}" scope="request" />		
		 <li>
		 	<!-- S -->
		 	<c:choose>
		 	<c:when test="${action.getType().name() == 'ACTIVITY_DETECTION'}">
		 	<div class="col1 qz-pointer animated flash" ng-click="openModal('${action.getType().name().toUpperCase()}',${currentId});"><div class="cont">
            	<div class="cont-col1"><div class="label label-sm BG_${action.getType().name()}"><i class="fa fa-magic"></i></div></div>
            	<div class="cont-col2"><div class="desc"><spring:message code="activity.detection.title" text="Activity Detection" /></div></div>
            </div></div>	
            </c:when>
            <c:when test="${action.getType().name() == 'QUESTIONNAIRE'}">
            <div class="col1 qz-pointer animated flash" ng-click="openModal('${action.getType().name().toUpperCase()}${currentId}',${currentId});"><div class="cont">
            	<div class="cont-col1"><div class="label label-sm BG_${action.getType().name()}"><i class="fa fa-list-ul"></i></div></div>
            	<div class="cont-col2"><div class="desc"><spring:message code="questionnaire.title" text="Questionnaire"/></div></div>
            </div></div>	
            </c:when>
            <c:when test="${action.getType().name() == 'PHOTO'}">
            <div class="col1 qz-pointer animated flash" ng-click="openModal('${action.getType().name().toUpperCase()}',${currentId});"><div class="cont">
            	<div class="cont-col1"><div class="label label-sm BG_${action.getType().name()}"><i class="fa fa-camera"></i></div></div>
            	<div class="cont-col2"><div class="desc"><spring:message code="photo.title" text="Photo"/></div></div>
            </div></div>	
            </c:when>                        
			<c:when test="${action.getType().name() == 'SENSING_MOST'}">
			<div class="col1 qz-pointer animated flash" ng-click="openModal('${action.getPipelineType().name().toUpperCase()}',${currentId});"><div class="cont">
				<div class="cont-col1"><div class="label label-sm BG_${action.getType().name()}"><i class="fa fa-wifi"></i></div></div>
				<div class="cont-col2"><div class="desc">${action.getTranslated().toString()}</div></div>
			</div></div>	
			</c:when>                                    
            </c:choose>		 	
            <!-- E -->          
		 </li>
		 </c:forEach>
		 </ul></div>
		<!-- END DETAILS -->
		</div>	
</div>
<!-- MODALS -->
<div class="col-md-12">
	<c:forEach items="${actions}" var="action">
	<c:choose>
	<c:when test="${action.getType().name() == 'SENSING_MOST'}">
		<c:set var="actionId" value="${action.getId()}" scope="request" />
		<c:set var="pgTitle" value="${action.getTranslated().toString()}" scope="request"/>		
		<jsp:include page="/WEB-INF/views/protected/campaign-task-reports/data/${action.getPipelineType().name().toLowerCase()}.jsp" />
	</c:when>
	<c:when test="${action.getType().name() == 'ACTIVITY_DETECTION'}">
		<c:set var="actionId" value="${action.getId()}" scope="request" />
		<jsp:include page="/WEB-INF/views/protected/campaign-task-reports/data/${action.getType().name().toLowerCase()}.jsp" />
	</c:when>
	<c:when test="${action.getType().name() == 'PHOTO'}">
		<c:set var="actionId" value="${action.getId()}" scope="request" />
		<jsp:include page="/WEB-INF/views/protected/campaign-task-reports/data/${action.getType().name().toLowerCase()}.jsp" />
	</c:when>
	<c:when test="${action.getType().name() == 'QUESTIONNAIRE'}">
		<c:set var="actionId" value="${action.getId()}" scope="request" />
		<jsp:include page="/WEB-INF/views/protected/campaign-task-reports/data/${action.getType().name().toLowerCase()}.jsp" />
	</c:when>
	</c:choose>
	</c:forEach>
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />
<!-- END PAGE TITLE-->
