$(document).ready(function(){
    var bigSize = false;

    $('#articleSizeSwitcher').click(function(){
        if (bigSize == false){
            toLargeSize();
            bigSize = true;
        } else {
            toSmallSize();
            bigSize = false;
        }
    });

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
        var reviewContent = document.getElementById("reviewMessage");
        while (reviewContent.lastChild) {
            reviewContent.removeChild(reviewContent.lastChild);
        }
    });

    $('#commentsHeader').click(function(){
        $('#commentsContent').toggle();
    });
    $('#annotationUaHeader').click(function(){
        $('#annotationUaContent').toggle();
    });
    $('#annotationUkHeader').click(function(){
        $('#annotationUkContent').toggle();
    });
    $('#annotationRuHeader').click(function(){
        $('#annotationRuContent').toggle();
    });
    $('#reviewersMenuHeader').click(function(){
        $('#reviewersMenuContent').toggle();
    });

});


function toLargeSize(){
    document.getElementById('articleSizeSwitcher').innerHTML='Згорнути статтю';

    $('#articleSizeSwitcher').css({
        "position":   "fixed",
        "width":      "100%",
        "padding-left": "60px",
        "z-index":    "1000",
        "top":        "0",
        "left":       "0"
    });

    $('#articlePdf').css({
        "position":   "fixed",
        "z-index":    "1000",
        "top":        "30px",
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
    document.getElementById('articleSizeSwitcher').innerHTML='Розгорнути статтю на всю сторінку';

    $('#articleSizeSwitcher').css({
        "position":     "relative",
        "width":        "auto",
        "padding-left": "22px"
    });

    $('#articlePdf').css({
        "position":   "relative",
        "top":        "0",
        "height":     "650px",
        "width":      "753px"
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

function setReview(articleId){
    var review = $("#reviewText").val();
    if (review.length < 100){
        alert("Рецензія не коректна")
    } else {
        var reviewJson = {
            articleId: articleId,
            review: $("#reviewText").val()
        };
        $.ajax({
            url: "/setReview",
            contentType: 'application/json',
            data: JSON.stringify(reviewJson),
            async: false,
            type: 'POST',
            success: function (data) {
                if (data =="OK"){
                    window.location.reload();
                } else {
                    alert(data);
                }
            },
            error: function (xhr, status, errorThrown) {
                alert('Виникла помилка: ' + status + ". " + errorThrown);
            }
        });
    }
}

function getReview(reviewId) {
    $.ajax({
        data: "reviewId="+reviewId,
        dataType: 'json',
        type: 'GET',
        url: '/getReview',
        success: function (data) {
            $('#reviewSection').show();
            var reviewMessage = $('#reviewMessage');
            for (var i = 0; i < data.length; i++){
                var par = $('<p></p>');
                par.html(data[i]);
                reviewMessage.append(par);
            }
        },
        error: function (xhr, status, errorThrown) {
            alert('Виникла помилка: ' + status + ". " + errorThrown);
        }
    });
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
            var allComments = $('#allComments');
            var commentBox = $('<div class="commentBox"></div>');
            var commentator = $('<span class="commentator"></span>');
            commentator.html(data);

            var commentDate = $('<span class="commentDate"></span>');

            var date = new Date();
            var options = {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
                time: 'numeric'
            };
            var localDate = date.toLocaleString("ua", options);
            commentDate.html(localDate);

            var newComment = $('<p class="allCommentsText"></p>');
            newComment.html($('#newComment').val());

            commentBox.append(commentator).append(commentDate).append(newComment);
            allComments.append(commentBox);

            $('#newComment').val('');
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