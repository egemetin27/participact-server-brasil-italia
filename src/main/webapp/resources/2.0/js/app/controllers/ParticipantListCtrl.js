ParticipActApp.controller('ParticipantListCtrl', function($scope, $timeout, $http, $window, $localStorage, PacticipActSrvc, ParticipantSrvc, ParticipantListSrvc, CampaignSrvc, BASE_URL) {
	//clean
	$scope.cleanParticipantList = function(){
		$scope.itemList = [];
		$scope.userList = [];
		$scope.hashmap = [];
		$scope.taskList = [];
		$scope.userSearch = null;
		$scope.filter = {name:null,value:'',item:'',input:'TEXT', campaignId:null};	
		$scope.audienceSelector = false;
		//Pag
		$scope.totalItems = 0; $scope.currentPage = 0;	 $scope.radioModel = '1000'; $scope.maxSize = 1000; $scope.deselectedItemsPerPage = 1000;
		$scope.deselectedTotalItems = 0; $scope.deselectedCurrentPage = 0;	 $scope.deselectedRadioModel = '1000'; $scope.deselectedMaxSize = 1000; $scope.deselectedItemsPerPage = 1000;
		//Checkbox
		$scope.selectedItems = 0;
		$scope.deselectedItems = 0;
	};
	/**
	 * Pesquisa
	 */
	$scope.initParticipantList = function(taskId){
		isSpinnerBar(true);	
		$localStorage.$reset();
		$timeout(function(){
			search = {isActive:false, isAdvancedSearch: true, filterBy: null, isCloudDownload: false, taskId:(typeof taskId !== 'undefined' ? taskId : 0), hashmap:$scope.hashmap, count:$scope.radioModel, offset:$scope.currentPage};
			$scope.taskId = search.taskId;
			//console.log(search);
			ParticipantSrvc.getListParticipant(search, search.count, search.offset).then(function(res){
				if(res.status == true){
					$timeout(function(){
						$scope.$apply(function(){
							$scope.userList = res.items;
							$scope.totalItems = res.total; $scope.currentPage = res.offset; $scope.itemsPerPage = res.count;
						});
					});
				}
				$timeout(function(){ isSpinnerBar(false);}, 500);
			});
		}, 500);		
	};
	/**
	 * Save
	 */
	$scope.saveParticipantList = function(userListId){
		isSpinnerBar(true);	
		ParticipantListSrvc.saveParticipantList({userListId: userListId, audienceSelector: $scope.audienceSelector, hashmap:$scope.hashmap}).then(function(res){
			if(res.status == true){
				$timeout(function(){
					$window.location.href = BASE_URL + res.message;
				}, 500);
			}else{
				$timeout(function(){ isSpinnerBar(false);}, 500);	
			}			
		});
	};
	/***
	 * Busca
	 */
	$scope.initCloudSearch = function(){
		$timeout(function(){
			$scope.initParticipantList($scope.taskId);
		},100);
	};	
	/**
	 * Filtro
	 */
	//Adiciona um filtro
	$scope.addFilterBy = function(){
		if(!isBlank($scope.form.filterBy) && !isBlank($scope.filter.value)){
			$timeout(function(){
				$scope.hashmap.push({key:$scope.form.filterBy,value:$scope.filter.value,item:$scope.filter.item,label:"FILTER_TASKID_RUNNING"==$scope.form.filterBy?"warning":"info"});				
				$scope.form.filterBy = '';
				$scope.filter.value = '';
				$scope.filter.input='TEXT';
				$scope.filter.item = '';
				setSelect2me('filterBy',0);
				$scope.initCloudSearch();
				$scope.form.campaign = {};
			},100);
		}
	};		
	//Remove um filtro
	$scope.removeFilterBy=function(key){
		if(key>-1){
			$scope.hashmap.splice(key,1);
		}
		$timeout(function(){
			$scope.initCloudSearch();
		},100);
	};
	//Alterando o tipo do filtro
	$scope.onChangeFilterBy=function(){
		var T=$scope.form.filterBy;
		if(!isBlank(T)){
			if(["FILTER_GENDER","FILTER_UNICOURSE","FILTER_SCHOOLCOURSEID","FILTER_INSTITUTIONID","FILTER_DOCUMENTIDTYPE","FILTER_TASKID_RUNNING","FILTER_TASKID"].includes(T)){
				$scope.filter.input = T;
			}else if(["FILTER_START","FILTER_DEADLINE"].includes(T)){
				$scope.filter.input = "DATEPICKER";
			}else{
				$scope.filter.input = "TEXT";
			}
		}else{
			$scope.filter.input = "TEXT";
		}
	};
	//Selected filter by index
	$scope.onChangeFilterSelected=function(key){
		$scope.filter.value=$("#"+key+" option:selected").text();
		$scope.filter.item=$scope.form[key];
	};
	//FILTER GENDER
	$scope.onChangeGender=function(){
		$timeout(function(){
			$scope.onChangeFilterSelected("gender");
		},100);
	};
	//Filter FILTER_UNICOURSE
	$scope.onChangeUniCourse=function(){
		$timeout(function(){
			$scope.onChangeFilterSelected("uniCourse");
		},100);
	};
	//FILTER_SCHOOLCOURSEID
	$scope.onChangeSchoolCourse=function(){
		$timeout(function(){
			$scope.onChangeFilterSelected("schoolCourseId");
		},100);
	};
	//FILTER_INSTITUTIONID
	$scope.onChangeInstitutionId=function(){
		$timeout(function(){
			$scope.onChangeFilterSelected("institutionId");
		},100);
	};
	//FILTER_DOCUMENTIDTYPE
	$scope.onChangeDocumentIdType=function(){
		$timeout(function(){
			$scope.onChangeFilterSelected("documentIdType");
		},100);
	};	
	//FILTER_CAMPAING
	$scope.onChangeCampaignId=function(){
		$timeout(function(){
			$scope.onChangeFilterSelected("campaignId");
		},100);
	};
	$scope.OnSelectedCampaignId = function(item){
		//console.log('OnSelectedCampaignId');
		//console.log(item);		
		$scope.filter.value=item[1];
		$scope.filter.item=item[0];
		$scope.filter.label = "warning";
	};
	$scope.OnClickCampaignId = function(){
		var T=$scope.form.filterBy;
		if(!isBlank(T)){
			if(["FILTER_TASKID"].includes(T)){
				$scope.form.filterBy = "FILTER_TASKID_RUNNING";
			}else{
				$scope.form.filterBy = "FILTER_TASKID";
			}
			$timeout(function(){
				$scope.onChangeFilterBy();
			},100);
		}
	};
	//SEARCH CAMPAIGN
	$scope.onAsyncSelectedCampaign = function(value){
		isSpinnerBar(true);
		// POST
		CampaignSrvc.getListCampaign({name: value, isActive:false,isAdvancedSearch:false,filterBy:null,isCloudDownload:false,sensingDuration:86400,orderByColumn:"Status",orderByDesc:false,hashmap:[],count:100,offset:1}, 100, 0).then(function(res){
			if(res.status == true){
				$scope.taskList = res.items;
			}
			isSpinnerBar(false);
		});	
	};
	/**
	 * Paginacao
	 */
	$scope.pageChanged = function (){ 
		$scope.initParticipantList($scope.taskId); 
	};
	/**
	 * Checkbox
	 */
	//Select all
	$scope.checkAll = function () {
		if ($scope.selectedAll) {
			$scope.selectedAll = true;
			$scope.selectedItems = $scope.userList.length;
		} else {
			$scope.selectedAll = false;
			$scope.selectedItems = 0;
		}
		angular.forEach($scope.userList, function (item) {
			item.Selected = $scope.selectedAll;
		});
	};
	//Se selecionado
	$scope.isSelected = function(id) {
		if ($('#'+id).is(':checked')) {
			$scope.selectedItems++;
		}else{
			$scope.selectedItems--;
		}
	}
	$scope.deselectCheckAll = function () {
		if ($scope.deselectedAll) {
			$scope.deselectedAll = true;
			$scope.deselectedItems = $scope.itemList.length;
		} else {
			$scope.deselectedAll = false;
			$scope.deselectedItems = 0;
		}
		angular.forEach($scope.itemList, function (item) {
			item.Selected = $scope.deselectedAll;
		});
	};
	//Se selecionado na lista de items
	$scope.isDeselected = function(id) {
		if ($('#'+id).is(':checked')) {
			$scope.deselectedItems++;			
		}else{
			$scope.deselectedItems--;
		}		
	}		
	//add item list
	$scope.addItemList = function(userListId){
		var userListId = (typeof userListId !== 'undefined' ? userListId : 0);
		var tempList = $scope.userList;
		//Added
		angular.forEach(tempList, function (item, index) {			
			if(item.Selected){
				item.isLoading = true;
				ParticipantListSrvc.addUserItem({userId:item[0], userListId: userListId}).then(function(res){
					if(res.status == true){
						item.userListItemId = res.outcome;
						item.Selected = false;
						item.Contains = false;
						$scope.selectedItems--;
						//Inserindo
						angular.forEach($scope.itemList, function (choose) {
							if(item[0]==choose[0]){
								item.Contains = true;		
							}
						});
						if(item.Contains == false){
							$scope.itemList.push(item);
						}
						//Remove old list
						angular.forEach($scope.userList, function (user, key) {
							if(item[0]==user[0]){
								$scope.userList.splice(key, 1);
							}
						});
					}	
					item.isLoading = false;
				});	
			}
		});
		//Uncheck
		$timeout(function(){
			$scope.deselectedAll = false;
			$scope.selectedAll = false;
		},100);
	};
	//remove item list
	$scope.removeItemList = function(userListId){
		var userListId = (typeof userListId !== 'undefined' ? userListId : 0);
		var tempList = $scope.itemList;
		//Removed
		angular.forEach(tempList, function (item, index) {			
			if(item.Selected){
				item.isLoading = true;
				ParticipantListSrvc.removeUserItem({userId:item[0], userListId: userListId}).then(function(res){
					if(res.status == true){
						item.Selected = false;
						$scope.userList.push(item);
						$scope.deselectedItems--;
						//Remove old list
						angular.forEach($scope.itemList, function (user, key) {
							if(item[0]==user[0]){
								$scope.itemList.splice(key, 1);
							}
						});
					}
					item.isLoading = false;
				});	
			}
		});		
		//Uncheck
		$timeout(function(){
			$scope.deselectedAll = false;
			$scope.selectedAll = false;
		},100);
	};
	/**
	 * Carregando lista
	 */
	$scope.getListParticipantList = function(taskId, userListId){
		var taskId = (typeof taskId !== 'undefined' ? taskId : 0);
		var userListId = (typeof userListId !== 'undefined' ? userListId : 0);
		if(userListId>0){
			isSpinnerBar(true);
			//UserList
			ParticipantListSrvc.getParticipantList(userListId).then(function(res){
				if(res.status == true){
					$scope.audienceSelector = res.item.audienceSelector;
					if($scope.audienceSelector == 'SELECTOR_ALL'){//Todos
						
						$timeout(function(){ isSpinnerBar(false);}, 500);
					}else if($scope.audienceSelector == 'SELECTOR_RESTRICTED'){//Registro
						var hashmap = JSON.parse(res.item.hashmap);
						if (hashmap.length > 0) {
							angular.forEach(hashmap, function(map) {
								$scope.hashmap.push({
									key : map.key,
									value : map.value,
									item : map.item,
									label : "info"
								});
							});
							
							$timeout(function(){
								$scope.initCloudSearch();
							},100);							
						}
						//$timeout(function(){ isSpinnerBar(false);}, 500);
					}else if($scope.audienceSelector == 'SELECTOR_CLOSED'){//Fechado
						//Itens
						ParticipantListSrvc.getListParticipantList({taskId:taskId, userListId:userListId}, 2000, 0).then(function(res){
							if(res.status == true){
								$timeout(function(){
									$scope.$apply(function(){
										$scope.itemList = res.items;
										$scope.deselectedTotalItems = res.total; $scope.deselectedCurrentPage = res.offset; $scope.deselectedItemsPerPage = res.count;
									});
								});
							}
							$timeout(function(){ isSpinnerBar(false);}, 500);
						});
					}else{
						$timeout(function(){ isSpinnerBar(false);}, 500);
					}
				}else{
					$timeout(function(){ isSpinnerBar(false);}, 500);
				}
			});
		}
	};
	/**
	 * Init 
	 */
	$scope.cleanParticipantList();
	//Jquery + Angularjs
	$(".pa-advanced").keypress(function(a){
		var b=a.keyCode||a.which;
		if("13"==b){
			$scope.addFilterBy();
		}
	});	
	//Ajustes
	$('#filter_campaign').removeClass('hide').addClass('show');
});