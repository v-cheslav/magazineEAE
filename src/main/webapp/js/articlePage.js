$(document).ready(function(){
    annotationToggle();
    scrollWindow();
    validate();
    pdfSizeControl();
    buttonsListener();
});


function annotationToggle(){
    $('#annotationEngContent').hide();
    $('#annotationRuContent').hide();
    $('#ua').click(function(){
        document.getElementById('annotationHeader').innerHTML = 'Анотація';
        $('#annotationEngContent').hide();
        $('#annotationRuContent').hide();
        $('#annotationUkrContent').show();
    });
    $('#eng').click(function(){
        document.getElementById('annotationHeader').innerHTML = 'Annotation';
        $('#annotationUkrContent').hide();
        $('#annotationRuContent').hide();
        $('#annotationEngContent').show();
    });
    $('#ru').click(function(){
        document.getElementById('annotationHeader').innerHTML = 'Аннотация';
        $('#annotationUkrContent').hide();
        $('#annotationEngContent').hide();
        $('#annotationRuContent').show();
    });
}


function scrollWindow (){
    $(window).scroll(function(){
        if($(this).scrollTop()>220){
            $('#topnav').addClass('fixed');

        }
        else if ($(this).scrollTop()<220){
            $('#topnav').removeClass('fixed');
        }
    });

};


function buttonsListener(){
    $('#dennyReviewBtn').click(function(){
        $('#confirmation').show();
    });

    $('#cancelBtn').click(function(){
        toSmallSize();
        $('#addReview').hide();
    });

    $('#cancelConfBtn').click(function(){
        $('#confirmation').hide();
    });

    $('#closeSign').click(function(){
        $('#reviewSection').hide();
        var reviewContent = document.getElementById("reviewPdf");
        while (reviewContent.lastChild) {
            reviewContent.removeChild(reviewContent.lastChild);
        }
    });

    $('#btnUpload').click( function() {
        if ($("#pdfUploadForm").valid()){
            addReview();
        } else {
            alert("Заповніть будь-ласка коректно форму додавання статті.")
        };
    });

    $('#btnClear').on('click', function() {
        $('[name="articleFile"]').val('');
    });
};


function validate(){
    $('#pdfUploadForm').validate({
        rules: {
            reviewFile: {
                required: true
            }
        },
        messages: {
            reviewFile:{
                required: "Оберіть файл pdf."
            }
        }
    });
};

//Control view of pdf document
function pdfSizeControl(){
    var largeSize = false;
    $('#toLargeSize').click(function(){
        if (largeSize == false){
            toLargeSize();
            largeSize = true;
        } else {
            toSmallSize();
            largeSize = false;
        }
    });
};

function toLargeSize(){
    $('#toLargeSize').css({
        "position":   "fixed",
        "width":      "32px",
        "height":      "32px",
        //"padding-left": "60px",
        "z-index":    "10000",
        "top":        "0",
        "right":        "0",
        "background": "transparent url(\"../images/closeSign.png\")",
        "background-repeat": "no-repeat"
    });

    $('#articlePdf').css({
        "position":   "fixed",
        "z-index":    "1000",
        "top":        "0px",
        "left":       "0",
        "height":     "100%",
        "width":      "100%"
    });
};

function toReviewerSize(){
    $('#articlePdf').css({
        "position":   "fixed",
        "z-index":    "1000",
        "top":        "0",
        "left":       "0",
        "height":     "100%",
        "width":      "70%"
    });
};

function toSmallSize(){

    $('#toLargeSize').css({
        "z-index":    "100",
        "position":     "relative",
        "background": "transparent url(\"../images/toAllPage.png\")"
});

    $('#articlePdf').css({
        "z-index":    "100",
        "position":   "relative",
        "top":        "0",
        "height":     "650px",
        "width":      "100%"
    });
}


