/**
 * Páginas do sistema
 */
ParticipActApp.factory('SystemPageSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveSystemPage : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/system-page/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListSystemPage : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/system-page/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getSystemPage : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/system-page/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeSystemPage : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/system-page/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},		
	}
});