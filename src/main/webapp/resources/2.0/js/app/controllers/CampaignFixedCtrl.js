ParticipActApp.controller('CampaignFixedCtrl', function($scope, $timeout, $http, $window, $interval ,  $filter, $localStorage,PacticipActSrvc, CampaignFixedSrvc, ParticipantSrvc, ParticipantListSrvc, CampaignSrvc, BASE_URL) {
	$scope.form = {};
	$scope.items = [];
	//Pag
	$scope.totalItems = 0;
	$scope.currentPage = 1;	
	$scope.radioModel = '10';
	$scope.maxSize = 10;	
	$scope.itemsPerPage = 10;
	//Init Fixed
	$scope.initCampaignFixed = function(){
		isSpinnerBar(true);	
		CampaignFixedSrvc.getCampaignFixed(1).then(function(res){
			if(res.status == true){
				$timeout(function(){
					$scope.$apply(function(){
						var item = res.item;
						item.intervalTime = new Date(res.item.intervalTime*1000);
						item.isEnabled = item.enabled;						
						$timeout(function(){
							$scope.$apply(function(){
								$scope.form = item;
							});
						},100);
					});
				});
			}
			$timeout(function(){ isSpinnerBar(false);}, 500);
		});		
	};
	//Save Fixed
	$scope.saveCampaignFixed = function(){
		isSpinnerBar(true);	
		CampaignFixedSrvc.saveCampaignFixed($scope.form).then(function(res){
			PacticipActSrvc.display(res.message, res.resultType);
			isSpinnerBar(false);
		});		
	};
	//Search List
	$scope.initParticipantList = function(){
		isSpinnerBar(true);	
		$localStorage.$reset();
		$timeout(function(){
			search = {taskId:0, count:$scope.radioModel, offset:$scope.currentPage, search: $scope.form.search||'', isIssueSearch: true};
			ParticipantSrvc.getListParticipant(search, search.count, search.offset).then(function(res){
				if(res.status == true){
					$timeout(function(){
						$scope.$apply(function(){
							$scope.items = res.items;
							$scope.totalItems = res.total; 
							$scope.currentPage = res.offset; 
							$scope.itemsPerPage = res.count;
						});
					});
				}
				$timeout(function(){ isSpinnerBar(false);}, 500);
			});
		}, 500);		
	};
	//Export List
	$scope.initGpsListExport = function(){
		var listOfIds = [{key:"FILTER_FIXED_ID",value:0,item:0,label:"info"}];
		isSpinnerBar(true);
		CampaignSrvc.getCloudExport({isGpsLocation: true,hashmap:listOfIds}).then(function(res){
			if(res.status == true){
				PacticipActSrvc.display(res.message, res.resultType);
			}else{
				bootbox.alert(res.message);
			}
			isSpinnerBar(false);
		});			
	};
	//Time reload
	$scope.hasInterval = false;	
	//User Gps Point
	$scope.getListGpsUser = function(uid){
		console.log('getListGpsUser');
		$timeout(function(){
			isSpinnerBar(true);	
			CampaignFixedSrvc.getListGpsUser(uid).then(function(res){
				if(res.status == true){
					$scope.items = res.items;
					$timeout(function(){ $scope.addMarkers($scope.items); },100);
		            //Set interval
		            if($scope.hasInterval == false){
		                $scope.hasInterval = true;
		                $interval(function() {
		                    $scope.getListGpsUser(uid);
		                }, 60000);
		            }					
				}
				$timeout(function(){ isSpinnerBar(false);}, 500);				
			});
		},100);
	};	
	//Map
	var map;
	var markers = [];	
	$scope.hasCenter = false;		
	$scope.initMap = function(){
		var mapOptions = {zoom: 14, mapTypeId: google.maps.MapTypeId.ROADMAP, center: new google.maps.LatLng(-27.586347, -48.502900)};		
		map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);			
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
	//Add Marker
	$scope.addMarkers = function(rows){
		for (var i = 0; i < markers.length; i++) {
			markers[i].setMap(null);
		}		
		markers = [];
		$timeout(function(){
			for (var i = 0; i < rows.length; i++) {
				var marker = new google.maps.Marker({position: new google.maps.LatLng(rows[i][4],rows[i][5]), title:rows[i][2], map:map});
				if(i == 0){
					marker.setIcon('//maps.google.com/mapfiles/ms/micons/blue-pushpin.png');
					marker.setAnimation(google.maps.Animation.DROP);
					var myCircle = new google.maps.Circle({
						center: new google.maps.LatLng(rows[i][4], rows[i][5]),
						radius: 50,
						strokeColor: "#0000FF",
						strokeOpacity: 0.8,
						strokeWeight: 1,
						fillColor: "#0000FF",
						fillOpacity: 0.4
					});
					myCircle.setMap(map);
				}
				markers.push(marker);
				if($scope.hasCenter == false){
					$scope.hasCenter = true;
					map.setCenter(new google.maps.LatLng(rows[i][4],rows[i][5]));
				}
			}
		},100);
	};	
	//Pag
	$scope.pageChanged = function (){ 
		$scope.initParticipantList(); 
	};	
	$(".pa-filter").keypress(function(a){
		var b=a.keyCode||a.which;
		if("13"==b){
			$scope.initParticipantList();
		}
	});
	//Menu
	setMenuOpen('pa-menu-campaigns','pa-submenu-campaign-tasks');	
});