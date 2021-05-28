<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- BEGIN RESEND -->
<div tabindex="-1" class="modal fade in" id="resendEmailModal" role="basic" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header"><button class="close" aria-hidden="true" type="button" data-dismiss="modal"></button><h4 class="modal-title"><spring:message code="confirmation.title" text="Confirmation" /></h4></div>
            <div class="modal-body">
				<div class="portlet yellow-crusta box">
					<div class="portlet-title"><div class="caption">&nbsp;{{form.name | stripslashes}}</div></div>				
					<div class="portlet-body form">
					<form class="form-horizontal" role="form" name="formResendEmail">
					<div class="form-body">
					<div class="form-group">
						<jsp:include page="/WEB-INF/templates/partial/emailForm.jsp" />
					</div>	
					</div>	
					</form>
					</div>
				</div> 
            </div>
            <div class="modal-footer">
                <button class="btn default pull-left" type="button" data-dismiss="modal"><spring:message code="label.button.cancel" text="Cancel"/></button>
                <button class="btn blue pull-right" type="button" ng-click="resendEmail(${id});"><i class="fa fa-envelope"></i>&nbsp;<spring:message code="resend.title" text="Resend" /></button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>	
<!-- END RESEND -->