ParticipActApp.controller('CguEouvCtrl', function ($scope, $timeout, $http, $window, $interval, $sce, $localStorage, PacticipActSrvc, CguEouvSrvc) {
    //Vars
    $scope.items = [];
    $scope.form = {};
    //Index
    $scope.searchCguEouv = function () {
        isSpinnerBar(false);
    };
    //ServicoConsultaDadosCodigos.svc
    //getListaOrgaosSiorg
    $scope.getListaOrgaosSiorg = function (is, protocol) {
        console.log('getListaOrgaosSiorg');
        isSpinnerBar(true)
        CguEouvSrvc.getListaOrgaosSiorg({
            isSync: (is != undefined ? is : false),
            protocol: (protocol != undefined ? protocol : 'json')
        }).then(function (res) {
            if (res.status == true) {
                $scope.items = res.data;
            }
            isSpinnerBar(false);
        });
    };
    //ServicoOuvidorias.svc
    //GetOuvidorias
    $scope.getOuvidorias = function (is, protocol) {
        console.log('getOuvidorias');
        isSpinnerBar(true)
        CguEouvSrvc.getOuvidorias({
            isSync: (is != undefined ? is : false),
            protocol: (protocol != undefined ? protocol : 'json')
        }).then(function (res) {
            if (res.status == true) {
                $scope.items = res.data;
            }
            isSpinnerBar(false);
        });
    };
    // Get
    $scope.setOuvidoria = function (id) {
        console.log(id);
        isSpinnerBar(true)
        CguEouvSrvc.setOuvidoria(id).then(function (res) {
            if (res.status == true) {
                $scope.form = res.item;
            }
            isSpinnerBar(false);
        });
    };
    // Toggle
    $scope.toggleOmbudsman = function (id) {
        isSpinnerBar(true);
        CguEouvSrvc.toggleOmbudsman(id).then(function (res) {
            if (res.status == true) {
                $scope.form.hasAllowOmbudsman = res.item;
            }
            $timeout(function () {
                isSpinnerBar(false);
            }, 500);
        });
    };
    $scope.toggleSubCategory = function (id, ck, sk) {
        isSpinnerBar(true);
        var subCategoryId = $scope.form.categories[ck].map[sk].id;
        $timeout(function () {
            CguEouvSrvc.toggleSubCategory(id, subCategoryId).then(function (res) {
                if (res.status == true) {
                    $scope.form.categories[ck].map[sk].hasAllowOmbudsman = res.item;
                }
                $timeout(function () {
                    isSpinnerBar(false);
                }, 500);
            });
        }, 500);
    };
    // Crontab
    $scope.crontab = function () {
        console.log('crontab');
        isSpinnerBar(true);
        CguEouvSrvc.crontab($scope.form).then(function (res) {
            isSpinnerBar(false);
            PacticipActSrvc.display(res.message, res.resultType)
        });
    };
    //Menu
    setMenuOpen('pa-menu-cgu', 'pa-submenu-cgu-eouv');
});