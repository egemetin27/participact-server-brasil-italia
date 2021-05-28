/**
 * Tarefas das Campanhas
 */
ParticipActApp.factory('CampaignTaskSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		getCampaignTasks : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/campaign-task/edit/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeCampaignTasks : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/campaign-task/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		}
	}
});