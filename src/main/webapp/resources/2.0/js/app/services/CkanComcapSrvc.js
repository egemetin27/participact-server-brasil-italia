/**
 * Ckan Comcap
 */
ParticipActApp.factory('CkanComcapSrvc', function($q, $timeout, $http, $window, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		getListCkanComcap : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/ckan-comcap/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		downloadCkanComcap : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/ckan-comcap/download/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
	}
});