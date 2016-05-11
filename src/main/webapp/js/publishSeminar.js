$(document).ready(function() {
    $('#reportUploadMessage').hide();
    $('#presentationUploadMessage').hide();

    autocompleteSeminarNames();

    $('#reportUploadForm').validate({
        rules: {report: {
                required: true}
        },
        messages: {
            report:{required: "Оберіть файл доповіді."}
        }
    });

    $('#presentationUploadForm').validate({
        rules: {
            presentation: {required: true}
        },
        messages: {
            presentation:{required: "Оберіть файл презентації."}
        }
    });

    $('#seminarPublishForm').validate({
        rules: {
            seminarName: {
                minlength: 10,
                required: true
            },
            seminarKeyWords: {
                minlength: 5,
                required: true
            }
        },
        messages: {
            seminarName:{
                minlength: "Введіть коректно назву семінару.",
                required: "Введіть назву семінару."
            },
            seminarKeyWords:{
                minlength: "Введіть коректно ключові слова.",
                required: "Необхідно вказати кілька ключових слів."
            }
        }
    });

    $('#btnPresentationUpload').click( function() {
        if ($("#presentationUploadForm").valid()){
            uploadSeminarSwf();
        } else {
            alert("Заповніть будь-ласка коректно форму додавання статті.")
        };
    });

    $('#btnReportUpload').click( function() {
        if ($("#reportUploadForm").valid()){
            uploadSeminarReport();
        } else {
            alert("Заповніть будь-ласка коректно форму додавання статті.")
        };

    });

    $('#publSeminarBtn').click( function() {
        if ($("#seminarPublishForm").valid() && $("#presentationUploadForm").valid() && $("#reportUploadForm").valid()){
            addSeminar();
        } else {
            alert("Заповніть будь-ласка коректно форму додавання статті.")
        };
    });


    $('#btnPresentationClear').on('click', function() {
        $('[name="presentation"]').val('');
    });
    $('#btnReportClear').on('click', function() {
        $('[name="report"]').val('');
    });

});

// add seminar details to DB
function addSeminar(){
    var swfFile = $('[name="presentation"]');
    var pdfFile = $('[name="report"]');
    var seminar = {
        seminarId: $("#seminarName :selected").val(),
        swfFileName: $.trim(swfFile.val()).split('\\').pop(),
        pdfFileName: $.trim(pdfFile.val()).split('\\').pop(),
        seminarKeyWords: $("#seminarKeyWords").val()
    };

    $.ajax({
        url: "/publishSeminar",
        contentType: 'application/json',
        data: JSON.stringify(seminar),
        async: false,
        type: 'POST',
        success: function (data) {
            var seminarErrorMessage = $('#seminarErrorMessage');
            if (data =="OK"){
                location.reload();
            } else {
                seminarErrorMessage.html(data);
            };
        },
        error: function (xhr, status, errorThrown) {
            alert('Виникла помилка при завантаженні: ' + status + ". " + errorThrown);
        }
    });
}

//upload seminar swf file to server
function uploadSeminarSwf(){
    $.ajax({
        url: '/savePresentation',//todo rename to saveFile
        type: "POST",
        data: new FormData(document.getElementById("presentationUploadForm")),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false
    }).done(function() {
        $('#presentationUploadingContent').hide();
        $('#presentationUploadMessage').show();
    }).fail(function(jqXHR, textStatus) {
        alert(jqXHR + textStatus + 'Помилка завантаження! Спробуйте змінити назву файлу.');
    });
}

//upload seminar report to server
function uploadSeminarReport(){
    $.ajax({
        url: '/saveReport',//todo rename to saveFile
        type: "POST",
        data: new FormData(document.getElementById("reportUploadForm")),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false
    }).done(function(data) {
        $('#reportUploadingContent').hide();
        $('#reportUploadMessage').show();
    }).fail(function(jqXHR, textStatus) {
        alert(jqXHR + textStatus + 'Помилка завантаження! Спробуйте змінити назву файлу.');
    });
}

function autocompleteSeminarNames() {
    $.ajax({
        type: 'GET',
        url: '/getAllUnpublishedSeminars',
        data: '',
        dataType: "json",
        success: function (data) {
            var seminarName = '#seminarName';
            var new_options = "<option selected='selected' value=''>Виберіть ваш семінар</option>";
            var seminar;
            var seminarId;
            var seminarName;
            for (var i in data) {
                seminar = data[i]
                seminarId = seminar[0];
                seminarName = seminar[1];
                new_options += "<option value='" + seminarId + "'>" + seminarName + "</option>";
            }
            $('#seminarName').html(new_options);
        },
        error: function (event, xhr, options, exc) {
            alert('Виникла помилка' + xhr + ' ' + options + ' ' + exc);
        }
    });
}
