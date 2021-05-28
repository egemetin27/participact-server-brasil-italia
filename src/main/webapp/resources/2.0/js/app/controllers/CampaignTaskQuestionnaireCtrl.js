/**
 * Tarefa com questionarios
 */
ParticipActApp.controller('CampaignTaskQuestionnaireCtrl', function ($scope, $timeout, $http, $window, $filter, PacticipActSrvc, CampaignTaskQuestionnaireSrvc, BASE_URL) {
    //clean
    $scope.cleanCampaignTaskQuestionnaire = function () {
        $scope.campaignTaskQuestionnaire = [];
        $scope.form = {};
        $scope.contest_questions = [];
    };
    //Salvando
    $scope.saveCampaignTaskQuestionnaire = function (a) {
        isSpinnerBar(!0),
            $scope.form.campaign_id = a,
            $scope.form.questions = $scope.contest_questions,
            CampaignTaskQuestionnaireSrvc.saveCampaignTaskQuestionnaire($scope.form).then(function (a) {
                1 == a.status ? $timeout(function () {
                    $("#fixgoback")[0].click()
                }, 600) : (bootbox.alert(a.message), $timeout(function () {
                    isSpinnerBar(!1)
                }, 500))
            })
    };
    //Edicao
    $scope.getCampaignTaskQuestionnaire = function (campaign_id, question_id) {
        isSpinnerBar(!0);
        if (question_id > 0) {
            CampaignTaskQuestionnaireSrvc.getCampaignTaskQuestionnaire(campaign_id, question_id).then(function (res) {
                if (res.status == true) {
                    $scope.form = res.payload;
                    $scope.contest_questions = res.payload.questions;
                    delete $scope.form.questions;
                    $timeout(function () {
                        // angular.forEach($scope.contest_questions, function(value, key) { console.log(value, key);});
                        isSpinnerBar(false);
                    }, 100);
                } else {
                    isSpinnerBar(false);
                }
            });
        } else {

            //$scope.form = {"title":"T","description":"Y","isrepeat":true,"campaign_id":9866218,"questions":[{"question":"Y","contest_options":[{"is_correct":false,"option":"U","$$hashKey":"object:222","target_order":"1"},{"is_correct":false,"option":"I","$$hashKey":"object:241","target_order":"-1"}],"contest_medias":[],"contest_type_id":"CLOSEDQUESTIONSIN","number_photos":1,"$$hashKey":"object:101","target_order":"-1"},{"question":"O","contest_options":[],"contest_medias":[],"contest_type_id":"OPENQUESTION","number_photos":1,"$$hashKey":"object:124","target_order":"2"},{"question":"P","contest_options":[],"contest_medias":[],"contest_type_id":"OPENQUESTION","number_photos":1,"$$hashKey":"object:171","target_order":"0"}]},
            //$scope.contest_questions = [{"question":"Y","contest_options":[{"is_correct":false,"option":"U","$$hashKey":"object:222","target_order":"1"},{"is_correct":false,"option":"I","$$hashKey":"object:241","target_order":"-1"}],"contest_medias":[],"contest_type_id":"CLOSEDQUESTIONSIN","number_photos":1,"$$hashKey":"object:101","target_order":"-1"},{"question":"O","contest_options":[],"contest_medias":[],"contest_type_id":"OPENQUESTION","number_photos":1,"$$hashKey":"object:124","target_order":"2"},{"question":"P","contest_options":[],"contest_medias":[],"contest_type_id":"OPENQUESTION","number_photos":1,"$$hashKey":"object:171","target_order":"0"}],
            PacticipActSrvc.isDebug() === !0 && PacticipActSrvc.fake($scope), isSpinnerBar(!1);
            $scope.form = {}, PacticipActSrvc.isDebug() === !0 && PacticipActSrvc.fake($scope), isSpinnerBar(!1);

            isSpinnerBar(false);
        }
    };
    //Remove Question
    $scope.removeQuestion = function (key) {
        $scope.contest_questions.splice(key, 1);
    };
    //Add Question
    $scope.addQuestion = function (id) {
        var n = [];
        angular.forEach($scope.contest_types, function (value, key) {
            this[value.id] = [{'is_correct': false, option: ''}];
        }, n);

        $scope.contest_questions.push({
            question: '',
            contest_options: n,
            contest_medias: [],
            contest_type_id: 'OPENQUESTION',
            number_photos: 1
        });
        $timeout(function () {
            $("#inputQuestion" + ($scope.contest_questions.length - 1)).focus();
        }, 50);

    };
    //Add Answers
    $scope.addAnswer = function (q_key, opt_type) {
        if (isBlank($scope.contest_questions[q_key].contest_options)) {
            $scope.contest_questions[q_key].contest_options = [];
            $scope.contest_questions[q_key].contest_options.push({'is_correct': false, option: ''});
            $timeout(function () {
                $("#input" + opt_type + "" + q_key + "" + ($scope.contest_questions[q_key].contest_options.length - 1)).focus();
            }, 50);
        } else {
            $scope.contest_questions[q_key].contest_options.push({'is_correct': false, option: ''});
            $timeout(function () {
                $("#input" + opt_type + "" + q_key + "" + ($scope.contest_questions[q_key].contest_options.length - 1)).focus();
            }, 50);
        }
    };
    //Remove Answers
    $scope.removeAnswer = function (q_key, id, key) {
        $scope.contest_questions[q_key].contest_options.splice(key, 1);
    };
    //Seta a coorreta e fixa bug
    $scope.setIsCorrect = function (q_key, id, key) {
        $scope.contest_questions[q_key].contest_options[key].is_correct = true;
        angular.forEach($scope.contest_questions[q_key].contest_options, function (v, k) {//fix bug
            if (k !== key) {
                $scope.contest_questions[q_key].contest_options[k].is_correct = false;
            }
        });
    };
    //Get page response
    $scope.getQuestionnaireRawAnswers = function (t, a, u) {
        //isSpinnerBar(true);
        $http.get(BASE_URL + "/protected/taskreport/show/questionnaire/" + t + "/user/" + u + "/action/" + a).then(function (response) {
            $scope.raw_html = response.data;
            //isSpinnerBar(false);
        });
    };
    //Pagincao
    $scope.initPagination = function () {
        //Pag
        $scope.totalItems = 0;
        $scope.currentPage = 1;
        $scope.radioModel = '10';
        $scope.maxSize = 10;
        $scope.itemsPerPage = 10;
        $scope.responses = [];
    };
    //Carregando respostas da campanha
    $scope.initCampaignTaskQuestionnaireResponses = function (campaign_id, action_id, user_id) {
        user_id = typeof user_id !== 'undefined' ? user_id : 0;
        if (user_id > 0) {
            $scope.radioModel = 100;
            $scope.currentPage = 1;
            $scope.responses[action_id] = [];
        }
        isSpinnerBar(true);
        $timeout(function () {
            CampaignTaskQuestionnaireSrvc.getCampaignTaskQuestionnaireResponses({
                campaign_id: campaign_id,
                action_id: action_id,
                user_id: user_id,
                count: $scope.radioModel,
                offset: $scope.currentPage
            }).then(function (res) {
                if (res.status == true) {
                    $scope.totalItems = res.total;
                    $scope.currentPage = res.offset;
                    $scope.itemsPerPage = res.count;

                    angular.forEach(res.payload, function (value) {
                        if (user_id > 0) {
                            $scope.responses[action_id].push(value);
                        } else {
                            $scope.responses.push(value);
                        }
                    });
                    //console.log($scope.responses);
                } else {
                    $scope.responses = [];
                }
                $timeout(function () {
                    isSpinnerBar(false);
                }, 500);
            });
        }, 100);
    };
    //Carregando series de uma pergunta
    $scope.initCampaignTaskQuestionnaireChart = function (question_id, is_photo) {
        console.log(question_id);
        CampaignTaskQuestionnaireSrvc.getCampaignTaskQuestionnaireChartSeries(question_id).then(function (res) {
            if (res.status == true) {
                if (is_photo) {
                    $(document).ready(function () {
                        $('#pa-painel-galleria-' + question_id).nanogallery2({
                            thumbnailHeight: 50,
                            thumbnailWidth: 50,
                            album: "none",
                            galleryDisplayMode: "moreButton",
                            galleryDisplayMoreStep: 2,
                            items: res.payloadList
                        });
                    });
                } else {
                    $('#pa-painel-chart-' + question_id).height(400);
                    am4core.ready(function () {
                        amchartPie('pa-painel-chart-' + question_id, res.payloadList, false);
                    });
                }
            } else {
                $('#pa-painel-chart-' + question_id).height(0);
                $('#pa-painel-no-data-' + question_id).removeClass('hidden');
            }
            $timeout(() => {
                $('#pa-painel-hourglass-' + question_id).hide();
            }, 1000);
        });
    };
    //Init
    $scope.cleanCampaignTaskQuestionnaire();
    //Menu
    setMenuOpen('pa-menu-campaigns', 'pa-submenu-campaign-tasks');
});