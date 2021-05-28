ParticipActApp.factory('CampaignTaskDetectionSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveCampaignTaskDetection : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/campaign-task-detection/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		}
	}
});