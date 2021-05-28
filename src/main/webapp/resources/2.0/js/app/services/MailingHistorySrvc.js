/**
 * Emails do sistema
 */
ParticipActApp.factory('MailingHistorySrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		getMailingHistory : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/mailing-history/edit/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},	
	}
});