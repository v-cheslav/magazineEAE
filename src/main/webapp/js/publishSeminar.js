$(document).ready(function() {

    autocompleteSeminarNames();

    $('#seminarPublishForm').validate({
        rules: {
            seminarName: {
                required: true
            },
            seminarKeyWords: {
                minlength: 5,
                required: true
            },
            presentation: {required: true},
            report: {required: true}
        },
        messages: {
            seminarName:{
                required: "Назва семінару не вказана."
            },
            seminarKeyWords:{
                minlength: "Ключові слова вказано не коректно.",
                required: "Колючові слова не вказано."
            },
            presentation:{required: "Презентацію не вибрано."},
            required: "Доповідь не вибрано."
        }
    });

    $('#publSeminarBtn').click( function() {
        if ($("#seminarPublishForm").valid()){
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

    $(window).scroll(function(){
        if($(this).scrollTop()>220){
            $('#topnav').addClass('fixed');
        }
        else if ($(this).scrollTop()<220){
            $('#topnav').removeClass('fixed');
        }
    });
});

function addSeminar(){
    $.ajax({
        url: '/addSeminar',
        type: "POST",
        data: new FormData(document.getElementById("seminarPublishForm")),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false
    }).done(function(data) {
        if (data.seminarMassage == null){
            alert(data.seminarId);
            window.location.href = '/seminarPage?publicationId='+data.seminarId;
        } else {
            var errorMessage = $('#seminarErrorMessage');
            errorMessage.html(data.seminarMassage);
        };

    }).fail(function(jqXHR, textStatus) {
        alert(jqXHR + textStatus + 'Помилка завантаження! Спробуйте змінити назву файлу.');
    });
}

//// add seminar details to DB
//function addSeminar(){
//    var swfFile = $('[name="presentation"]');
//    var pdfFile = $('[name="report"]');
//    var seminar = {
//        seminarId: $("#seminarName :selected").val(),
//        swfFileName: $.trim(swfFile.val()).split('\\').pop(),
//        pdfFileName: $.trim(pdfFile.val()).split('\\').pop(),
//        seminarKeyWords: $("#seminarKeyWords").val()
//    };
//
//    $.ajax({
//        url: "/publishSeminar",
//        contentType: 'application/json',
//        data: JSON.stringify(seminar),
//        async: false,
//        type: 'POST',
//        success: function (data) {
//            var seminarErrorMessage = $('#seminarErrorMessage');
//            if (data =="OK"){
//                location.reload();
//            } else {
//                seminarErrorMessage.html(data);
//            };
//        },
//        error: function (xhr, status, errorThrown) {
//            alert('Виникла помилка при завантаженні: ' + status + ". " + errorThrown);
//        }
//    });
//}

////upload seminar swf file to server
//function uploadSeminarSwf(){
//    $.ajax({
//        url: '/savePresentation',//todo rename to saveFile
//        type: "POST",
//        data: new FormData(document.getElementById("presentationUploadForm")),
//        enctype: 'multipart/form-data',
//        processData: false,
//        contentType: false
//    }).done(function() {
//        $('#presentationUploadingContent').hide();
//        $('#presentationUploadMessage').show();
//    }).fail(function(jqXHR, textStatus) {
//        alert(jqXHR + textStatus + 'Помилка завантаження! Спробуйте змінити назву файлу.');
//    });
//}
//
////upload seminar report to server
//function uploadSeminarReport(){
//    $.ajax({
//        url: '/saveReport',//todo rename to saveFile
//        type: "POST",
//        data: new FormData(document.getElementById("reportUploadForm")),
//        enctype: 'multipart/form-data',
//        processData: false,
//        contentType: false
//    }).done(function(data) {
//        $('#reportUploadingContent').hide();
//        $('#reportUploadMessage').show();
//    }).fail(function(jqXHR, textStatus) {
//        alert(jqXHR + textStatus + 'Помилка завантаження! Спробуйте змінити назву файлу.');
//    });
//}

function autocompleteSeminarNames() {
    $.ajax({
        type: 'GET',
        url: '/getAllUnpublishedSeminars',
        data: '',
        dataType: "json",
        success: function (data) {
            var seminarName = '#seminarId';
            var new_options = "<option selected='selected' value=''>Семінар для публікації</option>";
            var seminar;
            var seminarId;
            var seminarName;
            for (var i in data) {
                seminar = data[i]
                seminarId = seminar[0];
                seminarName = seminar[1];
                new_options += "<option value='" + seminarId + "'>" + seminarName + "</option>";
            }
            $('#seminarId').html(new_options);
        },
        error: function (event, xhr, options, exc) {
            alert('Виникла помилка' + xhr + ' ' + options + ' ' + exc);
        }
    });
}
