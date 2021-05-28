ParticipActApp.controller('SystemPageCtrl', function($scope, $timeout, $http, $window, $localStorage, PacticipActSrvc, SystemPageSrvc) {
	//clean
	$scope.cleanSystemPage = function(){
		$scope.system_pages = [];
		$scope.form = {isActive:false};
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
	$scope.initSystemPage = function(){//Carrega todos os itens
		search = $scope.form;
		search.count = $scope.radioModel;
		search.offset = $scope.currentPage;
		
		isSpinnerBar(true);	
		SystemPageSrvc.getListSystemPage(search, search.count, search.offset).then(function(res){
			if(res.status == true){
				$timeout(function(){
					$scope.$apply(function(){
						$scope.system_pages = res.items;
						$scope.totalItems = res.total;
						$scope.currentPage = res.offset;
						//$scope.maxSize = res.count;
						$scope.itemsPerPage = res.count;
					});
				});
			}else{
				$scope.cleanSystemPage();
			}
			$timeout(function(){ isSpinnerBar(false);}, 500);
		});
	};	
	//Salvando
	$scope.saveSystemPage = function (){
		isSpinnerBar(true);	
		SystemPageSrvc.saveSystemPage($scope.form).then(function(res){
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
	$scope.editSystemPage = function(id){
		isSpinnerBar(true);	
		$timeout(function(){
			$window.location.href = "edit/"+id;
		}, 500);
	};
	
	$scope.getSystemPage = function(){
		isSpinnerBar(true);	
		$scope.form = {};
		if(PacticipActSrvc.isDebug() === true){
			PacticipActSrvc.fake($scope);
		}	
		isSpinnerBar(false);;
	};
	
	$scope.setSystemPage = function(j){
		isSpinnerBar(true);	
		SystemPageSrvc.getSystemPage(j).then(function(res){
			if(res.status == true){
				$scope.form = res.item;
				$timeout(function(){
					if($scope.form.type == 'PAGE_FAQ'){
						$scope.form.typeId = 1;
						setSelect2me('type_id',1);
					}else if($scope.form.type == 'PAGE_ABOUT'){
						$scope.form.typeId = 2;
						setSelect2me('type_id',2);
					}else{
						$scope.form.typeId = 0;
						setSelect2me('type_id',0);
					}
				},100);
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
			angular.forEach($scope.system_pages, function (item, k) {
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
							angular.forEach($scope.system_pages, function (item, k) {
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
		SystemPageSrvc.removeSystemPage(id).then(function(res){
			if(res.status == true){
				$('#system_pages-tr-'+id).addClass('hide');
				PacticipActSrvc.display(res.message);
			}else{
				$('#system_pages-tr-'+id).addClass('danger');
				PacticipActSrvc.display(res.message,'error');
			}
			isSpinnerBar(false);
		});
	};	
	
	//Alterando paginacao
	$scope.pageChanged = function (){ $scope.initSystemPage(); }
	//Select all
	$scope.checkAll = function () {
		if ($scope.selectedAll) {
			$scope.selectedAll = true;
			$scope.selected_items = $scope.totalItems;
		} else {
			$scope.selectedAll = false;
			$scope.selected_items = 0;
		}
		angular.forEach($scope.system_pages, function (item) {
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
	$scope.cleanSystemPage();
	//Jquery + Angularjs
	$('.pa-filter').keypress(function(e) {
	    var k = e.keyCode || e.which;
	    if(k == '13') {
	    	$scope.initSystemPage();    	
	    }
	});
	//Menu
	setMenuOpen('pa-menu-settings','pa-submenu-pages');
});