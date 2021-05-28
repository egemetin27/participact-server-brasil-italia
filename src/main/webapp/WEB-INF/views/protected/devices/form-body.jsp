<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- CORPO DO FORMULARIO -->
<div class="form-group">
	<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="information.title" text="Informations" /></label>
</div>					

<!-- BRAND -->
<div class="form-group" ng-class="formDevices.brand.$invalid?'has-error':'has-success'">
	<label class="col-md-2 control-label bold"><spring:message code="brand.title" text="Brand" /></label>
	<div class="col-md-7">
		<input type="text" ng-model="form.brand" name="brand" class="form-control"  maxlength="100" required="required"  placeholder="Apple, Google, Motorola .."/>
		<small class="help-block"><spring:message code="changelist.help.message" text="The consumer-visible brand with which the product/hardware will be associated, if any." /></small>
	</div>
</div>

<!-- model -->
<div class="form-group" ng-class="formDevices.model.$invalid?'has-error':'has-success'">
	<label class="col-md-2 control-label bold"><spring:message code="model.title" text="Model" /></label>
	<div class="col-md-7">
		<input type="text" ng-model="form.model" name="model" class="form-control"  maxlength="100" required="required" placeholder="Iphone, Nexus, Moto G" />
		<small class="help-block"><spring:message code="model.help.message" text="The end-user-visible name for the end product." /></small>
	</div>
</div>

<!-- manufacturer -->
<div class="form-group ">
	<label class="col-md-2 control-label bold"><spring:message code="manufacturer.title" text="Manufacturer" /></label>
	<div class="col-md-7">
		<input type="text" ng-model="form.manufacturer" class="form-control"  maxlength="100"/>
		<small class="help-block"><spring:message code="manufacturer.help.message" text="The manufacturer of the product/hardware." /></small>
	</div>
</div>

<!-- display -->
<div class="form-group ">
	<label class="col-md-2 control-label bold"><spring:message code="display.title" text="Display" /></label>
	<div class="col-md-7">
		<input type="text" ng-model="form.display" class="form-control"  maxlength="100"/>
		<small class="help-block"><spring:message code="display.help.message" text="A build ID string meant for displaying to the user." /></small>
	</div>
</div>

<!-- fingerprint -->
<div class="form-group ">
	<label class="col-md-2 control-label bold"><spring:message code="fingerprint.title" text="Fingerprint" /></label>
	<div class="col-md-7">
		<input type="text" ng-model="form.fingerprint" class="form-control"  maxlength="100"/>
		<small class="help-block"><spring:message code="fingerprint.help.message" text="A string that uniquely identifies this build." /></small>
	</div>
</div>	

<!-- hardware -->
<div class="form-group ">
	<label class="col-md-2 control-label bold"><spring:message code="hardware.title" text="Hardware" /></label>
	<div class="col-md-7">
		<input type="text" ng-model="form.hardware" class="form-control"  maxlength="100"/>
		<small class="help-block"><spring:message code="hardware.help.message" text="The name of the hardware (from the kernel command line or /proc)." /></small>
	</div>
</div>	

<!-- tags -->
<div class="form-group ">
	<label class="col-md-2 control-label bold"><spring:message code="tags.title" text="Tags" /></label>
	<div class="col-md-7">
		<input type="text" ng-model="form.tags" class="form-control"  maxlength="100"/>
		<small class="help-block"><spring:message code="tags.help.message" text="Comma-separated tags describing the build, like unsigned, debug." /></small>
	</div>
</div>	

<!-- type -->
<div class="form-group ">
	<label class="col-md-2 control-label bold"><spring:message code="type.title" text="Brand" /></label>
	<div class="col-md-7">
		<input type="text" ng-model="form.type" class="form-control"  maxlength="100"/>
		<small class="help-block"><spring:message code="type.help.message" text="The type of build, like user or eng." /></small>
	</div>
</div>

<!-- changelist -->
<div class="form-group ">
	<label class="col-md-2 control-label bold"><spring:message code="changelist.title" text="Changelist" /></label>
	<div class="col-md-7">
		<input type="text" ng-model="form.changelist" class="form-control"  maxlength="100"/>
		<small class="help-block"><spring:message code="changelist.help.message" text="Either a changelist number, or a label like M4-rc20." /></small>
	</div>
</div>