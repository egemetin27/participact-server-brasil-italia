/**
 * For checking if a string is blank, null or undefined I use:
 * @param str
 * @returns
 */
function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

/**
 * Verifica se um elemento esta vazio
 * @param str
 * @returns {Boolean}
 */
function isEmpty(str) {
    return (!str || 0 === str.length);
}

/**
 * Se eh um valido json string
 * @param str
 * @returns {boolean}
 */
function isJson(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        return false;
    }
    return true;
}

/**
 *  sleep function in js
 * @param milliseconds
 */
function sleep(milliseconds) {
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
        if ((new Date().getTime() - start) > milliseconds) {
            break;
        }
    }
}

/**
 * Exibe ou oculta o spinner bar
 * @param load
 */
function isSpinnerBar(load) {
    if (load === true) {
        $('#page-spinner-bar').removeClass('ng-hide hide').addClass('ng-show show');
        $('#no-data-to-display').removeClass('show').addClass('hide');
    } else {
        setTimeout(function () {
            $('#page-spinner-bar').removeClass('ng-show show').addClass('ng-hide hide');
            $('#no-data-to-display').removeClass('hide').addClass('show');
        }, 100);
    }
}

/**
 * Ativando switch button via javascripts
 */
function isMakeSwitch() {
    $(".make-switch").bootstrapSwitch();
}

/**
 * Carregando select2me quando chamado via ajax
 */
function isSelect2me() {
    $('.select2me').select2({allowClear: false});
}


function isColorPicker() {
    $('.colorpicker-default').each(function () {
        $(this).minicolors({
            control: $(this).attr('data-control') || 'hue',
            defaultValue: $(this).attr('data-defaultValue') || '',
            inline: $(this).attr('data-inline') === 'true',
            letterCase: $(this).attr('data-letterCase') || 'lowercase',
            opacity: $(this).attr('data-opacity'),
            position: $(this).attr('data-position') || 'bottom left',
            change: function (hex, opacity) {
                if (!hex) return;
                if (opacity) hex += ', ' + opacity;
                if (typeof console === 'object') {
                    console.log(hex);
                }
            },
            theme: 'bootstrap'
        });
    });
}

/**
 * Seta um valor do select2
 * @param key
 * @param value
 */
function setSelect2me(key, value, simple) {
    console.log(key, value);
    simple = typeof simple !== 'undefined' ? simple : true;

    if (simple == true) {
        $("#" + key).val(value).trigger("change");
        console.log(1);
    } else {
        console.log(2);
        $("#" + key).select2().select2("val", value);
    }
}

/**
 * Carregamento dinamico
 * @param elm
 * @param url
 */
function isSelect2RemoteData(elm, url, help) {
    try {
        if ($("#" + elm).length) {
            $("#" + elm).select2({
                allowClear: true,
                triggerChange: true,
                ajax: {
                    url: url,
                    dataType: 'json',
                    delay: 250,
                    data: function (params) {
                        return {search: params.term, count: 10};
                    },
                    processResults: function (data, params) {
                        params.page = params.page || 1;
                        if (data.status == true) {
                            var res = [];
                            var items = data.data.items;
                            for (var i = 0; i < items.length; i++) {
                                res.push({id: items[i].id, text: stripslashes(items[i].name)});
                            }
                            return {results: res};
                        } else {
                            return {results: false};
                        }
                    },
                    cache: true
                },
                escapeMarkup: function (markup) {
                    return markup;
                }, // let our custom formatter work
                minimumInputLength: 0,
            }).select2('val', []);

            help = typeof help !== 'undefined' ? help : false;
            if (help !== false) {
                $("#" + elm).change(function () {
                    setValue(help, $("#" + elm).text());
                });
            }

        }
    } catch (err) {
        console.log(err);
    }

}

/**
 * Seta um valor
 * @param key
 * @param value
 */
function setValue(key, value) {
    $('#' + key).empty();
    $('#' + key).html(value);
}

/**
 * Seta uma valor em um input text
 * @param key
 * @param value
 * @returns
 */
function setInputValue(key, value) {
    $(key).val(value).trigger("change");
}

/**
 * Selecionando um item
 * @param index
 * @param id
 */
function isSelectedPlatform(id) {
    $('#platform').val(id).trigger("change");
}

/**
 * Ativa o icheck via javascript
 */
