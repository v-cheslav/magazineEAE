$(document).on({
    ajaxStart: function() { $('#uploading').show();},
    ajaxStop: function() { $('#uploading').hide(); }
});

$(document).ready(function() {
    var date = new Date();
    var options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    };
    var localDate = date.toLocaleString("ua", options);
    if ($("#currentDate") != null) {
        document.getElementById('currentDate').innerHTML = localDate;
    }

    $('#searchingForm').click(function(){
        alert("search");
    });

});


