ParticipActApp.controller('AbuseReportCtrl', function($scope, $timeout, $http, $window, $localStorage, PacticipActSrvc, AbuseReportSrvc) {
	//clean
	$scope.cleanAbuseReport=function(){$scope.abuses=[],$scope.form={issueId:0},$scope.totalItems=0,$scope.currentPage=0,$scope.radioModel="10",$scope.maxSize=10,$scope.itemsPerPage = 10,$scope.selected_items=0,$scope.controller=""};
	//Lista de itens
	$scope.initAbuseReport=function(){search=$scope.form,search.count=$scope.radioModel,search.offset=$scope.currentPage,isSpinnerBar(!0),AbuseReportSrvc.getListAbuseReport(search,search.count,search.offset).then(function(o){1==o.status?$timeout(function(){$scope.$apply(function(){$scope.abuses=o.items,$scope.totalItems=o.total,$scope.currentPage=o.offset,$scope.itemsPerPage=o.count})}):$scope.cleanAbuseReport(),$timeout(function(){isSpinnerBar(!1)},500)})};
	//Salvando
	$scope.saveAbuseReport=function(k){
		isSpinnerBar(true);
		var i = $scope.abuses[k];
		var f = {id:i[0], comment:i[1]};		
		$timeout(function(){
			AbuseReportSrvc.saveAbuseReport(f).then(function(res){
				if(res.status == true){
					PacticipActSrvc.display(res.message);
				}
				isSpinnerBar(false);
			});
		},100);
	};
	//executa a acao de remover
	$scope.removedAbuseReport=function(s){isSpinnerBar(!0),AbuseReportSrvc.removeAbuseReport(s).then(function(e){1==e.status?($("#abuses-tr-"+s).addClass("hide"),PacticipActSrvc.display(e.message)):($("#abuses-tr-"+s).addClass("danger"),PacticipActSrvc.display(e.message,"error")),isSpinnerBar(!1)})};
	//Alterando paginacao
	$scope.pageChanged = function (){ $scope.initAbuseReport(); }
	//Select all
	$scope.checkAll=function(){$scope.selectedAll?($scope.selectedAll=!0,$scope.selected_items=$scope.totalItems):($scope.selectedAll=!1,$scope.selected_items=0),angular.forEach($scope.abuses,function(e){e.Selected=$scope.selectedAll})};
	//Se selecionado
	$scope.isSelected=function(e){$("#"+e).is(":checked")?$scope.selected_items++:$scope.selected_items--};
	//Init
	$scope.cleanAbuseReport();
});