<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="it.unibo.paserver.domain.UniCourse" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<select class="selectpicker" ng-change="onChangeUniCourse();" data-live-search="true" id="uniCourse" data-size="20" ng-model="form.uniCourse" title="<spring:message code="education.literacy" text="Literacy" />">
  <option></option>
  <option value="<%=UniCourse.CHILD_EDUCATION.name()%>"><spring:message code="education.child.title" text="Child Education" /></option>
  <option value="<%=UniCourse.ELEMENTARY_SCHOOL.name()%>"><spring:message code="education.elementary.title" text="Elementary School" /></option>
  <option value="<%=UniCourse.HIGH_SCHOOL.name()%>"><spring:message code="education.high.title" text="High School" /></option>
  <option value="<%=UniCourse.TECHNICAL_EDUCATION.name()%>"><spring:message code="education.techincal.title" text="Technical Education" /></option>
  <option value="<%=UniCourse.GRADUATION.name()%>"><spring:message code="education.graduation.title" text="Degree" /></option>
  <option value="<%=UniCourse.MASTER_DEGREE.name()%>"><spring:message code="education.master.title" text="Master's Degree" /></option>
  <option value="<%=UniCourse.DOCTORATE_DEGREE.name()%>"><spring:message code="education.doctorate.title" text="Doctorate Degree" /></option>
  <option value="<%=UniCourse.POSTDOCTORAL.name()%>"><spring:message code="education.postdoctoral.title" text="PhD" /></option>
</select>