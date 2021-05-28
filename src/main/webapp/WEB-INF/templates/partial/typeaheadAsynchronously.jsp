<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="it.unibo.paserver.domain.UniCourse" %>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<small ng-show="loadingLocations" class="help-block">
	<i class="glyphicon glyphicon-refresh"></i>
</small>
<small ng-show="noResults" class="help-block">
	<i class="glyphicon glyphicon-remove"></i> No Results Found
</small>
<script type="text/ng-template" id="typeahead-template.html">
  <div class="custom-popup-wrapper" ng-style="{top: position().top+'px', left: position().left+'px'}" style="display: block;"
     ng-show="isOpen() && !moveInProgress"
     aria-hidden="{{!isOpen()}}">

    <ul class="dropdown-menu" role="listbox">
      <li class="uib-typeahead-match" ng-repeat="match in matches track by $index" ng-class="{active: isActive($index) }"
        ng-mouseenter="selectActive($index)" ng-click="selectMatch($index)" role="option" id="{{::match.id}}">
        <div uib-typeahead-match index="$index" match="match" query="query" template-url="templateUrl"></div>
      </li>
    </ul>
  </div>
</script>