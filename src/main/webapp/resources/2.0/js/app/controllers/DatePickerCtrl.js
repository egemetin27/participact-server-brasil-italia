ParticipActApp.controller('DatePickerCtrl', function($scope, $timeout, $http) {
	$('.datepicker-here').datepicker({
		maxDate : new Date(),		
		clearButton: true,
		dateFormat : 'dd/mm/yyyy',
		onSelect: function onSelect(fd, date) {
			var birthdate = moment(date).format('YYYY-MM-DD');
			$('.datepicker-here').trigger("change");
			$('#birthdate').val(birthdate).trigger("change");
			console.log(birthdate);
		}
	});
	
	$('.filterpicker-here').datepicker({
		todayButton : new Date(),
		clearButton: true,
		dateFormat : 'dd/mm/yyyy',
		onSelect: function onSelect(date) {
			$('.filterpicker-here').trigger("change");
			$('#filterpicker').trigger("change");
		}
	});	
	
	$('.datestart-here').datepicker({
		todayButton: new Date(),
		clearButton: true,
		range: false,
		dateFormat : 'dd/mm/yyyy hh:ii',
		timepicker:true,
		minutesStep:30,
		timeFormat: 'hh:ii',
		onSelect: function onSelect(fd, date) {
			$scope.form.start = moment(date).format('YYYY-MM-DD HH:mm');
			$('.datestart-here').trigger("change");
		}
	});	
	
	$('.dateend-here').datepicker({
		todayButton: new Date(),
		clearButton: true,
		range: false,
		dateFormat : 'dd/mm/yyyy hh:ii',
		timepicker:true,
		minutesStep:30,
		timeFormat: 'hh:ii',
		onSelect: function onSelect(fd, date) {
			$scope.form.deadline = moment(date).format('YYYY-MM-DD HH:mm');
			$('.dateend-here').trigger("change");
		}
	});	
	
	$('#duration-picker, #duration-picker-2').durationPicker({
		lang:'pt',
		showSeconds: false,
		showDays:true,
		autoclose: true
	});
});