/**
 * CGU e-OUV/Fala.BR
 */
ParticipActApp.factory('CguEouvSrvc', function ($q, $timeout, $http, $window, $localStorage, BASE_URL) {
    var deferred = $q.defer();
    return {
        //ServicoConsultaDadosCodigos.svc
        getListaOrgaosSiorg: function (data) {
            // POST
            return $http.post(BASE_URL + '/protected/cgu-eouv/get-lista-orgos-siorg', data).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
        //ServicoOuvidorias.svc
        getOuvidorias: function (data) {
            // POST
            return $http.post(BASE_URL + '/protected/cgu-ouvidoria/get-ouvidorias', data).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
        setOuvidoria: function (id) {
            // GET
            return $http.get(BASE_URL + '/protected/cgu-ouvidoria/edit/' + id + '/find').then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
        toggleOmbudsman: function (id) {
            // GET
            return $http.get(BASE_URL + '/protected/cgu-ouvidoria/toggle-ombudsman/' + id).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
        toggleSubCategory: function (id, subCategoryId) {
            // GET
            return $http.get(BASE_URL + '/protected/cgu-ouvidoria/toggle-subcategory/' + id + '/' + subCategoryId).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
        //GetListaManifestacaoOuvidoria
        getListaManifestacaoOuvidoria: function (data) {
            // POST
            return $http.post(BASE_URL + '/protected/cgu-eouv/get-lista-manifestacao-ouvidoria', data).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
        //Crontab
        crontab : function(data){
            // POST
            return $http.post(BASE_URL + '/protected/cgu-crontab/submit', data).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
    }
});