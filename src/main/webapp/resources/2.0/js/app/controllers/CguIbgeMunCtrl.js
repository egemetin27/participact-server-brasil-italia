ParticipActApp.controller('CguIbgeMunCtrl', function ($scope, $timeout, $http, $window, $interval, $sce, $localStorage, PacticipActSrvc, CguIbgeMunSrvc) {
    //Vars
    $scope.items = [];
    $scope.form = {search: null};
    //Pag
    $scope.totalItems = 0;
    $scope.currentPage = 0;
    $scope.radioModel = '10';
    $scope.maxSize = 10;
    $scope.itemsPerPage = 10;
    //Index
    $scope.initCguIbgeMun = function () {
        $scope.searchCguIbgeMun();
    };
    //Search
    $scope.searchCguIbgeMun = function () {
        console.log('searchCguIbgeMun');
        var search = $scope.form.search;
        isSpinnerBar(true)
        CguIbgeMunSrvc.searchCguIbgeMun({search: search}, $scope.radioModel, $scope.currentPage).then(function (res) {
            if (res.status == true) {
                $scope.items = res.item;
                $scope.totalItems = res.total;
                $scope.currentPage = res.offset;
                $scope.itemsPerPage = res.count;
            }
            isSpinnerBar(false);
        });
    };
    //Pagination
    $scope.pageChanged = function () {
        $scope.searchCguIbgeMun();
    }
    // JS
    $(".pa-filter").keypress(function(a){var b=a.keyCode||a.which;"13"==b&&$scope.searchCguIbgeMun()});
    setMenuOpen('pa-menu-cgu', 'pa-submenu-cgu-ibge-mun');
});