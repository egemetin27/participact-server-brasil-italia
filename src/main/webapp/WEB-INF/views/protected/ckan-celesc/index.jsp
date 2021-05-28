<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page import="br.com.bergmannsoft.config.Config" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CkanCelescCtrl" ng-init="initCkanCelesc();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/ckan-celesc/breadcrumb.jsp" />
		<!-- FILTER -->
		<div class="portlet-body">
		<div class="row"><div class="col-md-12">
		<div class="pull-right">
			<form class="form-inline" role="form">
				<div class="form-group">
					<small class="help-block"><spring:message code="start.time.title" text="Start Date" /></small>
					<input type="date" class="form-control  pa-filter col-md-2" placeholder="<spring:message code="start.time.title" text="Start Date" />" name="start" id="start" ng-model="form.start" ng-change="initCkanCelesc();" />
				</div>
				<div class="form-group">
					<small class="help-block"><spring:message code="end.time.title" text="End Date" /></small>				
					<input type="date" class="form-control  pa-filter col-md-2" placeholder="<spring:message code="end.time.title" text="End Date" />" name="deadline" id="deadline" ng-model="form.deadline" ng-change="initCkanCelesc();" />					
				</div>
				<div class="form-group">
					<small class="help-block">&nbsp;</small>
					<input type="text" class="form-control  pa-filter col-md-2" placeholder="<spring:message code="search.title" text="search" />" ng-model="form.search" ng-change="initCkanCelesc();" />
				</div>
				<div class="form-group">
					<small class="help-block">&nbsp;</small>
					<button title="<spring:message code="search.title" text="Search" />" class="btn blue " type="button" ng-click="initCkanCelesc();"><i class="fa fa-search"></i></button>
				</div>
			</form>
			</div>
		</div></div>	
		</div>		
		<!-- BEGIN TABLE -->
		<div class="portlet-body" ng-show="items.length > '0'">
			<div class="table-scrollable">
				<table class="table table-striped table-bordered table-hover table-checkable" id="<%UUID.randomUUID().toString();%>">
					<thead>
						<tr role="row" class="heading">
							<th class="col-md-1"><spring:message code="ckan.referencia.title" text="Referencia" /></th>
							<th class="col-md-1"><spring:message code="ckan.classe.title" text="Classe" /></th>							
							<th class="col-md-2"><spring:message code="ckan.uc.title" text="UC" /></th>
							<th class="col-md-2"><spring:message code="ckan.logradouro.title" text="Logradouro" /></th>
							<th class="col-md-2"><spring:message code="ckan.bairro.title" text="Bairro" /></th>
							<th class="col-md-2"><spring:message code="ckan.cep.title" text="Cep" /></th>
							<th class="col-md-2"><spring:message code="ckan.consumo.title" text="Consumo" /></th>
							<th class="col-md-2"><spring:message code="ckan.queryAt.title" text="Data" /></th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="(key, item) in items" id="items-tr-{{item[0]}}">
							<td  class="">{{item[1]||0}}</td>
							<td  class="">{{item[2]||0}}</td>							
							<td  class="">
								<small class="help-block">#&nbsp;{{item[3]||0}}</small>
							</td>
							<td  class="">
								{{item[4]|stripslashes}}
							</td>
							<td  class="">
								{{item[5]|stripslashes}}
							</td>
							<td  class="">
								{{item[6]|stripslashes}}
							</td>
							<td  class="">{{item[7]|stripslashes}}</td>
							<td  class="">{{item[10]|stripslashes}}</td>																																					
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
		<div class="portlet-body" ng-show="items.length == '0'" id="no-data-to-display">
		<jsp:include page="/WEB-INF/templates/partial/nodata.jsp" />
		</div>
		<!-- END DISPLAY -->	
		</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />