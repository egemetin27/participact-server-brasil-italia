ParticipActApp.controller('MailingHistoryCtrl', function($scope, $timeout, $http, $window, $localStorage, PacticipActSrvc, MailingHistorySrvc) {
	$scope.getMailingHistory = function(){
		isSpinnerBar(true);	
		var id = $scope.form.emailHistoryId||0;		
		MailingHistorySrvc.getMailingHistory(id).then(function(res){
			if(res.status == true){
				$scope.form.emailSubject = res.item.emailSubject;
				$scope.form.emailBody = res.item.emailBody;
			}else{
				bootbox.alert(res.message);
			}
			$timeout(function(){isSpinnerBar(false);},500)
		});
	};
});