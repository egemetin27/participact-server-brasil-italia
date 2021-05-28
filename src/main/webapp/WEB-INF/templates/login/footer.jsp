<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
       </div>
      <!-- END CONTENT -->
      <!-- COPYRIGHT -->
      <section class="copyright-pallete">
      	<jsp:include page="/WEB-INF/templates/partial/copyright.jsp"></jsp:include>
      </section> 
      
	<!-- BEGIN SCRIPTS -->
    <script src="<c:url value="/resources/2.0/assets/bower_components/jquery/dist/jquery.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/bootstrap/dist/js/bootstrap.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/js-cookie/src/js.cookie.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/jquery-slimscroll/jquery.slimscroll.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/blockUI/jquery.blockUI.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/bootstrap-switch/dist/js/bootstrap-switch.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/angular/angular.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/angular-loading-bar/build/loading-bar.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/angular-animate/angular-animate.min.js"/>" type="text/javascript"></script>
	<!-- END SCRIPTS-->
	<!-- BEGIN ANGULAR -->
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/functions.js"/>"></script>
	<script src="<c:url value="/resources/2.0/js/app/LoginApp.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/2.0/js/app/controllers/LoginCtrl.js"/>" type="text/javascript"></script>
	<!-- END ANGULAR -->
</body>
</html>