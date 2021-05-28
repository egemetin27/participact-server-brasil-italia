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
		<span class="caption-subject font-green sbold uppercase"><spring:message code="sss.title" text="Sensors' Required" /></span>
		</div>
	</div>
	</div>	
	<div class="portlet-body">		
	<c:choose>
	<c:when test="${show eq 'logic'}">
	<label class="col-md-2 control-label bold"><spring:message code="ls.title" text="Logic Sensors" /></label>
	<div class="col-md-7">
	<dl>
		<dd>
			<ul>
				<c:forEach items="${logicsens}" var="l">
					<li>
				  		<c:out value="${l}" />
					</li>
				</c:forEach>
			</ul>
			</dd>
			</dl>
	</div>
	</c:when>
	<c:when test="${show eq 'physical'}">
	<label class="col-md-2 control-label bold"><spring:message code="phy.title" text="Physical Sensors" /></label>
	<div class="col-md-7">
	<dl>
		<dd>
			<ul>
				<c:forEach items="${physens}" var="ph">
					<li>
				  		<c:out value="${ph}" />
					</li>
				</c:forEach>
			</ul>
			</dd>
			</dl>
	</div>
	</c:when>
	<c:when test="${show eq 'both'}">
	<div class="form-group ">
	<label class="col-md-2 control-label bold"><spring:message code="ls.title" text="Logical Sensors" /></label>
	<div class="col-md-7">
	<dl>
		<dd>
			<ul>
				<c:forEach items="${logicsens}" var="l">
					<li>
				  		<c:out value="${l}" />
					</li>
				</c:forEach>
			</ul>
			</dd>
			</dl>
	</div>
	<br/>
	<br/>
	<br/>
	<br/>
	<label class="col-md-2 control-label bold"><spring:message code="phy.title" text="Physical Sensors" /></label>
	<div class="col-md-7">
	<dl>
		<dd>
			<ul>
				<c:forEach items="${physens}" var="ph">
					<li>
				  		<c:out value="${ph}" />
					</li>
				</c:forEach>
			</ul>
			</dd>
			</dl>
	</div>
	</div>
	</c:when>
	</c:choose>
	<br/>
	<br/>
	<br/>
	<br/>
	<a href="<c:url value="/protected/pipelineDescription/index"/>" class="btn default col-xs-6 col-sm-4 col-md-2 col-lg-2" id="fixgoback"><spring:message code="gbck.title" text="Go back" /></a>
	<br/>
	<br/>
	<br/>
	<br/>
	<c:choose>
	<c:when test="${dev eq 'show'}">
	<label class="col-md-2 control-label bold"><spring:message code="dddv.title" text="Compatible devices" /></label>
	<div class="col-md-7">
	<dl>
		<dd>
			<ul>
				<c:forEach items="${devices}" var="d">
					<li>
						<a href='<c:url value="/protected/devices/edit/${d.id}" />'><c:out value="${d.model}" /></a>
					</li>
				</c:forEach>
			</ul>
			</dd>
			</dl>
	</div>
	</c:when>
	</c:choose>

</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />