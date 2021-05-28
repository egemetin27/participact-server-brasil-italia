<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- TITLE -->
<div class="form-group"  ng-class="formCguCategoryType.name.$invalid?'has-error':'has-success'">
	<label class="col-md-2 control-label bold"><spring:message code="name.title" text="Name" /></label>	
	<div class="col-md-5">
		<input type="text" ng-model="form.name" name="name" id="name" class="form-control"  maxlength="255" required="required" />
	</div>
	<div class="col-sm-2">
		<input id="color" name="color" class="form-control" type="color" ng-model="form.color" />
	</div>
</div>
<!-- ICONE -->
<div class="form-group">
	<label class="col-md-2 control-label bold"><spring:message code="icon.title" text="Icon" />&nbsp;<i class="fa fa-image"></i></label>
	<div class="col-md-7">
		<div class="input-group">
			<span class="input-group-btn">
		   		<button type="button" class="btn blue" ng-controller="FileUploadCtrl" type="button" ngf-select="uploadStorage($file, 'c','urlAssetCategory');" ng-model="file" name="file" ngf-pattern="'image/*'" ngf-accept="'image/*'" ngf-max-size="1MB"><i class="fa fa-cloud-upload" aria-hidden="true"></i></button></span>
			<input type="url" class="form-control"  name="urlAssetCategory" id="urlAssetCategory" ng-model="form.urlAsset"/>
			<span class="input-group-btn"><a ng-href="{{form.urlAsset}}" class="btn blue"  target="_blank"><i class="fa fa-external-link" aria-hidden="true"></i></a></span>
		</div><!-- /input-group -->
	</div>
	<!-- ICON -->
	<div class="col-md-2" ng-show="form.urlAsset.length>0">
		<img ng-src="{{form.urlAsset}}" alt="" class="img-thumbnail" style="height: 52px; max-width: 61px;" ng-style="{'background-color': form.color||'white'}"/>
	</div>
</div>
<!-- MARKER -->
<div class="form-group">
	<label class="col-md-2 control-label bold"><spring:message code="marker.title" text="Pin Marker" />&nbsp;<i class="fa fa-map-pin"></i>&nbsp;<small class="text-muted">168x208</small></label>
	<div class="col-md-7">
		<div class="input-group">
		   		<span class="input-group-btn">
		   		<button type="button" class="btn blue" ng-controller="FileUploadCtrl" type="button" ngf-select="uploadStorage($file, 'c','urlMarkerCategory');" ng-model="file" name="file" ngf-pattern="'image/*'" ngf-accept="'image/*'" ngf-max-size="1MB"><i class="fa fa-cloud-upload" aria-hidden="true"></i></button></span>
			<input type="url" class="form-control"  name="urlMarkerCategory" id="urlMarkerCategory" ng-model="form.urlIcon"/>
			<span class="input-group-btn"><a ng-href="{{form.urlIcon}}" class="btn blue"  target="_blank"><i class="fa fa-external-link" aria-hidden="true"></i></a></span>
		</div><!-- /input-group -->
	</div>
	<!-- MARKER -->
	<div class="col-md-2" ng-show="form.urlIcon.length>0">
		<img ng-src="{{form.urlIcon}}" alt="" class="img-thumbnail" style="height: 52px; max-width: 61px;"/>
	</div>
</div>
<!-- LEVELs -->
<div class="form-group">
	<!-- BUTTON -->
	<div class="col-md-2">
		<button class="btn btn-circle btn-sm blue animated bounceInRight" type="button" ng-click="addSubCategory();">&nbsp;&nbsp;&nbsp;<i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;<spring:message code="subcategory.title" text="SubCategory" />&nbsp;&nbsp;&nbsp;</button>
	</div>
	<!-- LEVEL-->
	<div class="col-md-10">
		<ul class="list-unstyled" ui-sortable ng-model="subcategories">
			<li ng-repeat="(k, s) in subcategories">
				<!-- SUBCATEGORY -->
				<div class="row well">
					<div class="col-md-10">
						<!-- NAME -->
						<div class="col-md-12 margin-bottom-5">
							<div class="input-group">
								<span class="input-group-btn"><div class="btn yellow-saffron myHandle">{{k+1}}</div></span>
								<input type="text" ng-model="s.name" name="nameSubCategory{{k}}" id="nameSubCategory{{k}}" class="form-control"  maxlength="200" required="required" />
								<span class="input-group-btn"><button class="btn red" type="button" ng-click="removeSubCategory(k);"><i class="fa fa-trash"></i></button></span>
							</div><!-- /input-group -->
						</div>
						<!-- ICON -->
						<div class="col-md-12  margin-bottom-5">
							<div class="input-group">
							<span class="input-group-btn">
								<button type="button" class="btn blue" ng-controller="FileUploadCtrl" type="button" ngf-select="uploadStorage($file, 's','urlAssetSubCategory{{k}}');" ng-model="file" name="file" ngf-pattern="'image/*'" ngf-accept="'image/*'" ngf-max-size="1MB"><i class="fa fa-cloud-upload" aria-hidden="true"></i></button>
							</span>
								<input type="url" class="form-control"  name="urlAssetSubCategory{{k}}" id="urlAssetSubCategory{{k}}" ng-model="s.urlAsset"/>
								<span class="input-group-btn"><a ng-href="{{s.urlAsset}}" class="btn blue"  target="_blank"><i class="fa fa-external-link" aria-hidden="true"></i></a></span>
							</div><!-- /input-group -->
						</div>
						<!-- RELACOES -->
						<div class="col-md-12  margin-bottom-5">
							<!-- CONSULTA -->
							<div class="input-group">
								<input type="text" name="pa-cgu-ombudsmen-search-{{k}}" class="form-control py-2 border-right-0 border"
									   ng-change="onSearchCguOmbudsmen(k, search);" ng-model="search"/>
								<span class="input-group-addon"><i class="fa fa-search"></i></span>
							</div>
							<!-- LISTA -->
							<ul id="pa-cgu-category-list-{{k}}" class="list-group pa-data-list">
								<li ng-repeat="(x, i) in listOmbudsmen" class="list-group-item qz-pointer" ng-click="onSelectCguOmbudsmen(k, i);" >{{i.nomeOrgaoOuvidoria}}&nbsp;<small class="text-muted">#{{i.idOuvidoria}}</small></li>
							</ul>
							<!-- SELECIONADOS -->

							<ul class="margin-top-10">
								<li ng-repeat="(y, o) in s.ombudsmen">{{o.nomeOrgaoOuvidoria}}&nbsp;<i class="fa fa-times qz-pointer" ng-click="onRemoveCguOmbudsmen(k, y);"></i></li>
							</ul>
						</div>

					</div>
					<div class="col-md-2">
						<span class="input-group-btn"><img ng-src="{{s.urlAsset|userimage}}" alt="" class="img-responsive"/></span>
					</div>
				</div>
				<!-- ./SUBCATEGORY -->
			</li>
		</ul>
	</div>
</div>
