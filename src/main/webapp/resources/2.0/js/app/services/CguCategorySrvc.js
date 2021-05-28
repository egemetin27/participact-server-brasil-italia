/**
 * Categorias vinculadas a Cgu
 */
ParticipActApp.factory('CguCategorySrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveCguCategory : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/cgu-category/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListCguCategory : function(search) {
			// POST
			return $http.post(BASE_URL+'/protected/cgu-category/search', search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListCguSubCategory : function(search) {
			// POST
			return $http.post(BASE_URL+'/protected/cgu-subcategory/search', search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getCguCategory : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/cgu-category/edit/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeCguCategory : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/cgu-category/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},		
	}
});