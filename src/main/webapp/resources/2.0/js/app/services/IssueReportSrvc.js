/**
 * Issues
 */
ParticipActApp.factory('IssueReportSrvc', function($q, $timeout, $http, $window, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveIssueReport : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/issue-report/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getCloudExport: function(data){
			// POST
			return $http.post(BASE_URL+'/protected/issue-report/export/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});				
		},
		getListIssueReport : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/issue-report/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getIssueReport : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/issue-report/edit/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeIssueReport : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/issue-report/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},	
		openNewTab : function(id){
			$window.open(BASE_URL+'/protected/issue-report/edit/' + id, '_blank');
			return;
		},
	}
});