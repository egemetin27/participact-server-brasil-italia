ParticipActApp.factory('CampaignTaskGeoDrawingSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveCampaignTaskGeoDrawing : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/campaign-task-geo-drawing/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeTaskGeoDrawing : function(data){
			// POST
			return $http.post(BASE_URL+'/protected/campaign-task-geo-drawing/removed/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});			
		},
		copyTaskGeoDrawing : function(data){
			// POST
			return $http.post(BASE_URL+'/protected/campaign-task-geo-drawing/copy/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});			
		},		
		getTaskGeoDrawing : function(data){
			// POST
			return $http.post(BASE_URL+'/protected/campaign-task-geo-drawing/find', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});			
		},		
	}
});