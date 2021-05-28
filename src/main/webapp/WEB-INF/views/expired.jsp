<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/login/header.jsp"></jsp:include>
<!-- BEGIN PAGE -->
  <div class="row">
      <div class="col-md-12 page-500">
          <div class=" details">
              <h3><spring:message code="label.expired.title" text="Oops, your session has expired." /></h3>
              <p><a href="<c:url value="/"/>" class="btn btn-block red btn-outline"><spring:message code="label.title" text="Login" /> </a><br> </p>
          </div>
      </div>
  </div>
<!-- END PAGE -->
<jsp:include page="/WEB-INF/templates/login/footer.jsp"></jsp:include>