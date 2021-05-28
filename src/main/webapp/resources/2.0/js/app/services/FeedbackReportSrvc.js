/**
 * Feedbacks
 */
ParticipActApp.factory('FeedbackReportSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveFeedbackReport : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/feedback-report/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListFeedbackReport : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/feedback-report/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getFeedbackReport : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/feedback-report/edit/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeFeedbackReport : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/feedback-report/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},		
	}
});