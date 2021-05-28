<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-show="totalItems > 10">
	<ul uib-pagination ng-change="setDataPagination(${taskId},${userId},${actionId})" items-per-page="itemsPerPage" total-items="totalItems" ng-model="currentPage" max-size="maxSize" class="pagination-sm" boundary-links="true" rotate="false" num-pages="numPages" previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;" last-text="&raquo;"></ul>
	<div class="pull-right">
	<select class="form-control input-sm" ng-model="radioModel" ng-change="setDataPagination(${taskId},${userId},${actionId})">
		<option value="10">10</option>
		<option value="25">25</option>
		<option value="50">50</option>
		<option value="100">100</option>
		<option value="500">500</option>
		<option value="1000">1000</option>
	</select>
	</div>				
</div>	