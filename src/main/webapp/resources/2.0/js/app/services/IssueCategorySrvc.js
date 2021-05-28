/**
 * Categorias dos Relatos
 */
ParticipActApp.factory('IssueCategorySrvc', function ($q, $timeout, $http, $localStorage, BASE_URL) {
    var deferred = $q.defer();
    return {
        submitIssueCategory: function (data) {
            // POST
            return $http.post(BASE_URL + '/protected/issue-category/submit/', data).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
        reorderIssueCategory: function (data) {
            // POST
            return $http.post(BASE_URL + '/protected/issue-category/reorder/', data).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
        removedIssueCategory: function (id) {
            // GET
            return $http.get(BASE_URL + '/protected/issue-category/removed/' + id).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
        removedIssueAnyCategory: function (data) {
            // POST
            return $http.post(BASE_URL + '/protected/issue-category/removed/', data).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
        saveIssueCategory: function (data) {
            // POST
            return $http.post(BASE_URL + '/protected/issue-category/save/', data).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
        getIssueCategory: function (id) {
            // GET
            return $http.get(BASE_URL + '/protected/issue-category/edit/' + id + '/find').then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
        getListIssueCategory: function (data) {
            // GET
            return $http.post(BASE_URL + '/protected/issue-category/search', data).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
        getListIssueSubCategory: function (data) {
            // GET
            return $http.post(BASE_URL + '/protected/issue-subcategory/search', data).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
    }
});