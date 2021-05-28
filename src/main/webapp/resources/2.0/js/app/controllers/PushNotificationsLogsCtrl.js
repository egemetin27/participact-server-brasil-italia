ParticipActApp.controller('PushNotificationsLogsCtrl', function($scope, $timeout, $http, $window, $localStorage, PacticipActSrvc, PushNotificationsLogsSrvc, BASE_URL ) {
	//clean
	$scope.cleanPushNotificationsLogs = function(){
		$scope.pushies = [];
		//Pag
		$scope.totalItems = 0; $scope.currentPage = 1;	 $scope.radioModel = '10'; $scope.maxSize = 10;	 $scope.selected_items = 0; $scope.itemsPerPage = 10;
		$scope.pushNotificationsId = 0;
	};
	//Lista de itens
	$scope.initPushNotificationsLogs = function(){//Carrega todos os itens
		isSpinnerBar(true);	
		PushNotificationsLogsSrvc.getListPushNotificationsLogs({pushNotificationsId: $scope.pushNotificationsId}, $scope.radioModel, $scope.currentPage).then(function(res){
			if(res.status == true){
				$timeout(function(){
					$scope.$apply(function(){
						$scope.pushies = res.items;
						$scope.totalItems = res.total;
						$scope.currentPage = res.offset;
						$scope.itemsPerPage = res.count;
					});
				});
			}else{
				$scope.cleanPushNotificationsLogs();
			}
			$timeout(function(){ isSpinnerBar(false);}, 500);
		});
	};	
	//Alterando paginacao
	
	$scope.pageChanged = function (){
		$scope.initPushNotificationsLogs();
	}
	//Menu
	setMenuOpen('pa-menu-messaging','pa-submenu-push-notifications');
});