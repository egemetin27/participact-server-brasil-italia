/**
 * Usuários Participantes
 */
ParticipActApp.factory('ParticipantSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveParticipant : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/participant/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListParticipant : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/participant/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getParticipant : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/participant/edit/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeParticipant : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/participant/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
	}
});