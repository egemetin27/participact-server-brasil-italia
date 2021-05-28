<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.*,java.text.*" %>
<div class="copyright">
<b><% Date dNow = new Date( ); SimpleDateFormat ft = new SimpleDateFormat ("yyyy"); out.print(ft.format(dNow));%> &copy; </b> <strong class="font-white">ParticipAct Brasil.</strong>&nbsp;<br/>
<div class="text-left">
	<pa class="pa-footer-break"/><strong class="font-white">LabGES</strong>&nbsp;<em class="small">&nbsp;Laboratório de Tecnologias de Gestão</em>&nbsp;
	<pa class="pa-footer-break"/><strong class="font-white">ESAG</strong>&nbsp;<em class="small">&nbsp;Centro de Ciências da Administração e Socioeconômicas</em>&nbsp;
	<pa class="pa-footer-break"/><strong class="font-white">UDESC</strong>&nbsp;<em class="small">&nbsp;Universidade do estado de Santa Catarina</em>&nbsp;
	<pa class="pa-footer-break"/><strong class="font-white">UNIBO</strong>&nbsp;<em class="small">&nbsp;Dipartimento di Informatica - Scienza e Ingegneria - <b>DISI</b> - Universita di Bologna</em>&nbsp;
	<pa class="pa-footer-break"/><strong class="font-white">LaPeSD</strong>&nbsp;<em class="small">&nbsp;Laboratório de Pesquisas em Sistemas Distribuídos </em>&nbsp; 
	<pa class="pa-footer-break"/><strong class="font-white">UFSC</strong>&nbsp;<em class="small">&nbsp;Universidade Federal de Santa Catarina </em>&nbsp;
	<pa class="pa-footer-break"/><strong class="font-white">CAPES</strong>&nbsp;<em class="small">&nbsp;Programa Professor Visitante do Exterior -&nbsp;<b>PVE</b></em>&nbsp;
</div>
<sec:authorize access="isAuthenticated()">
<br/><small ng-controller="LocaleCtrl">
<a href="javascript:;" ng-click="changeLanguage('en_US');"><img src="<c:url value="/resources/2.0/assets/pages/img/flags/us.png"/>" />&nbsp;English</a>&nbsp;&#124;&nbsp;
<a href="javascript:;" ng-click="changeLanguage('pt_BR');"><img src="<c:url value="/resources/2.0/assets/pages/img/flags/br.png"/>"/>&nbsp;Português</a></small>
</sec:authorize>
<sec:authorize access="isAnonymous()">
<br/><small><a href="?language=en_US"><img src="<c:url value="/resources/2.0/assets/pages/img/flags/us.png"/>" />&nbsp;English</a>&nbsp;&#124;&nbsp;<a href="?language=pt_BR"><img src="<c:url value="/resources/2.0/assets/pages/img/flags/br.png"/>"/>&nbsp;Português</a></small>
</sec:authorize>
</div>