<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%  String lang = (new RequestContext(request)).getLocale().getLanguage(); lang = lang=="en_us"?"en":lang; %>
<div class="list-item-content" ng-cloak>
   <section>
    <div class="pull-right"><form class="form-inline" role="form">
	<div class="form-group"><jsp:include page="/WEB-INF/templates/partial/selectFilterBy.jsp" /></div>
	<div class="form-group">
		<div class="input-group">
			<input ng-show="filter.input=='TEXT'" type="text" class="form-control pa-advanced input-large" maxlength="200" placeholder="<spring:message code="value.title" text="Value" />" ng-model="filter.value">
			<input ng-controller="DatePickerCtrl" ng-show="filter.input=='DATEPICKER'" type="text" class="form-control pa-advanced input-large filterpicker-here" data-date-format="dd/mm/yyyy" data-orientation="bottom" readonly="" data-date-language="<%=lang%>" ng-model="filter.value">
			<div ng-show="filter.input=='FILTER_GENDER'"><jsp:include page="/WEB-INF/templates/partial/selectGender.jsp" /></div>
			<div ng-show="filter.input=='FILTER_UNICOURSE'"><jsp:include page="/WEB-INF/templates/partial/selectUniCourse.jsp" /></div>
			<div ng-show="filter.input=='FILTER_SCHOOLCOURSEID'"><jsp:include page="/WEB-INF/templates/partial/selectSchoolCourse.jsp" /></div>
			<div ng-show="filter.input=='FILTER_INSTITUTIONID'"><jsp:include page="/WEB-INF/templates/partial/selectInstitution.jsp" /></div>
			<div ng-show="filter.input=='FILTER_DOCUMENTIDTYPE'"><jsp:include page="/WEB-INF/templates/partial/selectDocumentIdType.jsp" /></div>
			<div class="input-group-btn"><button title="<spring:message code="filter.add.title" text="Add item" />" type="button" class="btn green" ng-disabled="filter.value.length=='0'||form.filterBy.length=='0'" ng-click="addFilterBy();"><i class="fa fa-search-plus"></i></button></div>
		</div>
	</div>
    </form></div>
   <!-- TAGS --> 
<div ng-show="hashmap.length>'0'">
	<div class="row"><div class="col-md-12"><div class="pull-right">
		<ul class="list-inline"><li ng-repeat="(k,h) in hashmap" class="margin-top-10 " id="filterHashmap"><span class="label label-{{h.label}} margin-top-20">{{h.value}}&nbsp;<i class="fa fa-close qz-pointer" ng-click="removeFilterBy(k);"></i></span></li></ul>
	</div></div></div>		
</div>
</section>
</div>