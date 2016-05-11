$(document).ready(function(){

    $('#pdfMessage').hide();
    autocompleteReviewers();

    $('#pdfUploadForm').validate({
        rules: {
            articleFile: {
                required: true
            }
        },
        messages: {
            articleFile:{
                required: "Оберіть файл pdf."
            }
        }
    });

    $('#articlePublishForm').validate({
        rules: {
            selectSection: {
                required: true
            },
            articleName: {
                maxlength: 61,
                minlength: 10,
                required: true
            },
            keyWords: {
                minlength: 5,
                required: true
            },
            firstReviewer: {
                required: true
            },
            secondReviewer: {
                required: true
            },
            annotationUkr: {
                minlength: 30,
                required: true
            },
            annotationEng: {
                minlength: 30,
                required: true
            },
            annotationRu: {
                minlength: 30,
                required: true
            }
        },
        messages: {
            selectSection:{
                required: "Виберіть розділ у якому слід опублікувати статтю."
            },
            articleName:{
                maxlength: "Назва статті не повинна перевищувати 61 символ",
                minlength: "Введіть коректно назву статті.",
                required: "Введіть назву статті."
            },
            keyWords:{
                minlength: "Введіть коректно ключові слова.",
                required: "Необхідно вказати кілька ключових слів."
            },
            firstReviewer: {
                required: "Необхідно вибрати рецензента."
            },
            secondReviewer: {
                required: "Необхідно вибрати рецензента."
            },
            annotationUkr: {
                minlength: "Надто коротка анотація.",
                required: "Напишіть анотацію українською мовою."
            },
            annotationEng: {
                minlength: "Надто коротка анотація.",
                required: "Напишіть анотацію англійською мовою."
            },
            annotationRu: {
                minlength: "Надто коротка анотація.",
                required: "Напишіть анотацію російською мовою."
            }
        }
    });

    $('#btnUpload').click( function() {
        if ($("#pdfUploadForm").valid()){
            uploadArticleFile();
        } else {
            alert("Заповніть будь-ласка коректно форму додавання статті.")
        };
    });

    $('#publArticleBtn').click( function() {
        if ($("#articlePublishForm").valid() && $("#pdfUploadForm").valid()){
            addArticle();
        } else {
            alert("Заповніть будь-ласка коректно форму додавання статті.")
        };
    });//todo перевірити в джаві чи були додані на сервер файли

    $('#btnClear').on('click', function() {
        $('[name="articleFile"]').val('');
    });

});

// add article details to DB
function addArticle(){
    var file = $('[name="articleFile"]');
    var article = {
        articleName: $("#articleName").val(),
        fileName: $.trim(file.val()).split('\\').pop(),
        articleSection: $("#selectSection :selected").val(),
        firstReviewer: $("#firstReviewer :selected").val(),
        secondReviewer: $("#secondReviewer :selected").val(),
        annotationUkr: $("#annotationUkr").val(),
        annotationEng: $("#annotationEng").val(),
        annotationRu: $("#annotationRu").val(),
        keyWords: $("#keyWords").val()
    };

    $.ajax({
        url: "/publishArticle",
        contentType: 'application/json',
        data: JSON.stringify(article),
        async: false,
        type: 'POST',
        success: function (data) {
            var articleErrorMessage = $('#articleErrorMessage');
            if (data == "OK"){
                location.reload();
            } else {
                articleErrorMessage.html(data);
            };

        },
        error: function (xhr, status, errorThrown) {
            alert('Виникла помилка при завантаженні: ' + status + ". " + errorThrown);
        }
    });
}

//uploads article file to server
function uploadArticleFile(){
    $.ajax({
        url: '/saveArticle',//todo rename to saveFile
        type: "POST",
        data: new FormData(document.getElementById("pdfUploadForm")),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false
    }).done(function(data) {
        $('#fileUploadingContent').hide();
        $('#pdfMessage').show();
    }).fail(function(jqXHR, textStatus) {
        alert(jqXHR + textStatus + 'Помилка завантаження! Спробуйте змінити назву файлу.');
    });
}

function autocompleteReviewers() {
    $.ajax({
        type: 'GET',
        url: '/getAllReviewers',
        data: '',
        dataType: "json",
        success: function (data) {
            var firstSelectedReviewer = '#firstReviewer';
            var new_options1 = "<option selected='selected' value=''>Оберіть 1-го рецензента</option>";
            fillSelectedReviewers(new_options1, data, firstSelectedReviewer);

            var secondSelectedReviewer = '#secondReviewer';
            var new_options2 = "<option selected='selected' value=''>Оберіть 2-го рецензента</option>";
            fillSelectedReviewers(new_options2, data, secondSelectedReviewer);
        },
        error: function (event, xhr, options, exc) {
            alert('Виникла помилка' + xhr + ' ' + options + ' ' + exc);
        }
    });
}

function fillSelectedReviewers (new_options, data, selectedReviewer){
    var user;
    var userId;
    var userName;

    for (var i in data) {
        user = data[i]
        userId = user[0];
        userName = user[1];
        new_options += "<option value='" + userId + "'>" + userName + "</option>";
    }
    $(selectedReviewer).html(new_options);
}
