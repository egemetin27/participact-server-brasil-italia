/**
 * Barra de notificacao
 */
ParticipActApp.controller('NotificationBarCtrl', function($scope, $timeout, $http, $interval, PacticipActSrvc, NotificationBarSrvc) {
	//var
	$scope.header_notification_messages = [];
	$scope.header_notification_counter = 1;
	$scope.header_notification_timer = 1000;
	$scope.header_notification_unread = false;
	$scope.header_notification_badge = 0;
	//stop
	var stop;
	//start
	$scope.startBell = function() {
		if(PacticipActSrvc.isBell()===true){
			NotificationBarSrvc.getListNotificationBar({unread:$scope.header_notification_unread}).then(function(res){
				try{
					if(res.status == true){
						if(res.total > 0){
							$scope.unreadAll();
							$scope.header_notification_messages = res.items;
							angular.forEach(res.items, function(item) {
								if(item[1] == false){
									$scope.resetTimer();
									$scope.header_notification_unread = true;
									PacticipActSrvc.display(item[0], item[4]);
								}
							});
						}
						$scope.header_notification_badge = res.total;
					}
					$timeout(function(){ $scope.incrementTimer(); },1000);
					$timeout(function(){ $scope.startBell(); },$scope.header_notification_timer);
				}catch(err){ console.log(err);}
			});
		}
	};	
	//unread
	$scope.unreadAll = function(){
		NotificationBarSrvc.setUnreadAll().then(function(res){
//			console.log(res);
		});
	};
	//stop
	$scope.stopBell = function() {
		if (angular.isDefined(stop)) {
			$interval.cancel(stop);
			stop = undefined;
		}
	};	
	//incrmenet
	$scope.incrementTimer = function(){
		$scope.header_notification_timer = ($scope.header_notification_counter < 30) ? ($scope.header_notification_counter++ * 1000) : 1000 * 30;
	};
	//reset
	$scope.resetTimer = function() {
		$scope.header_notification_counter = 1;
		$scope.header_notification_timer = 1000;
	};
});