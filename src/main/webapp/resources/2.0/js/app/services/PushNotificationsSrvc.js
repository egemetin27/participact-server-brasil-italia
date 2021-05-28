/**
 * Push Notificações
 */
ParticipActApp.factory('PushNotificationsSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		savePushNotifications : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/push-notifications/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListPushNotifications : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/push-notifications/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getPushNotifications : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/push-notifications/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removePushNotifications : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/push-notifications/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},		
	}
});