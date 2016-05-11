$(document).ready(function() {
    var file = $('[name="file"]');

    $('#fileForm').validate({
        rules: {
            file: {required: true}
        },
        messages: {file: {required: "Виберіть фото для завантаження."}
        }
    });

    $('#btnUpload').on('click', function() {
        if ($("#fileForm").valid()) {
            var filename = $.trim(file.val()).split('\\').pop();
            var form;
            if (!(isJpg(filename) || isPng(filename) || isPNG(filename) || isJPG(filename))) {
                return;
            }
            form = document.getElementById("fileForm");
            uploadFile(file, 'image', form);
        }
    });
});

function uploadFile(file, type, form){
    if (type == 'image'){
        var imgContainer = $('#newImgContainer');
    }
    $.ajax({
        url: '/saveImage',//todo rename to saveFile
        type: "POST",
        data: new FormData(form),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false
    }).done(function(data) {
        if (type=='image'){
            imgContainer.html('');
            var img = '<img src="data:' + data.contenttype + ';base64,'
                + data.base64 + '"/>';
            imgContainer.append(img);
        }

    }).fail(function(jqXHR, textStatus) {
        alert(jqXHR + textStatus + 'Помилка завантаження! Спробуйте змінити назву файлу.');
    });

    $('#btnClear').on('click', function() {
        if (type=='image') {
            imgContainer.html('');
            var img = '<img src="../images/noPhotoMan.png">';
            imgContainer.append(img);
        }
        file.val('');
    });
}

var isJpg = function(name) {
    return name.match(/jpg$/i)
};

var isPng = function(name) {
    return name.match(/png$/i)
};

var isJPG = function(name) {
    return name.match(/JPG$/i)
};

var isPNG = function(name) {
    return name.match(/PNG$/i)
};

var isPdf = function(name) {
    return name.match(/pdf$/i)
};

var isSwf = function(name) {
    return name.match(/swf$/i)
};