function isICheck(changed) {
    changed = typeof changed !== 'undefined' ? changed : false;

    $(document).ready(function () {
        $('.icheck').iCheck({
            checkboxClass: 'icheckbox_square-yellow',
            radioClass: 'iradio_square-yellow',
            increaseArea: '20%' // optional
        });

        if (changed === true) {
            $('.icheck').on('ifChecked', function (event) {
                $('#' + event.target.name).val(true).trigger('change');
            });

            $('.icheck').on('ifUnchecked', function (event) {
                $('#' + event.target.name).val(false).trigger('change');
            });
        }
    });
}

/**
 * Seta o menu aberto
 * @param m
 * @param s
 * @returns
 */
function setMenuOpen(m, s) {
    $('.nav li').removeClass('active open selected');
    $('#' + m).addClass('active open selected');
    //$('#'+s).addClass('active open selected');
}

/**
 * Forca o calendario via class
 */
function isDateTimepicker() {
    $.fn.datetimepicker.dates['pt-BR'] = {
        format: 'dd/mm/yyyy',
        days: ["Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"],
        daysShort: ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom"],
        daysMin: ["Do", "Se", "Te", "Qu", "Qu", "Se", "Sa", "Do"],
        months: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
        monthsShort: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],
        today: "Hoje",
        suffix: [],
        meridiem: []
    };

    $(".form_datetime").datetimepicker({
        autoclose: true,
        format: "dd/mm/yyyy hh:ii",
        maxDate: new Date(),
        pickerPosition: "bottom-right",
        language: 'pt-BR'
    });
}

/**
 * Formatando data
 */
function isDatePicker() {
    $.fn.datetimepicker.dates['pt-BR'] = {
        format: 'dd/mm/yyyy',
        days: ["Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"],
        daysShort: ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom"],
        daysMin: ["Do", "Se", "Te", "Qu", "Qu", "Se", "Sa", "Do"],
        months: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
        monthsShort: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],
        today: "Hoje",
        suffix: [],
        meridiem: []
    };

    $(".form_datepicker").datetimepicker({
        autoclose: true,
        format: "dd/mm/yyyy",
        maxDate: new Date(),
        pickerPosition: "bottom-right",
        language: 'pt-BR',
        pickTime: false
    });
}

/**
 * Ativando mascara para um campo
 * @param type
 */
function isMask(type) {
    if (type == '.form_datepicker') {
        $(".form_datepicker").inputmask("dd/mm/yyyy", {
            autoUnmask: false
        });
    }
}

/**
 * Ativando o popover via jquery
 */
function isPopover() {
    $('[data-toggle="popover"]').popover({
        placement: 'top',
        trigger: 'hover'
    });
}

/**
 * Ativando markdown editor por jquery
 */
function isMarkdown() {
    $('[data-provide="markdown"]').markdown({});
}

/**
 * Ativando dropzone por jquery
 */
function isDropzone() {
    FormDropzone.init();
    //$("#my-dropzone").dropzone({ url: "/file/post" });
}

/**
 * Carrega um arquivo e retorna para o input informado
 * @param id
 */
function fileUploadDropzone(id) {
    $('#dropzone-to-imagem').val('');
    $('#dropzone-to-id').val(id);
    $('#modal_file_upload_form_static').modal('show');
}

/**
 * Tipo da extensao de um arquivo
 * @param filename
 * @returns
 */
function isExtesionFile(filename) {
    return (/[.]/.exec(filename)) ? /[^.]+$/.exec(filename) : false;
}

/**
 * escape slashes to/from a string
 * @param str
 * @returns
 */
