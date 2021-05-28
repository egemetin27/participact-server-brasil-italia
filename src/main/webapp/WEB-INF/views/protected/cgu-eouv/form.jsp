<%--
  Created by IntelliJ IDEA.
  User: Claudio
  Date: 29/04/2019
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN -->
<div class="row" ng-controller="CguEouvCtrl" ng-cloak>
    <div class="col-md-12">
        <div class="portlet">
            <!-- BREADCRUMB -->
            <jsp:include page="/WEB-INF/views/protected/cgu-eouv/breadcrumb.jsp" />
            <!-- BODY -->
            <!-- END BODY -->
        </div>
    </div>
</div>
<!-- END -->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />

