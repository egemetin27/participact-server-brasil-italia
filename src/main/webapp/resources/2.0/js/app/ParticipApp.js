var ParticipActApp = angular.module('ParticipActApp', ['angular-loading-bar', 'ngStorage', 'ngSanitize', 'ngFileUpload', 'toastr', 'ipsum', 'ui.mask', 'ui.bootstrap', 'ui.sortable'
    , 'google.places', 'summernote', 'ngMap', 'highcharts-ng', 'chart.js', 'ui.select', 'minicolors', 'toggle-switch', 'ngMask', 'ui.utils.masks']);
/* Config*/
ParticipActApp.config(['cfpLoadingBarProvider', function (cfpLoadingBarProvider) {
    cfpLoadingBarProvider.includeSpinner = false;
}]);

ParticipActApp.config(function (minicolorsProvider) {
    angular.extend(minicolorsProvider.defaults, {
        control: 'hue',
        position: 'top left'
    });
});

ParticipActApp.config(function (toastrConfig) {
    angular.extend(toastrConfig, {
        allowHtml: true,
        autoDismiss: true,
        maxOpened: 10,
        newestOnTop: true,
        positionClass: 'toast-top-right',
        preventDuplicates: false,
        preventOpenDuplicates: false,
        target: 'body',
        timeOut: 4000,
        progressBar: false,
        tapToDismiss: true
    });
});
/* Run */
ParticipActApp.run(function ($rootScope, $timeout) {
    //Loading
    $rootScope.$on('$stateChangeStart', function () {
        isSpinnerBar(true);
    });
    $rootScope.$on('$stateChangeSuccess', function () {
        isSpinnerBar(false);
    });
});