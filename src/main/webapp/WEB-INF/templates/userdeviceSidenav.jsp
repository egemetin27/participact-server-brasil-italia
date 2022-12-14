<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="subSectionKey">${param.subsection}</c:set>
<ul class="nav nav-list bs-docs-sidenav affix-top" data-spy="affix" data-offset-top="200">
	<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
		<c:choose>
			<c:when test="${subSectionKey eq 'protected.userdevice'}">
				<li class="active"><a href='<c:url value="/protected/userdevice"/>'>Devices</a></li>
			</c:when>
			<c:otherwise>
				<li><a href='<c:url value="/protected/userdevice"/>'>Devices</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${subSectionKey eq 'protected.userdevice.add'}">
				<li class="active"><a href='<c:url value="/protected/userdevice/add"/>'>Add Device</a></li>
			</c:when>
			<c:otherwise>
				<li><a href='<c:url value="/protected/userdevice/add"/>'>Add Device</a></li>
			</c:otherwise>
		</c:choose>
	</sec:authorize>
</ul>
