ParticipActApp.factory('CampaignTaskGeoRoutesSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		getCampaignTaskDirections : function(taskId, userId) {
			// GET
			return $http.get(BASE_URL+'/protected/campaign-task-routes/directions/'+taskId+'/'+userId).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
	}
});