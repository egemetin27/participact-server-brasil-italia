ParticipActApp.factory('PacticipActSrvc', function($q, $timeout, $http, $localStorage, toastr, ipsumService) {
	var deferred = $q.defer();
	var debug = false;
	var bell = true;//True em producao
	return {
		/**
		 * Fake Random
		 */
		fake: function($scope){
			var person = ipsumService.randomName('r');
			var email =  person.first + '.' + person.last + '@' + ipsumService.words(1) + ipsumService.randomItem(['.net','.org','.com','.biz']);
			var username = person.first + '.' + person.last;			
			var file = "https://unsplash.it/100/100";
			//isBackgroundPhoto('prizes-photo',$scope.file);
			$timeout(function(){
				$scope.$apply(function(){
					$scope.form = {
							username: username, 
							password: 'abcd1234',
							rpassword: 'abcd1234',
							npassword: 'abcd1234',
							provider: 'generic',
							name: person.first,
							title: ipsumService.words(3),
							fullname: person.first + '.' + person.last,
							surname:person.last,
							firstname: person.first,
							lastname: person.last,
							phone: getRandomInt(1000000000,9999999999),
							homePhoneNumber: getRandomInt(1000000000,9999999999),
							contactPhoneNumber: getRandomInt(1000000000,9999999999),
							documentNumber:getRandomInt(1000000000,9999999999),
							address: ipsumService.words(10),
							addressCity: ipsumService.words(2),
							addressPostalCode: getRandomInt(100000,900000),
							addressNumber: getRandomInt(100,900),
							addressState: ipsumService.words(1),
							addressCountry: ipsumService.words(1),
							complement: ipsumService.words(3),
							city: ipsumService.words(2),
							country: ipsumService.words(1),
							dt_birthday: '01/01/1980',
							points: getRandomInt() * 100,
							description: ipsumService.paragraphs(1),
							emailBody: ipsumService.paragraphs(2),
							isSendEmail: true,
							notes: ipsumService.paragraphs(1),
							content: ipsumService.paragraphs(3),
							uniYear:getRandomInt(1990,2030),
							schoolCourseId:getRandomInt(300,423),
							numericThreshold:getRandomInt(1,9),
							uniCourse:"GRADUATION",
							institutionId:getRandomInt(100,700),
							canBeRefused:true,
							duration:getRandomInt(60,360000),
							sensingDuration:getRandomInt(60,360000),
							documentidType:"DRIVING_LICENCE",
							gender:"FEMALE",
							image: file,
							media_url : file,
							brand : ipsumService.words(1),
							model : ipsumService.words(2),
							manufacturer : ipsumService.words(2),
							display : ipsumService.words(4),
							fingerprint : ipsumService.words(5),
							hardware : ipsumService.words(6),
							tags : ipsumService.words(8),
							type : ipsumService.words(1),
							changelist : ipsumService.words(3),
							officialEmail:email,
							email: email};			

				});
			},500);
		},
		/**
		 * toastr
		 */
		display:function(message, type, delay){
			type = typeof type !== 'undefined' ? type : 'success';
			delay = typeof delay !== 'undefined' ? delay : 0;
			if(type == 'success' || type == 'TYPE_SUCCESS'){
				toastr.success(message);
			}else if(type == 'warning' || type == 'TYPE_WARNING'){
				toastr.warning(message);
			}else if(type == 'error' || type == 'TYPE_ERROR'){
				toastr.error(message);
			}else{
				toastr.info(message);
			}
		},		
		/**
		 * Debug
		 */
		isDebug: function(){
			if(debug === true){
				console.log(' ---------- Debug Enable -----------');
			}
			return debug;
		},		
		/**
		 * Notificacoes
		 */
		isBell : function(){
			if(bell === true){
				console.log(' ---------- Bell Enable -----------');
			}
			return bell;
		}
	}
});	