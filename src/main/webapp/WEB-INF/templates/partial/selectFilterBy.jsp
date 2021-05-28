<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<select class="selectpicker" id="filterBy" ng-change="onChangeFilterBy();" data-live-search="true" data-size="60"  ng-model="form.filterBy" title=" &nbsp; &nbsp;<spring:message code="filter.title" text="Filter by" />&nbsp;&nbsp;<spring:message code="by.title" text="by" /> &nbsp; &nbsp;" ng-cloak>
	<option></option>
	<option value="FILTER_START"><spring:message code="fromof.title" text="From" /></option>
	<option value="FILTER_NAME"><spring:message code="filter.name" text="Name" /></option>
	<option value="FILTER_TASKID" id="filter_campaign" class="hide"><spring:message code="campaign.title" text="Campaign" /></option>
	<option value="FILTER_GENDER"><spring:message code="filter.gender" text="Gender" /></option>
	<option value="FILTER_FILESOURCE"><spring:message code="filter.fileSource" text="Source" /></option>
	<option value="FILTER_OFFICIALEMAIL" data-subtext="<spring:message code="title.email" text="Email" />"><spring:message code="username.title" text="Username" /></option>
	<option value="FILTER_CONTACTPHONENUMBER"><spring:message code="filter.contactPhoneNumber" text="Phone" /></option>
	<option value="FILTER_HOMEPHONENUMBER"><spring:message code="filter.homePhoneNumber" text="Cellphone" /></option>
	<option value="FILTER_UNICOURSE"><spring:message code="filter.uniCourse" text="Education" /></option>
	<option value="FILTER_SCHOOLCOURSEID"><spring:message code="filter.schoolCourseId" text="School Course" /></option>
	<option value="FILTER_INSTITUTIONID"><spring:message code="filter.institutionId" text="Institution" /></option>
	<option value="FILTER_UNIYEAR"><spring:message code="filter.uniYear" text="Year" /></option>
	<option value="FILTER_DOCUMENTIDTYPE"><spring:message code="filter.documentIdType" text="Document Type" /></option>
	<option value="FILTER_DOCUMENTID"><spring:message code="filter.documentId" text="Document Number" /></option>
	<option value="FILTER_DEVICE"><spring:message code="filter.device" text="Device" /></option>
	<option value="FILTER_NOTES"><spring:message code="filter.notes" text="Notes" /></option>
	<option value="FILTER_ADDRESS"><spring:message code="filter.address" text="Address" /></option>
	<option value="FILTER_ADDRESSDISTRICT"><spring:message code="filter.addressDistrict" text="Disctrict" /></option>
	<option value="FILTER_ADDRESSCITY"><spring:message code="filter.addressCity" text="City" /></option>
	<option value="FILTER_ADDRESSSTATE"><spring:message code="filter.addressState" text="State" /></option>
	<option value="FILTER_ADDRESSPOSTALCODE"><spring:message code="filter.addressPostalCode" text="Postal Code" /></option>
	<option value="FILTER_ADDRESSCOUNTRY"><spring:message code="filter.addressCountry" text="Coutry" /></option>
</select>