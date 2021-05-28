<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- BEGIN CONFIRMATION -->
<div tabindex="-1" class="modal fade in" id="confirmationPublish" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header"><button class="close" aria-hidden="true" type="button" data-dismiss="modal"></button><h4 class="modal-title"><spring:message code="confirmation.title" text="Confirmation" /></h4></div>
            <div class="modal-body">
				<div class="portlet yellow-crusta box">
					<div class="portlet-title"><div class="caption">&nbsp;{{picked.name | stripslashes}}</div></div>				
					<div class="portlet-body">
						<div class="row static-info" ng-show="picked.canBeRefused"><div class="col-md-12 name"><small class="help-block"><mark><spring:message code="campaign.refused.message" text="This task can be refused by users" /></mark></small></div></div>
						<div class="row static-info"><div class="col-md-12 name text-justify"><small class="help-block" ng-bind-html="picked.description"></small></div></div>
						<div class="row static-info"><div class="col-md-12 name text-justify"><strong><spring:message code="start.title" text="Start" />&nbsp;:</strong><small class="help-block">{{picked.dt_format_start}}</small></div></div>
						<div class="row static-info"><div class="col-md-12 name text-justify"><strong><spring:message code="end.title" text="End" />&nbsp;:</strong><small class="help-block">{{picked.dt_format_end}}</small></div></div>
						<div class="row static-info"><div class="col-md-12 name text-justify"><strong><spring:message code="sensing.duration.title" text="Sensing Duration" />&nbsp;:</strong><small class="help-block text-lowercase">{{picked.sensingDuration.minutes}}<spring:message code="minutes.title" text="Minutes" />&nbsp;({{picked.sensingDuration.d}}d&nbsp;{{picked.sensingDuration.h}}h&nbsp;{{picked.sensingDuration.m}}m)</small></div></div>
						<div class="row static-info"><div class="col-md-12 name text-justify"><strong><spring:message code="task.duration.title" text="Task Duration" />&nbsp;:</strong><small class="help-block text-lowercase">{{picked.duration.minutes}}<spring:message code="minutes.title" text="Minutes" />&nbsp;({{picked.duration.d}}d&nbsp;{{picked.duration.h}}h&nbsp;{{picked.duration.m}}m)</small></div></div>
					</div>
				</div> 
				<p class="text-justify text-danger"><spring:message code="confirmation.publish.institutions" text="You'll to publish the campaign and send a notification to the participants.. Note: This action can not be undone and the campaign can not be edited after it has been published." /></p>
            </div>
            <div class="modal-footer">
                <button class="btn default pull-left" type="button" data-dismiss="modal"><spring:message code="label.button.cancel" text="Cancel"/></button>
                <button class="btn purple-seance pull-right" type="button" ng-click="confirmPublish();"><i class="fa fa-send"></i>&nbsp;<spring:message code="publish.title" text="Publish" /></button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>	
<!-- END CONFIRMATION -->