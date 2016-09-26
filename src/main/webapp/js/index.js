$(document).ready(function(){

    $('#sectionsMenuHeader').on('click', function(){
        $('#sectionsMenuContent').toggle();
    });
    $('#publishMenuHeader').on('click', function(){
        $('#publishMenuContent').toggle();
    });
    $('#scheduleMenuHeader').on('click', function(){
        $('#scheduleMenuContent').toggle();
    });

    $(window).scroll(function(){
        if($(this).scrollTop()>413){
            $('#topnav').addClass('fixed');
        }
        else if ($(this).scrollTop()<413){
            $('#topnav').removeClass('fixed');
        }
    });


})
//
//function toFullPage (){
//    alert("toFullPage");
//    $('#mainContentHeader').css({
//        "width":      "749px"
//    });
//
//}
