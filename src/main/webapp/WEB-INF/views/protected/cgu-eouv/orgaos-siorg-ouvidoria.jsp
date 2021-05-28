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
<div class="row" ng-controller="CguEouvCtrl" ng-init="getListaOrgaosSiorg(false);" ng-cloak>
    <div class="col-md-12">
        <div class="portlet">
            <!-- BREADCRUMB -->
            <div class="portlet-title">
                <div class="caption"><span class="caption-subject font-green sbold uppercase"><spring:message code="cgu.organs.sirog.title" text="Organs and Structures" /></span></div>
                <div class="pull-right">
                    <!-- Split button -->
                    <div class="btn-group">
                        <button title="btnGetListaOrgaosSiorg" type="button" class="btn blue-steel" ng-click="getListaOrgaosSiorg(true, 'json');"><i class="fa fa-exchange"></i>&nbsp;<spring:message code="synchronize.title" text="Synchronize" /></button>
                        <button type="button" class="btn blue-chambray dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="caret"></span>
                            <span class="sr-only">Toggle Dropdown</span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a href="javascript:void(0)" ng-click="getListaOrgaosSiorg(true, 'json');">RestFul (JSON)</a></li>
                            <li><a href="javascript:void(0)" ng-click="getListaOrgaosSiorg(true, 'soap');">SOAP (XML)</a></li>
                        </ul>
                    </div>
                </div>
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
                            <th class="col-md-2">CodOrgao</th>
                            <th class="col-md-10">NomOrgao</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="(key, item) in items|filter:form.search" id="items-tr-{{item.codOrg}}">
                            <td  class="">{{item.codOrg||0}}</td>
                            <td  class="">{{item.nomOrgao||''}}</td>
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