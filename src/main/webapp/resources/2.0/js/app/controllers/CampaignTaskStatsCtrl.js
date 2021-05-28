/**
 * Estatisticas das tarefas
 */
ParticipActApp.controller('CampaignTaskStatsCtrl', function($scope, $timeout, $window, $filter, $localStorage, CampaignTaskStatsSrvc, BASE_URL) {
	//CLEAN
	$scope.cleanCampaignTaskStats = function() {
		$scope.stats= {ByState:{"AVAILABLE":0,"ACCEPTED":0,"RUNNING":0,"REJECTED":0,"COMPLETED":0,"NONE":0}, "TOTAL":0};
		$scope.percentage= {"AVAILABLE":0,"ACCEPTED":0,"RUNNING":0,"REJECTED":0,"COMPLETED":0,"NONE":0};
		$scope.checkUserDetails = false;
		//Pag
		$scope.totalItems = 0;
		$scope.currentPage = 0;	
		$scope.radioModel = '10';
		$scope.maxSize = 10;	
		$scope.itemsPerPage = 10;
		//Checkbox
		$scope.selected_items = 0;	
		$scope.selected_ids = [];		
		//Vars
		$scope.search = "";
		$scope.reports = [];		
		$scope.taskId = 0;
		$scope.actionId = 0;
		$scope.userId = 0;
		$scope.currentId=0;	
		$scope.checkDetails=false;
		$scope.chartConfig = {};
		$scope.hasChart = false;
		$scope.orderByColumn = 'Status';
		$scope.orderByDesc = false;		
	};
	//Iniciando o processo
	$scope.initCampaignTaskStats=function(a){$scope.getTotalByState(a),$scope.getTaskReports(a)};
	//Obtem estatisticas de user e estado por mes
	$scope.getTotalByStateAndUser = function(a){
		CampaignTaskStatsSrvc.getTotalByStateAndUser(a).then(function(res){
			if(res.status == true){
				var series = [];
				angular.forEach(res.chart.result, function(value, key) {
					var data = [];
					angular.forEach(value, function(item) {
						data.push(item==null?0:parseInt(item));
					});
					series.push({name:key, data:data});
				});
				$scope.chartConfig = { options: { chart: { type: res.chart.type, zoomType: res.chart.zoomType}},
						series: series,
						title: {text: res.chart.text},
						xAxis: {categories:res.chart.categories}
				}
				$scope.hasChart = true;
			}else{
				$scope.checkDetails = true;
			}
		});
	};
	//Collpase
	$scope.collapseDetails=function(a){$scope.checkDetails=!$scope.checkDetails};
	//TotalByState
	$scope.getTotalByState = function(a) {
	    CampaignTaskStatsSrvc.getTotalByState(a).then(function(a) {
	        1 == a.status && $timeout(function() {
	            $scope.$apply(function() {
	                angular.forEach(a.items, function(a) {
	                    value = 0 | a[1], key = a[0], $scope.stats.TOTAL = $scope.stats.TOTAL + value, 
	                    ["ACCEPTED", "RUNNING"].indexOf(key) > -1 ? $scope.stats.ByState["ACCEPTED"] = $scope.stats.ByState["ACCEPTED"] + value :["AVAILABLE", "REJECTED"].indexOf(key) > -1 ? $scope.stats.ByState[key] = $scope.stats.ByState[key] + value : ["COMPLETED_NOT_SYNC_WITH_SERVER", "COMPLETED_WITH_SUCCESS", "COMPLETED_WITH_FAILURE"].indexOf(key) > -1 ? $scope.stats.ByState.COMPLETED = $scope.stats.ByState.COMPLETED + value : $scope.stats.ByState.NONE = $scope.stats.ByState.NONE + value
	                }), angular.forEach($scope.stats.ByState, function(a, b) {
	                    $scope.percentage[b] = $scope.roundedPercentage(a, $scope.stats.TOTAL)
	                })
	            })
	        })
	    })
	};
	//Task Reports
	$scope.getTaskReports=function(a){$scope.taskId = a,isSpinnerBar(!0),CampaignTaskStatsSrvc.getListTaskReports({search:$scope.search,id:a,orderByColumn: $scope.orderByColumn, orderByDesc:$scope.orderByDesc},$scope.radioModel,$scope.currentPage).then(function(a){1==a.status&&$timeout(function(){$scope.$apply(function(){$scope.reports=a.items,$scope.totalItems=a.total,$scope.currentPage=a.offset,$scope.itemsPerPage=a.count})}),$timeout(function(){isSpinnerBar(!1)},500)})};
	//Alterando paginacao
	$scope.pageChanged = function (id){
		var taskId = typeof taskId !== 'undefined' ? taskId : $scope.taskId;
		$scope.getTaskReports(taskId); 
	}	
	//Indo para detalhes da tarefa
	$scope.gotoTaskDetails=function(a){};
	//Open modal
	$scope.openModal=function(a,b){$(".modal").modal("hide"),$scope.currentId=b,$timeout(function(){$("#full"+a).modal("show"),$("#Hey"+a).val(Date.now()/1e3|0).trigger("change")},100)};
	//Collpasse User Reports
	$scope.collapseUserDetails=function(){$scope.checkUserDetails=!$scope.checkUserDetails};
	//Infos do usuario
	$scope.gotoTaskDetails=function(a,b){isSpinnerBar(!0),$timeout(function(){$window.location.href=BASE_URL+"/protected/campaign-task-reports/user/"+a+"/"+b},500)};
	//Add Stats
	$scope.roundedPercentage = function(a, b) {if(a > 0){return (a / b * 100).toFixed(2);}else{return 0;}};
	//Select all
	//Select all
	$scope.checkAll = function () {
		if ($scope.selectedAll) {
			$scope.selectedAll = true;
		} else {
			$scope.selectedAll = false;
		}
		$scope.selected_items = 0;
		angular.forEach($scope.reports, function (item) {
			item.Selected = $scope.selectedAll;
			//console.log(item);
			if($scope.selectedAll == true){
				$scope.selected_items++;
				$scope.addSelectedItem(item[1]);
			}else{
				$scope.removeSelectedItem(item[1]);
			}
		});

	};
	//Se selecionado
	$scope.isSelected = function(id, item_id) {
		if ($('#'+id).is(':checked')) {
			$scope.selected_items++;
			$scope.addSelectedItem(item_id);
		}else{
			$scope.selected_items--;
			$scope.removeSelectedItem(item_id);
		}
	};		
	//Remove
	$scope.removeSelectedItem = function(id){
		angular.forEach($scope.selected_ids, function (item, key) {
			if(id == item[1]){
				$scope.selected_ids.splice(key, 1);
			};
		});
	};	
	//add lista de selecionados
	$scope.addSelectedItem = function(id){
		var found = false;
		angular.forEach($scope.selected_ids, function (item) {
			if(id == item[1]){
				found = true;
			};
		});

		if(found == false){
			angular.forEach($scope.reports, function (item) {
				if(item[1] == id && item[8] == true){
					$timeout(function(){
						$scope.$apply(function(){
							$scope.setSelectedItem(item);		
						});
					});
				}
			});			
		};
	};	
	//Set selected
	$scope.setSelectedItem = function(item){
		$scope.selected_ids.push(item);		
	};
	//Sincroniza com lista de selecionados
	$scope.syncSelectedItems = function(){
		angular.forEach($scope.reports, function (l) {
			angular.forEach($scope.selected_ids, function (s) {
				if(l[1] == s.id){
					l.Selected =  true;
				}
			});
		});
	};	
	//Limpando selecionados
	$scope.cleanSelectedItems = function (){
		delete $scope.selected_ids;
		$scope.selectedAll = false;
		$timeout(function(){
			$scope.selected_ids = [];
			$scope.syncSelectedUnits();
			$scope.checkAll();
		},100);
	};	
	//Remove com historico
	$scope.removeDuplicates = function(){
		var arr = $scope.selected_ids;
		angular.forEach(arr, function (item, key) {
			if(item.os.length > 0){
				$timeout(function(){ $scope.removeSelectedItem(item[1]);},10);
			};
		});
	};	
	//Routes
	$scope.getTaskRoutes = function(id, userId){
		isSpinnerBar(true);	
		userId = typeof userId !== 'undefined' ? userId : 0;	
		var listOf = [];
		angular.forEach($scope.selected_ids, function (item) {
			listOf.push({id:item[1],name:item[2]+' '+item[3],username:item[4]});
		});
		$localStorage.listOf = listOf;
		$localStorage.taskId = id;
		//Render
		$timeout(function(){				
			$window.location.href=BASE_URL+"/protected/campaign-task-routes/index/"+id+"/"+userId;
		},500);
	};
	//Order By
	$scope.setOrderByTask = function(col, taskId){
		$scope.orderByDesc = !$scope.orderByDesc;
		$scope.orderByColumn = col;
		$timeout(function(){
			$scope.getTaskReports(taskId);
		},50);		
	};
	//init
	$scope.cleanCampaignTaskStats();
});