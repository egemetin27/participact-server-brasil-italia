<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="no-js" ng-app="ParticipActApp">
<head>
	<meta charset="utf-8" />
	<title>ParticipAct Brasil</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<!-- BEGIN STYLES -->
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700|Roboto|Roboto+Condensed" rel="stylesheet">
    <link href="<c:url value="/resources/2.0/assets/global/plugins/simple-line-icons/simple-line-icons.min.css"/>" rel="stylesheet" type="text/css" />
	<!-- BOWER -->
	<link href="<c:url value="/resources/2.0/assets/bower_components/components-font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css" /> 	

    <link href="<c:url value="/resources/2.0/assets/bower_components/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css" />      
    <link href="<c:url value="/resources/2.0/assets/bower_components/bootstrap-switch/dist/css/bootstrap3/bootstrap-switch.min.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/2.0/assets/bower_components/bootstrap-select/dist/css/bootstrap-select.min.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/2.0/assets/bower_components/angular-loading-bar/build/loading-bar.min.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/2.0/assets/bower_components/animate.css/animate.min.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/2.0/assets/bower_components/angular-toastr/dist/angular-toastr.min.css"/>" rel="stylesheet" type="text/css" />				
    <link href="<c:url value="/resources/2.0/assets/bower_components/angular-google-places-autocomplete/dist/autocomplete.min.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/2.0/assets/bower_components/summernote/dist/summernote.css"/>" rel="stylesheet" type="text/css" />
    <!-- <link href="<c:url value="/resources/2.0/assets/bower_components/air-datepicker/dist/css/datepicker.min.css"/>" rel="stylesheet" type="text/css" /> -->
    <link href="<c:url value="/resources/2.0/assets/bower_components/bootstrap-tagsinput/dist/bootstrap-tagsinput.css"/>" rel="stylesheet" type="text/css" />   
    <link href="<c:url value="/resources/2.0/assets/bower_components/bootstrap-duration-picker/src/jquery-duration-picker.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/2.0/assets/bower_components/highcharts/css/highcharts.css"/>" rel="stylesheet" type="text/css" />
	<link href="<c:url value="/resources/2.0/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.css"/>" rel="stylesheet" type="text/css" />
	<link href="<c:url value="/resources/2.0/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css"/>" rel="stylesheet" type="text/css" />
	<link href="<c:url value="/resources/2.0/assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css"/>" rel="stylesheet" type="text/css" />
	<link href="<c:url value="/resources/2.0/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>" rel="stylesheet" type="text/css" />    
	<link href="<c:url value="/resources/2.0/assets/bower_components/angular/angular-csp.css"/>" rel="stylesheet" type="text/css" />
	<link href="<c:url value="/resources/2.0/assets/bower_components/angular-ui-select/dist/select.min.css"/>" rel="stylesheet" type="text/css" />
	<link href="<c:url value="/resources/2.0/assets/bower_components/angular-bootstrap-toggle-switch/style/bootstrap3/angular-toggle-switch-bootstrap-3.css"/>" media="all" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ekko-lightbox/5.3.0/ekko-lightbox.css" integrity="sha256-HAaDW5o2+LelybUhfuk0Zh2Vdk8Y2W2UeKmbaXhalfA=" crossorigin="anonymous" />
    <link href="https://unpkg.com/nanogallery2/dist/css/nanogallery2.min.css" rel="stylesheet" type="text/css">

    <!-- END STYLES -->
    <!-- BEGIN THEME GLOBAL STYLES -->
 	<link href="<c:url value="/resources/2.0/assets/global/css/components.min.css"/>" rel="stylesheet" id="style_components" type="text/css" />
    <link href="<c:url value="/resources/2.0/assets/global/css/plugins.min.css"/>" rel="stylesheet" type="text/css" />    
	<!-- END THEME GLOBAL STYLES -->
    <!-- BEGIN PAGE LEVEL STYLES -->
    <link href="<c:url value="/resources/2.0/assets/layouts/layout/css/layout.min.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/2.0/assets/layouts/layout/css/themes/default.min.css"/>" rel="stylesheet" type="text/css" id="style_color"/>
    <link href="<c:url value="/resources/2.0/css/styles.css?v=1494020607"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/2.0/css/spinner.css?v=1486490015"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/2.0/css/cloak.css?v=1486490015"/>" rel="stylesheet" type="text/css" />
    <!-- END PAGE LEVEL STYLES -->
	<jsp:include page="/WEB-INF/templates/partial/favicon.jsp"></jsp:include>    
