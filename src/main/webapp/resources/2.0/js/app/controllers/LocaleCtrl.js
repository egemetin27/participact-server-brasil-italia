ParticipActApp.controller('LocaleCtrl',function($scope, $timeout, $window) {
	$scope.changeLanguage = function(idiom){
		isSpinnerBar(true);	
		idiom = typeof idiom !== 'undefined' ? idiom : 'pt_BR';
		$("#HeyLocale").trigger('change');
		$timeout(function(){
			$window.location.href = '?language='+idiom;	
		},1000);
	};
});