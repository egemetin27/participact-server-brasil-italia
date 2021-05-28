ParticipActApp.directive('stringToNumber', function() {
  return {
    require: 'ngModel',
    link: function(scope, element, attrs, ngModel) {
      ngModel.$parsers.push(function(value) {
        return '' + value;
      });
      ngModel.$formatters.push(function(value) {
        return parseFloat(value, 10);
      });
    }
  };
});

ParticipActApp.directive('audios', function($sce) {
	return {
		restrict: 'A',
		scope: { code:'=' },
		replace: true,
		template: '<video ng-src="{{url}}" controls preload="metadata"></video>',
		link: function (scope) {
			scope.$watch('code', function (newVal, oldVal) {
				if (newVal !== undefined) {
					scope.url = $sce.trustAsResourceUrl(newVal);
				}
			});
		}
	};
});

ParticipActApp.directive('videos', function($sce) {
	return {
		restrict: 'A',
		scope: { code:'=' },
		replace: true,
		template: '<video ng-src="{{url}}" controls preload="metadata"></video>',
		link: function (scope) {
			scope.$watch('code', function (newVal, oldVal) {
				if (newVal !== undefined) {
					scope.url = $sce.trustAsResourceUrl(newVal);
				}
			});
		}
	};
});

ParticipActApp.filter('toNum', function() {
    return function(input) {
      return parseInt(input, 10);
    };
});

ParticipActApp.filter('stripslashes', function () {
    return function (data) {
    	if(data != null && data.length > 1){
    		return stripslashes(data);
    	}else{
    		return (data != 'null')?data:'';
    	}
    };
});

ParticipActApp.directive('compileHtml', function ($compile) {
	return function (scope, element, attrs) {
		scope.$watch(
				function(scope) {
					return scope.$eval(attrs.compileHtml);
				},
				function(value) {
					element.html(value);
					$compile(element.contents())(scope);
				}
		);
	};
});

ParticipActApp.filter('comma2decimal', function () {
	return function(input) {
		return input.toLocaleString('de-DE');
	};
});

ParticipActApp.filter('userimage', function () {
    return function (data) {
    	if(data != null && data.length > 1){
    		return data;
    	}else{
    		return 'http://placehold.it/150/53C323/fff?text=Pa';
    	}
    };
});

ParticipActApp.directive('focusMe', ['$timeout', '$parse', function ($timeout, $parse) {
    return {
        //scope: true,   // optionally create a child scope
        link: function (scope, element, attrs) {
            var model = $parse(attrs.focusMe);
            scope.$watch(model, function (value) {
                console.log('value=', value);
                if (value === true) {
                    $timeout(function () {
                        element[0].focus();
                    });
                }
            });
            // to address @blesh's comment, set attribute value to 'false'
            // on blur event:
            element.bind('blur', function () {
                console.log('blur');
                scope.$apply(model.assign(scope, false));
            });
        }
    };
}]);

ParticipActApp.filter('dateFormat', function($filter){
	return function(input){
		if(input == null){ 
			return ""; 
		} 
		var _date = $filter('date')(new Date(input), 'dd/MM/yyyy HH:mm:ss');
		return _date.toUpperCase();
	};
});

ParticipActApp.filter('dateFormatDefault', function($filter){
	return function(input){
		if(input == null){ 
			return ""; 
		} 
		var _date = $filter('date')(new Date(input), 'yyyy-MM-dd HH:mm:ss');
		return _date.toUpperCase();
	};
});

ParticipActApp.filter('dateFormatWithouHours', function($filter){
	return function(input){
		if(input == null){ 
			return ""; 
		} 
		var _date = $filter('date')(new Date(input), 'dd/MM/yyyy');
		return _date.toUpperCase();
	};
});

ParticipActApp.filter('secondsToDateTime', [function() {
	/**
	 * This code returns a date string formatted manually.
	 * Code "new Date(1970, 0, 1).setSeconds(seconds)" returns malformed output on days.
	 * Eg. 4 days, magically becomes 5, 15 becomes 16 and so on...;
	 * */
	return function(seconds) {
		var days = Math.floor(seconds/86400);
		var hours = Math.floor((seconds % 86400) / 3600);
		var mins = Math.floor(((seconds % 86400) % 3600) / 60);
		var secs = ((seconds % 86400) % 3600) % 60;
		var minutes = Math.floor(seconds / 60);
		return {
			d : days,
			h : ('00' + hours).slice(-2),
			m : ('00' + mins).slice(-2),
			s : ('00' + secs).slice(-2),
			minutes : (minutes<60)?('00' + minutes).slice(-2):minutes,
		};
		//return (days > 0 ? days+'d ' : '') +' ( '+ ('00'+hours).slice(-2) +'h ' + ('00'+mins).slice(-2)+'m ' + ('00'+secs).slice(-2)+'s )' ;
	};
}]);

ParticipActApp.filter('trustUrl', function ($sce) {
	return function(url) {
		return $sce.trustAsResourceUrl(url);
	};
});

ParticipActApp.directive('copyToClipboard', function (toastr) {
	return {
		restrict: 'A',
		link: function (scope, elem, attrs) {
			elem.click(function () {
				if (attrs.copyToClipboard) {
					var $temp_input = $("<input>");
					$("body").append($temp_input);
					$temp_input.val(attrs.copyToClipboard).select();
					document.execCommand("copy");
					toastr.success("CTRL + C "+$temp_input.val());
					$temp_input.remove();
				}
			});
		}
	};
});