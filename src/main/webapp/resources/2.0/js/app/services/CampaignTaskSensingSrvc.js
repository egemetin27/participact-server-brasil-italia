ParticipActApp.factory('CampaignTaskSensingSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveCampaignTaskSensing : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/campaign-task-sensing/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		}
	}
});