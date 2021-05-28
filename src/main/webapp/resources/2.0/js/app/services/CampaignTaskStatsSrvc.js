ParticipActApp.factory('CampaignTaskStatsSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		getTotalByState : function(id){
			// GET
			return $http.get(BASE_URL+'/protected/campaign-task-state-stats/' + id ).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getTotalByStateAndUser : function(id){
			// GET
			return $http.get(BASE_URL+'/protected/campaign-task-user-stats/' + id ).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});			
		},
		getListTaskReports : function(data, count, offset){	
			//POST
			return $http.post(BASE_URL+'/protected/campaign-task-reports/search/'+count+'/'+offset,data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});			
		},
		getListStatisticsDashboard : function(data){
			// POST
			return $http.post(BASE_URL+'/protected/dashboard/statistics/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});			
		},
		getListTaskDatas : function(data, count, offset){
			//POST
			return $http.post(BASE_URL+'/protected/campaign-task-reports/action/'+count+'/'+offset,data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});			
		},	
		getChartTaskDatas : function(data){
			return $http.post(BASE_URL+'/protected/campaign-task-reports/action/chart/',data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});			
		},
		getTotalByIssueCategory  : function(data){
			return $http.post(BASE_URL+'/protected/dashboard/statistics/categories', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
	}
});