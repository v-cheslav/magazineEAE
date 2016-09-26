$(document).ready(function(){



    menuControl();
    scrollWindow();
    seminarMenuControl();
});

function menuControl(){
    $('#seminarContent').hide();
    $('#addAdminRights').hide();
    $('#articleMenuBtn').click(function(){
        $('#seminarContent').hide();
        $('#addAdminRights').hide();
        $('#articleContent').show();
    });

    $('#seminarMenuBtn').click(function(){
        $('#articleContent').hide();
        $('#addAdminRights').hide();
        $('#seminarContent').show();
        autocompleteUsers();

    });

    $('#adminMenuBtn').click(function(){
        $('#articleContent').hide();
        $('#seminarContent').hide();
        $('#addAdminRights').show();
    });
};

function seminarMenuControl(){


    $('#newSeminarChBox').removeAttr('checked');


    $('#newSeminarChBox').click(function () {
        $('#newSeminar').toggle();
        $('#existedSeminar').toggle();
        $('#SeminarMessageContent').toggle();
    });

    $('#changeSemNameChBox').click(function(){
        $('#changedSemName').toggle();
    });

    $('#newSeminar').hide();

    $('#seminarCreation').click(function(){
        $('#deleteUserContainer').hide();
        $('#seminarCreationContainer').show();
    });


    $('#changedSemName').hide();
    $('#changeSemNameChBox').removeAttr('checked');


    $( "#datepicker" ).datepicker({ dateFormat: 'dd.mm.yy' });

    $('#seminarPublishForm').validate({
        rules: {
            seminarName: {
                required: true
            },
            calendar: {
                required: true
            }
        },
        messages: {
            seminarName:{
                required: "Введіть назву семінару."
            },
            calendar:{
                required: "Введіть дату доповіді."
            }
        }
    });

    $('#publSeminarBtn').click(function(){
        //if ($("#seminarPublishForm").valid()){//todo
        createUnpublishedSeminar()
        //} else {
        //    alert("Заповніть будь-ласка коректно форму реєстрації.")
        //};
    });
};

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


function getArticlesWithoutReviewers() {
    $.ajax({
        type: 'GET',
        url: '/getArticlesWithoutReviewers',
        //data: JSON.stringify(chosenSection),
        contentType: 'application/json',
        success: function (data) {
            console.log(data);
            var tableType = 'articlesTable';
            fillTableByPublications(data, tableType);
            autocompleteArticles(data);
        },
        error: function (event, xhr, options, exc) {
            alert('Виникла помилка: ' + event + ' ' + xhr + ' ' + options + ' ' + exc);
        }
    });
};


function fillTableByPublications(publications, tableType){

    if (tableType == 'articlesTable'){
        var tableContent = document.getElementById("articlesTableContent");
    } else if (tableType == 'seminarsTable') {
        var tableContent = document.getElementById("seminarsTableContent");
    }
    while (tableContent.lastChild) {
        tableContent.removeChild(tableContent.lastChild);
    }

    var options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    };

    var table;
    if (tableType == 'articlesTable'){
        table = $('#articlesTable');
    } else if (tableType == 'seminarsTable') {
        table = $('#seminarsTable');
    }

    for (var i = 0; i < publications.length; i++) {
        var date = new Date(publications[i].publicationDate);
        var row = $('<tr></tr>');
        if (i%2 == 0){
            row.attr('class', "light");
        }
        if (i%2 != 0){
            row.attr('class', "dark");
        }
        var tdNumber = $('<td align="center" class="tableContent"></td>');
        tdNumber.html(i+1);

        var tdPublishDate  =$('<td align="center" class="tableContent"></td>');
        tdPublishDate.html(date.toLocaleString("ua", options));

        var tdAuthor = $('<td align="center" class="tableContent"></td>');
        var userUrl = "authorPage?authorId="+publications[i].user.userId;
        var linkAuthor = $('<a></a>');
        linkAuthor.attr('href', userUrl);
        linkAuthor.attr('id', publications[i].user.userId);
        linkAuthor.html(publications[i].user.surname + ' ' + publications[i].user.name + ' ' + publications[i].user.middleName);
        linkAuthor.attr('authorId', publications[i].user.userId);
        tdAuthor.append(linkAuthor);

        var tdTheme = $('<td align="justify" class="tableContent"></td>');
        var url = "articlePage?publicationId="+publications[i].id;
        var linkTheme = $('<a></a>');
        linkTheme.html(publications[i].publicationName);
        linkTheme.attr('href', url);
        linkTheme.attr('id', publications[i].id);
        tdTheme.append(linkTheme);

        row.append(tdNumber).append(tdPublishDate).append(tdAuthor).append(tdTheme);
        table.append(row);

    }
};

function autocompleteArticles(articles) {

    var article = document.getElementsByName("selectArticle");
    var new_options = "<option selected='selected' value=''>Стаття</option>";

    var articleId;
    var articleName;

    for (var i in articles) {
        articleId = articles[i].publicationId;
        articleName = articles[i].publicationName;
        new_options += "<option value='" + articleId + "'>" + articleName + "</option>";
    }
    $(article).html(new_options);
}

