ParticipActApp.controller('IssueCategoryCtrl', function ($scope, $timeout, $http, $window, $localStorage, BASE_URL, PacticipActSrvc, IssueCategorySrvc) {
    //default
    $scope.form = {};
    $scope.categories = [];
    $scope.currentCategory = {};
    $scope.currentCategoryId = 0;
    $scope.currentSubCategoryId = 0;
    $scope.currentSubCategory = {};
    //Init
    $scope.initIssueCategory = function () {
        //Search
        $scope.searchIssueCategory();
    };
    //Search
    $scope.searchIssueCategory = function () {
        isSpinnerBar(true);
        IssueCategorySrvc.getListIssueCategory({}).then(function (res) {
            if (res.status) {
                //Result
                $scope.categories = res.item;
            }
            isSpinnerBar(false);
        });
    };
    //Salvando
    $scope.saveIssueCategory = function () {
        isSpinnerBar(true);
        // console.log(JSON.stringify($scope.form));
        IssueCategorySrvc.submitIssueCategory($scope.form).then(function (res) {
            if (res.status == true) {
                PacticipActSrvc.display(res.message);
                //Refresh page
                console.log('Refresh Page');
                if (isBlank($scope.form.id)) {
                    $scope.form.id = res.outcome;
                    if ($scope.form.level > 0) {
                        $scope.categories.forEach((v, k) => {
                            if (v.id == $scope.form.categoryId) {
                                $scope.searchIssueSubCategory(k, v.id, $scope.currentSubCategoryId, $scope.form.level);
                            }
                        });
                    } else {
                        $scope.categories.push($scope.form);
                    }
                }
                // Hide
                $('#paModalCategory').modal('hide')
            } else {
                bootbox.alert(res.message);
            }
            isSpinnerBar(false);
        });
    };
    //Edicao
    $scope.getIssueCategory = function () {
        isSpinnerBar(true);
        $scope.form = [];
        if (PacticipActSrvc.isDebug() === true) {
            PacticipActSrvc.fake($scope);
        } else {
            $timeout(function () {
                $scope.setIssueCategory(0)
            });
            ;
        }
        isSpinnerBar(false);
        ;
    };
    $scope.setIssueCategory = function (j) {
        isSpinnerBar(true);
        IssueCategorySrvc.getIssueCategory(j).then(function (res) {
            if (res.status == true) {
                $scope.form = res.item;
                angular.forEach($scope.form, function (value, key) {
                    value.subcategories = value.map;
                });
            }
            isSpinnerBar(false);
        });
    };
    //Category
    $scope.addCategory = function (level) {
        $scope.form = {
            id: null,
            name: '',
            sequence: (level > 0 ? 0 : $scope.categories.length + 1),
            subcategories: [],
            urlAsset: null,
            urlAssetLight: null,
            urlIcon: null,
            level: level,
            type: (level > 0 ? 'S' : 'C'),
            categoryId: (level > 0 ? $scope.currentCategory.id : 0),
            parentId: (level > 1 ? $scope.currentSubCategory.id : 0),
            color: ''
        };
        $timeout(function () {
            //Show
            $('#paModalCategory').modal('toggle')
        }, 10);
    };
    $scope.editCategory = function (key, level) {
        $scope.form = level > 0 ? $scope.currentCategory.map[level][key] : $scope.categories[key];
        $scope.form.categoryId = $scope.currentCategoryId;
        $scope.form.type = level > 0 ? 'S' : 'C';
        $scope.form.level = level;
        $('#paModalCategory').modal('toggle')
    };
    $scope.selectCategory = function (key, level, parentId) {
        if (level > 0) {
            // S
            $scope.currentSubCategory = $scope.currentCategory.map[level][key];
            $scope.currentSubCategoryId = $scope.currentCategory.map[level][key].id;
        } else {
            // C
            $scope.form = $scope.categories[key];
            $scope.currentCategoryId = $scope.form.id;
            $scope.currentCategory = $scope.form;

            $scope.currentSubCategory = 0;
            $scope.currentSubCategoryId = 0;
        }
        //Sub Search
        $timeout(function () {
            $scope.searchIssueSubCategory(key, $scope.currentCategoryId, $scope.currentSubCategoryId, level + 1);
        }, 100);
    };
    $scope.removeCategory = function (key, level) {
        const id = level > 0 ? $scope.currentCategory.map[level][key].id : $scope.categories[key].id;
        isSpinnerBar(true);
        IssueCategorySrvc.removedIssueAnyCategory({id: id, type: (level > 0 ? 'S' : 'C')}).then(function (res) {
            if (res.status == true) {
                PacticipActSrvc.display(res.message);
                if (level > 0) {
                    $scope.currentCategory.map[level].splice(key, 1);
                } else {
                    $scope.categories.splice(key, 1);
                    if ($scope.currentCategoryId != 0 && $scope.currentCategoryId == id) {
                        $scope.form = {};
                        $scope.currentCategoryId = 0;
                    }
                }
            } else {
                bootbox.alert(res.message);
            }
            isSpinnerBar(false);
        });
    };
    $scope.reorderCategory = function (type, orderList) {
        isSpinnerBar(true);
        IssueCategorySrvc.reorderIssueCategory({type: type, orderly: orderList}).then(function (res) {
            if (res.status == true) {
                console.log('Category Re Order OK');
                PacticipActSrvc.display('OK');
            } else {
                bootbox.alert(res.message);
            }
            isSpinnerBar(false);
        });
    };
    $scope.sortableOptions = {
        handle: '.myHandle',
        stop: function (e, ui) {
            const orderList = [];
            let type = 'C';
            ui.item.sortable.sourceModel.map(function (i) {
                type = i.level > 0 ? 'S' : 'C';
                orderList.push(i.id);
            });
            $scope.reorderCategory(type, orderList)
        }
    };
    //Sub Category
    $scope.searchIssueSubCategory = function (categoryKey, categoryId, parentId, level) {
        isSpinnerBar(true);
        IssueCategorySrvc.getListIssueSubCategory({
            categoryId: categoryId,
            parentId: parentId,
            level: level
        }).then(function (res) {
            if (res.status) {
                //Result
                $scope.currentCategory.map = Array.isArray($scope.currentCategory.map) ? $scope.currentCategory.map : [];
                $scope.currentCategory.map[level] = res.item;
            }
            isSpinnerBar(false);
        });
    };
    //Jquery + Angularjs
    $(".pa-filter").keypress(function (e) {
        "13" == (e.keyCode || e.which) && $scope.initIssueCategory()
    });
    //Menu
    if ($('#pa-cgu-issue-category').length) {
        setMenuOpen('pa-menu-cgu', 'pa-submenu-cgu-category');
    } else {
        setMenuOpen('pa-menu-settings', 'pa-submenu-issue-category');
    }
});