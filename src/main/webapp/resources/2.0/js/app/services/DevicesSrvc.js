/**
 * Aparelhos
 */
ParticipActApp.factory('DevicesSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveDevices : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/devices/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListDevices : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/devices/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getDevices : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/devices/edit/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeDevices : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/devices/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
	}
});