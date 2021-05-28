/**
 * Usuários do Sistema
 */
ParticipActApp.factory('UserSystemSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveUserSystem : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/account/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListUserSystem : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/account/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getUserSystem : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/account/edit/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getUserSystemName : function(id){
			// GET
			return $http.get(BASE_URL+'/protected/account/name/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});			
		},
		removeUserSystem : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/account/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
	}
});