<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="en" class="no-js" ng-app="LoginApp">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
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
    <link href="<c:url value="/resources/2.0/assets/bower_components/select2/dist/css/select2.min.css"/>" rel="stylesheet" type="text/css" />	      
    <link href="<c:url value="/resources/2.0/assets/bower_components/select2-bootstrap-theme/dist/select2-bootstrap.min.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/2.0/assets/bower_components/angular-loading-bar/build/loading-bar.min.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/2.0/assets/bower_components/animate.css/animate.min.css"/>" rel="stylesheet" type="text/css" />				
	<!-- END STYLES -->
    <!-- BEGIN THEME GLOBAL STYLES -->
 	<link href="<c:url value="/resources/2.0/assets/global/css/components.min.css"/>" rel="stylesheet" id="style_components" type="text/css" />
    <link href="<c:url value="/resources/2.0/assets/global/css/plugins.min.css"/>" rel="stylesheet" type="text/css" />    
	<!-- END THEME GLOBAL STYLES -->
    <!-- BEGIN PAGE LEVEL STYLES -->
    <link href="<c:url value="/resources/2.0/assets/pages/css/login.min.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/2.0/css/login.css"/>" rel="stylesheet" type="text/css" />
    <!-- END PAGE LEVEL STYLES -->
	<jsp:include page="/WEB-INF/templates/partial/favicon.jsp"></jsp:include>    
</head>
<body class=" login">
      <!-- BEGIN LOGO -->
      <div class="logo"><img src="<c:url value="/resources/2.0/img/logo1.png"/>" alt="ParticipAct Brasil" class="img-responsive"/></div>
      <!-- END LOGO -->
      <!-- BEGIN CONTENT -->
		<div class="content" ng-controller="LoginCtrl">