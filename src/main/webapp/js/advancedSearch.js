$(document).ready(function() {

    $("#dateFrom").datepicker({dateFormat: 'dd.mm.yy'});
    $("#dateTo").datepicker({dateFormat: 'dd.mm.yy'});

    setSearchParameters();
    $('#searchTypeBt').click(function () {
        setSearchParameters();
    });

});

function advancedSearch(){
    var searchType = checkSearchType();
    if (searchType === 'ARTICLE') {
        var article = {
            nameOfArticle: $("#nameOfArticle").val(),
            nameOfUser: $("#nameOfUser").val(),
            acadStatus: $("#acadStatus :selected").val(),
            sciDegree: $("#sciDegree :selected").val(),
            dateFrom: $("#dateFrom").val(),
            dateTo: $("#dateTo").val(),
            keyWords: $("#keyWordsSearch").val(),
            section: $("#selectSection :selected").val()
        };

        $.ajax({
            url: "/searchArticles",
            contentType: 'application/json',
            data: JSON.stringify(article),
            async: false,
            type: 'POST',
            success: function (data) {
                clearContent("tableContent");
                clearContent("errorMessage");
                if (data[0] == null){
                    $("#errorMessage").html("За даними критеріями пошуку нічого не знайдено!");
                } else {
                    var dataType = 'article';
                    fillTable(data, dataType);
                }
                //if (data[0].id != -1){
                //
                //    var dataType = 'article';
                //    fillTable(data, dataType);
                //} else {
                //    $("#errorMessage").html(data[0].publicationName);
                //}
            },
            error: function (xhr, status, errorThrown) {
                alert('Виникла помилка: ' + status + ". " + errorThrown);
            }
        });
    } else if (searchType === 'SEMINAR') {
        var seminar = {
            nameOfSeminar: $("#nameOfArticle").val(),
            nameOfUser: $("#nameOfUser").val(),
            acadStatus: $("#acadStatus :selected").val(),
            sciDegree: $("#sciDegree :selected").val(),
            dateFrom: $("#dateFrom").val(),
            dateTo: $("#dateTo").val(),
            keyWords: $("#keyWordsSearch").val()
        };

        $.ajax({
            url: "/searchSeminars",
            contentType: 'application/json',
            data: JSON.stringify(seminar),
            async: false,
            type: 'POST',
            success: function (data) {
                clearContent("tableContent");
                clearContent("errorMessage");
                if (data[0] == null){
                    $("#errorMessage").html("За даними критеріями пошуку нічого не знайдено!");
                } else {
                    var dataType = 'seminar';
                    fillTable(data, dataType);
                }
                //if (data[0].id != -1){
                //    var dataType = 'seminar';
                //    fillTable(data, dataType);
                //} else {
                //    $("#errorMessage").html(data[0].seminarName);
                //}
            },
            error: function (xhr, status, errorThrown) {
                alert('Виникла помилка: ' + status + ". " + errorThrown);
            }
        });
    } else if (searchType === 'AUTHORSrch') {
        var user = {
            nameOfUser: $("#nameOfUser").val(),
            acadStatus: $("#acadStatus :selected").val(),
            sciDegree: $("#sciDegree :selected").val()
        };
        $.ajax({
            url: "/searchUsers",
            contentType: 'application/json',
            data: JSON.stringify(user),
            async: false,
            type: 'POST',
            success: function (data) {
                clearContent("listContent");
                clearContent("errorMessage");
                if (data[0] == null){
                    $("#errorMessage").html("За даними критеріями пошуку нічого не знайдено!");
                } else {
                    fillTableByUsers(data);
                }
                //if (data[0].userId != -1){
                //    fillTableByUsers(data);
                //
                //} else {
                //    $("#errorMessage").html(data[0].userName);
                //}
            },
            error: function (xhr, status, errorThrown) {
                alert('Виникла помилка: ' + status + ". " + errorThrown);
            }
        });
    } else {
        alert("Виникла помилка!")
    }
}

function fillTable(data, dataType){
    var options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    };
    var table = $('table');
    for (var i = 0; i < data.length; i++) {
        var date = new Date(data[i].publicationDate);
        var url = dataType+"Page?publicationId="+data[i].id;
        var tdTheme = $('<td align="justify" class="tableContent"></td>');
        var linkTheme = $('<a></a>');
        linkTheme.html(data[i].publicationName);
        linkTheme.attr('id', data[i].id);

        //if (dataType == 'article'){
        //    url = "articlePage?publicationId="+data[i].id;
        //} else if (dataType == 'seminar'){
        //    url = "seminarPage?seminarId="+data[i].id;
        //}

        linkTheme.attr('href', url);
        tdTheme.append(linkTheme);

        var row = $('<tr></tr>');
        if (i%2 == 0){
            row.attr('class', "light");
        }
        if (i%2 != 0){
            row.attr('class', "dark");
        }
        var tdNumber = $('<td align="center" class="tableContent"></td>');
        tdNumber.html(i+1);

        var tdPublishDate = $('<td align="center" class="tableContent"></td>');
        tdPublishDate.html(date.toLocaleString("ua", options));

        var tdAuthor = $('<td align="center" class="tableContent"></td>');
        var linkAuthor= $('<a></a>');

        var userUrl = "authorPage?authorId="+data[i].user.userId;
        linkAuthor.attr('href', userUrl);
        linkAuthor.attr('id', data[i].user.userId);
        linkAuthor.html(data[i].user.surname + ' ' + data[i].user.name + ' ' + data[i].user.middleName);
        linkAuthor.attr('authorId', data[i].user.userId);
        tdAuthor.append(linkAuthor);

        row.append(tdNumber).append(tdPublishDate).append(tdAuthor).append(tdTheme);
        table.append(row);
    }
};

function fillTableByUsers(data){
    var listContent = $('#listContent');
    var userListContent = $('<ol></ol>');
    for (var i = 0; i < data.length; i++) {
        var userList = $('<li class="userListContent"></li>');
        var linkAuthor = $('<a></a>');
        var userUrl = "authorPage?authorId=" + data[i].userId;
        linkAuthor.attr('href', userUrl);
        linkAuthor.attr('id', data[i].userId);
        linkAuthor.attr('authorId', data[i].userId);
        linkAuthor.html(data[i].surname + ', ' + data[i].name + ', ' + data[i].middleName);
        var university = $('<span></span>');
        if (data[i].university != null){
            university.html(' ' + data[i].university);
        }
        var institute = $('<span></span>');
        if (data[i].institute != null){
            institute.html(' ' + data[i].institute);
        }
        userList.append(linkAuthor).append(university).append(institute);
        userListContent.append(userList)
    }
    listContent.append(userListContent);
};

function setSearchParameters(){
    var searchType = checkSearchType();
    var tableContent = document.getElementById("tableContent");

    if (searchType === 'ARTICLE') {
        $("#publicationName").show();
        $("#datepickerContainer").show();
        $("#keyWordsSearchContainer").show();
        $("#selectSectionContainer").show();
        $("#tables").show();
        $("#listContent").hide();
        document.getElementById('barHeader').innerHTML = 'Статті';
        while (tableContent.lastChild) {
            tableContent.removeChild(tableContent.lastChild);
        }
    } else if (searchType === 'SEMINAR') {
        $("#publicationName").show();
        $("#datepickerContainer").show();
        $("#keyWordsSearchContainer").show();
        $("#selectSectionContainer").hide();
        $("#tables").show();
        $("#listContent").hide();
        document.getElementById('barHeader').innerHTML = 'Доповіді семінару';
        while (tableContent.lastChild) {
            tableContent.removeChild(tableContent.lastChild);
        }
    } else if (searchType === 'AUTHORSrch') {
        $("#publicationName").hide();
        $("#datepickerContainer").hide();
        $("#keyWordsSearchContainer").hide();
        $("#selectSectionContainer").hide();
        $("#tables").hide();
        $("#listContent").show();
        document.getElementById('barHeader').innerHTML = 'Автор';
    }
}

function checkSearchType(){
    var searchType = document.getElementsByName('searchTypeBt');
    for (var i = 0; i < searchType.length; i++) {
        if (searchType[i].type == "radio" && searchType[i].checked) {
            return searchType[i].value;
        }
    }
};

function clearContent(content){
    var tableContent = document.getElementById(content);
    while (tableContent.lastChild) {
        tableContent.removeChild(tableContent.lastChild);
    }
}