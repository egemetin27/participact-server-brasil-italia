<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="br.com.bergmannsoft.config.Config" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%  
	String lang = (new RequestContext(request)).getLocale().getLanguage(); 
	lang = lang=="en_us"?"en":lang; 
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<input type="hidden" id="HeyMan" />
	</div><!-- END CONTENT BODY -->
    </div><!-- END CONTENT -->
	</div><!-- END CONTAINER -->
     <!-- BEGIN FOOTER -->
     <div class="page-footer">
     	 <!-- COPYRIGHT -->
         <div class="page-footer-inner"><jsp:include page="/WEB-INF/templates/partial/copyright.jsp"></jsp:include></div>
         <!-- UPPER PAGE -->
         <div class="scroll-to-top"><i class="icon-arrow-up"></i></div>
     </div>
     <!-- END FOOTER -->     
	<!-- BEGIN SCRIPTS -->
    <script src="<c:url value="/resources/2.0/assets/bower_components/jquery/dist/jquery.min.js?v=1510948903"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/jquery-ui/jquery-ui.min.js?v=1510948903"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/bootstrap/dist/js/bootstrap.min.js?v=1510948903"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/js-cookie/src/js.cookie.js?v=1510948903"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js?v=1510948903"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/jquery-slimscroll/jquery.slimscroll.min.js?v=1510948903"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/blockUI/jquery.blockUI.js?v=1510948903"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/bootstrap-switch/dist/js/bootstrap-switch.min.js?v=1510948903"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/moment/min/moment.min.js?v=1510948903"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/bootstrap-duration-picker/src/jquery-duration-picker.js?v=1510948903"/>"  type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.js?v=1510948903"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js?v=1510948903"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js?v=1510948903"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js?v=1510948903"/>" type="text/javascript"></script>
	<!-- END SCRIPTS-->
	<!-- BEGIN EXTERNO -->
	<script src="https://maps.googleapis.com/maps/api/js?libraries=visualization,drawing,geometry,places&key=<%=Config.GOOGLE_API_KEY_MAP%>"></script>
	<c:choose>
	<c:when test="${lang=='en_us'}">
		<script src="<c:url value="/resources/2.0/js/language/en_US.js?v=1494961469"/>" type="text/javascript"></script>
	</c:when>
	<c:otherwise>
		<script src="<c:url value="/resources/2.0/js/language/pt_BR.js?v=1494961469"/>" type="text/javascript"></script>
	</c:otherwise>
	</c:choose>	
	<!-- END EXTERNO -->
    <!-- BEGIN THEME LAYOUT SCRIPTS -->
    <script src="<c:url value="/resources/2.0/assets/global/scripts/app.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/layouts/layout/scripts/layout.min.js"/>" type="text/javascript"></script>
    <!-- END THEME LAYOUT SCRIPTS -->	
	<!-- BEGIN ANGULAR -->
	<script src="<c:url value="/resources/2.0/assets/bower_components/angular/angular.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/angular-loading-bar/build/loading-bar.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/angular-animate/angular-animate.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/ngstorage/ngStorage.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/angular-sanitize/angular-sanitize.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/ng-file-upload/ng-file-upload.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/angular-toastr/dist/angular-toastr.tpls.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/angular-ipsum/dist/ipsum.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/bootbox.js/bootbox.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/angular-ui-mask/dist/mask.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/angular-google-places-autocomplete/dist/autocomplete.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/bootstrap-select/dist/js/bootstrap-select.min.js"/>" type="text/javascript"></script>    
    <script src="<c:url value="/resources/2.0/assets/bower_components/ajax-bootstrap-select/dist/js/ajax-bootstrap-select.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/angular-minicolors/angular-minicolors.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/summernote/dist/summernote.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/summernote/dist/lang/summernote-pt-BR.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/2.0/assets/bower_components/angular-summernote/dist/angular-summernote.min.js"/>" type="text/javascript"></script>
    <!-- <script src="<c:url value="/resources/2.0/assets/bower_components/air-datepicker/dist/js/datepicker.min.js"/>" type="text/javascript"></script> -->
	<!-- <script src="<c:url value="/resources/2.0/assets/bower_components/air-datepicker/dist/js/i18n/datepicker.en.js"/>" type="text/javascript"></script>-->
	<!-- <script src="<c:url value="/resources/2.0/assets/bower_components/air-datepicker/dist/js/i18n/datepicker.pt.js"/>" type="text/javascript"></script>-->
	<!--<script src="<c:url value="/resources/2.0/assets/bower_components/air-datepicker/dist/js/i18n/datepicker.pt_br.js"/>" type="text/javascript"></script>-->
	<script src="<c:url value="/resources/2.0/assets/bower_components/bootstrap-tagsinput/dist/bootstrap-tagsinput-angular.min.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/2.0/assets/bower_components/angular-bootstrap-toggle-switch/angular-toggle-switch.min.js?v=1510948903"/>"  type="text/javascript"></script>
	<script src="<c:url value="/resources/2.0/assets/bower_components/angular-ui-sortable/sortable.min.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/2.0/assets/bower_components/ngmap/build/scripts/ng-map.min.js"/>" type="text/javascript"></script>
	<script src="https://rawgit.com/allenhwkim/angularjs-google-maps/master/testapp/scripts/markerclusterer.js" type="text/javascript"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/ngMask/3.1.1/ngMask.min.js" integrity="sha256-2KJN2PANqwgh/8y1nTVBLY0Hi3Dnxp++fPH493eNs54=" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-input-masks/4.2.1/angular-input-masks-standalone.min.js" integrity="sha256-IHB7xs9DBgyfmu+Dzdwypswd/E0kNL21DGOBMZZrvEg=" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/ekko-lightbox/5.3.0/ekko-lightbox.min.js" integrity="sha256-Y1rRlwTzT5K5hhCBfAFWABD4cU13QGuRN6P5apfWzVs=" crossorigin="anonymous"></script>
	<script type="text/javascript" src="https://unpkg.com/nanogallery2/dist/jquery.nanogallery2.min.js"></script>

	<script>
	    MarkerClusterer.prototype.MARKER_CLUSTER_IMAGE_PATH_ = 'https://raw.githubusercontent.com/googlemaps/js-marker-clusterer/gh-pages/images/m';  //changed image path
	</script>
	<!-- CHARTS -->
	<script src="https://www.amcharts.com/lib/4/core.js"></script>
	<script src="https://www.amcharts.com/lib/4/charts.js"></script>
	<script src="https://www.amcharts.com/lib/4/themes/material.js"></script>
	<script src="https://www.amcharts.com/lib/4/themes/animated.js"></script>
	<script src="<c:url value="/resources/2.0/assets/bower_components/highcharts/highcharts.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/2.0/assets/bower_components/highcharts-ng/dist/highcharts-ng.min.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/2.0/assets/bower_components/chart.js/dist/Chart.min.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/2.0/assets/bower_components/angular-chart.js/dist/angular-chart.min.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/2.0/assets/bower_components/angular-ui-select/dist/select.min.js"/>" type="text/javascript"></script>
    <!-- APP -->	
    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js"></script>
    <script type="text/javascript" src="<c:url value="/resources/2.0/js/functions.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/ParticipApp.js"/>"></script>
	<script type="text/javascript">
		ParticipActApp.constant('BASE_URL','<%=Config.PRODUCTION_HOST%>');
		var BASE_GEO_LAT=<%=Config.PRODUCTION_GEO_LAT%>;
		var BASE_GEO_LNG=<%=Config.PRODUCTION_GEO_LNG%>;
	</script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/directives/directive.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/ParticipActSrvc.js?v=1510948903"/>"></script>
	<sec:authorize access="isAuthenticated()"><sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND')">
	<!-- SERVICES -->
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/UserSystemSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/InstitutionsSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/DevicesSrvc.js?v=1510948903"/>"></script>	
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/SystemPageSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/SystemEmailSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/SystemBackupSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/MailingHistorySrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/SchoolCourseSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/ParticipantSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/ParticipantListSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/NotificationBarSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/CampaignSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/CampaignTaskSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/CampaignFixedSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/CampaignTaskGeoDrawingSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/CampaignTaskGeoRoutesSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/CampaignTaskDetectionSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/CampaignTaskPhotoSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/CampaignTaskQuestionnaireSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/CampaignTaskSensingSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/CampaignTaskAssignSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/CampaignTaskStatsSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/PushNotificationsSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/PushNotificationsLogsSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/AbuseTypeSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/FeedbackTypeSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/FeedbackReportSrvc.js?v=1510948903"/>"></script>	
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/IssueReportSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/IssueCategorySrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/AbuseReportSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/StorageFileSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/CkanComcapSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/CkanCelescSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/CguEouvSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/CguCategorySrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/CguIbgeMunSrvc.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/services/MailNotificationsSrvc.js?v=1510948903"/>"></script>		
	<!-- CONTROLLERS -->
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/LocaleCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/PlacesCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/DrawingManagerCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/FileUploadCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/SummernoteCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/NotificationBarCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/DashboardCtrl.js?v=1510948903"/>"></script>	
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/DevicesCtrl.js?v=1510948903"/>"></script>		
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/DatePickerCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/UserSystemCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/ParticipantCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/ParticipantFilterCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/ParticipantListCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/InstitutionsCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/SystemPageCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/SystemEmailCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/SystemBackupCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/MailingHistoryCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/SchoolCourseCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CampaignCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CampaignTaskCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CampaignFixedCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CampaignTaskGeoDrawingCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CampaignTaskGeoRoutesCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CampaignTaskDetectionCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CampaignTaskPhotoCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CampaignTaskQuestionnaireCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CampaignTaskSensingCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CampaignTaskAssignCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CampaignTaskStatsCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CampaignTaskResendCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/PushNotificationsCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/PushNotificationsLogsCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/AbuseTypeCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/IssueCategoryCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/IssueReportCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/AbuseReportCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/FeedbackTypeCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/FeedbackReportCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CkanComcapCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CkanCelescCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CguEouvCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CguCategoryCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/CguIbgeMunCtrl.js?v=1510948903"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/MailNotificationsCtrl.js?v=1510948903"/>"></script>
		<script type="text/javascript" src="<c:url value="/resources/2.0/js/app/controllers/SidebarCtrl.js?v=1510948903"/>"></script>
	</sec:authorize></sec:authorize>
	<!-- END ANGULAR -->
	<script type="text/javascript">
	$(document).on('click', '[data-toggle="lightbox"]', function(event) {
		event.preventDefault();
		$(this).ekkoLightbox();
	});
	$(function () {
		$('[data-toggle="tooltip"]').tooltip()
	});
	</script>
</body>
</html>