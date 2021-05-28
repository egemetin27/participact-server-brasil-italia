var LoginApp = angular.module('LoginApp', ['angular-loading-bar']);
/* Config*/
LoginApp.config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
	cfpLoadingBarProvider.includeSpinner = false;
}]);

/* Run */
LoginApp.run(function($rootScope, $timeout) {
	//Loading
	$rootScope.$on('$stateChangeStart',function(){
		isSpinnerBar(true);
	});
	$rootScope.$on('$stateChangeSuccess',function(){
		isSpinnerBar(false);
	});	
});