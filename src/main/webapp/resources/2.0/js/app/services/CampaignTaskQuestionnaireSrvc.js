ParticipActApp.factory('CampaignTaskQuestionnaireSrvc', function ($q, $timeout, $http, $localStorage, BASE_URL) {
    var deferred = $q.defer();
    return {
        saveCampaignTaskQuestionnaire: function (data) {
            // POST
            return $http.post(BASE_URL + '/protected/campaign-task-questionnaire/save/', data).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
        getCampaignTaskQuestionnaire: function (campaign_id, question_id) {
            // GET
            return $http.get(BASE_URL + '/protected/campaign-task-questionnaire/form/' + campaign_id + '/' + question_id + '/find').then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
        getCampaignTaskQuestionnaireResponses: function (data) {
            // POST
            return $http.post(BASE_URL + '/protected/campaign-task-questionnaire-responses/search', data).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        },
        getCampaignTaskQuestionnaireChartSeries: function (id) {
            // GET
            return $http.get(BASE_URL + '/protected/campaign-task-questionnaire-chart/series/' + id).then(function (res) {
                return res.data;
            }, function (err) {
                console.log(err);
            });
        }
    }
});