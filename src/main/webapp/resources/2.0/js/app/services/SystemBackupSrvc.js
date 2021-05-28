/**
 * Backups do sistema
 */
ParticipActApp.factory('SystemBackupSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveSystemBackup : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/system-backup/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListSystemBackup : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/system-backup/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getSystemBackup : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/system-backup/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeSystemBackup : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/system-backup/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},		
	}
});