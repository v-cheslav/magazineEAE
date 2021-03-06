$(document).ready(function(){

    var chosenSection = checkSections();

    fillSectionsHeader(chosenSection);
    getSeminars(chosenSection);
    $('#sections').on('click', function () {
        var currentSection = checkSections();
        if (chosenSection != currentSection) {
            chosenSection = currentSection;
            fillSectionsHeader(chosenSection);
            getSeminars(chosenSection);
        }
    })

    $(window).scroll(function(){
        if($(this).scrollTop()>220){
            $('#topnav').addClass('fixed');
        }
        else if ($(this).scrollTop()<220){
            $('#topnav').removeClass('fixed');
        }
    });
});


function getSeminars(chosenSection) {
    $.ajax({
        type: 'POST',
        url: '/getSeminars',
        data: JSON.stringify(chosenSection),
        contentType: 'application/json',
        success: function (data) {
            fillTableBySeminars(data);
        },
        error: function (event, xhr, options, exc) {
            alert('Виникла помилка: ' + event + ' ' + xhr + ' ' + options + ' ' + exc);
        }
    });
};

function fillTableBySeminars(seminars){
    var tableContent = document.getElementById("tableContent");
    while (tableContent.lastChild) {
        tableContent.removeChild(tableContent.lastChild);
    }

    var options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    };

    var table = $('table');
    for (var i = 0; i < seminars.length; i++) {

        var row = $('<tr></tr>');
        if (i%2 == 0){
            row.attr('class', "light");
        }
        if (i%2 != 0){
            row.attr('class', "dark");
        }
        var tdNumber = $('<td align="center" class="tableContent"></td>');
        tdNumber.html(i+1);

        var tdPublishDate  = $('<td align="center" class="tableContent"></td>');
        var date = new Date(seminars[i].publicationDate);
        tdPublishDate.html(date.toLocaleString("ua", options));

        var tdAuthor = $('<td align="center" class="tableContent"></td>');
        var linkAuthor= $('<a></a>');
        if (seminars[i].user == null){
            linkAuthor.html(seminars[i].unRegUserName);
        } else {
            var userUrl = "authorPage?authorId="+seminars[i].user.userId;
            linkAuthor.attr('href', userUrl);
            linkAuthor.attr('isd', seminars[i].user.userId);
            linkAuthor.html(seminars[i].user.surname + ' ' + seminars[i].user.name + ' ' + seminars[i].user.middleName);
            linkAuthor.attr('authorId', seminars[i].user.userId);
        }

        tdAuthor.append(linkAuthor);

        var tdTheme  =$('<td align="justify" class="tableContent"></td>');
        var linkTheme = $('<a></a>');
        if (seminars[i].isPublished == true) {
            var url = "seminarPage?publicationId="+seminars[i].id;
            linkTheme.attr('href', url);

        }
        linkTheme.html(seminars[i].publicationName);
        linkTheme.attr('id', seminars[i].id);
        tdTheme.append(linkTheme);

        row.append(tdNumber).append(tdPublishDate).append(tdAuthor).append(tdTheme);
        table.append(row);
    }
};

function fillSectionsHeader(chosenSection){
    switch(chosenSection) {
        case 'ALL':
            document.getElementById('tableName').innerHTML = 'Доповіді семінару';
            $('#seminarInformation').hide();
            $('#innerTable').show();
            break;
        case 'NEXT':
            document.getElementById('tableName').innerHTML = 'Розклад наступних доповідей';
            $('#seminarInformation').hide();
            $('#innerTable').show();
            break;
        case 'ABOUT':
            document.getElementById('tableName').innerHTML = 'Про семінар';
            $('#innerTable').hide();
            $('#seminarInformation').show();
            break;
        default:
            alert('Невірне значення!');
            break;
    }
};

function checkSections(){
    var section = document.getElementsByName('section');
    for (var i = 0; i < section.length; i++) {
        if (section[i].type == "radio" && section[i].checked) {
            return section[i].value;
        }
    }
};

