ParticipActApp.factory('CampaignTaskAssignSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		/**
		 * Selecionados
		 */
		saveCampaignTaskAssign : function(data) {
			// POST
			return $http.post(BASE_URL + '/protected/campaign-task-assign/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getCampaignTaskAssign : function(id) {
			// GET
			return $http.get(BASE_URL + '/protected/campaign-task-assign/find/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeSelectedAssign : function(id) {
			// GET
			return $http.get(BASE_URL + '/protected/campaign-task-assign/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		/**
		 * Excluidos
		 */
		setCampaignExcluded : function(data) {
			// POST
			return $http.post(BASE_URL + '/protected/campaign-user-excluded/set/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getCampaignExcludedList : function(id) {
			// GET
			return $http.get(BASE_URL + '/protected/campaign-user-excluded/list/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		}
	}
});