<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- MAPA -->
<div class="form-group">
    <div class="row">
        <div class="col-md-offset-1 col-md-8">
            <!-- STATUS -->
            <button class="btn btn-success btn-block text-uppercase" disabled ng-show="form.resolved"><spring:message code="resolved.title" text="Resolved" /></button>
            <button class="btn btn-warning btn-block text-uppercase" disabled ng-hide="form.resolved"><spring:message code="opened.title" text="Opened" /></button>
            <!-- MAPA -->
            <div id="map-full-canvas">
                <ng-map zoom="11" center=" -27.586347, -48.502900" class="ng-map-h-50">
                    <marker position="currentPosition" animation="Animation.BOUNCE"></marker>
                </ng-map>
            </div>
        </div>
    </div>
</div>
<!-- ENDERECO -->
<div class="form-group">
    <div class="row">
        <div class="col-md-offset-1 col-md-8">

            <div class="col-md-12">
                <label class="small form-text text-muted"><spring:message code="address.title" text="provider"/></label>
                <div class="input-group">
                    <input type="text" class="form-control" id="addr_street" name="addr_street"
                           placeholder="<spring:message code="address.title" text="Street" />" maxlength="255"
                           places-auto-complete ng-model="form.addr_street" on-place-changed="ngmapPlaceChanged()"/>
                    <div class="input-group-btn">
                        <button class="btn btn-primary" type="button"><i class="fa fa-map-marker"
                                                                         aria-hidden="true"></i></button>
                    </div>
                </div>
            </div>

            <div class="col-md-2">
                <label class="small form-text text-muted"><spring:message code="provider.title"
                                                                          text="provider"/></label>
                <input type="text" class="form-control" id="provider" name="provider"
                       placeholder="<spring:message code="provider.title" text="provider" />" maxlength="40"
                       ng-model="form.provider"/>
            </div>

            <div class="col-md-2">
                <label class="small form-text text-muted"><spring:message code="latitude.title"
                                                                          text="Latitude"/></label>
                <input type="text" class="form-control" id="addr_lat" name="addr_lat"
                       placeholder="<spring:message code="latitude.title" text="Latitude" />" maxlength="40"
                       ng-model="form.latitude" ui-number-mask="7" ui-negative-number ui-hide-group-sep/>
            </div>

            <div class="col-md-2">
                <label class="small form-text text-muted"><spring:message code="longitude.title"
                                                                          text="Longitude"/></label>
                <input type="text" class="form-control" id="addr_lng" name="addr_lng"
                       placeholder="<spring:message code="longitude.title" text="Longitude" />" maxlength="40"
                       ng-model="form.longitude" ui-number-mask="7" ui-negative-number ui-hide-group-sep/>
            </div>

            <div class="col-md-2">
                <label class="small form-text text-muted"><spring:message code="altitude.title"
                                                                          text="Altitude"/></label>
                <input type="text" class="form-control" id="altitude" name="altitude"
                       placeholder="<spring:message code="altitude.title" text="altitude" />" maxlength="40"
                       ng-model="form.altitude" ui-number-mask="7" ui-negative-number ui-hide-group-sep/>
            </div>

            <div class="col-md-2">
                <label class="small form-text text-muted"><spring:message code="course.title" text="Course"/></label>
                <input type="text" class="form-control" id="course" name="course"
                       placeholder="<spring:message code="course.title" text="course" />" maxlength="40"
                       ng-model="form.course" ui-number-mask="7" ui-negative-number ui-hide-group-sep/>
            </div>

            <div class="col-md-2">
                <label class="small form-text text-muted"><spring:message code="floor.title" text="Floor"/></label>
                <input type="text" class="form-control" id="floor" name="floor"
                       placeholder="<spring:message code="floor.title" text="floor" />" maxlength="40"
                       ng-model="form.floor" ui-number-mask="7" ui-negative-number ui-hide-group-sep/>
            </div>

            <div class="col-md-2">
                <label class="small form-text text-muted"><spring:message code="speed.title" text="Speed"/></label>
                <input type="text" class="form-control" id="speed" name="speed"
                       placeholder="<spring:message code="speed.title" text="speed" />" maxlength="40"
                       ng-model="form.speed" ui-number-mask="7" ui-negative-number ui-hide-group-sep/>
            </div>

            <div class="col-md-2">
                <label class="small form-text text-muted"><spring:message code="accuracy.title"
                                                                          text="Accuracy"/></label>
                <input type="text" class="form-control" id="accuracy" name="accuracy"
                       placeholder="<spring:message code="accuracy.title" text="accuracy" />" maxlength="40"
                       ng-model="form.accuracy" ui-number-mask="7" ui-negative-number ui-hide-group-sep/>
            </div>

            <div class="col-md-2">
                <label class="small form-text text-muted"><spring:message code="horizontal.accuracy.title"
                                                                          text="H. Accuracy"/></label>
                <input type="text" class="form-control" id="horizontalaccuracy" name="horizontalaccuracy"
                       placeholder="<spring:message code="horizontal.accuracy.title" text="horizontalAccuracy" />"
                       maxlength="40"
                       ng-model="form.horizontalAccuracy" ui-number-mask="7" ui-negative-number ui-hide-group-sep/>
            </div>

            <div class="col-md-2">
                <label class="small form-text text-muted"><spring:message code="vertical.accuracy.title"
                                                                          text="V. Accuracy"/></label>
                <input type="text" class="form-control" id="verticalaccuracy" name="verticalaccuracy"
                       placeholder="<spring:message code="vertical.accuracy.title" text="verticalAccuracy" />"
                       maxlength="40"
                       ng-model="form.verticalAccuracy" ui-number-mask="7" ui-negative-number ui-hide-group-sep/>
            </div>
        </div>
    </div>
