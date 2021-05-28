<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- BEGIN LOOP :P -->
<div class="row">
<div class="col-md-12 margin-bottom-30">
<ul class="list-unstyled" ui-sortable ng-model="contest_questions">
<li class="col-md-12 margin-bottom-10" ng-repeat="(q_key, q_item) in contest_questions">
<div class="well">
<!-- BEGIN QUESTION -->
<!-- BEGIN FORM QUESTION -->
<div class="form-group">
	<div class="col-md-2 col-sm-6">
		<small class="help-block"><spring:message code="question.sing.title" text="Question" /></small>
		<div class="input-group">
			<span class="input-group-btn">
				<div class="btn yellow myHandle" title="<spring:message code="order.title"  text="Order"/>"> {{q_key+1 || 0}}</div>
				<button ng-click="removeQuestion(q_key);" class="btn red" type="button" title="<spring:message code="remove.title" text="Remove" />"><i class="fa fa-trash"></i></button></span>
		</div>
	</div>
	<div class="col-md-4 col-sm-6">
		<small class="help-block"><spring:message code="type.question.title" text="Type" /></small>
		<div class="btn-group" role="group" aria-label="Question Type">
			<button type="button" class="btn" title="<spring:message code="open.question.title" text="Open question" />"
				ng-class="contest_questions[q_key].contest_type_id=='OPENQUESTION'?'btn-primary':'btn-outline btn-default'" ng-click="contest_questions[q_key].contest_type_id='OPENQUESTION'"><i class="fa fa-font"></i></button>

			<button type="button" class="btn" title="<spring:message code="school.question.title" text="School question" />"
					ng-class="contest_questions[q_key].contest_type_id=='SCHOOLSFROMGPSQUESTION'?'btn-primary':'btn-outline btn-default'" ng-click="contest_questions[q_key].contest_type_id='SCHOOLSFROMGPSQUESTION'"><i class="fa fa-university"></i></button>

			<button type="button" class="btn" title="<spring:message code="date.question.title" text="Date question" />"
					ng-class="contest_questions[q_key].contest_type_id=='DATEQUESTION'?'btn-primary':'btn-outline btn-default'" ng-click="contest_questions[q_key].contest_type_id='DATEQUESTION'"><i class="fa fa-calendar"></i></button>

			<button type="button" class="btn btn-secondary" title="<spring:message code="single.question.title" text="Single choice" />"
					ng-class="contest_questions[q_key].contest_type_id=='CLOSEDQUESTIONSIN'?'btn-primary':'btn-outline btn-default'" ng-click="contest_questions[q_key].contest_type_id='CLOSEDQUESTIONSIN'"><i class="fa fa-circle-o"></i></button>
			<button type="button" class="btn btn-secondary" title="<spring:message code="multiple.question.title" text="Multiple choices" />"
					ng-class="contest_questions[q_key].contest_type_id=='CLOSEDQUESTIONMUL'?'btn-primary':'btn-outline btn-default'" ng-click="contest_questions[q_key].contest_type_id='CLOSEDQUESTIONMUL'"><i class="fa fa-square-o"></i></button>
			<button type="button" class="btn btn-secondary" title="<spring:message code="photo.question.title" text="Photo" />"
					ng-class="contest_questions[q_key].contest_type_id=='PHOTOQUESTION'?'btn-primary':'btn-outline btn-default'" ng-click="contest_questions[q_key].contest_type_id='PHOTOQUESTION'"><i class="fa fa-image"></i></button>
			<button class="btn" title="<spring:message code="required.question.title" text="If checked, the question is required." /> "
					ng-class="contest_questions[q_key].isrequired?'btn-danger':'btn-outline btn-default'" ng-click="contest_questions[q_key].isrequired=!contest_questions[q_key].isrequired"><i class="fa fa-asterisk" aria-hidden="true"></i></button>
		</div>
	</div>

	<div class="col-md-4 col-sm-12" ng-show="contest_questions[q_key].contest_type_id=='OPENQUESTION'||contest_questions[q_key].contest_type_id=='PHOTOQUESTION'||contest_questions[q_key].contest_type_id=='DATEQUESTION'||contest_questions[q_key].contest_type_id=='SCHOOLSFROMGPSQUESTION'">
		<small class="help-block"><spring:message code="target.question.title" text="Target" /></small>
		<select class="form-control" ng-model="contest_questions[q_key].target_order">
			<option value="-1"><spring:message code="next.auto.title" text="Next/Auto" /></option>
			<option value="{{sqk}}"  ng-repeat="(sqk, sqi) in contest_questions" ng-hide="sqk==q_key">{{sqk+1||0}}&nbsp;{{sqi.question}}</option>
		</select>
	</div>

	<div class="col-md-2 col-sm-12" ng-show="contest_questions[q_key].contest_type_id=='PHOTOQUESTION'">
		<small class="help-block"><spring:message code="quantity.question.title" text="Quantity" /></small>
		<input type="text" class="form-control"  name="number_photos"
			   ng-model="contest_questions[q_key].number_photos" min="0" max="10" ui-number-mask="0" ui-hide-group-sep ng-min="0" ng-max="10" placeholder="1" maxlength="2" />
	</div>
</div>

<div class="form-group">
	<div class="col-md-12">
		<input type="text" ng-model="contest_questions[q_key].question" class="form-control" id="inputQuestion{{q_key}}"  maxlength="255" placeholder="<spring:message code="enter.question.title" text="Enter an question ..."/>"/>
	</div>
