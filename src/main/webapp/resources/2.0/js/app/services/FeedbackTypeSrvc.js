/**
 * Tipos de Feedbacks
 */
ParticipActApp.factory('FeedbackTypeSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveFeedbackType : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/feedback-type/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListFeedbackType : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/feedback-type/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getFeedbackType : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/feedback-type/edit/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeFeedbackType : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/feedback-type/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},		
	}
});