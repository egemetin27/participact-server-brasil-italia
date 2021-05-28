<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%  String lang = (new RequestContext(request)).getLocale().getLanguage(); lang = lang=="en_us"?"en":lang; %>
<%@ page import="java.util.UUID" %>
<%@ page import="it.unibo.paserver.domain.AudienceSelector" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<!-- <div class="row" ng-controller="ParticipantListCtrl" ng-init="initParticipantList();" ng-cloak> -->
<div class="row" ng-controller="ParticipantListCtrl" ng-init="getListParticipantList('${taskId}', '${userListId}');" ng-cloak>
	<div class="col-md-12">
			<div class="portlet">
			<!-- BREADCRUMB -->
			<jsp:include page="/WEB-INF/views/protected/participant-list/breadcrumb.jsp" />
			<!-- FILTER -->
			<div class="portlet-body" ng-show="audienceSelector!='<%=AudienceSelector.SELECTOR_ALL.name()%>'">
				<div class="row">
					<div class="col-md-12">
						<div class="pull-left">
							<p><jsp:include page="/WEB-INF/views/protected/participant-list/filter.jsp" /></p>
						</div>
					</div>
				</div>
			</div>
			<!-- ./FILTER -->
			<!-- CHECKBOX/RESULT -->
			<div class="portlet-body" ng-show="audienceSelector!='<%=AudienceSelector.SELECTOR_ALL.name()%>'">
				<div class="row">
				<!-- AVAILABLE -->
					<div class="" ng-class="audienceSelector=='<%=AudienceSelector.SELECTOR_RESTRICTED.name()%>'?'col-md-12':'col-md-7'">
						<!-- BEGIN Portlet PORTLET-->
						<div class="portlet light padding-left-0">
							<div class="portlet-title">
								<div class="caption"><span class="caption-subject bold uppercase"><spring:message code="available.title" text="Available" /></span></div>
							</div>
							<div class="portlet-body">									
								<!-- LISTA PARA SELECAO -->
								<div ng-show="userList.length > '0'">
									<div class="slimScrollDiv" style="position: relative; overflow-x:hidden; overflow-y: auto; width: auto; height: 600px;">
									<div class="table-scrollable">
										<table class="table table-striped table-hover table-checkable" id="<%UUID.randomUUID().toString();%>">
											<thead>
												<tr role="row" class="heading">
													<th class="col-md-1" ng-show="audienceSelector=='<%=AudienceSelector.SELECTOR_CLOSED.name()%>'"><input type="checkbox" class="group-checkable" ng-model="selectedAll" ng-click="checkAll()" title="<spring:message code="selected.all.title" text="Selected All" />" /></th>
													<th class="col-md-8"><input type="text" ng-model="userSearch" class="form-control " placeholder="<spring:message code="search.title" text="Search" />..."></th>
													<th class="col-md-3"><button type="button" ng-show="audienceSelector=='<%=AudienceSelector.SELECTOR_CLOSED.name()%>'" ng-click="addItemList(${userListId});" class="btn btn-success uppercase pull-right" ng-disabled="selectedItems==0" ng-class="selectedItems>0?'animated flash':''">&nbsp;&nbsp;<spring:message code="toselect.title" text="To select" />&nbsp;&nbsp;<small class="badge">{{selectedItems||0}}</small>&nbsp;&nbsp;<i class="fa fa-long-arrow-right" aria-hidden="true"></i>&nbsp;&nbsp;</button></th>
												</tr>
											</thead>
											<tbody>
												<tr ng-repeat="item in userList | filter:userSearch" id="user-list-tr-{{item[0]}}">
													<td ng-show="audienceSelector=='<%=AudienceSelector.SELECTOR_CLOSED.name()%>'">
														<input type="checkbox" class="group-checkable" ng-model="item.Selected" ng-change="isSelected('checkbox-{{item[0]}}')" id="checkbox-{{item[0]}}" ng-hide="item.isLoading"/>
														<i class="fa fa-refresh fa-spin fa-fw" ng-show="item.isLoading"></i>
													</td>
													<td colspan="2">{{item[1]|stripslashes}}&nbsp;<br/><small class="text-muted">{{item[3]|stripslashes}}</small></td>
											</tbody>
										</table>
									</div>
									</div>
								</div>
								<!-- AVISO SEM DADOS -->
								<div ng-show="userList.length == '0'" id="no-data-to-display">
									<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
								</div>											
							</div>
						</div>
						<!-- END Portlet PORTLET-->
					</div>
					<!-- ./AVAILABLE -->
					<!-- SELECTED -->
					<div class="col-md-5" ng-show="audienceSelector=='<%=AudienceSelector.SELECTOR_CLOSED.name()%>'">
							<!-- BEGIN Portlet PORTLET-->
							<div class="portlet light padding-right-0">
								<div class="portlet-title">
									<div class="caption"><span class="caption-subject bold uppercase"><spring:message code="selected.title" text="Selected" /></span></div>
								</div>
								<div class="portlet-body">									
									<!-- LISTA PARA SELECAO -->
									<div ng-show="itemList.length > '0'">
										<div class="slimScrollDiv" style="position: relative; overflow-x:hidden; overflow-y: auto; width: auto; height: 600px;">
										<div class="table-scrollable">
											<table class="table table-striped table-hover table-checkable" id="<%UUID.randomUUID().toString();%>">
												<thead>
													<tr role="row" class="heading">
														<th class="col-md-1"><input type="checkbox" class="group-checkable" ng-model="deselectedAll" ng-click="deselectCheckAll()" title="<spring:message code="select.all.title" text="Select All" />" /></th>
														<th class="col-md-8"><input type="text" ng-model="deselectSearch" class="form-control " placeholder="<spring:message code="search.title" text="Search" />..."></th>
														<th class="col-md-3"><button type="button" class="btn btn-danger btn-block uppercase pull-right" ng-click="removeItemList(${userListId});" ng-disabled="deselectedItems==0" ng-class="deselectedItems>0?'animated flash':''">&nbsp;&nbsp;<i class="fa fa-long-arrow-left" aria-hidden="true"></i>&nbsp;&nbsp;<small class="badge">{{deselectedItems||0}}</small>&nbsp;&nbsp;<spring:message code="deselect.title" text="deselect" />&nbsp;&nbsp;</button></th>
													</tr>
												</thead>
												<tbody>
													<tr ng-repeat="item in itemList | filter:deselectSearch" id="deselect-list-tr-{{item[0]}}">
														<td>
															<input type="checkbox" class="group-checkable" ng-model="item.Selected" ng-change="isDeselected('decheckbox-{{item[0]}}')" id="decheckbox-{{item[0]}}"  ng-hide="item.isLoading"/>
															<i class="fa fa-refresh fa-spin fa-fw" ng-show="item.isLoading"></i>
														</td>
														<td colspan="2">{{item[1]|stripslashes}}&nbsp;<br/><small class="text-muted">{{item[3]|stripslashes}}</small></td>
												</tbody>
											</table>
										</div>
										</div>
									</div>
									<!-- AVISO SEM DADOS -->
									<div ng-show="itemList.length == '0'" id="no-item-to-display">
										<jsp:include page="/WEB-INF/templates/partial/noitem.jsp" />
									</div>											
								</div>
							</div>
							<!-- END Portlet PORTLET-->					
					</div>					
					<!-- ./SELECTED -->
				</div>
			</div>		
			<!-- ./CHECKBOX/RESULT -->
			<!-- PUBLIC ALERT -->
			<div class="portlet-body" ng-show="audienceSelector=='<%=AudienceSelector.SELECTOR_ALL.name()%>'">
			<jsp:include page="/WEB-INF/templates/partial/noprivacy.jsp" />
			</div>
			<!-- ./PUBLIC ALERT -->
			</div>
	</div>
</div>		
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />