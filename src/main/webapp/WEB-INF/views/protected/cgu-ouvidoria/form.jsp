<jsp:useBean id="form" scope="request" type="java.lang.Long"/>
<%--
  Created by IntelliJ IDEA.
  User: Claudio
  Date: 17/07/2019
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/WEB-INF/templates/layout/header.jsp"/>
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CguEouvCtrl" ng-init="setOuvidoria('${form}')" ng-cloak>
    <div class="col-md-12">
        <div class="portlet">
            <!-- BREADCRUMB -->
            <div class="portlet-title">
                <div class="caption"><span class="caption-subject font-green sbold uppercase"><spring:message code="cgu.ombudsman.title" text="Ombudsman"/></span></div>
            </div>
            <!-- BEGIN BODY -->
            <div class="portlet-body">
                <form>
                    <!-- OUVIDORIA -->
                    <div class="row">
                        <div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <small class="help-block text-uppercase text-right text-muted"><spring:message code="cgu.enabled.title" text="Enabled" />/<spring:message code="cgu.disabled.title" text="Disabled" />&nbsp;<i class="fa fa-info-circle" data-toggle="tooltip" data-placement="right" title="<spring:message code="cgu.status.help" text="If enabled, reports that have been georeferenced will be sent to this ombudsman." />"></i></small>
                            <button class="btn btn-block" role="button" ng-class="form.hasAllowOmbudsman?'btn-warning':'btn-default'" ng-click="toggleOmbudsman('${form}');">
                                <i class="fa " ng-class="form.hasAllowOmbudsman?'fa-heartbeat animated pulse infinite text-danger':'fa-heart-o text-muted'"></i>&nbsp;
                                </sec:authorize>
                                <span ng-show="form.hasAllowOmbudsman" class="text-uppercase"><spring:message code="cgu.enabled.title" text="Enabled" /></span>&nbsp;
                                <span ng-hide="form.hasAllowOmbudsman" class="text-uppercase"><spring:message code="cgu.disabled.title" text="Disabled" /></span>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                            </button>
                                </sec:authorize>
                        </div>
                        <div class="col-lg-4 col-md-5 col-sm-6 col-xs-12">
                            <small class="help-block"><spring:message code="name.title" text="Name" /></small>
                            <input type="text" class="form-control" ng-model="form.nomeOrgaoOuvidoria" readonly>
                        </div><!-- /.col -->

                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <small class="help-block"><spring:message code="cgu.sphere.title" text="Sphere" /></small>
                            <input type="text" class="form-control" ng-model="form.descEsfera" readonly>
                        </div><!-- /.col -->

                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <small class="help-block"><spring:message code="cgu.subject.title" text="Subject" /></small>
                            <input type="text" class="form-control" ng-model="form.subAssuntosOuvidoria" readonly>
                        </div><!-- /.col -->
                    </div><!-- /.row -->
                    <!-- REGIAO -->
                    <div class="row">
                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <small class="help-block"><spring:message code="cgu.ibge.muni" text="City" /></small>
                            <input type="text" class="form-control" ng-model="form.nomemun" readonly>
                        </div><!-- /.col -->

                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <small class="help-block"><spring:message code="cgu.ibge.uf" text="UF" /></small>
                            <input type="text" class="form-control" ng-model="form.nomeuf" readonly>
                        </div><!-- /.col -->

                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <small class="help-block"><spring:message code="latitude.title" text="Latitude" /></small>
                            <input type="text" class="form-control" ng-model="form.geoLat" readonly>
                        </div><!-- /.col -->

                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <small class="help-block"><spring:message code="longitude.title" text="Longitude" /></small>
                            <input type="text" class="form-control" ng-model="form.geoLong" readonly>
                        </div><!-- /.col -->
                    </div>
                    <!-- CATEGORIA -->
                    <div class="row">
                        <div class="col-md-12">
                        <p><h5 class="text-uppercase bold text-primary"><spring:message code="subcategory.title" text="Categories" /></h5></p>
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <!-- body -->
                                <tbody>
                                    <tr ng-repeat="(ck, cv) in form.categories">
                                        <td>
                                            <div class="media">
                                                <div class="media-left"><img class="media-object" ng-src="{{cv.urlAsset}}" width="16" /></div>
                                                <div class="media-body">
                                                    <h6 class="media-heading text-uppercase">{{cv.name}}</h6>
                                                </div>
                                            </div>
                                        </td>
                                        <td ng-repeat="(sk, sv) in cv.map">
                                            <button class="btn btn-block btn-sm text-capitalize" ng-class="sv.hasAllowOmbudsman?'btn-success':'btn-default'" ng-click="toggleSubCategory('${form}', ck, sk);">{{sv.name}}</button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        </div>
                    </div>
                </form>
            </div>
            <!-- END BODY -->
        </div>
    </div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp"/>