/**
 * Denuncias
 */
ParticipActApp.factory('AbuseReportSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveAbuseReport : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/abuse-report/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListAbuseReport : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/abuse-report/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getAbuseReport : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/abuse-report/edit/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeAbuseReport : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/abuse-report/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},		
	}
});