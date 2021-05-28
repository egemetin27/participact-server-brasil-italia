<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="col-md-12" ng-controller="DashboardCtrl" ng-init="initStatCategory();" id="pa-dashboard-issue-category">
    <div class="portlet">
        <!-- BREADCRUMB -->
        <jsp:include page="/WEB-INF/views/protected/dashboard/breadcrumb.jsp" />
        <!-- BEGIN FILTER -->
        <!-- END FILTER -->
        <!-- BEGIN BODY -->
        <div class="portlet-body">
            <div class="row">
                <!-- A -->
                <div class="col-md-12 margin-bottom-20">
                    <h4 class="text-center"><spring:message code="category.title" text="Category" /></h4>
                    <div id="pa-chart-category-pie" class="chartdiv"></div>
                </div>
                <!-- B -->
                <div class="col-md-12 margin-top-20">
                    <h4 class="text-center"><spring:message code="subcategory.title" text="SubCategory" /></h4>
                    <div id="pa-chart-subcategory-bar" class="chartdiv"></div>
                </div>
                <!-- C -->
                <div class="col-md-12 margin-top-20">
                    <h4 class="text-center"><spring:message code="interval.time.title" text="Interval Date" /></h4>
                    <div id="pa-chart-subcategory-line-graph" class="chartdiv"></div>
                </div>
            </div>
        </div>
        <!-- END BODY -->
    </div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />