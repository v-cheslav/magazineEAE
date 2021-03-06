$(document).ready(function(){

    var chosenSection = checkSections();
    fillArticlesHeader(chosenSection);
    getArticles(chosenSection);
    $('#sections').on('click', function() {
        var currentSection = checkSections();
        if (chosenSection != currentSection){
            chosenSection = currentSection;
            fillArticlesHeader(chosenSection);
            getArticles(chosenSection);
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


function getArticles(chosenSection) {
    $.ajax({
        type: 'POST',
        url: '/getArticles',
        data: JSON.stringify(chosenSection),
        contentType: 'application/json',
        success: function (data) {
            console.log(data);
            fillTableByArticles(data);
        },
        error: function (event, xhr, options, exc) {
            alert('Виникла помилка: ' + event + ' ' + xhr + ' ' + options + ' ' + exc);
        }
    });
};

function fillTableByArticles(articles){
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
    for (var i = 0; i < articles.length; i++) {
        var date = new Date(articles[i].publicationDate);
        var row = $('<tr></tr>');
        if (i%2 == 0){
            row.attr('class', "light");
        }
        if (i%2 != 0){
            row.attr('class', "dark");
        }
            var tdNumber = $('<td align="center" class="tableContent"></td>');
            tdNumber.html(i+1);

            var tdPublishDate  =$('<td align="center" class="tableContent"></td>');
            tdPublishDate.html(date.toLocaleString("ua", options));

            var tdAuthor = $('<td align="center" class="tableContent"></td>');
                var userUrl = "authorPage?authorId="+articles[i].user.userId;
                var linkAuthor = $('<a></a>');
                linkAuthor.attr('href', userUrl);
                linkAuthor.attr('id', articles[i].user.userId);
                linkAuthor.html(articles[i].user.surname + ' ' + articles[i].user.name + ' ' + articles[i].user.middleName);
                linkAuthor.attr('authorId', articles[i].user.userId);
            tdAuthor.append(linkAuthor);

            var tdTheme = $('<td align="justify" class="tableContent"></td>');
                var url = "articlePage?publicationId="+articles[i].id;
                var linkTheme = $('<a></a>');
                linkTheme.html(articles[i].publicationName);
                linkTheme.attr('href', url);
                linkTheme.attr('id', articles[i].id);
            tdTheme.append(linkTheme);

        row.append(tdNumber).append(tdPublishDate).append(tdAuthor).append(tdTheme);
        table.append(row);
    }
};

function fillArticlesHeader(chosenSection){
    switch(chosenSection) {
        case 'ALL':
            document.getElementById('tableName').innerHTML = 'Всі статті';
            break;
        case 'AUTOMATION':
            document.getElementById('tableName').innerHTML = 'Автоматика та робототехнічні системи';
            break;
        case 'EXPLOITATION':
            document.getElementById('tableName').innerHTML = 'Експлуатація електрообладнання';
            break;
        case 'MACHINES':
            document.getElementById('tableName').innerHTML = 'Електричні машини';
            break;
        case 'SUPPLYING':
            document.getElementById('tableName').innerHTML = 'Електропостачання';
            break;
        case 'DRIVING':
            document.getElementById('tableName').innerHTML = 'Електропривід';
            break;
        case 'MATHEMATICS':
            document.getElementById('tableName').innerHTML = 'Математика';
            break;
        case 'HEATENERGY':
            document.getElementById('tableName').innerHTML = 'Теплоенергетика';
            break;
        case 'PHYSIC':
            document.getElementById('tableName').innerHTML = 'Фізика';
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

