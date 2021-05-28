/**
 * Notificacoes da barra de menu
 */
ParticipActApp.factory('NotificationBarSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		getListNotificationBar : function(search, count, offset) {
			search = typeof search !== 'undefined' ? search : {read:0};
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/notification-bar/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		setUnreadAll : function() {
			// GET
			return $http.get(BASE_URL+'/protected/notification-bar/unread-all').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});			
		}
	}
});	