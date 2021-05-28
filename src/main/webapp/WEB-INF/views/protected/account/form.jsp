<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="it.unibo.paserver.domain.Role" %>
<%@ page import="java.util.UUID" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="UserSystemCtrl" ng-cloak>
<c:choose><c:when test="${form eq null}"><span ng-init="getUserSystem();"/></c:when><c:otherwise><span ng-init="setUserSystem(${form})";/></c:otherwise></c:choose>
<div class="col-md-12">
	<div class="portlet">
	<!-- BREADCRUMB -->
	<jsp:include page="/WEB-INF/views/protected/account/breadcrumb.jsp" />
	<!-- BEGIN FORM -->
	<div class="portlet-body form">
		<form class="form-horizontal" role="form" name="formUserSystem">
				<div class="form-body">
					<!-- NAME -->
					<div class="form-group"  ng-class="formUserSystem.name.$invalid?'has-error':'has-success'">
						<label class="col-md-2 control-label bold"><spring:message code="protected.account.name" text="Name" /></label>
						<div class="col-md-7">
							<input type="text" ng-model="form.name" name="name" class="form-control"  maxlength="255" placeholder="<spring:message code="protected.account.fullname" text="Fullname" />" required="required" />
						</div>
					</div>
					
					<div class="form-group">
					<label class="col-md-2 control-label" ng-class="formUserSystem.username.$invalid?'bold font-red':''"><spring:message code="protected.account.username" text="Username" /></label>
					<div class="col-md-2">
						<input type="text" ng-model="form.username" name="username" class="form-control" placeholder="<spring:message code="choose.message" text="Choose the username" />"  maxlength="255" required="required"/>
						<small class="help-block"><spring:message code="choose.subtitle" text="You can use letters, numbers and periods." /></small>
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

					<!-- CONTACT -->	
					<div class="form-group">
						<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="label.login.contact" text="Contact" /></label>
					</div>					
					
					<div class="form-group">
					<label class="col-md-2 control-label"><spring:message code="protected.account.email" text="Email" /></label>
					<div class="col-md-3">
						<input type="email" ng-model="form.email" name="email" class="form-control" placeholder="example@dominio.com" maxlength="255"/>
						<small class="help-block text-danger" ng-show="(formUserSystem.email.$error.email)"><spring:message code="label.login.required.email" text="Required Email" /></small>
					</div>
					<label class="col-md-1 control-label"><spring:message code="protected.account.phone" text="Phone" /></label>
					<div class="col-md-3">
						<input type="text" ng-model="form.phone" ui-mask="(99) 9999-9999?9" class="input-number form-control"  maxlength="30" />
					</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-2 control-label"><spring:message code="institution.title" text="Institution" /></label>
						<div class="col-md-10">
						<div class="input-group">
							<select class="form-control" data-live-search="true" id="institutionId" data-size="20" ng-model="form.institutionId" title="<spring:message code="choose.following" text="Choose one of the following..." />">
							  <option></option>
							  <c:forEach items="${institutions}" var="item">
							  <option value="${item.id}" data-subtext="${item.address}">${item.name}</option>
							  </c:forEach>
							</select>
						</div>
						</div>
					</div>

					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<!-- PERMISSOES -->
						<div class="form-group">
							<label class="col-md-offset-2 col-md-7 uppercase bold"><spring:message code="roles.permission" text="Access and Permission" /></label>
						</div>

						<c:choose><c:when test="${controller eq 'ResearcherController'}">
							<div class="form-group">
								<label class="col-md-2 control-label"><spring:message code="researcher.type.title" text="Researcher Type" /></label>
								<div class="col-md-7">
									<select class="form-control" id="privilege" data-live-search="true" data-style="btn-default" ng-model="form.privilege" title="<spring:message code="roles.title" text="User Type" />">
										<option></option>
										<option value="<%=Role.ROLE_RESEARCHER_FIRST.ordinal()%>"><spring:message code="roles.researcher.author" text="Researcher - Author (Advanced)" /></option>
										<option value="<%=Role.ROLE_RESEARCHER_SECOND.ordinal()%>"><spring:message code="roles.researcher.subscriber" text="Researcher - Subscriber (Simple)" /></option>
										<option value="<%=Role.ROLE_COOPERATION_AGREEMENT.ordinal()%>"><spring:message code="roles.researcher.agreement" text="Researcher - Partner (Cooperation Agreement)" /></option>

										<option value="<%=Role.ROLE_RESEARCHER_OMBUDSMAN_CONSULTANT.ordinal()%>"><spring:message code="roles.researcher.consultant" text="Researcher - Consultant" /></option>
										<option value="<%=Role.ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR.ordinal()%>"><spring:message code="roles.researcher.collaborator" text="Researcher - Collaborator" /></option>
										<option value="<%=Role.ROLE_RESEARCHER_OMBUDSMAN_EDITOR.ordinal()%>"><spring:message code="roles.researcher.editor" text="Researcher - Editor" /></option>

										<option value="<%=Role.ROLE_RESEARCHER_OMBUDSMAN.ordinal()%>"><spring:message code="roles.researcher.admin" text="Researcher - Admin/Designer" /></option>
									</select>
								</div>
							</div>
						</c:when></c:choose>

						<c:choose><c:when test="${controller eq 'ResearcherController'}">
							<div class="form-group">
								<label class="col-md-2 control-label"><spring:message code="researcher.type.title.secondary" text="Researcher Type Secondary" /></label>
								<div class="col-md-7">
									<select class="form-control" id="privilege2" data-live-search="true" data-style="btn-default" ng-model="form.privilege2" title="<spring:message code="roles.title" text="User Type" />">
										<option></option>
										<option value="<%=Role.ROLE_RESEARCHER_FIRST.ordinal()%>"><spring:message code="roles.researcher.author" text="Researcher - Author (Advanced)" /></option>
										<option value="<%=Role.ROLE_RESEARCHER_SECOND.ordinal()%>"><spring:message code="roles.researcher.subscriber" text="Researcher - Subscriber (Simple)" /></option>
										<option value="<%=Role.ROLE_COOPERATION_AGREEMENT.ordinal()%>"><spring:message code="roles.researcher.agreement" text="Researcher - Partner (Cooperation Agreement)" /></option>

										<option value="<%=Role.ROLE_RESEARCHER_OMBUDSMAN_CONSULTANT.ordinal()%>"><spring:message code="roles.researcher.consultant" text="Researcher - Consultant" /></option>
										<option value="<%=Role.ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR.ordinal()%>"><spring:message code="roles.researcher.collaborator" text="Researcher - Collaborator" /></option>
										<option value="<%=Role.ROLE_RESEARCHER_OMBUDSMAN_EDITOR.ordinal()%>"><spring:message code="roles.researcher.editor" text="Researcher - Editor" /></option>

										<option value="<%=Role.ROLE_RESEARCHER_OMBUDSMAN.ordinal()%>"><spring:message code="roles.researcher.admin" text="Researcher - Admin/Designer" /></option>
									</select>
								</div>
							</div>
						</c:when></c:choose>

						<c:choose><c:when test="${controller eq 'ResearcherController'}">
							<!-- NAME -->
							<div class="form-group" >
								<label class="col-md-2 control-label bold"><spring:message code="protected.account.municipality" text="Municipality" /></label>
								<div class="col-md-7">
									<input type="text" ng-model="form.municipality" name="municipality" class="form-control"  maxlength="40" placeholder="<spring:message code="label.restriction.municipality" text="Restriction Municipality" />" required="required" />
								</div>
							</div>
						</c:when></c:choose>

						<div class="form-group">
							<ul class="list-unstyled small col-md-offset-2">
								<li><spring:message code="roles.researcher.author" text="Researcher - Author (Advanced)" />&nbsp;:&nbsp;<span class="text-muted"><spring:message code="roles.researcher.author.help" text="You can create simple campaigns and users. View only your campaigns." /></span></li>
								<li><spring:message code="roles.researcher.subscriber" text="Researcher - Subscriber (Simple)" />&nbsp;:&nbsp;<span class="text-muted"><spring:message code="roles.researcher.subscriber.help" text="Only view campaigns from a user to author / advanced."/></span></li>
								<li><spring:message code="roles.researcher.agreement" text="Researcher - Partner (Cooperation Agreement)" />&nbsp;:&nbsp;<span class="text-muted"><spring:message code="roles.researcher.agreement.help" text=""/></span></li>
								<li><spring:message code="roles.researcher.consultant" text="Researcher - Consultant" />&nbsp;:&nbsp;<span class="text-muted"><spring:message code="roles.researcher.consultant.help" text="Can view issues"/></span></li>
								<li><spring:message code="roles.researcher.collaborator" text="Researcher - Collaborator" />&nbsp;:&nbsp;<span class="text-muted"><spring:message code="roles.researcher.collaborator.help" text="Can edit issues"/></span></li>
								<li><spring:message code="roles.researcher.editor" text="Researcher - Editor" />&nbsp;:&nbsp;<span class="text-muted"></span><spring:message code="roles.researcher.editor.help" text="Can view, edit and remove reports/issues"/></li>
								<li><spring:message code="roles.researcher.admin" text="Researcher - Admin/Designer" />&nbsp;:&nbsp;<span class="text-muted"><spring:message code="roles.researcher.admin.help" text="Can create campaigns, categories and link ombudsmen. You can view and edit the reports."/></span></li>
							</ul>
						</div>
					</sec:authorize>
						
					<!-- BUTTON -->
					<div class="form-group margin-top-40 margin-bottom-10 col-md-9">
						<button type="button" class="btn green col-xs-6 col-sm-4 col-md-offset-2 col-md-2 col-lg-2 margin-right-10" ng-click="saveUserSystem();" ng-disabled="formUserSystem.$invalid"><spring:message code="save.title" text="Save" /></button>
						<c:choose><c:when test="${controller eq 'AccountController'}">
						<a href="<c:url value="/protected/account/index"/>" class="btn default col-xs-6 col-sm-4 col-md-2 col-lg-2 pull-right" id="fixgoback"><spring:message code="goback.title" text="Go Back" /></a>
						</c:when><c:otherwise>
						<a href="<c:url value="/protected/researcher/index"/>" class="btn default col-xs-6 col-sm-4 col-md-2 col-lg-2 pull-right" id="fixgoback"><spring:message code="goback.title" text="Go Back" /></a>
						</c:otherwise></c:choose>
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