ParticipActApp.controller('SystemEmailCtrl', function($scope, $timeout, $http, $window, $localStorage, PacticipActSrvc, SystemEmailSrvc) {
	//clean
	$scope.cleanSystemEmail = function(){
		$scope.system_emails = [];
		$scope.form = {isActive:false};
		//Pag
		$scope.totalItems = 0;
		$scope.currentEmail = 1;	
		$scope.radioModel = '10';
		$scope.maxSize = 10;	
		$scope.itemsPerEmail = 10;
		//Checkbox
		$scope.selected_items = 0;
		$scope.controller = '';
	};
	//Lista de itens
	$scope.initSystemEmail = function(){//Carrega todos os itens
		search = $scope.form;
		search.count = $scope.radioModel;
		search.offset = $scope.currentEmail;
		
		isSpinnerBar(true);	
		SystemEmailSrvc.getListSystemEmail(search, search.count, search.offset).then(function(res){
			if(res.status == true){
				$timeout(function(){
					$scope.$apply(function(){
						$scope.system_emails = res.items;
						$scope.totalItems = res.total;
						$scope.currentEmail = res.offset;
						//$scope.maxSize = res.count;
						$scope.itemsPerEmail = res.count;
					});
				});
			}else{
				$scope.cleanSystemEmail();
			}
			$timeout(function(){ isSpinnerBar(false);}, 500);
		});
	};	
	//Salvando
	$scope.saveSystemEmail = function (){
		isSpinnerBar(true);	
		SystemEmailSrvc.saveSystemEmail($scope.form).then(function(res){
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
	$scope.editSystemEmail = function(id){
		isSpinnerBar(true);	
		$timeout(function(){
			$window.location.href = "edit/"+id;
		}, 500);
	};
	
	$scope.getSystemEmail = function(){
		isSpinnerBar(true);	
		$scope.form = {};
		if(PacticipActSrvc.isDebug() === true){
			PacticipActSrvc.fake($scope);
		}	
		isSpinnerBar(false);;
	};
	
	$scope.setSystemEmail = function(j){
		isSpinnerBar(true);	
		SystemEmailSrvc.getSystemEmail(j).then(function(res){
			if(res.status == true){
				$scope.form = res.item;
				$timeout(function(){
					$("#limitPer").val(res.item.limitPer).trigger("change");	
				},5);				
			}
			isSpinnerBar(false);
		});
	};
	
	$scope.isPublish = function(){
		$scope.form.isActive= false;
		if($scope.form.typeId == 1 || $scope.form.typeId == 2){
			$scope.form.isActive= true;	
		}
	};
	//Remover varios itens
	$scope.removeSelected = function(y, n, t, m, err) {
		var html = '';
		if($scope.selected_items < 1){
			bootbox.alert(err);
		}else{
			angular.forEach($scope.system_emails, function (item, k) {
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
							angular.forEach($scope.system_emails, function (item, k) {
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
		SystemEmailSrvc.removeSystemEmail(id).then(function(res){
			if(res.status == true){
				$('#system_emails-tr-'+id).addClass('hide');
				PacticipActSrvc.display(res.message);
			}else{
				$('#system_emails-tr-'+id).addClass('danger');
				PacticipActSrvc.display(res.message,'error');
			}
			isSpinnerBar(false);
		});
	};	
	
	//Alterando paginacao
	$scope.pageChanged = function (){ $scope.initSystemEmail(); }
	//Select all
	$scope.checkAll = function () {
		if ($scope.selectedAll) {
			$scope.selectedAll = true;
			$scope.selected_items = $scope.totalItems;
		} else {
			$scope.selectedAll = false;
			$scope.selected_items = 0;
		}
		angular.forEach($scope.system_emails, function (item) {
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
	$scope.cleanSystemEmail();
	//Jquery + Angularjs
	$('.pa-filter').keypress(function(e) {
	    var k = e.keyCode || e.which;
	    if(k == '13') {
	    	$scope.initSystemEmail();    	
	    }
	});
	//Menu
	setMenuOpen('pa-menu-settings','pa-submenu-emails');
});