<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.UUID" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
	<div class="portlet">
	<div class="portlet-title">
		<div class="caption">
		<span class="caption-subject font-green sbold uppercase"><spring:message code="pipe.title" text="Pipelines' Descriptions" /></span>
		</div>
	</div>
	</div>	
	<div class="portlet-body">	
	<label class="col-md-2 control-label bold"><spring:message code="pipe.title" text="Pipelines" /></label>
	<div class="col-md-7">	
	<dl>
		<dd>
			<ul>
				<c:forEach items="${pipeNames}" var="pipe">
					<li>
				  		<a href='<c:url value="/protected/pipelineDescription/show/${pipe}" />'><c:out value="${pipe}" /></a>
					</li>
				</c:forEach>
			</ul>
			</dd>
			</dl>
	</div>		
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />