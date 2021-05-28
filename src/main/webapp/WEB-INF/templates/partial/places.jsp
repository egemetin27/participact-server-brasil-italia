<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<section ng-controller="PlacesCtrl">
<div class="form-group">
	<label class="col-md-2 control-label"><spring:message code="street.title" text="Street Addresses" /></label>
	<div class="col-md-5">
		<input type="text" id="autocomplete" ng-hide="form.address.length > 0" g-places-autocomplete ng-model="places[0]" class="form-control"  maxlength="255" ng-change="autoComplete(0);"/>
		
		<div class="input-group" ng-show="form.address.length > 0">
	      <input type="text" ng-show="form.address.length > 0" ng-model="form.address" class="form-control"  maxlength="255"/>
	      <span class="input-group-btn">
	        <button class="btn btn-default" type="button" ng-click="cleanAutoComplete();"><i class="fa fa-eraser"></i> </button>
	      </span>
	    </div><!-- /input-group -->								
	</div>
	
	<label class="col-md-1 control-label"><spring:message code="number.title" text="Number" /></label>
	<div class="col-md-1">
		<input type="text" id="street_number" ng-model="form.addressNumber" class="form-control"  maxlength="255"/>
	</div>							
</div><!--/span-->

<div class="form-group">
	<label class="col-md-2 control-label"><spring:message code="district.title" text="District" /></label>
	<div class="col-md-3">
		<input type="text" ng-model="form.addressDistrict" class="form-control"  maxlength="255"/>
	</div>
</div>		

<div class="form-group">
	<label class="col-md-2 control-label"><spring:message code="city.title" text="City" /></label>
	<div class="col-md-3">
		<input type="text" id="locality" ng-model="form.addressCity" class="form-control"  maxlength="255"/>
	</div>

	<label class="col-md-1 control-label"><spring:message code="state.title" text="State" /></label>
	<div class="col-md-3">
		<input type="text" id="administrative_area_level_1" ng-model="form.addressState" class="form-control"/>
	</div>
</div>						
<!--/span-->

<div class="form-group">
	<label class="col-md-2 control-label"><spring:message code="country.title" text="Country" /></label>
	<div class="col-md-3">
		<input type="text" id="country" ng-model="form.addressCountry" class="form-control"  maxlength="255"/>
	</div>

	<label class="col-md-1 control-label"><spring:message code="zipcode.title" text="Postal Code" /></label>
	<div class="col-md-3">
		<input type="text" id="postal_code" ng-model="form.addressPostalCode" class="form-control"  maxlength="20"/>
	</div>
</div>						
<!--/span-->	
<!-- GEO -->
	<div class="form-group" ng-show="false">
		<input type="text" ng-model="form.mapLat" maxlength="255"/>
		<input type="text" ng-model="form.mapLng" maxlength="255"/>
	</div>		
</section>