/**
 * Tarefa com sensores passivo
 */
ParticipActApp.controller('CampaignTaskSensingCtrl', function($scope, $timeout, $http, $window, $filter, PacticipActSrvc, CampaignTaskSensingSrvc, CampaignTaskStatsSrvc, BASE_URL) {
	//clean
	$scope.cleanCampaignTaskSensing=function(){$scope.campaignTaskSensing=[],$scope.form={},$scope.records=[],$scope.listed=[],$scope.totalItems=0,$scope.currentPage=0,$scope.radioModel="10",$scope.maxSize=10,$scope.itemsPerPage = 10,$scope.selected_items=0,$scope.fullscreen = false};
	//Salvando
	$scope.saveCampaignTaskSensing=function(a){isSpinnerBar(!0),$scope.form.campaign_id=a,CampaignTaskSensingSrvc.saveCampaignTaskSensing($scope.form).then(function(a){1==a.status?$timeout(function(){$("#fixgoback")[0].click()},1000):(bootbox.alert(a.message),$timeout(function(){isSpinnerBar(!1)},500))})};
	//Edicao
	$scope.getCampaignTaskSensing=function(){isSpinnerBar(!0),$scope.form={},PacticipActSrvc.isDebug()===!0&&PacticipActSrvc.fake($scope),isSpinnerBar(!1)};
	//Listas
	$scope.getListTaskSensing=function(taskId, userId, actionId){
		console.log(taskId, userId, actionId);
		if(actionId==$scope.currentId){
			isSpinnerBar(true);	
			var listOf = [{}];
			angular.forEach($scope.form.hashmap, function(value) {
				listOf.push({key:"",value:value,item:value,label:"info"});
			});
			CampaignTaskStatsSrvc.getListTaskDatas({taskId:taskId,userId:userId,actionId:actionId, hashmap:listOf}, $scope.radioModel, $scope.currentPage).then(function(res){
				if(res.status == true){
					$timeout(function(){
						$scope.$apply(function(){
							$scope.records = res.items;
							//console.log($scope.records);
							$scope.totalItems = res.total;
							$scope.currentPage = res.offset;
							//$scope.maxSize = res.count;
							$scope.itemsPerPage = res.count;
							if($scope.hasMapLocation==true){
								$scope.loadLocation($scope.records);
							}
						});
					});
				}
				$timeout(function(){ isSpinnerBar(false);}, 500);
			});	
		}else{
			console.log('No Action Id');
		}
	};
	//Chart
	$scope.getChartTaskSensing = function(taskId, userId, actionId){
		//console.log(taskId, userId, actionId);
		if(actionId==$scope.currentId){
			isSpinnerBar(true);	
			CampaignTaskStatsSrvc.getChartTaskDatas({taskId:taskId,userId:userId,actionId:actionId}, $scope.radioModel, $scope.currentPage).then(function(res){
				if(res.status == true){
					$timeout(function(){
						$scope.$apply(function(){
							$scope.hasChart = true;
							$scope.listed = res.chart.listed;
							$scope.chartConfig = {options: {chart: {type: 'bar'}},
									series: res.chart.series,
									title: {text: res.chart.title},
									loading: false,
									 yAxis: { max: 100, 
									 labels: {
							                formatter: function () {
							                    return (this.value > 0 ? ' + ' : '') + this.value + '%';
							                }
							            }, 
							         },
							        credits: {enabled: false},							        
							};
							$timeout(function(){
								$('.selectpicker').selectpicker('refresh');
							},1000);
						});
					});
				}else{
					$scope.hasChart = false;
				}
				$timeout(function(){ isSpinnerBar(false);}, 500);
			});
		}
	};
	//Paginacao
	$scope.setDataPagination = function(taskId, userId, actionId){
		$scope.getListTaskSensing(taskId, userId, actionId);
	};
	//Change Select
	$scope.onChangeSensingSelect = function(){
		$scope.getListTaskSensing($scope.taskId, $scope.userId, $scope.actionId);
	};
	//Watch
	$("#HeyACCELEROMETER").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyACCELEROMETER_CLASSIFIER").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyBATTERY").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyACTIVITY_RECOGNITION_COMPARE").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyGYROSCOPE").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyPHONE_CALL_DURATION").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyAPPS_NET_TRAFFIC").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeySYSTEM_STATS").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyCONNECTION_TYPE").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyDR").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyDEVICE_NET_TRAFFIC").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyCELL").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyMAGNETIC_FIELD").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyGOOGLE_ACTIVITY_RECOGNITION").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyBLUETOOTH").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyAPP_ON_SCREEN").change(function() {
	    $timeout(function() {
	        $scope.getListTaskSensing($scope.taskId, $scope.userId, $scope.actionId);
	    	$scope.getChartTaskSensing($scope.taskId, $scope.userId, $scope.actionId);
	    }, 100)
	});
	$("#HeyLOCATION").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId), $scope.hasMapLocation=true},100)});
	$("#HeyINSTALLED_APPS").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyLIGHT").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyPHONE_CALL_EVENT").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyWIFI_SCAN").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyACTIVITY_DETECTION").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	$("#HeyPHOTO").change(function(){$timeout(function(){$scope.getListTaskSensing($scope.taskId,$scope.userId,$scope.actionId)},100)});
	//Map
	var map;
	var markers = [];	
	$scope.hasCenter = false;
	$scope.loadLocation = function(rows){
		var mapOptions = {zoom: 14, mapTypeId: google.maps.MapTypeId.ROADMAP, center: new google.maps.LatLng(-27.586347, -48.502900)};		
		map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
		$timeout(function(){ $scope.addMarkers(rows); },100);
		// Try HTML5 geolocation.
		try{
			if (navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(function(position) {
					if($scope.hasCenter == false){
						map.setCenter(new google.maps.LatLng(position.coords.latitude, position.coords.longitude));
						google.maps.event.trigger(map, 'resize');
					}
				}, function() {
					//handleLocationError(true, infoWindow, map.getCenter());
					google.maps.event.trigger(map, 'resize');
				});
			}else{
				google.maps.event.trigger(map, 'resize');
			}
		}catch(err){
			console.log(err);
			google.maps.event.trigger(map, 'resize');
		}
	};
	//Full
	$scope.fullScreenClick = function(){
		isSpinnerBar(true);
		$timeout(function(){
			if($scope.fullscreen){
				$scope.fullscreen = false;
				$('#map-canvas').css('min-height','400px');
				google.maps.event.trigger(map, 'resize');
				$timeout(function(){ isSpinnerBar(false);}, 100);
			}else{
				$scope.fullscreen = true;
				var h = $window.innerHeight;
				if(h > 0){
					$('#map-canvas').css('min-height',(85*h/100));
					google.maps.event.trigger(map, 'resize');
				}
				$timeout(function(){ isSpinnerBar(false);}, 100);
			}
			$timeout(function(){
			if(markers.length>0){
				try{
					map.setCenter(markers[0].position);
					map.setZoom(16);
				}catch(err){
					console.log(err);
					map.setCenter(new google.maps.LatLng(-27.586347, -48.502900));
				}
			}
			},500);
		},1000);
	};
	//Add Marker
	$scope.addMarkers = function(rows){
		for (var i = 0; i < markers.length; i++) {
			markers[i].setMap(null);
		}		
		markers = [];
		$timeout(function(){
			for (var i = 0; i < rows.length; i++) {
				var marker = new google.maps.Marker({position: new google.maps.LatLng(rows[i][4],rows[i][5]), title:rows[i][2], map:map});
				markers.push(marker);
				if($scope.hasCenter == false){
					$scope.hasCenter = true;
					map.setCenter(new google.maps.LatLng(rows[i][4],rows[i][5]));
				}
			}
		},100);
	};
	//Init
	$scope.cleanCampaignTaskSensing();
	//Menu
	setMenuOpen('pa-menu-campaigns','pa-submenu-campaign-tasks');	
});