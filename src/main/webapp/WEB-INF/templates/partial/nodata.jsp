<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<p><div class="row"><div class="col-md-12">
<div class="col-md-12 well text-center">
	<div class="margin-top-20">
		<i class="icon-ghost fa-5x animated rollIn" ng-class="{rollOut: hover}" ng-mouseenter="hover = true"></i>
		<h2><spring:message code="nodata.title" text="No data to display!" /></h2>
		<p><spring:message code="nodata.subtitle" text="Your search did not match any records." /></p>
		<ul class="list-unstyled">
		<li class="bold"><spring:message code="suggestions.title" text="Suggestions" /></li>
		<li><spring:message code="suggestions.message.make" text="Make sure that all words are spelled correctly." /></li>
		<li><spring:message code="suggestions.message.different" text="Try different keywords." /></li>
		<li><spring:message code="suggestions.message.general" text="Try more general keywords." /></li>
		<li><spring:message code="suggestions.fewer" text="Try fewer keywords." /></li>
		<li><spring:message code="suggestions.new" text="Create a new record." /></li>
		</ul>
	</div>
</div>
</div></div></p>