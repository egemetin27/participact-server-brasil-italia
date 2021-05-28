<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="portlet-title">
	<div class="caption"><span class="caption-subject font-green sbold uppercase"><spring:message code="issues.title" text="Issues" /></span></div>
	<a href="<c:url value="/protected/campaign/index"/>" class="btn default col-xs-6 col-sm-4 col-md-2 col-lg-2 pull-right" id="fixgoback"><i class="icon-layers"></i>&nbsp;<spring:message code="protected.campaigns.title" text="Campaigns" /></a>
</div>