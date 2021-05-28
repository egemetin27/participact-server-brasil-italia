/**
 * Upload de Arquivos
 */
ParticipActApp.controller('FileUploadCtrl', function ($scope, Upload, $timeout, PacticipActSrvc, BASE_URL) {
    $scope.action = '';
    //Clean
    $scope.cleanFileUpload = function () {
        $scope.progress = '';
        $scope.file = null;
        $scope.fileSource = '';
    };
    //open modal
    $scope.openUploadForm = function (name) {
        $scope.action = name;
        $timeout(function () {
            $('#modalImportFileForm').modal('toggle');
        }, 100);
    };
    // upload on file select or drop
    $scope.upload = function (file, action, id) {
        //console.log(file, action, id);
        if (!isBlank(file)) {
            isSpinnerBar(true);
            Upload.upload({
                url: BASE_URL + 'protected/file-upload',
                data: {filename: file, action: action, id: id, fileSource: $scope.fileSource}
            }).then(function (resp) {
                var res = resp.data;
                if (res.status == true) {
                    PacticipActSrvc.display(res.message, 'TYPE_INFO');
                    $('#modalImportFileForm').modal('hide');
                    $scope.cleanFileUpload();
                } else {
                    bootbox.alert(res.message);
                }
                $scope.progress = '';
                $timeout(function () {
                    isSpinnerBar(false);
                }, 500);
            }, function (resp) {
                console.log('Error status: ' + resp.status);
                $timeout(function () {
                    isSpinnerBar(false);
                }, 500);
            }, function (evt) {
                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                console.log('progress: ' + progressPercentage + '% ' + evt.config.data.filename.name);
                $scope.progress = progressPercentage > 98 ? 'Check ' + evt.config.data.filename.name + ' ...' : progressPercentage + '% ' + evt.config.data.filename.name;
            });
        }
    };
    //Storage File
    $scope.uploadStorage = function (file, action, id) {
        //console.log(file, action, id);
        if (!isBlank(file)) {
            isSpinnerBar(true);
            Upload.upload({
                url: BASE_URL + 'protected/storage-upload',
                data: {filename: file, action: action, id: id, fileSource: $scope.fileSource}
            }).then(function (resp) {
                var res = resp.data;
                if (res.status == true) {
                    $("#" + id).val(res.item).change();
                    $("#std-class").val(JSON.stringify(res.stdClass || {})).change();
                    $scope.cleanFileUpload();
                } else {
                    bootbox.alert(res.message);
                }
                $scope.progress = '';
                $timeout(function () {
                    isSpinnerBar(false);
                }, 500);
            }, function (resp) {
                console.log('Error status: ' + resp.status);
                $timeout(function () {
                    isSpinnerBar(false);
                }, 500);
            }, function (evt) {
                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                console.log('progress: ' + progressPercentage + '% ' + evt.config.data.filename.name);
                $scope.progress = progressPercentage > 98 ? 'Check ' + evt.config.data.filename.name + ' ...' : progressPercentage + '% ' + evt.config.data.filename.name;
            });
        }
    };
    //init
    $scope.cleanFileUpload();
});