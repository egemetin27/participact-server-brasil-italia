/**
 * Selecao de area de notificacao/ativacao
 */
ParticipActApp.controller('CampaignTaskGeoDrawingCtrl', function($scope, $timeout, $http, $window, $filter, PacticipActSrvc, CampaignTaskGeoDrawingSrvc) {
	$scope.all_overlays = [];
	$scope.has_spherical = [];
	$scope.form = {};
	$scope.is_static_maps = false;
	//save
	$scope.saveCampaignTaskGeoDrawing=function(a){$scope.form.consolidated=[],angular.forEach($scope.all_overlays,function(a){$scope.form.consolidated.push(a.consolidated)}),isSpinnerBar(!0),$scope.form.campaign_id=a,CampaignTaskGeoDrawingSrvc.saveCampaignTaskGeoDrawing($scope.form).then(function(a){1==a.status?$timeout(function(){$("#fixgoback")[0].click()},600):(bootbox.alert(a.message),$timeout(function(){isSpinnerBar(!1)},500))})};
	//get
	$scope.getCampaignTaskGeoDrawing = function(isNotification, isActivation, id){
		$scope.form.isNotification = isNotification;
		$scope.form.isActivation = isActivation;
		isSpinnerBar(true);	
		CampaignTaskGeoDrawingSrvc.getTaskGeoDrawing({isNotification:isNotification, campaign_id:id}).then(function(res){
			if(res.status == true){
				//Reconstruindo o objeto
				angular.forEach(res.item[0], function(value, key) {
					if(key == 'center'){
						angular.forEach(value, function(sub_value, sub_key) {
							$scope.has_spherical.push({guid: sub_value.guid, center:{lat: sub_value.lat, lng: sub_value.lng}, radius:sub_value.radius, type:sub_value.type, spherical:[]});
						});	
					}
				});
				angular.forEach(res.item[0], function(value, key) {
					if(key == 'spherical'){
						angular.forEach(value, function(sub_value, sub_key) {
							angular.forEach($scope.has_spherical, function(sph) {
								if(sph.guid == sub_value.guid){
									sph.spherical.push({key: sub_value.key,lat: sub_value.lat, lng:sub_value.lng});
								}
								if(isBlank($scope.form.mapCenter)){
									$scope.form.mapCenter = new google.maps.LatLng(sub_value.lat, sub_value.lng);
								}
							});
						});	
					}
				}); 
				//Montagem direta ou estatica
				if($scope.is_static_maps == false){
					$("#HeyUpdateTheList").trigger('change');					
				}else if(!isBlank($scope.form.mapId)){
					$scope.addStaticShape($scope.form.mapId);
				}
			}
			isSpinnerBar(false);	
		});	
	};
	//Criando
	$scope.createStaticMap = function(isNotification, isActivation, id, mapId){
		$scope.form.mapId = mapId;
		$scope.is_static_maps = true;		
		$('#'+mapId).height(200);
		$timeout(function(){
			$scope.getCampaignTaskGeoDrawing(isNotification, isActivation, id);
		},500);
	};
	//set direct shape
	$scope.addStaticShape = function(id){
		var map = new google.maps.Map(document.getElementById(id), { zoom: 11, center: $scope.form.mapCenter, mapTypeId: google.maps.MapTypeId.ROADMAP});
		angular.forEach($scope.has_spherical, function(newShape) {
			if (newShape.type === google.maps.drawing.OverlayType.POLYGON) {
				var paths=[];
				angular.forEach(newShape.spherical, function(value) {
					paths[value.key]=(new google.maps.LatLng(value.lat, value.lng));
				},paths);	
				var POLYGON = new google.maps.Polygon({
					paths: paths,
					strokeOpacity: 0.8,
					strokeWeight: 1,
					fillColor: '#4CAF50',
					fillOpacity: 0.35,
					clickable: true,
					editable: false,
					map: map
				});				
				POLYGON.type =newShape.type;
				POLYGON.guid=newShape.guid;
			}else if(newShape.type === google.maps.drawing.OverlayType.CIRCLE){
				var CIRCLE = new google.maps.Circle({
					strokeOpacity: 0.8,
					strokeWeight: 1,
					fillColor: '#2196F3',
					fillOpacity: 0.35,
					clickable: true,
					editable: false,
					map: map,
					center: new google.maps.LatLng(newShape.center.lat, newShape.center.lng),
					radius: newShape.radius
				});
				CIRCLE.type=newShape.type;
				CIRCLE.guid=newShape.guid;
			}else if(newShape.type === google.maps.drawing.OverlayType.RECTANGLE){
				var bounds={north:0,west:0,south:0,east:0};
				angular.forEach(newShape.spherical, function(value) {
					if(value.key=="SE"){
						bounds.south = parseFloat(value.lat);
						bounds.east = parseFloat(value.lng);
					}else if(value.key=="NW"){
						bounds.north = parseFloat(value.lat);
						bounds.west = parseFloat(value.lng);
					}
				});
				var RECTANGLE = new google.maps.Rectangle({
					strokeOpacity: 0.8,
					strokeWeight: 1,
					fillColor: '#F44336',
					fillOpacity: 0.35,
					clickable: true,
					editable: false,
					map: map,
					bounds: bounds
				}); 
				RECTANGLE.type=newShape.type;
				RECTANGLE.guid=newShape.guid;	
			}
		});		
	}
	//Menu
	setMenuOpen('pa-menu-campaigns','pa-submenu-campaign-tasks');	
});