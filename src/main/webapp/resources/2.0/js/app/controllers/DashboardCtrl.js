ParticipActApp.controller('DashboardCtrl', function ($scope, $timeout, $http, $localStorage, CampaignTaskStatsSrvc) {
    // ## Campanhas
    $scope.cleanDashboard = function () {
        isSpinnerBar(false);
        $scope.totalItems = 0;
        $scope.stats = {
            ByState: {
                "AVAILABLE": 0,
                "ACCEPTED": 0,
                "RUNNING": 0,
                "REJECTED": 0,
                "COMPLETED": 0,
                "NONE": 0
            }, "TOTAL": 0
        };
        $scope.percentage = {"AVAILABLE": 0, "ACCEPTED": 0, "RUNNING": 0, "REJECTED": 0, "COMPLETED": 0, "NONE": 0};
        $scope.chartColumn = {
            "options": {"chart": {"type": "column"},}, "series": [],
            "title": {"text": "Totais"}, yAxis: {title: {text: 'Total'}},
            "credits": {"enabled": false}, "loading": false, "size": {}
        };
    };
    //Init
    $scope.initDashboard = function () {
        $timeout(function () {
            $scope.form.isAdvancedSearch = true;
        }, 100);
    };
    //Statistics
    $scope.getStatistics = function () {
        isSpinnerBar(true);
        try {
            CampaignTaskStatsSrvc.getListStatisticsDashboard({hashmap: $scope.hashmap}).then(function (res) {
                if (res.status) {
                    //BY STATE
                    $scope.stats.ByState = res.chart.byState;
                    $scope.stats.TOTAL = res.chart.byState.TOTAL;
                    $scope.chartColumn.series = res.chart.chartColumn;
                    $scope.chartColumn.yAxis.title.text = res.chart.chartColumnyAxis;
                    $scope.chartColumn.title.text = res.chart.chartColumnxAxis;
                    angular.forEach($scope.stats.ByState, function (value, key) {
                        $scope.percentage[key] = value > 0 ? (value / $scope.stats.TOTAL * 100).toFixed(2) : 0;
                    });
                    //Chart
                }
                $timeout(function () {
                    isSpinnerBar(false);
                }, 100);
            });
        } catch (err) {
            console.log(err);
            isSpinnerBar(false);
        }
    };
    // ## Categorias
    $scope.initStatCategory = function () {
        isSpinnerBar(true);
        CampaignTaskStatsSrvc.getTotalByIssueCategory($scope.form).then((res) => {
            console.log(res);
            if (res.status) {
                am4core.ready(function () {
                    amchartPie('pa-chart-category-pie', res.chart.byCategory, true);
                    amchartBar('pa-chart-subcategory-bar', res.chart.bySubCategory, false);
                    amchartLineGraph('pa-chart-subcategory-line-graph', res.chart.timeline, res.chart.categories);
                    $timeout(function () {
                        resizableMe('pa-dashboard-issue-category');
                    }, 10);
                });
            }
            isSpinnerBar(false);
        });
    };
    //Menu
    if ($('#pa-dashboard-issue-category').length) {
        $scope.form = {};
        setMenuOpen('pa-menu-dashboard', 'pa-submenu-statistics-category');
    } else {
        //Whatch
        $scope.$watchCollection('hashmap', function () {
            $timeout(function () {
                $scope.getStatistics();
            }, 100);
        });
        //Clean
        $scope.cleanDashboard();
        //Menu
        setMenuOpen('pa-menu-dashboard', 'pa-submenu-statistics');
    }
});