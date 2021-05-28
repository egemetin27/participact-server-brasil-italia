ParticipActApp.controller('SystemBackupCtrl', function($scope, $timeout, $http, $window, $localStorage, PacticipActSrvc, SystemBackupSrvc) {
	//clean
	$scope.cleanSystemBackup = function(){
		$scope.system_backups = [];
		$scope.form = {count:1, offset:0};
		//Pag
		$scope.totalItems = 0;
		$scope.currentPage = 1;	
		$scope.radioModel = '10';
		$scope.maxSize = 10;	
		$scope.itemsPerPage = 10;
		//Checkbox
		$scope.selected_items = 0;
		$scope.controller = '';
	};
	//Lista de itens
	$scope.initSystemBackup = function(){//Carrega todos os itens
		search = $scope.form;
		search.count = $scope.radioModel;
		search.offset = $scope.currentPage;
		
		isSpinnerBar(true);	
		SystemBackupSrvc.getListSystemBackup(search, search.count, search.offset).then(function(res){
			if(res.status == true){
				$timeout(function(){
					$scope.$apply(function(){
						$scope.system_backups = res.items;
						$scope.totalItems = res.total;
						$scope.currentPage = res.offset;
						//$scope.maxSize = res.count;
						$scope.itemsPerPage = res.count;
					});
				});
			}else{
				$scope.cleanSystemBackup();
			}
			$timeout(function(){ isSpinnerBar(false);}, 500);
		});
	};	
	//Salvando
	$scope.saveSystemBackup = function (){
		isSpinnerBar(true);	
		SystemBackupSrvc.saveSystemBackup($scope.form).then(function(res){
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
	//Edicao
	$scope.editSystemBackup = function(id){
		isSpinnerBar(true);	
		$timeout(function(){
			$window.location.href = "edit/"+id;
		}, 500);
	};
	
	$scope.getSystemBackup = function(){
		isSpinnerBar(true);	
		$scope.form = {};
		if(PacticipActSrvc.isDebug() === true){
			PacticipActSrvc.fake($scope);
		}	
		isSpinnerBar(false);;
	};
	
	$scope.setSystemBackup = function(j){
		isSpinnerBar(true);	
		SystemBackupSrvc.getSystemBackup(j).then(function(res){
			if(res.status == true){
				$scope.form = res.item;
			}
			isSpinnerBar(false);
		});
	};
	//Remover varios itens
	$scope.removeSelected = function(y, n, t, m, err) {
		var html = '';
		if($scope.selected_items < 1){
			bootbox.alert(err);
		}else{
			angular.forEach($scope.system_backups, function (item, k) {
				if(item.Selected === true){
					html = html +'<b class="has-error">'+item[1] +'</b><br/>';
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
							angular.forEach($scope.system_backups, function (item, k) {
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
		SystemBackupSrvc.removeSystemBackup(id).then(function(res){
			if(res.status == true){
				$('#system_backups-tr-'+id).addClass('hide');
				PacticipActSrvc.display(res.message);
			}else{
				$('#system_backups-tr-'+id).addClass('danger');
				PacticipActSrvc.display(res.message,'error');
			}
			isSpinnerBar(false);
		});
	};	
	
	//Alterando paginacao
	$scope.pageChanged = function (){ $scope.initSystemBackup(); }
	//Select all
	$scope.checkAll = function () {
		if ($scope.selectedAll) {
			$scope.selectedAll = true;
			$scope.selected_items = $scope.totalItems;
		} else {
			$scope.selectedAll = false;
			$scope.selected_items = 0;
		}
		angular.forEach($scope.system_backups, function (item) {
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
	$scope.cleanSystemBackup();
	//Jquery + Angularjs
	$('.pa-filter').keypress(function(e) {
	    var k = e.keyCode || e.which;
	    if(k == '13') {
	    	$scope.initSystemBackup();    	
	    }
	});
	//Menu
	setMenuOpen('pa-menu-settings','pa-submenu-backups');
});