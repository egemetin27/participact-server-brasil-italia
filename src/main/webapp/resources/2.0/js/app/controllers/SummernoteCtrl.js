ParticipActApp.controller('SummernoteCtrl', function($scope, $timeout, $http) {
	$scope.options = {
			height: 400,
			focus: true,
			lang:'pt-BR',
			toolbar: [
				['edit',['undo','redo']],
				['headline', ['style']],
				['style', ['bold', 'italic', 'underline', 'superscript', 'subscript', 'strikethrough', 'clear']],
				['fontface', ['fontname']],
				['textsize', ['fontsize']],
				['fontclr', ['color']],
				['alignment', ['ul', 'ol', 'paragraph', 'lineheight']],
				['height', ['height']],
				['table', ['table']],
				['insert', ['link','video','hr']],
				['view', ['fullscreen', 'codeview']],
				['help', ['help']]]
	};	
});