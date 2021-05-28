<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div tabindex="-1" class="modal fade in" id="modalImportFileForm" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" aria-hidden="true" type="button" data-dismiss="modal"></button>
				<h4 class="modal-title">
					<span class="caption-subject font-green sbold uppercase"><i class="fa fa-cloud-upload"></i>&nbsp;&nbsp;<spring:message code="import.title" text="Import" />&nbsp;</span>				
				</h4>
			</div>
			<div class="modal-body" ng-controller="FileUploadCtrl">
			<!-- BEGIN FORM -->
			<form role="form" name="fileUploadForm">
			<div class="form-body">
				 <div class="form-group" ng-class="fileUploadForm.fileSource.$invalid?'font-red':'font-green'">
				    <label><spring:message code="where.import.message" text="Where are you importing the file from?" /></label>
				    <input type="text" class="form-control" name="fileSource" ng-model="fileSource" id="fileSource" placeholder="<spring:message code="where.import.help" text="Database, contacts, List of students ..." />" required="required" maxlength="100"/>
				 </div>		
				<div class="form-group" ng-class="fileUploadForm.file.$invalid?'font-red':'font-green'">
    				<label><spring:message code="select.import.message" text="Select a CSV or TXT file" /></label>
    				<input class="form-control" type="file" ngf-model-invalid="errorFile" ngf-select ng-model="file" name="file" ngf-accept="'.csv'" ngf-max-size="5MB" title="<spring:message code="import.title" text="Import" />" required="required"/>
  				</div>
  				
  				<div class="form-group">
  					<button type="button" ng-click="upload(file,'${controller}')" ng-disabled="fileUploadForm.$invalid" ng-class="fileUploadForm.$invalid?'':'animated bounce'" class="btn green-seagreen"><i class="fa fa-cloud-upload"></i>&nbsp;&nbsp;<spring:message code="import.title" text="Import" />&nbsp;</button>
  					<p><jsp:include page="/WEB-INF/templates/partial/uploadProgress.jsp" /></p>
  				</div>
  				
  				<div class="form-group">
  					<hr/>
  					<span class="text-justify"><a href="<c:url value="/protected/example/participant"/>" target="_blank"><i class="fa fa-file-o"></i>&nbsp;<spring:message code="file.example.title" text="Example File" /></a></span>
  				</div>
  				
  				<div class="form-group">
  					<hr/>
  					<small class="text-justify"><spring:message code="import.article.suporte" text="See default format " /></small>
  					<pre class="help-block">
&quot;name&quot;, &quot;surname&quot;, &quot;email&quot;
&quot;Maria&quot;,&quot;Oliveira&quot;,&quot;maria.oliveira@example.com&quot;
&quot;Joao&quot;,&quot;Souza&quot;,&quot;maria.oliveira@example.com&quot;
&quot;Fulano&quot;,&quot;Santos&quot;,&quot;maria.oliveira@example.com&quot;
&quot;Beltrano&quot;,&quot;Silva&quot;,&quot;maria.oliveira@example.com&quot;
&quot;Ciclano&quot;,&quot;Castro&quot;,&quot;maria.oliveira@example.com&quot;
  					</pre>
  				</div>
  				
  				<div class="form-group">
  					<small class="text-justify bold"><spring:message code="file.columns.required.title" text="Required columns in the file." /></small>
  					<ul class="text-danger small">
  						<li>name</li>
  						<li>surname</li>
  						<li>email</li>
  					</ul>
  				</div>
  				
  				<div class="form-group">
  					<small class="text-justify bold"><spring:message code="file.columns.opcional.title" text="Opcional columns in the file." /></small>
  					<ul class="text-info small">
					  	<li>birthdate</li>
						<li>currentAddress	</li>
						<li>currentCity</li>
						<li>currentZipCode</li>
						<li>currentNumber</li>
						<li>currentProvince</li>
						<li>currentCountry</li>
						<li>mapLat</li>
						<li>mapLng</li>
						<li>contactPhoneNumber</li>						
						<li>homePhoneNumber</li>
						
						<li>uniDepartment</li>
						<li>uniCodCourse</li>
						<li>schoolCourse</li>
						<li>uniPhase</li>
						<li>uniStatus</li>
						
						<li>device</li>
						<li>notes</li>
  					</ul>
  				</div>  				
			</div>	 
			</form>
			<!-- END FORM -->
			</div>
			<div class="modal-footer">
				<button class="btn dark btn-outline" type="button" data-dismiss="modal"><spring:message code="close.title" text="Close" /></button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>