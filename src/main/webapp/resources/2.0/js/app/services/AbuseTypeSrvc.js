/**
 * Tipos de Abusos/Alertas/Problemas registrados
 */
ParticipActApp.factory('AbuseTypeSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveAbuseType : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/abuse-type/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListAbuseType : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/abuse-type/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getAbuseType : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/abuse-type/edit/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeAbuseType : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/abuse-type/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},		
	}
});