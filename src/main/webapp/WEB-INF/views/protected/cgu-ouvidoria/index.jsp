<%--
  Created by IntelliJ IDEA.
  User: Claudio
  Date: 09/04/2019
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CguEouvCtrl" ng-init="getOuvidorias(false);" ng-cloak>
    <div class="col-md-12">
        <div class="portlet">
            <!-- BREADCRUMB -->
            <div class="portlet-title">
                <div class="caption"><span class="caption-subject font-green sbold uppercase"><spring:message code="cgu.ombudsman.title" text="Ombudsman" /></span></div>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                <div class="pull-right">
                    <!-- Split button -->
                    <div class="btn-group">
                        <button title="btnGetListOuvidorias" type="button" class="btn blue-steel" ng-click="getOuvidorias(true, 'json');"><i class="fa fa-exchange"></i>&nbsp;<spring:message code="synchronize.title" text="Synchronize" /></button>
                        <button type="button" class="btn blue-chambray dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="caret"></span>
                            <span class="sr-only">Toggle Dropdown</span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a href="javascript:void(0)" ng-click="getOuvidorias(true, 'json');">RestFul (JSON)</a></li>
                            <li><a href="javascript:void(0)" ng-click="getOuvidorias(true, 'soap');">SOAP (XML)</a></li>
                        </ul>
                    </div>
                </div>
                </sec:authorize>
            </div>
            <!-- FILTER -->
            <div class="portlet-body" ng-show="items.length > '0'">
                <div class="row"><div class="col-md-12">
                    <div class="pull-right">
                        <form class="form-inline" role="form">
                            <div class="input-group">
                                <input type="text" class="form-control pa-filter" placeholder="<spring:message code="search.title" text="search" />" ng-model="form.search"/>
                                <span class="input-group-btn">
                                    <button title="<spring:message code="search.title" text="Search" />" class="btn blue  disabled" type="button"><i class="fa fa-search"></i></button>
                                </span>
                            </div>
                        </form>
                    </div>
                </div></div>
            </div>
            <!-- BEGIN TABLE -->
            <div class="portlet-body" ng-show="items.length > '0'">
                <!-- RESULT -->
                <div class="table-scrollable">
                    <table class="table table-striped table-bordered table-hover table-checkable" id="<%UUID.randomUUID().toString();%>">
                        <thead>
                        <tr role="row" class="heading">
                            <th class="col-md-3 wordwrap"><spring:message code="name.title" text="Name" /></th>
                            <th class="col-md-2 wordwrap"><spring:message code="cgu.sphere.title" text="Sphere" /></th>
                            <th class="col-md-2 wordwrap"><spring:message code="cgu.ibge.muni" text="City" /></th>
                            <th class="col-md-2 wordwrap"><spring:message code="cgu.subject.title" text="Subject" /></th>
                            <th class="col-md-2 wordwrap"><spring:message code="category.title" text="Category" /></th>
                            <th class="col-md-1 text-right"><spring:message code="status.title" text="Status" /></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="(key, item) in items|filter:form.search" id="items-tr-{{item.idOuvidoria}}">
                            <td class="text-left">{{item.nomeOrgaoOuvidoria||''}}
                                <small class="help-block">{{item.idOuvidoria}}</small>
                            </td>
                            <td class="text-left">{{item.descEsfera||''}}
                                <small class="help-block">{{item.idEsfera}}</small>
                            </td>
                            <td class="text-left">{{item.descMunicipio||''}}
                                <small class="help-block">{{item.idMunicipio}}</small>
                            </td>
                            <td class="text-justify">{{item.subAssuntosOuvidoria||''}}</td>
                            <td><ul class="list-unstyled"><li ng-repeat="(k, v) in item.categorias" class="text-capitalize">{{v}}</li></ul></td>
                            <td>
                                <a href="<c:url value="/protected/cgu-ouvidoria/form/"/>{{item.idOuvidoria}}" class="btn btn-block" role="button" ng-class="item.hasAllowOmbudsman?'btn-warning':'btn-default'">
                                    <i class="fa " ng-class="item.hasAllowOmbudsman?'fa-heartbeat animated pulse infinite text-danger':'fa-heart-o text-muted'"></i>&nbsp;
                                    <span ng-show="item.hasAllowOmbudsman" class="text-uppercase"><spring:message code="cgu.enabled.title" text="Enabled" /></span>&nbsp;
                                    <span ng-hide="item.hasAllowOmbudsman" class="text-uppercase"><spring:message code="cgu.disabled.title" text="Disabled" /></span>
                                </a>
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