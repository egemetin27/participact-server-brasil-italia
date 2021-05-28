ParticipActApp.controller('MailNotificationsCtrl', function($scope, $timeout) {
	//clean
	$scope.cleanMailNotifications = function(){
		$scope.items = [];
		//Pag
		if (! $( "#isMailNotification" ).length ) {
			$scope.totalItems = 0; $scope.currentPage = 1;	 $scope.radioModel = '10'; $scope.maxSize = 10;	 $scope.selected_items = 0; $scope.itemsPerPage = 10;
		};
		//Forcando reconhecer o filtro
		$scope.paNotification = null;
		$scope.paMessage = null;
		$scope.paIsPublish = false;
	};
	//Init
	$scope.initMailNotifications = function(){
		console.log("initMailNotifications");
	};
	//Remover varios itens
	$scope.removeSelected=function(e,a,c,l,o){var s="";$scope.selected_items<1?bootbox.alert(o):(angular.forEach($scope.items,function(e,a){!0===e.Selected&&(s=s+'<b class="has-error">'+e[1]+"</b><br/>")}),bootbox.dialog({title:c,message:l+" : <p>"+s+"</p>",buttons:{success:{label:a,className:"btn-default",callback:function(){}},danger:{label:e,className:"btn-danger",callback:function(){isSpinnerBar(!0),angular.forEach($scope.items,function(e,a){!0===e.Selected&&$scope.removed(e[0])})}}}}))};
	//executa a acao de remover
	$scope.removed=function(i){isSpinnerBar(!0),MailNotificationsSrvc.removeMailNotifications(i).then(function(a){1==a.status?($("#email-tr-"+i).addClass("hide"),PacticipActSrvc.display(a.message)):($("#email-tr-"+i).addClass("danger"),PacticipActSrvc.display(a.message,"error")),isSpinnerBar(!1)})};
	//Alterando paginacao	
	$scope.pageChanged=function(){$scope.initMailNotifications()};
	//Select all
	$scope.checkAll=function(){$scope.selectedAll?($scope.selectedAll=!0,$scope.selected_items=$scope.totalItems):($scope.selectedAll=!1,$scope.selected_items=0),angular.forEach($scope.items,function(e){e.Selected=$scope.selectedAll})};
	//Se selecionado
	$scope.isSelected=function(e){$("#"+e).is(":checked")?$scope.selected_items++:$scope.selected_items--};
	//Init
	$scope.cleanMailNotifications();
	//Menu
	setMenuOpen('pa-menu-messaging','pa-submenu-mail-notifications');
});