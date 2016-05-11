$(document).ready(function(){

    autocompleteUsers();

    $('#seminarCreationContainer').hide();
    $('#deleteUserContainer').hide();
    $('#newSeminar').hide();

    $('#seminarCreation').click(function(){
        $('#deleteUserContainer').hide();
        $('#seminarCreationContainer').show();
    });

    $('#deleteUser').click(function(){
        $('#seminarCreationContainer').hide();
        $('#deleteUserContainer').show();
    });



    $('#newSeminarChBox').removeAttr('checked');

    $('#newSeminarChBox').click(function () {
        $('#newSeminar').toggle();
        $('#existedSeminar').toggle();
    });

    $('#changedSemName').hide();
    $('#changeSemNameChBox').removeAttr('checked');

    $('#changeSemNameChBox').click(function(){
        $('#changedSemName').toggle();
    });


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

});

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

            //alert("SeminarId=" +seminarId + "  seminarName=" +seminarName +"  userId=" +userId +"  unRegUserName=" +unRegUserName +"  reportDate=" +reportDate );
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