function addslashes(str) {
    str = str.replace(/\\/g, '\\\\');
    str = str.replace(/\'/g, '\\\'');
    str = str.replace(/\"/g, '\\"');
    str = str.replace(/\0/g, '\\0');
    return str;
}

/**
 * escape slashes to/from a string
 * @param str
 * @returns
 */
function stripslashes(str) {
    str = str.replace(/\\'/g, '\'');
    str = str.replace(/\\"/g, '"');
    str = str.replace(/\\0/g, '\0');
    str = str.replace(/\\\\/g, '\\');
    return str;
}

/**
 * Setando imagem de fundo
 */
function isBackgroundPhoto(id, base64) {
    $('#' + id).css("background-image", "url('" + base64 + "')");
    $('#' + id).css("background-size", "100% 100%");
    $('#' + id).css("background-size", "100% 100%");
}

/**
 * Se a url eh valida
 * @param str
 * @returns {Boolean}
 */
function isValidURL(str) {
    var pattern = new RegExp('^(https?:\\/\\/)?' + // protocol
        '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.?)+[a-z]{2,}|' + // domain name
        '((\\d{1,3}\\.){3}\\d{1,3}))' + // OR ip (v4) address
        '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*' + // port and path
        '(\\?[;&a-z\\d%_.~+=-]*)?' + // query string
        '(\\#[-a-z\\d_]*)?$', 'i'); // fragment locator
    return pattern.test(str);
}

/**
 * Create GUID / UUID in JavaScript
 * @returns {String}
 */
function guid() {
    function s4() {
        return Math.floor((1 + Math.random()) * 0x10000)
            .toString(16)
            .substring(1);
    }

    return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
        s4() + '-' + s4() + s4() + s4();
}

/**
 * Returns a random number between min (inclusive) and max (exclusive)
 * https://developer.mozilla.org/en/Core_JavaScript_1.5_Reference/Global_Objects/Math/random
 */
function getRandomArbitrary(min, max) {
    return Math.random() * (max - min) + min;
}

/**
 * Returns a random integer between min (inclusive) and max (inclusive)
 * Using Math.round() will give you a non-uniform distribution!
 * https://developer.mozilla.org/en/Core_JavaScript_1.5_Reference/Global_Objects/Math/random
 */
function getRandomInt(min, max) {
    var min = typeof min !== 'undefined' ? min : 0;
    var max = typeof max !== 'undefined' ? max : 100;

    return Math.floor(Math.random() * (max - min + 1)) + min;
}

/**
 * Returns a random number between 0 (inclusive) and 1 (exclusive)
 * https://developer.mozilla.org/en/Core_JavaScript_1.5_Reference/Global_Objects/Math/random
 */
function getRandom() {
    return Math.random();
}

/**
 * Calculo de porcentagem para barra de progresso
 * @param a, valor
 * @param b, total
 * @returns
 */
function progressBar(a, b) {
    if (a > 0) {
        return (a / b * 100).toFixed(2);
    } else {
        return 0;
    }
};

/**
 * Chegando suporte do navegador
 * @param name
 * @returns
 */
function isBrowser(name) {
    switch (name) {
        case 'Opera':
            // Opera 8.0+
            return (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
            break;

        case 'Firefox':
            // Firefox 1.0+
            return typeof InstallTrigger !== 'undefined';
            break;

        case 'Safari':
            // Safari 3.0+ "[object HTMLElementConstructor]"
            return /constructor/i.test(window.HTMLElement) || (function (p) {
                return p.toString() === "[object SafariRemoteNotification]";
            })(!window['safari'] || safari.pushNotification);
            break;

        case 'IE':
            // Internet Explorer 6-11
            return /*@cc_on!@*/false || !!document.documentMode;
            break;

        case 'Edge':
            //Edge 20+
            return !isIE && !!window.StyleMedia;
            break;

        case 'Chrome':
            // Chrome 1+
            return !!window.chrome && !!window.chrome.webstore;
            break;

        case 'Blink':
            // Opera 8.0+
            var isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
            // Chrome 1+
            var isChrome = !!window.chrome && !!window.chrome.webstore;
            // Blink engine detection
            return (isChrome || isOpera) && !!window.CSS;
            break;

        default:
            return false;
    }
}

function resizableMe(id) {
    $(window).resize(function () {
        console.log($(window).height());
        $('#' + id).height($(window).height());
        $('#' + id).css("display", "contents");
    });

    $(window).trigger('resize');
}

/**
 *  Chart 4
 **/
function amchartPie(id, series, hasColor) {
    id = typeof id == undefined ? "chartdiv" : id;
    series = typeof series == undefined ? [] : series;
    // Themes begin
    am4core.useTheme(am4themes_animated);
    // Themes end
    // Create chart instance
    var chart = am4core.create(id, am4charts.PieChart);
    // Add and configure Series
    var pieSeries = chart.series.push(new am4charts.PieSeries());
    pieSeries.dataFields.value = "values";
    pieSeries.dataFields.category = "name";
    // Let's cut a hole in our Pie chart the size of 30% the radius
    chart.innerRadius = am4core.percent(30);
    // Put a thick white border around each Slice
    pieSeries.slices.template.stroke = am4core.color("#fff");
    pieSeries.slices.template.strokeWidth = 2;
    pieSeries.slices.template.strokeOpacity = 1;
    pieSeries.slices.template.cursorOverStyle = [
        {
            "property": "cursor",
            "value": "pointer"
        }
    ];
    pieSeries.alignLabels = true;
    pieSeries.labels.template.bent = false;
    pieSeries.labels.template.radius = 3;
    // Create a base filter effect (as if it's not there) for the hover to return to
    var shadow = pieSeries.slices.template.filters.push(new am4core.DropShadowFilter);
    shadow.opacity = 0;
    // Create hover state
    var hoverState = pieSeries.slices.template.states.getKey("hover"); // normally we have to create the hover state, in this case it already exists
    // Slightly shift the shadow and make it more prominent on hover
    var hoverShadow = hoverState.filters.push(new am4core.DropShadowFilter);
    hoverShadow.opacity = 0.7;
    hoverShadow.blur = 5;
    // Export
    chart.exporting.menu = new am4core.ExportMenu();
    chart.exporting.menu.items = [
        {
            "label": "...",
            "menu": [
                {
                    "label": "Image",
                    "menu": [
                        {"type": "png", "label": "PNG"},
                        {"type": "jpg", "label": "JPG"},
                        {"type": "svg", "label": "SVG"},
                        {"type": "pdf", "label": "PDF"}
                    ]
                }, {
                    "label": "Data",
                    "menu": [
                        {"type": "json", "label": "JSON"},
                        {"type": "csv", "label": "CSV"},
                        {"type": "xlsx", "label": "XLSX"},
                        {"type": "html", "label": "HTML"},
                        {"type": "pdfdata", "label": "PDF"}
                    ]
                }, {
                    "label": "Print", "type": "print"
                }
            ]
        }
    ];
    // Add a legend
    chart.legend = new am4charts.Legend();
    chart.legend.position = 'left';
    chart.legend.scrollable = true;
    chart.legend.maxWidth = undefined;
    // Add data
    var containsColor = hasColor != undefined ? hasColor : false;
    if (containsColor) {
        pieSeries.slices.template.propertyFields.fill = "color";
        for (let item of series) {
            item.color = am4core.color(item.color);
        }
    }
    chart.data = series;
}

function amchartBar(id, series, hasColor) {
    id = typeof id == undefined ? "chartdiv" : id;
    series = typeof series == undefined ? [] : series;
    // Themes begin
    am4core.useTheme(am4themes_animated);
    // Themes end
    var chart = am4core.create(id, am4charts.XYChart);
    chart.hiddenState.properties.opacity = 0; // this creates initial fade-in
    chart.data = series;

    var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
    categoryAxis.renderer.grid.template.location = 0;
    categoryAxis.dataFields.category = "name";
    categoryAxis.labelsEnabled = false;
    categoryAxis.renderer.minGridDistance = 10;
    categoryAxis.fontSize = 1;
    // categoryAxis.renderer.labels.template.rotation = 270;


    var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
    valueAxis.min = 0;
    valueAxis.renderer.minGridDistance = 30;
    valueAxis.renderer.baseGrid.disabled = true;

    var series = chart.series.push(new am4charts.ColumnSeries());
    series.dataFields.categoryX = "name";
    series.dataFields.valueY = "values";
    //series.columns.template.tooltipText = "{valueY.value}";
    series.columns.template.tooltipText = "{name}: [bold]{valueY.value}[/]";
    series.columns.template.tooltipY = 0;
    series.columns.template.strokeOpacity = 0;
    // as by default columns of the same series are of the same color, we add adapter which takes colors from chart.colors color set
    series.columns.template.adapter.add("fill", function (fill, target) {
        return chart.colors.getIndex(target.dataItem.index);
    });
}

function amchartLineGraph(id, series, labels) {
    id = typeof id == undefined ? "chartdiv" : id;
    series = typeof series == undefined ? [] : series;
    // Themes end
    // Create chart instance
    am4core.useTheme(am4themes_animated);
    // Create chart instance
    var chart = am4core.create(id, am4charts.XYChart);
    // Add data
    chart.data = series;
    // Create axes
    var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
    dateAxis.renderer.grid.template.location = 0;
    var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
    labels.forEach(function (v, i) {
        var series = chart.series.push(new am4charts.LineSeries());
        series.dataFields.valueY = "value" + v.id;
        series.dataFields.dateX = "date";
        series.name = v.name;
        series.tooltipText = "{name} {dateX}: [b]{valueY}[/]";
        series.strokeWidth = 2;

        var bullet = series.bullets.push(new am4charts.CircleBullet());
        bullet.circle.stroke = am4core.color("#fff");
        bullet.circle.fill = am4core.color(v.color);
        bullet.circle.strokeWidth = 2;
    });
    chart.legend = new am4charts.Legend();
    chart.cursor = new am4charts.XYCursor();
}
