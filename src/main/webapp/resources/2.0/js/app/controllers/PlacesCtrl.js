ParticipActApp.controller('PlacesCtrl', function($scope, $timeout, $http) {
	$scope.places = [];	
	//Address place
	$scope.autoComplete = function(id){
		try{
			angular.forEach($scope.places[id], function(value, key) {
				if(key == 'address_components'){
					angular.forEach(value, function(item){
						angular.forEach(item.types, function(level){
							$scope.formattedAddress(level, item, id);
						});
					});
				}else if(key == 'geometry'){
					$scope.formattedAddress(key, value.location, id);
				}
			});
		}catch(err){
			console.log(err);
		}
	};
	//Limpando
	$scope.cleanAutoComplete = function(id){
		$scope.places[id] = null;
		$scope.cleanAddress();
	};
	//Removendo os dados do formulario
	$scope.cleanAddress = function(){
		$scope.form.addressCity = $scope.form.address = $scope.form.addressSublocality = $scope.form.addressState = $scope.form.addressCountry = $scope.form.addressPostalCode = $scope.form.mapLat = $scope.form.mapLng = '';
	};
	
	//Pre formata o endereco
	$scope.formattedAddress = function(level, item, id){
		switch (level) {
			case 'locality' : 
				$scope.form.addressCity = item.long_name; 
				break;
			case 'route' :
				$scope.form.address = item.long_name; 
				$scope.form.route = item.long_name;
				break;
			case 'sublocality' : 
				$scope.form.address = $scope.form.address +' - '+ item.long_name;
				$scope.form.addressSublocality = item.long_name;
				break;
			case 'administrative_area_level_1' : 
				$scope.form.addressState = item.long_name; 
				break;
			case 'country' : 
				$scope.form.addressCountry = item.long_name; 
				break;
			case 'postal_code' : 
				$scope.form.addressPostalCode = item.long_name;
				break;
			case 'geometry' : 
				$scope.form.mapLat = item.lat();
				$scope.form.mapLng = item.lng();
				break;
			default : break;
		}
	};	
});