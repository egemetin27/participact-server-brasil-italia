/**
 * Ckan Celesc
 */
ParticipActApp.factory('CkanCelescSrvc', function($q, $timeout, $http, $window, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		getListCkanCelesc : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/ckan-celesc/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		downloadCkanCelesc : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/ckan-celesc/download/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
	}
});