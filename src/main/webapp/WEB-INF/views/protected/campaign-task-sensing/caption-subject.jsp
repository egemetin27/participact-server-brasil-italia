<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<span class="caption-subject font-green sbold uppercase"><i class="fa fa-wifi"></i>&nbsp;&nbsp;<c:out value="${requestScope.pgTitle}" />&nbsp;&nbsp;&#45;&nbsp;&nbsp;<spring:message code="passive.sensing.title" text="Passive Sensing" />&nbsp;</span>