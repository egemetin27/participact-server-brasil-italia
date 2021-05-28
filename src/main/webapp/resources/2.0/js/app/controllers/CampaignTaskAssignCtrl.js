/**
 * Vinculando usuarios para uma campanha
 */
ParticipActApp.controller('CampaignTaskAssignCtrl', function($scope, $timeout, $http, $window, $filter, PacticipActSrvc, ParticipantSrvc , CampaignTaskAssignSrvc, BASE_URL) {
	//Constructor
	$scope.fixit_available = 0;
	$scope.selected_excluded = 0;
	$scope.list_excluded = [];
	$scope.cleanCampaignTaskAssign = function() {
		$scope.assign = {
			isSelectAll : false,
			campaign_id : null,
			assign_available : 0,
			assign_selected : 0
		};
	};
	//Save
	$scope.saveCampaignTaskAssign = function(id) {
		var data = $scope.assign;
		data.assign_filter = $scope.hashmap;
		data.assign_selected = $scope.totalItems;
		data.assign_available = $scope.fixit_available;
		isSpinnerBar(true);
		CampaignTaskAssignSrvc.saveCampaignTaskAssign(data).then(function(res) {
			if (res.status == true) {
				$timeout(function() {
					$('#fixgoback')[0].click();
				}, 600);
			} else {
				bootbox.alert(res.message);
				$timeout(function() {
					isSpinnerBar(false);
				}, 500);
			}
		});
	};
	//Find
	$scope.getCampaignTaskAssign = function(id, assign_available) {
		$scope.assign.campaign_id = id;
		$scope.assign.assign_available = assign_available || 0;
		$scope.fixit_available = assign_available;
		isSpinnerBar(true);
		CampaignTaskAssignSrvc.getCampaignExcludedList(id).then(function(excl) {
			//Excluidos			
			if (excl.status == true) {
				$scope.list_excluded = excl.items;
			}
			//Data
			CampaignTaskAssignSrvc.getCampaignTaskAssign(id).then(function(res) {
				if (res.status == true) {
					$timeout(function() {
						$scope.assign = res.item;
						$('#isSelectAll').bootstrapSwitch('state', res.item.isSelectAll);
						var assign_filter = JSON.parse(res.item.assign_filter);
						if (assign_filter.length > 0) {
							angular.forEach(assign_filter, function(map) {
								$scope.hashmap.push({
									key : map.key,
									value : map.value,
									item : map.item,
									label : "info"
								});
							});
						}
					}, 500);
				}
				$timeout(function() {
					isSpinnerBar(false);
				}, 500);
			});
		});
	};
	//Change Switch
	$scope.makeSwitchSelectAll = function() {
		$timeout(function() {
			$scope.assign.isSelectAll = $('#isSelectAll').bootstrapSwitch('state');
		});
	};
	//Se selecionado
	$scope.isExcluded = function(id, campaign_id, user_id) {
		isSpinnerBar(true);
		if ($('#' + id).is(':checked')) {
			$scope.selected_excluded++;
			$scope.setExcluded(campaign_id, user_id, true);
			$scope.list_excluded.push([0,  user_id, campaign_id, 0]);
			$scope.updateExcludedList(campaign_id);
		} else {
			$scope.selected_excluded--;
			$scope.setExcluded(campaign_id, user_id, false);
			$scope.updateExcludedList(campaign_id);
		}
	};
	//Update exclude list
	$scope.updateExcludedList = function(id){
		CampaignTaskAssignSrvc.getCampaignExcludedList(id).then(function(excl) {
			if (excl.status == true) { $scope.list_excluded = excl.items; }
			$timeout(function(){ isSpinnerBar(false);},100);
		});			
	}
	//Removendo da lista de excluidos
	$scope.setExcluded = function(campaign_id, user_id, excluded) {
		CampaignTaskAssignSrvc.setCampaignExcluded({
			campaign_id : campaign_id,
			user_id : user_id,
			excluded : excluded,
			isSelectAll : $scope.assign.isSelectAll
		}).then(function(res) {
			PacticipActSrvc.display(res.message, res.resultType);
		});
	};
	//Watch list
	$scope.$watchCollection('participants', function() {
		angular.forEach($scope.list_excluded, function(excluded) {
			angular.forEach($scope.participants, function(item) {
				if (item[0] == excluded[1]) {
					$scope.selected_excluded++;
					item.Selected = true;
				}
			});
		});
	});
	$scope.pageChanged = function (){
		if ($( "#isCampaignTaskAssignForm" ).length ) {
			search = $scope.form;
			search.hashmap = ($scope.form.isAdvancedSearch)?$scope.hashmap:[];
			search.count = $scope.radioModel;
			search.offset = $scope.currentPage;
			//console.log(search);
			isSpinnerBar(true);	
			ParticipantSrvc.getListParticipant(search, search.count, search.offset).then(function(res){
				if(res.status == true){
					$timeout(function(){
						$scope.$apply(function(){
							$scope.hashmap = res.item;
							$scope.participants = res.items;
							$scope.totalItems = res.total; $scope.currentPage = res.offset; $scope.itemsPerPage = res.count;
							$scope.totalItemsU = res.total; $scope.currentPageU = res.offset;$scope.itemsPerPageU = res.count;
						});
					});
					if(search.isCloudDownload==true){ PacticipActSrvc.display(res.message, res.resultType, 10); }
				}else{
					$scope.cleanParticipant();
				}
				$timeout(function(){ isSpinnerBar(false);}, 500);
			});				
		}
	}	
	//INIT
	$scope.cleanCampaignTaskAssign();
	//Switch
	$('#isSelectAll').on('switchChange.bootstrapSwitch', function() {
		$scope.makeSwitchSelectAll();
	});
	$('#isSelectAll').bootstrapSwitch('state', false);
	//Menu
	setMenuOpen('pa-menu-campaigns', 'pa-submenu-campaign-tasks');
});