</div>
<!-- USER/CATEGORIA/OUVIDORIA/SIORG -->
<div class="form-group">
    <hr/>
    <!-- FORM BOX -->
    <div class="row">
        <div class="col-md-offset-1 col-md-8">
            <!-- USER -->
            <div class="col-md-3">
                <small class="help-block"><spring:message code="participant.title" text="Participant"/></small>
                <span ng-show="issueParticipant.name==undefined||issueParticipant.name.length==0">
					<!-- SEARCH -->
					<div class="input-group margin-bottom-10">
						<input type="text" name="pa-issue-participant-search" class="form-control border-right-0 border"
                               ng-change="onSearchIssueParticipant(search);" ng-model="search"/>
						<span class="input-group-addon"><i class="fa fa-search"></i></span>
					</div>
                    <!-- LISTA -->
					<ul id="pa-issue-participant-list" class="list-group pa-data-list">
						<li ng-repeat="(x, i) in listParticipant" class="list-group-item qz-pointer"
                            ng-click="onSelectIssueParticipant(i);">{{i[1]}}&nbsp;<small
                                class="text-muted">#{{i[0]}}</small></li>
					</ul>
				</span>
                <!-- USUARIO -->
                <span ng-show="issueParticipant.name.length>0">
					<div class="" ng-class="payload.readonly?'':'input-group'">
						<input type="text" ng-model="issueParticipant.name" name="user_name" id="user_name"
                               class="form-control" maxlength="255" readonly/>
                        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')">
						<span class="input-group-addon" ng-hide="payload.readonly"><i
                                class="fa fa-trash qz-pointer font-red"
                                ng-click="onRemoveIssueParticipant();"></i></span>
                        </sec:authorize>
					</div>
					<small class="help-block"><spring:message code="primary.title" text="Primary"/></small>
					<input type="email" ng-model="issueParticipant.email" name="primaryEmail" id="user_officialemail"
                           class="form-control" maxlength="255" readonly required/>
                    <!-- SECUNDARIO -->
					<small class="help-block"><spring:message code="email.title" text="Email"/></small>
					<input type="email" ng-model="issueParticipant.secondary" name="secondaryEmail" id="secondaryEmail"
                           class="form-control" maxlength="255" ng-disabled="payload.readonly" required/>
				</span>
            </div>
            <!-- CATEGORY -->
            <div class="col-md-3">
                <small class="help-block"><spring:message code="subcategory.title" text="Subcategory"/></small>
                <!-- CONSULTA -->
                <span ng-show="issueSubcategory.name==undefined||issueSubcategory.name.length==0">
					<!-- SEARCH -->
					<div class="input-group margin-bottom-10">
						<input type="text" name="pa-issue-subcategory-search" class="form-control border-right-0 border"
                               ng-change="onSearchIssueSubcategory(searchSub);" ng-model="searchSub"/>
						<span class="input-group-addon"><i class="fa fa-search"></i></span>
					</div>
                    <!-- LISTA -->
					<ul id="pa-issue-subcategory-list" class="list-group pa-data-list">
						<li ng-repeat="(x, i) in listSubcategory" class="list-group-item qz-pointer"
                            ng-click="onSelectIssueSubcategory(i);">
							<div class="media">
							  <div class="media-left">
								  <img class="media-object img-circle" ng-src="{{i[3]|userimage}}" alt="{{i[1]}}"
                                       width="32">
							  </div>
							  <div class="media-body">
								  <h6 class="media-heading">{{i[1]}}</h6>
								  <small style="color: {{i[5]}}">{{i[4]}}</small>&nbsp;<small
                                      class="text-muted">#{{i[0]}}</small>
							  </div>
							</div>
						</li>
					</ul>
				</span>

                <span ng-show="issueSubcategory.name.length>0">
					<!-- CATEGORY -->
					<div class="" ng-class="payload.readonly?'':'input-group'">
						<input type="text" ng-model="issueSubcategory.name" name="s_name" id="s_name"
                               class="form-control" maxlength="255" readonly/>
                        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')">
						<span class="input-group-addon" ng-hide="payload.readonly"><i
                                class="fa fa-trash qz-pointer font-red"
                                ng-click="onRemoveIssueSubcategory();"></i></span>
                        </sec:authorize>
					</div>
                    <!-- SUB CATEGORY -->
					<small class="help-block"><spring:message code="category.title" text="Category"/></small>
					<input type="text" ng-model="issueSubcategory.category" name="c_name" id="c_name"
                           class="form-control" maxlength="255" readonly required/>
				</span>

                <!--  SCORE -->
                <small class="help-block" ng-class="form.negativeScore>0?'text-danger':'text-success'"><spring:message
                        code="negative.score.title" text="Negative Score"/></small>
                <input type="number" ng-model="form.negativeScore" name="negativescore" id="negativescore"
                       class="form-control" min="0"/>
            </div>
            <!-- OMBUDSMAN -->
            <div class="col-md-3">
                <small class="help-block"><spring:message code="cgu.ombudsman.title" text="Ombudsman"/></small>
                <span ng-show="issueOmbudsman.name==undefined||issueOmbudsman.name.length==0">
					<!-- SEARCH -->
					<div class="input-group margin-bottom-10">
						<input type="text" name="pa-issue-ombudsman-search" class="form-control border-right-0 border"
                               ng-change="onSearchIssueOmbudsman(searchOm);" ng-model="searchOm"/>
						<span class="input-group-addon"><i class="fa fa-search"></i></span>
					</div>
                    <!-- LISTA -->
					<ul id="pa-issue-ombudsman-list" class="list-group pa-data-list">
						<li ng-repeat="(x, i) in listOmbudsman" class="list-group-item qz-pointer"
                            ng-click="onSelectIssueOmbudsman(i);">{{i.nomeOrgaoOuvidoria}}&nbsp;<small
                                class="text-muted">#{{i.idOuvidoria}}</small></li>
					</ul>
				</span>
                <!-- SELECIONADO -->
                <span ng-show="issueOmbudsman.name.length>0">
					<div class="" ng-class="payload.readonly?'':'input-group'">
						<input type="text" ng-model="issueOmbudsman.name" name="ombudsman_name" id="ombudsman_name"
                               class="form-control" maxlength="255" readonly/>
                        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')">
						<span class="input-group-addon" ng-hide="payload.readonly"><i
                                class="fa fa-trash qz-pointer font-red" ng-click="onRemoveIssueOmbudsman();"></i></span>
                        </sec:authorize>
					</div>
					<small class="help-block"><spring:message code="cgu.sphere.title" text="Sphere"/></small>
					<input type="text" ng-model="issueOmbudsman.sphere" name="sphere" id="sphere" class="form-control"
                           maxlength="255" readonly required/>
                    <!-- DADOS -->
					<small class="help-block"><spring:message code="county.title" text="County"/></small>
					<input type="text" ng-model="issueOmbudsman.county" name="county" id="county" class="form-control"
                           maxlength="255" readonly/>
				</span>
            </div>
            <!-- SIORG -->
            <div class="col-md-3">
                <small class="help-block"><spring:message code="cgu.siorg.title" text="SiOrg"/></small>
                <span ng-show="issueSiorg.name==undefined||issueSiorg.name.length==0">
					<!-- SEARCH -->
					<div class="input-group margin-bottom-10">
						<input type="text" name="pa-issue-siorg-search" class="form-control border-right-0 border"
                               ng-change="onSearchIssueSiorg(searchSo);" ng-model="searchSo"/>
						<span class="input-group-addon"><i class="fa fa-search"></i></span>
					</div>
                    <!-- LISTA -->
					<ul id="pa-issue-siorg-list" class="list-group pa-data-list">
						<li ng-repeat="(x, i) in listSiorg" class="list-group-item qz-pointer"
                            ng-click="onSelectIssueSiorg(i);">{{i.nomOrgao}}&nbsp;<small
                                class="text-muted">#{{i.codOrg}}</small></li>
					</ul>
				</span>
                <!-- SELECIONADO -->
                <span ng-show="issueSiorg.name.length>0">
					<div class="" ng-class="payload.readonly?'':'input-group'">
						<input type="text" ng-model="issueSiorg.name" name="nomorgao" id="nomorgao" class="form-control"
                               maxlength="255" readonly/>
                        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')">
						<span class="input-group-addon" ng-hide="payload.readonly"><i
                                class="fa fa-trash qz-pointer font-red" ng-click="onRemoveIssueSiorg();"></i></span>
                        </sec:authorize>
					</div>
					<small class="help-block"><spring:message code="cgu.codorg.title" text="CodOrg"/></small>
					<input type="text" ng-model="issueSiorg.id" name="codorg" id="codorg" class="form-control"
                           maxlength="255" readonly required/>
				</span>
            </div>
        </div>
        <div class="col-md-offset-1 col-md-8">
            <!-- e-OUV/Fala.BR -->
            <!-- PROTOCOL -->
