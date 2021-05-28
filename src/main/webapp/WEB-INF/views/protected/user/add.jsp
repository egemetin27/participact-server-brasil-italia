<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="protected.user.add.title" name="title" />
</jsp:include>
<script src="<c:url value="/resources/1.0/js/bootstrap-datepicker.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/1.0/css/datepicker.css"/>">
<script>
	function updateInputs() {
		var disableDomicileInputs = $('#isDomicileEqualToCurrentAddr1').is(
				':checked');
		var disableUniYearInput = $('#uniIsSupplementaryYear1').is(':checked');
		var disableSIMdata = $('#simStatus').val() != "PORTABILITY";
		if (disableDomicileInputs) {
			$('#domicileAddress').prop('disabled', true).val("");
			$('#domicileCity').prop('disabled', true).val("");
			$('#domicileZipCode').prop('disabled', true).val("");
			$('#domicileProvince').prop('disabled', true).val("");
			$('#domicile_address').slideUp();
		}

		if (disableUniYearInput) {
			$('#uniYear').prop('disabled', true).val("");
		}

		if (disableSIMdata) {
			$('#cf').prop('disabled', true).val("");
			$('#projectPhoneNumber').prop('disabled', true).val("");
			$('#originalPhoneOperator').prop('disabled', true).val("");
			$('#iccid').prop('disabled', true).val("");
			$('#siminfo').slideUp();
		}
	}
	$(document).ready(function() {
// 		$('#birthdate').datepicker({
// 			format : 'yyyy-mm-dd',
// 			viewMode : 'years'
// 		});

		$('#isDomicileEqualToCurrentAddr1').change(function() {
			var disableInput = $(this).is(':checked');
			$('#domicileAddress').prop('disabled', disableInput).val("");
			$('#domicileCity').prop('disabled', disableInput).val("");
			$('#domicileZipCode').prop('disabled', disableInput).val("");
			$('#domicileProvince').prop('disabled', disableInput).val("");
			if (disableInput == true) {
				$('#domicile_address').slideUp();
			} else {
				$('#domicile_address').slideDown();
			}
		});

		$('#uniIsSupplementaryYear1').change(function() {
			var disableInput = $(this).is(':checked');
			$('#uniYear').prop('disabled', disableInput).val("");
		});

		$('#officialEmail').on('keyup keydown keypress', function(e) {
			$('#proj_email').text($('input[id=officialEmail]').val());
		});

		$('#simStatus').change(function() {
			var disableSIMdata = $('#simStatus').val() != "PORTABILITY";
			$('#cf').prop('disabled', disableSIMdata).val("");
			$('#projectPhoneNumber').prop('disabled', disableSIMdata).val("");
			$('#originalPhoneOperator').prop('disabled', disableSIMdata).val("");
			$('#iccid').prop('disabled', disableSIMdata).val("");
			if (disableSIMdata) {
				$('#siminfo').slideUp();
			} else {
				$('#siminfo').slideDown();
			}
		});
		updateInputs();
	});
