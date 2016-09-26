$(document).ready(function(){

    $('#articlePublishForm').validate({
        rules: {
            articleSection: {required: true},
            articleName: {maxlength: 61, minlength: 10, required: true},
            keyWords: {minlength: 5, required: true},
            articleFile: {required: true},
            annotationUa: {minlength: 30, required: true},
            annotationEng: {minlength: 30, required: true},
            annotationRu: {minlength: 30, required: true}
        },
        messages: {
            articleSection:{required: "Рубрику не вибрано."},
            articleName:{
                maxlength: "Назва статті не повинна перевищувати 61 символ",
                minlength: "Назва статті не коректна.",
                required: "Назва статті не вказана."
            },
            keyWords:{
                minlength: "Ключові слова вказано не коректно.",
                required: "Колючові слова не вказано."
            },
            articleFile:{required: "Статтю не вибрано."},
            annotationUa: {
                minlength: "Надто коротка анотація.",
                required: "Анотація українською не вказана."
            },
            annotationEng: {
                minlength: "Надто коротка анотація.",
                required: "Анотація англійською не вказана.."
            },
            annotationRu: {
                minlength: "Надто коротка анотація.",
                required: "Анотація російською не вказана.."
            }
        }
    });


    $('#publArticleBtn').click( function() {
        if ($("#articlePublishForm").valid()){
            addArticle();
        } else {
            alert("Заповніть будь-ласка коректно форму додавання статті.")
        };
    });

    $('#btnClear').on('click', function() {
        $('[name="articleFile"]').val('');
        $('[name="articleSection"]').val('');
        $('[name="articleName"]').val('');
        $('[name="keyWords"]').val('');
        $('[name="annotationUa"]').val('');
        $('[name="annotationEng"]').val('');
        $('[name="annotationRu"]').val('');
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

function addArticle(){
    $.ajax({
        url: '/addArticle',
        type: "POST",
        data: new FormData(document.getElementById("articlePublishForm")),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false
    }).done(function(data) {
        if (data.articleMassage == null){
            window.location.href = '/myPage'
        };
        if (data.articleMassage != null){
            var errorMessage = $('#articleErrorMessage');
            errorMessage.html(data.articleMassage);
        };

    }).fail(function(jqXHR, textStatus) {
        alert(jqXHR + textStatus + 'Помилка завантаження! Спробуйте змінити назву файлу.');
    });
}

// add article details to DB
//function addArticle(){
//    var file = $('[name="articleFile"]');
//    var article = {
//        articleName: $("#articleName").val(),
//        fileName: $.trim(file.val()).split('\\').pop(),
//        articleSection: $("#articleSection :selected").val(),
//        //firstReviewer: $("#firstReviewer :selected").val(),
//        //secondReviewer: $("#secondReviewer :selected").val(),
//        annotationUkr: $("#annotationUkr").val(),
//        annotationEng: $("#annotationEng").val(),
//        annotationRu: $("#annotationRu").val(),
//        keyWords: $("#keyWords").val()
//    };
//
//    $.ajax({
//        url: "/publishArticle",
//        contentType: 'application/json',
//        data: JSON.stringify(article),
//        async: false,
//        type: 'POST',
//        success: function (data) {
//            var articleErrorMessage = $('#articleErrorMessage');
//            if (data == "OK"){
//                location.reload();
//            } else {
//                articleErrorMessage.html(data);
//            };
//
//        },
//        error: function (xhr, status, errorThrown) {
//            alert('Виникла помилка при завантаженні: ' + status + ". " + errorThrown);
//        }
//    });
//}

//uploads article file to server
//function uploadArticleFile(){
//    $.ajax({
//        url: '/saveArticleFile',//todo rename to saveFile
//        type: "POST",
//        data: new FormData(document.getElementById("pdfUploadForm")),
//        enctype: 'multipart/form-data',
//        processData: false,
//        contentType: false
//    }).done(function(data) {
//        $('#fileUploadingContent').hide();
//        $('#pdfMessage').show();
//    }).fail(function(jqXHR, textStatus) {
//        alert(jqXHR + textStatus + 'Помилка завантаження! Спробуйте змінити назву файлу.');
//    });
//}

