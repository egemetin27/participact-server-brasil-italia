/**
 * Ferramentas de desenho no mapa
 */
ParticipActApp.controller('DrawingManagerCtrl', function($scope, $timeout, $http, $window, $filter, NgMap) {
	var vm = this;
	var selectedShape;
	var center = {lat: BASE_GEO_LAT,lng: BASE_GEO_LNG};
	NgMap.getMap().then(function(map) {
		vm.map = map;
		//User location
		map.setCenter(center);
		// Try HTML5 geolocation.
		try{
			if (navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(function(position) {
					var center = {lat: position.coords.latitude,lng: position.coords.longitude};
					map.setCenter(center);
				}, function() {
					handleLocationError(true, infoWindow, map.getCenter());
				});
			}	
		}catch(err){
			console.log(err);
		}
	});
	//Overlay
	vm.onMapOverlayCompleted = function(e){
		// Add an event listener that selects the newly-drawn shape when the user mouses down on it.
		var newShape = e.overlay;
		newShape.type = e.type;
		newShape.guid = guid();
		vm.addListenerShape(newShape);
	};
	//Adicionando evento
	vm.addListenerShape =  function(newShape){
		$scope.all_overlays.push(newShape);
		google.maps.event.addListener(newShape, 'click', function (e) {
			if (e.vertex !== undefined) {
				if (newShape.type === google.maps.drawing.OverlayType.POLYGON) {
					var path = newShape.getPaths().getAt(e.path);
					path.removeAt(e.vertex);
					if (path.length < 3) {
						newShape.setMap(null);
					}
				}
				if (newShape.type === google.maps.drawing.OverlayType.POLYLINE) {
					var path = newShape.getPath();
					path.removeAt(e.vertex);
					if (path.length < 2) {
						newShape.setMap(null);
					}
				}
			}
			vm.setSelection(newShape);
		});	
		//Consolidate
		vm.addingInConsolidated(newShape);
	};
	//Add consolidate
	vm.addingInConsolidated = function(newShape){
		//Salvando coordenadas
		if (newShape.type === google.maps.drawing.OverlayType.POLYGON) {
			newShape.consolidated = {guid: newShape.guid, type:newShape.type, radius:0, spherical:vm.getPolygonCoordinates(newShape.getPath().getArray()), center:vm.getCenterCoordinates(newShape.getBounds().getCenter())};
		}else if(newShape.type === google.maps.drawing.OverlayType.CIRCLE){
			newShape.consolidated = {guid: newShape.guid, type:newShape.type, radius:newShape.radius, spherical:vm.getCircleCoordinates(vm.generateCircleSpherical(newShape.getCenter(), newShape.radius)), center:vm.getCenterCoordinates(newShape.getCenter())};
		}else if(newShape.type === google.maps.drawing.OverlayType.RECTANGLE){
			newShape.consolidated = {guid: newShape.guid, type:newShape.type, radius:0, spherical:vm.getRectangleCoordinates(newShape.getBounds()), center:vm.getCenterCoordinates(newShape.getBounds().getCenter())};
		}
		$timeout(function(){
			//Done
			vm.setSelection(newShape);       
		},100);
	};
	//getCircleCoordinates 
	vm.getCircleCoordinates = function(spherical){
		var coords = [];
		angular.forEach(spherical, function(value, key) {
			coords.push({key: key, lat: value.lat(), lng: value.lng()});
		});
		return coords;
	};
	//getPolygonCoordinates 
	vm.getPolygonCoordinates = function(spherical){
		var coords = [];
		angular.forEach(spherical, function(value, key) {
			coords.push({key: key, lat: value.lat(), lng: value.lng()});
		});
		return coords;
	};	
	//getRECTANGLECoordinates 
	vm.getRectangleCoordinates = function(bounds){
		var NE = bounds.getNorthEast();
		var SW = bounds.getSouthWest();
		return [{key: 'NW', lat: NE.lat(), lng: SW.lng()},{key: 'SE', lat: SW.lat(), lng: NE.lng()}];
	};	
	//Get Center
	vm.getCenterCoordinates = function(LatLng){
		return {lat: LatLng.lat(), lng: LatLng.lng()};
	};	
	//Calcula coordenadas
	vm.calculateCoordinates = function(){
		angular.forEach($scope.all_overlays, function(value, key) {
			var newShape = value.overlay;
			newShape.type = value.type;
		});
	};
	//Calcula coordenadas do circulo
	vm.generateCircleSpherical = function(center, radius, points){
		points = typeof points !== 'undefined' ? points : 36;
		var a=[],p=360/points,d=0;
		for(var i=0;i<points;++i,d+=p){
			a.push(google.maps.geometry.spherical.computeOffset(center,radius,d));
		}
		return a;
	};
	//Limpa selecao
	vm.clearSelection = function () {
		if (selectedShape) {
			selectedShape.setEditable(false);
			selectedShape = null;
		}
	};
	//Add item
	vm.addShape = function(newShape){
		NgMap.getMap().then(function(map) {
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
				vm.addListenerShape(POLYGON);				
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
				vm.addListenerShape(CIRCLE);
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
				vm.addListenerShape(RECTANGLE);
			}
		});
	};
	//Settar selecionado
	vm.setSelection = function(shape) {
		vm.clearSelection();
		selectedShape = shape;
		shape.setEditable(true);
	}
	//Remove selecionado
	vm.deleteSelectedShape = function() {
		if (selectedShape) {
			selectedShape.setMap(null);
			angular.forEach($scope.all_overlays, function(value, index) {
				if(value.guid == selectedShape.guid && index > -1){
					$scope.all_overlays.splice(index, 1);
				}
			});
		}
	}
	//Remove todos
	vm.deleteAllSelectedShape = function() {
		angular.forEach($scope.all_overlays, function(value, key) {
			$scope.all_overlays[key].setMap(null);
		});
		$scope.all_overlays = [];
	}	
	//Calculo do centro do poligo
	try{
		if (!google.maps.Polygon.prototype.getBounds) {
			google.maps.Polygon.prototype.getBounds=function(){
				var bounds = new google.maps.LatLngBounds()
				this.getPath().forEach(function(element,index){bounds.extend(element)})
				return bounds
			}
		}	
	}catch(err){
		bootbox.alert("INTERNET ERR CONNECTION");
		console.log(err);
	}
	//Avisa o Drawing para atualizar
	$( "#HeyUpdateTheList" ).change(function() {
		$timeout(function(){
			angular.forEach($scope.has_spherical, function(item) {
				vm.addShape(item);
			});
			console.log("Hey update the list");
		},500);
	});
});