function checkReviewer(reviewerId){
    var reviewerIdJson = {
        reviewerId: reviewerId
    };
    $.ajax({
        url: "/checkReviewer",
        contentType: 'application/json',
        data: JSON.stringify(reviewerIdJson),
        async: false,
        type: 'POST',
        success: function (data) {
            if (data =="OK"){
                toReviewerSize();
                $('#addReview').show();
            } else {
                alert(data);
            };
        },
        error: function (xhr, status, errorThrown) {
            alert('Виникла помилка: ' + status + ". " + errorThrown);
        }
    });
}


function dennyReview(articleId) {
    var articleIdJson = {
        articleId: articleId
    };
    $.ajax({
        url: "/dennyReview",
        contentType: 'application/json',
        data: JSON.stringify(articleIdJson),
        async: false,
        type: 'POST',
        success: function (data) {
            if (data =="OK"){
               alert("У наданні рецензії відмовлено!")
                toSmallSize();
                $('#confirmation').hide();
                $('#addReview').hide();
                window.location.reload();
            } else {
                alert(data);
            };
        },
        error: function (xhr, status, errorThrown) {
            alert('Виникла помилка: ' + status + ". " + errorThrown);
        }
    });
}


function addReview(){
    $.ajax({
        url: '/addReview',
        type: "POST",
        data: new FormData(document.getElementById("pdfUploadForm")),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false
    }).done(function(data) {
        if (data.reviewErrorMassage == null) {
            $('#fileUploadingContent').hide();
            window.location.reload();
        }
        if (data.reviewErrorMassage != null){
            var errorMessage = $('#reviewErrorMassage');
            errorMessage.html(data.reviewErrorMassage);
        };
    }).fail(function(errorThrown) {
        alert(errorThrown + ' Помилка завантаження!');
    });
}


//function addReview(articleId){
//    var file = $('[name="reviewFile"]');
//        var reviewJson = {
//            articleId: articleId,
//            fileName: $.trim(file.val()).split('\\').pop()
//        };
//        $.ajax({
//            url: "/addReview",
//            contentType: 'application/json',
//            data: JSON.stringify(reviewJson),
//            async: false,
//            type: 'POST',
//            success: function (data) {
//                if (data =="OK"){
//                    window.location.reload();
//                } else {
//                    alert(data);
//                }
//            },
//            error: function (xhr, status, errorThrown) {
//                alert('Виникла помилка: ' + status + ". " + errorThrown);
//            }
//        });
//    //}
//}


function getReview(review) {
    var reviewPdf = $('#reviewPdf');
    var path = 'getFile?name=' + review + '&type=article';
    $('#reviewSection').show();

    var iframe = $('<iframe id="reviewPdf1"></iframe>');
    iframe.attr('src', path);
    reviewPdf.append(iframe);

}

function addComment(publicationId, type) {
    var articleId;
    var seminarId;
    if (type == 'article'){
        articleId = publicationId;
        seminarId = null;
    } else if  (type == 'seminar'){
        seminarId = publicationId;
        articleId = null;
    }
    var commentJson = {
        articleId: articleId,
        seminarId: seminarId,
        newComment: $('#newComment').val()
    };
    $.ajax({
        contentType: 'application/json',
        data: JSON.stringify(commentJson),
        async: false,
        type: 'POST',
        url: '/addComment',
        success: function (data) {
            $('#newComment').val('');
            window.location.reload();
        },
        error: function (xhr, status, errorThrown) {
            alert('Виникла помилка: ' + status + ". " + errorThrown);
        }
    });
}


function deleteComment(commentId){
    if (confirm("Видалити комертар?")){
        $.ajax({
            url: "/deleteComment",
            contentType: 'application/json',
            data: JSON.stringify(commentId),
            async: false,
            type: 'POST',
            success: function (data) {
                if (data == "OK"){
                    window.location.reload();
                } else {
                    alert(data);
                };
            },
            error: function (xhr, status, errorThrown) {
                alert('Виникла помилка: ' + status + ". " + errorThrown);
            }
        });
    }
}