</script>
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
					<jsp:param value="protected.user.add" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>Add user accounts ParticipAct</h1>
				</div>
				<c:if test="${not empty formerror}">
					<div class="alert alert-error fade in" id="loginerror">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						Error creating account. Check the form again.
					</div>
				</c:if>
				<spring:url value="/protected/user/addAccount" var="addUser"></spring:url>
				<form:form method="POST" modelAttribute="addAdminUserForm"
					action="${addUser}" enctype="multipart/form-data">
					<fieldset>
						<div class="span9">
							<div class="span6">
								<h2>Contact</h2>
								<t:input path="name" label="Name*" placeholder="Mario"
									inputprepend="fa fa-user" />
								<t:input path="surname" label="Surname*" placeholder="Rossi"
									inputprepend="fa fa-user" />
								<t:input path="birthdate" label="Birthday (AAAA-MM-GG)*"
									placeholder="AAAA-MM-GG" inputprepend="fa fa-calendar" />
								<t:radiobuttons label="Gender*" items="${genders}" path="gender" />

								<h3>Address</h3>
								<t:input path="currentAddress" label="Address*"
									placeholder="v.le Risorgimento 2" inputprepend="fa fa-envelope" />
								<t:input path="currentZipCode" label="CAP*" placeholder="40135"
									inputprepend="fa fa-map-marker" />
								<t:input path="currentCity" label="City*"
									placeholder="Bologna" inputprepend="fa fa-map-marker" />
								<t:input path="currentProvince" label="State*"
									placeholder="BO" inputprepend="fa fa-map-marker" />

								<h3>Home Address</h3>
								<t:checkbox label="The address is equal to the residence."
									path="isDomicileEqualToCurrentAddr"></t:checkbox>
								<div id="domicile_address">
									<t:input path="domicileAddress" label="Address"
										placeholder="v.le Risorgimento 2" inputprepend="fa fa-envelope" />
									<t:input path="domicileZipCode" label="Postal Code" placeholder="40135"
										inputprepend="fa fa-map-marker" />
									<t:input path="domicileCity" label="City"
										placeholder="Bologna" inputprepend="fa fa-map-marker" />
									<t:input path="domicileProvince" label="State"
										placeholder="BO" inputprepend="fa fa-map-marker" />
								</div>

								<h3>Contact Information</h3>
								<span class="label label-warning">Attention</span>
								<p>The official e-mail of the university will be used for all official communication of the project. 
								In addition, the user identifier will be included on the app and smartphone on the website of ParticipAct.</p>
								<t:input path="officialEmail"
									label="Email official of the university*"
									placeholder="nome.cognome@studio.unibo.it"
									stringinputprepend="@" />
								<t:input path="contactPhoneNumber"
									label="Primary Telephone Number*"
									placeholder="+39 051 12345678" inputprepend="fa fa-phone" />
								<t:input path="homePhoneNumber" label="Home phone number"
									placeholder="+39 051 12345678" inputprepend="fa fa-phone" />
									
								<t:select label="Device"
									path="device" items="${devices}"></t:select>

								<t:select label="Identity"
									path="documentIdType" items="${documentTypes}"></t:select>
								<t:input path="documentId" label="Document Number*"
									placeholder="AJ000000" inputprepend="fa fa-tag" />

								<h2>University Satus</h2>
								<t:select label="City*" path="uniCity" items="${uniCities}"></t:select>
								<t:select label="Faculty*" path="uniSchool" items="${uniSchools}"></t:select>
								<t:input path="uniDepartment" label="Department*"
									placeholder="DISI" inputprepend="fa fa-book" />
								<t:input path="uniDegree" label="Course of Study"
									placeholder="Ingegneria Informatica" inputprepend="fa fa-book" />
								<t:select label="Degree course" path="uniCourse"
									items="${uniCourses}"></t:select>
								<t:checkbox path="uniIsSupplementaryYear" label="Off course" />
								<t:input path="uniYear" label="Year of the course" placeholder="2"
									inputprepend="fa fa-book" />

								<h2>Information on the project</h2>
								<span class="label label-warning">Attention</span>
								<p>Enter the address in the following field Google Account that will be associated with your smartphone assigned.</p>
								<p>
									<a href="https://accounts.google.com/SignUp" target="_blank">Click here to create a new Google Account or associate an e-mail to an existing Google Account</a>.
								</p>
								<t:input path="projectEmail" label="E-mail for the project*"
									placeholder="nome.cognome@gmail.com"
									inputprepend="fa fa-google-plus" />
								<t:input path="facebookEmail" label="E-mail account Facebook"
									placeholder="nome.cognome@gmail.com"
									inputprepend="fa fa-facebook" />
								<t:checkbox label="You want to get the phone"
									path="wantsPhone" />
								<t:select label="SIM" items="${simStatuses}" path="simStatus" />
								<div id="siminfo">
									<t:input path="cf" label="Tax code"
										placeholder="MRARSS92C04F824P" inputprepend="fa fa-credit-card" />
									<t:input path="projectPhoneNumber"
										label="Phone number for the project"
										placeholder="+39 349 12345678" inputprepend="fa fa-phone" />
									<t:input path="originalPhoneOperator"
										label="Operatore telefonico di partenza"
										inputprepend="fa fa-building" />
									<t:input path="iccid"
										label="ICCID della SIM (riportato sul retro della SIM stessa)"
										inputprepend="fa fa-phone" />
									<p>Upload a scan of the ID.</p>
									<form:input path="idScan" type="file"/>
									<p>Upload a scan last payment receipt (for users with subscription).</p>
									<form:input path="lastInvoiceScan" type="file"/>
								</div>
								<span class="label label-info">Information</span>
								<p>
									The identifier that will be used on the Android app and the site of ParticipAct is &egrave; &quot;<strong><span
										id="proj_email"></span></strong>&quot;. Choose a password for this account.
								</p>
								<t:password path="password" label="Password*"
									inputprepend="fa fa-key" />
								<t:password path="confirmPassword" label="Confirm password*"
									inputprepend="fa fa-key" />
								<h2>Additional Notes</h2>
								<t:textarea path="notes" cssClass="span6" label="Additional notes on this user, free text."></t:textarea>
							</div>
						</div>
						<div class="span2">
							<button class="btn btn-primary" type="submit">Save</button>
							<a class="btn btn-danger" type="submit"
								href='<c:url value="/protected/user/add"/>'>Cancel</a>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>