<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!-- BEGIN SIDEBAR -->
 <div class="page-sidebar-wrapper" ng-cloak>
     <!-- BEGIN SIDEBAR -->
     <div class="page-sidebar navbar-collapse collapse">
         <!-- BEGIN SIDEBAR MENU -->
         <ul id="pa-page-sidebar-menu" class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200" style="padding-top: 20px">
             <li class="sidebar-toggler-wrapper hide">
                 <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                 <div class="sidebar-toggler"><span></span></div>
                 <!-- END SIDEBAR TOGGLER BUTTON -->
             </li>
             <sec:authorize access="isAuthenticated()">
				<!-- 1 -->
				 <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND','ROLE_RESEARCHER_OMBUDSMAN','ROLE_RESEARCHER_OMBUDSMAN_CONSULTANT','ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR','ROLE_RESEARCHER_OMBUDSMAN_EDITOR')">
				 <li class="nav-item start <c:if test="${sectionKey eq 'protected.dashboard'}"> active open </c:if>" id="pa-menu-dashboard">
					<a href="<c:url value="/protected/dashboard"/>" class="nav-link nav-toggle"><i class="icon-globe"></i><span class="title"><spring:message code="protected.dashboard.title" text="Dashboard" /></span><span class="arrow"></span></a>
					<ul class="sub-menu">
						<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')">
						<li class="nav-item <c:if test="${breadcrumb eq '/protected/dashboard/index'}"> active open selected </c:if>" id="pa-submenu-statistics"><a href="<c:url value="/protected/dashboard/index"/>"> <span class="title"><spring:message code="campaign.title" text="Campaign" /></span></a></li>
						</sec:authorize>
					 	<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
						<li class="nav-item <c:if test="${breadcrumb eq '/protected/dashboard/category'}"> active open selected </c:if>" id="pa-submenu-statistics-category"><a href="<c:url value="/protected/dashboard/category"/>"> <span class="title"><spring:message code="category.title" text="Category" /></span></a></li>
						</sec:authorize>
					</ul>
				</li>
			 	</sec:authorize>
				<!-- 2 -->
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="nav-item " id="pa-menu-messaging">
						<a href="javascript:;" class="nav-link nav-toggle"><i class="fa fa-envelope-open-o"></i><span class="title"><spring:message code="messaging.title" text="Messaging" /></span><span class="arrow"></span></a>
						<ul class="sub-menu">
							<li class="nav-item <c:if test="${controller eq 'PushNotificationsController'}"> active open selected </c:if>" id="pa-submenu-push-notifications"><a href="<c:url value="/protected/push-notifications/index"/>" class="nav-link "> <span class="title"><spring:message code="push.notifications.title" text="Push Notifications" /></span></a></li>
							<li class="nav-item <c:if test="${controller eq 'MailNotificationsController'}"> active open selected </c:if>" id="pa-submenu-mail-notifications"><a href="<c:url value="/protected/mail-notifications/index"/>" class="nav-link "> <span class="title"><spring:message code="email.notifications.title" text="E-mails" /></span></a></li>
						</ul>
					</li>

					 <li class="nav-item " id="pa-menu-users">
						<a href="javascript:;" class="nav-link nav-toggle"><i class="icon-users"></i><span class="title"><spring:message code="protected.account.users" text="Users" /></span><span class="arrow"></span></a>
						<ul class="sub-menu">
							<sec:authorize access="hasRole('ROLE_ADMIN')">
							<li class="nav-item <c:if test="${controller eq 'AccountController'}"> active open selected </c:if>" id="pa-submenu-account"><a href="<c:url value="/protected/account/index"/>" class="nav-link "> <span class="title"><spring:message code="protected.adminstrators.title" text="Administrators" /></span><span class="selected"></span></a></li>
							</sec:authorize>
							<li class="nav-item <c:if test="${controller eq 'ResearcherController'}"> active open selected </c:if>" id="pa-submenu-researcher"><a href="<c:url value="/protected/researcher/index"/>" class="nav-link "> <span class="title"><spring:message code="protected.researchers.title" text="Researchers" /></span></a></li>
							<li class="nav-item <c:if test="${controller eq 'ParticipantController'}"> active open selected </c:if>" id="pa-submenu-participant"><a href="<c:url value="/protected/participant/index"/>" class="nav-link nav-toggle"><span class="title"><spring:message code="participants.title" text="Participants" /></span></a></li>
						</ul>
					 </li>
             	</sec:authorize>
				<!-- 3 -->
				<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')">
             		<li class="nav-item <c:if test="${sectionKey eq 'CampaignController'}"> active open </c:if>" id="pa-menu-campaigns"><a href="<c:url value="/protected/campaign/index"/>" class="nav-link nav-toggle"><i class="icon-layers"></i><span class="title"><spring:message code="protected.campaigns.title" text="Campaigns" /></span></a></li>
				</sec:authorize>
             	<!-- 4 -->
             	<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_CONSULTANT', 'ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')">
				 <li class="nav-item <c:if test="${sectionKey eq 'IssueReportController'}"> active open </c:if>" id="pa-menu-issue-report"><a href="<c:url value="/protected/issue-report/index"/>" class="nav-link nav-toggle">&nbsp;<i class="icon-flag"></i>&nbsp;<span class="title"><spring:message code="issues.title" text="Issues" /></span></a></li>
				 <sec:authorize access="hasRole('ROLE_ADMIN')">
					 <li class="nav-item <c:if test="${sectionKey eq 'FeedbackReportController'}"> active open </c:if>" id="pa-menu-feedback-report"><a href="<c:url value="/protected/feedback-report/index"/>" class="nav-link nav-toggle">&nbsp;<i class="icon-bubble"></i>&nbsp;<span class="title"><spring:message code="feedback.title" text="Feedbacks" /></span></a></li>
					 <li class="nav-item" id="pa-menu-ckan"><a href="javascript:;" class="nav-link nav-toggle"><i class="fa fa-database" aria-hidden="true"></i><span class="title"><spring:message code="ckan.title" text="Ckan" /></span><span class="arrow"></span></a>
					<ul class="sub-menu">
						<li class="nav-item <c:if test="${controller eq 'CkanComcapController'}"> active open selected </c:if>" id="pa-submenu-ckan-comcap"><a href="<c:url value="/protected/ckan-comcap/index"/>" class="nav-link "> <span class="title"><spring:message code="ckan.comcap.title" text="Comcap" /></span></a></li>
						<li class="nav-item <c:if test="${controller eq 'CkanCelescController'}"> active open selected </c:if>" id="pa-submenu-ckan-celesc"><a href="<c:url value="/protected/ckan-celesc/index"/>" class="nav-link "> <span class="title"><spring:message code="ckan.celesc.title" text="Celesc" /></span></a></li>
					</ul>
				 </li>
				</sec:authorize>

				 <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')">
				 <li class="nav-item" id="pa-menu-cgu"><a href="javascript:;" class="nav-link nav-toggle"><i class="fa fa-server" aria-hidden="true"></i><span class="title"><spring:message code="cgu.title" text="CGU" /></span><span class="arrow"></span></a>
				 	<ul class="sub-menu">
				 		<li class="nav-item <c:if test="${breadcrumb eq '/protected/cgu-ouvidoria/index'}"> active open selected </c:if>" id="pa-submenu-ouvidorias"><a href="<c:url value="/protected/cgu-ouvidoria/index"/>" class="nav-link "> <span class="title"><spring:message code="cgu.ombudsman.title" text="Ombudsman" /></span></a></li>
				 		<sec:authorize access="hasRole('ROLE_ADMIN')">
				 		<li class="nav-item <c:if test="${breadcrumb eq '/protected/cgu-eouv/orgaos-siorg-ouvidoria'}"> active open selected </c:if>" id="pa-submenu-orgaos-siorg-ouvidoria"><a href="<c:url value="/protected/cgu-eouv/orgaos-siorg-ouvidoria"/>" class="nav-link "> <span class="title"><spring:message code="cgu.organs.sirog.title" text="Organs and Structures" /></span></a></li>
						<li class="nav-item <c:if test="${breadcrumb eq '/protected/cgu-ibge-mun/index'}"> active open selected </c:if>" id="pa-submenu-cgu-ibge-mun"><a href="<c:url value="/protected/cgu-ibge-mun/index"/>" class="nav-link "> <span class="title"><spring:message code="cgu.ibge.mun" text="Cities" /></span></a></li>
						</sec:authorize>
				 		<li class="nav-item <c:if test="${breadcrumb eq '/protected/issue-category/index'}"> active open selected </c:if>" id="pa-submenu-cgu-category"><a href="<c:url value="/protected/issue-category/index"/>" class="nav-link "> <span class="title"><spring:message code="cgu.category.title" text="Complaint Category" /></span></a></li>
				 		<sec:authorize access="hasRole('ROLE_ADMIN')">
				 		<li class="nav-item <c:if test="${breadcrumb eq '/protected/cgu-crontab/index'}"> active open selected </c:if>" id="pa-submenu-cgu-crontab"><a href="<c:url value="/protected/cgu-crontab/index"/>" class="nav-link "> <span class="title"><spring:message code="crontab.title" text="Crontab" /></span></a></li>
						</sec:authorize>
				 	</ul>
				 </li>
				 </sec:authorize>

				 <sec:authorize access="hasRole('ROLE_ADMIN')">
				 <li class="nav-item" id="pa-menu-settings"><a href="javascript:;" class="nav-link nav-toggle"><i class="icon-settings"></i><span class="title"><spring:message code="settings.title" text="Settings" /></span><span class="arrow"></span></a>
					<ul class="sub-menu">
						<li class="nav-item <c:if test="${controller eq 'SystemPageController'}"> active open selected </c:if>" id="pa-submenu-pages"><a href="<c:url value="/protected/system-page/index"/>" class="nav-link "> <span class="title"><spring:message code="pages.title" text="Pages" /></span></a></li>
						<li class="nav-item <c:if test="${controller eq 'InstitutionsController'}"> active open selected </c:if>" id="pa-submenu-institutions"><a href="<c:url value="/protected/institutions/index"/>" class="nav-link "> <span class="title"><spring:message code="institutions.title" text="Institutions" /></span></a></li>
						<li class="nav-item <c:if test="${controller eq 'SchoolCourseController'}"> active open selected </c:if>" id="pa-submenu-school-course"><a href="<c:url value="/protected/school-course/index"/>" class="nav-link "> <span class="title"><spring:message code="education.courses.title" text="School Courses" /></span></a></li>
						<li class="nav-item <c:if test="${controller eq 'DevicesController'}"> active open selected </c:if>" id="pa-submenu-devices"><a href="<c:url value="/protected/devices/index"/>" class="nav-link "> <span class="title"><spring:message code="devices.title" text="Devices" /></span><span class="selected"></span></a></li>
						<li class="nav-item <c:if test="${controller eq 'SystemEmailController'}"> active open selected </c:if>" id="pa-submenu-emails"><a href="<c:url value="/protected/system-email/index"/>" class="nav-link "> <span class="title"><spring:message code="emails.title" text="Emails" /></span><span class="selected"></span></a></li>

						<!--<li class="nav-item <c:if test="${controller eq 'SystemBackupController'}"> active open selected </c:if>" id="pa-submenu-backups"><a href="<c:url value="/protected/system-backup/index"/>" class="nav-link "> <span class="title"><spring:message code="backup.title" text="Backup" /></span><span class="selected"></span></a></li>-->

						<li class="nav-item <c:if test="${controller eq 'AbuseTypeController'}"> active open selected </c:if>" id="pa-submenu-abuse-type"><a href="<c:url value="/protected/abuse-type/index"/>" class="nav-link "> <span class="title"><spring:message code="abuse.type.title" text="Abuse (Types)" /></span></a></li>
						<li class="nav-item <c:if test="${controller eq 'FeedbackTypeController'}"> active open selected </c:if>" id="pa-submenu-feedback-type"><a href="<c:url value="/protected/feedback-type/index"/>" class="nav-link "> <span class="title"><spring:message code="feedback.type.title" text="Feedback (Types)" /></span></a></li>
						<!--
						<li class="nav-item <c:if test="${controller eq 'IssueCategoryController'}"> active open selected </c:if>" id="pa-submenu-issue-category"><a href="<c:url value="/protected/issue-category/index"/>" class="nav-link "> <span class="title"><spring:message code="issue.category.title" text="Issue (Categories)" /></span></a></li>
						-->
					</ul>
				 </li>
				 </sec:authorize>
             	</sec:authorize>
             </sec:authorize>
             <li class="nav-item end <c:if test="${sectionKey eq 'protected.account'}"> active open </c:if>"><a href="<c:url value="/logout"/>" class="nav-link nav-toggle"><i class="icon-logout"></i><span class="title"><spring:message code="logout.title" text="Logout" /></span></a></li>
		  </ul>
         <!-- END SIDEBAR MENU -->
     </div>
     <!-- END SIDEBAR -->
 </div>
 <!-- END SIDEBAR -->