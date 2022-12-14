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
	<jsp:param value="protected.user.show.title" name="title" />
</jsp:include>

<script src="<c:url value="/resources/1.0/js/bootstrap-datepicker.js"/>"></script>
<script src="<c:url value="/resources/1.0/js/highcharts/highcharts.js"/>"></script>
<script
	src="<c:url value="/resources/1.0/js/highcharts/modules/exporting.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/1.0/css/datepicker.css"/>">





<script>
	//alert("data");

	$
			.getJSON(
					'<c:url value="/user/${user.id}/usertaskstats" />',
					function(data) {

						$('#chartTaskUser')
								.highcharts(
										{
											chart : {
												type : 'area'
											},
											title : {
												text : 'Task created by user'
											},
											xAxis : {
												categories : data.months
											},
											yAxis : {
												min : 0,
												title : {
													text : 'Total task created'
												},
												stackLabels : {
													enabled : true,
													style : {
														fontWeight : 'bold',
														color : (Highcharts.theme && Highcharts.theme.textColor)
																|| 'gray'
													}
												}
											},
											legend : {
												align : 'right',
												x : -70,
												verticalAlign : 'top',
												y : 20,
												floating : true,
												backgroundColor : (Highcharts.theme && Highcharts.theme.background2)
														|| 'white',
												borderColor : '#CCC',
												borderWidth : 1,
												shadow : false
											},
											tooltip : {
												formatter : function() {
													return '<b>'
															+ this.x
															+ '</b><br/>'
															+ this.series.name
															+ ': '
															+ this.y
															+ '<br/>'
															+ 'Total: '
															+ this.point.stackTotal;
												}
											},
											plotOptions : {
												area : {
													stacking : 'normal',
													dataLabels : {
														enabled : false,
														color : (Highcharts.theme && Highcharts.theme.dataLabelsColor)
																|| 'white',
														style : {
															textShadow : '0 0 3px black, 0 0 3px black'
														}
													}
												}
											},
											series : data.user
										});
					});

	$
			.getJSON(
					'<c:url value="/user/${user.id}/stats" />',
					function(data) {
						//alert(JSON.stringify(data.months));
						$('#chartAssignedTask')
								.highcharts(
										{
											chart : {
												type : 'column'
											},
											title : {
												text : 'Task assigned to user'//+data
											},
											xAxis : {
												categories : data.months
											},
											yAxis : {
												min : 0,
												title : {
													text : 'Total task sent'
												},
												stackLabels : {
													enabled : true,
													style : {
														fontWeight : 'bold',
														color : (Highcharts.theme && Highcharts.theme.textColor)
																|| 'gray'
													}
												}
											},
											legend : {
												align : 'right',
												x : -70,
												verticalAlign : 'top',
												y : 20,
												floating : true,
												backgroundColor : (Highcharts.theme && Highcharts.theme.background2)
														|| 'white',
												borderColor : '#CCC',
												borderWidth : 1,
												shadow : false
											},
											tooltip : {
												formatter : function() {
													return '<b>'
															+ this.x
															+ '</b><br/>'
															+ this.series.name
															+ ': '
															+ this.y
															+ '<br/>'
															+ 'Total: '
															+ this.point.stackTotal;
												}
											},
											plotOptions : {
												column : {
													stacking : 'normal',
													dataLabels : {
														enabled : false,
														color : (Highcharts.theme && Highcharts.theme.dataLabelsColor)
																|| 'white',
														style : {
															textShadow : '0 0 3px black, 0 0 3px black'
														}
													}
												}
											},
											series : data.user
										});
					});
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.webuser.user" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Profile</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/profileSidenav.jsp" />				
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>
						<c:out value="${user.name} ${user.surname}" />
					</h1>
		
				</div>
				<div class="row">
					<div class="span4">
						<h4>Profile Information</h4>
						<dl>
							<dt>Name</dt>
							<dd>${user.name}</dd>
							<dt>Surname</dt>
							<dd>${user.surname}</dd>
							<dt>Birthdate</dt>
							<dd>${user.birthdate}</dd>
							<dt>Official e-mail</dt>
							<dd>
								<a href='<c:url value="mailto:${user.officialEmail}" />'>${user.officialEmail}</a>
							</dd>
							<dt>Main contact phone number</dt>
							<dd>${user.contactPhoneNumber}</dd>
							<dt>Home phone number</dt>
							<dd>${user.homePhoneNumber}</dd>
							<dt>Document</dt>
							<dd>
								<c:choose>
									<c:when test="${user.documentIdType == 'NATIONAL_ID'}">Carta d'identit&agrave;</c:when>
									<c:when test="${user.documentIdType == 'CF'}">Codice fiscale</c:when>
									<c:when test="${user.documentIdType == 'DRIVING_LICENCE'}">Patente di guida</c:when>
									<c:when test="${user.documentIdType == 'PASSPORT'}">Passaporto</c:when>
								</c:choose>
								: ${user.documentId}
							</dd>
							<dt>Additional notes</dt>
							<dd>
								<c:out value="${user.notes}"></c:out>
						</dl>
					</div>
					<div class="span4">
						<h4>Project Information</h4>
						<dl>
							<dt>Registration date</dt>
							<dd>${user.registrationDateTime}</dd>
							<dt>Is Active</dt>
							<dd>
								<c:if test="${user.isActive}">Yes <i class="fa fa-ok"></i>
								</c:if>
								<c:if test="${!user.isActive}">No <i class="fa fa-remove"></i>
								</c:if>
							</dd>
							<dt>Has phone</dt>
							<dd>
								<c:if test="${user.hasPhone}">Yes <i
										class="text-info fa fa-ok"></i>
								</c:if>
								<c:if test="${!user.hasPhone}">No <i
										class="text-info fa fa-remove"></i>
								</c:if>
							</dd>
							<dt>IMEI</dt>
							<dd>${user.imei}</dd>
							<dt>Has SIM</dt>
							<dd>
								<c:if test="${user.hasSIM}">Yes <i
										class="text-info fa fa-ok"></i>
								</c:if>
								<c:if test="${!user.hasSIM}">No <i
										class="text-info fa fa-remove"></i>
								</c:if>
							</dd>
							<dt>New SIM ICCID</dt>
							<dd>${user.newIccid}</dd>
							<dt>Project phone number</dt>
							<dd>${user.projectPhoneNumber}</dd>
							<dt>Android account</dt>
							<dd>${user.projectEmail}</dd>
							<dt>Facebook account</dt>
							<dd>${user.facebookEmail}</dd>
							<dt>Wants phone</dt>
							<dd>
								<c:if test="${user.wantsPhone}">Yes <i class="fa fa-ok"></i>
								</c:if>
								<c:if test="${!user.wantsPhone}">No <i
										class="fa fa-remove"></i>
								</c:if>
							</dd>
							<dt>SIM choice</dt>
							<dd>
								<c:choose>
									<c:when test="${user.simStatus == 'NO'}">No SIM</c:when>
									<c:when test="${user.simStatus == 'NEW_SIM'}">New SIM</c:when>
									<c:when test="${user.simStatus == 'PORTABILITY'}">Portabilit&agrave;</c:when>
								</c:choose>
							</dd>
							<c:if test="${user.simStatus == 'PORTABILITY'}">
								<dt>Codice Fiscale</dt>
								<dd>${user.cf}</dd>
								<dt>Original SIM company</dt>
								<dd>${user.originalPhoneOperator}</dd>
								<dt>Original ICCID</dt>
								<dd>${user.iccid}</dd>
							</c:if>
							<dt>Registered on TIM MyCompany</dt>
							<dd>
								<c:if test="${user.isMyCompanyRegistered}">Yes <i
										class="fa fa-ok"></i>
								</c:if>
								<c:if test="${!user.isMyCompanyRegistered}">No <i
										class="fa fa-remove"></i>
								</c:if>
							</dd>							
						</dl>
					</div>

				</div>
				<hr />
				<div class="row">
					<div class="span4">
						<h4>Address Information</h4>
						<h5>
							Current address (residenza) (<a
								href='<c:url value="http://maps.google.com/maps?q=${user.currentAddress} ${user.currentCity}" />'>map</a>)
						</h5>
						<address>
							${user.currentAddress}<br />${user.currentZipCode}<br />
							${user.currentCity} (${user.currentProvince})<br />
						</address>
						<h5>
							Domicile (domicilio) (<a
								href='<c:url value="http://maps.google.com/maps?q=${user.domicileAddress} ${user.domicileCity}" />'>map</a>)
						</h5>
						<address>
							${user.domicileAddress}<br /> ${user.domicileZipCode}<br />
							${user.domicileCity} (${user.domicileProvince})<br />
						</address>
					</div>
					<div class="span4">
						<h4>University</h4>
						<dl>
							<dt>University city</dt>
							<dd>
								<c:choose>
									<c:when test="${user.uniCity == 'BOLOGNA'}">Bologna</c:when>
									<c:when test="${user.uniCity == 'CESENA'}">Cesena</c:when>
								</c:choose>
							</dd>
							<dt>School</dt>
							<dd>
								<c:choose>
									<c:when
										test="${user.uniSchool == 'AGRARIA_E_MEDICINA_VETERINARIA'}">Agraria e medicina veterinaria</c:when>
									<c:when
										test="${user.uniSchool == 'ECONOMIA_MANAGEMENT_E_STATISTICA'}">Economia, Management e Statistica</c:when>
									<c:when
										test="${user.uniSchool == 'FARMACIA_BIOTECNOLOGIE_E_SCIENZE_MOTORIE'}">Farmacia, Biotecnologie e Scienze Motorie</c:when>
									<c:when test="${user.uniSchool == 'GIURISPRUDENZA'}">Giurisprudenza</c:when>
									<c:when test="${user.uniSchool == 'INGEGNERIA_E_ARCHITETTURA'}">Ingegneria e Architettura</c:when>
									<c:when test="${user.uniSchool == 'LETTERE_E_BENI_CULTURALI'}">Lettere e Beni Culturali</c:when>
									<c:when
										test="${user.uniSchool == 'LINGUE_E_LETTERATURE_TRADUZIONE_E_INTERPRETAZIONE'}"> Lingue e Letterature, Traduzioni e Interpretazione</c:when>
									<c:when test="${user.uniSchool == 'MEDICINA_E_CHIRURGIA'}">Medicina e Chirurgia</c:when>
									<c:when
										test="${user.uniSchool == 'PSICOLOGIA_E_SCIENZE_DELLA_FORMAZIONE'}">Psicologia a Scienze della Formazione</c:when>
									<c:when test="${user.uniSchool == 'SCIENZE'}">Scienze</c:when>
									<c:when test="${user.uniSchool == 'SCIENZE_POLITICHE'}">Scienze Politiche</c:when>
								</c:choose>
							</dd>
							<dt>Department</dt>
							<dd>${user.uniDepartment}</dd>
							<dt>Corso di studi</dt>
							<dd>${user.uniDegree}</dd>
							<dt>Corso di laurea</dt>
							<dd>
								<c:choose>
									<c:when test="${user.uniCourse == 'TRIENNALE'}">Triennale</c:when>
									<c:when test="${user.uniCourse == 'MAGISTRALE'}">Magistrale/Specialistica</c:when>
								</c:choose>
							</dd>
							<dt>Fuori corso</dt>
							<dd>
								<c:if test="${user.uniIsSupplementaryYear}">S&igrave; <i
										class="fa fa-ok"></i>
								</c:if>
								<c:if test="${!user.uniIsSupplementaryYear}">No <i
										class="fa fa-remove"></i>
								</c:if>
							</dd>
							<c:if test="${!user.uniIsSupplementaryYear}">
								<dt>Anno di corso</dt>
								<dd>${user.uniYear}</dd>
							</c:if>
						</dl>
					</div>
				</div>
				<hr />
				<div class="row">
					<div class="span4">
						<h4>SIM and Phone management</h4>
						<dl>
							<dt>Received phone on</dt>
							<dd>${user.receivedPhoneOn}</dd>
						</dl>
						<dl>
							<dt>Returned phone on</dt>
							<dd>${user.returnedPhoneOn}</dd>
						</dl>
						<dl>
							<dt>Activated SIM on</dt>
							<dd>${user.activatedSIMOn}</dd>
						</dl>
						<dl>
							<dt>Received SIM on</dt>
							<dd>${user.receivedSIMOn}</dd>
						</dl>
						<dl>
							<dt>Returned SIM on</dt>
							<dd>${user.returnedSIMOn}</dd>
						</dl>
					</div>
				</div>
				<hr />
			</div>
		</div>

	</div>

	<div class="modal hide fade" id="statisticsModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">??</button>
					<h2>Statistics</h2>
				</div>
				<div class="modal-body">
					<div id="chartAssignedTask"></div>
					<div id="chartTaskUser"></div>

				</div>
				<div class="modal-footer">
				</div>
			</div>
		</div>
	</div>
</body>
</html>