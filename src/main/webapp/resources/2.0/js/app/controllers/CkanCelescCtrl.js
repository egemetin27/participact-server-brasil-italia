ParticipActApp.controller('CkanCelescCtrl', function ($scope, $timeout, $http, $window, $interval, $sce, $localStorage, PacticipActSrvc, CkanCelescSrvc) {
    //clean
    $scope.cleanCkanCelesc = function () {
        $scope.form = {};
        $scope.items = [];
        //Pag
        $scope.totalItems = 0;
        $scope.currentPage = 0;
        $scope.radioModel = '10';
        $scope.maxSize = 10;
        $scope.itemsPerPage = 10;
        //Checkbox
        $scope.selected_items = 0;
        $scope.selected_ids = [];
    };
    //Search
    $scope.initCkanCelesc = function () {
        var search = $scope.form;
        search.count = $scope.radioModel;
        search.offset = $scope.currentPage;
        isSpinnerBar(true)
        CkanCelescSrvc.getListCkanCelesc(search, search.count, search.offset).then(function (res) {
            if (res.status == true) {
                $scope.items = res.items;
                $scope.totalItems = res.total;
                $scope.currentPage = res.offset;
                $scope.itemsPerPage = res.count;
            } else {
                $scope.cleanCkanCelesc();
            }
            $timeout(function () {
                isSpinnerBar(false)
            }, 500);
        });
    }
    //Download
    $scope.downloadCkanCelesc = function () {
        console.log('downloadCkanCelesc');
        isSpinnerBar(true)
        CkanCelescSrvc.downloadCkanCelesc($scope.form).then(function (res) {
            PacticipActSrvc.display(res.message, res.status);
            $timeout(function () {
                isSpinnerBar(false)
            }, 500);
        });
    }
    //Alterando paginacao
    $scope.pageChanged = function () {
        $scope.initCkanCelesc();
    }
    //clean
    $scope.cleanCkanCelesc();
    //Jquery + Angularjs
    $(".pa-filter").keypress(function (e) {
        var o = e.keyCode || e.which;
        "13" == o && $scope.initCkanCelesc()
    });
    //Menu
    setMenuOpen('pa-menu-ckan', 'pa-submenu-ckan-celesc');
});