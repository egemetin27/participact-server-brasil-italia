ParticipActApp.controller('InstitutionsCtrl', function($scope, $timeout, $http, $window, $localStorage, PacticipActSrvc, InstitutionsSrvc) {
	//clean
	$scope.cleanInstitutions = function(){
		$scope.institutions = [];
		$scope.form = {};
		//Pag
		$scope.totalItems = 0;
		$scope.currentPage = 1;	
		$scope.radioModel = '10';
		$scope.maxSize = 10;	
		$scope.itemsPerPage = 10;
		//Checkbox
		$scope.selected_items = 0;
	};
	//Lista de itens
	$scope.initInstitutions = function(){//Carrega todos os itens
		search = $scope.form;
		search.count = $scope.radioModel;
		search.offset = $scope.currentPage;
		
		isSpinnerBar(true);	
		InstitutionsSrvc.getListInstitutions(search, search.count, search.offset).then(function(res){
			if(res.status == true){
				$timeout(function(){
					$scope.$apply(function(){
						$scope.institutions = res.items;
						$scope.totalItems = res.total;
						$scope.currentPage = res.offset;
						//$scope.maxSize = res.count;
						$scope.itemsPerPage = res.count;
					});
				});
			}else{
				$scope.cleanInstitutions();
			}
			$timeout(function(){ isSpinnerBar(false);}, 500);
		});
	};	
	//Salvando
	$scope.saveInstitutions = function (){
		isSpinnerBar(true);	
		InstitutionsSrvc.saveInstitutions($scope.form).then(function(res){
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
	$scope.editInstitutions = function(id){
		isSpinnerBar(true);	
		$timeout(function(){
			$window.location.href = "edit/"+id;
		}, 500);
	};
	
	$scope.getInstitutions = function(){
		isSpinnerBar(true);	
		$scope.form = {};
		if(PacticipActSrvc.isDebug() === true){
			PacticipActSrvc.fake($scope);
		}	
		isSpinnerBar(false);;
	};
	
	$scope.setInstitutions = function(j){
		isSpinnerBar(true);	
		InstitutionsSrvc.getInstitutions(j).then(function(res){
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
			angular.forEach($scope.institutions, function (item, k) {
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
							angular.forEach($scope.institutions, function (item, k) {
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
		InstitutionsSrvc.removeInstitutions(id).then(function(res){
			if(res.status == true){
				$('#institutions-tr-'+id).addClass('hide');
				PacticipActSrvc.display(res.message);
			}else{
				$('#institutions-tr-'+id).addClass('danger');
				PacticipActSrvc.display(res.message,'error');
			}
			isSpinnerBar(false);
		});
	};	
	
	//Alterando paginacao
	$scope.pageChanged = function (){ $scope.initInstitutions(); }
	//Select all
	$scope.checkAll = function () {
		if ($scope.selectedAll) {
			$scope.selectedAll = true;
			$scope.selected_items = $scope.totalItems;
		} else {
			$scope.selectedAll = false;
			$scope.selected_items = 0;
		}
		angular.forEach($scope.institutions, function (item) {
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
	$scope.cleanInstitutions();
	//Jquery + Angularjs
	$('.pa-filter').keypress(function(e) {
	    var k = e.keyCode || e.which;
	    if(k == '13') {
	    	$scope.initInstitutions();    	
	    }
	});
	//Menu
	setMenuOpen('pa-menu-settings','pa-submenu-institutions');
});