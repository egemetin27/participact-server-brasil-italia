<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.user.edit.title" name="title" />
</jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.user" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>User account</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/userSidenav.jsp">
					<jsp:param value="protected.user.edit" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>Edit ParticipAct user</h1>
				</div>
				<!-- String fixo / Mensagem fixa -->
				<c:if test="${not empty formerror}">
					<div class="alert alert-error fade in" id="loginerror">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						Errore nella creazione dell'account. Ricontrollare il form.
					</div>
				</c:if>
				<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
				<spring:url value="/protected/user/edit/${userId}" var="editAdminUserForm"></spring:url>
				</sec:authorize>
				<form:form method="POST" modelAttribute="editAdminUserForm" action="${editAdminUserForm}" enctype="multipart/form-data">
					<fieldset>
						<div class="span9">
							<div class="span6">
								<h2>Contact</h2>
								<t:input path="name" label="Name*" placeholder="Mario" inputprepend="fa fa-user" />
								<t:input path="surname" label="Surname*" placeholder="Rossi" inputprepend="fa fa-user" />
								<t:input path="birthdate" label="Birthdate (AAAA-MM-GG)*" placeholder="AAAA-MM-GG"
									inputprepend="fa fa-calendar" />
								<t:radiobuttons label="Gender*" items="${genders}" path="gender" />

								<h3>Contact Information</h3>
								<t:input path="officialEmail" label="Email*" placeholder="nome.cognome@studio.unibo.it"
									stringinputprepend="@" />
								<t:input path="contactPhoneNumber" label="Primary Telephone number*" placeholder="+39 051 12345678"
									inputprepend="fa fa-phone" />
								<t:input path="homePhoneNumber" label="Home number" placeholder="+39 051 12345678"
									inputprepend="fa fa-phone" />
									
									<t:select label="Device"
									path="device" items="${devices}"></t:select>

								<t:select label="Identity*" path="documentIdType" items="${documentTypes}"></t:select>
								<t:input path="documentId" label="Number*" placeholder="AJ000000" inputprepend="fa fa-tag" />
								
								<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
								<h2>Information for the project</h2>
								<t:input path="projectEmail" label="E-mail*" placeholder="nome.cognome@gmail.com"
									inputprepend="fa fa-google-plus" />
								<t:checkbox label="User is active" path="isActive"></t:checkbox>
								<t:input path="facebookEmail" label="E-mail account Facebook" placeholder="nome.cognome@gmail.com"
									inputprepend="fa fa-facebook" />
								<t:input path="projectPhoneNumber" label="Telephone number of the project" placeholder="+39 349 12345678"
									inputprepend="fa fa-phone" />
								<t:checkbox label="You want to get a phone" path="wantsPhone" />
								<t:checkbox label="You have a phone" path="hasPhone" />
								<t:input path="imei" label="Phone IMEI" />
								<t:checkbox label="The user has the SIM" path="hasSIM" />
								<t:checkbox label="The registered user of TIM" path="isMyCompanyRegistered" />
								<t:input path="newIccid" label="ICCID of the NEW SIM" />
								<t:input path="receivedPhoneOn"
									label="Date WITHDRAWAL phone. If left empty, PUT is a check mark in the user has the phone, this field will be filled automatically with the current date."
									placeholder="AAAA-MM-GG" inputprepend="fa fa-calendar" />
								<t:input path="returnedPhoneOn"
									label="Date RETURN phone. If left empty and REMOVED is a check mark in the user has the phone, this field will be filled automatically with the current date."
									placeholder="AAAA-MM-GG" inputprepend="fa fa-calendar" />
								<t:input path="activatedSIMOn"
									label="Date ACTIVATION of the SIM."
									placeholder="AAAA-MM-GG" inputprepend="fa fa-calendar" />
								<t:input path="receivedSIMOn"
									label="Date WITHDRAWAL SIM. If left empty, PUT is a check mark in the user has a SIM, this field will be filled automatically with the current date."
									placeholder="AAAA-MM-GG" inputprepend="fa fa-calendar" />
								<t:input path="returnedSIMOn"
									label="Date RETURN SIM. If left empty and REMOVED is a check mark in 'The user has the SIM card', this field will be filled automatically with the current date."
									placeholder="AAAA-MM-GG" inputprepend="fa fa-calendar" />
								<t:password path="password" label="Password" inputprepend="fa fa-key" />
								<t:password path="confirmPassword" label="Confirm password" inputprepend="fa fa-key" />
								<t:select label="SIM" items="${simStatuses}" path="simStatus" />
								<t:input path="cf" label="Fiscal code" placeholder="MRARSS92C04F824P" inputprepend="fa fa-credit-card" />
								<t:input path="originalPhoneOperator" label="Phone operator starting" inputprepend="fa fa-building" />
								<t:input path="iccid" label="ICCID of OLD SIM (on the back of the SIM)"
									inputprepend="fa fa-phone" />
								<p>Upload a scan of the identity document.</p>
								<form:input path="idScan" type="file" />
								<br /> <br />
								<p>Upload a scan last payment receipt (for users with subscription).</p>
								<form:input path="lastInvoiceScan" type="file" />
								<br /> <br />
								<p>Scanning release privacy</p>
								<form:input path="privacyScan" type="file" />
								<br /> <br />
								<p>Taken delivery phone</p>
								<form:input path="presaConsegnaPhoneScan" type="file" />
								<br /> <br />
								<p>Taken deliverySIM</p>
								<form:input path="presaConsegnaSIMScan" type="file" />
								<br /> <br />
								</sec:authorize>
								<h3>Address</h3>
								<t:input path="currentAddress" label="Address*" placeholder="v.le Risorgimento 2" inputprepend="fa fa-envelope" />
								<t:input path="currentZipCode" label="Postal Code*" placeholder="40135" inputprepend="fa fa-map-marker" />
								<t:input path="currentCity" label="City*" placeholder="Bologna" inputprepend="fa fa-map-marker" />
								<t:input path="currentProvince" label="State*" placeholder="BO" inputprepend="fa fa-map-marker" />

								<h3>Home Address</h3>
								<t:input path="domicileAddress" label="Address" placeholder="v.le Risorgimento 2" inputprepend="fa fa-envelope" />
								<t:input path="domicileZipCode" label="Postal Code" placeholder="40135" inputprepend="fa fa-map-marker" />
								<t:input path="domicileCity" label="City" placeholder="Bologna" inputprepend="fa fa-map-marker" />
								<t:input path="domicileProvince" label="State" placeholder="BO" inputprepend="fa fa-map-marker" />

								<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
								<h2>University Status</h2>
								<t:select label="Local*" path="uniCity" items="${uniCities}"></t:select>
								<t:select label="School*" path="uniSchool" items="${uniSchools}"></t:select>
								<t:input path="uniDepartment" label="Departamente*" placeholder="DISI" inputprepend="fa fa-book" />
								<t:input path="uniDegree" label="Course" placeholder="Ingegneria Informatica" inputprepend="fa fa-book" />
								<t:select label="Degree course" path="uniCourse" items="${uniCourses}"></t:select>
								<t:checkbox path="uniIsSupplementaryYear" label="Not student" />
								<t:input path="uniYear" label="Year of the course" placeholder="2" inputprepend="fa fa-book" />
								</sec:authorize>
								<h2>Additional Notes</h2>
								<t:textarea path="notes" cssClass="span6" label="Additional notes on this user, free text."></t:textarea>
							</div>
						</div>
						<div class="span2">
							<button class="btn btn-primary" type="submit">Save</button>
							<a class="btn btn-danger" type="submit" href='<c:url value="/protected/user"/>'>Cancel</a>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>