$(document).ready(function () {
    $('#message').hide();

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
        }
    });

    $('#btnClear').on('click', function() {
        var file = $('#photo');
        file.val('');
    });
});


function addUser(){
    $.ajax({
        url: '/regUser',
        type: "POST",
        data: new FormData(document.getElementById("registrationForm")),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false
    }).done(function(data) {
        if (data.registrationMassage == null){
            $('#regContent').hide();
            $('#message').show();
        }
        if (data.registrationMassage != null){
            var errorMessage = $('#regErrorMessage');
            errorMessage.html(data.registrationMassage);
        }

    }).fail(function(jqXHR, textStatus) {
        alert(jqXHR + textStatus + 'Помилка завантаження! Спробуйте змінити назву файлу.');
    });
}

$.validator.addMethod("pwCheck", function(value) {
    return /(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{8,}$/.test(value)
});




