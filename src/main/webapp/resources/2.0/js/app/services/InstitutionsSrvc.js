/**
 * Instituições
 */
ParticipActApp.factory('InstitutionsSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveInstitutions : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/institutions/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListInstitutions : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/institutions/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getInstitutions : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/institutions/edit/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeInstitutions : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/institutions/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
	}
});