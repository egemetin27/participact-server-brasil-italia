<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page import="br.com.bergmannsoft.config.Config" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CkanComcapCtrl" ng-init="initCkanComcap();" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/ckan-comcap/breadcrumb.jsp" />
		<!-- FILTER -->
		<div class="portlet-body">
		<div class="row"><div class="col-md-12">
		<div class="pull-right">
			<form class="form-inline" role="form">
				<div class="form-group">
					<small class="help-block"><spring:message code="start.time.title" text="Start Date" /></small>
					<input type="date" class="form-control  pa-filter col-md-2" placeholder="<spring:message code="start.time.title" text="Start Date" />" name="start" id="start" ng-model="form.start" ng-change="initCkanComcap();" />
				</div>
				<div class="form-group">
					<small class="help-block"><spring:message code="end.time.title" text="End Date" /></small>				
					<input type="date" class="form-control  pa-filter col-md-2" placeholder="<spring:message code="end.time.title" text="End Date" />" name="deadline" id="deadline" ng-model="form.deadline" ng-change="initCkanComcap();" />					
				</div>
				<div class="form-group">
					<small class="help-block">&nbsp;</small>
					<input type="text" class="form-control  pa-filter col-md-2" placeholder="<spring:message code="search.title" text="search" />" ng-model="form.search" ng-change="initCkanComcap();" />
				</div>
				<div class="form-group">
					<small class="help-block">&nbsp;</small>
					<button title="<spring:message code="search.title" text="Search" />" class="btn blue " type="button" ng-click="initCkanComcap();"><i class="fa fa-search"></i></button>
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
							<th class="col-md-1"><spring:message code="ckan.balanca.title" text="Balanca" /></th>
							<th class="col-md-1"><spring:message code="ckan.peso.liquido.title" text="Peso Liquido" /></th>							
							<th class="col-md-2"><spring:message code="ckan.roteiro.title" text="Roteiro" /></th>
							<th class="col-md-2"><spring:message code="ckan.destino.title" text="Destino" /></th>
							<th class="col-md-2"><spring:message code="ckan.residuo.title" text="Residuo" /></th>
							<th class="col-md-2"><spring:message code="ckan.regiao.title" text="Regiao" /></th>
							<th class="col-md-2"><spring:message code="ckan.data.pesagem.title" text="Data Pesagem" /></th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="(key, item) in items" id="items-tr-{{item[0]}}">
							<td  class="">{{item[1]||0}}</td>
							<td  class="">{{item[2]||0}}</td>							
							<td  class="">
								{{item[4]|stripslashes}}
								<small class="help-block">#&nbsp;{{item[3]||0}}</small>
							</td>
							<td  class="">
								{{item[6]|stripslashes}}
								<small class="help-block">#&nbsp;{{item[5]||0}}</small>
							</td>
							<td  class="">
								{{item[8]|stripslashes}}
								<small class="help-block">#&nbsp;{{item[7]||0}}</small>
							</td>
							<td  class="">
								{{item[10]|stripslashes}}
								<small class="help-block">#&nbsp;{{item[9]||0}}</small>
							</td>
							<td  class="">{{item[11]|stripslashes}}</td>																																			
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