ParticipActApp.controller('IssueReportCtrl', function ($scope, $timeout, $http, $window, $interval, $sce, $localStorage, NgMap,
                                                       PacticipActSrvc, ParticipantSrvc, IssueReportSrvc, CguCategorySrvc, CguEouvSrvc, StorageFileSrvc) {
    //clean
    $scope.cleanIssueReport = function () {
        $scope.issues = [], $scope.payload = {}, $scope.form = {isActive: !1}, $scope.totalItems = 0, $scope.currentPage = 0, $scope.radioModel = "10", $scope.maxSize = 10, $scope.itemsPerPage = 10, $scope.selected_items = 0, $scope.controller = "";
        $scope.issueCguManifestacaoResposta = [];
        $scope.issueCguManifestacaoHistorico = [];
        $scope.issueOmbudsmanList = [];
    };
    //Lista de itens
    $scope.initIssueReport = function () {
        var search = $scope.form;
        search.count = $('#map-full-canvas').length ? 10000 : $scope.radioModel;
        search.offset = $scope.currentPage;
        isSpinnerBar(true)
        IssueReportSrvc.getListIssueReport(search, search.count, search.offset).then(function (res) {
            if (res.status == true) {
                $scope.issues = res.items;
                $scope.totalItems = res.total;
                $scope.currentPage = res.offset;
                $scope.itemsPerPage = res.count;
                $timeout(function () {
                    if ($('#map-full-canvas').length) {
                        $scope.ngmapIssueReport();
                    }
                    ;
                }, 100);
            } else {
                $scope.cleanIssueReport();
            }
            $timeout(function () {
                isSpinnerBar(false)
            }, 500);
        });
    }
    //Salvando
    $scope.saveIssueReport = function () {
        isSpinnerBar(true);
        $scope.form.issueParticipant = $scope.issueParticipant;
        $scope.form.issueSubcategory = $scope.issueSubcategory;
        $scope.form.issueOmbudsman = $scope.issueOmbudsman;
        $scope.form.issueSiorg = $scope.issueSiorg;
        $timeout(function () {
            IssueReportSrvc.saveIssueReport($scope.form).then(function (res) {
                if (res.status == true) {
                    $("#fixgoback")[0].click()
                } else {
                    bootbox.alert(res.message);
                }
                isSpinnerBar(false);
            });
        }, 5);
    };
    //Edicao
    $scope.editIssueReport = function (o) {
        isSpinnerBar(!0), $timeout(function () {
            $window.location.href = "edit/" + o
        }, 500)
    };
    $scope.getIssueReport = function () {
        isSpinnerBar(!0), $scope.form = {}, PacticipActSrvc.isDebug() === !0 && PacticipActSrvc.fake($scope), isSpinnerBar(!1)
    };
    $scope.setIssueReport = function (e) {
        isSpinnerBar(true);
        IssueReportSrvc.getIssueReport(e).then(function (e) {
            if (e.status == true) {
                $scope.form = e.item;
                $scope.payload = e.payload;
                $scope.issueParticipant = e.payload.issueParticipant;
                $scope.issueSubcategory = e.payload.issueSubcategory;
                $scope.issueOmbudsman = e.payload.issueOmbudsman;
                $scope.issueSiorg = e.payload.issueSiorg;
                $scope.issueCguManifestacaoHistorico = e.payload.issueCguManifestacaoHistorico;
                $scope.issueCguManifestacaoResposta = e.payload.issueCguManifestacaoResposta;
                $scope.issueOmbudsmanList = e.payload.issueOmbudsmanList;
                //Marker
                $timeout(function () {
                    $scope.ngmapAddMarker(new google.maps.LatLng(e.item.latitude, e.item.longitude));
                }, 100);
            }
            isSpinnerBar(false);
        });
    };
    //Export
    $scope.initIssueListExport = function () {
        isSpinnerBar(true);
        IssueReportSrvc.getCloudExport({}).then(function (res) {
            if (res.status == true) {
                PacticipActSrvc.display(res.message, res.resultType);
            } else {
                bootbox.alert(res.message);
            }
            isSpinnerBar(false);
        });
    };
    //Removendo Arquivo
    $scope.removeFile = function (key, fid) {
        isSpinnerBar(true);
        if (fid != undefined && fid != null) {
            StorageFileSrvc.removeStorageFile(fid).then(function (res) {
                if (res.status == true) {
                    $scope.form.files.splice(key, 1);
                } else {
                    bootbox.alert(res.message);
                }
                $timeout(function () {
                    isSpinnerBar(false);
                }, 100);
            });
        } else {
            $scope.form.files.splice(key, 1);
            isSpinnerBar(false);
        }
    };
    //Remover varios itens
    $scope.removeSelected = function (e, c, a, o, s) {
        var l = "";
        $scope.selected_items < 1 ? bootbox.alert(s) : (angular.forEach($scope.issues, function (e, c) {
            e.Selected === !0 && (l = l + '<b class="has-error">' + e[1] + "</b><br/>")
        }), bootbox.dialog({
            title: a,
            message: o + " : <p>" + l + "</p>",
            buttons: {
                success: {
                    label: c, className: "btn-default", callback: function () {
                    }
                }, danger: {
                    label: e, className: "btn-danger", callback: function () {
                        isSpinnerBar(!0), angular.forEach($scope.issues, function (e, c) {
                            e.Selected === !0 && $scope.removed(e[0])
                        })
                    }
                }
            }
        }))
    };
    //executa a acao de remover
    $scope.removed = function (s) {
        isSpinnerBar(!0), IssueReportSrvc.removeIssueReport(s).then(function (e) {
            1 == e.status ? ($("#issues-tr-" + s).addClass("hide"), PacticipActSrvc.display(e.message)) : ($("#issues-tr-" + s).addClass("danger"), PacticipActSrvc.display(e.message, "error")), isSpinnerBar(!1)
        })
    };
    //Files
    $scope.initStorageFiles = function (key, id) {
        try {
            $scope.issues[key].files = [];
        } catch (err) {
            $scope.issues[key] = {files: []};
        }
        $timeout(function () {
            StorageFileSrvc.getListStorageFile({issueReportId: id, source: "IssueReport"}).then(function (res) {
                if (res.status == true) {
                    $scope.issues[key].files = res.items;
                }
            });
        }, 500);
    }
    //Alterando paginacao
    $scope.pageChanged = function () {
        $scope.initIssueReport();
    }
    //Select all
    $scope.checkAll = function () {
        $scope.selectedAll ? ($scope.selectedAll = !0, $scope.selected_items = $scope.totalItems) : ($scope.selectedAll = !1, $scope.selected_items = 0), angular.forEach($scope.issues, function (e) {
            e.Selected = $scope.selectedAll
        })
    };
    //Se selecionado
    $scope.isSelected = function (e) {
        $("#" + e).is(":checked") ? $scope.selected_items++ : $scope.selected_items--
    };
    //Mapa
    $scope.map = null;
    $scope.currentPosition = {lat: -27.586347, lng: -48.502900};
    $scope.markers = [];
    $scope.ngmapIssueReport = function () {
        var mapMarkerClusterer;
        NgMap.getMap().then(function (map) {
            var bounds = new google.maps.LatLngBounds(),
                dynMarkers = [];
            // Add markers, listeners and get bounds
            angular.forEach($scope.issues, function (item) {
                var newLat = item[18] + (Math.random() - .5) / 1500;
                var newLng = item[19] + (Math.random() - .5) / 1500;
                var marker = new google.maps.Marker({
                    position: new google.maps.LatLng(newLat, newLng),
                    id: item[0]
                });
                marker.addListener('click', function () {
                    IssueReportSrvc.openNewTab(item[0]);
                });
                // Get bounds and add to marker to be clustered
                bounds.extend(marker.getPosition());
                dynMarkers.push(marker);
            });
            // Set or refresh cluster
            if (mapMarkerClusterer) {
                mapMarkerClusterer.clearMarkers()
            }
            mapMarkerClusterer = new MarkerClusterer(map, dynMarkers, {});
            // Center and fit bounds
            map.setCenter(bounds.getCenter());
            map.fitBounds(bounds);
            // Apply map changes to scope
            $scope.map = map;
        });
    };
    //Mapa Form
    $scope.ngmapIssueForm = function () {
        NgMap.getMap().then(function (map) {
            var bounds = new google.maps.LatLngBounds();
            // Center and fit bounds
            map.setCenter(bounds.getCenter());
            map.fitBounds(bounds);
            // Apply map changes to scope
            $scope.map = map;
        });
    };

    $scope.ngmapPlaceChanged = function () {
        for (var i = 0; i < $scope.markers.length; i++) {
            $scope.markers[i].setMap(null);
        }
        var place = this.getPlace();
        var location = place.geometry.location;
        $scope.form.formatted_address = place.formatted_address;
        console.log('location', location);
        //Marker
        $scope.ngmapAddMarker(location);
        //Form
        $timeout(function () {
            $scope.form.latitude = location.lat();
            $scope.form.longitude = location.lng();
            $scope.form.provider = "Google Geo Code";
            $scope.form.altitude = 0.0;
            $scope.form.course = 0.0;
            $scope.form.floor = 0.0;
            $scope.form.speed = 0.0;
            $scope.form.accuracy = 0.0;
            $scope.form.horizontalAccuracy = 0.0;
            $scope.form.verticalAccuracy = 0.0;
        }, 10);
    };
    $scope.ngmapAddMarker = function (location) {
        if ($scope.map == undefined && $scope.map == null) {
            $scope.ngmapIssueForm();
        }
        for (var i = 0; i < $scope.markers.length; i++) {
            $scope.markers[i].setMap(null);
        }
        $timeout(function () {
            $scope.currentPosition = {lat: location.lat, lng: location.lng};
            var marker = new google.maps.Marker({
                position: location,
                map: $scope.map
            });
            $scope.markers.push(marker);
            $timeout(function () {
                $scope.map.setCenter(location);
            }, 500);
        }, 500);
    };
    //Participante
    $scope.onSearchIssueParticipant = function (search) {
        console.log('onSearchIssueParticipant');
        if (search != undefined && search != null && search.length > 0) {
            isSpinnerBar(true)
            $('#pa-issue-participant-list').show();
            ParticipantSrvc.getListParticipant({search: search}, 10, 0).then(function (res) {
                if (res.status == true) {
                    $scope.listParticipant = res.items;
                }
                isSpinnerBar(false);
            });
        } else {
            $('#pa-issue-participant-list').hide();
        }
    };
    $scope.onSelectIssueParticipant = function (i) {
        console.log('onSelectIssueParticipant');
        $scope.issueParticipant = {id: i[0], name: i[1], email: i[3], secondary: i[26]};
        $('.pa-data-list').hide();
        $timeout(function () {
            $scope.search = null;
        }, 5);

    };
    $scope.onRemoveIssueParticipant = function () {
        $scope.issueParticipant = {};
    };
    //Categorias/Sub
    $scope.onSearchIssueSubcategory = function (search) {
        console.log('onSearchIssueSubcategory');
        if (search != undefined && search != null && search.length > 0) {
            isSpinnerBar(true)
            $('#pa-issue-subcategory-list').show();
            CguCategorySrvc.getListCguSubCategory({search: search, count: 10, offset: 0}).then(function (res) {
                if (res.status == true) {
                    $scope.listSubcategory = res.items;
                }
                isSpinnerBar(false);
            });
        } else {
            $('#pa-issue-subcategory-list').hide();
        }
    };
    $scope.onSelectIssueSubcategory = function (i) {
        console.log('onSelectIssueSubcategory');
        $scope.issueSubcategory = {id: i[0], name: i[1], icon: i[3], category: i[4]};
        $('.pa-data-list').hide();
        $timeout(function () {
            $scope.searchSub = null;
        }, 5);

    };
    $scope.onRemoveIssueSubcategory = function () {
        $scope.issueSubcategory = {};
    };
    //Ouvidoria
    $scope.onSearchIssueOmbudsman = function (search) {
        console.log('onSearchIssueOmbudsman');
        if (search != undefined && search != null && search.length > 0) {
            isSpinnerBar(true)
            $('#pa-issue-ombudsman-list').show();
            CguEouvSrvc.getOuvidorias({search: search, count: 10, offset: 0}).then(function (res) {
                if (res.status == true) {
                    $scope.listOmbudsman = res.item;
                }
                isSpinnerBar(false);
            });
        } else {
            $('#pa-issue-ombudsman-list').hide();
        }
    };
    $scope.onSelectIssueOmbudsman = function (i) {
        console.log('onSelectIssueOmbudsman');
        $scope.issueOmbudsman = {
            id: i.idOuvidoria,
            name: i.nomeOrgaoOuvidoria,
            sphere: i.descEsfera,
            county: i.descMunicipio
        };
        $('.pa-data-list').hide();
        $timeout(function () {
            $scope.search = null;
        }, 5);

    };
    $scope.onRemoveIssueOmbudsman = function () {
        $scope.issueOmbudsman = {};
    };
    //SiOrg
    $scope.onSearchIssueSiorg = function (search) {
        console.log('onSearchIssueSiorg');
        if (search != undefined && search != null && search.length > 0) {
            isSpinnerBar(true)
            $('#pa-issue-siorg-list').show();
            CguEouvSrvc.getListaOrgaosSiorg({search: search, count: 10, offset: 0}).then(function (res) {
                if (res.status == true) {
                    $scope.listSiorg = res.item;
                }
                isSpinnerBar(false);
            });
        } else {
            $('#pa-issue-siorg-list').hide();
        }
    };
    $scope.onSelectIssueSiorg = function (i) {
        console.log('onSelectIssueSiorg');
        $scope.issueSiorg = {
            id: i.codOrg,
            name: i.nomOrgao
        };
        $('.pa-data-list').hide();
        $timeout(function () {
            $scope.searchSo = null;
        }, 5);

    };
    $scope.onRemoveIssueSiorg = function () {
        $scope.issueSiorg = {};
    };
    //Protocol
    $scope.onUpdateIssueProtocol = function (id) {
        console.log('onUpdateIssueProtocol');
        isSpinnerBar(true)
        CguEouvSrvc.getListaManifestacaoOuvidoria({issueId: id || 0}).then(function (res) {
            if (res.status == true) {
                $window.location.reload();
            }
            isSpinnerBar(false);
        });
    };
    //Upload
    $scope.onChangeUploadFile = function () {
        console.log("onChangeUploadFile");
        var f = $('#std-class').val();
        if (f != undefined && !isBlank(f) && isJson(f)) {
            //console.log(f);
            var stdClass = JSON.parse(f);
            if ("discriminatorType" in stdClass) {
                $scope.form.files = Array.isArray($scope.form.files) ? $scope.form.files : [];
                $scope.form.files.push(stdClass);
            }
        }
    };
    //Init
    $scope.cleanIssueReport();
    //Jquery + Angularjs
    $(".pa-filter").keypress(function (e) {
        var o = e.keyCode || e.which;
        "13" == o && $scope.initIssueReport()
    });
    //Menu
    setMenuOpen('pa-menu-issue-report', 'pa-submenu-issue-report');
});