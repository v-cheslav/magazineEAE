$(document).ready(function(){
    $('#remindPassword').click(function(){
        sendRestoreInformation()
    });

    $('#restorePasswordForm').validate({
        rules: {
            username: {email: true, required: true},
            restoreCodeStr: {required: true},
            newPassword: {
                minlength: 8,
                pwCheck: true,
                required: true},
            newPasswordConfirm: {equalTo: "#newPassword"}
        },
        messages: {
            username: { email: "Введіть будь-ласка Вашу електронну пошту."},
            restoreCodeStr: {required: "Введіть номер надісланий на e-mail."},
            newPassword: {
                minlength: "Введіть будь-ласка мінімум {0} символів.",
                required: "Введіть будь-ласка пароль.",
                pwCheck: "Пароль повинен містити цифри, великі і малі букви латинського алфавіту."
            },
            newPasswordConfirm: {equalTo: "Паролі не співпадають."}
        }
    });

    $('#restorePasswordBtn').click( function() {
        if ($("#restorePasswordForm").valid()){
            restorePassword();
        } else {
            alert("Заповніть будь-ласка коректно форму відновлення паролю.")
        };
    });

})

function sendRestoreInformation (){
    var email = $("#j_username").val();
    if (email != "" && email != " ") {
        $.ajax({
            url: "/sendRestoreInformation",
            contentType: 'application/json',
            data: JSON.stringify(email),
            async: false,
            type: 'POST',
            success: function (data) {
                if (data == "OK") {
                    window.location.href = '/restorePasswordPage'
                } else {
                    alert(data);
                };
            },
            error: function (xhr, status, errorThrown) {
                alert('Виникла помилка: ' + status + ". " + errorThrown);
            }
        });
    } else {alert("Для відновлення паролю вкажіть вашу електронну адресу.")}

}

function restorePassword() {
    var restoreInformation = {
        email: $("#username").val(),
        restoreCodeStr: $("#restoreCodeStr").val(),
        newPassword: $("#newPassword").val()
    };


            $.ajax({
                url: "/restorePassword",
                contentType: 'application/json',
                data: JSON.stringify(restoreInformation),
                async: false,
                type: 'POST',
                success: function (data) {
                    if (data == "OK") {
                        window.location.href = '/login'
                    } else {
                        alert(data);
                    }
                    ;
                },
                error: function (xhr, status, errorThrown) {
                    alert('Виникла помилка: ' + status + ". " + errorThrown);
                }
            });


}

$.validator.addMethod("pwCheck", function(value) {
    return /(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{8,}$/.test(value)
});

function registrationPage(){
    window.location.href = '/registration'
}