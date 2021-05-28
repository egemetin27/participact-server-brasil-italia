<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="DrawingManagerCtrl as vm">
	  <div id="vm-panel" class="pull-right">
	  	<button class="btn red" ng-click="vm.deleteSelectedShape();"><i class="fa fa-trash"></i>&nbsp;<spring:message code="remove.selected.title" text="Remove selected " /></button>
	  	<!-- <button class="btn default" ng-click="vm.deleteAllSelectedShape();"><i class="fa fa-eraser"></i>&nbsp;<spring:message code="clean.title" text="Reset " /></button> -->
	  </div>	
	  <ng-map zoom="13" center=" -27.586347, -48.502900" class="ng-map" map-type-id="ROADMAP" street-view-control-options="{position: 'LEFT_CENTER'}">
	    <drawing-manager
	      on-overlaycomplete="vm.onMapOverlayCompleted()"
	      drawing-control-options="{position: 'TOP_CENTER',drawingModes:['polygon','circle','rectangle']}"
	      drawingControl="true"
	      drawingMode="null"
	      rectangleOptions="{fillColor:'#F44336',strokeWeight:1,clickable: false,zIndex: 1,editable: false}"
	      circleOptions="{fillColor: '#2196F3',strokeWeight:1,clickable: false,zIndex: 1,editable: false}"
	      polygonOptions="{fillColor: '#4CAF50',strokeWeight:1,clickable: false,zIndex: 1,editable: false}" >
	    </drawing-manager>
	  </ng-map>
</div>			