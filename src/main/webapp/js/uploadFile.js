//$(document).ready(function() {
//
//    $('#btnUpload').click( function() {
//    //    if ($("#pdfUploadForm").valid()){
//            uploadArticle();
//    //    } else {
//    //        alert("Заповніть будь-ласка коректно форму додавання статті.")
//    //    };
//    });
//
//    //$('#btnUpload').on('click', function() {
//    //    uploadPdf();
//    //});
//});
//
//function uploadArticle(){
//    var file = $('[name="file"]');
//
//    $('#btnClear').on('click', function() {
//        file.val('');
//    });
//        var filename = $.trim(file.val()).split('\\').pop();
//        $.ajax({
//            url: '/savePdf',//todo rename to saveFile
//            type: "POST",
//            data: new FormData(document.getElementById("pdfUploadForm")),
//            enctype: 'multipart/form-data',
//            processData: false,
//            contentType: false
//        }).done(function(data) {
//            //todo remove the uploading form
//        }).fail(function(jqXHR, textStatus) {
//            alert(jqXHR + textStatus + 'Помилка завантаження! Спробуйте змінити назву файлу.');
//        });
//
//}
//