<%--            <div class="col-md-4">--%>
<%--                <small class="help-block"><spring:message code="cgu.eouv.protocol.title" text="Protocol (e)"/></small>--%>
<%--                <div class="" ng-class="payload.readonly?'NO-CLASS':''">--%>
<%--                    <input type="text" ng-model="form.publicProtocol" name="pubProtocol" id="pubProtocol" class="form-control" maxlength="255" readonly/>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <!-- URL -->--%>
<%--            <div class="col-md-4">--%>
<%--                <small class="help-block"><spring:message code="cgu.eouv.url.title" text="URL (e)"/></small>--%>
<%--                <input type="text" ng-model="form.publicUrl" name="pubUrl" id="pubUrl" class="form-control" maxlength="255" readonly/>--%>
<%--            </div>--%>
<%--            <!-- MESSAGE -->--%>
<%--            <div class="col-md-4">--%>
<%--                <small class="help-block"><spring:message code="cgu.eouv.message.title" text="Message (e)"/></small>--%>
<%--                <input type="text" ng-model="form.publicMessage" name="pubMessage" id="pubMessage" class="form-control" maxlength="255" readonly/>--%>
<%--            </div>--%>

            <div class="col-md-12">
                <h4><spring:message code="cgu.eouv.protocol.title" text="Protocol (e)"/></h4>
                <ul class="list-unstyled">
                    <li>
                        <a href="{{form.publicUrl}}" target="_blank" ng-show="form.publicProtocol.length>0">{{form.publicProtocol}}</a>&nbsp;
                        - &nbsp;{{form.ombudsmanName||''}}&nbsp;|&nbsp;{{form.publicMessage||''}}
                        <br/>
                    </li>
                    <li ng-repeat="(ko, vo) in issueOmbudsmanList">
                        <a href="{{vo[30]}}" target="_blank" ng-show="vo[26].length>0">{{vo[26]}}</a>&nbsp;
                        - &nbsp;{{vo[35]||''}}&nbsp;|&nbsp;{{vo[28]||''}}
                        <br/>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<hr/>