</div>
<!-- END FORM QUESTION -->
<!-- BEGIN OPTIONS -->
<div class="form-group">
<!-- BEGIN TEXT -->
<div class="col-md-12" ng-show="contest_questions[q_key].contest_type_id == 'OPENQUESTION'">
	<input ng-show="false" type="text" class="hidden" ng-model="q_item.contest_options[0].option" placeholder="<spring:message code="answer.title" text="Answer"/>">
</div>
<!-- END TEXT -->	
<!-- BEGIN SELECT ONE -->
<div class="col-md-12" ng-show="contest_questions[q_key].contest_type_id == 'CLOSEDQUESTIONSIN'">
	<ul class="list-unstyled" ui-sortable ng-model="q_item.contest_options">
		<li ng-repeat="(opt_key, opt_item) in q_item.contest_options" class="col-md-12 margin-bottom-10">
			<div class="col-lg-9 col-md-8 pull-left">
				<small class="help-block text-right"><spring:message code="option.question.title" text="Option" /></small>
				<div class="input-group">
					<span class="input-group-btn"><div class="btn yellow-saffron myHandle">{{opt_key+1||0}}</div></span>
					<span class="input-group-btn"><button class="btn red-mint" type="button" ng-click="removeAnswer(q_key, 'CLOSEDQUESTIONSIN', opt_key)"><i class="fa fa-minus"></i></button></span>
					<input type="text" class="form-control" ng-model="opt_item.option" id="inputCLOSEDQUESTIONSIN{{q_key}}{{opt_key}}" placeholder="<spring:message code="enter.answer.message" text="Type your answer here"/>">
				</div>
			</div>
			<div class="col-lg-3 col-md-4 pull-right">
				<small class="help-block"><spring:message code="target.question.title" text="Target" /></small>
				<select class="form-control" ng-model="opt_item.target_order">
					<option value="-1"><spring:message code="next.auto.title" text="Next/Auto" /></option>
					<option value="{{sqk}}"  ng-repeat="(sqk, sqi) in contest_questions" ng-hide="sqk==q_key">{{sqk+1||0}}&nbsp;{{sqi.question}}</option>
				</select>
			</div>
		</li>
		<li class="col-md-12"><a href="javascript:;" class="btn green-sharp" ng-click="addAnswer(q_key, 'CLOSEDQUESTIONSIN');"><i class="fa fa-plus"></i>&nbsp;<spring:message code="answer.title" text="Answer"/></a></li>				
	</ul>
</div>						
<!-- END SELECT ONE -->
<!-- BEGIN MULTI CHOICE -->
<div class="col-md-12"  ng-show="contest_questions[q_key].contest_type_id == 'CLOSEDQUESTIONMUL'">
	<ul class="list-unstyled" ui-sortable="sortableOptions" ng-model="q_item.contest_options">
		<li ng-repeat="(opt_key, opt_item) in q_item.contest_options" class="col-md-12 margin-bottom-10">
			<div class="col-lg-9 col-md-8 pull-left">
				<small class="help-block text-right"><spring:message code="option.question.title" text="Option" /></small>
				<div class="input-group">
					<span class="input-group-btn"><div class="btn yellow myHandle">{{opt_key+1||0}}</div></span>
					<span class="input-group-btn"><button class="btn red" type="button" ng-click="removeAnswer(q_key, 'CLOSEDQUESTIONMUL', opt_key)"><i class="fa fa-minus"></i></button></span>
					<input type="text" class="form-control" ng-model="opt_item.option" id="inputCLOSEDQUESTIONMUL{{q_key}}{{opt_key}}" placeholder="<spring:message code="enter.answer.message" text="Type your answer here"/>">
				</div>
			</div>
			<div class="col-lg-3 col-md-4 pull-right">
				<small class="help-block"><spring:message code="target.question.title" text="Target" /></small>
				<select class="form-control" ng-model="opt_item.target_order">
					<option value="-1"><spring:message code="next.auto.title" text="Next/Auto" /></option>
					<option value="{{sqk}}"  ng-repeat="(sqk, sqi) in contest_questions" ng-hide="sqk==q_key">{{sqk+1||0}}&nbsp;{{sqi.question}}</option>
				</select>
			</div>
		</li>
		<li class="col-md-12"><a href="javascript:;" class="btn green" ng-click="addAnswer(q_key, 'CLOSEDQUESTIONMUL');"><i class="fa fa-plus"></i>&nbsp;<spring:message code="answer.title" text="Answer"/></a></li>				
	</ul>
</div>
<!-- END MULTI CHOICE -->
</div>
<!-- END OPTIONS -->				
</div>
<!-- END QUESTION -->
</li></ul></div>
<!-- END LOOP :D -->
</div>
<!-- BEGIN DISPLAY -->
<div class="portlet-body" ng-show="contest_questions.length == '0'" id="no-data-to-display">
<div class="col-md-12 well text-center">
	<div class="margin-top-20">
		<i class="icon-ghost fa-5x animated rollIn" ng-class="{rollOut: hover}" ng-mouseenter="hover = true"></i>
		<h2><spring:message code="noquestions.title" text="No questions to display!"/></h2>
	</div>
</div>
</div>
<!-- END DISPLAY -->
<!-- BEGIN BUTTON NEW QUESTION -->
<button type="button" class="hidden-sm hidden-xs btn btn-lg green-sharp btn-outline sbold uppercase floating-action-button animated bounceInLeft" ng-click="addQuestion(1);"><i class="fa fa-plus"></i>&nbsp;<spring:message code="add.question.title" text="New Question"/></button>
<!-- END BUTTON NEW QUESTION -->