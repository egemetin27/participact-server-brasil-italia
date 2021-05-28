<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%  String lang = (new RequestContext(request)).getLocale().getLanguage();
	lang = lang=="en_us"?"en":lang; %>
<%@ page import="it.unibo.paserver.domain.Gender" %>
<%@ page import="java.util.UUID" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="ParticipantCtrl" ng-cloak>
<c:choose><c:when test="${form eq null}"><span ng-init="getParticipant();"/></c:when><c:otherwise><span ng-init="setParticipant(${form})";/></c:otherwise></c:choose>
<div class="col-md-12">
	<div class="portlet">
	<!-- BREADCRUMB -->
	<jsp:include page="/WEB-INF/views/protected/participant/breadcrumb.jsp" />
	<!-- BEGIN FORM -->
	<div class="portlet-body form">
		<form class="form-horizontal" role="form" name="formParticipant">
				<input type="text" class="hidden" id="HeyLocale" />
				<div class="form-body">
					<!-- ACCESS -->	
					<div class="form-group">
						<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="credentials.title" text="Credentials" /></label>
					</div>					
					<!-- NAME -->
					<div class="form-group"  ng-class="formParticipant.name.$invalid||formParticipant.surname.$invalid?'has-error':'has-success'">
						<label class="col-md-2 control-label bold"><spring:message code="name.title" text="Name" /></label>
						<div class="col-md-6"><input type="text" ng-model="form.name" name="name" class="form-control"  maxlength="255" placeholder="<spring:message code="name.title" text="Name" />" required="required" /></div>
						<div class="col-md-2">
							<div class="mt-checkbox-list">
								<label class="mt-checkbox mt-checkbox-outline small" ng-class="form.hasAllowOmbudsman?'text-danger bold':''"><spring:message code="has.allow.ombudsman" text="User can enter the ombudsman" /><input type="checkbox" ng-model="form.hasAllowOmbudsman" /><span></span></label>
							</div>
						</div>
					</div>
					
					<div class="form-group">
					<label class="col-md-2 control-label" ng-class="formParticipant.officialEmail.$invalid?'bold font-red':''"><spring:message code="email.title" text="Email" /></label>
					<div class="col-md-2">
						<input type="email" ng-model="form.officialEmail" name="officialEmail" class="form-control" placeholder="example@domain.com"  maxlength="255" required="required"/>
						<small class="help-block hidden-sm"><spring:message code="choose.subtitle" text="You can use letters, numbers and periods." /></small>
						<small class="help-block hidden-sm"><spring:message code="choose.email" text="It should be used a valid email address." /></small>
					</div>
					
					<label class="col-md-1 control-label" ng-class="!form.npassword.length||!form.rpassword.length?'bold font-red':''"><spring:message code="label.login.password" text="Password" /></label>
					<div class="col-md-2">
						<input type="password" ng-model="form.npassword" class="form-control" placeholder="<spring:message code="label.login.password.create" text="Create a password" />"  maxlength="100" minlength="8" />
						<small class="help-block text-muted"><spring:message code="label.login.password.min" text="Minimum 8 characters." /></small>
					</div>

					<div class="col-md-2">
						<input type="password" ng-model="form.rpassword" class="form-control" placeholder="<spring:message code="label.login.repassword" text="Confirm your password" />" maxlength="100" />
						<small class="help-block text-danger" ng-show="form.npassword!=form.rpassword"><spring:message code="label.login.repassword.match" text="These passwords don't match. Try again?" /></small>
					</div>
					</div>				
					
					<div class="form-group">
						<label class="col-md-2 control-label"><spring:message code="primary.title" text="Primary" /></label>
						<div class="col-md-2">
							<input type="email" ng-model="form.emailPrimary" name="emailPrimary" class="form-control" placeholder="example@domain.com" readonly="readonly"/>
						</div>
						
						<label class="col-md-1 control-label"><spring:message code="secondary.title" text="Secondary" /></label>
						<div class="col-md-2">
							<input type="email" ng-model="form.emailSecondary" name="emailSecondary" class="form-control" placeholder="example@domain.com"/>
						</div>						
					</div>
					<!-- CONTACT -->	
					<div class="form-group">
						<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="label.login.contact" text="Contact" /></label>
					</div>					
					<div class="form-group">
					<label class="col-md-2 control-label small"><spring:message code="birthday.title" text="Birthday" /></label>
					<div class="col-md-2" ng-controller="DatePickerCtrl">
						<div class="input-group input-medium date datepicker-here" data-date-format="dd/mm/yyyy" data-date-viewmode="years">
							<input class="form-control" type="text" readonly="" ng-model="form.dt_format" ng-change="changeBirthdate();"> <span class="input-group-btn"><button class="btn default" type="button"><i class="fa fa-calendar"></i></button></span>
						</div>
						<small class="help-block"><spring:message code="choose.date" text="Select date" /></small>
						<input type="text" class="hide" id="birthdate" ng-model="form.birthdate" value="">	
					</div>
					<!-- SEXO -->
					<div class="col-md-offset-1 col-md-2 col-sm-6"><jsp:include page="/WEB-INF/templates/partial/selectGender.jsp" /></div>
					<!-- APARELHOS -->
					<div class="col-md-2 col-sm-6">
						<input type="text" class="form-control" id="device" ng-model="form.device" placeholder="<spring:message code="device.title" text="Device" />" maxlength="100"/>
					</div>
					</div>
					<!-- PHONE -->
					<div class="form-group">
						<label class="col-md-2 control-label small"><spring:message code="phone.title" text="Phone" /></label>
						<div class="col-md-2"><input type="text" ng-model="form.homePhoneNumber" ui-mask="(99) 9999-9999?9" class="input-number form-control"  maxlength="30" /></div>
						<label class="col-md-1 control-label small"><spring:message code="cellphone.title" text="Cellphone" /></label>
						<div class="col-md-2"><input type="text" ng-model="form.contactPhoneNumber" ui-mask="(99) 9999-9999?9" class="input-number form-control"  maxlength="30" /></div>
						<div class="col-md-2">
							<input type="text" ng-model="form.ageRange" class="form-control"  size="30" />
							<small class="help-block"><spring:message code="age.range.title" text="Age Range" /></small>
						</div>
					</div>
					
					<!-- DOCUMENT -->
					<div class="form-group">
						<label class="col-md-2 control-label small"><spring:message code="identity.title" text="Identity" /></label>
						<div class="col-md-2">
						<jsp:include page="/WEB-INF/templates/partial/selectDocumentIdType.jsp" />
						</div>
						<label class="col-md-1 control-label small"><spring:message code="document.number.title" text="Document Number" /></label>
						<div class="col-md-2">
							<input type="text" class="form-control" maxlength="30" ng-model="form.documentId" />
						</div>
						<div class="col-md-2">
                              <div class="mt-checkbox-list">
                                  <label class="mt-checkbox mt-checkbox-outline small"><input type="checkbox" ng-model="form.authorizeContact" /><spring:message code="authorize.contact.title" text="Authorize Contact" /><span></span></label>
                              </div>
                          </div>						
					</div>
					<!-- SCHOOL -->
					<div class="form-group">
						<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="education.title" text="Education" /></label>
					</div>					
					<div class="form-group">
						<label class="col-md-2 control-label small"><spring:message code="institution.title" text="Institution" /></label>
						<div class="col-md-7"><jsp:include page="/WEB-INF/templates/partial/selectInstitution.jsp" /></div>
					</div>	
						
					<div class="form-group">
						<label class="col-md-2 control-label small"><spring:message code="education.level.title" text="Education Level" /></label>
						<div class="col-md-7">
							<jsp:include page="/WEB-INF/templates/partial/selectUniCourse.jsp" />						
						</div>
					</div>
					
					<div class="form-group">
						<div ng-show="form.uniCourse=='GRADUATION'||form.uniCourse=='MASTER_DEGREE'">
							<label class="col-md-2 control-label small"><spring:message code="education.course.title" text="Course" /></label>
							<div class="col-md-7">
								<jsp:include page="/WEB-INF/templates/partial/selectSchoolCourse.jsp" />	
							</div>
						</div>	
					</div>	
					
					<div class="form-group">
						<div class="col-md-offset-2 col-md-1">
                              <div class="mt-checkbox-list">
                                  <label class="mt-checkbox mt-checkbox-outline small"><input type="checkbox" ng-model="form.uniIsSupplementaryYear" /><spring:message code="education.off.title" text="Off Course" /><span></span></label>
                              </div>
                          </div>						
						<div class="col-md-1">
							<input type="number" string-to-number class="form-control" min="0" maxlength="4" ng-model="form.uniYear" ng-disabled="form.uniIsSupplementaryYear" placeholder="2016" />
							<small class="help-block"><spring:message code="education.year.title" text="Year of the course"/></small>
						</div>
						<div class="col-md-1">
							<input type="text" class="form-control" maxlength="10" ng-model="form.uniDepartment" placeholder="ESAG"/>
							<small class="help-block"><spring:message code="uni.department.title" text="Department" /></small>
						</div>
						<div class="col-md-1">
							<input type="text" class="form-control" maxlength="10" ng-model="form.uniCodCourse" placeholder="ECN"/>
							<small class="help-block"><spring:message code="uni.codcourse.title" text="Cod Course" /></small>
						</div>
						<div class="col-md-1">
							<input type="number" string-to-number class="form-control" min="0" maxlength="10" ng-model="form.uniPhase" placeholder="0"/>
							<small class="help-block"><spring:message code="uni.phase.title" text="Phase" /></small>
						</div>
						<div class="col-md-2">
							<input type="text" class="form-control" maxlength="10" ng-model="form.uniStatus" placeholder="ATIVO"/>
							<small class="help-block"><spring:message code="uni.status.title" text="Status" /></small>
						</div>
					</div>				
					
					<div class="form-group">
						<label class="col-md-2 control-label"><spring:message code="source.title" text="Source" /></label>
						<div class="col-md-3">
							<input type="text" ng-model="form.fileSource" class="form-control"  maxlength="255"/>
						</div>
					</div>						
					<!-- ADDRESS -->
					<div class="form-group">
						<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="address.title" text="Address" /></label>
					</div>
					<div class="form-group">							
						<jsp:include page="/WEB-INF/templates/partial/places.jsp" />
					</div>					
					<!-- NOTES -->
					<div class="form-group">
						<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="notes.title" text="Additional Notes" /></label>
					</div>						
					<div class="form-group">
						<div class="col-md-offset-2 col-md-7">
							<textarea class="form-control" ng-model="form.notes" rows="6"></textarea>
							<small class="help-block"><spring:message code="notes.help.message" text="Additional notes on this user, free text." /></small>
						</div>
					</div>
					<!-- BUTTON -->
					<div class="form-group margin-top-40 margin-bottom-10 col-md-9">
						<button type="button" class="btn green col-xs-6 col-sm-4 col-md-offset-2 col-md-2 col-lg-2 margin-right-10" ng-click="saveParticipant();"><spring:message code="save.title" text="Save" /></button>
						<a href="<c:url value="/protected/participant/index"/>" class="btn default col-xs-6 col-sm-4 col-md-2 col-lg-2 pull-right" id="fixgoback"><spring:message code="goback.title" text="Go Back" /></a>
					</div>					
				</div>
			</form>
	</div>
	<!-- END FORM -->	
	</div>	
</div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />