$(document).ready(function() {

    $('#firstReviewer').click( function() {
        autocompleteReviewers();
    });
    $('#secondReviewer').click( function() {
        autocompleteReviewers();
    });

    $('#newReviewer1').click( function() {
        setNewReviewer($("#firstReviewer :selected").val());
    });
    $('#newReviewer2').click( function() {
        setNewReviewer($("#secondReviewer :selected").val());
    });

    $('#regBtn').click( function() {
        if ($("#userUpdatingForm").valid()){
            updateUser();
        } else {
            alert("Заповніть будь-ласка коректно форму реєстрації.")
        };
    });

    $('#userUpdatingForm').validate({
        rules: {
            surname: {minlength: 2},
            name: {minlength: 2},
            middleName: {minlength: 2},
            university: {minlength: 2},
            institute: {minlength: 2},
            chair: {minlength: 2},
            position: {minlength: 2},
            phone: {minlength: 7},
            username: {email: true},
            password: {required: false, minlength: 8, pwCheck: true},
            passwordConfirm: {equalTo: "#password"},
            keyWords: {minlength: 2}
        },
        messages: {
            surname: {minlength: "Введіть коректні дані."},
            name: {minlength: "Введіть коректні дані."},
            middleName: {minlength: "Введіть коректні дані."},
            university: {minlength: "Введіть коректні дані."},
            institute: {minlength: "Введіть коректні дані."},
            chair: {minlength: "Введіть коректні дані."},
            position: {minlength: "Введіть коректні дані."},
            phone: {minlength: "Введіть коректні дані."},
            username: {email: "Введіть коректну електронну пошту."},
            password: {
                pwCheck: "Пароль повинен містити цифри, великі і малі букви латинського алфавіту.",
                minlength: "Введіть будь-ласка мінімум {0} символів."
            },
            passwordConfirm: {equalTo: "Паролі не співпадають."},
            keyWords: {minlength: "Введіть коректні дані."}
        }
    });

    var chosenSection = checkSections();
    fillSectionsHeader(chosenSection);
    $('#sections').on('click', function() {
        var currentSection = checkSections();
        if (chosenSection != currentSection){
            chosenSection = currentSection;
            fillSectionsHeader(chosenSection);
            getSeminars(chosenSection);
        }
    })

    $( "#datepicker" ).datepicker({ dateFormat: 'dd.mm.yy' });

});

function updateUser(){
    var file = $('[name="file"]');
    var user = {
        username: $("#username").val(),
        password: $("#password").val(),
        name: $("#name").val(),
        surname: $("#surname").val(),
        middleName: $("#middleName").val(),
        university: $("#university").val(),
        institute: $("#institute").val(),
        chair: $("#chair").val(),
        position: $("#position").val(),
        phone: $("#phone").val(),
        photo: $.trim(file.val()).split('\\').pop(),
        acadStatus: $("#acadStatus :selected").val(),
        sciDegree: $("#sciDegree :selected").val(),
        userSex: (checkSex()),
        interests: $("#keyWords").val()
    };

    $.ajax({
        url: "/updateUser",
        contentType: 'application/json',
        data: JSON.stringify(user),
        async: false,
        type: 'POST',
        success: function (data) {
            var message = $('#regErrorMessage');
            if (data =="OK"){
                alert("Ваші дані успішно оновлені.");
                location.reload();
            } else {
                message.html(data);
            };
        },
        error: function (xhr, status, errorThrown) {
            alert('Виникла помилка при реєстрації: ' + status + ". " + errorThrown);
        }
    });
};

function getUserArticles(userId) {
    $.ajax({
        data: "userId="+userId,
        dataType: 'json',
        type: 'GET',
        url: '/getUserArticles',
        success: function (data) {
            var dataType = 'article';
            fillTable(data, dataType);
        },
        error: function (xhr, status, errorThrown) {
            alert('Виникла помилка: ' + status + ". " + errorThrown);
        }
    });
}

function getUserSeminars(userId) {
    $.ajax({
        data: "userId="+userId,
        dataType: 'json',
        type: 'GET',
        url: '/getUserSeminars',
        success: function (data) {
            var dataType = 'seminar';
            fillTable(data, dataType);
        },
        error: function (xhr, status, errorThrown) {
            alert('Виникла помилка: ' + status + ". " + errorThrown);
        }
    });
}

function getReviewersArticles(userId) {
    $.ajax({
        data: "userId="+userId,
        dataType: 'json',
        type: 'GET',
        url: '/getReviewerArticles',
        success: function (data) {
            var dataType = 'article';
            fillTable(data, dataType);
        },
        error: function (xhr, status, errorThrown) {
            alert('Виникла помилка: ' + status + ". " + errorThrown);
        }
    });
}

function fillTable(data, dataType){
    var tableContent = document.getElementById("tableContent");
    while (tableContent.lastChild) {
        tableContent.removeChild(tableContent.lastChild);
    }

    var options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    };

    var table = $('table');
    for (var i = 0; i < data.length; i++) {
        var date = new Date(data[i].publicationDate);
        var url = dataType+"Page?publicationId="+data[i].id;
        var tdTheme = $('<td align="justify" class="tableContent"></td>');
        var linkTheme = $('<a></a>');
        linkTheme.html(data[i].publicationName);
        linkTheme.attr('id', data[i].id);

        //url = dataType+"Page?publicationId="+data[i].id;
        //if (dataType == 'article'){
        //    url = "articlePage?publicationId="+data[i].id;
        //} else if (dataType == 'seminar'){
        //    url = "seminarPage?seminarId="+data[i].id;
        //}

        linkTheme.attr('href', url);
        tdTheme.append(linkTheme);

        var row = $('<tr></tr>');
        var tdNumber = $('<td align="center" class="tableContent"></td>');
        tdNumber.html(i+1);

        var tdPublishDate = $('<td align="center" class="tableContent"></td>');
        tdPublishDate.html(date.toLocaleString("ua", options));

        var tdAuthor  =$('<td align="center" class="tableContent"></td>');
        var linkAuthor = $('<a href="authorPage.html"></a>');
        linkAuthor.html(data[i].user.surname + ' ' + data[i].user.name + ' ' + data[i].user.middleName);
        linkAuthor.attr('id', data[i].user.userId);
        tdAuthor.append(linkAuthor);

        row.append(tdNumber).append(tdPublishDate).append(tdAuthor).append(tdTheme);
        table.append(row);
    }
};

function setNewReviewer(userId) {
    var userIdJson = {
        userId: userId
    };
    $.ajax({
        url: "/setNewReviewer",
        contentType: 'application/json',
        data: JSON.stringify(userIdJson),
        async: false,
        type: 'POST',
        success: function (data) {
            if (data == "OK") {
                alert("Заявку на рецензування надіслано!");
                window.location.reload();
            } else if((data == "theSameReviewer")){
                $('#confirmation').show();
                $('#confirmBtn').click(function(){
                    setNewReviewerFinally(userId);
                    $('#confirmation').hide();
                });
                $('#cancelConfBtn').click(function(){
                    $('#confirmation').hide();
                });
            } else {
                alert(data);
            };
        },
        error: function (xhr, status, errorThrown) {
            alert('Виникла помилка: ' + status + ". " + errorThrown);
        }
    });
}

function setNewReviewerFinally(userId) {
    var userIdJson = {
        userId: userId
    };
    $.ajax({
        url: "/setNewReviewerFinally",
        contentType: 'application/json',
        data: JSON.stringify(userIdJson),
        async: false,
        type: 'POST',
        success: function (data) {
            if (data == "OK") {
                alert("Заявку на рецензування надіслано!");
                window.location.reload();
            }  else {
                alert(data);
            };
        },
        error: function (xhr, status, errorThrown) {
            alert('Виникла помилка: ' + status + ". " + errorThrown);
        }
    });
}

