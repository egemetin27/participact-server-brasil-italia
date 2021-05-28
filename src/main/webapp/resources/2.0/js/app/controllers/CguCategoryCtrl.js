ParticipActApp.controller('CguCategoryCtrl', function ($scope, $timeout, $http, $window, $localStorage, PacticipActSrvc, CguCategorySrvc, CguEouvSrvc) {
    //Vars
    $scope.categories = [];
    $scope.subcategories = [];
    $scope.listOmbudsmen = [];
    $scope.form = {};
    $scope.search = null;
    //Pag
    $scope.totalItems = 0;
    $scope.currentPage = 1;
    $scope.radioModel = '10';
    $scope.maxSize = 10;
    $scope.itemsPerPage = 10;
    //Checkbox
    $scope.selected_items = 0;
    //Init
    $scope.initCguCategory = function () {
        //Cache
        if (!isBlank($localStorage.PaSearchCguCategory)) {
            var search = $localStorage.PaSearchCguCategory;
            delete $localStorage.PaSearchCguCategory;
            $scope.form = search;
            //Search
            $scope.searchCguCategory();
        } else {
            //Search
            $scope.searchCguCategory();
        }
    };
    //Search
    $scope.searchCguCategory = function () {
        isSpinnerBar(true);
        CguCategorySrvc.getListCguCategory($scope.form).then(function (res) {
            if (res.status) {
                //Result
                $scope.categories = res.items;
                $scope.totalItems = res.total;
                $scope.currentPage = res.offset;
                //$scope.maxSize = res.count;
                $scope.itemsPerPage = res.count;
                //Cache local
                delete $localStorage.PaSearchCguCategory;
                $timeout(function () {
                    //Cache
                    $localStorage.PaSearchCguCategory = $scope.form;
                }, 5);
            }
            isSpinnerBar(false);
        });
    };
    //Page
    $scope.pageChanged = function () {
        $scope.form.count = $scope.radioModel;
        $scope.form.offset = $scope.currentPage;
        $timeout(function () {
            $scope.searchCguCategory();
        }, 5);
    };
    //Select all
    $scope.checkAll = function () {
        if ($scope.selectedAll) {
            $scope.selectedAll = true;
            $scope.selected_items = $scope.categories.length;
            //console.log($scope.selected_items);
        } else {
            $scope.selectedAll = false;
            $scope.selected_items = 0;
        }
        $timeout(function () {
            angular.forEach($scope.categories, function (item) {
                item.Selected = $scope.selectedAll;
            });
        }, 10);
    };
    //Se selecionado
    $scope.isSelected = function (id) {
        if ($('#' + id).is(':checked')) {
            $scope.selected_items++;
        } else {
            $scope.selected_items--;
        }
    };
    //Save
    $scope.saveCguCategory = function () {
        isSpinnerBar(true);
        $scope.form.subcategories = $scope.subcategories;
        $timeout(function () {
            //console.log(JSON.stringify($scope.subcategories));
            CguCategorySrvc.saveCguCategory($scope.form).then(function (res) {
                if (res.status) {
                    $("#fixgoback")[0].click();
                } else {
                    bootbox.alert(res.message || '');
                }
                isSpinnerBar(false);
            });
        }, 5);
    };
    //Edit
    $scope.editCguCategory = function (id) {
        isSpinnerBar(true);
        $timeout(function () {
            $window.location.href = "edit/" + id;
        }, 500);
    };
    //Get
    $scope.setCguCategory = function (id) {
        isSpinnerBar(true);
        CguCategorySrvc.getCguCategory(id).then(function (res) {
            if (res.status == true) {
                $scope.form = res.item;
                $scope.subcategories = res.item.map;
            }
            isSpinnerBar(false);
        });
    };
    //Remove
    $scope.removeSelected = function (y, n, t, m, err) {
        var html = '';
        if ($scope.selected_items < 1) {
            bootbox.alert(err);
        } else {
            angular.forEach($scope.categories, function (item, k) {
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
                            angular.forEach($scope.categories, function (item, k) {
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
    //Remove item
    $scope.removed = function (id) {
        isSpinnerBar(true);
        CguCategorySrvc.removeCguCategory(id).then(function (res) {
            if (res.status == true) {
                $('#categories-tr-' + id).addClass('hide');
                PacticipActSrvc.display(res.message);
            } else {
                $('#categories-tr-' + id).addClass('danger');
                PacticipActSrvc.display(res.message, 'error');
            }
            isSpinnerBar(false);
        });
    };
    //Sub Categories
    $scope.addSubCategory = function () {
        $scope.subcategories.push({id: null, name: '', urlAsset: null, ombudsmen: []});
        $timeout(function () {
            $("#nameSubCategory" + ($scope.subcategories.length - 1)).focus();
        }, 50);
    };
    $scope.removeSubCategory = function (k) {
        $scope.subcategories.splice(k, 1);
    };
    //Ouvidorias
    $scope.onSearchCguOmbudsmen = function (k, search) {
        console.log('onSearchCguOmbudsmen');
        if (search != undefined && search != null && search.length > 0) {
            isSpinnerBar(true)
            $('#pa-cgu-category-list-' + k).show();
            CguEouvSrvc.getOuvidorias({isSync: false, search: search}).then(function (res) {
                if (res.status == true) {
                    $scope.listOmbudsmen = res.data;
                }
                isSpinnerBar(false);
            });
        } else {
            $('#pa-cgu-category-list-' + k).hide();
        }
    };
    $scope.onSelectCguOmbudsmen = function (k, i) {
        console.log('onSelectCguOmbudsmen');
        var list = $scope.subcategories[k].ombudsmen || [];
        var isOk = true;
        angular.forEach(list, function (v) {
            if (v.idOuvidoria == i.idOuvidoria) {
                isOk = false;
            }
        });
        if (isOk) {
            $scope.subcategories[k].ombudsmen = Array.isArray($scope.subcategories[k].ombudsmen) ? $scope.subcategories[k].ombudsmen : [];
            $scope.subcategories[k].ombudsmen.push(i);
            $('.pa-data-list').hide();
            $timeout(function () {
                $scope.search = null;
            }, 5);
        }
    };
    $scope.onRemoveCguOmbudsmen = function (k, x) {
        if (k > -1 && x > -1) {
            $scope.subcategories[k].ombudsmen.splice(x, 1);
        }
    };
    //Jquery + Angularjs
    $(".pa-filter").keypress(function (e) {
        var o = e.keyCode || e.which;
        "13" == o && $scope.initCguCategory()
    });
    //Menu
    setMenuOpen('pa-menu-cgu', 'pa-submenu-cgu-category');
});