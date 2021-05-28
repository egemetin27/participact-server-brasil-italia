<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page import="br.com.bergmannsoft.config.Config" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="IssueReportCtrl" ng-init="initIssueReport();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/issue-report/breadcrumb.jsp" />	
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
					<select class="form-control" ng-model="form.category_id">
						<option value="-1"><spring:message code="all.title" text="All" /></option>
						 <c:forEach items="${categories}" var="category">						
							<option value="${category.id}">${category.name}</option>
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
					<button title="<spring:message code="search.title" text="Search" />" class="btn blue" type="button" ng-click="initIssueReport();"><i class="fa fa-search"></i></button>
				</div>
			</form>
			</div>
		</div></div>	
		</div>			
		<!-- BEGIN TABLE -->
		<div class="portlet-body" ng-show="issues.length > '0'">
			<div class="table-scrollable">
				<table class="table table-striped table-bordered table-hover table-checkable" id="<%UUID.randomUUID().toString();%>">
					<thead>
						<tr role="row" class="heading">
							<th width="2%"><label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="group-checkable" ng-model="selectedAll" ng-click="checkAll()" /> <span></span></label></th>
						    <th width="2%" class="small text-center"><i class="icon-shield"  style="color:#F44336;"></i></th>
							<th class="col-md-2"><spring:message code="name.title" text="Name" /></th>							
							<th class="col-md-2"><spring:message code="subcategory.title" text="SubCategory" /></th>
							<th class="col-md-3"><spring:message code="comment.title" text="Comment" /></th>
							<th class="col-md-2"><spring:message code="location.title" text="Location" /></th>
							<th class="col-md-2"><spring:message code="status.title" text="Status" /></th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="(key, item) in issues" id="issues-tr-{{item[0]}}">
							<td><input type="checkbox" class="group-checkable" ng-model="item.Selected" ng-change="isSelected('checkbox-{{item[0]}}')" id="checkbox-{{item[0]}}"/></td>

							<td class="qz-pointer bold text-center" ng-class="item[9]>0?(item[9]>2?'bg-danger':'bg-warning'):'bg-success'">{{item[9]}}</td>
							<td>
								<span ng-class="item[23]?'text-muted':'text-info bold'">{{item[1] | stripslashes}}</span>
								<small class="help-block">{{item[2]| stripslashes}}</small>
								<small class="help-block">{{item[5]|date:'dd/MM/yyyy HH:mm:ss'}}</small>
								<small class="help-block">#&nbsp;{{item[0]}}</small>
								<ul ng-show="item[25]" class="list-unstyled small">
									<li><a ng-href="{{item[30]}}" target="_blank">{{item[26]}}</a>&nbsp;/&nbsp;{{item[36]}}</li>
									<li ng-repeat="(k,v) in item[35]"><a ng-href="{{v.priv_rel_eouv}}" target="_blank">{{v.pub_protocol}}</a>&nbsp;/&nbsp;{{v.ombudsman_name}}</li>
								</ul>
								<small class="badge badge-info text-center" ng-show="item[28].length>0">{{item[28]}}</small>
							</td>
							<td ng-click="editIssueReport(item[0]);" class="qz-pointer" ng-cloak><p>{{item[4]|stripslashes}}</p>
							</td>
							<td ng-click="editIssueReport(item[0]);" class="qz-pointer">{{item[3]|stripslashes}}</td>
							<td ng-click="editIssueReport(item[0]);" class="qz-pointer">
								<ul class="list-unstyled">
									<li><small><b><spring:message code="location.title" text="Location" /></b> {{item[18]}},{{item[19]}} </small></li>
									<li><small><b><spring:message code="provider.title" text="Provider" /></b> {{item[9]}}  </small></li>
									<li><small><b><spring:message code="course.title" text="Course" />	  </b> {{item[10]}}  </small></li>
									<li><small><b><spring:message code="floor.title" text="Floor" />	  </b> {{item[11]}}  </small></li>
									<li><small><b><spring:message code="speed.title" text="Speed" />	  </b> {{item[12]}}  </small></li>
									<li><small><b><spring:message code="accuracy.title" text="Accuracy" /></b> {{item[15]}}  </small></li>
									<li><small><b><spring:message code="altitude.title" text="altitude" /></b> {{item[16]}}  </small></li>
									<li><small class="text-capitalize"><b><spring:message code="city.title" text="City" /></b> {{item[31]}}  </small></li>
									<li><small class="text-capitalize"><b><spring:message code="files.title" text="Files" /></b> {{item[32]}}  </small></li>
								</ul>
							</td>
							<td ng-click="editIssueReport(item[0]);" class="qz-pointer">
								<p>
									<button class="btn btn-block text-uppercase" disabled>{{item[34]}}</button>
								</p>
								<p ng-show="item[25]">
									<a ng-href="{{item[27]}}" target="_blank"><img src="<c:url value="/resources/2.0/img/falabr.png"/>" height="48" /></a>
								</p>
							</td>
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
		<div class="portlet-body" ng-show="issues.length == '0'" id="no-data-to-display">
		<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
		</div>
		<!-- END DISPLAY -->	
		</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />