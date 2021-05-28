/**
 * Rotas dos participantes
 */
ParticipActApp.controller('CampaignTaskGeoRoutesCtrl', function($scope, $timeout, $http, $window, $localStorage, PacticipActSrvc, CampaignTaskGeoRoutesSrvc) {
	$scope.listOf = [];
	$scope.legends = [];
	$scope.taskId = 0;
	$scope.progressbar = 0;
	$scope.loadingbar = false;
	$scope.hasCenter = [BASE_GEO_LAT, BASE_GEO_LNG];
	$scope.isCenter = false;
	//get
	$scope.initCampaignTaskGeoRoutes = function(){
		isSpinnerBar(true);
		if(!isBlank($localStorage.listOf) && !isBlank($localStorage.taskId)){
			$scope.loadingbar = true;
			$scope.listOf = $localStorage.listOf;
			var total = $localStorage.listOf.length;
			$scope.taskId = $localStorage.taskId;
			angular.forEach($scope.listOf, function(item, key) {
				var value = key + 1;
				$scope.progressbar = value > 0?(value / total * 100).toFixed(2): 0;	
				CampaignTaskGeoRoutesSrvc.getCampaignTaskDirections($scope.taskId, item.id).then(function(res){
					if(res.status == true){
						var paths = [];
						angular.forEach(res.items, function(i) {
							paths.push({lat: i[4], lng:  i[5]});
							if($scope.isCenter == false){
								$scope.isCenter = true;
								map.setCenter(new google.maps.LatLng(i[4],i[5]));
								map.setZoom(16);
							}
						});
						$scope.legends.push({name: item.name, color: res.item});
						$scope.addPolyline(res.item, paths);
					}
				});
			});
			$scope.loadingbar = false;
			isSpinnerBar(false);	
		}else{
			isSpinnerBar(false);	
		}
	};
	//Map
	var mapOptions = {zoom: 14, mapTypeId: google.maps.MapTypeId.ROADMAP, center: new google.maps.LatLng(BASE_GEO_LAT, BASE_GEO_LNG)};		
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	// Try HTML5 geolocation.
	try{
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function(position) {
				map.setCenter(new google.maps.LatLng(position.coords.latitude, position.coords.longitude));
				google.maps.event.trigger(map, 'resize');
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
	$timeout(function(){
		$scope.fullscreen = true;
		var h = $window.innerHeight;
		if(h > 0){
			$('#map-canvas').css('min-height',(85*h/100));
			map.setCenter(new google.maps.LatLng(BASE_GEO_LAT, BASE_GEO_LNG));
			google.maps.event.trigger(map, 'resize');
		}	
	},2000);
	//Add lines
	$scope.addPolyline = function (color, coordinates){
		var line = new google.maps.Polyline({
			path: coordinates,
			geodesic: true,
			strokeColor: '#'+color,
			strokeOpacity: 1.0,
			strokeWeight: 2,
			fillColor: '#'+color,
		});

		line.setMap(map);
	}
	//Menu
	setMenuOpen('pa-menu-campaigns','pa-submenu-campaign-tasks');	
});