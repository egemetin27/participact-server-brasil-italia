/**
 * Cursos registrados
 */
ParticipActApp.factory('SchoolCourseSrvc', function($q, $timeout, $http, $localStorage, BASE_URL) {
	var deferred = $q.defer();
	return {
		saveSchoolCourse : function(data) {
			// POST
			return $http.post(BASE_URL+'/protected/school-course/save/', data).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getListSchoolCourse : function(search, count, offset) {
			count = typeof count !== 'undefined' ? count : 10;
			offset = typeof offset !== 'undefined' ? offset : 0;
			// POST
			return $http.post(BASE_URL+'/protected/school-course/search/'+count+'/'+offset, search).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		getSchoolCourse : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/school-course/edit/' + id + '/find').then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},
		removeSchoolCourse : function(id) {
			// GET
			return $http.get(BASE_URL+'/protected/school-course/removed/' + id).then(function(res) {
				return res.data;
			}, function(err) {
				console.log(err);
			});
		},		
	}
});