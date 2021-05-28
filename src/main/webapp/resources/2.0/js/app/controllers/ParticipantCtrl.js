ParticipActApp.controller('ParticipantCtrl', function($scope, $timeout, $http, $window, $localStorage, PacticipActSrvc, ParticipantSrvc) {
	//clean
	$scope.cleanParticipant = function(){
		$scope.participants = [];
		$scope.form = {isActive:false, isAdvancedSearch: false,filterBy:'',isCloudDownload:false};
		$scope.filter = {name:null,value:'',item:'',input:'TEXT'};
		$scope.hashmap = [];
		//Pag
		$scope.totalItems = 0; $scope.currentPage = 1;	 $scope.radioModel = '10'; $scope.maxSize = 10;	 $scope.itemsPerPage = 10;
		$scope.totalItemsU = 0; $scope.currentPageU = 0;	 $scope.radioModelU = '10'; $scope.maxSizeU = 10; $scope.selected_itemsU = 0; $scope.itemsPerPageU = 10;
		//Checkbox
		$scope.selected_items = 0;
		$scope.controller = '';
	};
	//Lista de itens
	$scope.initParticipant = function(){//Carrega todos os itens
		//Cache
		if (!isBlank($localStorage.ParticipActSearchParticipant)) {
			var search = $localStorage.ParticipActSearchParticipant;
			delete $localStorage.ParticipActSearchParticipant;
			$scope.form = search;
			//Search
			$scope.searchParticipant();
		} else {
			//Search
			$scope.searchParticipant();
		}
	};
	// Search
	$scope.searchParticipant = function(){
		$localStorage.$reset();
		search = $scope.form;
		search.hashmap = ($scope.form.isAdvancedSearch)?$scope.hashmap:[];
		search.count = $scope.radioModel;
		search.offset = $scope.currentPage;

		//console.log(search);
		isSpinnerBar(true);
		ParticipantSrvc.getListParticipant(search, search.count, search.offset).then(function(res){
			if(res.status == true){
				$timeout(function(){
					$scope.$apply(function(){
						$scope.hashmap = res.item;
						$scope.participants = res.items;
						$scope.totalItems = res.total; $scope.currentPage = res.offset; $scope.itemsPerPage = res.count;
						$scope.totalItemsU = res.total; $scope.currentPageU = res.offset;$scope.itemsPerPageU = res.count;
					});
				});
				if(search.isCloudDownload==true){ PacticipActSrvc.display(res.message, res.resultType, 10); }
				//Cache local
				delete $localStorage.ParticipActSearchParticipant;
				$timeout(function () { $localStorage.ParticipActSearchParticipant = $scope.form;}, 5);
			}else{
				$scope.cleanParticipant();
			}
			$timeout(function(){ isSpinnerBar(false);}, 500);
		});
	};
	//Salvando
	$scope.saveParticipant = function (){
		isSpinnerBar(true);	
		ParticipantSrvc.saveParticipant($scope.form).then(function(res){
			if(res.status == true){
				$timeout(function(){
					$('#fixgoback')[0].click();
				},600);
			}else{
				bootbox.alert(res.message);
				$timeout(function(){ isSpinnerBar(false);}, 500);
			}
		});
	};
	//Edicao
	$scope.editParticipant = function(id){
		isSpinnerBar(true);	
		$timeout(function(){
			$window.location.href = "edit/"+id;
		}, 500);
	};
	
	$scope.getParticipant = function(){
		isSpinnerBar(true);
		if(!isBlank($localStorage.savedFormData)){
			$scope.form=$localStorage.savedFormData;
			if(!isBlank($scope.form.schoolCourseId)){
				setSelect2me('schoolCourseId',$scope.form.schoolCourseId);
			}
			if(!isBlank($scope.form.institutionId)){
				setSelect2me('institutionId',$scope.form.institutionId);
			}
			$timeout(function(){isSpinnerBar(false);},10);
		}else{
			$scope.form = {};
			if(PacticipActSrvc.isDebug() === true){
				PacticipActSrvc.fake($scope);
			}	
			isSpinnerBar(false);
		}
	};
	
	$scope.setParticipant = function(j){
		isSpinnerBar(true);	
		ParticipantSrvc.getParticipant(j).then(function(res){
			if(res.status == true){
				$scope.form = res.item;
				$timeout(function(){
					try{
						var d =(res.item.birthdate.values[2] < 10) ? "0"+res.item.birthdate.values[2]:res.item.birthdate.values[2]; 
						var m =(res.item.birthdate.values[1] < 10) ? "0"+res.item.birthdate.values[1]:res.item.birthdate.values[1];
						var y =	res.item.birthdate.values[0];
						$scope.form.dt_format = d+"/"+m+"/"+y;
						$scope.form.birthdate = y+"-"+m+"-"+d;
						
						$timeout(function(){
							setSelect2me('schoolCourseId',$scope.form.schoolCourseId);
							setSelect2me('institutionId',$scope.form.institutionId);
							setSelect2me('gender',$scope.form.gender);
							setSelect2me('uniCourse',$scope.form.uniCourse);
						},100);						
					}catch(err){
						console.log(err);
					}
				},100);
			}
			isSpinnerBar(false);
		});
	};
	
	$scope.isPublish = function(){
		$scope.form.isActive= false;
		if($scope.form.typeId == 1 || $scope.form.typeId == 2){
			$scope.form.isActive= true;	
		}
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
		$timeout(function(){$scope.initParticipant();},100);
	};
	$scope.initCloudSearch = function(){
		$scope.form.isCloudDownload = false;
		if($( "#isSelectAll" ).length || $("#isPushNotifications" ).length) {
			$scope.form.isAdvancedSearch = true;
			$timeout(function(){$scope.initParticipant();},100);
		}else{	
			$timeout(function(){$scope.initParticipant();},100);
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
				//if($( "#isSelectAll" ).length || $("#isPushNotifications" ).length) {
					$scope.initCloudSearch();
				//}
			},100);
		}
	};		
	//Remove um filtro
	$scope.removeFilterBy=function(e){
		e>-1&&$scope.hashmap.splice(e,1);
		//if($( "#isSelectAll" ).length || $("#isPushNotifications" ).length) {
			$scope.initCloudSearch();	
		//}
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
	//Remover varios itens
	$scope.removeSelected=function(e,a,c,t,o){var s="";$scope.selected_items<1?bootbox.alert(o):(angular.forEach($scope.participants,function(e,a){e.Selected===!0&&(s=s+'<b class="has-error">'+e[1]+"</b><br/>")}),bootbox.dialog({title:c,message:t+" : <p>"+s+"</p>",buttons:{success:{label:a,className:"btn-default",callback:function(){}},danger:{label:e,className:"btn-danger",callback:function(){isSpinnerBar(!0),angular.forEach($scope.participants,function(e,a){e.Selected===!0&&$scope.removed(e[0])})}}}}))};
	//executa a acao de remover
	$scope.removed=function(a){isSpinnerBar(!0),ParticipantSrvc.removeParticipant(a).then(function(i){1==i.status?($("#participants-tr-"+a).addClass("hide"),PacticipActSrvc.display(i.message)):($("#participants-tr-"+a).addClass("danger"),PacticipActSrvc.display(i.message,"error")),isSpinnerBar(!1)})};	
	//Alterando paginacao
	$scope.pageChanged = function (){ $scope.initParticipant(); }
	$scope.pageChangedUser	= function (){
		console.log('pageChangedUser');
		$scope.totalItems = $scope.totalItemsU;
		
		//console.log($scope.currentPage);
		//console.log($scope.currentPageU);
		
		$scope.currentPage = $scope.currentPageU;
		$scope.itemsPerPage = $scope.itemsPerPageU;		
		$timeout(function(){
			$scope.initParticipant();	
		},100);
	}
	//Select all
	$scope.checkAll=function(){$scope.selectedAll?($scope.selectedAll=!0,$scope.selected_items=$scope.totalItems):($scope.selectedAll=!1,$scope.selected_items=0),angular.forEach($scope.participants,function(e){e.Selected=$scope.selectedAll})};
	//Se selecionado
	$scope.isSelected=function(e){$("#"+e).is(":checked")?$scope.selected_items++:$scope.selected_items--};
	//Init
	if (! $( "#isPushNotification" ).length &&  ! $( "#isCampaignTaskAssignForm" ).length) {
		$scope.cleanParticipant();
	}else{
		$scope.participants = [];
		$scope.form = {isActive:false, isAdvancedSearch: false,filterBy:'',isCloudDownload:false};
		$scope.filter = {name:null,value:'',item:'',input:'TEXT'};
		$scope.hashmap = [];		
		$scope.totalItems = 0; $scope.currentPage = 0;	 $scope.radioModel = '10'; $scope.maxSize = 10; $scope.selected_items = 0; $scope.itemsPerPage = 10;
	}
	
	//Jquery + Angularjs
	$(".pa-filter").keypress(function(a){var b=a.keyCode||a.which;"13"==b&&$scope.initParticipant()});
	$(".pa-advanced").keypress(function(a){var b=a.keyCode||a.which;"13"==b&&$scope.addFilterBy()});
	$("#HeyLocale").change(function() {
		$timeout(function(){$localStorage.savedFormData = $scope.form;	},10);
		console.log("Hey, Locale!");
	});	
	//Menu
	if(!$("#isSelectAll" ).length&&!$("#isPushNotifications" ).length) {
		setMenuOpen('pa-menu-users','pa-submenu-participant');	
	}
});