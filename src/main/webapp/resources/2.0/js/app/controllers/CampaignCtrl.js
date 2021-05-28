/**
 * Campanhas
 */
ParticipActApp.controller('CampaignCtrl', function($scope, $timeout, $http, $window, $filter, $localStorage, PacticipActSrvc, CampaignSrvc, UserSystemSrvc, BASE_URL) {
	//clean
	$scope.time ={sensingDuration:0, duration:0};
	$scope.picked ={id:0,name:'',sensingDuration:{},duration:{},dt_format_start:'',dt_format_end:''};
	$scope.cleanCampaign=function(){
		$scope.campaigns=[],$scope.form={isActive:!1,isAdvancedSearch:false,filterBy:"",isCloudDownload:!1,sensingDuration:86400},
		$scope.filter={name:null,value:"",item:"",input:"TEXT"},$scope.hashmap=[],$scope.totalItems=0,$scope.currentPage=1,
		$scope.radioModel="10",$scope.maxSize=10,$scope.itemsPerPage = 10,$scope.selected_items=0,$scope.controller=""};
	$scope.outcome=0;
	$scope.isPublishingByTask = false;
	$scope.orderByColumn = 'Dates';
	$scope.orderByDesc = true;
	//Lista de itens
	$scope.initCampaign = function() {
	    $localStorage.$reset(), search = $scope.form, search.orderByColumn = $scope.orderByColumn, search.orderByDesc=$scope.orderByDesc,search.hashmap = $scope.form.isAdvancedSearch ? $scope.hashmap : [], search.count = $scope.radioModel, search.offset = $scope.currentPage, isSpinnerBar(!0), CampaignSrvc.getListCampaign(search, search.count, search.offset).then(function(a) {
	        1 == a.status ? ($timeout(function() {
	            $scope.$apply(function() {
	                $scope.hashmap = a.item, $scope.campaigns = a.items, $scope.totalItems = a.total, $scope.currentPage = a.offset, $scope.itemsPerPage = a.count, angular.forEach($scope.campaigns, function(a, b) {
	                    $scope.campaigns[b].duration = $filter("secondsToDateTime")(a[5]), $scope.campaigns[b].sensingDuration = $filter("secondsToDateTime")(a[6]), 0 == a[8] ? $scope.campaigns[b].status = 0 : 1 == a[9] ? $scope.campaigns[b].status = 2 : $scope.campaigns[b].status = 1
	                })
	            })
	        }), 1 == search.isCloudDownload && PacticipActSrvc.display(a.message, a.resultType, 10)) : $scope.cleanCampaign(), $timeout(function() {
	            isSpinnerBar(!1)
	        }, 500)
	    })
	};	
	//Salvando
	$scope.saveCampaign = function() {
	    isSpinnerBar(!0), CampaignSrvc.saveCampaign($scope.form).then(function(n) {
	        1 == n.status ? $timeout(function() {
	        	$scope.outcome = n.outcome;
	        	$timeout(function(){
	        		$("#fixgotask")[0].click();	
	        	},100);
	            
	        }, 600) : (bootbox.alert(n.message), $timeout(function() {
	            isSpinnerBar(!1)
	        }, 500))
	    })
	};
	//Edicao
	$scope.editCampaign=function(i){isSpinnerBar(!0),$timeout(function(){$window.location.href="edit/"+i},500)};
	$scope.getCampaign=function(){
		//Check
		if(isBrowser('Safaria')||isBrowser('Firefox')){
			bootbox.alert("Your browser does not support some features of HTML5!");
		}
		//Execute
		isSpinnerBar(true);
		if(!isBlank($localStorage.savedFormData)){
			$scope.form=$localStorage.savedFormData;
			if(!isBlank($scope.form.start)){
				$('#reportrange span').html($scope.form.start+' - '+ $scope.form.deadline);	
			}
            setInputValue("#duration-picker", $localStorage.savedFormData.duration||0);
            setInputValue("#duration-picker-2", $localStorage.savedFormData.sensingDuration||0);			
			$timeout(function(){isSpinnerBar(false);},10);
			$timeout(function(){$( "#name").focus();},300);
			
			if(!isBlank($scope.form.emailSystemId)){
				setSelect2me('emailSystemId',$scope.form.emailSystemId);
			}			
		}else{
			$scope.form={canBeRefused:true,isSendEmail:false, isDuration: true, sensingDuration:86400},PacticipActSrvc.isDebug()===!0&&PacticipActSrvc.fake($scope),isSpinnerBar(false);
			$timeout(function(){$( "#name").focus();},300);
		}
	};
	$scope.setCampaign = function(a) {
		//Function
	    isSpinnerBar(true); 
	    CampaignSrvc.getCampaign(a).then(function(a) {
	    	if(a.status == true){
	    		$timeout(function() {
	    			if(!isBlank(a.item.start.millis)){
	    				a.item.start = new Date(a.item.start.millis);
	    				a.item.dt_format_start = moment(a.item.start).format("D/M/YYYY HH:mm:ss");
	    			}else{
	    				a.item.start = new Date(a.item.start);
	    				a.item.dt_format_start = moment(a.item.start).format("D/M/YYYY HH:mm:ss");
	    			}
	    			
	    			if(!isBlank(a.item.deadline.millis)){
	    				a.item.deadline = new Date(a.item.deadline.millis);
	    				a.item.dt_format_end = moment(a.item.deadline).format("D/M/YYYY HH:mm:ss");
	    			}else{
	    				a.item.deadline = new Date(a.item.deadline);
	    				a.item.dt_format_end = moment(a.item.deadline).format("D/M/YYYY HH:mm:ss");
	    			}
	    			
	    			$scope.form = a.item;
	    			
	    			if(!isBlank($scope.form.emailSystemId)){
	    				setSelect2me('emailSystemId',$scope.form.emailSystemId);
	    			}			

	    			setInputValue("#duration-picker", a.item.duration);
	    			setInputValue("#duration-picker-2", a.item.sensingDuration);
	    			$scope.time.duration = $filter("secondsToDateTime")(a.item.duration);
	    			$scope.time.sensingDuration = $filter("secondsToDateTime")(a.item.sensingDuration);
	    			try{
	    				$scope.campaignTasks.hasActions = a.item.actions.length > 0;
	    				$scope.campaignTasks.actions = $scope.campaignTasks.hasActions ? a.item.actions : []
	    				angular.forEach($scope.campaignTasks.actions, function(value) {
	    					value.hasDuration = false;
	    					value.duration = {};
	    					if(!isBlank(value.duration_threshold) && value.duration_threshold > 0){
	    						value.hasDuration = true;
	    						value.duration = $filter("secondsToDateTime")(value.duration_threshold);
	    					}
	    					if(value.type == "ACTIVITY_DETECTION"){
	    						$scope.campaignTasks.hasActivityDetection =  true;
	    					}
	    					if(value.type == "PHOTO"){
	    						$scope.campaignTasks.hasPhotos =  true;
	    					}
	    					if(value.type == "QUESTIONNAIRE"){
	    						value.numeric_threshold = value.questions.length;
	    					}
	    				});
	    				if(!isBlank(a.item.notificationArea) && a.item.notificationArea.length > 10){
	    					$scope.campaignTasks.hasNotificationArea = true;
	    					$scope.campaignTasks.hasActions = true;
	    					$scope.campaignTasks.notificationArea = a.item.notificationArea;
	    				}
	    				if(!isBlank(a.item.activationArea) && a.item.activationArea.length > 10){
	    					$scope.campaignTasks.hasActivationArea = true;
	    					$scope.campaignTasks.hasActions = true;
	    					$scope.campaignTasks.activationArea = a.item.activationArea;
	    				}            	
	    				if(a.item.assign){
	    					$scope.campaignTasks.hasActions = true;
	    					$scope.campaignTasks.hasAssign = true;
	    					$scope.campaignTasks.selectAll = a.item.selectAll;
	    					$scope.campaignTasks.assignAvailable = a.item.assignAvailable;
	    					$scope.campaignTasks.assignSelected = a.item.assignSelected;
	    					$scope.campaignTasks.assignFilter = JSON.parse(a.item.assignFilter);
	    				}	       
	    				if($( "#createdByAuthor" ).length){
	    					UserSystemSrvc.getUserSystemName(a.item.parentId).then(function(res){
	    						$scope.form.created_by = res.status?res.message:'';
	    					});
	    				}	            	
	    			}catch(err){
	    				console.log(err);
	    				$timeout(function(){$( "#name").focus();},1000);
	    			}
	    		}, 100);	    		
	    	}
	    	isSpinnerBar(false);
	    });
	};
	//Odernacao
	$scope.setOrderBy = function(col){
		$scope.orderByDesc = !$scope.orderByDesc;
		$scope.orderByColumn = col;
		$timeout(function(){
			$scope.initCloudSearch();
		},50);
	};
	//Abrindo confirmacao
	$scope.beginPublishingById = function(id){
		$scope.isPublishingByTask = true;
		$scope.openPublishing(id, $scope.form.name, $scope.form.sensingDuration, $scope.form.duration, $scope.form.start, $scope.form.deadline, $scope.form.description, $scope.form.canBeRefused);
	};
	//Iniciando processo de confirmacao
	$scope.beginPublishing = function(key_item){
		var selected = $scope.campaigns[key_item];
		if(!isBlank(selected[3].millis)){
			$scope.openPublishing(selected[0], selected[1],selected[6],selected[5],selected[3].millis,selected[4].millis,selected[2],selected[10]);
		}else{
			$scope.openPublishing(selected[0], selected[1],selected[6],selected[5],selected[3],selected[4],selected[2],selected[10]);	
		}
	};
	//Abrindo modal
	$scope.openPublishing = function(id, name, sensingDuration, duration, start, deadline, desc, canBeRefused){
		$scope.picked ={id:id,name:name,sensingDuration:$filter("secondsToDateTime")(sensingDuration),duration:$filter("secondsToDateTime")(duration),dt_format_start:$filter("dateFormat")(start),dt_format_end:$filter("dateFormat")(deadline),description:desc,canBeRefused:canBeRefused};
		$timeout(function(){ $('#confirmationPublish').modal('toggle');	},100);
	};
	//Confirmacao de publicacao
	$scope.confirmPublish = function(){
		isSpinnerBar(true);	
		CampaignSrvc.confirmPublish($scope.picked.id).then(function(res) {
			if(res.status == true){
				PacticipActSrvc.display(res.message);
				if($scope.isPublishingByTask){
					$window.location.reload();
				}else{
					$scope.initCloudSearch();	
				}
			}else{
				bootbox.alert(res.message);
			}
			isSpinnerBar(false);	
		});
		$timeout(function(){ $('#confirmationPublish').modal('hide');},100);
	};
	//se publicado
	$scope.isPublish=function(){$scope.form.isActive=!1,(1==$scope.form.typeId||2==$scope.form.typeId)&&($scope.form.isActive=!0)};
	//Reenvia os emails
	$scope.resendEmail =function(id){
		isSpinnerBar(true);	
		CampaignSrvc.resendEmail(id, $scope.form).then(function(res) {
			if(res.status == true){
				PacticipActSrvc.display(res.message);
				$timeout(function(){ $('#resendEmailModal').modal('hide');},100);
			}else{
				bootbox.alert(res.message);
			}
			isSpinnerBar(false);	
		});		
	};
	//Ir para tarefas
	$scope.gotoTasks = function(i){isSpinnerBar(!0),$timeout(function(){$window.location.href=BASE_URL+"/protected/campaign-task/index/"+i},500)};
	//Copiar
	$scope.copyTask = function(id){
		isSpinnerBar(true);
		$timeout(function(){
			CampaignSrvc.copyTask(id).then(function(res) {
				if(res.status == true){
					PacticipActSrvc.display(res.message);
					$window.location.href = BASE_URL+"/protected/campaign/index";
				}else{
					bootbox.alert(res.message);
					isSpinnerBar(false);		
				}		
			});
		},50);
	};
	//Convidar
	$scope.inviteTask = function(id){
		isSpinnerBar(true);
		$timeout(function(){
			CampaignSrvc.inviteTask(id).then(function(res) {
				if(res.status == true){
					PacticipActSrvc.display(res.message);
					$window.location.href = BASE_URL+"/protected/campaign/index";
				}else{
					bootbox.alert(res.message);
					isSpinnerBar(false);
				}
			});
		},50);
	};
	//Busca avancada
	$scope.initAdvancedSearch=function(){$scope.form={isActive:!1,isAdvancedSearch:!0,filterBy:"",isCloudDownload:!1},$scope.filter={name:null,value:"",item:"",input:"TEXT"},$scope.hashmap=[]};
	$scope.initSimpleSearch=function(){$scope.form={isActive:!1,isAdvancedSearch:!1,filterBy:"",isCloudDownload:!1, parentId:null},$scope.filter={name:null,value:"",item:"",input:"TEXT"},$scope.hashmap=[]};
	$scope.initCloudDownload=function(){$scope.form.isCloudDownload=!0,$timeout(function(){$scope.initCampaign()},100)};
	$scope.initCloudSearch=function(){$scope.form.isCloudDownload=!1,$timeout(function(){$scope.initCampaign()},100)};
	$scope.initCloudExport=function(){
		if($scope.selected_items > 0){
			var listOfIds = [];
			angular.forEach($scope.campaigns, function (item, k) {
				if(item.Selected === true){
					listOfIds.push({key:"FILTER_TASK_ID",value:item[0],item:item[0],label:"info"});
				}
			});		
			isSpinnerBar(true);
			CampaignSrvc.getCloudExport({hashmap:listOfIds}).then(function(res){
				if(res.status == true){
					PacticipActSrvc.display(res.message, res.resultType);
				}else{
					bootbox.alert(res.message);
				}
				isSpinnerBar(false);
			});			
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
				$timeout(function(){ $scope.initCloudSearch();},10);
			},100);
		}
	};	
	//Remove um filtro
	$scope.removeFilterBy=function(e){
		e>-1&&$scope.hashmap.splice(e,1);
		$timeout(function(){ $scope.initCloudSearch();},10);
	};
	//Collapse
	$scope.collapseDetails=function(a){$scope.form.checkDetails=!$scope.form.checkDetails,CampaignSrvc.collapseDetails(a,{checkDetails:$scope.form.checkDetails})};
	//Alterando o tipo do filtro
	$scope.onChangeFilterBy = function() {
		var T = $scope.form.filterBy;
		if(!isBlank(T)){
			if(["FILTER_PIPELINE_TYPE","FILTER_CANBEREFUSED","FILTER_HAS_PHOTO","FILTER_HAS_QUESTION","FILTER_PARENT_ID"].includes(T)){   	
				$scope.filter.input = T;
			}else if(["FILTER_START","FILTER_DEADLINE"].includes(T)){
				$scope.filter.input = "DATEPICKER";
			} else{
				$scope.filter.input = "TEXT";
			}
		}else{
			$scope.filter.input = "TEXT";
		}
	};
	//Selected filter by index
	$scope.onChangeFilterSelected=function(e){1==$scope.form.isAdvancedSearch&&($scope.filter.value=$("#"+e+" option:selected").text(),$scope.filter.item=$scope.form[e])};
	//FILTER GENDER
	$scope.onChangeGender=function(){$timeout(function(){$scope.onChangeFilterSelected("gender")},100)};
	$scope.onChangeYesNo = function(){$timeout(function(){$scope.onChangeFilterSelected("yesandnot")},100)};
	$scope.onChangePipelineType = function(){$timeout(function(){$scope.onChangeFilterSelected("pipelineType")},100)};
	$scope.onChangePipelineType = function(){$timeout(function(){$scope.onChangeFilterSelected("pipelineType")},100)};
	$scope.onChangeParentUser = function(){
		$timeout(function(){$scope.onChangeFilterSelected("parentId")},100)
		$scope.pageChanged();	
	};	
	//Remover varios itens
	$scope.removeSelected=function(e,a,c,t,o){var s="";$scope.selected_items<1?bootbox.alert(o):(angular.forEach($scope.campaigns,function(e,a){e.Selected===!0&&(s=s+'<b class="has-error">'+e[1]+"</b><br/>")}),bootbox.dialog({title:c,message:t+" : <p>"+s+"</p>",buttons:{success:{label:a,className:"btn-default",callback:function(){}},danger:{label:e,className:"btn-danger",callback:function(){isSpinnerBar(!0),angular.forEach($scope.campaigns,function(e,a){e.Selected===!0&&$scope.removed(e[0])})}}}}))};
	//executa a acao de remover
	$scope.removed = function(a) {
	    isSpinnerBar(!0), CampaignSrvc.removeCampaign(a).then(function(i) {
	        1 == i.status ? ($("#campaigns-tr-" + a).addClass("hide"), PacticipActSrvc.display(i.message), $scope.selectedAll=false, $timeout(function(){$scope.checkAll()},1000)): ($("#campaigns-tr-" + a).addClass("danger"), PacticipActSrvc.display(i.message, "error")), isSpinnerBar(!1)
	    })
	};	
	//Alterando paginacao
	$scope.pageChanged = function (){ $scope.initCampaign(); }
	//Select all
	$scope.checkAll=function(){$scope.selectedAll?($scope.selectedAll=!0,$scope.selected_items=$scope.totalItems):($scope.selectedAll=!1,$scope.selected_items=0),angular.forEach($scope.campaigns,function(e){e.Selected=$scope.selectedAll})};
	//Se selecionado
	$scope.isSelected=function(e){$("#"+e).is(":checked")?$scope.selected_items++:$scope.selected_items--};
	//Init
	$scope.cleanCampaign();
	//Jquery + Angularjs
	$(".pa-filter").keypress(function(a){var b=a.keyCode||a.which;"13"==b&&$scope.initCampaign()});
	$(".pa-advanced").keypress(function(a){var b=a.keyCode||a.which;"13"==b&&$scope.addFilterBy()});
	$("#HeyMan").change(function() {
		isSpinnerBar(!0);
		$timeout(function(){
			$window.location.reload();
			console.log("Hey, Man!");
		},500);
	});	
	$("#HeyLocale").change(function() {
		$timeout(function(){$localStorage.savedFormData = $scope.form;	},10);
		console.log("Hey, Locale!");
	});	
	//Menu
	if(!$("#isDashboard" ).length) {
		setMenuOpen('pa-menu-campaigns','pa-submenu-campaign');	
	}
});