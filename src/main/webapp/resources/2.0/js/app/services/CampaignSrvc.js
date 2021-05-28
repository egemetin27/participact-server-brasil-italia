/**
 * Campanhas
 */
ParticipActApp.factory('CampaignSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveCampaign : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/campaign/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListCampaign : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/campaign/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getCloudExport : function(data){
			// POST
			return $http.post(BASE_URL+'/protected/campaign/export/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});			
		},
		getCampaign : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/campaign/edit/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeCampaign : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/campaign/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		//Collapse
		collapseDetails : function(id, data){
			// POST
			return $http.post(BASE_URL+'/protected/campaign/collapse-details/'+id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});		
		},
		//Enviar camapanha
		confirmPublish : function(id){
			// GET
			return $http.get(BASE_URL+'/protected/campaign/confirm-publish/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});			
		},
		//Copiando Campanha
		copyTask : function(id){
			// POST
			return $http.post(BASE_URL+'/protected/campaign/copy/' + id, {}).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});				
		},
		//Convidando
		inviteTask: function(id){
			// POST
			return $http.post(BASE_URL+'/protected/campaign/invite/' + id, {}).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		//Reenvio de email
		resendEmail : function(id, data){
			// POST
			return $http.post(BASE_URL+'/protected/campaign-task-resend/save/' + id, data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});				
		},
	}
});