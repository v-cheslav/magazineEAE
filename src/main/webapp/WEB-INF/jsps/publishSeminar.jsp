<%@page contentType="text/html;charset=utf-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title></title>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="../../css/site_global.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/publish.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/formsAndButtons.css"/>


    <!-- scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/jquery.validate.min.js"></script>
    <script src="../../js/publishSeminar.js"></script>

</head>
<body>
<div class="header">
    <div class="banner">
        <div class="bannerTop">
            <h3 class="headerUniversityName">Національний університет біоресурсів і природокористування України</h3>

            <div class="authForm" id="authForm">
                <sec:authorize access="isAnonymous()">
                    <p>
                        <a href="/login">Увійти</a>
                        <a href="/registration">Зареєструватись</a>
                    </p>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <p>
                        <a href="/myPage">${userDetails.name} ${userDetails.surname}</a>
                        <a href="/j_spring_security_logout">Вийти</a>
                    </p>
                </sec:authorize>
            </div>
        </div>
        <hr class="horizontalLine" id="bannerLine">
        <div class="bannerBottom">
            <div class="instituteLogo"></div>
            <h3 class="instituteHeaderName">ННІ Енергетики, автоматики <br>і енергозбереження</h3>
            <sec:authorize access="hasRole('ADMIN')">
                <p class="adminLink">
                    <a href="/administrator">Сторінка адміністратора</a>
                </p>
            </sec:authorize>
            <p id="currentDate"></p>
        </div>
    </div>
    <hr>
    <div id="topnav">
        <ul class="nav">
            <li><a href="index.html">Головна</a></li>
            <li><a href="articles.html">Статті</a></li>
            <li><a href="seminars.html">Семінари</a></li>
            <li><a href="conference.html">Конференції</a></li>
            <li class="active"><a href="#">Опублікувати</a>
                <ul>
                    <li><a href="publishArticle.html">Статтю</a></li>
                    <li><a href="publishSeminar.html">Cемінар</a></li>
                    <li><a href="publishConference.html">Корференцію</a></li>
                </ul>
            </li>
            <li><a href="advancedSearch.html">Пошук</a></li>
            <li><a href="contacts.html">Контакти</a></li>
        </ul>
    </div>
    <hr>
</div>

<div class="content">
    <div class="whiteBox">

        <div class="leftSideBar">
            <div class="barHeader">
                Правила публікації
            </div>
            <div class="barContent">
                Описати правила розміщення.
            </div>
        </div>

        <div class="mainContent">

            <form class="form" name="publishForm" id="seminarPublishForm" method="post" action="null">

                <div class="barHeader">
                    <h2>Публікація семінару</h2>
                </div>

                <div class="formField">
                    <label for="seminarId" class="label">
                        Назва семінару
                        <span>*</span>
                    </label>
                    <select class="textField" id="seminarId" name="seminarId"></select>
                </div>

                <div>
                    <label for="seminarKeyWords" class="label">
                        Ключові слова
                        <span>*</span>
                    </label>
                    <textarea class="textField" id="seminarKeyWords" name="seminarKeyWords"></textarea>
                </div>


                <div class="formField">
                    <label id="pickPresentationLabel" for="presentation" class="label">
                        Обрати презентацію
                        <span>*</span>
                    </label>
                    <input type="file" class="textField" id="presentation" name="presentation">
                </div>

                <div class="formField">
                    <label id="pickReportLabel" for="presentation" class="label">
                        Обрати доповідь
                        <span>*</span>
                    </label>
                    <input type="file" class="textField" id="report" name="report">
                </div>

                <div>
                    <input id="publSeminarBtn" class="button" name="seminar" type="button" value="Опублікувати"/>
                    <input id="btnClear" class="button" type="button" value="Очистити"/>
                </div>

            </form>

            <div class="messageField">
                <p id="seminarErrorMessage" name="message"></p>
            </div>
        </div>
        <div class="clear"></div>

    </div>

</body>
</html>