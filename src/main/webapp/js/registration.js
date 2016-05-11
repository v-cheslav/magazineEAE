$(document).ready(function () {

    $('#registrationForm').validate({
        rules: {
            username: {email: true, required: true},
            name: {required: true},
            surname: {required: true},
            university: {required: true},
            password: {minlength: 8,
                pwCheck: true,
                required: true},
            passwordConfirm: {equalTo: "#password"}
        },
        messages: {
            username: { email: "Введіть будь-ласка Вашу електронну пошту."},
            name:{required: "Введіть будь-ласка ім'я."},
            surname:{required: "Введіть будь-ласка прізвище."},
            university:{required: "Необхідно вказати Ваш універсисет."},
            password: {
                minlength: "Введіть будь-ласка мінімум {0} символів.",
                required: "Введіть будь-ласка пароль.",
                pwCheck: "Пароль повинен містити цифри, великі і малі букви латинського алфавіту."
            },
            passwordConfirmation: {equalTo: "Паролі не співпадають."}
        }
    });

    $('#regBtn').click( function() {
        if ($("#registrationForm").valid()){
            addUser();
        } else {
            alert("Заповніть будь-ласка коректно форму реєстрації.")
        };
    });
});

function addUser(){
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
            phone: $("#pnone").val(),
            photo: $.trim(file.val()).split('\\').pop(),
            acadStatus: $("#acadStatus :selected").val(),
            sciDegree: $("#sciDegree :selected").val(),
            userSex: (checkSex()),
            interests: $("#keyWords").val(),
            isAdministrator: (checkAdmin())
        };

        $.ajax({
            url: "/regUser",
            contentType: 'application/json',
            data: JSON.stringify(user),
            async: false,
            type: 'POST',
            success: function (data) {
                var message = $('#regErrorMessage');
                if (data =="OK"){
                    window.location.href = '/login'
                } else {
                    message.html(data);
                };

            },
            error: function (xhr, status, errorThrown) {
                alert('Виникла помилка при реєстрації: ' + status + ". " + errorThrown);
            }
        });
};

function checkAdmin(){
        var isAdmin = document.getElementsByName('adminChBox');
        if (isAdmin[0].type == "checkbox" && isAdmin[0].checked) {
            return isAdmin[0].value;
        } else {
            return '';
        }
};

function checkSex(){
    var userSex = document.getElementsByName('userSex');
    for (var i = 0; i < userSex.length; i++) {
        if (userSex[i].type == "radio" && userSex[i].checked) {
            return userSex[i].value;
        }
    }
};

$.validator.addMethod("pwCheck", function(value) {
    return /(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{8,}$/.test(value)
});

function registrationPage(){
    window.location.href = '/registration'
}
