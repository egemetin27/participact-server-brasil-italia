/**
 * Emails do sistema
 */
ParticipActApp.factory('SystemEmailSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveSystemEmail : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/system-email/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListSystemEmail : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/system-email/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getSystemEmail : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/system-email/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeSystemEmail : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/system-email/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},		
	}
});