function selectArticleForAddReviewers() {

    var oldSelectedArticle = document.getElementById("addReviewers");
    while (oldSelectedArticle.lastChild) {
        oldSelectedArticle.removeChild(oldSelectedArticle.lastChild);
    }

    var articleId = $("#selectArticle :selected").val();
    if(articleId != ''){

        var addReviewers = $("#addReviewers");

        var firstReviewer = $('<div></div>');
        var firstLabel = $('<label for="firstReviewer" class="label"></label>');
        var firstReviewerSelect = $('<select class="textField" id="firstReviewer" name="firstReviewer"></select>');
        firstReviewer.append(firstLabel).append(firstReviewerSelect);

        var secondReviewer = $('<div></div>');
        var secondLabel = $('<label for="secondReviewer" class="label"></label>');
        var secondReviewerSelect = $('<select class="textField" id="secondReviewer" name="secondReviewer"></select>');
        secondReviewer.append(secondLabel).append(secondReviewerSelect);

        autocompleteReviewers();

        var button = $('<input type="button" class="button" id="addReviewersBtn" value="Призначити">');
        addReviewers.append(firstReviewer).append(secondReviewer).append(button);


        $('#addReviewersBtn').click(function(){
            addReviewersFunction(articleId)
        });
    };
}

function autocompleteReviewers() {
    $.ajax({
        type: 'GET',
        url: '/getAllReviewers',
        data: '',
        dataType: "json",
        success: function (data) {
            var firstSelectedReviewer = document.getElementsByName("firstReviewer");
            var new_options1 = "<option selected='selected' value=''>Оберіть 1-го рецензента</option>";
            fillSelectedReviewers(new_options1, data, firstSelectedReviewer);

            var secondSelectedReviewer = document.getElementsByName("secondReviewer");
            var new_options2 = "<option selected='selected' value=''>Оберіть 2-го рецензента</option>";
            fillSelectedReviewers(new_options2, data, secondSelectedReviewer);
        },
        error: function (event, xhr, options, exc) {
            alert('Виникла помилка' + xhr + ' ' + options + ' ' + exc);
        }
    });
}

function addReviewersFunction(articleId){

    var firstReviewerId = getReviewerId('firstReviewer');
    var secondReviewerId = getReviewerId('secondReviewer');

        var reviewers = {
        articleId: articleId,
        firstReviewer: firstReviewerId,
        secondReviewer: secondReviewerId
    };

    $.ajax({
        url: "/addReviewers",
        contentType: 'application/json',
        data: JSON.stringify(reviewers),
        async: false,
        type: 'POST',
        success: function (data) {
            var reviewersAddingError = $('#reviewersAddingError');
            if (data == "OK"){
                location.reload();
            } else {
                reviewersAddingError.html(data);
            };

        },
        error: function (xhr, status, errorThrown) {
            alert('Виникла помилка: ' + status + ". " + errorThrown);
        }
    });
}

function getReviewerId (DOMname){
    var reviewer = $("[name="+DOMname+"] option:selected");
    var reviewerId;
    for (var i=0; i<reviewer.length; i++){
        if (reviewer[i].value != ""){
            reviewerId = reviewer[i].value;
        }
    };
    return reviewerId;
}

function autocompleteUsers() {
    $.ajax({
        type: 'GET',
        url: '/getAllUsers',
        data: '',
        dataType: "json",
        success: function (data) {
            var new_options = "<option selected='selected' value=''>Виберіть доповідача зі списку</option>";
            var user;
            var userId;
            var userName;

            for (var i in data) {
                user = data[i]
                userId = user[0];
                userName = user[1];
                new_options += "<option value='" + userId + "'>" + userName + "</option>";
            }
            $('#userName').html(new_options);
            $('#selectUserForDelete').html(new_options);

        },
        error: function (event, xhr, options, exc) {
            alert('Виникла помилка' + xhr + ' ' + options + ' ' + exc);
        }
    });
}


function getDeclaredSeminars() {
    $.ajax({
        type: 'GET',
        url: '/getDeclaredSeminars',
        contentType: 'application/json',
        success: function (data) {
            console.log(data);
            var tableType = 'seminarsTable';
            fillTableByPublications(data, tableType);
            //autocompleteSeminars(data);
        },
        error: function (event, xhr, options, exc) {
            alert('Виникла помилка: ' + event + ' ' + xhr + ' ' + options + ' ' + exc);
        }
    });
};

function createUnpublishedSeminar(){
    var seminarId = "";
    var userId ="";
    var unRegUserName ="";
    var seminarName ="";
    if (!$('#newSeminarChBox').prop("checked")) {
        seminarId = $("#chooseSeminarName :selected").val();
        if ($('#changeSemNameChBox').prop("checked")) {
            seminarName = $("#existedSeminarName").val();
        } else {
            seminarName = "";
        }
    } else {
        userId = $("#userName :selected").val();
        unRegUserName = $("#unRegUserName").val();
        seminarName = $("#newSeminarName").val();
    }

    var reportDate = $("#datepicker").val();

    var seminar = {
        seminarId: seminarId,
        seminarName: seminarName,
        userId: userId,
        unRegUserName: unRegUserName,
        reportDate: reportDate
    };
    $.ajax({
        url: "/announceSeminar",
        contentType: 'application/json',
        data: JSON.stringify(seminar),
        async: false,
        type: 'POST',
        success: function (data) {
            var seminarRegErrorMessage = $('#seminarRegErrorMessage');
            if (data == "OK") {
                alert("Інформацію опубліковано.");
                location.reload();
            } else {
                seminarRegErrorMessage.html(data);
            }
        },
        error: function (xhr, status, errorThrown) {
            alert('Виникла помилка при завантаженні: ' + status + ". " + errorThrown);
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



function deleteUser() {
    var userId = $("#selectUserForDelete :selected").val();
    if (confirm("Видалити користувача?")) {
        $.ajax({
            url: "/deleteUser",
            contentType: 'application/json',
            data: JSON.stringify(userId),
            async: false,
            type: 'POST',
            success: function (data) {
                if (data == "OK") {
                    alert("Користувача видалено")
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