<!-- TEXT -->
<div class="form-group">
    <div class="col-md-offset-1 col-md-8">
        <small class="help-block"><spring:message code="issue.title" text="Issue (Report)"/></small>
        <textarea rows="6" cols="" class="form-control" ng-model="form.comment" required></textarea>
    </div>

    <div class="col-md-offset-1 col-md-8" ng-show="issueCguManifestacaoResposta.length>0">
        <small class="help-block"><spring:message code="answer.title" text="Answer"/></small>
        <ul class="list-unstyled">
            <li ng-repeat="(k, r) in issueCguManifestacaoResposta" class="well">
                {{r.texto||''}}
            </li>
        </ul>
    </div>

    <div class="col-md-offset-1 col-md-8" ng-show="issueCguManifestacaoHistorico.length>0">
        <hr/>
        <small class="help-block"><spring:message code="history.title" text="History"/></small>
        <ul class="list-unstyled">
            <li ng-repeat="(k, h) in issueCguManifestacaoHistorico" class="well">
                <small>{{h.acao||''}} / {{h.responsavel||''}}</small>
                <small class="text-muted">{{h.infoAdicionais||''}}</small>
            </li>
        </ul>
    </div>
</div>
<hr/>
<!-- UPLOAD -->
<div class="form-group">
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR','ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR')">
    <!-- BUTTON -->
    <div class="col-md-2 pull-right">
        <button class="btn btn-circle blue animated bounceInRight" type="button"
                ng-controller="FileUploadCtrl" type="button"
                ngf-select="uploadStorage($file, 's','pa-issue-report-upload');"
                ng-model="file" name="file" ngf-pattern="'image/*'" ngf-accept="'image/*'" ngf-max-size="10MB">&nbsp;&nbsp;&nbsp;
            <i class="fa fa-cloud-upload"></i>&nbsp;&nbsp;&nbsp;<spring:message code="upload.title" text="Upload"/>&nbsp;&nbsp;&nbsp;
        </button>
    </div>
    <input type="text" class="form-control" id="std-class" name="upload" ng-model="form.upload"
           ng-change="onChangeUploadFile();" ng-show="false" ng-cloak/>
    </sec:authorize>
</div>
<!-- IMAGE/VIDEO/AUDIO -->
<div class="form-group">
    <div class="col-md-offset-2 col-md-7">
        <div class="col-md-2" ng-repeat="(k, f) in form.files" ng-switch on="f.discriminatorType">
            <div ng-switch-when='I'>
                <img class="img-responsive" ng-src="{{f.assetUrl}}" alt="{{f.fileName}}"/>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR','ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR')">
                <button type="button" class="btn red btn-block" ng-click="removeFile(k, f.id);"><i
                        class="fa fa-trash"></i></button>
                </sec:authorize>
            </div>
            <div ng-switch-when='A'>
                <div audios code="f.assetUrlWeb" class="well col-md-12"></div>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR','ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR')">
                <button type="button" class="btn red btn-block" ng-click="removeFile(k, f.id);"><i
                        class="fa fa-trash"></i></button>
                </sec:authorize>
            </div>
            <div ng-switch-when='V'>
                <div videos code="f.assetUrlWeb" class="col-md-12"></div>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR','ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR')">
                <button type="button" class="btn red btn-block" ng-click="removeFile(k, f.id);"><i
                        class="fa fa-trash"></i></button>
                </sec:authorize>
            </div>
        </div>
    </div>
</div>
<hr/>