<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="it.unibo.paserver.domain.AudienceSelector" %>
<div class="portlet-title">
	<div class="caption"><span class="caption-subject font-green sbold uppercase"><spring:message code="participants.title" text="Participants" /></span></div>
	<div class="pull-right">
		<!--  RETURN -->
		<a href="javascript:history.back();" class="btn default" role="button"><i class="fa fa-angle-left"></i>&nbsp;&nbsp;<spring:message code="goback.title" text="Back" />&nbsp;&nbsp;</a>
		<!-- SAVE BUTTON -->
		<div class="btn-group" ng-show="audienceSelector=='<%=AudienceSelector.SELECTOR_ALL.name()%>'||(audienceSelector=='<%=AudienceSelector.SELECTOR_RESTRICTED.name()%>'&&hashmap.length>0)||(audienceSelector=='<%=AudienceSelector.SELECTOR_CLOSED.name()%>'&&itemList.length>0)">
			<button type="button" class="btn green btn-block animated bounce uppercase" ng-click="saveParticipantList(${userListId});" >&nbsp;<i class="fa fa-save" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;<spring:message code="save.title" text="Save"/>&nbsp;&nbsp;&nbsp;</button>
		</div>			
		<!-- ./SAVE BUTTON -->	
	
		<div class="btn-group">
		  <button
		  	id="<%=AudienceSelector.SELECTOR_ALL.name()%>"
		  	ng-click="audienceSelector='<%=AudienceSelector.SELECTOR_ALL.name()%>'" 
		  	ng-class="audienceSelector=='<%=AudienceSelector.SELECTOR_ALL.name()%>'?'btn-success':'btn-default'"
		  	type="button" class="btn uppercase">&nbsp;<i class="fa fa-globe" aria-hidden="true"></i>&nbsp;&nbsp;<spring:message code="filter.all" text="All" /></button>
		  	<!-- 
		  <button
		  	id="<%=AudienceSelector.SELECTOR_RESTRICTED.name()%>"
		  	ng-click="audienceSelector='<%=AudienceSelector.SELECTOR_RESTRICTED.name()%>'"
		  	ng-class="audienceSelector=='<%=AudienceSelector.SELECTOR_RESTRICTED.name()%>'?'btn-primary':'btn-default'" 
		  	type="button" class="btn uppercase">&nbsp;<i class="fa fa-users" aria-hidden="true"></i>&nbsp;&nbsp;<spring:message code="filter.restricted" text="Restricted" /></button>
		  	 -->
		  <button 
		  	id="<%=AudienceSelector.SELECTOR_CLOSED.name()%>"	
		  	ng-click="audienceSelector='<%=AudienceSelector.SELECTOR_CLOSED.name()%>'"
		  	ng-class="audienceSelector=='<%=AudienceSelector.SELECTOR_CLOSED.name()%>'?'btn-warning':'btn-default'"
		  	type="button" class="btn uppercase">&nbsp;<i class="fa fa-lock" aria-hidden="true"></i>&nbsp;&nbsp;<spring:message code="filter.closed" text="Closed" /></button>
		</div>
	</div>
</div>