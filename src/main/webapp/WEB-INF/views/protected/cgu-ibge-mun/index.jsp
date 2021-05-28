<%--
  Created by IntelliJ IDEA.
  User: Claudio
  Date: 16/07/2019
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CguIbgeMunCtrl" ng-init="initCguIbgeMun();" ng-cloak>
    <div class="col-md-12">
        <div class="portlet">
            <!-- BREADCRUMB -->
            <jsp:include page="/WEB-INF/views/protected/cgu-ibge-mun/breadcrumb.jsp" />
            <!-- FILTER -->
            <div class="portlet-body">
                <div class="row"><div class="col-md-12">
                    <div class="pull-right">
                        <form class="form-inline" role="form">
                            <div class="input-group">
                                <input type="text" class="form-control pa-filter" placeholder="<spring:message code="search.title" text="search" />" ng-model="form.search"/>
                                <span class="input-group-btn">
                                    <button title="<spring:message code="search.title" text="Search" />" class="btn blue " type="button" ng-click="searchCguIbgeMun();"><i class="fa fa-search"></i></button>
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
                            <th>ID</th>
                            <th>MUN</th>
                            <th>UF</th>
                            <th>COD7</th>
                            <th>ALT</th>
                            <th>LAT</th>
                            <th>LONG</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="(key, item) in items" id="items-tr-{{item.id}}">
                            <td>{{item.id||0}}
                                <p><a ng-href="https://www.google.com.br/maps/@{{item.geoLat}},{{item.geoLong}},15z" class="btn blue" role="button" target="_blank"><i class="fa fa-map"></i></a></p>
                            </td>
                            <td>{{item.nomemun||''}}
                                <small class="help-block">{{item.divurN1}}</small>
                                <small class="help-block">{{item.geoTipo}}</small>
                            </td>
                            <td>{{item.siglauf||''}}
                                <small class="help-block">{{item.nomeuf}}</small>
                            </td>
                            <td>{{item.codmun7||''}}
                                <small class="help-block">{{item.codmun6}}</small>
                            </td>
                            <td>{{item.geoAlt||''}}</td>
                            <td>{{item.geoLat||''}}</td>
                            <td>{{item.geoLong||''}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!-- BEGIN PAGINATION -->
                <jsp:include page="/WEB-INF/templates/partial/paginationPerItem.jsp" />
                <!-- END PAGINATION -->
            </div>
            <!-- END TABLE -->
        </div>
    </div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />