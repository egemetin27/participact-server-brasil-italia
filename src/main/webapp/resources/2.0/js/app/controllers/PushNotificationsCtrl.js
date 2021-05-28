ParticipActApp.controller('PushNotificationsCtrl', function($scope, $timeout, $http, $window, $localStorage, PacticipActSrvc, PushNotificationsSrvc, BASE_URL ) {
	//clean
	$scope.cleanPushNotifications = function(){
		$scope.pushies = [];
		$scope.form = {};
		//Pag
		if (! $( "#isPushNotification" ).length ) {
			$scope.totalItems = 0; $scope.currentPage = 1;	 $scope.radioModel = '10'; $scope.maxSize = 10;	 $scope.selected_items = 0; $scope.itemsPerPage = 10;
		};
		//Fornando reconhecer o filtro
		$scope.paNotification = null;
		$scope.paMessage = null;
		$scope.paIsPublish = false;
	};
	//Lista de itens
	$scope.initPushNotifications = function(){//Carrega todos os itens
		isSpinnerBar(true);	
		PushNotificationsSrvc.getListPushNotifications({isMail:($('#portlet-email-notifications').length>0)}, $scope.radioModel, $scope.currentPage).then(function(res){
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
				$scope.cleanPushNotifications();
			}
			$timeout(function(){ isSpinnerBar(false);}, 500);
		});
	};	
	//Detailhes
	$scope.detailes = function(id, isPublish){
		isSpinnerBar(true);	
		$timeout(function(){
			if(isPublish){
				$window.location.href = BASE_URL+"/protected/push-notifications-logs/index/"+id;
			}else{
				$window.location.href = BASE_URL+"/protected/push-notifications/form/"+id;
			}
		}, 500);
	};
	//Salvando
	$scope.savePushNotifications = function (pushId){
		isSpinnerBar(true);	
		PushNotificationsSrvc.savePushNotifications({pushId: pushId, 
			paNotification:$scope.paNotification,
			hashmap:$scope.hashmap, 
			paMessage:$scope.paMessage, 
			isPublish: $scope.paIsPublish, 
			emailBody: $scope.form.emailBody||null,
			emailSubject: $scope.form.emailSubject||null,
			emailSystemId: $scope.form.emailSystemId||null,
			}).then(function(res){
			if(res.status == true){
				$timeout(function(){
					$('#fixgoback')[0].click();
				},600);
			}else{
				bootbox.alert(res.message);
				$timeout(function(){ isSpinnerBar(false);}, 500);
			}
		});
	};
	$scope.publishPushNotifications = function(pushId){
		$scope.paIsPublish = true;
		$timeout(function(){
			$scope.savePushNotifications(pushId);
		},100);
	};
	//Remover varios itens
	$scope.removeSelected = function(y, n, t, m, err) {
		var html = '';
		if($scope.selected_items < 1){
			bootbox.alert(err);
		}else{
			angular.forEach($scope.pushies, function (item, k) {
				if(item.Selected === true){
					html = html +'<b class="has-error">'+item[11] +'</b><br/>';
				}
			});

			bootbox.dialog({
				title: t,
				message: m+' : <p>'+html+'</p>',
				buttons: {
					success: {
						label: n,
						className: "btn-default",
						callback: function() {}
					},
					danger: {
						label: y,
						className: "btn-danger",
						callback: function() {
							isSpinnerBar(true);
							angular.forEach($scope.pushies, function (item, k) {
								if(item.Selected === true){
									$scope.removed(item[0]);
								}
							});
						}
					},				
				}
			});	
		}
	};  
	//executa a acao de remover
	$scope.removed = function(id){
		isSpinnerBar(true);
		PushNotificationsSrvc.removePushNotifications(id).then(function(res){
			if(res.status == true){
				$('#pushies-tr-'+id).addClass('hide');
				PacticipActSrvc.display(res.message);
			}else{
				$('#pushies-tr-'+id).addClass('danger');
				PacticipActSrvc.display(res.message,'error');
			}
			isSpinnerBar(false);
		});
	};	
	
	//Alterando paginacao	
	$scope.pageChanged = function (){
		$scope.initPushNotifications();
	}
	//Select all
	$scope.checkAll = function () {
		if ($scope.selectedAll) {
			$scope.selectedAll = true;
			$scope.selected_items = $scope.totalItems;
		} else {
			$scope.selectedAll = false;
			$scope.selected_items = 0;
		}
		angular.forEach($scope.pushies, function (item) {
			item.Selected = $scope.selectedAll;
		});

	};
	//Se selecionado
	$scope.isSelected = function(id) {
		if ($('#'+id).is(':checked')) {
			$scope.selected_items++;
		}else{
			$scope.selected_items--;
		}
	};	
	//Init
	$scope.cleanPushNotifications();
	if($('#portlet-email-notifications').length){
		$('.page-content').css({ 'height': 1500 + "px" });
	}
	//Menu
	setMenuOpen('pa-menu-messaging','pa-submenu-push-notifications');
});