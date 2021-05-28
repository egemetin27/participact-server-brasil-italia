<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
<!-- BEGIN PAGE-->
<div class="row" ng-controller="IssueCategoryCtrl" ng-init="initIssueCategory();" ng-cloak id="pa-cgu-issue-category">
    <div class="col-md-12">
        <div class="portlet">
            <!-- BREADCRUMB -->
            <jsp:include page="/WEB-INF/views/protected/issue-category/breadcrumb.jsp" />
            <!-- BEGIN BODY -->
            <div class="portlet-body">
                <div class="table-scrollable">
                    <table class="table table-bordered">
                    <thead>
                        <tr role="row" class="heading">
                            <th class="col-md-4"><spring:message code="issue.category.root.title" text="Category (Root)" />
                                &nbsp;<button class="btn btn-success btn-sm pull-right" type="button" ng-click="addCategory(0);"><i class="fa fa-plus"></i></button>
                            </th>
                            <th class="col-md-4"><spring:message code="issue.subcategory.title" text="Sub Category" />&nbsp;/<spring:message code="issue.level.title" text="Level" /> (1)
                                &nbsp;<button class="btn btn-primary btn-sm pull-right" type="button" ng-disabled="!currentCategoryId" ng-click="addCategory(1);"><i class="fa fa-plus-circle"></i></button>
                            </th>
                            <th class="col-md-4"><spring:message code="issue.subcategory.title" text="Sub Category" />&nbsp;/<spring:message code="issue.level.title" text="Level" /> (2)
                                &nbsp;<button class="btn btn-warning btn-sm pull-right" type="button" ng-disabled="!currentSubCategoryId" ng-class="!currentSubCategoryId?'':'animated bounce'" ng-click="addCategory(2);"><i class="fa fa-plus-square"></i></button>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <!-- LEVEL 0 -->
                            <td class="col-md-4">
                                <ul class="list-group" ui-sortable="sortableOptions" ng-model="categories">
                                    <li ng-repeat="(k, v) in categories" class="list-group-item" ng-style="v.id==currentCategory.id? {'background-color': v.color||'white', 'margin-right':'-10px','margin-left':'-10px'}:{'background-color': v.color||'white'}">
                                        <div class="media" >
                                            <div class="media-left media-middle"><img class="media-object img-rounded" src="{{v.urlIcon|userimage}}" alt="{{v.name}}" height="24"></div>
                                            <div class="media-left media-middle"><img class="media-object img-rounded" src="{{v.urlAsset|userimage}}" alt="{{v.name}}" height="24"></div>
                                            <div class="media-left media-middle" ng-style="{'background-color': v.color||'white'}"><img class="media-object img-rounded " src="{{v.urlAssetLight|userimage}}" alt="{{v.name}}" height="24"></div>
                                            <div class="media-body">
                                                <span class="media-heading bold">{{v.name}}</span>
                                                <!-- BUTTONS -->
                                                <div class="btn-group btn-group-sm pull-right" role="group" aria-label="">
                                                    <div class="btn btn-warning myHandle">{{k+1}}</div>
                                                    <button type="button" class="btn btn-primary" ng-click="editCategory(k, 0);"><i class="fa fa-edit"></i></button>
                                                    <button type="button" class="btn btn-default" ng-click="selectCategory(k, 0)"><i class="fa fa-list-ul"></i></button>
                                                    <button type="button" class="btn btn-danger" ng-click="removeCategory(k, 0)"><i class="fa fa-trash"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </td>
                            <!-- LEVEL 1 -->
                            <td ng-style="{'background-color':currentCategory.color||'white'}">
                                <div ng-hide="!currentCategoryId" ng-cloak>
                                    <!-- SELECIONADO -->
                                    <div class="media">
                                        <div class="media-left"><img class="media-object" src="{{currentCategory.urlAsset}}" alt="{{currentCategory.name}}" height="24" style="max-width: 48px;"></div>
                                        <div class="media-body">
                                            <h3 class="media-heading bold text-uppercase">{{currentCategory.name}}</h3>
                                        </div>
                                    </div>
                                    <!-- ITEMS LEVEL 0 -->
                                    <ul class="list-unstyled" ui-sortable="sortableOptions" ng-model="currentCategory.map[1]">
                                        <li ng-repeat="(k1, v1) in currentCategory.map[1]" class="margin-top-15">
                                            <div class="input-group">
                                                <span class="input-group-btn"><img src="{{v1.urlAsset}}" height="24" style="max-width: 48px;"/></span>
                                                <span class="input-group-btn"><img src="{{v1.urlAssetLight}}" height="24" style="max-width: 48px;"/></span>
                                                <input type="text" class="form-control text-capitalize" placeholder="{{v1.name}}" ng-value="v1.name" readonly>
                                                <span class="input-group-btn"><div class="btn btn-warning myHandle">{{k1+1}}</div></span>
                                                <span class="input-group-btn"><button type="button" class="btn btn-primary" ng-click="editCategory(k1,1);"><i class="fa fa-edit"></i></button></span>
                                                <span class="input-group-btn"><button type="button" class="btn btn-default" ng-click="selectCategory(k1, 1)"><i class="fa fa-list-ul"></i></button></span>
                                                <span class="input-group-btn"><button type="button" class="btn btn-danger" ng-click="removeCategory(k1, 1 )"><i class="fa fa-trash"></i></button></span>
                                            </div><!-- /input-group -->
                                        </li>
                                    </ul>
                                </div>
                            </td>
                            <!-- LEVEL 2 -->
                            <td ng-style="!currentSubCategoryId?'':{'background-color':currentCategory.color||'white'}">
                                <div ng-hide="!currentSubCategoryId" ng-cloak>
                                    <!-- SELECIONADO -->
                                    <div class="media">
                                        <div class="media-left"><img class="media-object" src="{{currentSubCategory.urlAsset}}" alt="{{currentSubCategory.name}}" height="24" style="max-width: 48px;"></div>
                                        <div class="media-body">
                                            <h3 class="media-heading bold text-uppercase">{{currentSubCategory.name}}</h3>
                                        </div>
                                    </div>
                                    <!-- ITEMS LEVEL 2 -->
                                    <ul class="list-unstyled" ui-sortable="sortableOptions" ng-model="currentCategory.map[2]">
                                        <li ng-repeat="(k2, v2) in currentCategory.map[2]" class="margin-top-15">
                                            <div class="input-group">
                                                <span class="input-group-btn"><img src="{{v2.urlAsset}}" height="24" style="max-width: 48px;"/></span>
                                                <span class="input-group-btn"><img src="{{v2.urlAssetLight}}" height="24" style="max-width: 48px;"/></span>
                                                <input type="text" class="form-control text-capitalize" placeholder="{{v2.name}}" ng-value="v1.name" readonly>
                                                <span class="input-group-btn"><div class="btn btn-warning myHandle">{{k2+1}}</div></span>
                                                <span class="input-group-btn"><button type="button" class="btn btn-primary" ng-click="editCategory(k2,2);"><i class="fa fa-edit"></i></button></span>
                                                <span class="input-group-btn"><button type="button" class="btn btn-danger" ng-click="removeCategory(k2,2)"><i class="fa fa-trash"></i></button></span>
                                            </div><!-- /input-group -->
                                        </li>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                </div>
            </div>
            <!-- END BODY -->
        </div>
    </div>
    <!-- MODAL -->
    <div class="modal fade" id="paModalCategory" tabindex="-1" role="dialog" aria-labelledby="paModalCategoryLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <form name="paForm">
                        <!-- A -->
                        <div class="row">
                            <div class="col-md-8  col-md-8 col-xs-8">
                                <label><spring:message code="name.title" text="Name" /></label>
                                <input type="text" class="form-control" name="name" id="name" ng-model="form.name" aria-label="" required maxlength="45">
                            </div>

                            <div class="col-md-4 col-md-4 col-xs-4" ng-show="form.level==0" ng-cloak>
                                <label><spring:message code="color.title" text="Color" /></label>
                                <input type="color" class="form-control" name="color" id="color" ng-model="form.color" aria-label="">
                            </div>
                        </div>
                        <!-- B -->
                        <div class="row margin-top-20" ng-show="form.level==0" ng-cloak>
                            <div class="col-md-8 col-sm-8 col-xs-8">
                                <label><spring:message code="marker.title" text="Pin" /></label>
                                <div class="input-group">
			                        <span class="input-group-btn"><button type="button" class="btn blue" ng-controller="FileUploadCtrl" type="button" ngf-select="uploadStorage($file, 'c','urlIcon');" ng-model="file" name="file" ngf-pattern="'image/*'" ngf-accept="'image/*'" ngf-max-size="1MB"><i class="fa fa-cloud-upload" aria-hidden="true"></i></button></span>
                                    <input type="url" class="form-control"  name="urlIcon" id="urlIcon" ng-model="form.urlIcon"/>
                                    <span class="input-group-btn"><a ng-href="{{form.urlIcon}}" class="btn blue"  target="_blank"><i class="fa fa-external-link" aria-hidden="true"></i></a></span>
                                </div><!-- /input-group -->
                            </div>
                            <!-- ICON -->
                            <div class="col-md-2 col-sm-2 col-xs-12" ng-show="form.urlIcon.length>0">
                                <img ng-src="{{form.urlIcon}}" alt="" class="img-thumbnail img-responsive" height="48" ng-style="{'background-color': form.color||'white'}"/>
                            </div>
                        </div>
                        <!-- C -->
                        <div class="row margin-top-20">
                            <div class="col-md-8 col-sm-8 col-xs-8">
                                <spring:message code="icon.title" text="Icon" />
                                <div class="input-group">
                                    <span class="input-group-btn"><button type="button" class="btn blue" ng-controller="FileUploadCtrl" type="button" ngf-select="uploadStorage($file, 'c','urlAsset');" ng-model="file" name="file" ngf-pattern="'image/*'" ngf-accept="'image/*'" ngf-max-size="1MB"><i class="fa fa-cloud-upload" aria-hidden="true"></i></button></span>
                                    <input type="url" class="form-control"  name="urlAsset" id="urlAsset" ng-model="form.urlAsset"/>
                                    <span class="input-group-btn"><a ng-href="{{form.urlAsset}}" class="btn blue"  target="_blank"><i class="fa fa-external-link" aria-hidden="true"></i></a></span>
                                </div><!-- /input-group -->
                            </div>
                            <!-- ICON -->
                            <div class="col-md-2 col-sm-2 col-xs-12" ng-show="form.urlAsset.length>0">
                                <img ng-src="{{form.urlAsset}}" alt="" class="img-thumbnail img-responsive" height="48" ng-style="{'background-color': form.color||'white'}"/>
                            </div>
                        </div>
                        <!-- D -->
                        <div class="row margin-top-20">
                            <div class="col-md-8 col-sm-8 col-xs-8">
                                <spring:message code="icon.light.title" text="Icon Light" />
                                <div class="input-group">
                                    <span class="input-group-btn"><button type="button" class="btn blue" ng-controller="FileUploadCtrl" type="button" ngf-select="uploadStorage($file, 'c','urlAssetLight');" ng-model="file" name="file" ngf-pattern="'image/*'" ngf-accept="'image/*'" ngf-max-size="1MB"><i class="fa fa-cloud-upload" aria-hidden="true"></i></button></span>
                                    <input type="url" class="form-control"  name="urlAssetLight" id="urlAssetLight" ng-model="form.urlAssetLight"/>
                                    <span class="input-group-btn"><a ng-href="{{form.urlAssetLight}}" class="btn blue"  target="_blank"><i class="fa fa-external-link" aria-hidden="true"></i></a></span>
                                </div><!-- /input-group -->
                            </div>
                            <!-- ICON -->
                            <div class="col-md-2 col-sm-2 col-xs-12" ng-show="form.urlAssetLight.length>0">
                                <img ng-src="{{form.urlAssetLight}}" alt="" class="img-thumbnail img-responsive" height="48" ng-style="{'background-color': form.color||'white'}"/>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default pull-left" data-dismiss="modal"><spring:message code="close.title" text="Close" /> </button>
                    <button type="button" class="btn btn-primary pull-right" ng-click="saveIssueCategory();"><spring:message code="save.title" text="Save" /> </button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- END PAGE-->
<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />