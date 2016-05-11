$(document).ready(function(){

    $('#articlePublCheck').click(function(){
        contentFiller("/articlePublicationContent");

    });

    $('#seminarPublCheck').click(function(){
        contentFiller("/seminarPublicationContent");
    });
})

function contentFiller(content){
    $.ajax({
        url: content,
        cache: false,
        success: function(html){
            $("#publishContent").html(html);
        }
    });
}
