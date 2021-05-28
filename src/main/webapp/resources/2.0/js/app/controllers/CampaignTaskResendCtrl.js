/**
 * Campanhas
 */
ParticipActApp.controller('CampaignTaskResendCtrl', function($scope, $timeout, $http, $window, $filter, $localStorage, PacticipActSrvc, CampaignSrvc, BASE_URL) {
	//clean
	$scope.cleanCampaign=function(){
		$scope.form={isSelectAll : false};
		$scope.hashmap = [];
		$scope.taskId = null;
	};
	//Init
	$scope.initCampaignTaskResend=function(id){
		isSpinnerBar(true);
		$scope.taskId = id;
		CampaignSrvc.getCampaign(id).then(function(res) {
			if(res.status == true){
				$timeout(function(){
					$scope.form = res.item;
					$scope.form.isSelectAll = false;
					if(!isBlank($scope.form.emailSystemId)){
						setSelect2me('emailSystemId',$scope.form.emailSystemId);
					}
				},100);
			}else{
				bootbox.alert(res.message);
			}
			$timeout(function(){ isSpinnerBar(false);}, 500);
		});
	};
	
	$scope.resendEmail = function(id){
		data  = {};
		data  = $scope.form;
		data.hashmap = $scope.hashmap;
		
		isSpinnerBar(true);	
		CampaignSrvc.resendEmail(id, data).then(function(res) {
			if(res.status == true){
				PacticipActSrvc.display(res.message);
			}else{
				bootbox.alert(res.message);
			}
			isSpinnerBar(false);	
		});		
	};	
	//init
	$scope.cleanCampaign();
	//Menu
	if(!$("#isDashboard" ).length) {
		setMenuOpen('pa-menu-campaigns','pa-submenu-campaign');	
	}
});