function autocompleteReviewers() {
    $.ajax({
        type: 'GET',
        url: '/getAllReviewers',
        data: '',
        dataType: "json",
        synchronous: false,
        success: function (data) {
            var reviewer = '#firstReviewer';
            var new_options = '#firstOption';
            fillSelectedReviewers(new_options, data, reviewer);
            var reviewer = '#secondReviewer';
            var new_options = '#secondOption';
            fillSelectedReviewers(new_options, data, reviewer);
        },
        error: function (event, xhr, options, exc) {
            alert('Виникла помилка' + xhr + ' ' + options + ' ' + exc);
        }
    });
}

function fillSelectedReviewers (new_options, data, reviewer){
    var user;
    var userId;
    var userName;
    for (var i in data) {
        user = data[i]
        userId = user[0];
        userName = user[1];
        new_options += "<option value='" + userId + "'>" + userName + "</option>";
    }
    $(reviewer).html(new_options);
}

function checkSex(){
    var userSex = document.getElementsByName('userSex');
    for (var i = 0; i < userSex.length; i++) {
        if (userSex[i].type == "radio" && userSex[i].checked) {
            return userSex[i].value;
        }
    }
};

$.validator.addMethod("pwCheck", function(value) {
    if (value == ""){
        return true;
    } else {
        return /(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{8,}$/.test(value);
    }
});

function fillSectionsHeader(chosenSection){
    switch(chosenSection) {
        case 'PUBLICATIONS':
            document.getElementById('mainContentHeader').innerHTML = 'Мої публікації';
            $('#updateUser').hide();
            $('#applySeminarContainer').hide();
            $('#innerTable').show();
            break;
        case 'REPORTS':
            document.getElementById('mainContentHeader').innerHTML = 'Мої доповіді';
            $('#updateUser').hide();
            $('#applySeminarContainer').hide();
            $('#innerTable').show();
            break;
        case 'REVIEW':
            document.getElementById('mainContentHeader').innerHTML = 'Рецензовані статті';
            $('#updateUser').hide();
            $('#applySeminarContainer').hide();
            $('#innerTable').show();
            break;
        case 'EDIT':
            document.getElementById('mainContentHeader').innerHTML = 'Редагування сторінки';
            $('#innerTable').hide();
            $('#innerTable').hide();
            $('#applySeminarContainer').hide();
            $('#updateUser').show();
        break;
        case 'APPLY':
            document.getElementById('mainContentHeader').innerHTML = 'Заявка на участь в семінарі';
            $('#innerTable').hide();
            $('#updateUser').hide();
            $('#applySeminarContainer').show();
            break;
        default:
            $('#innerTable').hide();
            $('#updateUser').hide();
            $('#applySeminarContainer').hide();
            break;
    }
};

function checkSections(){
    var section = document.getElementsByName('section');
    for (var i = 0; i < section.length; i++) {
        if (section[i].type == "radio" && section[i].checked) {
            return section[i].value;
        }
    }
};

function applySeminar(){
    var reportDate = $("#datepicker").val();
        var seminar = {
            seminarName: $("#newSeminarTheme").val(),
            reportDate: reportDate
        };
        $.ajax({
            url: "/applySeminar",
            contentType: 'application/json',
            data: JSON.stringify(seminar),
            async: false,
            type: 'POST',
            success: function (data) {
                var seminarErrorMessage = $('#seminarErrorMessage');
                if (data == "OK") {
                    alert("Заявку надіслано адміністратору.")
                    location.reload();
                } else {
                    seminarErrorMessage.html(data);
                }
            },
            error: function (xhr, status, errorThrown) {
                alert('Виникла помилка при завантаженні: ' + status + ". " + errorThrown);
            }
        });
}
