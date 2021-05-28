<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<h3><c:out value="${questionnaire.title}"></c:out></h3>
<small class="helpb-block"><c:out value="${questionnairedescription}" /></small>
<ol>
<c:forEach items="${dataToShow}" var="entry">
<li>${entry.question}
<c:choose>
	<c:when test="${entry.isClosed}">
		<ul><c:forEach items="${entry.answers}" var="answer">
				<li>${answer.answer}
				<c:choose>
						<c:when test="${answer.result == null}"><i class="fa fa-check-empty"></i></c:when>
						<c:when test="${answer.result eq true}"><i class="fa fa-check"></i></c:when>
						<c:when test="${answer.result eq false}"><i class="fa fa-times"></i></c:when>
						<c:otherwise><i class="fa fa-check-empty"></i></c:otherwise>
				</c:choose></li>
			</c:forEach></ul>
	</c:when>
	<c:otherwise>
		<blockquote><c:out value="${entry.openanswer}" /></blockquote>
	</c:otherwise>
</c:choose></li>
</c:forEach>
</ol>