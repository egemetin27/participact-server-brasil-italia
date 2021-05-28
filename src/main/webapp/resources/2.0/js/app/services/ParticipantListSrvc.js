/**
 * Lista de Usarios
 */
ParticipActApp.factory('ParticipantListSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		addUserItem : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/participant-list/item/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeUserItem : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/participant-list/item/removed/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		saveParticipantList : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/participant-list/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},			
		getParticipantList : function(id) {
			// GET
			return $http.get(BASE_URL + '/protected/participant-list/edit/'+id+'/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},		
		getListParticipantList : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/participant-list/item/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		}
	}
});