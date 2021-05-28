<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="it.unibo.paserver.domain.support.Pipeline" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedHashSet" %>
<%@ page import="it.unibo.paserver.domain.ActionSensing" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  List<ActionSensing> hasSensors = (List) request.getAttribute("hasSensors"); %> 
<select class="selectpicker pull-right form-control" ng-change="onChangePipelineType();" data-live-search="true" id="pipelineType" name="pipelineType" data-size="20" ng-model="form.pipelineType" title="<spring:message code="pipeline.type.title" text="Pipeline Type" />" required="required">
	<option></option>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.ACCELEROMETER.toInt())){%> <option value="<%=Pipeline.Type.ACCELEROMETER.name()%>"><spring:message code="pipeline.type.accelerometer" text="ACCELEROMETER" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.ACCELEROMETER_CLASSIFIER.toInt())){%> <option value="<%=Pipeline.Type.ACCELEROMETER_CLASSIFIER.name()%>"><spring:message code="pipeline.type.accelerometer_classifier" text="ACCELEROMETER CLASSIFIER" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.ACTIVITY_RECOGNITION_COMPARE.toInt())){%> <option value="<%=Pipeline.Type.ACTIVITY_RECOGNITION_COMPARE.name()%>"><spring:message code="pipeline.type.activity_recognition_compare" text="ACTIVITY RECOGNITION COMPARE" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.APP_ON_SCREEN.toInt())){%> <option value="<%=Pipeline.Type.APP_ON_SCREEN.name()%>"><spring:message code="pipeline.type.app_on_screen" text="APP ON SCREEN" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.APPS_NET_TRAFFIC.toInt())){%> <option value="<%=Pipeline.Type.APPS_NET_TRAFFIC.name()%>"><spring:message code="pipeline.type.apps_net_traffic" text="APPS NET TRAFFIC" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.BATTERY.toInt())){%> <option value="<%=Pipeline.Type.BATTERY.name()%>"><spring:message code="pipeline.type.battery" text="BATTERY" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.BLUETOOTH.toInt())){%> <option value="<%=Pipeline.Type.BLUETOOTH.name()%>"><spring:message code="pipeline.type.bluetooth" text="BLUETOOTH" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.CELL.toInt())){%> <option value="<%=Pipeline.Type.CELL.name()%>"><spring:message code="pipeline.type.cell" text="CELL" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.CONNECTION_TYPE.toInt())){%> <option value="<%=Pipeline.Type.CONNECTION_TYPE.name()%>"><spring:message code="pipeline.type.connection_type" text="CONNECTION TYPE" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.DEVICE_NET_TRAFFIC.toInt())){%> <option value="<%=Pipeline.Type.DEVICE_NET_TRAFFIC.name()%>"><spring:message code="pipeline.type.device_net_traffic" text="DEVICE_NET_TRAFFIC" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.GOOGLE_ACTIVITY_RECOGNITION.toInt())){%> <option value="<%=Pipeline.Type.GOOGLE_ACTIVITY_RECOGNITION.name()%>"><spring:message code="pipeline.type.google_activity_recognition" text="GOOGLE_ACTIVITY_RECOGNITION" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.GYROSCOPE.toInt())){%> <option value="<%=Pipeline.Type.GYROSCOPE.name()%>"><spring:message code="pipeline.type.gyroscope" text="GYROSCOPE" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.INSTALLED_APPS.toInt())){%> <option value="<%=Pipeline.Type.INSTALLED_APPS.name()%>"><spring:message code="pipeline.type.installed_apps" text="INSTALLED_APPS" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.LIGHT.toInt())){%> <option value="<%=Pipeline.Type.LIGHT.name()%>"><spring:message code="pipeline.type.light" text="LIGHT" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.LOCATION.toInt())){%> <option value="<%=Pipeline.Type.LOCATION.name()%>"><spring:message code="pipeline.type.location" text="LOCATION" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.MAGNETIC_FIELD.toInt())){%> <option value="<%=Pipeline.Type.MAGNETIC_FIELD.name()%>"><spring:message code="pipeline.type.magnetic_field" text="MAGNETIC FIELD" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.PHONE_CALL_DURATION.toInt())){%> <option value="<%=Pipeline.Type.PHONE_CALL_DURATION.name()%>"><spring:message code="pipeline.type.phone_call_duration" text="PHONE CALL DURATION" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.PHONE_CALL_EVENT.toInt())){%> <option value="<%=Pipeline.Type.PHONE_CALL_EVENT.name()%>"><spring:message code="pipeline.type.phone_call_event" text="PHONE CALL EVENT" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.SYSTEM_STATS.toInt())){%> <option value="<%=Pipeline.Type.SYSTEM_STATS.name()%>"><spring:message code="pipeline.type.system_stats" text="SYSTEM STATS" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.WIFI_SCAN.toInt())){%> <option value="<%=Pipeline.Type.WIFI_SCAN.name()%>"><spring:message code="pipeline.type.wifi_scan" text="WIFI SCAN" /></option><% } %>
	<% if(!Pipeline.Type.hasPipelineType(hasSensors, Pipeline.Type.DR.toInt())){%> <option value="<%=Pipeline.Type.DR.name()%>"><spring:message code="pipeline.type.dr" text="DR" /></option><% } %>
</select>