</head>
<body class="page-sidebar-closed-hide-logo page-content-white page-header-fixed" ng-csp>
<jsp:include page="/WEB-INF/templates/partial/spinner.jsp"></jsp:include>    
 <!-- BEGIN HEADER -->
 <div class="page-header navbar navbar-fixed-top">
     <!-- BEGIN HEADER INNER -->
     <div class="page-header-inner ">
         <!-- BEGIN LOGO -->
        <section ng-controller="SidebarCtrl">
         <div class="page-logo">
             <a href="<c:url value="/protected/dashboard/index"/>"><img src="<c:url value="/resources/2.0/img/logo-small.png"/>" alt="ParticipAct" class="logo-default" /> </a>
             <div class="menu-toggler sidebar-toggler" ng-click="onClickToggler()"><span></span></div>
         </div>
         <!-- END LOGO -->
         <!-- BEGIN RESPONSIVE MENU TOGGLER -->
         <a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse" ng-click="onClickToggler()"> <span></span> </a>
        </section>
         <!-- END RESPONSIVE MENU TOGGLER -->
         <!-- BEGIN TOP NAVIGATION MENU -->
         <div class="top-menu">
             <ul class="nav navbar-nav pull-right">
             	<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')">
                 <!-- BEGIN NOTIFICATION DROPDOWN -->
                 <li class="dropdown dropdown-extended dropdown-notification" id="header_notification_bar" ng-controller="NotificationBarCtrl" ng-init="startBell();" ng-cloak>
                     <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                         <i class="icon-bell"></i><span class="badge badge-default">{{header_notification_badge}}</span>
                     </a>
                     <ul class="dropdown-menu" ng-hide="header_notification_badge=='0'">
                         <li class="external lowercase">&nbsp;&nbsp;<h3><span class="bold">{{header_notification_badge}}</span>&nbsp;&nbsp;<spring:message code="notifications.title" text="notifications"/></h3></li>
                         <li>
                             <ul class="dropdown-menu-list scroller" style="height: 250px;" data-handle-color="#637283">
                                 <li ng-repeat="(k,i) in header_notification_messages"><a ng-href="{{i[3]}}"><span class="details text-jutify"><span ng-bind-html="i[0]"></span><small class="help-block pull-right margin-bottom-10">{{i[2].millis | date:'dd/MM/yyyy HH:mm:ss'}}</small><br/><br/></span></a></li>
                             </ul>
                         </li>
                     </ul>
                 </li>
                 <!-- END NOTIFICATION DROPDOWN -->
                 </sec:authorize>
                 <!-- BEGIN USER -->
					<li class="dropdown dropdown-user">
					<sec:authorize access="hasRole('ROLE_USER')">
					<a href="<c:url value="/protected/user/show"/>" class="dropdown-toggle" data-close-others="true">
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
					<a href="javascript:;" class="dropdown-toggle" data-close-others="true">
					</sec:authorize>
					<img alt="" class="img-circle" src="<c:url value="/resources/2.0/assets/layouts/layout/img/avatar.png" />">
					<span class="username username-hide-on-mobile font-white"> <sec:authentication property="principal.username" /> </span>
					</a></li>
                 <!-- END USER -->
                 <!-- BEGIN QUICK SIDEBAR TOGGLER -->
                 <li class="dropdown dropdown-quick-sidebar-toggler"><a href="<c:url value="/logout"/>" class="dropdown-toggle"><i class="icon-logout"></i></a></li>
                 <!-- END QUICK SIDEBAR TOGGLER -->
             </ul>
         </div>
         <!-- END TOP NAVIGATION MENU -->
     </div>
     <!-- END HEADER INNER -->
 </div>
 <!-- END HEADER -->
<!-- BEGIN HEADER & CONTENT DIVIDER -->
<div class="clearfix"> </div>
<!-- END HEADER & CONTENT DIVIDER -->
<!-- BEGIN CONTAINER -->
<div class="page-container">
<jsp:include page="/WEB-INF/templates/layout/sidebar.jsp"></jsp:include>
<!-- BEGIN CONTENT -->
<div class="page-content-wrapper">
<!-- BEGIN CONTENT BODY -->
<div class="page-content">