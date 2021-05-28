ParticipActApp.controller('ParticipantFilterCtrl', function($scope, $timeout, $http, $window, $localStorage, PacticipActSrvc, ParticipantSrvc) {
	//clean
	$scope.cleanParticipant = function(){
		$scope.participants = [];
		$scope.filter = {name:null,value:'',item:'',input:'TEXT'};
		//Pag
		$scope.totalItemsU = 0; $scope.currentPageU = 0; $scope.radioModelU = '10'; $scope.maxSizeU = 10; $scope.selected_itemsU = 0; $scope.itemsPerPageU = 10;
		$scope.totalItems = 0; $scope.currentPage = 0;	 $scope.radioModel = '10'; $scope.maxSize = 10; $scope.selected_items = 0; $scope.itemsPerPage = 10;
		//Checkbox
		$scope.selected_items = 0;
		$scope.controller = '';
	};
	//Lista de itens
	$scope.initParticipantFilter = function(taskId){//Carrega todos os itens
		isSpinnerBar(true);	
		$localStorage.$reset();
		$timeout(function(){
		search = {};
		search.hashmap = $scope.hashmap;
		search.count = $scope.radioModel;
		search.offset = $scope.currentPage;
		search.isAdvancedSearch = true;
		search.taskId = typeof taskId !== 'undefined' ? taskId : null;
		$scope.taskId = search.taskId;
		//console.log(search);
		ParticipantSrvc.getListParticipant(search, search.count, search.offset).then(function(res){
			if(res.status == true){
				$timeout(function(){
					$scope.$apply(function(){
						$scope.participants = res.items;
						$scope.totalItems = res.total; $scope.currentPage = res.offset; $scope.itemsPerPage = res.count;
						$scope.totalItemsU = res.total; $scope.currentPageU = res.offset;$scope.itemsPerPageU = res.count;
					});
				});
				if(search.isCloudDownload==true){ PacticipActSrvc.display(res.message, res.resultType, 10); }
			}
			$timeout(function(){ isSpinnerBar(false);}, 500);
		});
		}, 500);
	};	
	//Busca avancada
	$scope.initAdvancedSearch = function(){
		$scope.form = {isActive:false, isAdvancedSearch: true,filterBy:'',isCloudDownload:false};
		$scope.filter = {name:null,value:'',item:'',input:'TEXT'};
		$scope.hashmap = [];		
	};
	$scope.initSimpleSearch = function(){
		$scope.form = {isActive:false, isAdvancedSearch: false,filterBy:'', isCloudDownload:false};
		$scope.filter = {name:null,value:'',item:'',input:'TEXT'};
		$scope.hashmap = [];
	};
	$scope.initCloudDownload = function(){
		$scope.form.isCloudDownload = true;
		$timeout(function(){$scope.initParticipantFilter($scope.taskId);},100);
	};
	$scope.initCloudSearch = function(){
		$scope.form.isCloudDownload = false;
		if($( "#isSelectAll" ).length || $("#isPushNotifications" ).length || $("#isParticipantFilter" ).length) {
			$scope.form.isAdvancedSearch = true;
			$timeout(function(){$scope.initParticipantFilter($scope.taskId);},100);
		}else{	
			$timeout(function(){$scope.initParticipantFilter($scope.taskId);},100);
		}
	};
	//Adiciona um filtro
	$scope.addFilterBy = function(){
		if(!isBlank($scope.form.filterBy) && !isBlank($scope.filter.value)){
			$timeout(function(){
				$scope.hashmap.push({key:$scope.form.filterBy,value:$scope.filter.value,item:$scope.filter.item,label:"info"});
				$scope.form.filterBy = '';
				$scope.filter.value = '';
				$scope.filter.input='TEXT';
				$scope.filter.item = '';
				setSelect2me('filterBy',0);
				$scope.initCloudSearch();
			},100);
		}
	};		
	//Remove um filtro
	$scope.removeFilterBy=function(e){
		e>-1&&$scope.hashmap.splice(e,1);
		$scope.initCloudSearch();
	};
	//Alterando o tipo do filtro
	$scope.onChangeFilterBy=function(){
		var T=$scope.form.filterBy;
		if(!isBlank(T)){
			if(["FILTER_GENDER","FILTER_UNICOURSE","FILTER_SCHOOLCOURSEID","FILTER_INSTITUTIONID","FILTER_DOCUMENTIDTYPE"].includes(T)){
				$scope.filter.input = T;
			}else if(["FILTER_START","FILTER_DEADLINE"].includes(T)){
				$scope.filter.input = "DATEPICKER";
			}else{
				$scope.filter.input = "TEXT";
			}
		}else{
			$scope.filter.input = "TEXT";
		}
	};
	//Selected filter by index
	$scope.onChangeFilterSelected=function(e){
		1==$scope.form.isAdvancedSearch&&($scope.filter.value=$("#"+e+" option:selected").text(),$scope.filter.item=$scope.form[e])
		if($( "#isSelectAll" ).length ) { 0==$('#isSelectAll').bootstrapSwitch('state')&&($scope.filter.value=$("#"+e+" option:selected").text(),$scope.filter.item=$scope.form[e])}
	};
	//FILTER GENDER
	$scope.onChangeGender=function(){$timeout(function(){$scope.onChangeFilterSelected("gender")},100)};
	//Filter FILTER_UNICOURSE
	$scope.onChangeUniCourse=function(){$timeout(function(){$scope.onChangeFilterSelected("uniCourse")},100)};
	//FILTER_SCHOOLCOURSEID
	$scope.onChangeSchoolCourse=function(){$timeout(function(){$scope.onChangeFilterSelected("schoolCourseId")},100)};
	//FILTER_INSTITUTIONID
	$scope.onChangeInstitutionId=function(){$timeout(function(){$scope.onChangeFilterSelected("institutionId")},100)};
	//FILTER_DOCUMENTIDTYPE
	$scope.onChangeDocumentIdType=function(){$timeout(function(){$scope.onChangeFilterSelected("documentIdType")},100)};
	//Alterando paginacao
	$scope.pageChanged = function (){ $scope.initParticipantFilter($scope.taskId); }
	$scope.pageChangedUser	= function (){
		$scope.totalItems = $scope.totalItemsU;
		$scope.currentPage = $scope.currentPageU;
		$scope.itemsPerPage = $scope.itemsPerPageU;		
		$timeout(function(){
			$scope.initParticipantFilter($scope.taskId);	
		},100);
	}
	//Select all
	$scope.checkAll=function(){$scope.selectedAll?($scope.selectedAll=!0,$scope.selected_items=$scope.totalItems):($scope.selectedAll=!1,$scope.selected_items=0),angular.forEach($scope.participants,function(e){e.Selected=$scope.selectedAll})};
	//Se selecionado
	$scope.isSelected=function(e){$("#"+e).is(":checked")?$scope.selected_items++:$scope.selected_items--};
	//Change Switch
	$scope.makeSwitchSelectAll = function() { 
		$timeout(function() { 
			$scope.form.isSelectAll = $('#isSelectAll').bootstrapSwitch('state'); 
		});
	};	
	//Init
	$scope.cleanParticipant();
	//Jquery + Angularjs
	$(".pa-filter").keypress(function(a){var b=a.keyCode||a.which;"13"==b&&$scope.initParticipantFilter($scope.taskId)});
	$(".pa-advanced").keypress(function(a){var b=a.keyCode||a.which;"13"==b&&$scope.addFilterBy()});
	//Switch
	$('#isSelectAll').on('switchChange.bootstrapSwitch', function() {
		$scope.makeSwitchSelectAll();
	});
	$('#isSelectAll').bootstrapSwitch('state', false);	
	$("#HeyLocale").change(function() {
		$timeout(function(){$localStorage.savedFormData = $scope.form;	},10);
		console.log("Hey, Locale!");
	});	
});