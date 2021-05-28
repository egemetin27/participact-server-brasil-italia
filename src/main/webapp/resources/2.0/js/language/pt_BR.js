moment.createFromInputFallback = function(config) { config._d = new Date(config._i); };
var start = moment();
var end = moment().add(30,'days');
$('#reportrange').daterangepicker({
		opens: 'left' ,
		startDate: start,
		endDate: end,
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
			'Hoje': [moment().startOf('day'), moment().endOf('day')],
			'Amanhã': [moment().add(1,'days'), moment().add(1,'days')],
			'Próx. 7 dias': [moment(),moment().add(6,'days')],
			'Próx. 30 dias': [moment(),moment().add(29,'days')],
			'Próx. 365 dias': [moment(),moment().add(365,'days')],
			'Neste mês': [moment().startOf('month'), moment().endOf('month')],
			'Próx mês': [moment().add(1,'month').startOf('month'), moment().add(1,'month').endOf('month')]
		},
		buttonClasses: ['btn'],
		applyClass: 'green',
		cancelClass: 'default',
		separator: ' - ',
		locale: {
			applyLabel: 'Aplicar',
			fromLabel: 'De',
			toLabel: 'Até',
			customRangeLabel: 'Definir',
			daysOfWeek: ['Do', 'Se', 'Ter', 'Qua', 'Qui', 'Se', 'Sa'],
			monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
			firstDay: 1
		}
	},
	function (start, end) {
		$('#reportrange span').html(start.format('DD/MM/YYYY HH:mm') + ' - ' + end.format('DD/MM/YYYY HH:mm'));
		$('#input-date-range-from').val(start.format('YYYY-MM-DD HH:mm')+':00').trigger('change');
		$('#input-date-range-to').val(end.format('YYYY-MM-DD HH:mm')+':59').trigger('change')			
	}
); 	