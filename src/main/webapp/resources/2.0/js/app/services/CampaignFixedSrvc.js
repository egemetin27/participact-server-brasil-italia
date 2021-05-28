ParticipActApp.factory('CampaignFixedSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		getCampaignFixed : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/campaign-fixed/edit/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListGpsUser : function(id){
			// GET
			return $http.get(BASE_URL+'/protected/campaign-fixed/location/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});			
		},
		saveCampaignFixed: function(data){
			// POST
			return $http.post(BASE_URL+'/protected/campaign-fixed/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});			
		},
	}
});