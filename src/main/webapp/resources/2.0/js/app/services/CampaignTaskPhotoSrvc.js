ParticipActApp.factory('CampaignTaskPhotoSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveCampaignTaskPhoto : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/campaign-task-photo/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		}
	}
});