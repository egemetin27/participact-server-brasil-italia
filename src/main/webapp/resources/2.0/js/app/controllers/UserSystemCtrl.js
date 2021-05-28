ParticipActApp.controller('UserSystemCtrl', function ($scope, $timeout, $http, $window, $localStorage, PacticipActSrvc, UserSystemSrvc) {
    //clean
    $scope.cleanUserSystem = function () {
        $scope.system_users = [];
        $scope.form = {};
        //Pag
        $scope.totalItems = 0;
        $scope.currentPage = 1;
        $scope.radioModel = '10';
        $scope.maxSize = 10;
        $scope.itemsPerPage = 10;
        //Checkbox
        $scope.selected_items = 0;
        $scope.controller = '';
    };
    //Lista de itens
    $scope.initUserSystem = function () {//Carrega todos os itens
        search = $scope.form;
        search.count = $scope.radioModel;
        search.offset = $scope.currentPage;

        isSpinnerBar(true);
        UserSystemSrvc.getListUserSystem(search, search.count, search.offset).then(function (res) {
            if (res.status == true) {
                $timeout(function () {
                    $scope.$apply(function () {
                        $scope.system_users = res.items;
                        $scope.totalItems = res.total;
                        $scope.currentPage = res.offset;
                        //$scope.maxSize = res.count;
                        $scope.itemsPerPage = res.count;
                    });
                });
            } else {
                $scope.cleanUserSystem();
            }
            $timeout(function () {
                isSpinnerBar(false);
            }, 500);
        });
    };
    //Salvando
    $scope.saveUserSystem = function () {
        isSpinnerBar(true);
        UserSystemSrvc.saveUserSystem($scope.form).then(function (res) {
            if (res.status == true) {
                $timeout(function () {
                    $('#fixgoback')[0].click();
                }, 600);
            } else {
                bootbox.alert(res.message);
                $timeout(function () {
                    isSpinnerBar(false);
                }, 500);
            }
        });
    };
    //Edicao
    $scope.editUserSystem = function (id) {
        isSpinnerBar(true);
        $timeout(function () {
            $window.location.href = "edit/" + id;
        }, 500);
    };

    $scope.getUserSystem = function () {
        isSpinnerBar(true);
        $scope.form = {};
        if (PacticipActSrvc.isDebug() === true) {
            PacticipActSrvc.fake($scope);
        }
        isSpinnerBar(false);
        ;
    };

    $scope.setUserSystem = function (j) {
        isSpinnerBar(true);
        UserSystemSrvc.getUserSystem(j).then(function (res) {
            if (res.status == true) {
                $scope.form = res.item;
                $timeout(function () {
                    try {
                        $scope.form.institutionId = !isBlank(res.item.institution.id) ? res.item.institution.id : 0;
                        setSelect2me('institutionId', $scope.form.institutionId);
                    } catch (err) {
                        $scope.form.institutionId = 0;
                        console.log(err);
                    }
                    try {
                        setSelect2me('privilege', $scope.form.privilege);
                        setSelect2me('privilege2', $scope.form.privilege2 || 0);
                    } catch (err) {
                        $scope.form.privilege = 0;
                        $scope.form.privilege2 = 0;
                        console.log(err);
                    }
                }, 100);
            }
            isSpinnerBar(false);
        });
    };
    //Remover varios itens
    $scope.removeSelected = function (y, n, t, m, err) {
        var html = '';
        if ($scope.selected_items < 1) {
            bootbox.alert(err);
        } else {
            angular.forEach($scope.system_users, function (item, k) {
                if (item.Selected === true) {
                    html = html + '<b class="has-error">' + item[1] + '</b><br/>';
                }
            });

            bootbox.dialog({
                title: t,
                message: m + ' : <p>' + html + '</p>',
                buttons: {
                    success: {
                        label: n,
                        className: "btn-default",
                        callback: function () {
                        }
                    },
                    danger: {
                        label: y,
                        className: "btn-danger",
                        callback: function () {
                            isSpinnerBar(true);
                            angular.forEach($scope.system_users, function (item, k) {
                                if (item.Selected === true) {
                                    $scope.removed(item[0]);
                                }
                            });
                        }
                    },
                }
            });
        }
    };
    //executa a acao de remover
    $scope.removed = function (id) {
        isSpinnerBar(true);
        UserSystemSrvc.removeUserSystem(id).then(function (res) {
            if (res.status == true) {
                $('#system_users-tr-' + id).addClass('hide');
                PacticipActSrvc.display(res.message);
            } else {
                $('#system_users-tr-' + id).addClass('danger');
                PacticipActSrvc.display(res.message, 'error');
            }
            isSpinnerBar(false);
        });
    };

    //Alterando paginacao
    $scope.pageChanged = function () {
        $scope.initUserSystem();
    }
    //Select all
    $scope.checkAll = function () {
        if ($scope.selectedAll) {
            $scope.selectedAll = true;
            $scope.selected_items = $scope.totalItems;
        } else {
            $scope.selectedAll = false;
            $scope.selected_items = 0;
        }
        angular.forEach($scope.system_users, function (item) {
            item.Selected = $scope.selectedAll;
        });

    };
    //Se selecionado
    $scope.isSelected = function (id) {
        if ($('#' + id).is(':checked')) {
            $scope.selected_items++;
        } else {
            $scope.selected_items--;
        }
    };
    //Init
    $scope.cleanUserSystem();
    //Jquery + Angularjs
    $('.pa-filter').keypress(function (e) {
        var k = e.keyCode || e.which;
        if (k == '13') {
            $scope.initUserSystem();
        }
    });
    //Menu
    setMenuOpen('pa-menu-users', 'pa-submenu-account');
});