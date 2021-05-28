ParticipActApp.controller('FeedbackTypeCtrl', function($scope, $timeout, $http, $window, $localStorage, PacticipActSrvc, FeedbackTypeSrvc) {
	//clean
	$scope.cleanFeedbackType=function(){$scope.feedbacks=[],$scope.form={isActive:!1},$scope.totalItems=0,$scope.currentPage=0,$scope.radioModel="10",$scope.maxSize=10,$scope.itemsPerPage = 10,$scope.selected_items=0,$scope.controller=""};
	//Lista de itens
	$scope.initFeedbackType=function(){search=$scope.form,search.count=$scope.radioModel,search.offset=$scope.currentPage,isSpinnerBar(!0),FeedbackTypeSrvc.getListFeedbackType(search,search.count,search.offset).then(function(o){1==o.status?$timeout(function(){$scope.$apply(function(){$scope.feedbacks=o.items,$scope.totalItems=o.total,$scope.currentPage=o.offset,$scope.itemsPerPage=o.count})}):$scope.cleanFeedbackType(),$timeout(function(){isSpinnerBar(!1)},500)})};
	//Salvando
	$scope.saveFeedbackType=function(){isSpinnerBar(!0),FeedbackTypeSrvc.saveFeedbackType($scope.form).then(function(o){1==o.status?$timeout(function(){$("#fixgoback")[0].click()},600):(bootbox.alert(o.message),$timeout(function(){isSpinnerBar(!1)},500))})};
	//Edicao
	$scope.editFeedbackType=function(o){isSpinnerBar(!0),$timeout(function(){$window.location.href="edit/"+o},500)};
	$scope.getFeedbackType=function(){isSpinnerBar(!0),$scope.form={},PacticipActSrvc.isDebug()===!0&&PacticipActSrvc.fake($scope),isSpinnerBar(!1)};
	$scope.setFeedbackType=function(e){isSpinnerBar(!0),FeedbackTypeSrvc.getFeedbackType(e).then(function(e){1==e.status&&($scope.form=e.item,$scope.form.title=e.item.name,$timeout(function(){setSelect2me("uniCourseId",$scope.form.uniCourseId)},100)),isSpinnerBar(!1)})};
	$scope.isPublish=function(){$scope.form.isActive=!1,(1==$scope.form.typeId||2==$scope.form.typeId)&&($scope.form.isActive=!0)};
	//Remover varios itens
	$scope.removeSelected=function(e,c,a,o,s){var l="";$scope.selected_items<1?bootbox.alert(s):(angular.forEach($scope.feedbacks,function(e,c){e.Selected===!0&&(l=l+'<b class="has-error">'+e[1]+"</b><br/>")}),bootbox.dialog({title:a,message:o+" : <p>"+l+"</p>",buttons:{success:{label:c,className:"btn-default",callback:function(){}},danger:{label:e,className:"btn-danger",callback:function(){isSpinnerBar(!0),angular.forEach($scope.feedbacks,function(e,c){e.Selected===!0&&$scope.removed(e[0])})}}}}))};
	//executa a acao de remover
	$scope.removed=function(s){isSpinnerBar(!0),FeedbackTypeSrvc.removeFeedbackType(s).then(function(e){1==e.status?($("#feedbacks-tr-"+s).addClass("hide"),PacticipActSrvc.display(e.message)):($("#feedbacks-tr-"+s).addClass("danger"),PacticipActSrvc.display(e.message,"error")),isSpinnerBar(!1)})};
	//Alterando paginacao
	$scope.pageChanged = function (){ $scope.initFeedbackType(); }
	//Select all
	$scope.checkAll=function(){$scope.selectedAll?($scope.selectedAll=!0,$scope.selected_items=$scope.totalItems):($scope.selectedAll=!1,$scope.selected_items=0),angular.forEach($scope.feedbacks,function(e){e.Selected=$scope.selectedAll})};
	//Se selecionado
	$scope.isSelected=function(e){$("#"+e).is(":checked")?$scope.selected_items++:$scope.selected_items--};
	//Init
	$scope.cleanFeedbackType();
	//Jquery + Angularjs
	$(".pa-filter").keypress(function(e){var o=e.keyCode||e.which;"13"==o&&$scope.initFeedbackType()});
	//Menu
	setMenuOpen('pa-menu-settings','pa-submenu-feedback-type');
});