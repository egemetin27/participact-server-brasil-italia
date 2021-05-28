/**
 * Tarefas das campanhas
 */
ParticipActApp.controller('CampaignTaskCtrl', function($scope, $timeout, $http, $window, $filter, PacticipActSrvc, CampaignTaskSrvc, CampaignTaskGeoDrawingSrvc, CampaignTaskAssignSrvc,  BASE_URL) {
	//clean
    $scope.campaignTasks= {checkDetails:true, hasActions: false, hasActivityDetection: false, hasPhotos: false, hasNotificationArea: false, hasActivationArea:false, actions:[]};
	//Constructor
	$scope.initCampaignTasks = function(){ $scope.campaignTasks.hasActions = false;};
	//Passive Sensing
	$scope.gotoCampaignTaskForm =  function(f, i){isSpinnerBar(!0),$timeout(function(){$window.location.href=BASE_URL+"/protected/campaign-task-"+f+"/form/"+i},500)};
	//Remover varios itens
	$scope.removeSelected=function(a,b,c,d,e,f){var g="";angular.forEach($scope.campaignTasks.actions,function(b,c){if(b.id==a){var d=isBlank(b.name)?"":b.name;g=g+" <b>"+d+"</b><br/><small class='help-block'>"+b.translated+"</small>"}}),bootbox.dialog({title:d,message:e+" : <p>"+g+"</p>",buttons:{success:{label:c,className:"btn-default",callback:function(){}},danger:{label:b,className:"btn-danger",callback:function(){$scope.removed(a)}}}})};
	//executa a acao de remover
	$scope.removed=function(a){isSpinnerBar(!0),CampaignTaskSrvc.removeCampaignTasks(a).then(function(b){1==b.status?($("#campaign-task-"+a).addClass("hide"),PacticipActSrvc.display(b.message),$("#HeyMan").trigger('change')):($("#campaign-task-"+a).addClass("danger"),PacticipActSrvc.display(b.message,"error")),isSpinnerBar(!1)})};	
	//Remove uma area
	$scope.removeSelectedArea = function(isNotification, k ,id){
	    isSpinnerBar(!0), CampaignTaskGeoDrawingSrvc.removeTaskGeoDrawing({isNotification:isNotification, area:k, campaign_id:id}).then(function(b) {
	        1 == b.status ? ($("#campaign-task-area-" + k).addClass("hide"), 
	        		PacticipActSrvc.display(b.message)) : ($("#campaign-task-area-" + k).addClass("danger"), PacticipActSrvc.display(b.message, "error")), isSpinnerBar(!1)
	    })
	};
	$scope.copySelectedArea = function (isNotification, k, id){
	    isSpinnerBar(!0), CampaignTaskGeoDrawingSrvc.copyTaskGeoDrawing({isNotification:isNotification, area:k, campaign_id:id}).then(function(b) {
	        1 == b.status ? (PacticipActSrvc.display(b.message)) : (PacticipActSrvc.display(b.message, "error")), isSpinnerBar(!1), $("#HeyMan").trigger('change')
	    })		
	};
	$scope.removeSelectedAssign = function(id){
	    isSpinnerBar(!0), CampaignTaskAssignSrvc.removeSelectedAssign(id).then(function(b) {
	        1 == b.status ? (PacticipActSrvc.display(b.message)) : (PacticipActSrvc.display(b.message, "error")), isSpinnerBar(!1), $("#HeyMan").trigger('change')
	    })		
	};
	//Menu
	setMenuOpen('pa-menu-campaigns','pa-submenu-campaign-tasks');	
});