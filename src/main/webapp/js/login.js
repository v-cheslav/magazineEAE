$(document).ready(function(){
    $('#remindPassword').click(function(){
        remindPassword()
    });
})

function remindPassword (){
    var email = $("#j_username").val();
    alert(email)
    if (email != "" && email != " ") {
        $.ajax({
            url: "/remindPassword",
            contentType: 'application/json',
            data: JSON.stringify(email),
            async: false,
            type: 'POST',
            success: function (data) {
                if (data == "OK") {
                    alert("Повідомлення з паролем відправлено на електронну адресу.")
                    window.location.reload();
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
