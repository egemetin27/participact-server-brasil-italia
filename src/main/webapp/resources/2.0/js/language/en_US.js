moment.createFromInputFallback = function(config) { config._d = new Date(config._i); };
var start = moment();
var end = moment().add(30,'days');
$('#reportrange').daterangepicker({
			opens: 'left' ,
			startDate: moment(),
			endDate: moment().add(30,'days'),
			dateLimit: {
				days: 366
			},
			showDropdowns: false,
			showWeekNumbers: false,
			timePicker: true,
			timePickerIncrement: 10,
			timePicker24Hour: true,
			format: 'DD/MM/YYYY',
			ranges: {
				'Today': [moment().startOf('day'), moment().endOf('day')],
				'Tomorrow': [moment().add(1,'days'), moment().add(1,'days')],
				'Next 7 Days': [moment(),moment().add(6,'days')],
				'Next 30 Days': [moment(),moment().add(29,'days')],
				'Next 365 Days': [moment(),moment().add(365,'days')],
				'This Month': [moment().startOf('month'), moment().endOf('month')],
				'Next Month': [moment().add(1,'month').startOf('month'), moment().add(1,'month').endOf('month')]
			},
			buttonClasses: ['btn'],
			applyClass: 'green',
			cancelClass: 'default',
			separator: ' - ',
			locale: {
				applyLabel: 'Apply',
				fromLabel: 'From',
				toLabel: 'To',
				customRangeLabel: 'Custom Range',
				daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
				monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
				firstDay: 1
			}
		},
		function (start, end) {
			$('#reportrange span').html(start.format('DD/MM/YYYY HH:mm') + ' - ' + end.format('DD/MM/YYYY HH:mm'));
			$('#input-date-range-from').val(start.format('YYYY-MM-DD HH:mm')+':00').trigger('change');
			$('#input-date-range-to').val(end.format('YYYY-MM-DD HH:mm')+':59').trigger('change')			
		}
	); 	
