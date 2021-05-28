<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="CguEouvCtrl" ng-cloak>
<div class="col-md-12">
		<div class="portlet">
		<!-- BREADCRUMB -->
		<jsp:include page="/WEB-INF/views/protected/cgu-crontab/breadcrumb.jsp" />
		<!-- BEGIN DISPLAY -->
		<div class="portlet-body margin-top-20">
			<form class="navbar-form">
				<div class="form-group">
					<input type="number" min="1" name="hh" id="hh" ng-model="form.hh" ng-init="form.hh=${hh}" class="form-control input-lg" placeholder="<spring:message code="hour.title" text="Hour" />">
				</div>
				<button type="button" class="btn btn-success btn-lg" ng-click="crontab();">&nbsp;<spring:message code="save.title" text="Save" />&nbsp;</button>
			</form>
		</div>
		<!-- END DISPLAY -->	
		</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />