/**
 * Tarefa com fotos
 */
ParticipActApp.controller('CampaignTaskPhotoCtrl', function($scope, $timeout, $http, $window, $filter, PacticipActSrvc, CampaignTaskPhotoSrvc, BASE_URL) {
	//clean
	$scope.cleanCampaignTaskPhoto=function(){$scope.campaignTaskPhoto=[],$scope.form={}};
	//Salvando
	$scope.saveCampaignTaskPhoto=function(a){isSpinnerBar(!0),$scope.form.campaign_id=a,CampaignTaskPhotoSrvc.saveCampaignTaskPhoto($scope.form).then(function(a){1==a.status?$timeout(function(){$("#fixgoback")[0].click()},600):(bootbox.alert(a.message),$timeout(function(){isSpinnerBar(!1)},500))})};
	//Edicao
	$scope.getCampaignTaskPhoto=function(){isSpinnerBar(!0),$scope.form={},PacticipActSrvc.isDebug()===!0&&PacticipActSrvc.fake($scope),isSpinnerBar(!1)};
	//Init
	$scope.cleanCampaignTaskPhoto();
	//Menu
	setMenuOpen('pa-menu-campaigns','pa-submenu-campaign-tasks');	
	//Menu
	setMenuOpen('pa-menu-campaigns','pa-submenu-campaign-tasks');	
});