$(document).ready(function() {
    $('#fillDB').click(function(){
        fillDB();
    });
});

function fillDB(){
    var sendingData = {
        saName: $("#saName").val(),
        saPassword: $("#saPassword").val()
    };
    $.ajax({
        url: "/DBFiller",
        contentType: 'application/json',
        data: JSON.stringify(sendingData),
        async: false,
        type: 'POST',
        success: function (data) {
            if (data =="OK"){
                window.location.href = '/registration'
            } else {
                alert(data);
            };
        },
        error: function (xhr, status, errorThrown) {
            alert('Виникла помилка: ' + status + ". " + errorThrown);
        }
    });
}