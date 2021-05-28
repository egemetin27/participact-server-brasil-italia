/**
 * CGU IBGE
 */
ParticipActApp.factory('CguIbgeMunSrvc', function ($q, $timeout, $http, $window, $localStorage, BASE_URL) {
    var deferred = $q.defer();
    return {
        searchCguIbgeMun: function (data, count, offset) {
            // POST
            return $http.post(BASE_URL + '/protected/cgu-ibge-mun/search/' + count + '/' + offset + '', data).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
    }
});